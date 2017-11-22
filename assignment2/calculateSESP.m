function [sensitivity,specificity]=calculateSESP(pvc, myPVC)
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
