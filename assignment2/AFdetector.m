load ('DATAF/afdb_file-04043_episode-4.mat')
class = DAT.class;
annot = DAT.annot;
ecg = DAT.ecg;
fs = 250;

sdnnRRALL = sdnnRRFunction(ecg, fs);

window = 20;
windowIndex = window * fs;

windowCount = ceil(length(ecg) / windowIndex);
sdnnRRWindows = zeros(windowCount,1);
i=1;
index=1;
finish = false;
while i<=length(ecg)-windowIndex
    tempEcg = ecg(i:i+windowIndex);
    
    sdnnRRWindows(index) = sdnnRRFunction(tempEcg, fs);

    i = i + windowIndex;
    index = index + 1;
    if i > length(ecg)-windowIndex && ~finish
        i = length(ecg)-windowIndex;
        finish = true;
    end
end
sdnnRRWindows = sdnnRRWindows';

threshold = (sdnnRRALL - min(sdnnRRWindows))/2;
myClass = length(class);
for i=1:windowCount
    value = 0;
    if sdnnRRWindows(i) >= threshold
        value = 1;
    end
    for j=(i-1)*windowIndex+1:i*windowIndex+1
        index = j;
        if index>length(class)
            index = length(class);
        end
        myClass(index) = value;
    end
end

% test
falsePos = 0;
falseNeg = 0;
truePos = 0;
trueNeg = 0;
for i=1:length(myClass)
    if class(i) == myClass(i)
        if myClass(i) == 0
            trueNeg = trueNeg + 1;
        else
            truePos = truePos + 1;
        end
    else
        if myClass(i) == 0
            falseNeg = falseNeg + 1;
        else
            falsePos = falsePos + 1;
        end
    end
end
sensitivity = truePos / (truePos + falseNeg);
specificity = trueNeg / (falsePos + trueNeg);

fprintf('==============================================\r')
fprintf('sensitivity %f\r', sensitivity)
fprintf('specificity %f\r', specificity)
fprintf('==============================================\r')
