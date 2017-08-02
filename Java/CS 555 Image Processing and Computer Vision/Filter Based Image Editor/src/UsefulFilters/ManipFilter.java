/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UsefulFilters;

import FilterInterface.ImageFilter;
import FilterUtilities.RGBObject;
import Utilities.RGBPixelArray;
import javax.swing.JOptionPane;

/**
 *
 * @author Matthew Lai
 */
public class ManipFilter extends ImageFilter {

    @Override
    public RGBPixelArray filterImage(RGBPixelArray original) {
//        int [][] ret = new int[original.getWidth()][original.getLength()];
        
        RGBPixelArray ret = new RGBPixelArray(original.getWidth(), original.getLength(), true);
        int gType = JOptionPane.showOptionDialog(new javax.swing.JFrame(), "What Type of Gray Scaling Do You Want to Try?", "Gray Scale Option", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Lightness","Average","Luminosity"}, "Lightness");
        for(int i = 0; i < original.getWidth(); i++)
            for (int j = 0; j < original.getLength(); j++) {
//                int numberLum = (int)(original.getRed()[i][j]*0.2989 + original.getGreen()[i][j]*.5870 + original.getBlue()[i][j]*.1140);
                int numberLum = (int)(original.getRed()[i][j]*0.21 + original.getGreen()[i][j]*.72+ original.getBlue()[i][j]*.07);
                int numberAvg = (original.getRed()[i][j] + original.getGreen()[i][j] + original.getBlue()[i][j])/3;
                int numberLig = (Math.max(original.getGreen()[i][j], Math.max(original.getRed()[i][j], original.getBlue()[i][j])) + Math.min(original.getGreen()[i][j], Math.min(original.getRed()[i][j], original.getBlue()[i][j])))/2;
                if(gType == 0) ret.setPixelValue(i, j, numberLig);
                if(gType == 1) ret.setPixelValue(i, j, numberAvg);
                if(gType == 2) ret.setPixelValue(i, j, numberLum);
            }
        
        return ret;
//        return new RGBPixelArray(ret);
    }
    
    public int[][] filterImage(int[][] original) {
//        int [][] ret = new int [original.length][original[0].length];
//        for (int i = 0; i < original.length; i++) {
//            for (int j = 0; j < original[0].length; j++) {
////                ret[i][j] = original[i][j] << 25;
//                RGBTuple oRGB = new RGBTuple(original[i][j]);
//                int numberLum = (int)(oRGB.getRed()*.21 + oRGB.getGreen()*.72 + oRGB.getBlue()*.07);
//                int numberAvg = (oRGB.getRed() + oRGB.getGreen() + oRGB.getBlue())/3;
//                int numberLig = (Math.max(oRGB.getGreen(), Math.max(oRGB.getRed(), oRGB.getBlue())) + Math.min(oRGB.getGreen(), Math.min(oRGB.getRed(), oRGB.getBlue())))/2;
//                
//                ret[i][j] = new RGBTuple(numberLum,numberLum,numberLum).getIntRGB();
//            }
//        }
//        JOptionPane optionPane = new JOptionPane("What Type of GrayScaling Do you want to try?", JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, new Object[] {""});
        int intoption = JOptionPane.showOptionDialog(new javax.swing.JFrame(), "What Type of Gray Scaling Do You Want to Try?", "Gray Scale Option", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Lightness","Average","Luminosity"}, "Lightness");
//        System.out.println(intoption);
        return filterImage(original, intoption);
//        return new Utilities.ImageResult(ret,true);
    }
    
    public int[][] filterImage(int[][] original, int gType) {
        int [][] ret = new int [original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            for (int j = 0; j < original[0].length; j++) {
//                ret[i][j] = original[i][j] << 25;
                RGBObject oRGB = new RGBObject(original[i][j]);
                int numberLum = (int)(oRGB.getRed()*0.2989 + oRGB.getGreen()*.5870 + oRGB.getBlue()*.1140);
                int numberAvg = (oRGB.getRed() + oRGB.getGreen() + oRGB.getBlue())/3;
                int numberLig = (Math.max(oRGB.getGreen(), Math.max(oRGB.getRed(), oRGB.getBlue())) + Math.min(oRGB.getGreen(), Math.min(oRGB.getRed(), oRGB.getBlue())))/2;
                
                if(gType == 0)
                    ret[i][j] = new RGBObject(numberLig,numberLig,numberLig).getIntRGB();
                if(gType == 1)
                    ret[i][j] = new RGBObject(numberAvg,numberAvg,numberAvg).getIntRGB();
                if(gType == 2)
                    ret[i][j] = new RGBObject(numberLum,numberLum,numberLum).getIntRGB();
            }
        }
        return ret;
    }

    @Override
    public String getFilterName() {
        return "Grayscale Filter";
    }

    @Override
    public String getFilterDecription() {
        return "Makes the image grayscale.";
    }
    
    @Override
    public String getFilterCategory() {
        return "Useful Filters";
    }

    
}
