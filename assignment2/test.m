%load ('ecg.dat')
%fs = 1000;

load ('DATPVC/DPVC_116.mat')
pvc = DAT.pvc;
ecg = DAT.ecg;
ind = DAT.ind;
fs = 250;

% analyse peaks
% lowpass filter
order = 4;
wc = 20;
fc = wc / (0.5 * fs);
[b, a] = butter(order, fc, 'low');
e1 = filtfilt(b, a, ecg);

% highpass filter
order = 4;
wc = 5;
fc = wc / (0.5 * fs);
[b, a] = butter(order, fc, 'high');
e2 = filtfilt(b, a, e1);

% differentiation
e3 = diff(e2);

% potentiation
e4 = e3.^2;

% moving average
timeWindow = 0.2;
N = timeWindow * fs;
b  = (1 / N) * ones(1, N);
a  = 1;
e5 = filter (b, a, e4);

% find R peaks
threshold = 0.7 * mean(e5);
pause = 0.3;
indexPause = pause * fs;
peaks = 0;
index = 1;
i = 2;
while i<=length(e5)
    if e5(i)==threshold || e5(i-1) < threshold && e5(i) > threshold
        peaks(index) = i;
        index = index + 1;
        i = i + indexPause;
    else
        i = i + 1;
    end
end

% set position
back = 0.2;
backIndex = back * fs;
for i=1:length(peaks)
    minIndex = peaks(i)-backIndex;
    stepBack = backIndex;
    if minIndex <= 0
        stepBack = backIndex + minIndex-2;
        minIndex = 1;
    end
    maxIndex = peaks(i)+backIndex;
    if maxIndex > length(ecg)
        maxIndex = length(ecg);
    end
    tempECG = ecg(minIndex:maxIndex);
    [~, maxIndex] = max(tempECG);
    peaks(i) = peaks(i) + maxIndex - stepBack-2;
end

peaks = peaks';

% test
right = 0;
around = 5;
idx = zeros(length(peaks),1);
for i=0:around
    tmpPeaks = peaks + i;
    tmpIdx = ismember(tmpPeaks, ind);
    idx = idx | tmpIdx;
    tmpPeaks = peaks - i;
    tmpIdx = ismember(tmpPeaks, ind);
    idx = idx | tmpIdx;
end
for i=1:length(idx)
    if idx(i) == true
        right = right + 1;
    end
end
rightPercent = right / length(idx) * 100;

fprintf('==============================================\r')
fprintf('right %f\r', rightPercent)
fprintf('==============================================\r')
