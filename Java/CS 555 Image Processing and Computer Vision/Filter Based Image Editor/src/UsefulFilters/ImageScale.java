
package UsefulFilters;

import FilterInterface.ImageFilter;
import FilterUtilities.RGBObject;
import GUI.SliderSelector;
import Utilities.RGBPixelArray;
import javax.swing.JOptionPane;

/**
 *
 * Allows for spacial scaling of the image, allow the user to select specific 
 * resolutions to scale the image to.
 * 
 * @author Matthew Lai
 */
public class ImageScale extends ImageFilter{

    int oWidth;
    int oHeight;
    RGBPixelArray scaledImage;
    
    @Override
    public RGBPixelArray filterImage(RGBPixelArray original) {
// Original Widths and heights        
        oWidth = original.getWidth();
        oHeight = original.getLength();
// scaledImage object is for displaying the preview and, eventually, returning.  
// Initializing the scaledImage object        
        scaledImage = new RGBPixelArray(original);
// GUI objects For user selection        
        SliderSelector sS = new SliderSelector(original, this);
        sS.setVisible(true);        
        int newWidth = sS.getSliderValue();
// Final scaling of the image before returning the new image.        
        scaledImage = scaleImage(original, newWidth, false);
        return scaledImage;
    }
// When the resulting image is smaller than the original image, this function
// subsamples the image.
    private static RGBPixelArray subsampleImage(RGBPixelArray original, int newWidth)
    {
        double downsizeRatio = (double)original.getWidth()/newWidth;
        RGBPixelArray newImage = new RGBPixelArray(newWidth, Math.round(newWidth * original.getLength()/original.getWidth()), original.isGrayscaleImage());
        for(int i = 0; i < newImage.getWidth(); i++)
            for(int j = 0; j < newImage.getLength(); j++)
            {
                newImage.setPixelValue(i, j, original, (int)Math.round(downsizeRatio*i), (int)Math.round(downsizeRatio*j));
            }
        return newImage;
    }
    
    final static int UPSCALE_NEAREST_NEIGHBOR = 0;
    final static int UPSCALE_BILINEAR = 1;
// When the resulting image is larger than the original image, this function upsample the image
// using a default or specified interpolation method.
    private static RGBPixelArray upsampleImage(RGBPixelArray original, int newWidth, int upsampleMethod)
    {
        double upsampleRatio = (double)newWidth/original.getWidth();
//        int[][] newImage = new int[newWidth][(int)Math.round(newWidth * (double)original.getLength()/original.getWidth())];
        RGBPixelArray newImage = new RGBPixelArray(newWidth, (int)Math.round(newWidth * (double)original.getLength()/original.getWidth()), original.isGrayscaleImage());
// Simple Pixel Replication (when the widths are multiples of each other) or otherwise, Nearest-Neighbor when the ratio between
// the images is a non integer
        if(upsampleMethod == 0) // Pixel Replication/Nearest Neighbor
        {
            for(int i = 0; i < newImage.getWidth(); i++)
                for(int j = 0; j < newImage.getLength(); j++)
                {
//                    newImage[i][j] = original[(int)Math.round((i/upsampleRatio)-.49)][(int)Math.round((j/upsampleRatio)-.49)];
                    newImage.setPixelValue(i, j, original, (int)Math.round((i/upsampleRatio)-.49), (int)Math.round((j/upsampleRatio)-.49));
                }
        }
// Bilinear interpolation. Calculates where the new pixel would be located on the old image if it were overlaid on top of
// it and then scales the four closest old pixels according to their proximity to the new pixel and adds them together.
        if(upsampleMethod == 1) // Bilinear Interpolation
        {
            for(int i = 0; i < newImage.getWidth(); i++)
                for(int j = 0; j < newImage.getLength(); j++)
                {
                    double x = ((double)i/upsampleRatio -.49);
                    double y = ((double)j/upsampleRatio -.49);
                    if(x < 0) x = 0;
                    if(y < 0) y = 0;
                    if(x > original.getWidth() - 1) x = original.getWidth() - 1;
                    if(y > original.getLength() - 1) y = original.getLength() - 1;
                    int p1 = original.getGrayscaleValues()[(int)Math.floor(x)][(int)Math.floor(y)];
                    int p2 = original.getGrayscaleValues()[(int)Math.floor(x)][(int)Math.ceil(y)];
                    int p3 = original.getGrayscaleValues()[(int)Math.ceil(x)][(int)Math.floor(y)];
                    int p4 = original.getGrayscaleValues()[(int)Math.ceil(x)][(int)Math.ceil(y)];
                    int value = (int)((p1 * (Math.ceil(x)-x) * (Math.ceil(y)-y)) + 
                            (p3 * (x-Math.floor(x)) * (Math.ceil(y)-y)) + 
                            (p2 * (Math.ceil(x)-x) * (y-Math.floor(y))) + 
                            (p4 * (x-Math.floor(x)) * (y-Math.floor(y))));
                    newImage.setPixelValue(i, j, value);
                }
        }
        return newImage;
    }
    
// Helper class containing the GUI and the logic to decide which algorithms to use.
    public static RGBPixelArray scaleImage(RGBPixelArray original, int newWidth, boolean useDefault)
    {
        RGBPixelArray newImage;
        if(newWidth > original.getWidth()) { // INSERT REPLICATION/NEAREST NEIGHBOR/BILINEAR CODE HERE
            if(useDefault) newImage = upsampleImage(original, newWidth, 0);
            else {
                int intoption = JOptionPane.showOptionDialog(new javax.swing.JFrame(), "Which Upsample Method Do You Want to Use?", "Upsample Option", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
                        new Object[] {"Replication/Nearest Neighbor","Bilinear Interpolation"}, "Replication/Nearest Neighbor"); 
                newImage = upsampleImage(original, newWidth, intoption);
            }
        }
        else { // INSERT SUBSAMPLE CODE HERE
            newImage = subsampleImage(original, newWidth);
        }
        return newImage;
    }

    @Override
    public String getFilterName() {
        return "Image Scaler";
    }

    @Override
    public String getFilterDecription() {
        return "Scales the image to a user selected resolution.";
    }
    
    @Override
    public String getFilterCategory() {
        return "Useful Filters";
    }
}
