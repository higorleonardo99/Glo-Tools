package bo.hlva.glotools.ui.dialogs;

import android.view.View;
import bo.hlva.glotools.ui.listeners.OnProjectChangeListener;
import bo.hlva.glotools.R;
import com.blankj.utilcode.util.FileUtils;
import java.io.File;

public class DeleteFileDialog extends BaseDialogFragment {

    
    private File file;
    
    private static DeleteFileDialog instance;
    
    public static DeleteFileDialog getIntance(File file,OnProjectChangeListener projectlistener){
        
        if(instance == null){
            instance = new DeleteFileDialog(file);
            instance.setOnProjectChangeListener(projectlistener);
        }
        
        return instance;
    }
    
    public DeleteFileDialog(File file){
        this.file = file;
    }
    

    @Override
    public String getTitle() {
        return "Delete File";
    }

    @Override
    public View getInflateView() {
        return null;
    }

    @Override
    public void onClickPositive() {
        
        FileUtils.delete(file);
        listener.onProjectChange();
        dismiss();
    }

    @Override
    public String getMessage() {
        return "Delete " + file.getName();
    }

    @Override
    public String getTextPositiveButton() {
        return "delete";
    }
    
    
}
