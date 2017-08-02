/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package FilterProcessor;

import FilterInterface.ImageFilter;
import GUI.GUIUtilities;
import Utilities.ImageResult;
import Utilities.RGBPixelArray;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Load List from file. Checks if classes exist and adds them to current 
 * available Filters.
 * 
 * @author Matthew Lai
 */
public class FilterProcessor {
    /** Possible Implementations
     *      - Read File in Filters folder & attempt to load them
     *      - Or Don't bother and require a file load action for all filters
     */
    final String setFilterArray[] = { "SegmentationProject.ColorEdgeExtraction", "SegmentationProject.SRG", 
        "P1Filters.HistogramEqualization", "P1Filters.SpatialFilter", "P1Filters.FourierPassFilters","P2Filters.Project2",
                                        "UsefulFilters.ImageGrayRes", "UsefulFilters.ImageScale", "UsefulFilters.TransformationFilter", "UsefulFilters.ManipFilter",
                                        "UsefulFilters.NoiseReductionFilters","UsefulFilters.NoiseFilter"};
    
    ArrayList<ImageFilter> filterList;
    HashSet<String> filterSet;
    public FilterProcessor() 
    {
        filterList = new ArrayList<>();
        filterSet = new HashSet<>();
//        ClassLoader cL = FilterProcessor.class.getClassLoader();
        for(String filterName : setFilterArray) loadClass(filterName);
//        List<Class<?>> list = ClassFinder.find("Filters");
//        for(Class<?> aClass : list) loadClass(aClass.getName());
//        for()
        
    }
    
    private void loadClass(String filterName)
    {
        ClassLoader cL = FilterProcessor.class.getClassLoader();
        try {
            Class aClass = cL.loadClass(filterName);
            Object o = aClass.newInstance();
            ImageFilter iF = (ImageFilter) o;
            if(iF instanceof ImageFilter && !filterSet.contains(iF.getFilterName()))
            {
                filterList.add(iF);
                filterSet.add(iF.getFilterName());
            }
            else if(iF instanceof ImageFilter && filterSet.contains(iF.getFilterName()))
            {
                System.out.println("The Filter you are attempting to add is already in the list.");
                GUIUtilities.errorMessage("The Filter you are attempting to add is already in the list.", false);
            }
            else if(!(iF instanceof ImageFilter))
            {
                System.out.println("The Filter you are attempting to add is not an ImageFilter.");
                GUIUtilities.errorMessage("The Filter you are attempting to add is not an ImageFilter.", false);
            }

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) 
          { 
             Logger.getLogger(FilterProcessor.class.getName()).log(Level.SEVERE, null, ex); 
          }
    }
    
    public String[] getFilterNameList()
    {
        String[] retA = new String[filterList.size()];
        int i = 0;
        for(ImageFilter filter : filterList)
        {
            retA[i++] = filter.getFilterName();
        }
        return retA;
    }
    
    public String[] getFilterDescriptionList()
    {
        String[] retA = new String[filterList.size()];
        int i = 0;
        for(ImageFilter filter : filterList)
        {
            retA[i++] = filter.getFilterDecription();
        }
        return retA;
    }
    
    public static void main(String[] args) {
        new FilterProcessor();
//        ClassLoader cL = FilterProcessor.class.getClassLoader();
//        try {
//            Class aClass = cL.loadClass("Filters.TestFilter");
//            Object o = aClass.newInstance();
//            ImageFilter iF = (ImageFilter) o;
//            System.out.println(iF.getFilterName());
//            
//        } catch(ClassNotFoundException e) {} catch (InstantiationException ex) {
//            Logger.getLogger(FilterProcessor.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            Logger.getLogger(FilterProcessor.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
    public ImageFilter getImageClass(String filterName)
    {
        String[] filterNameList = getFilterNameList();
        for(int i = 0; i < filterNameList.length; i++) 
        {
            if(filterNameList[i].equals(filterName)) return filterList.get(i);
        }
        return null;
    }
    
    public RGBPixelArray runFilter(String filterName, RGBPixelArray originalImage)
    {
        return getImageClass(filterName).filterImage(originalImage);
    }
    
    public String getFilterCategory(String filterName)
    {
        return getImageClass(filterName).getFilterCategory();
    }
    
    public void loadJavaFile(File f)
    {
        
    }
    
    public void loadJavaClass(File c)
    {
        
    }
    
}
