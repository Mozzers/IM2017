[fileID,msg] = fopen('message.hl7');
inp = textscan(fileID,'%s','Delimiter','\n');
res=vertcat(inp{1,1});
fclose(fileID);

columns=1;
rows=length(res);
 for i=1:rows
    temp = strread(res{i,1},'%s','delimiter','|')';
    if length(temp)>columns %Columns store the maximum number of fields found
        for j=1:i           %if a new segment has more field than the previous, the previous are padded with '\p'
            fields(j,columns+1:length(temp))={'\p'};  %to pad, the old value of columns is used until the new one
        end
         columns=length(temp);
    end
    fields(i,1:length(temp))=temp;
 end
 