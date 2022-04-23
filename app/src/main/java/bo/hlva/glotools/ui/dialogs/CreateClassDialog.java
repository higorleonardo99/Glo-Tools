package bo.hlva.glotools.ui.dialogs;

import android.view.View;
import bo.hlva.glotools.ui.listeners.OnProjectChangeListener;
import bo.hlva.glotools.R;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ResourceUtils;
import com.google.android.material.textfield.TextInputEditText;
import java.io.File;

public class CreateClassDialog extends BaseDialogFragment {

    public static final String TAG = "CreateClassDialog";
    
    private TextInputEditText edtNameClass;
    private File dirRoot;
    private static CreateClassDialog instance;
    
    public static CreateClassDialog getInstance(File dirRoot,OnProjectChangeListener projectListener){
        
        if(instance == null){
            instance = new CreateClassDialog(dirRoot);
            instance.setOnProjectChangeListener(projectListener);
        }
        
        return instance;
    }
    
    public CreateClassDialog(File dirRoot){
        this.dirRoot = dirRoot;
    }
    
    @Override
    public String getTitle() {
        return "New Class";
    }

    @Override
    public View getInflateView() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_create_class,null);
        edtNameClass = view.findViewById(R.id.dialog_create_class_edt_name_class);
        
        return view;
    }

    @Override
    public void onClickPositive() {
        
        if(edtNameClass.getText().toString().isEmpty()){
            edtNameClass.setError("Error Name Class");
            return;
        }
        else{
            
            //crear clase
            File newClass = new File(dirRoot,edtNameClass.getText().toString()+".java");
            FileUtils.createOrExistsFile(newClass);
            
            //modificar clase
            replaceContent(newClass);
            
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
    
    
    private void replaceContent(File file){
        
        //copiar template
        ResourceUtils.copyFileFromAssets("templates/DemoClass.txt",file.getAbsolutePath());
        
       //separar paquete
       String[] packageClass = file.getParent().split("java/");
       
       String content = FileIOUtils.readFile2String(file);
       content = content.replace("$package$",packageClass[1].replace("/","."));
       content = content.replace("$name_class$",edtNameClass.getText().toString());
       
       FileIOUtils.writeFileFromString(file,content);
       
    }
}
