/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SegmentationProject;

import FilterInterface.ImageFilter;
import Utilities.OpenCV;
import Utilities.RGBPixelArray;
import java.util.Arrays;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Matthew Lai
 */
public class ColorEdgeExtraction extends ImageFilter {
    
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    @Override
    public RGBPixelArray filterImage(RGBPixelArray original) {
        
//        int[][] testFilterH = {{-1,-2,-1},{0,0,0},{1,2,1}};
//        int[][] testFilterV = {{-1,0,1},{-2,0,2},{-1,0,1}};
//        int[][] ret = SpatialFilterUtil.convolution(original.getGrayscaleValues(), testFilterV);
//        ret = normalize(ret, 8);
//        boolean test = true;
//        if(test) return new RGBPixelArray(ret);
//        Mat newImg = new Mat(original.getWidth(), original.getLength(), CvType.CV_32FC3);
        Mat newImg = OpenCV.convertRGBPA2Mat(original);
        Imgproc.cvtColor(newImg, newImg, Imgproc.COLOR_BGR2YUV);
//        Imgproc.cvtColor(newImg, newImg, Imgproc.COLOR_YUV2BGR);
        int YUV[][][] = new int[3][newImg.rows()][newImg.cols()];
        for (int i = 0; i < YUV[0].length; i++) 
            for (int j = 0; j < YUV[0][i].length; j++) 
            {
                YUV[0][i][j] = (int)(newImg.get(i, j)[0]*255);
                YUV[1][i][j] = (int)(newImg.get(i, j)[1]*255);
                YUV[2][i][j] = (int)(newImg.get(i, j)[2]*255);
            }
        
        int[][] retImg = new int[newImg.rows()][newImg.cols()];
        
        int[][] hoeFilter = {{1,2,1},{0,0,0},{-1,-2,-1}};
        int[][] voeFilter = {{1,0,-1},{2,0,-2},{1,0,-1}};
        int[][] noeFilter = {{2,1,0},{1,0,-1},{0,-1,-2}};
        int[][] soeFilter = {{0,1,2},{-1,0,1},{-2,-1,0}};
        for (int i = 0; i < YUV.length; i++) {
            
            int[] f = new int[1000];
            int threshold;
            int M = 0;
            
            int[][]moe = new int[newImg.rows()][newImg.cols()];
            
            int[][]hoe = weightedSum(YUV[i], hoeFilter);
            int[][]voe = weightedSum(YUV[i], voeFilter);
            int[][]noe = weightedSum(YUV[i], noeFilter);
            int[][]soe = weightedSum(YUV[i], soeFilter);
            
            for (int j = 0; j < moe.length; j++) {
                for (int k = 0; k < moe[i].length; k++) {
                    moe[j][k] = Math.max(hoe[j][k], voe[j][k]);
                    moe[j][k] = Math.max(moe[j][k], noe[j][k]);
                    moe[j][k] = Math.max(moe[j][k], soe[j][k]);
                    if(moe[j][k] > M) M = moe[j][k];
                    f = inc(f, moe[j][k]);
                }
            }
//            int sum = 0;
//            for (int j = 0; j <= M; j++) sum+= ++f[j];
            
            //Calculate Threshold
            threshold = testThreshold(f, M, moe.length * moe[i].length);
//            threshold = threshold(f, M, sum);
//            System.out.println("Threshold: " + threshold);
//            threshold = 60;
            
            for (int j = 0; j < moe.length; j++) 
                for (int k = 0; k < moe[i].length; k++) 
                    if(moe[j][k] >= threshold) retImg[j][k] = 1;
            
        }
        return new RGBPixelArray(retImg);
//        return OpenCV.convertMat2RGBPA(newImg);
    }
    
    public static int testThreshold(int[] f, int M, int totalPixels)
    {
        double max = Integer.MIN_VALUE;
        int maxI = -1;
        double track = 0;
        for (int T = 0; T <= M; T++) {
            track += f[T];
            double Hn = 0;
            double He = 0;
            for (int i = 0; i <= T; i++) {
                Hn += (((double)f[i]+1)/(track+T))*Math.log(((double)f[i]+1)/(track+T));
            }
            Hn = -Hn;
            for (int i = T+1; i <= M; i++) {
                He += (((double)f[i]+1)/(totalPixels-track+M-(T+1)))*Math.log(((double)f[i]+1)/(totalPixels-track+M-(T+1)));
            }
            He = -He;
//            System.out.println(T + ": " + (He + Hn));
            if((He + Hn) > max)
            {
                max = (He + Hn);
                maxI = T;
            }
        }
        return maxI;
    }
    
    private int threshold(int[] f, int M, int totalPixels)
    {
        int threshold;
        int p0T = f[0];
        int p1T = totalPixels - f[0];
        double Hn = -(((double)f[0])/((double)p0T))*Math.log(((double)f[0])/((double)p0T));
        double He = -(((double)f[0])/((double)p1T))*Math.log(((double)f[0])/((double)p1T));
        int maxT = 0;
        double maxHt = Hn + He;
//            System.out.println("0: " + maxHt);

        for (int T = 0; T < M; T++) {
            int p0T_new = p0T + f[T+1];
            int p1T_new = p1T - f[T+1];

            Hn = ((double)p0T/(double)p0T_new)*Hn - ((double)f[T+1]/(double)p0T_new)*Math.log((double)f[T+1]/(double)p0T_new) - ((double)p0T/(double)p0T_new)*Math.log((double)p0T/(double)p0T_new);
            He = ((double)p1T/(double)p1T_new)*He + ((double)f[T+1]/(double)p1T_new)*Math.log((double)f[T+1]/(double)p1T_new) - ((double)p1T/(double)p1T_new)*Math.log((double)p1T/(double)p1T_new);
//                System.out.println(Hn);
            if(!Double.isNaN(Hn+He)) System.out.println((Hn+He));
//                if(Double.isNaN(Hn+He)) System.out.println("T: " + T + "\t\tHn: " + Hn + "\t\tHe: " + He + "\t\tp0T: " + p0T + "\t\tp1T: " + p1T + "\t\tp0T_new: " + p0T_new + "\t\tp1T_new: " + p1T_new + "\t\tf[T+1]: " + f[T+1]);
            if((Hn + He) > maxHt)
            {
                maxHt = (Hn + He);
                maxT = T+1;
            }
//                System.out.println((T+1) + ":" + (Hn + He));

            p0T = p0T_new;
            p1T = p1T_new;
        }

        threshold = maxT;
        System.out.println("Threshold: " + threshold);
        return threshold;
    }
    
    private int[] inc(int[] array, int index){
        if(index >= array.length) 
            return inc(Arrays.copyOf(array, array.length * 2), index);
        
        else {
            array[index]++;
            return array;
        }
    }
    
    public static int[][] weightedSum(int[][] image, int[][] filter)
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
                retImage[i][j] = Math.abs(sum);
            }
        }
        return retImage;
    }

    @Override
    public String getFilterName() {
        return "Color Edge Extraction";
    }

    @Override
    public String getFilterDecription() {
        return "Performs Project Color Edge Extraction Algorithm and Returns Normalized Image.";
    }

    @Override
    public String getFilterCategory() {
        return "ProjectFilters";
    }
    
}
