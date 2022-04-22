package bo.hlva.glotools.ui.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.ZipUtils;
import java.io.File;
import java.io.IOException;

public class CreateTemplateAppCompatTask extends AsyncTask<String,Boolean,String> {

    private Context context;
    private ProgressDialog dialog;

    public CreateTemplateAppCompatTask(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setMessage("Creating Project..");
        dialog.show();

    }

    @Override
    protected String doInBackground(String[] strings) {

        String pathProject = strings[0];
        String nameProject = strings[1];
        String namePackage = strings[2];

        setupProject(pathProject,nameProject,namePackage);

        return "Completed";
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        dialog.setMessage(result);
        dialog.hide();
    }


    /**
     * crear template del proyecto
     */
    private void setupProject(String dirPathProject,String nameProject,String namePackage){

        /**
         * objectivo 
         * copiar template a archivo temporales
         * descomprimir template en temporales
         * copiar a directorio del proyecto
         * eliminar archivo temporal
         * renombrar valores
         **/

        File dirProject = new File(dirPathProject);

        //template temporal
        File temp = new File(context.getCacheDir(),nameProject+".zip");
        FileUtils.createOrExistsFile(temp);

        //copy from assets
        String nameTemplate = "appcompat.zip";
        ResourceUtils.copyFileFromAssets("templates/appcompat/"+nameTemplate,temp.getAbsolutePath());

        //descomprimir template
        try {
            ZipUtils.unzipFile(temp, dirProject);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //borrar template temporal
        FileUtils.delete(temp);

        renameFiles(dirProject,nameProject,namePackage);
    }

    private void renameFiles(File dirProject,String nameProject,String namePackage){

        //gradle
        File build = new File(dirProject,"app/build.gradle");

        if(build.exists()){
            String content = FileIOUtils.readFile2String(build);
            content = content.replace("$package_name$",namePackage);
            FileIOUtils.writeFileFromString(build,content);
        }

        //AndroidManifest
        File androidManifest = new File(dirProject,"app/src/main/AndroidManifest.xml");

        if(androidManifest.exists()){
            String content = FileIOUtils.readFile2String(androidManifest);
            content = content.replace("$package_name$",namePackage);
            FileIOUtils.writeFileFromString(androidManifest,content);
        }

        //string.xml
        File strings = new File(dirProject,"app/src/main/res/values/strings.xml");

        if(strings.exists()){
            String content = FileIOUtils.readFile2String(strings);
            content = content.replace("$project_name$",nameProject);
            FileIOUtils.writeFileFromString(strings,content);
        }

        //directorio package
        File oldDirPackage = new File(dirProject,"app/src/main/java/$package_name$");
        File dirPackage = new File(dirProject,"app/src/main/java/"+ namePackage.replace(".","/"));
        FileUtils.createOrExistsDir(dirPackage);

        if(oldDirPackage.exists()){
            //move files
            FileUtils.move(oldDirPackage,dirPackage);
        }

        //files java
      //  File debug = new File(dirPackage,"ui/activities/DebugActivity.java");
        File main = new File(dirPackage,"ui/activities/MainActivity.java");

     /*   if(debug.exists()){
            String content = FileIOUtils.readFile2String(debug);
            content = content.replace("$package_name$",namePackage);
            FileIOUtils.writeFileFromString(debug,content);

        }*/

        if(main.exists()){
            String content = FileIOUtils.readFile2String(main);
            content = content.replace("$package_name$",namePackage);
            FileIOUtils.writeFileFromString(main,content);
        }

    }
    
}
