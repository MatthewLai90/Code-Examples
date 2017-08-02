

package P1Filters;

import FilterInterface.ImageFilter;
import FilterUtilities.SpatialFilterUtil;
import GUI.SpatialFilteringSelection;
import Utilities.RGBPixelArray;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mlai
 */
public class SpatialFilter extends ImageFilter {
    
// Performs the average smoothing with a uniform mask
    private int[][] flatSmoothing(int[][] image, int xFilterSize, int yFilterSize)
    {
// creates the mask
        int [][] filter = new int[xFilterSize][yFilterSize];
        int filterSum = 0;
        for (int i = 0; i < filter.length; i++) 
            for (int j = 0; j < filter[i].length; j++) 
            {
                filter[i][j] = 1;
                filterSum += 1;
            }
// correlates the mask with the image        
        int[][] returnImage = SpatialFilterUtil.correlation(image, filter);
        for (int i = 0; i < returnImage.length; i++) {
            for (int j = 0; j < returnImage[i].length; j++) {
                returnImage[i][j] = returnImage[i][j]/filterSum;
            }
        }
// returns the correlated image
        return returnImage;
    }
    
    private int[][] medianFilter(int[][] image, int xFilterSize, int yFilterSize)
    {
// creates the output image
        int [][] output = new int[image.length][image[0].length];
        int dx = xFilterSize/2;
        int dy = yFilterSize/2;
        for(int x = 0; x < image.length; x++)
            for(int y = 0; y < image[x].length; y++)
            {
                if(x - dx >= 0 && x + dx < image.length &&
                        y -dy >= 0 && y + dy < image[x].length)
                {
// places neighborhood values into window
                    int c = 0;
                    int [] window = new int[xFilterSize*yFilterSize];
                    for(int i = -dx; i <= dx; i++)
                        for(int j = -dy; j <= dy; j++)
                        {
                            window[c] = image[x+i][y+j];
                            c++;
                        }
// Sorts Window and places median in the target pixel location
                    int[] sortedWindow = FilterUtilities.QuicksortMedian.sort(window);
                    output[x][y] = sortedWindow[sortedWindow.length/2];
                }
                else output[x][y] = image[x][y];
            }
        return output;
    }
    
    private int[][] laplacianFilter(int[][] image, int xFilterSize, int yFilterSize)
    {
// calls the high-boost filter with A value of 1 and a negitivity value of 1
        return highBoostFilter(image, xFilterSize, yFilterSize, 1, 1);
    }
// highboost filter. Variable A modifies the algorithm, variable mod modifies the mask, either setting the
// center value to be positive or negative.
    private int[][] highBoostFilter(int[][] image, int xFilterSize, int yFilterSize, double A, int mod)
    {
// creates the filter mask
        int [][] filter = new int[xFilterSize][yFilterSize];
        int central = xFilterSize*yFilterSize - 1;
        for (int i = 0; i < filter.length; i++) 
            for (int j = 0; j < filter[i].length; j++) 
                filter[i][j] = 1 * mod;
        filter[xFilterSize/2][yFilterSize/2] = -central * mod;
// correlates the image with the mask
        int[][] correlatedImage = SpatialFilterUtil.correlation(image, filter);
        int[][] returnImage = new int[image.length][image[0].length];
// performs the high boost operations on the image.        
        for (int i = 0; i < returnImage.length; i++) 
            for (int j = 0; j < returnImage[i].length; j++) 
                returnImage[i][j] = (int)((double)image[i][j] * A) + (correlatedImage[i][j] * -1 * mod);
        
        return returnImage;
    }
// creates the GUI and calls the appropriate spatial filters
    @Override
    public RGBPixelArray filterImage(RGBPixelArray original) {
        SpatialFilteringSelection sFS = new SpatialFilteringSelection(null, true);
        sFS.setVisible(true);
        int x = sFS.getXSpinnerValue();
        int y = sFS.getYSpinnerValue();
        double A = sFS.getASpinnerValue();
        int selection = sFS.getSelection();
        
        boolean isGS = original.isGrayscaleImage();
        
        int[][] red = original.getRed();
        int[][] green = original.getGreen();
        int[][] blue = original.getBlue();
        int[][] gS = original.getGrayscaleValues();
        
        try {
        switch(selection) {
            case 0:
                if(isGS) return new RGBPixelArray(flatSmoothing(gS, x, y));
                else return new RGBPixelArray(flatSmoothing(red,x,y), flatSmoothing(green,x,y), flatSmoothing(blue,x,y));
                
            case 1:
                if(isGS) return new RGBPixelArray(medianFilter(gS, x, y));
                else return new RGBPixelArray(medianFilter(red,x,y), medianFilter(green,x,y), medianFilter(blue,x,y));
            case 2:
                if(isGS) return new RGBPixelArray(laplacianFilter(gS, x, y));
                else return new RGBPixelArray(laplacianFilter(red,x,y), laplacianFilter(green,x,y), laplacianFilter(blue,x,y));
            case 3:
                if(isGS) return new RGBPixelArray(highBoostFilter(gS, x, y, A, -1));
                else return new RGBPixelArray(highBoostFilter(red,x,y,A, -1), highBoostFilter(green,x,y,A, -1), highBoostFilter(blue,x,y,A, -1));
            default: break;
        }} catch (RGBPixelArray.RGBPixelArraySizeMismatchError ex) {
            System.out.println("RGB Mismatch. Shouldn't Happen Here.\n" + ex.getMessage());
            System.exit(1);
        } catch (Exception ex) {
            Logger.getLogger(SpatialFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String getFilterName() {
        return "Spatial Filtering";
    }

    @Override
    public String getFilterDecription() {
        return "Performs a specified spatial filter operation on the image.";
    }

    @Override
    public String getFilterCategory() {
        return "Project 1";
    }
}
