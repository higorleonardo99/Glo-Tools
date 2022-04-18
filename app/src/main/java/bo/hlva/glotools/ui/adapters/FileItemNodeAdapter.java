package bo.hlva.glotools.ui.adapters;

import java.io.File;
import com.unnamed.b.atv.model.TreeNode;
import android.view.View;
import android.content.Context;
import android.view.LayoutInflater;
import bo.hlva.glotools.R;
import android.widget.ImageView;
import android.widget.TextView;
import com.blankj.utilcode.util.FileUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/*
* clase adaptador de nodos para estructura del proyecto
* @author Higor Leonardo Vargas Arrazola
* @version 15.02.22
*/
public class FileItemNodeAdapter extends TreeNode.BaseNodeViewHolder<FileItemNodeAdapter.FileItem> {

    private ImageView itemArrow,itemIcon;
    private TextView itemTitle;
    private boolean isLeaf = false;
    
    public FileItemNodeAdapter(Context context){
        super(context);
    }
    
    
    @Override
    public View createNodeView(TreeNode node, FileItemNodeAdapter.FileItem value) {
        
        //inflate view
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_file_node,null);
        
        itemArrow = rootView.findViewById(R.id.item_file_node_arrow);
        itemIcon = rootView.findViewById(R.id.item_file_node_icon);
        itemTitle = rootView.findViewById(R.id.item_file_node_title);
        
        //set values
        isLeaf = node.isLeaf();
        if (node.isLeaf()) {
            itemArrow.setVisibility(View.INVISIBLE);
        }
        
        File file = value.getItemFile();
        
        itemTitle.setText(file.getName());
        setIcon(file);
        
        
        return rootView;
    }

    @Override
    public void toggle(boolean active) {
        
        if(!isLeaf){
            itemArrow.setImageResource(active ? R.drawable.ic_arrow_down : R.drawable.ic_arrow_right);
        }
        
    }

   private void setIcon(File file){
       
      
       if(file.isFile()){
           
           itemIcon.setImageResource(R.drawable.ic_file_outline);
           
           if(file.getName().endsWith(".java")){
               itemIcon.setImageResource(R.mipmap.file_type_java);
           }
           if(file.getName().endsWith(".xml")){
               itemIcon.setImageResource(R.mipmap.file_type_xml);
           }
           if(file.getName().endsWith(".gradle")){
               itemIcon.setImageResource(R.mipmap.file_type_gradle);
           }
          
       }
       if(file.isDirectory()){
           itemIcon.setImageResource(R.drawable.ic_folder_outline);
       }
       
       if(FileUtils.getFileExtension(file).equals("png") || FileUtils.getFileExtension(file).equals("jgpe")){

           Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
           itemIcon.setImageBitmap(bitmap);
       }
       
   }
    
    
    public static class FileItem{
        
        private File itemFile;
        
        public FileItem(File file){
            this.itemFile = file;
        }
        
        public File getItemFile(){
            return itemFile;
        }
    }
}
