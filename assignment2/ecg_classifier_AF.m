load ('DATAF/afdb_file-04043_episode-1.mat')
class = DAT.class;
annot = DAT.annot;
ecg = DAT.ecg;
fs = 250;

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

threshold = (mean(sdnnRRWindows) - min(sdnnRRWindows))/2;

% myMax = max(sdnnRRWindows);
% for i=1:length(sdnnRRWindows)
%     tmpsdnnRRWindows(i)=(sdnnRRWindows(i) ^ 2) / (myMax);
% end
% threshold=mean(tmpsdnnRRWindows);

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

% figure(1)
% plot(class, 'green')
% hold on
% plot(myClass, 'blue')
% hold off
% 
% bla1 = zeros(1,length(sdnnRRWindows));
% bla2 = zeros(1,length(sdnnRRWindows));
% bla3 = zeros(1,length(sdnnRRWindows));
% for i=1:length(sdnnRRWindows)
%     bla1(i) = sdnnRRALL;
%     bla2(i) = threshold;
%     bla3(i) = threshold1;
% end
% figure(2)
% plot(sdnnRRWindows, 'g-o')
% hold on
% plot(bla1, 'blue')
% hold on
% plot(bla2, 'red')
% hold on
% plot(bla3, 'yellow')
% hold off
