function [S] = im2segment2(im)
nrofsegments = 5;
S=cell(1,nrofsegments);
[m,n]=size(im);
weight=[1 1 1 ;1 14 1 ; 1 1 1];
imConv=conv2(im, weight,'same');
N=imbinarize(imConv,700);
N=bwareaopen(N,30);
[L,nrlabels]=bwlabel(N,8);
label=1;
i=1;
while i<=nrofsegments && label<= nrlabels
[r,c] = find(L==label);
        newImage=zeros(m,n);
        for j=1:size(r)
            newImage(r(j),c(j))=1;
        end
     S{i}=newImage;
     i=i+1;
   label=label+1;
end
