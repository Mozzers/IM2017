
ecg=DAT.ecg;
peaks=DAT.ind;

for i=1:length(peaks)
    if(peaks(i)>50)
        A=ecg(start:finish);
        start=peaks(i)-50;
        finish=peaks(i)+50;
    elseif (peaks(i)<50)       %case first peak is before 50
        A=ecg(1:finish);
        start=1;
        finish=peaks(i)+50;
    elseif (peaks(i)+50>length(ecg))
        A=ecg(peaks(i)-50:length(ecg)); %case for last peak
    end 
    [~,ind]=max(A);                 %find positive peak
    peaks(i)=peaks(i)-start+ind;       %code to reassign peak to positive values    
end
   

plot(1:5000,DAT.ecg(1:5000),'-',DAT.ind(1:20),DAT.ecg(DAT.ind(1:20)),'*')
figure
plot(1:5000,ecg(1:5000),'-',peaks(1:20),ecg(peaks(1:20)),'*')