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
       

        return null;
    }

}
