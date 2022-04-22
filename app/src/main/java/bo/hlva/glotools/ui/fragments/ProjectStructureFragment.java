package bo.hlva.glotools.ui.fragments;

import bo.hlva.glotools.R;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import bo.hlva.glotools.ui.activities.ProjectManagerActivity;
import bo.hlva.glotools.ui.adapters.FileItemNodeAdapter;
import bo.hlva.glotools.utils.ProjectStateManager;
import bo.hlva.glotools.utils.ProjectUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import bo.hlva.glotools.ui.listeners.OnProjectChangeListener;
import bo.hlva.glotools.ui.dialogs.CreateFolderDialog;
import bo.hlva.glotools.ui.dialogs.CreateClassDialog;
import bo.hlva.glotools.ui.dialogs.DeleteFileDialog;
import bo.hlva.glotools.ui.dialogs.CreateFragmentDialog;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import bo.hlva.glotools.ui.dialogs.CreateLayoutDialog;
import android.content.Intent;
import bo.hlva.glotools.ui.activities.DrawableActivity;
import bo.hlva.glotools.ui.dialogs.CreateActivityDialog;

public class ProjectStructureFragment extends Fragment implements OnProjectChangeListener {

    //Constantes
    public static final String TAG = "ProjectStructureFragment";

    private File dirProject;

