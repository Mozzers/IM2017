load ('DPVC_106.mat')
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

sdnn = sqrt(sum / length(RRIntervals));

myPVC = zeros(length(RRIntervals)+1,1);
for i=1:length(RRIntervals)
    if RRIntervals(i) > meanRR + sdnn
        myPVC(i) = 1;
    end
end

% QRS area
area = 0.1;


% test
right = 0;
falsePos = 0;
falseNeg = 0;
for i=1:length(pvc)
    if pvc(i) == myPVC(i)
        right = right + 1;
    else
        if pvc(i) == 0
            falsePos = falsePos + 1;
        else
            falseNeg = falseNeg + 1;
        end
    end
end
rightPercentage = right / length(pvc) * 100;
totalError = falsePos + falseNeg;

fprintf('\r')
fprintf('==============================================\r')
fprintf('right %f %% \r', rightPercentage)
fprintf('errors %i \r', totalError)
fprintf('false positives %i \r', falsePos)
fprintf('false negative %i \r', falseNeg)
fprintf('==============================================\r')
