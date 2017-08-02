/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UsefulFilters;

import FilterInterface.ImageFilter;
import FilterUtilities.SpatialFilterUtil;
import GUI.GUIUtilities;
import GUI.NoiseReductionFiltersSelection;
import Utilities.RGBPixelArray;
import javax.swing.JFrame;

/**
 *
 * @author Matthew Lai
 */
public class NoiseReductionFilters extends ImageFilter {
    
// Creates the GUI to retrieve the user selection. Allows for the user to set the mask size.
    @Override
    public RGBPixelArray filterImage(RGBPixelArray original) {
        NoiseReductionFiltersSelection NRFS = new NoiseReductionFiltersSelection(null, true);
        NRFS.setVisible(true);
        int xSpinnerValue = NRFS.getXSpinnerValue();
        int ySpinnerValue = NRFS.getYSpinnerValue();
        double qValue = NRFS.getQSpinnerValue();
        int dValue = NRFS.getDSpinnerValue();
        boolean[] selections = NRFS.getSelections();
// Filters are called according to the user's selection        
        if(selections[0]){
// New image is created by applying the arithmetic mean filter and is displayed on a frame. The program detects grayscale vs. color and returns the appropriate image.
            RGBPixelArray newImage = null;
            if(original.isGrayscaleImage())
                newImage = new RGBPixelArray(arithmeticMeanFilter(original.getGrayscaleValues(), xSpinnerValue, ySpinnerValue));
            else
                try {
                newImage = new RGBPixelArray(arithmeticMeanFilter(original.getRed(),xSpinnerValue,ySpinnerValue),
                                             arithmeticMeanFilter(original.getGreen(),xSpinnerValue,ySpinnerValue),
                                             arithmeticMeanFilter(original.getBlue(),xSpinnerValue,ySpinnerValue));
                } catch (Exception ex) { System.out.println("RBB Array Mismatch. Shouldn't Happen here."); System.exit(1); }
            createFrame(newImage, "Arithmethic Mean Filter");
        }
// New image is created by applying the geometric mean filter and is displayed on a frame. The program detects grayscale vs. color and returns the appropriate image.
        if(selections[1]){
            
            RGBPixelArray newImage = null;
            if(original.isGrayscaleImage())
                newImage = new RGBPixelArray(geometricMeanFilter(original.getGrayscaleValues(), xSpinnerValue, ySpinnerValue));
            else
                try {
                newImage = new RGBPixelArray(geometricMeanFilter(original.getRed(),xSpinnerValue,ySpinnerValue),
                                             geometricMeanFilter(original.getGreen(),xSpinnerValue,ySpinnerValue),
                                             geometricMeanFilter(original.getBlue(),xSpinnerValue,ySpinnerValue));
                } catch (Exception ex) { System.out.println("RBB Array Mismatch. Shouldn't Happen here."); System.exit(1); }
            createFrame(newImage, "Geometric Mean Filter");
        }
// New image is created by applying the harmonic mean filter and is displayed on a frame. The program detects grayscale vs. color and returns the appropriate image.
        if(selections[2]){
            
            RGBPixelArray newImage = null;
            if(original.isGrayscaleImage())
                newImage = new RGBPixelArray(harmonicMeanFilter(original.getGrayscaleValues(), xSpinnerValue, ySpinnerValue));
            else
                try {
                newImage = new RGBPixelArray(harmonicMeanFilter(original.getRed(),xSpinnerValue,ySpinnerValue),
                                             harmonicMeanFilter(original.getGreen(),xSpinnerValue,ySpinnerValue),
                                             harmonicMeanFilter(original.getBlue(),xSpinnerValue,ySpinnerValue));
                } catch (Exception ex) { System.out.println("RBB Array Mismatch. Shouldn't Happen here."); System.exit(1); }
            createFrame(newImage, "Harmonic Mean Filter");
        }
// New image is created by applying the contraharmonic  mean filter and is displayed on a frame. The program detects grayscale vs. color and returns the appropriate image.
        if(selections[3]){
            
            RGBPixelArray newImage = null;
            if(original.isGrayscaleImage())
                newImage = new RGBPixelArray(contraHaromonicMeanFilter(original.getGrayscaleValues(), xSpinnerValue, ySpinnerValue, qValue));
            else
                try {
                newImage = new RGBPixelArray(contraHaromonicMeanFilter(original.getRed(),xSpinnerValue,ySpinnerValue, qValue),
                                             contraHaromonicMeanFilter(original.getGreen(),xSpinnerValue,ySpinnerValue, qValue),
                                             contraHaromonicMeanFilter(original.getBlue(),xSpinnerValue,ySpinnerValue, qValue));
                } catch (Exception ex) { System.out.println("RBB Array Mismatch. Shouldn't Happen here."); System.exit(1); }
            createFrame(newImage, "Contraharmonic Mean Filter");
        }
// New image is created by applying the max filter and is displayed on a frame. The program detects grayscale vs. color and returns the appropriate image.
        if(selections[4]) {
            
            RGBPixelArray newImage = null;
            if(original.isGrayscaleImage())
                newImage = new RGBPixelArray(maxFilter(original.getGrayscaleValues(), xSpinnerValue, ySpinnerValue));
            else
                try {
                newImage = new RGBPixelArray(maxFilter(original.getRed(),xSpinnerValue,ySpinnerValue),
                                             maxFilter(original.getGreen(),xSpinnerValue,ySpinnerValue),
                                             maxFilter(original.getBlue(),xSpinnerValue,ySpinnerValue));
                } catch (Exception ex) { System.out.println("RBB Array Mismatch. Shouldn't Happen here."); System.exit(1); }
            createFrame(newImage, "Max Filter");
        }
// New image is created by applying the min filter and is displayed on a frame. The program detects grayscale vs. color and returns the appropriate image.
        if(selections[5]){
            
            RGBPixelArray newImage = null;
            if(original.isGrayscaleImage())
                newImage = new RGBPixelArray(minFilter(original.getGrayscaleValues(), xSpinnerValue, ySpinnerValue));
            else
                try {
                newImage = new RGBPixelArray(minFilter(original.getRed(),xSpinnerValue,ySpinnerValue),
                                             minFilter(original.getGreen(),xSpinnerValue,ySpinnerValue),
                                             minFilter(original.getBlue(),xSpinnerValue,ySpinnerValue));
                } catch (Exception ex) { System.out.println("RBB Array Mismatch. Shouldn't Happen here."); System.exit(1); }
            createFrame(newImage, "Min Filter");
        }
// New image is created by applying the midpoint filter and is displayed on a frame. The program detects grayscale vs. color and returns the appropriate image.
        if(selections[6]){
            
            RGBPixelArray newImage = null;
            if(original.isGrayscaleImage())
                newImage = new RGBPixelArray(midpointFilter(original.getGrayscaleValues(), xSpinnerValue, ySpinnerValue));
            else
                try {
                newImage = new RGBPixelArray(midpointFilter(original.getRed(),xSpinnerValue,ySpinnerValue),
                                             midpointFilter(original.getGreen(),xSpinnerValue,ySpinnerValue),
                                             midpointFilter(original.getBlue(),xSpinnerValue,ySpinnerValue));
                } catch (Exception ex) { System.out.println("RBB Array Mismatch. Shouldn't Happen here."); System.exit(1); }
            createFrame(newImage, "Midpoint Filter");
        }
// New image is created by applying the alpha-trimmed mean filter and is displayed on a frame. The program detects grayscale vs. color and returns the appropriate image.
        if(selections[7]){
            
            RGBPixelArray newImage = null;
            if(original.isGrayscaleImage())
                newImage = new RGBPixelArray(alphaTrimmedMeanFilter(original.getGrayscaleValues(), xSpinnerValue, ySpinnerValue, dValue));
            else
                try {
                newImage = new RGBPixelArray(alphaTrimmedMeanFilter(original.getRed(),xSpinnerValue,ySpinnerValue, dValue),
                                             alphaTrimmedMeanFilter(original.getGreen(),xSpinnerValue,ySpinnerValue, dValue),
                                             alphaTrimmedMeanFilter(original.getBlue(),xSpinnerValue,ySpinnerValue, dValue));
                } catch (Exception ex) { System.out.println("RBB Array Mismatch. Shouldn't Happen here."); System.exit(1); }
            createFrame(newImage, "Alpha-trimmed Mean Filter");
        }
        
        return null;
    }
    
