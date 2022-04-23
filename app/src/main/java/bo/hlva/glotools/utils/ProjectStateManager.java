package bo.hlva.glotools.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;

/**
* clase controla estado del proyecto
* @author Higor Leonardo Vargas Arrazola
* @version 15.02.22
*/
public class ProjectStateManager {
    
    // Constantes
    public static final String PREFERENCES = "PREFERENCES";
    public static final String ANDROID_PROJECT = "ANDROID_PROJECT";
    public static final String STATE_TREE_VIEW = "STATE_TREE_VIEW";
    public static final String PATH_PROJECTS = "PATH_PROJECTS";
    
    /**
    * guarda proyecto en preferencias
    * @param context, contexto de la aplicacion
    * @param dirProject, direccion del proyecto
    */
    public static void saveProject(Context context,@Nullable String dirProject){
        
        SharedPreferences prefereces = PreferenceManager.getDefaultSharedPreferences(context);
        prefereces.edit().putString(ANDROID_PROJECT,dirProject).apply();
        
    }
    
    /**
    * recupera proyecto de preferencias
    * @param context, context de la aplicacion
    * @return direccion de proyecto
    */
    @Nullable
    public static String getLastProject(Context context){
        
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String dirProject = preferences.getString(ANDROID_PROJECT,null);
        
        return dirProject;
    }
    
    /**
    * guarda estado de la estructura del proyecto
    * @param context, contexto de la aplicacion
    * @param state, estado a guardar
    */
    public static void saveStateTreeView(Context context,@Nullable String state){
        
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(STATE_TREE_VIEW,state).apply();
    }
    
    
    /**
    * retorna estado de la estructura del proyecto
    * @param context, contexto de la aplicacion
    * @return estado de estructura
    */
    @Nullable
    public static String getStateTreeView(Context context){
        
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String state = preferences.getString(STATE_TREE_VIEW,null);
        
        return state;
    }
    

    /**
     * guarda directorio de proyectos en preferencias
     * @param context, contexto de la aplicacion
     * @param dirPath, ruta de proyectos
     */
    public static void savePathProjects(Context context,@Nullable String dirPathProjects){

        SharedPreferences prefereces = PreferenceManager.getDefaultSharedPreferences(context);
        prefereces.edit().putString(PATH_PROJECTS,dirPathProjects).apply();

    }
    
    /**
     * recupera ruta de proyectos de preferencias
     * @param context, contexto de la aplicacion
     * @return ruta de proyectos
     */
    @Nullable
    public static String getPathProjects(Context context){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String dirProject = preferences.getString(PATH_PROJECTS,null);

        return dirProject;
    }
}
