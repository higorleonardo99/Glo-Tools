package bo.hlva.glotools.ui.dialogs;

import android.view.View;
import bo.hlva.glotools.ui.listeners.OnProjectChangeListener;
import bo.hlva.glotools.R;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ResourceUtils;
import com.google.android.material.textfield.TextInputEditText;
import java.io.File;

public class CreateFragmentDialog extends BaseDialogFragment {

    
    public static final String TAG = "CreateFragmentDialog";
    private static CreateFragmentDialog instance;
    
    private File dirFile;
    private File dirProject;
    
    private TextInputEditText edtNameFragment,edtNameLayout;
    
    
    public static CreateFragmentDialog getInstance(File dirProject,File dirFilePath,OnProjectChangeListener projectlistener){
        
        if(instance == null){
            instance = new CreateFragmentDialog(dirProject,dirFilePath);
            instance.setOnProjectChangeListener(projectlistener);
        }
        
        return instance;
    }
    
    public CreateFragmentDialog(File dirProject,File dirFile){
        this.dirProject = dirProject;
        this.dirFile = dirFile;
    }
    
    
    @Override
    public String getTitle() {
        return "New Fragment";
    }

    @Override
    public View getInflateView() {
        
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.dialog_create_fragment,null);
        edtNameFragment = rootView.findViewById(R.id.dialog_create_fragment_edt_name_fragment);
        edtNameLayout = rootView.findViewById(R.id.dialog_create_fragment_edt_name_layout);
        
        return rootView;
    }

    @Override
    public void onClickPositive() {
        
        if(isOk()){
            
            File fileFragment = new File(dirFile,edtNameFragment.getText().toString()+".java");
            File fileLayout = new File(dirProject,"app/src/main/res/layout/"+edtNameLayout.getText().toString()+".xml");
            
            //create file
            FileUtils.createOrExistsFile(fileFragment);
            FileUtils.createOrExistsFile(fileLayout);
            
            //copy template from assets
            ResourceUtils.copyFileFromAssets("templates/DemoFragment.txt",fileFragment.getAbsolutePath());
            ResourceUtils.copyFileFromAssets("templates/demo_layout.txt",fileLayout.getAbsolutePath());
            
            //search path package
            String[] pathPackage = fileFragment.getParent().split("/java.");
            
            //write file templates
            String textFileFragment = FileIOUtils.readFile2String(fileFragment);
            textFileFragment = textFileFragment.replace("$fragment_name$",edtNameFragment.getText().toString());
            textFileFragment = textFileFragment.replace("$layout_name$",edtNameLayout.getText().toString());
            textFileFragment = textFileFragment.replace("$package_name$",pathPackage[1].replace("/","."));
            
            FileIOUtils.writeFileFromString(fileFragment,textFileFragment);
            
            
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
        
        if(edtNameFragment.getText().toString().isEmpty()){
            edtNameFragment.setError("Error Name Fragment");
            edtNameFragment.requestFocus();
            
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