    private void createFrame(RGBPixelArray newImage, String windowName)
    {
            JFrame newFrame = new JFrame();
            newFrame.add(GUIUtilities.getScrollWindow(GUIUtilities.getImagePanel(newImage)));
            newFrame.setTitle(windowName);
            newFrame.setSize(1000, 1000);
            newFrame.setVisible(true);
    }

    @Override
    public String getFilterName() {
        return "Noise Reduction Filters";
    }

    @Override
    public String getFilterDecription() {
        return "Allows for the selection of various spatial filtering operations.";
    }

    @Override
    public String getFilterCategory() {
        return "Useful Filters";
    }

    private int[][] arithmeticMeanFilter(int[][] image, int xFilterSize, int yFilterSize)
    {
        int [][] filter = new int[xFilterSize][yFilterSize];
        int filterSum = 0;
        for (int i = 0; i < filter.length; i++) 
            for (int j = 0; j < filter[i].length; j++) 
            {
                filter[i][j] = 1;
                filterSum += 1;
            }
        
        int[][] returnImage = SpatialFilterUtil.correlation(image, filter);
        for (int i = 0; i < returnImage.length; i++) {
            for (int j = 0; j < returnImage[i].length; j++) {
                returnImage[i][j] = returnImage[i][j]/filterSum;
            }
        }
        return returnImage;
    }
    
