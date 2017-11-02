% load('DATPVC\DPVC_233');
% DAT.ind(1);
% 
for j=3:length(DAT.ind)-1       %Take into account first and last case later
    %a=(DAT.ind(j)-DAT.ind(j-1))/3;
    a=20;
    %b=(DAT.ind(j+1)-DAT.ind(j))/3;
    b=20;
    ecg=DAT.ecg(DAT.ind(j)-a:DAT.ind(j)+b);
    %ecg=DAT.ecg;

    model=ar(ecg,4);
    A=model.A;
    poles=roots(A);
    pvc=0;
    for i=1:length(poles)
        if norm(poles(i))>0.98
            pvc=1;
            break;
        end
    end
    result(j)= pvc;
end

% test
right = 0;
falsePos = 0;
falseNeg = 0;
for i=1:length(DAT.pvc)-1
    if DAT.pvc(i) == result(i)
        right = right + 1;
    else
        if result(i) == 0
            falseNeg = falseNeg + 1;
        else
            falsePos = falsePos + 1;
        end
    end
end
rightPercentage = right / length(result) * 100;
totalError = falsePos + falseNeg;

fprintf('\r')
fprintf('==============================================\r')
fprintf('right %f %% \r', rightPercentage)
fprintf('errors %i \r', totalError)
fprintf('false positives %i \r', falsePos)
fprintf('false negative %i \r', falseNeg)
fprintf('==============================================\r')
