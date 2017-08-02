

package P1Filters;

import FilterInterface.ImageFilter;
import FilterUtilities.RGBObject;
import GUI.HistogramChart;
import GUI.SpinnerGet;
import ImageProcessor.HistogramGen;
import Utilities.RGBPixelArray;
import javax.swing.JOptionPane;

/**
 *  Histogram Equalization of the images. Allows for the user to select either local
 *  or global histogram calculations for pixel valuation.
 * 
 * @author Matthew Lai
 */
public class HistogramEqualization extends ImageFilter {

    @Override
    public RGBPixelArray filterImage(RGBPixelArray original) {
        int intoption = JOptionPane.showOptionDialog(new javax.swing.JFrame(), "Do you want to do a local or global histogram equalization?", 
                "Histogram Option", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
                        new Object[] {"Global","Local"}, "Global"); 
        if(intoption == 0) 
        {
            return new RGBPixelArray(equalizeGlobal(original.getGrayscaleValues()));
        }
        else if(intoption == 1) 
        {
            return new RGBPixelArray(equalizeLocal(original.getGrayscaleValues()));
        }
        else return null;
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//          Equalizes the images using local histograms
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////            
    public int[][] equalizeLocal(int[][] original)
    {
// GUI Elements        
        SpinnerGet spinn = new SpinnerGet();
        spinn.setVisible(true);
        int dim = spinn.getSpinnerValue();
        spinn.dispose();
// New Image
        int[][] newImage = new int[original.length][original[0].length];
        int dimdim = dim*dim;
// Calculating Bit depth
        int bitCount = HistogramGen.getBitCount(original);
        for(int i = 0; i < newImage.length; i++)
            for(int j = 0; j < newImage[i].length; j++)
            {
// Calculating the local histogram
                int[] hist = HistogramGen.generateLocalHist(original, i, j, dim, bitCount);
// Totalling the current histogram value with previous histogram entries
                double total = 0;
                int k = original[i][j];
                while(k >= 0) total += ((double)hist[k--]);
// New pixel value based on the local histogram cluster
                int value = (int)Math.round(total/dimdim * (hist.length));
// Writing the new pixel value to the array.
                newImage[i][j] = value;
            }
        return newImage;
        
    }
    
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//          Equalizes the images using global histograms.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////        
    public int[][] equalizeGlobal(int[][] original)
    {
// New Image
        int[][] newImage = new int[original.length][original[0].length];
        int MN = original.length * original[0].length;
// Calculate the global Histogram
        int[] histogram = HistogramGen.generateGlobalHist(original);
        new HistogramChart(histogram).setVisible(true);
        for(int i = 0; i < newImage.length; i++)
            for(int j = 0; j < newImage[i].length; j++)
            {
// Totalling the current histogram value with previous histogram entries
                double total = 0;
                int k = original[i][j];
                while(k >= 0) total += ((double)histogram[k--]);
// New pixel value based on the local histogram cluster
                int value = (int)Math.round(total/MN * (histogram.length - 1));
// Writing the new pixel value to the array.
                newImage[i][j] = value;
            }
        
        // NORMALIZE HERE!!!
        newImage = ImageProcessor.ImageUtilities.normalize(newImage, 8);
        
        new HistogramChart(HistogramGen.generateGlobalHist(newImage)).setVisible(true);
        return newImage;
    }

    @Override
    public String getFilterName() {
        return "Historgram Equalization";
    }

    @Override
    public String getFilterDecription() {
        return "Equalizes the image based on the image histogram.";
    }
    
    @Override
    public String getFilterCategory() {
        return "Project 1";
    }
    
}
