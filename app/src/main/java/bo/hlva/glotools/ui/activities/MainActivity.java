package bo.hlva.glotools.ui.activities;

import bo.hlva.glotools.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import bo.hlva.glotools.ui.dialogs.CreateProjectDialog;
import bo.hlva.glotools.utils.ProjectStateManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.File;
import bo.hlva.glotools.ui.fragments.ListProjectsFragment;

public class MainActivity extends BaseActivity implements CreateProjectDialog.OnCreateProjectListener {
    
    public static final String PATH_PROJECT_MAIN = "PATH_PROJECT_MAIN";
    
    private Toolbar mToolbar;
    private FloatingActionButton mfab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
       
        
        //verificar permisos de almacenamiento
        
        //verificar proyecto abierto
        if(ProjectStateManager.getLastProject(getApplicationContext()) != null){
            
            File dirProject = new File(ProjectStateManager.getLastProject(getApplicationContext()));
            
            if(dirProject.exists()){
                passActivity(dirProject.getAbsolutePath());
            }
        }
        
        
        setup();
    }
    
    private void setup(){
        //ActionBar
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        
        //load fragment
        getSupportFragmentManager().
        beginTransaction().replace(R.id.activity_main_container_fragment,new ListProjectsFragment(),"list").commit();
        
        
        //Floating action button
        mfab = findViewById(R.id.main_fab);
        mfab.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    
                    CreateProjectDialog dialog = new CreateProjectDialog();
                    dialog.show(getSupportFragmentManager(),CreateProjectDialog.TAG);
                }
            });
    }
    
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        switch(item.getItemId()){
            case R.id.item_menu_main_about:
                showAboutDialog();
                return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    
    private void showAboutDialog(){
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("About");
        builder.setMessage("Glo Tools helper for aide IDE \nby @HL Dev\n@email hlvargasarrazola@gmail.com");
        builder.show();
    }
    
    @Override
    public void onCreateProject(File dirProject)
    {
        ProjectStateManager.saveProject(getApplicationContext(),dirProject.getAbsolutePath());
        
        //ToastUtils.showShort(dirProject.getAbsolutePath());
        passActivity(dirProject.getAbsolutePath());
    }
    
    private void passActivity(String dirProject){
        
        Intent intent = new Intent(this,ProjectManagerActivity.class);
        intent.putExtra(PATH_PROJECT_MAIN,dirProject);

        startActivity(intent);
        finish();
    }
    
}
