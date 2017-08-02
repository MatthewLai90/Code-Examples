/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package FilterInterface;


/**
 *
 * @author Matthew Lai
 */
public abstract class ImageFilter {
    
    public ImageFilter() { }
    public ImageFilter(java.awt.Component a) { }
    //Takes in a 2d array representation of an image and performs a filter operation
    // on the image, returning the filtered image
    public abstract Utilities.RGBPixelArray filterImage(Utilities.RGBPixelArray original);
    // Return Filter Name
    public abstract String getFilterName();
    // Return Filter Description
    public abstract String getFilterDecription();
    // Return Filter Category
    public abstract String getFilterCategory();
}