    //UI
    private ViewGroup containerNodes;
    private AndroidTreeView treeView;
    private FloatingActionButton mfab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = getLayoutInflater().inflate(R.layout.fragment_project_structure, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //recibir datos
        String path = getArguments().getString(ProjectManagerActivity.PATH_DIR_PROJECT);
        dirProject = new File(path);

        //findviews
        containerNodes = view.findViewById(R.id.fragment_project_structure_container_nodes);
        mfab = view.findViewById(R.id.fragment_project_structure_fab);

        //display
        onProjectChange();


        mfab.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {

                    AlertDialog.Builder adb = new AlertDialog.Builder(getContext());
                    adb.setTitle("Add Resources");

                    String[] items = {"Layout","Icon"};
                    adb.setItems(items, new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface p1, int position) {

                                switch (position) {
                                    case 0:
                                        File dirLayout = new File(dirProject,"app/src/main/res/layout");
                                        CreateLayoutDialog.getIntance(dirLayout,ProjectStructureFragment.this).show(getFragmentManager(), "la");
                                        break;
                                    case 1:
                                        startActivity(new Intent(getContext(), DrawableActivity.class));

                                }
                            }
                        });
                    adb.show();
                }
            });
    }
    

    @Override
    public void onDestroy() {
        super.onDestroy();

        //guardar estado 
        String state = treeView.getSaveState();
        if (state != null) {

            ProjectStateManager.saveStateTreeView(getContext(), state);
        }
    }


    public void showMenuItemNode(View anchor, final File file) {

        final PopupMenu menu = new PopupMenu(getContext(), anchor);
        menu.getMenuInflater().inflate(R.menu.menu_file_node, menu.getMenu());

        // descativar menus
        if (file.isFile()) {

            MenuItem itemNew = menu.getMenu().findItem(R.id.item_menu_file_node_new);
            itemNew.setVisible(false);

            /*  MenuItem item0 = menu.getMenu().findItem(R.id.item_menu_file_node_new_android_resource);
             item0.setVisible(false);
             MenuItem item1 = menu.getMenu().findItem(R.id.item_menu_file_node_new_folder);
             item1.setVisible(false);
             MenuItem item3 = menu.getMenu().findItem(R.id.item_menu_file_node_new_class);
             item3.setVisible(false);*/
        }
        if (file.isDirectory()) {

            if (ProjectUtils.isParentJava(file)) {
                MenuItem item0 = menu.getMenu().findItem(R.id.item_menu_file_node_new_android_resource);
                item0.setVisible(true);
                MenuItem item1 = menu.getMenu().findItem(R.id.item_menu_file_node_new_class);
                item1.setVisible(true);
            } else {
                MenuItem item0 = menu.getMenu().findItem(R.id.item_menu_file_node_new_android_resource);
                item0.setVisible(false);
                MenuItem item1 = menu.getMenu().findItem(R.id.item_menu_file_node_new_class);
                item1.setVisible(false);

            }
        }

        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){

                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.item_menu_file_node_new_folder:
                            CreateFolderDialog.getInstance(file, ProjectStructureFragment.this)
                                .show(getFragmentManager(), CreateFolderDialog.TAG);
                            return true;

                        case R.id.item_menu_file_node_new_class:
                            CreateClassDialog.getInstance(file, ProjectStructureFragment.this).
                                show(getFragmentManager(), ProjectStructureFragment.TAG);
                            return true;

                        case R.id.item_menu_file_node_delete:
                           
                            DeleteFileDialog.getIntance(file, ProjectStructureFragment.this).
                                show(getFragmentManager(), "delete");
                            return true;

                        case R.id.item_menu_file_node_new_fragment:
                            CreateFragmentDialog.getInstance(dirProject,file,ProjectStructureFragment.this).
                                show(getFragmentManager(), CreateFragmentDialog.TAG);
                                
                             return true;
                        
                        case R.id.item_menu_file_node_new_activity:
                            CreateActivityDialog.getInstance(dirProject,file,ProjectStructureFragment.this).
                            show(getFragmentManager(),CreateActivityDialog.TAG);
                    }

                    return false;
                }
            });

        menu.show();
    }


    // estructura del pryecto

    /**
     * crear structura del proyecto
     */

    @Override
    public void onProjectChange() {
        
        TreeNode root = TreeNode.root();

        GenerateProjectStructureTask task = new GenerateProjectStructureTask();
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, root);

    }

    //task
    class GenerateProjectStructureTask extends AsyncTask<TreeNode,String,String> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new ProgressDialog(getContext());
            dialog.setCancelable(false);
            dialog.setMessage("Loading...");
            dialog.show();
        }


        @Override
        protected String doInBackground(TreeNode[] treeNodes) {

            TreeNode root = treeNodes[0];

            TreeNode parent = new TreeNode(new FileItemNodeAdapter.FileItem(dirProject));
            parent.addChildren(getNodes(dirProject));
            root.addChild(parent);

            treeView = new AndroidTreeView(getContext(), root);
            treeView.setDefaultContainerStyle(R.style.TreeNodeStyle);
            treeView.setDefaultViewHolder(FileItemNodeAdapter.class);
            treeView.setDefaultNodeClickListener(onClickListener);
            treeView.setDefaultNodeLongClickListener(onLongClickListener);



            //return state
            String state = ProjectStateManager.getStateTreeView(getContext());
            if (state != null) {
                treeView.restoreState(state);
            }



            return "Completed";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            dialog.setMessage(result);
            dialog.hide();

            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            containerNodes.removeAllViews();
            containerNodes.addView(treeView.getView(), params);
        }


        /**
         * retuna lista de hijos
         */
        private ArrayList<TreeNode> getNodes(File dirProjectAndroid) {

            ArrayList<TreeNode> nodes = new ArrayList<>();

            if (dirProject != null && dirProject.exists()) {

                //organizar archivos A-Z
                ArrayList<File> files = new ArrayList<>();

                for (File file : dirProjectAndroid.listFiles()) {

                    files.add(file);
                }

                Collections.sort(files, new Comparator<File>(){

                        @Override
                        public int compare(File p1, File p2) {
                            return p1.getName().compareTo(p2.getName());
                        }
                    });

                //añadir hijos
                for (File file : files) {

                    if (file.isFile()) {
                        TreeNode node = new TreeNode(new FileItemNodeAdapter.FileItem(file));
                        nodes.add(node);
                    }
                    if (file.isDirectory()) {
                        TreeNode node = new TreeNode(new FileItemNodeAdapter.FileItem(file));
                        node.addChildren(getNodes(file));
                        nodes.add(node);
                    }

                }
            }

            return nodes;
        }

        //onclick node
        private TreeNode.TreeNodeClickListener onClickListener = new TreeNode.TreeNodeClickListener(){

            @Override
            public void onClick(TreeNode node, Object value) {

                FileItemNodeAdapter.FileItem item = (FileItemNodeAdapter.FileItem) value;
                if (item != null) {

                }

            }
        };

        //onlongclick node
        private TreeNode.TreeNodeLongClickListener onLongClickListener = new TreeNode.TreeNodeLongClickListener(){

            @Override
            public boolean onLongClick(TreeNode node, Object value) {

                FileItemNodeAdapter.FileItem item = (FileItemNodeAdapter.FileItem) value;
                if (item != null) {

                    showMenuItemNode(node.getViewHolder().getView(), item.getItemFile());
                }

                return false;
            }
        };

    }
}
