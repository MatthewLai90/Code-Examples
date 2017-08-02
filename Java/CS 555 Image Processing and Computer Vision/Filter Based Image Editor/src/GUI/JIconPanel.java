/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import ImageProcessor.ImageUtilities;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Matthew Lai
 */
public class JIconPanel extends JPanel {
    
    ImageIcon image;
    Graphics superG;
    public JIconPanel(ImageIcon ii)
    {
        super();
        superG = null;
        image = ii;
    }
    
    public void changeImage(ImageIcon img)
    {
        image = img;
        repaint();
//        super.paintComponent(superG);
//        superG.drawImage(ImageUtilities.getBufferedImage(imgArray), 0, 0, null);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(image == null) 
        {
            g.drawImage(null, 0, 0, null);
            return;
        }
        Graphics2D g2d = (Graphics2D) g;
        int x = (this.getWidth() - image.getIconWidth()) / 2;
        int y = (this.getHeight() - image.getIconHeight()) / 2;
        superG = g;
        if(image != null) g.drawImage(image.getImage(), x, y, null);
    }
}