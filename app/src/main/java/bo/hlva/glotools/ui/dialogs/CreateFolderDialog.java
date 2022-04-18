package bo.hlva.glotools.ui.dialogs;

import bo.hlva.glotools.R;
import android.view.View;
import com.google.android.material.textfield.TextInputEditText;
import java.io.File;
import bo.hlva.glotools.ui.listeners.OnProjectChangeListener;
import com.blankj.utilcode.util.FileUtils;

public class CreateFolderDialog extends BaseDialogFragment {

   
    public static final String TAG = "CreateFolderDialog";
    private TextInputEditText edtNameFolder;
    private File dirRoot;
    
    private static CreateFolderDialog instance;
    
    public static CreateFolderDialog getInstance(File dirRoot,OnProjectChangeListener projectlistener){
        
        if(instance == null){
            instance = new CreateFolderDialog(dirRoot);
            instance.setOnProjectChangeListener(projectlistener);
        }
        
        return instance;
    }
    
    public CreateFolderDialog(File dirRoot){
        this.dirRoot = dirRoot;
    }
    
    @Override
    public String getTitle() {
        return "New Folder";
    }

    @Override
    public View getInflateView() {
       
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_create_folder,null);
        edtNameFolder = view.findViewById(R.id.dialog_create_folder_edt_name_folder);
        return view;
        
    }

    @Override
    public void onClickPositive() {
        
        if(edtNameFolder.getText().toString().isEmpty()){
            edtNameFolder.setError("Error Name Folder");
            edtNameFolder.requestFocus();
            return;
        }
        else{
            
            // crear carpeta
            File newFolder = new File(dirRoot,edtNameFolder.getText().toString());
            FileUtils.createOrExistsDir(newFolder);
            
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
}