    private int[][] geometricMeanFilter(int[][] image, int xFilterSize, int yFilterSize)
    {
        int [][] output = new int[image.length][image[0].length];
        int dx = xFilterSize/2;
        int dy = yFilterSize/2;
        for(int x = 0; x < image.length; x++)
            for(int y = 0; y < image[x].length; y++)
            {
                if(x - dx >= 0 && x + dx < image.length &&
                        y -dy >= 0 && y + dy < image[x].length)
                {
                    double mult = 1;
                    for(int i = -dx; i <= dx; i++)
                        for(int j = -dy; j <= dy; j++)
                        {
                            mult = mult * image[x+i][y+j];
                        }
                    output[x][y] = (int)Math.round(Math.pow(mult, 1.0/(xFilterSize*yFilterSize)));
                }
                else output[x][y] = image[x][y];
            }
        return output;
    }
    
    private int[][] harmonicMeanFilter(int[][] image, int xFilterSize, int yFilterSize)
    {
        
        int [][] output = new int[image.length][image[0].length];
        int dx = xFilterSize/2;
        int dy = yFilterSize/2;
        for(int x = 0; x < image.length; x++)
            for(int y = 0; y < image[x].length; y++)
            {
                if(x - dx >= 0 && x + dx < image.length &&
                        y -dy >= 0 && y + dy < image[x].length)
                {
                    double sum = 0;
                    for(int i = -dx; i <= dx; i++)
                        for(int j = -dy; j <= dy; j++)
                        {
                            sum = sum + 1.0/image[x+i][y+j];
                        }
                    output[x][y] = (int)Math.round((double)(xFilterSize*yFilterSize)/sum);
                }
                else output[x][y] = image[x][y];
            }
        return output;
    }
    
    private int[][] contraHaromonicMeanFilter(int[][] image, int xFilterSize, int yFilterSize, double order)
    {
        
        int [][] output = new int[image.length][image[0].length];
        int dx = xFilterSize/2;
        int dy = yFilterSize/2;
        for(int x = 0; x < image.length; x++)
            for(int y = 0; y < image[x].length; y++)
            {
                if(x - dx >= 0 && x + dx < image.length &&
                        y -dy >= 0 && y + dy < image[x].length)
                {
                    double numer = 0;
                    double denom = 0;
                    for(int i = -dx; i <= dx; i++)
                        for(int j = -dy; j <= dy; j++)
                        {
                            numer = numer + Math.pow(image[x+i][y+j], order+1);
                            denom = denom + Math.pow(image[x+i][y+j], order);
                        }
                    output[x][y] = (int)Math.round(numer/denom);
                }
                else output[x][y] = image[x][y];
            }
        return output;
    }
    
    private int[][] maxFilter(int[][] image, int xFilterSize, int yFilterSize)
    {
        int [][] output = new int[image.length][image[0].length];
        int dx = xFilterSize/2;
        int dy = yFilterSize/2;
        for(int x = 0; x < image.length; x++)
            for(int y = 0; y < image[x].length; y++)
            {
                if(x - dx >= 0 && x + dx < image.length &&
                        y -dy >= 0 && y + dy < image[x].length)
                {
                    int max = 0;
                    for(int i = -dx; i <= dx; i++)
                        for(int j = -dy; j <= dy; j++)
                        {
                            if(image[x+i][y+j] > max) max = image[x+i][y+j];
                        }
                    output[x][y] = max;
                }
                else output[x][y] = image[x][y];
            }
        return output;
    }
    
