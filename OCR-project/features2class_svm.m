function y= features2class_svm(x, classification_data)
% Using Support Vector Machine to classify the data
    table=classification_data(2:end,:);
    response= classification_data(1,:);
    model=fitcecoc(table', response);
    y=predict(model,x');
end