/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Range;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;

/**
 *
 * @author Matthew Lai
 */
public class OpenCV {
    
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    
    public static Mat convertRGBPA2Mat(RGBPixelArray img)
    {
        Mat newImg = new Mat(img.getWidth(), img.getLength(), CvType.CV_32FC3);
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getLength(); j++) {
                newImg.put(i, j, new float[]{(float)img.getBlue()[i][j]/255,(float)img.getGreen()[i][j]/255,(float)img.getRed()[i][j]/255});
            }
        }
        
        return newImg;
    }
    
    public static RGBPixelArray convertMat2RGBPA(Mat img)
    {
        int[][] red = new int[img.rows()][img.cols()];
        int[][] green = new int[img.rows()][img.cols()];
        int[][] blue = new int[img.rows()][img.cols()];
        
        for (int i = 0; i < img.rows(); i++) {
            for (int j = 0; j < img.cols(); j++) {
                blue[i][j] = (int)(img.get(i, j)[0]*255);
                green[i][j] = (int)(img.get(i, j)[1]*255);
                red[i][j] = (int)(img.get(i, j)[2]*255);
            }
        }
        
        try { return new RGBPixelArray(red, green, blue); } 
        catch (Exception ex) { System.out.println("Dunno What Happened."); }
        
        return null;
    }
    
    public static Mat convertGS2Mat(RGBPixelArray img)
    {
        Mat newImg = new Mat(img.getWidth(), img.getLength(), CvType.CV_32F);
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getLength(); j++) {
                newImg.put(i, j, img.getGrayscaleValues()[i][j]);
            }
        }
        
        return newImg;
    }
    
    public static RGBPixelArray convertMat2GS(Mat img)
    {
        int[][] gs = new int[img.rows()][img.cols()];
        
        for (int i = 0; i < img.rows(); i++) {
            for (int j = 0; j < img.cols(); j++) {
                gs[i][j] = (int)(img.get(i, j)[0]*255);
            }
        }
        
        try { return new RGBPixelArray(gs); } 
        catch (Exception ex) { System.out.println("Dunno What Happened."); }
        
        return null;
    }
    
    private static Mat shiftQuadrants(Mat matrix, boolean inverse)
    {
        Mat newMat = new Mat(matrix, Range.all());
        int cx = (int)Math.ceil(newMat.cols()/2.0);
        int cy = (int)Math.ceil(newMat.rows()/2.0);
        if(inverse)
        {
            cx = (int)Math.floor(newMat.cols()/2);
            cy = (int)Math.floor(newMat.rows()/2);
        }
        
        Mat q0 = new Mat(newMat, new Rect(0, 0, cx, cy));
        Mat q1 = new Mat(newMat, new Rect(cx, 0, cx, cy));
        Mat q2 = new Mat(newMat, new Rect(0, cy, cx, cy));
        Mat q3 = new Mat(newMat, new Rect(cx, cy, cx, cy));
        
        Mat tmp = new Mat(q0.size(), q0.type());
        q0.copyTo(tmp);
        q3.copyTo(q0);
        tmp.copyTo(q3);
        
        q1.copyTo(tmp);
        q2.copyTo(q1);
        tmp.copyTo(q2);
        
        return newMat;
    }
    
    public static DFTObj shiftMagnitudeQuadrants(DFTObj obj, boolean inverse)
    {
        Mat matrix = obj.getMagnitude();
        Mat newMat = new Mat(matrix, Range.all());
        newMat = shiftQuadrants(newMat, inverse);
        
        return new DFTObj(newMat, obj.getPhase(), obj.getOriginalSize(), obj.getOriginalType(), inverse);
    }
    
    public static RGBPixelArray DFTMagnitudeImage(DFTObj obj)
    {
        Mat mag = new Mat(obj.getMagnitude(), Range.all());
        
        Core.add(mag, Scalar.all(1), mag);
        Core.log(mag, mag);
        
        mag = shiftQuadrants(mag, false);
        
        int[][] image = new int[obj.getHeight()][obj.getWidth()];
        for (int i = 0; i < image.length; i++) 
            for (int j = 0; j < image[i].length; j++) 
                image[i][j] = (int)mag.get(i, j)[0];
        
        image = ImageProcessor.ImageUtilities.normalize(image, 8);
        
        return new RGBPixelArray(image);
    }
    
    public static DFTObj dftForwardTransform(int[][] image)
    {
        Mat matImage = new Mat(image.length, image[0].length, CvType.CV_32F);
        for (int i = 0; i < image.length; i++)
            for (int j = 0; j < image[i].length; j++) 
                matImage.put(i, j, image[i][j]);
        return dftForwardTransform(matImage);
    }
    
    private static DFTObj dftForwardTransform(Mat originalMat)
    {
        java.util.List<Mat> planes = new java.util.ArrayList<>();
        
        planes.add(new Mat(originalMat,Range.all()));
        planes.add(Mat.zeros(originalMat.size(), CvType.CV_32F));
        
        Mat newMat = new Mat(originalMat.size(), CvType.CV_32FC2);
        
        Core.merge(planes, newMat);
        
        Core.dft(newMat, newMat);
        
        Core.split(newMat, planes);
        
        Mat magI = new Mat(planes.get(0).size(), planes.get(0).type());
        Mat phI = new Mat(planes.get(0).size(), planes.get(0).type());
        
        Core.cartToPolar(planes.get(0), planes.get(1), magI, phI);
        
        return new DFTObj(magI, phI, originalMat.size(), originalMat.type());
    }
    
    private static Mat dftInverseTransform(DFTObj obj)
    {
        if(!obj.isTransformable())
        {
            javax.swing.JOptionPane.showMessageDialog(null,"This DFTObject is not yet ready to be transformed. Please shift the quadrants.", "Error", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
        Mat mergedMat = new Mat(obj.getOriginalSize(), obj.getOriginalType());
        List<Mat> planes = new ArrayList<>();
        
        planes.add(new Mat(mergedMat.size(), mergedMat.type()));
        planes.add(new Mat(mergedMat.size(), mergedMat.type()));
        
        Core.polarToCart(obj.getMagnitude(), obj.getPhase(), planes.get(0), planes.get(1));
        Core.merge(planes, mergedMat);
        
        Mat invDFT = new Mat(mergedMat.size(), mergedMat.type());
        
        Core.idft(mergedMat, invDFT, Core.DFT_INVERSE + Core.DFT_SCALE, 0);
        return invDFT;
    }
    
    public static int[][] dftInverseTransformToImage(DFTObj obj)
    {
        Mat mImage = dftInverseTransform(obj);
        double [][] image = new double[mImage.rows()][mImage.cols()];
        for(int i = 0; i < mImage.rows(); i++)
        {
            for (int j = 0; j < mImage.cols(); j++) {
                image[i][j] = mImage.get(i, j)[0];
            }
        }
        return ImageProcessor.ImageUtilities.normalize(image, 8);
    }
    
}
