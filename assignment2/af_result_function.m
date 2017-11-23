function [se,sp] = af_result_function(patient, f1,f2,f3,w_size,tr_RR,tr_LFHF)


load (patient)
class = DAT.class;
annot = DAT.annot;
ecg = DAT.ecg;
fs = 250;
peaks = detectPeaks(ecg, fs);
sdnnRRALL = calculateSDNNRR(peaks);

window = w_size;
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
    LF = bandpower(tempEcg, fs, [f1 f2]);
    HF = bandpower(tempEcg, fs, [f2 f3]);
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


myClass1 = length(class);
for i=1:windowCount
    value = 0;
    if sdnnRRWindows(i) < tr_RR
        value = 1;
    end
    for j=(i-1)*windowIndex+1:i*windowIndex+1
        index = j;
        if index>length(class)
            index = length(class);
        end
        myClass1(index) = value;
    end
end

myClass2 = length(class);
for i=1:windowCount
    value = 0;
    if lfhfWindows(i) < tr_LFHF
        value = 1;
    end
    for j=(i-1)*windowIndex+1:i*windowIndex+1
        index = j;
        if index>length(class)
            index = length(class);
        end
        myClass2(index) = value;
    end
end

myClass3 = length(class);
for i=1:windowCount
    value = 0;
    if sdnnRRWindows(i) < tr_RR && lfhfWindows(i) < tr_LFHF
        value = 1;
    end
    for j=(i-1)*windowIndex+1:i*windowIndex+1
        index = j;
        if index>length(class)
            index = length(class);
        end
        myClass3(index) = value;
    end
end

[se(1),sp(1)]=calculateSESP(class,myClass1);
[se(2),sp(2)]=calculateSESP(class,myClass2);
[se(3),sp(3)]=calculateSESP(class,myClass3);