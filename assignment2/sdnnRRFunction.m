function [sdnnRR] = sdnnRRFunction(peaks)

    % RR regularity
    RRIntervals = zeros(length(peaks)-1,1);
    for i=2:length(peaks)
        RRIntervals(i-1) = peaks(i) - peaks(i-1);
    end
    meanRR = mean(RRIntervals);

    sum = 0;
    for i=1:length(RRIntervals)
        sum = sum + (RRIntervals(i) - meanRR).^2;
    end
    
    sdnnRR = sqrt(sum / length(RRIntervals));
end

