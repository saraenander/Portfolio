function features= newsegment2features4(I)
%Translate picture so that centre of mass is in the origin 
[row,col]=size(I);
coord=regionprops(I,'centroid'); 
cshift=round(col/2-coord.Centroid(1)); 
rshift=round(row/2-coord.Centroid(2)); 
im=circshift(I,[rshift,cshift]);
 
% Crops image into the smallest box possible, still containing the number 
bbox=regionprops(im,'BoundingBox').BoundingBox;
I2=imcrop(im, [bbox(1) bbox(2) bbox(3)-1 bbox(4)-1]);
[r,c]=size(I2);    

%Counts all the pixels in the image
[pixelSum,~]=imhist(I2);

% Inverts image and labels it
iminv=imcomplement(im);
[~, n]=bwlabel(iminv,8);

% Resizes the image to 100x100 and binarizes and labels it
[a,b]=find(I);
im1=I(min(a):max(a),min(b):max(b));
smallim=imbinarize(imresize(im1,[100,100]));
[M,N]=size(smallim);
bottom=smallim(ceil(M/2):M,:);
[L,holes]=bwlabel(~padarray(smallim,[1,1],0,'both'));

% Feature 1 and 2
% Finds perimeter and Euler number of digit 
s=regionprops(I,'EulerNumber','Perimeter');
f1=s.EulerNumber;
f2=s.Perimeter(1);

%Feature 3
%Counts pixels in bottom quarter of image
bottomIm= smallim(round(3*M/4):M,:);
f3=sum(bottomIm,'all')/(M*N/4);

% Feature 4
% Counts pixels in upper part of image
upper = I2(1:round(r/2-3),1:c);
f4=sum(upper,'all');


%Feature 5
% Counts pixels in a square in the middle of the image 
if r/2-1>0 && c/2-1>0
square = I2(round(r/2-1):round(r/2+1),round(c/2-1):round(c/2+1));
f5=sum(square, 'all')/sum(pixelSum);


% Feature 6
% Number of holes in image
f6=n;

% Feature 7
% Intesity shifts along central axis
count=0;
for i=floor(bbox(2))+1:floor(bbox(2)+bbox(4))-1
    intensity=I(i,floor(N(1)));
    if intensity~=I(i+1,floor(N(1)))
        count=count+1;
    end
end
f7=count;

% Feature 8
f8=sum(diff(I2(round(r*0.5),:))==1);

% Feature 9
f9=0;
if(holes==2)
    [a,~]=find(L==2);
    if min(a) > 50
        f9=5;
    elseif min(a)<30 && max(a) > 70
        f9=-10;
    end 
end

% Features 10-13
% Counting white pixels in bottom, top, right and left part of image
top=smallim(1:floor(M/2),:);
f10=sum(bottom,'all')/(M*N/2);
f11=sum(top,'all')/(M*N/2);
right=smallim(:,ceil(N/2):N);
f12=sum(right,'all')/(M*N/2);
left=smallim(:,ceil(N):N/2);
f13=sum(left,'all')/(M*N/2);

features=[f1 f2/100 f3*10 f4/30 f5 f6 f7 f8/10 f9/10 f10*10 f11*10 f12*10 f13*10];

end

