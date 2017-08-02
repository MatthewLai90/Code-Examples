/*
 * Loads an image from a directory and returns a 2d array.
 *//*
 * Loads an image from a directory and returns a 2d array.
 *//*
 * Loads an image from a directory and returns a 2d array.
 *//*
 * Loads an image from a directory and returns a 2d array.
 */

package ImageProcessor;

import FilterUtilities.RGBObject;
import GUI.GUIUtilities;
import Utilities.RGBPixelArray;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

/**
 *
 * @author Matthew Lai
 */
public final class ImageUtilities {
        
    public static RGBPixelArray loadImage(File file)
    {
        if(file == null) return null;
        BufferedImage img = null;
        try { img = ImageIO.read(file); } 
        catch(IOException e) { GUIUtilities.errorMessage("There was an error in loading your image", false); }
        
//        BufferedImage ret = new BufferedImage(img.getData().getWidth(),img.getData().getHeight(),BufferedImage.TYPE_BYTE_GRAY);
        BufferedImage ret = new BufferedImage(img.getData().getWidth(),img.getData().getHeight(),BufferedImage.TYPE_INT_RGB);
        Graphics g = ret.getGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        
//        ret.setRGB(0, 0, new Color(10, 50, 150).getRGB());
        
        return getRGBArray(ret);
    }
    
    public static int[][] getIntArray(BufferedImage img)
    {
        int[][] PixelArray = new int[img.getWidth()][img.getHeight()];
        for(int i = 0; i < img.getWidth(); i++)            
            for(int j = 0; j < img.getHeight(); j++) 
                PixelArray[i][j] = img.getRGB(i, j); 
        
        return PixelArray;
    }
    
    public static RGBPixelArray getRGBArray(BufferedImage img)
    {
        int[][] red = new int[img.getWidth()][img.getHeight()], 
                green = new int[img.getWidth()][img.getHeight()],
                blue = new int[img.getWidth()][img.getHeight()];
        boolean grayscale = true;
        int gscount = 0;
        for(int i = 0; i < img.getWidth(); i++)            
            for(int j = 0; j < img.getHeight(); j++)
            {
                int intRGB = img.getRGB(i,j);
                red[i][j] = (intRGB >> 16) & 0xFF;
                green[i][j] = (intRGB >> 8) & 0xFF;
                blue[i][j] = intRGB & 0xFF;
                if(grayscale && (red[i][j] != green[i][j] || red[i][j] != blue[i][j] || green[i][j] != blue[i][j])) grayscale = false;
            }
        
        if(grayscale) return new RGBPixelArray(red);
        
        try {
            return new RGBPixelArray(red, green, blue);
        } catch (Exception ex) { System.out.println("This shouldn't happen Here."); System.exit(0); }
        return null;
    }
    
    public static BufferedImage getBufferedImage(RGBPixelArray pixelArray)
    {
        BufferedImage img;
        if(!pixelArray.isGrayscaleImage()) img = new BufferedImage(pixelArray.getWidth(), pixelArray.getLength(), BufferedImage.TYPE_INT_RGB);
        else img = new BufferedImage(pixelArray.getWidth(), pixelArray.getLength(), BufferedImage.TYPE_INT_RGB);
        int[][] red = pixelArray.getRed();
        int[][] green = pixelArray.getGreen();
        int[][] blue = pixelArray.getBlue();
        int[][] gsValue = pixelArray.getGrayscaleValues();
        for(int i = 0; i < pixelArray.getWidth(); i++)
            for(int j = 0; j < pixelArray.getLength(); j++)
            {
                if(pixelArray.isGrayscaleImage()) img.setRGB(i, j, new RGBObject(gsValue[i][j], gsValue[i][j], gsValue[i][j]).getIntRGB());
                else img.setRGB(i, j, new RGBObject(red[i][j], green[i][j], blue[i][j]).getIntRGB());
            }
        
        return img;
    }
    
