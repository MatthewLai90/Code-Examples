/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Utilities;

/**
 *
 * @author Matthew Lai
 */
public class ImageResult {
    
    final int [][] image;
    final boolean imageChanged;
    
    public ImageResult(int[][] image, boolean imageChanged)
    {
        this.image = image;
        this.imageChanged = imageChanged;
    }
    
    public int[][] getImage()
    {
        return image;
    }
    
    public boolean imageChanged()
    {
        return imageChanged;
    }
    
}
