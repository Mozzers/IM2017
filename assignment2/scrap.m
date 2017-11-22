useEcg = ecg(1:ceil(length(ecg)/10));
L = length(useEcg);
n = 2^nextpow2(L);
Y = fft(useEcg, n);
f = fs*(0:(n/2))/n;
P = abs(Y/n);

plot(f,P(1:n/2+1)) 