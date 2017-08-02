/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P2Filters;

import FilterInterface.ImageFilter;
import FilterUtilities.SpatialFilterUtil;
import GUI.HistogramChart;
import GUI.Project2GUI;
import ImageProcessor.HistogramGen;
import ImageProcessor.ImageUtilities;
import SegmentationProject.ColorEdgeExtraction;
import Utilities.OpenCV;
import Utilities.RGBPixelArray;
import java.io.File;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Matthew Lai
 */
public class Project2 extends ImageFilter {
    
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    
    
    public static int[][] roberts(Mat img)
    {
        int[][]filter1 = {{1,0},{0,-1}};
        int[][]filter2 = {{0,1},{-1,0}};
        RGBPixelArray imgCpy = OpenCV.convertMat2GS(img);
        int[][] filtImg1 = SpatialFilterUtil.correlation(imgCpy.getGrayscaleValues(), filter1);
        int[][] filtImg2 = SpatialFilterUtil.correlation(imgCpy.getGrayscaleValues(), filter2);
        int[][] retImg = new int[filtImg1.length][filtImg1[0].length];
        for (int i = 0; i < filtImg1.length; i++) {
            for (int j = 0; j < filtImg1[i].length; j++) {
                retImg[i][j] = (int)Math.round(Math.sqrt(Math.pow(filtImg1[i][j], 2)+Math.pow(filtImg2[i][j], 2)));
            }
        }
        return retImg;
    }
    
    public static int[][] prewitt(Mat img)
    {
        int[][]filter1 = {{-1,0,1},{-1,0,1},{-1,0,1}};
        int[][]filter2 = {{-1,-1,-1},{0,0,0},{1,1,1}};
        RGBPixelArray imgCpy = OpenCV.convertMat2GS(img);
        int[][] filtImg1 = SpatialFilterUtil.correlation(imgCpy.getGrayscaleValues(), filter1);
        int[][] filtImg2 = SpatialFilterUtil.correlation(imgCpy.getGrayscaleValues(), filter2);
        int[][] retImg = new int[filtImg1.length][filtImg1[0].length];
        for (int i = 0; i < filtImg1.length; i++) {
            for (int j = 0; j < filtImg1[i].length; j++) {
                retImg[i][j] = (int)Math.round(Math.sqrt(Math.pow(filtImg1[i][j], 2)+Math.pow(filtImg2[i][j], 2)));
            }
        }
        
        return retImg;
    }
    
    public static int[][] LoG(Mat img)
    {
        Mat newImg = new Mat(img.size(), img.type());
        Imgproc.GaussianBlur(img, newImg, new Size(3,3), 0, 0, Core.BORDER_DEFAULT);
        Imgproc.Laplacian(newImg, newImg, CvType.CV_32S, 5, 1, 0, Core.BORDER_DEFAULT);
        int[][] retImg = OpenCV.convertMat2GS(newImg).getGrayscaleValues();
        return retImg;
    }
    
    public static int[][] sobel(Mat img)
    {
        Mat newImg = new Mat(img.size(), img.type());
        
        int[][]filter1 = {{1,2,1},{0,0,0},{-1,-2,-1}};
        int[][]filter2 = {{1,0,-1},{2,0,-2},{1,0,-1}};
        RGBPixelArray imgCpy = OpenCV.convertMat2GS(img);
        int[][] filtImg1 = SpatialFilterUtil.correlation(imgCpy.getGrayscaleValues(), filter1);
        int[][] filtImg2 = SpatialFilterUtil.correlation(imgCpy.getGrayscaleValues(), filter2);
        int[][] retImg = new int[filtImg1.length][filtImg1[0].length];
        for (int i = 0; i < filtImg1.length; i++) {
            for (int j = 0; j < filtImg1[i].length; j++) {
                retImg[i][j] = (int)Math.round(Math.sqrt(Math.pow(filtImg1[i][j], 2)+Math.pow(filtImg2[i][j], 2)));
            }
        }
        return retImg;
    }
    
