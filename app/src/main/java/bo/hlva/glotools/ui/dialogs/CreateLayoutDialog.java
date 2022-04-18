package bo.hlva.glotools.ui.dialogs;

import android.view.View;
import bo.hlva.glotools.R;
import java.io.File;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ResourceUtils;
import bo.hlva.glotools.ui.listeners.OnProjectChangeListener;

public class CreateLayoutDialog extends BaseDialogFragment {

    
    private TextInputEditText edtNameLayout;
    private TextInputLayout textLayout;
    private static CreateLayoutDialog instance;
    private File dirLayout;
    
    
    public static CreateLayoutDialog getIntance(File dirLayout,OnProjectChangeListener projectlistener){
        
        if(instance == null){
            instance = new CreateLayoutDialog(dirLayout);
            instance.setOnProjectChangeListener(projectlistener);
        }
        
        return instance;
    }
    
    public CreateLayoutDialog(File dirLayout){
        this.dirLayout = dirLayout;
    }
    

    @Override
    public String getTitle() {
        return "New Layout";
    }

    @Override
    public View getInflateView() {
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.dialog_create_layout,null);
        edtNameLayout = rootView.findViewById(R.id.dialog_create_layout_edt_name_layout);
        textLayout = rootView.findViewById(R.id.dialog_create_layout_text_input_name_layout);
        return rootView;
    }

    @Override
    public void onClickPositive(){
        
        File newLayout = new File(dirLayout,edtNameLayout.getText().toString()+".xml");
        
        if(newLayout.exists()){
            
            textLayout.setError("Error Layout Exits");
            textLayout.requestFocus();
            
            
        }
        else if(edtNameLayout.getText().toString().isEmpty()){
            
            textLayout.setError("Error Name Layout");
            textLayout.requestFocus();
        }
        else{
            FileUtils.createOrExistsFile(newLayout);

            //copy data
            ResourceUtils.copyFileFromAssets("templates/demo_layout.txt",newLayout.getAbsolutePath());
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
