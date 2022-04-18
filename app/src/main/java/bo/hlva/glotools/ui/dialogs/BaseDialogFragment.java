package bo.hlva.glotools.ui.dialogs;

import androidx.fragment.app.DialogFragment;
import android.app.Dialog;
import android.os.Bundle;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.content.DialogInterface;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import android.widget.Button;
import bo.hlva.glotools.ui.listeners.OnProjectChangeListener;
import android.content.Context;

public abstract class BaseDialogFragment extends DialogFragment {

    public OnProjectChangeListener listener;
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
        builder.setTitle(getTitle());
        
        builder.setPositiveButton(getTextPositiveButton(),null);
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface p1, int p2) {
                    
                    dismiss();
                }
            });
            
        if(getMessage() != null){
            builder.setMessage(getMessage());
        }
            
        //inflate view
        if(getInflateView() != null){
            builder.setView(getInflateView());
        }
        
        
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
                        
                        onClickPositive();
                      
                    }
                });
        }
    }

    public void setOnProjectChangeListener(OnProjectChangeListener listener){
        this.listener = listener;
    }
    
    
    public abstract String getTitle();
    
    public abstract View getInflateView();
    
    public abstract void onClickPositive();
    
    public abstract String getMessage();
    
    public abstract String getTextPositiveButton();
    
}
