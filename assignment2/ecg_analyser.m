load ('ecg.dat')

fs = 1000;

%figure(1)
%subplot(2,2,1)
%plot(ecg)
%title('ecg')

% lowpass filter
order = 4;
wc = 20;
fc = wc / (0.5 * fs);
[b, a] = butter(order, fc, 'low');
e1 = filtfilt(b, a, ecg);
%subplot(2,2,2)
%plot(e1)
%title('low')

% highpass filter
order = 4;
wc = 5;
fc = wc / (0.5 * fs);
[b, a] = butter(order, fc, 'high');
e2 = filtfilt(b, a, e1);
%subplot(2,2,3)
%plot(e2)
%title('high')

% differentiation
e3 = diff(e2);
%subplot(2,2,4)
%plot(e3)
%title('diff)')

% potentiation
e4 = e3.^2;
%figure(2)
%subplot(2,2,1)
%plot(e4)
%title('pot')

% moving average
timeWindow = 0.2;
N = timeWindow * fs;
b  = (1 / N) * ones(1, N);
a  = 1;
e5 = filter (b, a, e4);
%subplot(2,2,2)
%plot(e5)
%title('avg')

% find peaks
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

% set position TODO?
back = 0.5;
backIndex = back * fs;

for i=1:length(peaks)
    tempECG = ecg(peaks(i)-backIndex:peaks(i));
    [~, maxIndex] = max(tempECG);
    peaks(i) = peaks(i) + maxIndex - backIndex-1;
end

% output
beats = length(peaks);
duration = length(ecg) / fs;
bpm = (60 / duration) * beats;

fprintf('\r')
fprintf('==============================================\r')
fprintf('----------- Duration: %f seconds\r', duration)
fprintf('----------- In beats: %i\r', beats)
fprintf('\r')
fprintf('----------- beats/min: %f\r', bpm)
fprintf('==============================================\r')
