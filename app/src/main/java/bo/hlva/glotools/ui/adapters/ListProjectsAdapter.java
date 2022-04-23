package bo.hlva.glotools.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import bo.hlva.glotools.data.model.ItemProject;
import bo.hlva.glotools.ui.listeners.ItemRecyclerViewListener;
import bo.hlva.glotools.R;
import java.util.ArrayList;

public class ListProjectsAdapter extends RecyclerView.Adapter<ListProjectsAdapter.ViewHolder> {

    private ArrayList<ItemProject> listProjects;
    private ItemRecyclerViewListener listener;
    
    
    public ListProjectsAdapter(ArrayList<ItemProject> listItems){
        this.listProjects = listItems;
    }
    
    public void setItemRecyclerViewListener(ItemRecyclerViewListener listener){
        this.listener = listener;
    }
    
    
    @Override
    public ListProjectsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_project,parent,false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ListProjectsAdapter.ViewHolder holder, int position) {
        
        ItemProject project = listProjects.get(position);
        
        //set values
        holder.tvNameProject.setText(project.getItemProject().getName());
       // holder.tvNamePackage.setText(project.namePackage());
    }

    @Override
    public int getItemCount() {
        return listProjects.size();
    }

    
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        
        private TextView tvNameProject,tvNamePackage;
        
        public ViewHolder(View view){
            super(view);
            
            view.setOnClickListener(this);
            
            tvNameProject = view.findViewById(R.id.item_list_project_tv_name_project);
            tvNamePackage = view.findViewById(R.id.item_list_project_tv_name_package);
        }
        
        @Override
        public void onClick(View view)
        {
            listener.onClickItem(view,getAdapterPosition());
        }
        
        
    }
    
   
}
