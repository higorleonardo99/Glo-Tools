package bo.hlva.glotools.utils;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import java.io.IOException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import android.content.Context;
import android.content.SharedPreferences;


/**
* clase utilidades para proyecto
* @author Higor Leonardo Vargas Arrazola
* @version 16.02.22
*/
public class ProjectUtils {
    
    public static boolean isParentJava(File file){
        
        return file.getParent().contains("java");
        
    }
    /*
    public static boolean isParentRes(){

    }*/
    
    
    // read namepackage for AndroidManifest
  /*  public static String getNamePackage(File dirProject){
        
        String namePackage = null;
        
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documemtBuilder = documentBuilderFactory.newDocumentBuilder();
            try {
                Document doc = documemtBuilder.parse(new File(dirProject,"app/src/main/AndroidManifest.xml"));
                
                NodeList list = doc.getElementsByTagName("manifest");
                
                for(Node node : list){
                    
                }
                
                
            } catch (SAXException e) {} catch (IOException e) {}

            
        } catch (ParserConfigurationException e) {
            
            e.printStackTrace();
        }
        
       

        return namePackage;
    }
    */
   
}
