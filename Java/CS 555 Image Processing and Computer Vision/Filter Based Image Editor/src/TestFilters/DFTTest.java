/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestFilters;

import FilterInterface.ImageFilter;
import Utilities.RGBPixelArray;
import javax.swing.JOptionPane;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.CvType;
import org.opencv.core.Range;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;

/**
 *
 * @author Matthew Lai
 */
public class DFTTest extends ImageFilter {
    
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    
//    public static void main(String[] args) {
//        Core.dft(null, null);
//    }
    
    private void shiftQuadrants(Mat matrix, boolean inverse)
    {
        int cx = (int)Math.ceil(matrix.cols()/2.0);
        int cy = (int)Math.ceil(matrix.rows()/2.0);
        if(inverse)
        {
            cx = (int)Math.floor(matrix.cols()/2);
            cy = (int)Math.floor(matrix.rows()/2);
        }
        
        Mat q0 = new Mat(matrix, new Rect(0, 0, cx, cy));
        Mat q1 = new Mat(matrix, new Rect(cx, 0, cx, cy));
        Mat q2 = new Mat(matrix, new Rect(0, cy, cx, cy));
        Mat q3 = new Mat(matrix, new Rect(cx, cy, cx, cy));
        
        Mat tmp = new Mat(q0.size(), q0.type());
        q0.copyTo(tmp);
        q3.copyTo(q0);
        tmp.copyTo(q3);
        
        q1.copyTo(tmp);
        q2.copyTo(q1);
        tmp.copyTo(q2);
    }

    @Override
    public RGBPixelArray filterImage(RGBPixelArray original) {
        
        Mat originalMat = new Mat(original.getWidth(), original.getLength(), CvType.CV_32F);
//        originalMat.put(row, col, data)
        int[][] gsV = original.getGrayscaleValues();
        if(gsV == null) 
        {
            JOptionPane.showMessageDialog(null,"Requires Grayscale Image.", "Error", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
        for (int row = 0; row < gsV.length; row++) {
            for (int column = 0; column < gsV[row].length; column++) {
                originalMat.put(row, column, (double)gsV[row][column]);
            }
        }
        
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
        
        // Perform Stuff on MagI
        int rowWiseMax = magI.height() - 1;
        int colWiseMax = magI.width() - 1;
        
        int r = 20;
        
        for (int i = 0; i < r; i++)
            for (int j = 0; j < r; j++) 
            {
                magI.put(0+i, 0+j, 0);
                magI.put(0+i, colWiseMax-j, 0);
                magI.put(rowWiseMax-i, colWiseMax-j, 0);
                magI.put(rowWiseMax-i, 0+j, 0);
            }
        
        Core.polarToCart(magI, phI, planes.get(0), planes.get(1));
        Core.merge(planes, newMat);
        
        Mat invDFT = new Mat(originalMat.size(), originalMat.type());
//        Mat invDFTcvt = new Mat(originalMat.size(),CvType.CV_32F);
        
        Core.idft(newMat, invDFT, Core.DFT_INVERSE + Core.DFT_SCALE, 0);
//        invDFT.convertTo(invDFTcvt, CvType.CV_32F);
        
        
        
//        Core.split(newMat, planes);
//        Core.magnitude(planes.get(0), planes.get(1), planes.get(0));
        
//        Mat magI = planes.get(0);
        
        int rowLen = invDFT.rows();
        int colLen = invDFT.cols();
//        int[][] newGSV = new int[gsV.length][gsV[0].length];
//        for (int row = 0; row < gsV.length; row++) {
//            for (int column = 0; column < gsV[row].length; column++) {
//                try {
//                newGSV[row][column] = (int)originalMat.get(row, column)[0];
////                    System.out.println(row + " " + column + " " + originalMat.get(row, column)[0]);
//                } catch (NullPointerException ex)
//                {
//                    System.out.println("Null Pointer Exception at: " + row + " " + column);
//                    continue;
//                }
//            }
//        }
        int[][] newGSV = new int[rowLen][colLen];
        for (int row = 0; row < rowLen; row++) {
            for (int column = 0; column < colLen; column++) {
                try {
                    newGSV[row][column] = (int)invDFT.get(row, column)[0];
//                    System.out.println(row + " " + column + " " + originalMat.get(row, column)[0]);
                } catch (NullPointerException ex)
                {
                    System.out.println("Null Pointer Exception at: " + row + " " + column);
                    continue;
                }
            }
        }
        newGSV = ImageProcessor.ImageUtilities.normalize(newGSV, 8);
        return new RGBPixelArray(newGSV);
    }

    @Override
    public String getFilterName() {
        return "TestFilter";
    }

    @Override
    public String getFilterDecription() {
        return "A Filter to test new stuff";
    }

    @Override
    public String getFilterCategory() {
        return "Project 1";
    }
}
