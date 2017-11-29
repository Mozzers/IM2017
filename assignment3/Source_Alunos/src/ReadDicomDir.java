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
		ImageIO.scanForPlugins();
        filesExames = new Vector();
        atributosExames = new Vector();
        frameTime = new Vector();        
    }
    
    public Vector leDirectorio(String path, Vector atributosExames) throws Exception
    {   	
    	Plugin.setLicenseKey("NM73KIZUPKHLFLAQM5L0V9U"); 
       	File f = new File(path+"DICOMDIR");    	
    	Vector<Vector<String>> records = new Vector<Vector<String>>();
        FileSet fs = new FileSet(f,null);
        FileSet.Directory root = fs.getRootDirectory();
    	int n =root.getNumRecords();
    	for (int i=0; i<n; i++){
    		FileSet.Record r=root.getRecord(i);
    		DataSet att = r.getAttributes();
    		FileSet.Directory study = r.getLowerLevelDirectory(); 
    		DataSet patientAttributes = root.getRecord(i).getAttributes();    		
    		for (int j=0; j<study.getNumRecords(); j++){
    			DataSet studyAttributes = study.getRecord(j).getAttributes();
    			FileSet.Directory series = study.getRecord(j).getLowerLevelDirectory();
    			for(int k=0; k<series.getNumRecords();k++){
    				DataSet seriesAttributes = series.getRecord(k).getAttributes();
    				FileSet.Directory exams = series.getRecord(k).getLowerLevelDirectory();
    				for(int l=0; l<exams.getNumRecords();l++){
    	    			FileSet.Record examRecord = exams.getRecord(l);
    	    			if ("IMAGE".equals(examRecord.getType())){
        					DataSet imageAttributes= examRecord.getAttributes();
        	    			FileSet.Record imageRecord = examRecord;
        	    			//Add exam to vector
        	    			atributosExames.add(new Atributes(patientAttributes,studyAttributes,seriesAttributes,imageAttributes));
        	    			Vector<String> row = new Vector<String>();
    	    				row.add((String)series.getRecord(k).getAttribute(Tag.Modality));
    	    				row.add((String)root.getRecord(i).getAttribute(Tag.PatientID));
    	    				row.add(root.getRecord(i).getAttribute(Tag.PatientsBirthDate).toString());
    	    				row.add((String)root.getRecord(i).getAttribute(Tag.PatientsName));
        	    			records.addElement(row);
    	    			}
    				}
    					
    			}
    				
    		}
    	}
        return records;
    }

}
