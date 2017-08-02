/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ImageProcessor;

import GUI.GUIUtilities;
import FilterUtilities.RGBObject;

/**
 *
 * @author Matthew Lai
 */
public class HistogramGen {
    
    public static int[] generateGlobalHist(int[][]image)
    {
//        return generateGlobalHist(image, 1);
        return generateGlobalHistogram(image);
    }
    
    public static int getBitCount(int [][] image)
    {
        int bitCount = 1;
        for (int i = 0; i < image.length; i++) 
            for (int j = 0; j < image[i].length; j++) 
                if((image[i][j]) > Math.pow(2, bitCount) - 1) bitCount++;
        
        return (int)(Math.pow(2, bitCount) - 1);
    }
    
//    public static void displayHistogramChart(int[] histogram)
//    {
//        int max = 0;
//        for (int i = 0; i < histogram.length; i++) 
//            if(histogram[i] > max) max = histogram[i];
//        
//        int BLACK = new RGBObject(255, 255, 255).getIntRGB();
//        int WHITE = new RGBObject(0, 0, 0).getIntRGB();
//        int[][] histImage = new int[histogram.length][max];
//        for (int i = 0; i < histImage.length; i++) {
//            for(int j = 0; j < histogram[i]; j++)
//                histImage[i][j] = BLACK;
//            for(int j = histogram[i]; j < histImage[i].length; j++)
//                histImage[i][j] = WHITE;
//        }
//        
//        GUI.JImagePanel jIP = GUIUtilities.getImagePanel(histImage);
//        javax.swing.JFrame frame = new javax.swing.JFrame();
//        frame.add(jIP);
//        frame.setSize(1920,1080);
//        frame.setVisible(true);
//    }
    
    public static void printHistogramChart(int[] histogram)
    {
        for (int i = 0; i < histogram.length; i++) {
            System.out.println(histogram[i]);
        }
    }
    
    public static int[] generateLocalHist(int[][] image, int x, int y, int dim, int bitCount)
    {
        int[] histogram = new int[bitCount];
        for (int i = 0; i < histogram.length; i++) {
            histogram[i] = 0;
        }
        
        for (int i = -dim/2; i < dim/2; i++) 
            for (int j = -dim/2; j < dim/2; j++) 
            {
                int currentX = x+i;
                int currentY = y+j;
                if(currentX < 0) currentX = 0;
                if(currentY < 0) currentY = 0;
                if(currentX >= image.length) currentX = image.length-1;
                if(currentY >= image[0].length) currentY = image[0].length-1;
                histogram[image[currentX][currentY]]++;
            }
        
        return histogram;
    }
    
    public static int[] generateGlobalHistogram(int[][]image)
    {
        image = ImageUtilities.normalize(image, 8);
        int max = 0;
        for (int i = 0; i < image.length; i++) 
            for (int j = 0; j < image[i].length; j++) 
                if(image[i][j] > max) max = image[i][j];
//        int bitdepth = (int)Math.ceil(Math.log(max)/Math.log(2));
//        System.out.println(max + " " + bitdepth);
//        image = ImageUtilities.normalize(image, bitdepth);
        
        int histogramLength = (int)Math.pow(2, 0);
        int c = 0;
        while(histogramLength <= max) histogramLength = (int)Math.pow(2, ++c);
        
        int[] histogram = new int[histogramLength];
        
        for(int i = 0; i < image.length; i++)
            for (int j = 0; j < image[i].length; j++)
                histogram[image[i][j]]++;
        
        return histogram;
    }
    
    public static int[] generateGlobalHist(int[][]image, int bitCount)
    {
        int [] histogram = null;
        try {
            histogram = new int[(int) Math.pow(2,bitCount) - 1];
            for (int i = 0; i < histogram.length; i++) {
                histogram[i] = 0;
            }
            for (int i = 0; i < image.length; i++) {
                for (int j = 0; j < image[i].length; j++) {
                    histogram[image[i][j]]++;
                }
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException e)
        {
            return generateGlobalHist(image, bitCount+1);
        }
//        for (int i = 0; i < histogram.length; i++) {
//            histogram[i] = histogram[i];
//        }
        return histogram;
    }
}
