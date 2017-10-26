load('DATPVC\DPVC_233');
DAT.ind(1);
result=[];

for j=1:length(DAT.ind)
    ecg=DAT.ecg(DAT.ind(5)-4:DAT.ind(5)+8);
    model=ar(ecg,4);
    A=model.A;
    poles=roots(A);
    
    pvc=0;
    for i=2:length(poles)
        if norm(poles(i))>0.98
            disp("This is a PVC");
            pvc=1;
            break;
        end
    end
    result=[result;pvc];
end