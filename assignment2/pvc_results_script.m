for i=10:11
    [se(:,i),sp(:,i)]=pvc_results_function(['DATPVC_renamed/DPVC_' num2str(i) '.mat']);
    disp(['performed test on patient ' num2str(i)]);
end