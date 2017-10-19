fid = fopen('message.hl7','r');
j = 1;
while ~feof(fid)
    segment = fgets(fid);
    if j~=1
        fields = strsplit(segment,'|');
        for i=1:size(fields)
            components1 = fields(i);
            components = strsplit(components1{1},'^');
        end
    end
    j = j+1;
end
