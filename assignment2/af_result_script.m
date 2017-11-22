f1=0.04;
f2=0.15;
f3=0.4;
w_size=20;
for i=1:19
    if i==8
        continue
    end
    [se1(:,i),sp1(:,i)]=af_result_function(['DATF_renamed/afdb_file-' num2str(i) '.mat'],f1,f2,f3,w_size);
    disp(['performed test 1 on patient ' num2str(i)]);
end

f1=0.4;
f2=1.5;
f3=4;
for i=1:19
    if i==8
        continue
    end
    [se2(:,i),sp2(:,i)]=af_result_function(['DATF_renamed/afdb_file-' num2str(i) '.mat'],f1,f2,f3,w_size);
    disp(['performed test 2 on patient ' num2str(i)]);
end

f1=4;
f2=15;
f3=40;
for i=1:19
    if i==8
        continue
    end
    [se3(:,i),sp3(:,i)]=af_result_function(['DATF_renamed/afdb_file-' num2str(i) '.mat'],f1,f2,f3,w_size);
    disp(['performed test 3 on patient ' num2str(i)]);
end

m_se(1,1)=mean(se1(1,:));
m_se(2,1)=mean(se1(2,:));
m_se(3,1)=mean(se1(3,:));
m_se(1,2)=mean(se2(1,:));
m_se(2,2)=mean(se2(2,:));
m_se(3,2)=mean(se2(3,:));
m_se(1,3)=mean(se3(1,:));
m_se(2,3)=mean(se3(2,:));
m_se(3,3)=mean(se3(3,:));


m_sp(1,1)=mean(sp1(1,:));
m_sp(2,1)=mean(sp1(2,:));
m_sp(3,1)=mean(sp1(3,:));
m_sp(1,2)=mean(sp2(1,:));
m_sp(2,2)=mean(sp2(2,:));
m_sp(3,2)=mean(sp2(3,:));
m_sp(1,3)=mean(sp3(1,:));
m_sp(2,3)=mean(sp3(2,:));
m_sp(3,3)=mean(sp3(3,:));

%vertical= changing classification method
%horizontal= changing frequency parameter0

%first method does not change with frequency
