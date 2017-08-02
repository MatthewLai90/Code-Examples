/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import javax.swing.JScrollPane;

/**
 *
 * @author Matthew Lai
 */
public class JImageScrollPane extends JScrollPane{
    
    JImagePanel jIP;
    
    public JImageScrollPane()
    {
        super();
        jIP = null;
    }
    public JImageScrollPane(JImagePanel jIP)
    {
        super(jIP);
        this.jIP = jIP;
    }
    
    public Utilities.RGBPixelArray getImage()
    {
        return jIP.getImage();
    }
    
    public JImagePanel getImagePanel()
    {
        return jIP;
    }
}
