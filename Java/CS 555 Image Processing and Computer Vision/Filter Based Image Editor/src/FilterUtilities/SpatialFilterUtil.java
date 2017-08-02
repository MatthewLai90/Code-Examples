/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package FilterUtilities;

/**
 *
 * @author mlai
 */
public final class SpatialFilterUtil {
    
    public static int[][] correlation(int[][] image, int[][] filter)
    {
        int filterLengthMedian = filter.length/2;
        int filterWidthMedian = filter[0].length/2;
        
        int[][] retImage = new int[image.length][image[0].length];
        
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[i].length; j++) {
                int sum = 0;
                for(int k = 0; k < filter.length; k++) {
                    int xCursor = i - (k - filterLengthMedian);
                    for(int l = 0; l < filter[k].length; l++) {
                        int yCursor = j - (l - filterWidthMedian);
                        
                        if(xCursor >= 0 && yCursor >=0 && xCursor < image.length && yCursor < image[i].length) sum += image[xCursor][yCursor] * filter[k][l];
                        
                        else if(xCursor < image.length && yCursor < image[i].length){
                        
                            if(xCursor < 0 && yCursor < 0) sum += image[0][0] * filter[k][l];
                            else if (xCursor < 0) sum += image[0][yCursor] * filter[k][l];
                            else if (yCursor < 0) sum += image[xCursor][0] * filter[k][l];
                        }
                        else if(xCursor >= 0 && yCursor >= 0) {
                            
                            if(xCursor >= image.length && yCursor >= image[i].length) sum += image[image.length - 1][image[i].length - 1] * filter[k][l];
                            else if (xCursor >= image.length) sum += image[image.length - 1][yCursor] * filter[k][l];
                            else if (yCursor >= image[i].length) sum += image[xCursor][image[i].length - 1] * filter[k][l];

                        }
                        
                    }
                }
                retImage[i][j] = sum;
            }
        }
        return retImage;
    }
    
    public static int[][] convolution(int[][] image, int[][] filter)
    {
        int[][] newFilter = new int[filter.length][filter[0].length];
        
        for (int i = 0; i < newFilter.length; i++) {
            for (int j = 0; j < newFilter[i].length; j++) {
                newFilter[i][j] = filter[newFilter.length - 1 - i][newFilter[i].length - 1 - j];
            }
        }
        
        return correlation(image, newFilter);
    }
    
    public static void main(String[] args) {
        int[][] testSource = {{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,5,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}};
        int[][] testFilter = {{1,0,-1},{2,0,-2},{1,0,-1}};
        int[][] out = correlation(testSource, testFilter);
        
        for (int i = 0; i < out.length; i++) {
            for (int j = 0; j < out[i].length; j++) {
                System.out.print(out[i][j] + "\t");
            }
            System.out.println("");
            
        }
        
    }
    
}
