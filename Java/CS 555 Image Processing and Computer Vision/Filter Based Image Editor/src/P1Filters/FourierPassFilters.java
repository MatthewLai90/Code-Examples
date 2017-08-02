/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P1Filters;

import FilterInterface.ImageFilter;
import GUI.FourierPassSlider;
import Utilities.DFTObj;
import Utilities.OpenCV;
import Utilities.RGBPixelArray;

/**
 *
 * @author Matthew Lai
 */
public class FourierPassFilters extends ImageFilter{

    @Override
    public RGBPixelArray filterImage(RGBPixelArray original) {
        // Convert RGBPixelArray to DFTObj fourier frequency transformed object
        // consisting of Aplitude and Phase data as well as Matrix size and type
        DFTObj tImage = OpenCV.dftForwardTransform(original.getGrayscaleValues());
        // GUI to receive User input
        FourierPassSlider fPS = new FourierPassSlider(tImage, this);
        fPS.setVisible(true);
        // Retrieve User Input transform filter from GUI
        double[][] filter = fPS.getFilter();
        
        // Shift DFTObj Amplitude Matrix Quadrants to allow for convolution with filter
        tImage = OpenCV.shiftMagnitudeQuadrants(OpenCV.dftForwardTransform(original.getGrayscaleValues()), false);
        // Convolve Filter (simple element by element multiplication
        tImage = tImage.convolve(filter);
        // Revert Quadrant Shift in Amplitude Matrix for reverse transformation back to spatial domain
        tImage = OpenCV.shiftMagnitudeQuadrants(tImage, true);
        // Return new RGBPixelArray containing inverse-transformed image
        return new RGBPixelArray(OpenCV.dftInverseTransformToImage(tImage));
    }
    
	
    /**
     * generateFilter, used by the FourierPassSlider GUI to generate the filters that is used
     * by the GUI and that is used to filter the frequency image
     *
     * int width - The width of the filter
     * int height - The height of the filter
     * int hL - High/Low Filter Flag
     * int iG - Ideal/Gaussian Filter Flag
     * int mod - Radius/Standard Deviation of the Filter
     */
    // hL : 0 - High, 1 - Low; iG : 0 - Ideal, 1 - Gaussian
    public double[][] generateFilter(int width, int height, int hL, int iG, double mod)
    {
//        mod = 10; // default
        double[][] filter = new double[height][width];
        int cx = width/2;
        int cy = height/2;
        for (int y = 0; y < filter.length; y++) {
            for (int x = 0; x < filter[y].length; x++) {
                // Ideal
                if(iG == 0)
                {
                    if(dist(cx, cy, x, y) < mod)
                    {
                        filter[y][x] = 1;
                        if(hL == 0)
                            filter[y][x] = 0;
                    }
                    else
                    {
                        filter[y][x] = 0;
                        if(hL == 0)
                            filter[y][x] = 1;
                    }
                }
                // Guassian
                if(iG == 1)
                {
                    filter[y][x] = guassian(dist(cx, cy, x, y), mod);
                    // ideal
                    if(hL == 0)
                        filter[y][x] = 1 - filter[y][x];
                }
            }
        }
        return filter;
    }
    
	// Distance between two points
    private double dist(int cx, int cy, int x, int y)
    {
        return Math.sqrt(Math.pow(Math.abs(cx-x),2) + Math.pow(Math.abs(cy-y), 2));
    }
    
	// Gaussian value based on distance from the center point
    private double guassian(double dist, double mod)
    {
        return Math.exp(-Math.pow(dist,2)/(2*Math.pow(mod, 2)));
    }
	
    @Override
    public String getFilterName() {
        return "Fourier Pass Filter";
    }

    @Override
    public String getFilterDecription() {
        return "Performs a ideal or gaussian low or high pass filter on the Fourier transformed space of this image.";
    }

    @Override
    public String getFilterCategory() {
        return "Project 1";
    }
    
    // Implement ideal and Gausian High/Low pass filters
    
    // With Sliders
    
}
