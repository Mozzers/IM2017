load ecg.dat

fs = 1000;

figure(1)
subplot(2,2,1)
plot(ecg)
title('ecg')

% lowpass filter
order = 4;
wc = 20;
fc = wc / (0.5 * fs);
[b, a]=butter(order, fc, 'Low');
e1 = filter (b, a, ecg);
subplot(2,2,2)
plot(e1)
title('low')

% highpass filter
order = 4;
wc = 5;
fc = wc / (0.5 * fs);
[b,a] = butter(order, fc, 'High');
e2 = filter (b, a, e1);
subplot(2,2,3)
plot(e2)
title('high')

% differentiation
e3 = diff(e2);
subplot(2,2,4)
plot(e3)
title('diff')

% potentiation
e4 = e3.^2;
figure(2)
subplot(2,2,1)
plot(e4)
title('pot')

% moving average
timeWindow = 0.2;
N = 50;
b  = (1/N)*ones (1, N);
a  = 1;
e5 = filter (b, a, e4);
subplot(2,2,2)
plot(e5)
title('avg')

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
        index = index+1;
        i = i+indexPause;
    else
        i = i+1;
    end
end
length(peaks)
