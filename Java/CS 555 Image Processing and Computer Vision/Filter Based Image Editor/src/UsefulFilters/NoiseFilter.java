/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UsefulFilters;

import FilterInterface.ImageFilter;
import GUI.GUIUtilities;
import GUI.NoiseFilterSelection;
import Utilities.RGBPixelArray;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Matthew Lai
 */
public class NoiseFilter extends ImageFilter {

// Creates the GUI to retrieve the user selection.
    @Override
    public RGBPixelArray filterImage(RGBPixelArray original) {
        NoiseFilterSelection NFS = new NoiseFilterSelection(null, true);
        NFS.setVisible(true);
        double gaussV = NFS.getGaussianValue();
        int rayA = NFS.getRayAValue();
        double rayB = NFS.getRayBValue();
        int gammaA = NFS.getGammaAValue();
        int gammaB = NFS.getGammaBValue();
        int uniformA = NFS.getUniformAValue();
        int uniformB = NFS.getUniformBValue();
        int expA = NFS.getExpAValue();
        boolean doSalt = NFS.createSalt();
        boolean doPepper = NFS.createPepper();
        boolean[] selections = NFS.getSelections();
// the following call the filter and creates a new frame according to the user's selection.        
        if(selections[0]) //Gaussian
        {
            RGBPixelArray newImage = null;
            if(original.isGrayscaleImage()) newImage = new RGBPixelArray(gaussian(original.getGrayscaleValues(), gaussV));
            else
                try{
                    newImage = new RGBPixelArray(gaussian(original.getRed(), gaussV),
                                                 gaussian(original.getGreen(), gaussV),
                                                 gaussian(original.getBlue(), gaussV));
                } catch (Exception ex) { System.out.println("RBB Array Mismatch. Shouldn't Happen here."); System.exit(1); }
            for (int i = 0; i < original.getWidth(); i++) {
                for (int j = 0; j < original.getLength(); j++) {
                    if(original.isGrayscaleImage())
                    {
                        newImage.setPixelValue(i, j, original.getGrayscaleValues()[i][j] + newImage.getGrayscaleValues()[i][j]);
                    }
                    else
                    {
                        newImage.setPixelValue(i, j, original.getRed()[i][j] + newImage.getRed()[i][j], 
                                                     original.getGreen()[i][j] + newImage.getGreen()[i][j], 
                                                     original.getBlue()[i][j] + newImage.getBlue()[i][j]);
                    }
                }
            }
            createFrame(newImage, "Gaussian Noise");
        }
        if(selections[1]) //Rayleigh
        {
            RGBPixelArray newImage = null;
            if(original.isGrayscaleImage()) newImage = new RGBPixelArray(rayleigh(original.getGrayscaleValues(), rayA, rayB));
            else
                try{
                    newImage = new RGBPixelArray(rayleigh(original.getRed(), rayA, rayB),
                                                 rayleigh(original.getGreen(), rayA, rayB),
                                                 rayleigh(original.getBlue(), rayA, rayB));
                } catch (Exception ex) { System.out.println("RBB Array Mismatch. Shouldn't Happen here."); System.exit(1); }
            for (int i = 0; i < original.getWidth(); i++) {
                for (int j = 0; j < original.getLength(); j++) {
                    if(original.isGrayscaleImage())
                    {
                        newImage.setPixelValue(i, j, original.getGrayscaleValues()[i][j] + newImage.getGrayscaleValues()[i][j]);
                    }
                    else
                    {
                        newImage.setPixelValue(i, j, original.getRed()[i][j] + newImage.getRed()[i][j], 
                                                     original.getGreen()[i][j] + newImage.getGreen()[i][j], 
                                                     original.getBlue()[i][j] + newImage.getBlue()[i][j]);
                    }
                }
            }
            createFrame(newImage, "Rayleigh Noise");
        }
        if(selections[2]) //Gamma
        {
            RGBPixelArray newImage = null;
            if(original.isGrayscaleImage()) newImage = new RGBPixelArray(erlang(original.getGrayscaleValues(), gammaA, gammaB));
            else
                try{
                    newImage = new RGBPixelArray(erlang(original.getRed(), gammaA, gammaB),
                                                 erlang(original.getGreen(), gammaA, gammaB),
                                                 erlang(original.getBlue(), gammaA, gammaB));
                } catch (Exception ex) { System.out.println("RBB Array Mismatch. Shouldn't Happen here."); System.exit(1); }
            for (int i = 0; i < original.getWidth(); i++) {
                for (int j = 0; j < original.getLength(); j++) {
                    if(original.isGrayscaleImage())
                    {
                        newImage.setPixelValue(i, j, original.getGrayscaleValues()[i][j] + newImage.getGrayscaleValues()[i][j]);
                    }
                    else
                    {
                        newImage.setPixelValue(i, j, original.getRed()[i][j] + newImage.getRed()[i][j], 
                                                     original.getGreen()[i][j] + newImage.getGreen()[i][j], 
                                                     original.getBlue()[i][j] + newImage.getBlue()[i][j]);
                    }
                }
            }
            createFrame(newImage, "Gamma Noise");
        }
        if(selections[3]) //Exp
        {
            RGBPixelArray newImage = null;
            if(original.isGrayscaleImage()) newImage = new RGBPixelArray(exponential(original.getGrayscaleValues(), expA));
            else
                try{
                    newImage = new RGBPixelArray(exponential(original.getRed(), expA),
                                                 exponential(original.getGreen(), expA),
                                                 exponential(original.getBlue(), expA));
                } catch (Exception ex) { System.out.println("RBB Array Mismatch. Shouldn't Happen here."); System.exit(1); }
            for (int i = 0; i < original.getWidth(); i++) {
                for (int j = 0; j < original.getLength(); j++) {
                    if(original.isGrayscaleImage())
                    {
                        newImage.setPixelValue(i, j, original.getGrayscaleValues()[i][j] + newImage.getGrayscaleValues()[i][j]);
                    }
                    else
                    {
                        newImage.setPixelValue(i, j, original.getRed()[i][j] + newImage.getRed()[i][j], 
                                                     original.getGreen()[i][j] + newImage.getGreen()[i][j], 
                                                     original.getBlue()[i][j] + newImage.getBlue()[i][j]);
                    }
                }
            }
            createFrame(newImage, "Exponential Noise");
        }
        if(selections[4]) //Uniform
        {
            RGBPixelArray newImage = null;
            if(original.isGrayscaleImage()) newImage = new RGBPixelArray(uniform(original.getGrayscaleValues(), uniformA,uniformB));
            else
                try{
                    newImage = new RGBPixelArray(uniform(original.getRed(), uniformA,uniformB),
                                                 uniform(original.getGreen(), uniformA,uniformB),
                                                 uniform(original.getBlue(), uniformA,uniformB));
                } catch (Exception ex) { System.out.println("RBB Array Mismatch. Shouldn't Happen here."); System.exit(1); }
            for (int i = 0; i < original.getWidth(); i++) {
                for (int j = 0; j < original.getLength(); j++) {
                    if(original.isGrayscaleImage())
                    {
                        newImage.setPixelValue(i, j, original.getGrayscaleValues()[i][j] + newImage.getGrayscaleValues()[i][j]);
                    }
                    else
                    {
                        newImage.setPixelValue(i, j, original.getRed()[i][j] + newImage.getRed()[i][j], 
                                                     original.getGreen()[i][j] + newImage.getGreen()[i][j], 
                                                     original.getBlue()[i][j] + newImage.getBlue()[i][j]);
                    }
                }
            }
            createFrame(newImage, "Uniform Noise");
        }
        if(selections[5]) //Impulse
        {
            RGBPixelArray newImage = null;
            if(original.isGrayscaleImage()) newImage = new RGBPixelArray(impulse(original.getGrayscaleValues(), doSalt, doPepper));
            else
                try{
                    newImage = new RGBPixelArray(impulse(original.getRed(), doSalt, doPepper),
                                                 impulse(original.getGreen(), doSalt, doPepper),
                                                 impulse(original.getBlue(), doSalt, doPepper));
                } catch (Exception ex) { System.out.println("RBB Array Mismatch. Shouldn't Happen here."); System.exit(1); }
            for (int i = 0; i < original.getWidth(); i++) {
                for (int j = 0; j < original.getLength(); j++) {
                    if(original.isGrayscaleImage())
                    {
                        newImage.setPixelValue(i, j, original.getGrayscaleValues()[i][j] + newImage.getGrayscaleValues()[i][j]);
                    }
                    else
                    {
                        newImage.setPixelValue(i, j, original.getRed()[i][j] + newImage.getRed()[i][j], 
                                                     original.getGreen()[i][j] + newImage.getGreen()[i][j], 
                                                     original.getBlue()[i][j] + newImage.getBlue()[i][j]);
                    }
                }
            }
            createFrame(newImage, "Impulse Noise");
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
        return "Noise Filter";
    }

    @Override
    public String getFilterDecription() {
        return "Generates image with specified noise.";
    }

    @Override
    public String getFilterCategory() {
        return "Useful Filters";
    }
    
    private int[][] gaussian(int[][] image, double variance)
    {
        if(variance > 1) return null;
        Random r = new Random();
        int [][] noise = new int[image.length][image[0].length];
        int max = getImageMax(image);
        for (int i = 0; i < noise.length; i++) 
            for (int j = 0; j < noise[i].length; j++) 
            {
                noise[i][j] =  (int)(((1.0/Math.sqrt(2.0*Math.PI))*Math.pow(Math.E,-Math.pow((double)r.nextInt(max)-max/2.0,2.0)/(2*Math.pow(variance, 2))))*10000);
            }
            
        return noise;
    }
    
    private int[][] rayleigh(int[][] image, int a, double b)
    {
        Random r = new Random();
        int [][] noise = new int[image.length][image[0].length];
        int max = getImageMax(image);
        for (int i = 0; i < noise.length; i++) 
            for (int j = 0; j < noise[i].length; j++) 
            {
                int z = r.nextInt(max);
                if(z >= a) noise[i][j] =  (int)((2.0/b)*(z-a)*Math.pow(Math.E, -Math.pow(z-a,2)/b));
                else noise[i][j] = 0;
            }
        return noise;
    }
    
    private int[][] erlang(int[][] image, int a, int b)
    {
        Random r = new Random();
        int [][] noise = new int[image.length][image[0].length];
        int max = getImageMax(image);
        for (int i = 0; i < noise.length; i++) 
            for (int j = 0; j < noise[i].length; j++) 
            {
                int z = r.nextInt(max);
                noise[i][j] =  (int)(((Math.pow(a, b)*Math.pow(z, b-1))/fact(b-1))*Math.pow(Math.E, -a*z));
                if(z < a) noise[i][j] = 0;
            }
        return noise;
        
    }
    
    private double fact(double num)
    {
        double total = 1;
        for(double i = num; i > 0; i--)
            total *= i;
        return total;
    }
    
    private int[][] exponential(int[][] image, int a)
    {
        Random r = new Random();
        int [][] noise = new int[image.length][image[0].length];
        int max = getImageMax(image);
        for (int i = 0; i < noise.length; i++) 
            for (int j = 0; j < noise[i].length; j++) 
            {
                int z = r.nextInt(max);
                noise[i][j] =  (int)(a * Math.pow(Math.E, -a*z));
                if(z < a) noise[i][j] = 0;
            }
        return noise;
        
    }
    
    private int[][] uniform(int[][] image, int a, int b)
    {
        Random r = new Random();
        int [][] noise = new int[image.length][image[0].length];
        int max = getImageMax(image);
        for (int i = 0; i < noise.length; i++) 
            for (int j = 0; j < noise[i].length; j++) 
            {
                int z = r.nextInt(max);
                noise[i][j] = 0;
                if(z >= a && z <= b) noise[i][j] =  (int)(1/(b - a));
            }
        return noise;
        
    }
    
    private int[][] impulse(int[][] image, boolean salt, boolean pepper)
    {
        Random r = new Random();
        int imageMax = getImageMax(image);
        int [][] noiseImage = new int[image.length][image[0].length];
        for (int i = 0; i < noiseImage.length; i++) 
            for (int j = 0; j < noiseImage[i].length; j++)
            {
                int noiseValue =  r.nextInt(imageMax);
                noiseImage[i][j] = 0;
                if(pepper && noiseValue == imageMax - 1) noiseImage[i][j] = imageMax - 1;
                if(salt && noiseValue == 0) noiseImage[i][j] = -(imageMax - 1);
            }
        return noiseImage;
    }
    
    private int getImageMax(int[][] image)
    {
        int max = 0;
        for (int i = 0; i < image.length; i++) 
            for (int j = 0; j < image[i].length; j++) 
                if(image[i][j] > max) max = image[i][j];
        
        int imageMax = (int)Math.pow(2, 0);
        int c = 0;
        while(imageMax <= max) imageMax = (int)Math.pow(2, ++c);
        
//        return imageMax;
        return (int)Math.pow(2, 8);
    }
    
}
