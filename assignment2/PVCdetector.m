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
% right = 0;
% false = 0;
% around = 5;
% for i=1:length(peaks)
%     if peaks(i)-around <= ind(i) && peaks(i)+around >= ind(i)
%     %if peaks(i) == ind(i)
%         right = right + 1;
%     else
%         false = false + 1;
%     end
% end
% 
% rightPercent = right / (right + false) * 100;
% 
% fprintf('==============================================\r')
% fprintf('right %f\r', rightPercent)
% fprintf('==============================================\r')

% analyse PVC peaks
%ind = peaks; % uncomment for use own peaks

% RR regularity
RRIntervals = zeros(length(ind)-1,1);
for i=2:length(ind)
    RRIntervals(i-1) = ind(i) - ind(i-1);
end
meanRR = mean(RRIntervals);

sum = 0;
for i=1:length(RRIntervals)
    sum = sum + (RRIntervals(i) - meanRR).^2;
end
sdnnRR = sqrt(sum / length(RRIntervals));

myPVCRR = zeros(length(RRIntervals)+1,1);
for i=1:length(RRIntervals)
	myPVCRR(i) = RRIntervals(i) / (meanRR + sdnnRR);
end

% QRS area
area = 0.06;
areaIndex = round(area * fs);

areas = zeros(length(ind),1);
for i=1:length(ind)
    minIndex = ind(i)-areaIndex;
    if minIndex <= 0
        minIndex = 1;
    end
    maxIndex = ind(i)+areaIndex;
    if maxIndex > length(ecg)
        maxIndex = length(ecg);
    end
    actualAreaECG = ecg(minIndex:maxIndex);
    areas(i) = trapz(actualAreaECG);
end

meanArea = mean(areas);

sum = 0;
for i=1:length(areas)
    sum = sum + (areas(i) - meanArea).^2;
end

sdnnArea = sqrt(sum / length(RRIntervals));

myPVCArea = areas(:) / (meanArea + sdnnArea);

% hermite
% Code to get positive peaks
for i=1:length(ind)
    if(ind(i)>50 && ind(i)+50<length(ecg))
        start=ind(i)-50;
        finish=ind(i)+50;        
    elseif (ind(i)<50) % case first peak is before 50
        start=1;
        finish=ind(i)+50;
    else % case for last peak
        start=ind(i)-50;
        finish=length(ecg);
    end 
    A=ecg(start:finish); 
    [~,index]=max(A); % find positive peak
    ind(i)=start+index; % code to reassign peak to positive values
end

myPVCHermit = zeros(length(ind),1);
for j=1:length(ind) % Take into account first and last case later
    a=8;
    b=8;
    
    minIndex = ind(j)-a;
    if minIndex <= 0
        minIndex = 1;
    end
    
    maxIndex = ind(j)+b;
    if maxIndex > length(ind)
        maxIndex = length(ind);
    end
    
    window=ecg(ind(j)-a:ind(j)+b);

    model=ar(window,2);
    A=model.A;
    poles=roots(A);
    tmppvc=0;
    for i=1:length(poles)
        if norm(poles(i))>=0.98
            tmppvc=1;
            break;
        end
    end
    myPVCHermit(j)= tmppvc;
end

% sumup
myPVC = zeros(length(myPVCRR),1);
for i=1:length(myPVCRR)
    if myPVCRR(i) >= 1 || myPVCArea(i) >= 1 || (myPVCArea(i) + myPVCRR(i) >= 1.9) && myPVCHermit(i) == 1
        myPVC(i) = 1;
    end
end

% test
falsePos = 0;
falseNeg = 0;
truePos = 0;
trueNeg = 0;
for i=1:length(myPVC)
    if pvc(i) == myPVC(i)
        if myPVC(i) == 0
            trueNeg = trueNeg + 1;
        else
            truePos = truePos + 1;
        end
    else
        if myPVC(i) == 0
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
