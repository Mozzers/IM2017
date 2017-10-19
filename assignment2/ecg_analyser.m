load ecg.dat

% lowpass filter
order = 4;
wc = 20;
fc = wc / (0.5 * fs);
[b, a]=butter(order, fc, 'Low');
e1 = filter (b, a, ECG);

% highpass filter
order = 4;
wc = 5;
fc = wc / (0.5 * fs);
[b,a] = butter(order, fc, 'High');
e2 = filter (b, a, e1);
