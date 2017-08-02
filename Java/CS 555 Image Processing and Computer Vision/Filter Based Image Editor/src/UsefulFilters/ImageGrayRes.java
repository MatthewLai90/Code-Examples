

package UsefulFilters;

import FilterInterface.ImageFilter;
import FilterUtilities.RGBObject;
import GUI.GUIUtilities;
import Utilities.RGBPixelArray;
import javax.swing.JFrame;

/**
 *  Program to vary the image's Gray Resolution. Displays each image after a 
 *  less significant digit has been removed;
 * 
 * @author Matthew Lai
 */
public class ImageGrayRes extends ImageFilter{

    @Override
    public RGBPixelArray filterImage(RGBPixelArray original) {
        
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//      Bins the larger K gray resolutions to smaller ones and displays them.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////   
//  Loops 7 times, each time lowering the grayscale resolution
        for(int i = 1; i <= 7; i++)
        {
            int[][] newImage = new int[original.getWidth()][original.getLength()];
            for (int j = 0; j < newImage.length; j++) {
                for (int k = 0; k < newImage[0].length; k++) {
// Calculates the current value of the pixel
                    int value = original.getGrayscaleValues()[j][k];
// Calculates the new value of the pixel by performing a logical and with the appropriate byte value.
                    int newValue = value & (int) (Math.pow(2, 8) - Math.pow(2, i));
// Writes the pixel value to the image array to be returned.
                    newImage[j][k] = new RGBObject(newValue,newValue,newValue).getIntRGB();
                }
            }
            newImage = ImageProcessor.ImageUtilities.normalize(newImage, 8);
// Displays each image as the grayscale resolution is lowered.
            JFrame newFrame = new JFrame();
            newFrame.add(GUIUtilities.getScrollWindow(GUIUtilities.getImagePanel(new RGBPixelArray(newImage))));
            newFrame.setTitle("Grayscale Resolution k=" + (8-i));
            newFrame.setSize(1000, 1000);
            newFrame.setVisible(true);
        }
        
        return null;
    }

    @Override
    public String getFilterName() {
        return "Gray Resolution Filter";
    }

    @Override
    public String getFilterDecription() {
        return "Varies the Gray Resolution of the image.";
    }
    
    @Override
    public String getFilterCategory() {
        return "Useful Filters";
    }
    
}
