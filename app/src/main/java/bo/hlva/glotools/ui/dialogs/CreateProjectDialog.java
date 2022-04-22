package bo.hlva.glotools.ui.dialogs;

import bo.hlva.glotools.R;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import bo.hlva.glotools.ui.tasks.CreateTemplateAndroidxTask;
import com.blankj.utilcode.util.FileUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import java.io.File;
import com.google.android.material.textfield.TextInputLayout;
import android.text.TextWatcher;
import android.text.Editable;
import bo.hlva.glotools.utils.ProjectStateManager;
import android.widget.RadioButton;
import bo.hlva.glotools.ui.tasks.CreateTemplateAppCompatTask;

public class CreateProjectDialog extends DialogFragment {
    
    public static final String TAG = "CreateProjectDialog";
    
    private TextInputEditText edtNameProject,edtNamePackage;
    private TextInputLayout textLayoutProject,textLayoutPackage;
    
    private RadioButton rbAndroidX,rbAppCompat;
    
    private OnCreateProjectListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
        builder.setTitle("New Project");
        
        //buttons
        builder.setPositiveButton("create",null);
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface p1, int p2) {
                    dismiss();
                }
            });
            
        //inflate view
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_create_project,null);
        edtNameProject = view.findViewById(R.id.dialog_create_project_edt_name_project);
        edtNamePackage = view.findViewById(R.id.dialog_create_project_edt_name_package);
        
        textLayoutProject = view.findViewById(R.id.dialog_create_project_layout_name_project);
        textLayoutPackage = view.findViewById(R.id.dialog_create_project_layout_name_package);
        
        rbAndroidX = view.findViewById(R.id.dialog_create_project_btn_androidx);
        rbAppCompat = view.findViewById(R.id.dialog_create_project_btn_appcompat);
        
        builder.setView(view);
            
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        
        AlertDialog dialog = (AlertDialog) getDialog();
        
        if(dialog != null){
            
            Button btnPositive = dialog.getButton(dialog.BUTTON_POSITIVE);
            btnPositive.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        
                        if(isOk()){
                            
                            //directorio del proyecto
                            File dirProject;
                         /*  String pathProjects = ProjectStateManager.getPathProjects(getContext());
                            
                            if(pathProjects != null){
                                dirProject   = new File(pathProjects,edtNameProject.getText().toString());
                            }
                            */
                            dirProject = new File(getRootDir(),edtNameProject.getText().toString());
                            
                            FileUtils.createOrExistsDir(dirProject);
                            
                            //androidx or appcompat
                            if(rbAndroidX.isChecked()){
                                CreateTemplateAndroidxTask task = new CreateTemplateAndroidxTask(getContext());
                                task.execute(dirProject.getAbsolutePath(),edtNameProject.getText().toString(),edtNamePackage.getText().toString());
                                
                            }
                            else if(rbAppCompat.isChecked()){
                                CreateTemplateAppCompatTask task = new CreateTemplateAppCompatTask(getContext());
                                task.execute(dirProject.getAbsolutePath(),edtNameProject.getText().toString(),edtNamePackage.getText().toString());
                            }
                            
                            //pass data
                            listener.onCreateProject(dirProject);
                            dismiss();
                        }
                        
                    }
                });
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        
        if(context != null){
            listener = (OnCreateProjectListener) context;
        }
    }
    
    
    

    /**
     * retorna directorio donde se guardaran los proyectos
     */
    private File getRootDir(){

        File root = new File(Environment.getExternalStorageDirectory(),"AppProjects");
        FileUtils.createOrExistsDir(root);

        return root;
    }
    
    /**
    * verifica entradas de textos
    */
    private boolean isOk(){
        //buscar nombre de proyecto existente
        File file = new File(getRootDir(),edtNameProject.getText().toString());
        
        //nombre del proyecto
        if(edtNameProject.getText().toString().isEmpty()){
            textLayoutProject.setError("Name Project Empty ");
            textLayoutProject.requestFocus();
            
            return false;
        }
        
        else if(file.exists()){
            textLayoutProject.setError("Name Project Exits");
            textLayoutProject.requestFocus();

            return false;
        }
        
        else{
            textLayoutProject.setError(null);
            textLayoutProject.requestFocus();
        }
        
        
        //Nombre del Paquete
        if(edtNamePackage.getText().toString().isEmpty()){
            textLayoutPackage.setError("Name Package Empty");
            textLayoutPackage.requestFocus();
            return false;
        }
        else if(!edtNamePackage.getText().toString().contains(".")){
            textLayoutPackage.setError("Name Package Not Contains '.'");
            textLayoutPackage.requestFocus();
            return false;
        }
        else if(edtNamePackage.getText().toString().endsWith(".")){
            textLayoutPackage.setError("Name Package Not End '.'");
            textLayoutPackage.requestFocus();
            return false;
        }
        else
        {
            textLayoutPackage.setError(null);
            textLayoutPackage.requestFocus();
        }
        
     
        return true;
    }
    
    public interface OnCreateProjectListener{
        
        void onCreateProject(File dirProject);
    }
}
