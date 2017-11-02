<<<<<<< HEAD
load ('DATPVC/DPVC_233.mat');
=======
load ('DATPVC\DPVC_116.mat');
>>>>>>> 74eb11fa084eb2c1bcc86a09b9ce7c9ee24a208b
ecg=DAT.ecg;
peaks=DAT.ind;

%Code to get positive peaks
for i=1:length(peaks)
    if(peaks(i)>50 && peaks(i)+50<length(ecg))
        start=peaks(i)-50;
        finish=peaks(i)+50;        
    elseif (peaks(i)<50)       %case first peak is before 50
        start=1;
        finish=peaks(i)+50;
    else %case for last peak
        start=peaks(i)-50;
        finish=length(ecg);
    end 
    A=ecg(start:finish); 
    [~,ind]=max(A);                 %find positive peak
    peaks(i)=start+ind;       %code to reassign peak to positive values
end


<<<<<<< HEAD
for j=1:length(peaks)-1       %Take into account first and last case later
=======
for j=1:length(peaks)       %Take into account first and last case later
>>>>>>> 74eb11fa084eb2c1bcc86a09b9ce7c9ee24a208b
    a=8;
    b=8;
    window=ecg(peaks(j)-a:peaks(j)+b);

    model=ar(window,2);
    A=model.A;
    poles=roots(A);
    pvc=0;
    for i=1:length(poles)
<<<<<<< HEAD
        if norm(poles(i))>0.99
=======
        if norm(poles(i))>=1
>>>>>>> 74eb11fa084eb2c1bcc86a09b9ce7c9ee24a208b
            pvc=1;
            break;
        end
    end
    myPVC(j)= pvc;
end

% test
right=0;
truePos = 0;
trueNeg=0;
falsePos = 0;
falseNeg = 0;
for i=1:length(DAT.pvc)-1
    if DAT.pvc(i) == myPVC(i)
        right = right + 1;
        if myPVC(i) == 0
            trueNeg = trueNeg + 1;
        else
            truePos = truePos + 1;
        end
    else
        if myPVC(i) == 0
            falseNeg = falseNeg + 1;
        else
            falsePos = falsePos + 1;
        end
    end
end
rightPercentage = truePos+trueNeg / length(myPVC) * 100;
totalError = falsePos + falseNeg;
sensitivity = truePos / (truePos + falseNeg);
specificity = trueNeg / (falsePos + trueNeg);

fprintf('\r')
fprintf('==============================================\r')
fprintf('right %f %% \r', rightPercentage)
fprintf('errors %i \r', totalError)
fprintf('false positives %i \r', falsePos)
fprintf('false negative %i \r', falseNeg)
fprintf('sensitivity %f\r', sensitivity)
fprintf('specificity %f\r', specificity)
fprintf('==============================================\r')
