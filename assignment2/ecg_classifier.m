load ('DATPVC/DPVC_106.mat')
ind = DAT.ind;
pvc = DAT.pvc;
ecg = DAT.ecg;

fs = 250;

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


% sumup
myPVC = zeros(length(myPVCRR),1);
for i=1:length(myPVCRR)
    if myPVCRR(i) >= 1
        myPVC(i) = 1;
    end
    if myPVCArea(i) >= 1
        myPVC(i) = 1;
    end
    if myPVCArea(i) + myPVCRR(i) >= 1.9
        myPVC(i) = 1;
    end
end

% test
right = 0;
falsePos = 0;
falseNeg = 0;
truePos = 0;
trueNeg = 0;
for i=1:length(pvc)
    if pvc(i) == myPVC(i)
        right = right + 1;
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
rightPercentage = right / length(pvc) * 100;
totalError = falsePos + falseNeg;
sensitivity = truePos / (truePos + falseNeg);
specificity = trueNeg / (falsePos + trueNeg);

fprintf('==============================================\r')
fprintf('right %f %%\r', rightPercentage)
%fprintf('true positives %i\r', truePos)
%fprintf('true negative %i\r', trueNeg)
%fprintf('errors %i\r', totalError)
fprintf('false positives %i\r', falsePos)
fprintf('false negatives %i\r', falseNeg)
fprintf('sensitivity %f\r', sensitivity)
fprintf('specificity %f\r', specificity)
fprintf('==============================================\r')
