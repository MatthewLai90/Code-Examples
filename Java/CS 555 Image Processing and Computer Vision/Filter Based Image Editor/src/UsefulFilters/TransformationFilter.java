

package UsefulFilters;

import FilterInterface.ImageFilter;
import FilterUtilities.RGBObject;
import GUI.TransformationForm;
import Utilities.RGBPixelArray;

/**
 * 
 * The transformation filter, which allows the user to select either the logarithmic
 *  transformation or the power-law transformation and applies it to the the image.
 *
 * @author Matthew Lai
 */
public class TransformationFilter extends ImageFilter {
    
    double c, gamma;
    int transType;
    
    @Override
    public RGBPixelArray filterImage(RGBPixelArray original) {
// TransfomrationForm is the GUI object that takes in the user inputs.        
        TransformationForm tF = new TransformationForm();
        tF.setVisible(true);
        c = tF.getC();
        gamma = tF.getGamma();
        transType = tF.getTransformType();
        tF.dispose();
// Initializes the new image object to the same size and the old image object
        int [][] newImage = new int[original.getWidth()][original.getLength()];
// Performs the Log transform on the old image and stores it in the new image.
        if(transType == 0) // Log
        {
            for(int i = 0; i < newImage.length; i++)
                for(int j = 0; j < newImage[i].length; j++)
                {
                    int value = (int) Math.round(c * Math.log10(1.0 + (double)(original.getGrayscaleValues()[i][j])));
                    newImage[i][j] = value;
                }
        }
// Performs the power-law transform on the old image and stores it in the new image.        
        if(transType == 1) // Gamma
        {
            for(int i = 0; i < newImage.length; i++)
                for(int j = 0; j < newImage[i].length; j++)
                {
                    int value = (int) Math.round(c * Math.pow((double)(original.getGrayscaleValues()[i][j]), gamma));
                    newImage[i][j] = value;
                }
        }
// Returns the new image        
        return new RGBPixelArray(newImage);
    }

    @Override
    public String getFilterName() {
        return "Intensity Transformation";
    }

    @Override
    public String getFilterDecription() {
        return "Allow for the selection of intensity transformations.";
    }
    
    @Override
    public String getFilterCategory() {
        return "Useful Filters";
    }
    
}
