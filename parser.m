[fileID,msg] = fopen('message.hl7');
inp = textscan(fileID,'%s','Delimiter','\n');
res=vertcat(inp{1,1});
fclose(fileID);

columns=1;
rows=length(res);
 for i=1:rows
    temp = strread(res{i,1},'%s','delimiter','|')'; %The output is transposed so to get every segment on a row
    if length(temp)>columns %Columns store the maximum number of fields found
        for j=1:i           %if a new segment has more field than the previous, the previous are padded with '\p'
            fields(j,columns+1:length(temp))={'\p'};  %to pad, the old value of columns is used until the new one
        end
         columns=length(temp);
    end
    fields(i,1:length(temp))=temp;
 end
     

%checking validity
if ~strcmp(fields{1,1},'MSH')
    disp('First segment is not MSH - invalid syntax');
    return
end
if ~strcmp(fields{2,1},'PID')
    disp('Second segment is not Patient ID - invalid syntax');
    return
end


%There should be at least one OBR segment, if there are any 
validOBR=false;
validOBX=true;
for i=3:rows
    if strcmp(fields{i,1},'OBR')
        validOBR=true;
        if (i+1<rows)                        %check if the message does not end with an OBR segment
            if strcmp(fields{i+1,1},'OBX')
                if(1~=str2num(fields{i+1,2}))   %If the first OBX is not with ID 1
                    validOBX=false;
                    break
                end
                j=2;
                if(i+j<rows)                    %Check if the message ends with an OBX
                    while strcmp(fields{i+j,1},'OBX') %check if subsequent OBX have correct ID
                        if (j~=str2num(fields{i+j,2}))
                            validOBX=false;
                            break
                        end
                    j=j+1;    
                    end
                end
            end
        end
    end
end

if ~validOBR
    disp('There is not an OBR segment - invalid syntax');
    return
end

if ~validOBX
    disp('There is a problem with OBX segment indexes - invalid syntax');
    return
end
name=strsplit(fields{2,6},'^');
height=fields{5,6};
heightMeasure=strsplit(fields{5,7},'^');

weight=fields{6,6};
weightMeasure=strsplit(fields{6,7},'^');

heartRate=fields{7,6};
heartMeasure=strsplit(fields{7,7},'^');

sysPre=fields{8,6};
sysMeasure=strsplit(fields{8,7},'^');

dyaPre=fields{9,6};
dyaMeasure=strsplit(fields{9,7},'^');

fprintf('Information about the patient: %s %s\n' ,name{1},name{2}); 
fprintf('Details: \n');
fprintf('Weight: %s %s\n' ,weight,weightMeasure{2}); 
fprintf('Height: %s %s\n' ,height,heightMeasure{2}); 
fprintf('Systolic blood pressure: %s %s\n' ,sysPre,sysMeasure{2}); 
fprintf('Dyastolic blood pressure: %s %s\n' ,dyaPre,dyaMeasure{2}); 
fprintf('Mean blood pressure by ppg shown in figure\n'); 

y=str2double(strsplit(fields{13,6},'^'));
x=1:1000;
plot(x,y);