    public static BufferedImage getBufferedImage(int[][] PixelArray)
    {
        BufferedImage img = new BufferedImage(PixelArray.length, PixelArray[0].length, BufferedImage.TYPE_INT_RGB);
        for(int x = 0; x < PixelArray.length; x++)
            for(int y = 0; y < PixelArray[x].length; y++)
                img.setRGB(x, y, PixelArray[x][y]);
        
        return img;
    }
    
    public static void SaveImage(RGBPixelArray PixelArray, String format, File file)
    {
        System.out.println("Saving Image to " + file.getAbsolutePath());
        try { 
            if(format != "jpeg") System.out.println(ImageIO.write(getBufferedImage(PixelArray), format, new File(file.getAbsolutePath() + "." + format))); 
            else saveJPEG(PixelArray, file);
        }
        catch (IOException ex) { GUIUtilities.errorMessage("There was an error in saving your image", false);}
    }
    
    private static void saveJPEG(RGBPixelArray PixelArray, File file)
    {
        try {
            ImageOutputStream ios = ImageIO.createImageOutputStream(new File(file.getAbsolutePath() + ".jpeg"));
            Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpeg");
            ImageWriter writer = iter.next();
            ImageWriteParam iwp = writer.getDefaultWriteParam();
            iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            iwp.setCompressionQuality(1f);
            writer.setOutput(ios);
            writer.write(null, new IIOImage(getBufferedImage(PixelArray), null, null),iwp);
            writer.dispose();
        } catch (IOException ex) {
            Logger.getLogger(ImageUtilities.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void SaveImage(BufferedImage img, String format, File file)
    {
        try { ImageIO.write(img, format, new File(file.getAbsolutePath() + "." + format)); }
        catch (IOException ex) { GUIUtilities.errorMessage("There was an error in saving your image", false); }
    }
    
    public static RGBPixelArray normalize(RGBPixelArray rgbImage, int bitDepth)
    {
        if(rgbImage.isGrayscaleImage()) 
        {
            return new RGBPixelArray(normalize(rgbImage.getGrayscaleValues(), bitDepth));
        }
        int[][] red = rgbImage.getRed();
        int[][] green = rgbImage.getGreen();
        int[][] blue = rgbImage.getBlue();
        return new RGBPixelArray(normalize(red, bitDepth), normalize(green, bitDepth), normalize(blue, bitDepth));
    }
    
    public static int[][] normalize(int[][] image, int bitDepth)
    {
        int max = (int)Math.pow(2, bitDepth) - 1;
        int min = 0;
        
        int maxFound = Integer.MIN_VALUE;
        int minFound = Integer.MAX_VALUE;
        for (int i = 0; i < image.length; i++)
            for (int j = 0; j < image[i].length; j++) {
                if(image[i][j] > maxFound) maxFound = image[i][j];
                if(image[i][j] < minFound) minFound = image[i][j];
            }
        
        int[][] newImage = new int[image.length][image[0].length];
        int shift = (min - minFound);
        for (int i = 0; i < image.length; i++)
            for (int j = 0; j < image[i].length; j++)
                newImage[i][j] = (int)Math.round((double)(shift + image[i][j]) * ((double)max / (double) (maxFound + shift)));
        
        return newImage;
    }
    
    public static int[][] normalize(double[][] image, int bitDepth)
    {
        int max = (int)Math.pow(2, bitDepth) - 1;
        int min = 0;
        
        double maxFound = Integer.MIN_VALUE;
        double minFound = Integer.MAX_VALUE;
        for (int i = 0; i < image.length; i++)
            for (int j = 0; j < image[i].length; j++) {
                if(image[i][j] > maxFound) maxFound = image[i][j];
                if(image[i][j] < minFound) minFound = image[i][j];
            }
        
        int[][] newImage = new int[image.length][image[0].length];
        double shift = (min - minFound);
        for (int i = 0; i < image.length; i++)
            for (int j = 0; j < image[i].length; j++)
                newImage[i][j] = (int)Math.round((double)(shift + image[i][j]) * ((double)max / (double) (maxFound + shift)));
        
        return newImage;
    }
}
