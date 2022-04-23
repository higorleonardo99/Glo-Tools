package bo.hlva.glotools.ui.dialogs;

import android.view.View;
import bo.hlva.glotools.ui.listeners.OnProjectChangeListener;
import bo.hlva.glotools.R;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ResourceUtils;
import com.google.android.material.textfield.TextInputEditText;
import java.io.File;

public class CreateActivityDialog extends BaseDialogFragment {

    private TextInputEditText edtNameActivity,edtNameLayout;
    
    public static final String TAG = "CreateActivityDialog";
    private static CreateActivityDialog instance;

    private File dirFile;
    private File dirProject;


    public static CreateActivityDialog getInstance(File dirProject,File dirFilePath,OnProjectChangeListener projectlistener){

        if(instance == null){
            instance = new CreateActivityDialog(dirProject,dirFilePath);
            instance.setOnProjectChangeListener(projectlistener);
        }

        return instance;
    }

    public CreateActivityDialog(File dirProject,File dirFile){
        this.dirProject = dirProject;
        this.dirFile = dirFile;
    }
    
    
    
    @Override
    public String getTitle() {
        return "New Activity";
    }

    @Override
    public View getInflateView() {
        
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.dialog_create_activity,null,false);
        edtNameActivity = rootView.findViewById(R.id.dialog_create_activity_edt_name_activity);
        edtNameLayout = rootView.findViewById(R.id.dialog_create_activity_edt_name_layout);
        
        return rootView;
    }

    @Override
    public void onClickPositive() {
        
        if(isOk()){
            
            File fileActivity = new File(dirFile,edtNameActivity.getText().toString()+".java");
            File fileLayout = new File(dirProject,"app/src/main/res/layout/"+edtNameLayout.getText().toString()+".xml");

            //create file
            FileUtils.createOrExistsFile(fileActivity);
            FileUtils.createOrExistsFile(fileLayout);

            //copy template from assets
            ResourceUtils.copyFileFromAssets("templates/DemoActivity.txt",fileActivity.getAbsolutePath());
            ResourceUtils.copyFileFromAssets("templates/layout_activity.txt",fileLayout.getAbsolutePath());

            //search path package
            String[] pathPackage = fileActivity.getParent().split("/java.");

            //write file templates
            String textFileFragment = FileIOUtils.readFile2String(fileActivity);
            textFileFragment = textFileFragment.replace("$activity_name$",edtNameActivity.getText().toString());
            textFileFragment = textFileFragment.replace("$layout_name$",edtNameLayout.getText().toString());
            textFileFragment = textFileFragment.replace("$package_name$",pathPackage[1].replace("/","."));

            FileIOUtils.writeFileFromString(fileActivity,textFileFragment);

            
            listener.onProjectChange();
            dismiss();
        }
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public String getTextPositiveButton() {
        return "create";
    }

    /**
     * verificar campos de textos
     */
    private boolean isOk(){

        if(edtNameActivity.getText().toString().isEmpty()){
            edtNameActivity.setError("Error Name Fragment");
            edtNameActivity.requestFocus();

            return false;
        }
        if(edtNameLayout.getText().toString().isEmpty()){
            edtNameLayout.setError("Error Name Layout");
            edtNameLayout.requestFocus();

            return false;
        }

        return true;

    }
    
    
}
