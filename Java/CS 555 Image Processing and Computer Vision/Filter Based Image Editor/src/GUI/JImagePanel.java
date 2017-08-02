/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import ImageProcessor.ImageUtilities;
import Utilities.RGBPixelArray;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Matthew Lai
 */
public class JImagePanel extends JPanel {
    
    RGBPixelArray imageArray;
    Graphics superG;
    public JImagePanel(RGBPixelArray imgArray)
    {
        super();
        superG = null;
        imageArray = imgArray;
    }
    
    @Override
    public Graphics getGraphics()
    {
        return superG;
    }
    
    public RGBPixelArray getImage()
    {
        return imageArray;
    }
    
    public void changeImage(RGBPixelArray imgArray)
    {
        imageArray = imgArray;
        setPreferredSize(new Dimension(imageArray.getWidth(),imageArray.getLength()));
        repaint();
//        super.paintComponent(superG);
//        superG.drawImage(ImageUtilities.getBufferedImage(imgArray), 0, 0, null);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                superG = g;
                if(imageArray != null)
                {
                    imageArray = ImageUtilities.normalize(imageArray, 8);
                    g.drawImage(ImageUtilities.getBufferedImage(imageArray), 0, 0, null);
                }
            }
    
}
