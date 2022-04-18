package bo.hlva.glotools.data.model;

import java.io.File;
import bo.hlva.glotools.utils.ProjectUtils;

public class ItemProject {
    
    private File fileProject;
    
    public ItemProject(File file){
        this.fileProject = file;
    }
    
    public File getItemProject(){
        return this.fileProject;
    }
  /*  
    public String namePackage(){
        return ProjectUtils.getNamePackage(fileProject);
    }
    */
}