    private int[][] minFilter(int[][] image, int xFilterSize, int yFilterSize)
    {
        int [][] output = new int[image.length][image[0].length];
        int dx = xFilterSize/2;
        int dy = yFilterSize/2;
        for(int x = 0; x < image.length; x++)
            for(int y = 0; y < image[x].length; y++)
            {
                if(x - dx >= 0 && x + dx < image.length &&
                        y -dy >= 0 && y + dy < image[x].length)
                {
                    int min = image[x][y];
                    for(int i = -dx; i <= dx; i++)
                        for(int j = -dy; j <= dy; j++)
                        {
                            if(image[x+i][y+j] < min) min = image[x+i][y+j];
                        }
                    output[x][y] = min;
                }
                else output[x][y] = image[x][y];
            }
        return output;
    }
    
    private int[][] midpointFilter(int[][] image, int xFilterSize, int yFilterSize)
    {
        int [][] output = new int[image.length][image[0].length];
        int dx = xFilterSize/2;
        int dy = yFilterSize/2;
        for(int x = 0; x < image.length; x++)
            for(int y = 0; y < image[x].length; y++)
            {
                if(x - dx >= 0 && x + dx < image.length &&
                        y -dy >= 0 && y + dy < image[x].length)
                {
                    int max = 0;
                    int min = image[x][y];
                    for(int i = -dx; i <= dx; i++)
                        for(int j = -dy; j <= dy; j++)
                        {
                            if(image[x+i][y+j] > max) max = image[x+i][y+j];
                            if(image[x+i][y+j] < min) min = image[x+i][y+j];
                        }
                    output[x][y] = Math.round((max+min)/2);
                }
                else output[x][y] = image[x][y];
            }
        return output;
    }
    
    private int[][] alphaTrimmedMeanFilter(int[][] image, int xFilterSize, int yFilterSize, int d)
    {
        int [][] output = new int[image.length][image[0].length];
        int dx = xFilterSize/2;
        int dy = yFilterSize/2;
        for(int x = 0; x < image.length; x++)
            for(int y = 0; y < image[x].length; y++)
            {
                if(x - dx >= 0 && x + dx < image.length &&
                        y -dy >= 0 && y + dy < image[x].length)
                {
                    int[] bottomTrim = new int[d/2];
                    int[] topTrim = new int[d/2];
                    for (int i = 0; i < d/2; i++) {
                        bottomTrim[i] = Integer.MAX_VALUE;
                        topTrim[i] = 0;
                    }
                    int sum = 0;
                    int[] test = new int[xFilterSize*yFilterSize];
                    int c = 0;
                    for(int i = -dx; i <= dx; i++)
                        for(int j = -dy; j <= dy; j++)
                        {
                            test[c++] = image[x+i][y+j];
                            int number = image[x+i][y+j];
                            sum = sum + number;
                            for(int k = 0; k < d/2; k++)
                            {
                                if(number > topTrim[k] && ((!(k+1 >= topTrim.length) && number <= topTrim[k+1]) || k == topTrim.length-1))
                                {
                                    for(int l = 0; l < k; l++)
                                    {
                                        topTrim[l] = topTrim[l+1];
                                    }
                                    topTrim[k] = number;
                                }
                                if(number < bottomTrim[k] && ((!(k+1 >= bottomTrim.length) && number >= bottomTrim[k+1]) || k == bottomTrim.length-1))
                                {
                                    for(int l = 0; l < k; l++)
                                    {
                                        bottomTrim[l] = bottomTrim[l+1];
                                    }
                                    bottomTrim[k] = number;
                                }
                            }
                        }
                    
                    if(x == 50 && y == 50)
                    {
                        for (int i = 0; i < test.length; i++) {
                            System.out.print(test[i] + "\t");
                        }
                        System.out.println("");
                        for(int i = 0; i < bottomTrim.length; i++)
                            System.out.print(bottomTrim[i] + "\t");
                        for(int i = 0; i < topTrim.length; i++)
                            System.out.print(topTrim[i] + "\t");
                    }
                    
                    for(int i = 0; i < d/2; i++) sum = sum - bottomTrim[i] - topTrim[i];
                    
                    output[x][y] = (int)((double)(sum)/(double)(xFilterSize*yFilterSize - d));
                }
                else output[x][y] = image[x][y];
            }
        return output;
    }
    
}
