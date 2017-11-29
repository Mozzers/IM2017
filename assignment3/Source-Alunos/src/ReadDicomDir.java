import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import javax.imageio.ImageIO;

import fr.apteryx.imageio.dicom.DataSet;
import fr.apteryx.imageio.dicom.DicomReader; 
import fr.apteryx.imageio.dicom.FileSet; 
import fr.apteryx.imageio.dicom.Tag; 
import fr.apteryx.imageio.dicom.Plugin;

public class ReadDicomDir {

    Vector filesExames,atributosExames,frameTime; 
    
    
    public void ReadDicomDir()
    {
        Plugin.setLicenseKey("NM73KIZUPKHLFLAQM5L0V9U"); 
        filesExames = new Vector();
        atributosExames = new Vector();
        frameTime = new Vector();        
    }
    
    public Vector leDirectorio(String path, Vector atributosExames) throws Exception
    {
    	/*
    	 * Para um dado DICOMDIR especificado pelo utilizador, liste os
diversos exames do tipo imagem existentes no espaço definido
pelo DICOMDIR. Em particular, pretende-se que seja apresentado
num componente do tipo tabela uma entrada por cada uma das
SOPs de imagem existentes em ficheiro, apontadas naturalmente
por uma Directory Record do tipo IMAGE. Uma forma fácil de obter
essa informação é percorrendo os Directory Records (DR) do
DICOMDIR até atingir DRs do tipo IMAGE.*/
    	
       	File f = new File(path+"DICOMDIR");    	
    	Vector<FileSet.Record> records = new Vector<FileSet.Record>();
        FileSet fs = new FileSet(f,null); 
        FileSet.Directory root = fs.getRootDirectory();
    	int n =root.getNumRecords();
    	for (int i=0; i<n; i++){
    		FileSet.Record r=root.getRecord(i);
    		DataSet att = r.getAttributes();
    		FileSet.Directory study = r.getLowerLevelDirectory(); 
    		for (int j=0; j<study.getNumRecords(); j++){
    			FileSet.Directory series = study.getRecord(j).getLowerLevelDirectory();
    			for(int k=0; k<series.getNumRecords();k++){
    				FileSet.Directory exams = series.getRecord(k).getLowerLevelDirectory();
    				for(int l=0; l<exams.getNumRecords();l++){
    	    			/* Add attributes from images */
    	    			FileSet.Record examRecord = exams.getRecord(l);
    	    			if ("IMAGE".equals(examRecord.getType())){
    	    				
    	    				records.addElement(examRecord);
    	    				atributosExames.addElement(examRecord.getAttributes());
    	    			}
    	    			//Add exam to vector
    	    			//examsAttributes.add(new Attributes(patientAttributes,studyAttributes,seriesAttributes,imageAttributes));
    	    			//frameTime.add((Float) imageRecord.getAttribute(Tag.FrameTime));
    	    			//examImagesPaths.add(path + (imageRecord.getFile()).getPath());
    	    			//directoriesRecordTypes.add((String)imageRecord.getAttribute(Tag.DirectoryRecordType));
    				}
    					
    			}
    				
    		}
    	}
        return records;
    }

}
