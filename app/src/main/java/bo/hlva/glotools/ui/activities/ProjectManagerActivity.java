package bo.hlva.glotools.ui.activities;

import bo.hlva.glotools.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import bo.hlva.glotools.ui.fragments.ProjectStructureFragment;
import java.io.File;
import com.blankj.utilcode.util.BarUtils;
import android.graphics.Color;
import bo.hlva.glotools.utils.ProjectStateManager;

public class ProjectManagerActivity extends AppCompatActivity {
    
    //Constantes
    public static final String PATH_DIR_PROJECT = "PATH_DIR_PROJECT";
    
    //UI
    private Toolbar mToolbar;
    private ProjectStructureFragment projectStructuteFragment;
    
    
    //Project
    private File dirProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_manager);
       
        
        //recibir datos
        if(getIntent() != null){
            String path = getIntent().getStringExtra(MainActivity.PATH_PROJECT_MAIN);
            dirProject = new File(path);
        }
        
        setup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pm,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        switch(item.getItemId()){
            case R.id.item_menu_pm_settings:
                startActivity(new Intent(this,SettingsActivity.class));
            case R.id.item_menu_pm_refresh_project:
                if(projectStructuteFragment != null){
                    projectStructuteFragment.onProjectChange();
                }
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    

    private void setup() {
        //ActionBar
        mToolbar = findViewById(R.id.main_toolbar);
        
        setSupportActionBar(mToolbar);
        setupProjectStrucure();
    }
    
    private void setupProjectStrucure(){
        
        projectStructuteFragment = new ProjectStructureFragment();
        
        //enviar datos a fragment
        Bundle args = new Bundle();
        args.putString(PATH_DIR_PROJECT,dirProject.getAbsolutePath());
        projectStructuteFragment.setArguments(args);
        
        
        loadFragment(projectStructuteFragment);
        
        
    }
    
    private void loadFragment(Fragment fragment){
        
        getSupportFragmentManager().
        beginTransaction().replace(R.id.main_container_fragment,fragment).commit();
    }
    
    private void closeProject(){
        
        //reset app
        ProjectStateManager.saveProject(getApplicationContext(),null);
        ProjectStateManager.saveStateTreeView(getApplicationContext(),"");
        
    }
}
