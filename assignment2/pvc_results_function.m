function [sensitivity,specificity] = pvc_data(patient)

    load(patient)
    pvc = DAT.pvc;
    ecg = DAT.ecg;
    ind = DAT.ind;
    fs = 250;

    peaks = detectPeaks(ecg, fs);

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

%     fprintf('==============================================\r')
%     fprintf('right %f\r', rightPercent)
%     fprintf('==============================================\r')

    % analyse PVC peaks
    %ind = peaks; % uncomment for use own peaks

    % RR regularity
    RRIntervals = zeros(length(ind)-1,1);
    for i=2:length(ind)
        RRIntervals(i-1) = ind(i) - ind(i-1);
    end
    meanRR = mean(RRIntervals);
    sdnnRR = calculateSDNNRR(peaks);

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
        if maxIndex > length(ecg)
            maxIndex = ind(j);
        end

        window=ecg(minIndex:maxIndex);

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
    myPVC1 = zeros(length(myPVCRR),1);
    myPVC2 = zeros(length(myPVCRR),1);
    myPVC3 = zeros(length(myPVCRR),1);
    myPVC4 = zeros(length(myPVCRR),1);
    myPVC5 = zeros(length(myPVCRR),1);
    for i=1:length(myPVCRR)
        if myPVCRR(i) >= 1
            myPVC1(i) = 1;
        end
        if myPVCArea(i) >= 1
            myPVC2(i) = 1;
        end
        if (myPVCArea(i) + myPVCRR(i) >= 1.9)
            myPVC3(i) = 1;
        end
        if myPVCHermit(i) == 1
            myPVC4(i) = 1;
        end
        if myPVCRR(i) >= 1 || myPVCArea(i) >= 1 || (myPVCArea(i) + myPVCRR(i) >= 1.9) && myPVCHermit(i) == 1
            myPVC5(i) = 1;
        end
    end

    %calculate se and sp
    [sensitivity(1),specificity(1)]=calculateSESP(pvc,myPVC1);
    [sensitivity(2),specificity(2)]=calculateSESP(pvc,myPVC2);
    [sensitivity(3),specificity(3)]=calculateSESP(pvc,myPVC3);
    [sensitivity(4),specificity(4)]=calculateSESP(pvc,myPVC4);
    [sensitivity(5),specificity(5)]=calculateSESP(pvc,myPVC5);
    
    % fprintf('==============================================\r')
    % fprintf('sensitivity %f\r', sensitivity)
    % fprintf('specificity %f\r', specificity)
    % fprintf('==============================================\r')
