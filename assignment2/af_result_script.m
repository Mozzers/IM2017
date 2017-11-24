f1=0;
f2=40;
f3=124;
w_size=20;
tRR=0.8;
tF=0.6;

for i=1:19
    if i==8
        continue
    end
    [se1(:,i),sp1(:,i)]=af_result_function(['DATF_renamed/afdb_file-' num2str(i) '.mat'],f1,f2,f3,w_size,tRR,tF);
    disp(['performed test 1 on patient ' num2str(i)]);
end

se1=se1';
sp1=sp1';
se1(8,:)=[];
sp1(8,:)=[];

%vertical= changing classification method
%horizontal= changing frequency parameter0

%first method does not change with frequency