    public static int[][] canny(Mat img, double lowerThreshhold, double higherThreshold)
    {
        Mat newImg = new Mat(img.size(), CvType.CV_8U);
        Imgproc.GaussianBlur(img, newImg, new Size(3,3), 0, 0, Core.BORDER_DEFAULT);
        newImg.convertTo(newImg, CvType.CV_8U);
        Imgproc.Canny(newImg, newImg, lowerThreshhold, higherThreshold, 3, false);
        int[][] retImg = OpenCV.convertMat2GS(newImg).getGrayscaleValues();
        return retImg;
    }
    
    public static int[][] threshold(int[][] img, int threshold)
    {
        int[][] ret = new int[img.length][img[0].length];
        for (int i = 0; i < ret.length; i++) {
            for (int j = 0; j < ret[i].length; j++) {
//                if(img[i][j] > threshold) ret[i][j] = 1;
                if(img[i][j] > threshold) ret[i][j] = img[i][j];
                else ret[i][j] = 0;
            }
        }
        return ret;
    }
    
    public static double RMS(int[][] foundImage, int[][] idealImage)
    {
        if(foundImage.length != idealImage.length || foundImage[0].length != idealImage[0].length) throw new Error("Mismatched image lengths.");
        double n = foundImage.length * foundImage[0].length;
        double sum = 0;
        for (int i = 0; i < idealImage.length; i++) {
            for (int j = 0; j < idealImage[i].length; j++) {
                sum += Math.round(Math.pow(foundImage[i][j]-idealImage[i][j], 2));
            }
        }
        return Math.sqrt(sum/n);
    }
    
    public static double FOM(int[][] foundImage, int[][] idealImage, double alpha)
    {
        if(foundImage.length != idealImage.length || foundImage[0].length != idealImage[0].length) throw new Error("Mismatched image lengths.");
//        double alpha = 1;
        int Ii = 0;
        int If = 0;
        double distSum = 0;
        for (int i = 0; i < idealImage.length; i++) 
            for (int j = 0; j < idealImage[i].length; j++) 
            {
                if(idealImage[i][j] != 0) Ii++;
                if(foundImage[i][j] != 0) 
                {
                    If++;
                    distSum += 1.0 / (1.0 + (alpha * Math.pow(dist2ICB(idealImage, i, j), 2)));
                }
            }
        
        return distSum/(double)Math.max(Ii, If);
    }
    
    // Chessboard distance to nearest non-zero pixel in the ideal edgemap
    public static double dist2ICB(int[][] idealImage, int x, int y)
    {
        int dist = 0;
        while((x-dist < 0 || y-dist < 0 || idealImage[x-dist][y-dist] == 0) &&
                (y-dist < 0 || idealImage[x][y-dist] == 0) && 
                (x+dist >= idealImage.length || y-dist < 0 || idealImage[x+dist][y-dist] == 0) &&
                (x-dist < 0 || idealImage[x-dist][y] == 0) &&
                (x+dist >= idealImage.length || idealImage[x+dist][y] == 0) &&
                (x-dist < 0 || y+dist >= idealImage[0].length || idealImage[x-dist][y+dist] == 0) &&
                (y+dist >= idealImage[0].length || idealImage[x][y+dist] == 0) &&
                (x+dist >= idealImage.length || y+dist >= idealImage[0].length || idealImage[x+dist][y+dist] == 0))
            dist++;
        
        return dist;
    }
    
    @Override
    public RGBPixelArray filterImage(RGBPixelArray original) {
        Project2GUI p2GUI = new Project2GUI(null, true, original);
        p2GUI.setVisible(true);
        // Detect GS/RGB
        // Load Human Segmentation
        // Peform Checks (Same Image Size, etc)
        // Load GUI
            // Allow Grayscale-ification
            // Allow Selection of Edge Detector
            // Perform Edge Detection
            // Display FOM & RMS

        if(p2GUI.done())
            return p2GUI.finalImage();
        return null;
    }

    @Override
    public String getFilterName() {
        return "Edge Detectors";
    }

    @Override
    public String getFilterDecription() {
        return "Allows User to try different edge detectors and benchmark against human segmentation.";
    }

    @Override
    public String getFilterCategory() {
        return "Project 2";
    }
    
}
