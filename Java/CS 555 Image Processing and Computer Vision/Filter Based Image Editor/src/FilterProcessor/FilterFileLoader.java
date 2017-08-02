/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package FilterProcessor;

import FilterInterface.ImageFilter;
import GUI.GUIUtilities;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Matthew Lai
 */
public class FilterFileLoader {
    
    public static void main(String[] args) {
//        (new FilterFileLoader()).scanForClass();
        new FilterFileLoader();
    }
    
    File filtersDir;
    public FilterFileLoader()
    {
        getFilterDir();
        loadFilterDirClasses();
    }
    
    private void scanForClass()
    {
        List<Class<?>> classes = ClassFinder.find("Filters");
        for(Class c : classes)
        {
            try {
                Object o = c.newInstance();
                ImageFilter iF = (ImageFilter) o;
                System.out.println(iF.getFilterDecription());
            } catch (InstantiationException ex) { continue; } 
              catch (IllegalAccessException ex) { Logger.getLogger(FilterFileLoader.class.getName()).log(Level.SEVERE, null, ex); }
        }
    }
    
    private void installFile(File sourceClass)
    {
        try {
            Files.copy(sourceClass.toPath(), new File(filtersDir + "/Filters").toPath());
        } catch (IOException ex) { GUIUtilities.errorMessage("The filter could not be installed.", false); }
    }
    
    private void loadFile(File sourceFile)
    {
        
    }
    
    private void loadFilterDirClasses()
    {
        try {
            URL url = filtersDir.toURI().toURL();
            URL[] urls = new URL[]{url};
            ClassLoader cl = new URLClassLoader(urls);
            Object o = cl.loadClass("Filters.ManipFilter").newInstance();
            ImageFilter iF = (ImageFilter)o;
            System.out.println(iF.getFilterDecription());
            // Scan appdata directory. Load Classes
            
        } catch (MalformedURLException ex) { GUIUtilities.errorMessage("Unknown Error. This should not happen.", true); } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(FilterFileLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void getFilterDir()
    {
        String workingDir = "";
        String OS = (System.getProperty("os.name")).toUpperCase();
        
        if(OS.contains("WIN")) { workingDir = System.getenv("AppData"); }
        
        else if(OS.contains("LINUX")) { workingDir = System.getProperty("user.home"); }
        
        else if(OS.contains("MAC")) { workingDir = System.getProperty("user.home") + "/Library/Application Support"; }
        
        else 
        {
             GUIUtilities.errorMessage("Your OS is not supported.", true);
        }
        
        filtersDir = new File(workingDir + "/ImageFilterEditor");
        filtersDir.mkdirs();
        
        if(!(filtersDir.isDirectory())) 
        {
             GUIUtilities.errorMessage("Your OS is not supported.", true);
        }
        
    }
    
}
