package bo.hlva.glotools.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import bo.hlva.glotools.R;
import java.io.File;
import java.util.ArrayList;

public class DrawableListAdapter extends RecyclerView.Adapter<DrawableListAdapter.ViewHolder> {

    private ArrayList<DrawableFileItem> listItems;
    
    public DrawableListAdapter(ArrayList<DrawableFileItem> listItems){
        
        this.listItems = listItems;
    }
    
    @Override
    public DrawableListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawable,parent);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DrawableListAdapter.ViewHolder holder, int position) {
        
        //set item
       // Drawable itemDrawable =
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
    
    class ViewHolder extends RecyclerView.ViewHolder{
        
        private ImageView itemIconDrawable;
        private TextView itemNameDrawable;
        
        public ViewHolder(View view){
            super(view);
            
            itemIconDrawable = view.findViewById(R.id.item_drawable_icon);
            itemNameDrawable = view.findViewById(R.id.item_drawable_name);
        }
    }
    
    class DrawableFileItem{
        
        private File itemFile;
        
        public DrawableFileItem(File file){
            this.itemFile = file;
        }
        
        public File getDrawableFileItem(){
            return this.itemFile;
        }
    }
}
