package bo.hlva.glotools.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bo.hlva.glotools.data.model.ItemProject;
import bo.hlva.glotools.ui.activities.MainActivity;
import bo.hlva.glotools.ui.activities.ProjectManagerActivity;
import bo.hlva.glotools.ui.adapters.ListProjectsAdapter;
import bo.hlva.glotools.ui.listeners.ItemRecyclerViewListener;
import bo.hlva.glotools.utils.ProjectStateManager;
import bo.hlva.glotools.R;
import java.io.File;
import java.util.ArrayList;

public class ListProjectsFragment extends Fragment implements ItemRecyclerViewListener {
    
    private RecyclerView mRecyclerView;
    private ListProjectsAdapter mAdapter;
    
   
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_projects,container,false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        setup(view);
    }
    
    
    
    private void setup(View view){
        
        //recycler and adapter
        mRecyclerView = view.findViewById(R.id.fragment_list_projects_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        mAdapter = new ListProjectsAdapter(getLisProjects());
        mRecyclerView.setAdapter(mAdapter);
        
        mAdapter.setItemRecyclerViewListener(this);
        
        
        
    }
    
    @Override
    public void onClickItem(View view, int position) {
        
        File item = getLisProjects().get(position).getItemProject();
        
        //save data
        ProjectStateManager.saveProject(getContext(),item.getAbsolutePath());
        passActivity(item.getAbsolutePath());
        
    }
    
    private void passActivity(String dirProject){

        Intent intent = new Intent(getActivity(),ProjectManagerActivity.class);
        intent.putExtra(MainActivity.PATH_PROJECT_MAIN,dirProject);

        startActivity(intent);
        getActivity().finish();
    }
    
    private ArrayList<ItemProject> getLisProjects(){
        
        ArrayList<ItemProject> projects = new ArrayList<>();
        
      //  File delfautDirProjects = new File(ProjectStateManager.getPathProjects(getContext()));
        File dirProjects = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"AppProjects");
        
     /*   if(delfautDirProjects.exists()){
            for(File project : delfautDirProjects.listFiles()){

                if(project.isDirectory()){
                    projects.add(new ItemProject(project));
                }
            }
        }*/
        if(dirProjects.exists()){
            
            for(File project : dirProjects.listFiles()){
                
                if(project.isDirectory()){
                    projects.add(new ItemProject(project));
                }
            }
        }
        
        return projects;
    }
    
    
}
