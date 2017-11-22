load ('DATAF/afdb_file-04043_episode-1.mat')
class = DAT.class;
annot = DAT.annot;
ecg = DAT.ecg;
fs = 250;

peaks = detectPeaks(ecg, fs);
sdnnRRALL = calculateSDNNRR(peaks);

window = 20;
windowIndex = window * fs;

windowCount = ceil(length(ecg) / windowIndex);
sdnnRRWindows = zeros(windowCount,1);
lfhfWindows = zeros(windowCount,1);
i=1;
index=1;
finish = false;
while i<=length(ecg)-windowIndex
    tempEcg = ecg(i:i+windowIndex);
    tempPeaks = detectPeaks(tempEcg, fs);
    
    % irregularity of cardiac rhyme - RR intervals
    sdnnRRWindows(index) = calculateSDNNRR(tempPeaks);
    
    % analysis of atrial activity
    front = 0.06;
    frontIndex = front * fs;
    back = 0.02;
    backIndex = back * fs;
    for j=1:length(tempPeaks)
        minIndex = tempPeaks(j)-frontIndex;
        maxIndex = tempPeaks(j)+backIndex;
        if minIndex < 1
            minIndex = 1;
        end
        if maxIndex > length(tempEcg)
            maxIndex = length(tempEcg);
        end
    
        for k=minIndex:maxIndex
            tempEcg(k) = NaN;
        end
    end
    tempEcg(isnan(tempEcg)) = [];
    % TODO
    LF = bandpower(tempEcg, fs, [0.04 0.15]);
    HF = bandpower(tempEcg, fs, [0.15 0.4]);
    LFHF = LF / HF;
    lfhfWindows(index) = LFHF;

    i = i + windowIndex;
    index = index + 1;
    if i > length(ecg)-windowIndex && ~finish
        i = length(ecg)-windowIndex;
        finish = true;
    end
end
sdnnRRWindows = sdnnRRWindows';
sdnnRRWindows = sdnnRRWindows / max(sdnnRRWindows);
sdnnRRWindows = 1 - sdnnRRWindows;
lfhfWindows = lfhfWindows / max(lfhfWindows);

myClass = length(class);
for i=1:windowCount
    value = 0;
    if sdnnRRWindows(i) < 0.9 && lfhfWindows(i) < 0.4
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
