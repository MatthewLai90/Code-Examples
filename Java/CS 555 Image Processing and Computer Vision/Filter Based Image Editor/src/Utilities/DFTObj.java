/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import javax.swing.JOptionPane;
import org.opencv.core.Mat;
import org.opencv.core.Size;

/**
 *
 * @author Matthew Lai
 */
public class DFTObj {
    
    private Mat amp, phase;
    private Size size;
    private int type;
    private boolean transformable;
    
    protected DFTObj(Mat amp, Mat phase, Size s, int type)
    {
        this.amp = amp;
        this.phase = phase;
        this.size = s;
        this.type = type;
        transformable = true;
    }
    
    protected DFTObj(Mat amp, Mat phase, Size s, int type, boolean transformable)
    {
        this.amp = amp;
        this.phase = phase;
        this.size = s;
        this.type = type;
        this.transformable = transformable;
    }
    
    public DFTObj convolve(double[][] convolutionMatrix)
    {
        if(convolutionMatrix == null || amp.width() != convolutionMatrix[0].length || amp.height() != convolutionMatrix.length)
        {
            JOptionPane.showMessageDialog(null,"Requires convolution matrix to be the same dimensions as the transformed image.", "Error", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
        Mat convoMatrix = new Mat(amp.size(), amp.type());
        for (int i = 0; i < convolutionMatrix.length; i++) 
            for (int j = 0; j < convolutionMatrix[i].length; j++)
                convoMatrix.put(i, j, convolutionMatrix[i][j]);
        Mat newAmp = amp.mul(convoMatrix);
        return new DFTObj(newAmp, phase, size, type, transformable);
    }
    
    /**
     * Indicates whether this object is ready to be transformed. I.E. if the quadrants are properly aligned.
     * @return 
     */
    protected boolean isTransformable()
    {
        return transformable;
    }
    
    protected Size getOriginalSize()
    {
        return size;
    }
    
    protected int getOriginalType()
    {
        return type;
    }
    
    protected Mat getMagnitude()
    {
        return amp;
    }
    
    protected Mat getPhase()
    {
        return phase;
    }
    
    public int getWidth()
    {
        return amp.width();
    }
    
    public int getHeight()
    {
        return amp.height();
    }
    
}
