import java.io.Serializable;

import fr.apteryx.imageio.dicom.Tag;
import fr.apteryx.imageio.dicom.DataSet;


public class Atributes implements Serializable
{
  DataSet regPatient;
  DataSet regStudy;
  DataSet regSeries; 
  DataSet regImage;

  public Atributes(DataSet regPatient,DataSet regStudy, DataSet regSeries,DataSet regImage)
  {
      this.regPatient=regPatient;
      this.regStudy=regStudy;
      this.regSeries=regSeries;
      //this.regPrivate=regPrivate;
      this.regImage=regImage;
      String nome = regPatient.findString(Tag.PatientsName);
      regPatient.add(Tag.PatientsName,nome);
  }

  public void alteraNumFrames(int num)
  {
    regImage.add(Tag.NumberOfFrames,num);
  }

  public DataSet getPatientAtributes()
  {
         return regPatient;
  }

  public DataSet getStudyAtributes()
  {
         return regStudy;
  }

  public DataSet getSeriesAtributes()
  {
         return regSeries;
  }
   /*
  public DataSet getPrivateAtributes()
  {
         return regPrivate;
  } */

  public DataSet getImageAtributes()
  {
         return regImage;
  }

  public boolean existsData(String type)
  {
         if(type.equals("Patient"))
         {
                if(regPatient != null)
                    return true;
                else
                    return false;
         }
         if(type.equals("Study"))
         {
                if(regStudy != null)
                    return true;
                else
                    return false;
         }
         if(type.equals("Series"))
         {
                if(regSeries != null)
                    return true;
                else
                    return false;
         }
         /*
         if(type.equals("Private"))
         {
                if(regPrivate != null)
                    return true;
                else
                    return false;
         } */
         if(type.equals("Image"))
         {
                if(regImage != null)
                    return true;
                else
                    return false;
         }
         return false;
  }

}
