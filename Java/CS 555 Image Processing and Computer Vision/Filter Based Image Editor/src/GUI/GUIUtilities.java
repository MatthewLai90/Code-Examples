/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
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
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Matthew Lai
 */
public final class GUIUtilities {
    
    public static File getImageFileDialog(Component parent)
    {
        ThumbnailFileChooser fc = new ThumbnailFileChooser();
        fc.addChoosableFileFilter(new ImageFilter());
        fc.setAcceptAllFileFilterUsed(false);
        
//        fc.setFileView(new ImageFileView(fc));
        
        fc.setAccessory(new ImagePreview(fc));
        
        int returnVal = fc.showOpenDialog(parent);
        if(returnVal == 0) return fc.getSelectedFile();
        return null;
    }
    
    public static File getImageFileDialog(Component parent, String name)
    {
        ThumbnailFileChooser fc = new ThumbnailFileChooser();
        fc.addChoosableFileFilter(new ImageFilter());
        fc.setAcceptAllFileFilterUsed(false);
        fc.setDialogTitle(name);
//        fc.setFileView(new ImageFileView(fc));
        
        fc.setAccessory(new ImagePreview(fc));
        
        int returnVal = fc.showOpenDialog(parent);
        if(returnVal == 0) return fc.getSelectedFile();
        return null;
    }
    
    public static Utilities.FileObject saveFileDialog(Component parent)
    {
        JFileChooser fc = new JFileChooser();
        
        fc.addChoosableFileFilter(new FileNameExtensionFilter("GIF Image Format", "gif"));
        fc.addChoosableFileFilter(new FileNameExtensionFilter("JPEG Image Format", "jpg", "jpeg"));
        fc.addChoosableFileFilter(new FileNameExtensionFilter("PNG Image Format", "png"));
        fc.setSelectedFile(new File("Untitled"));
        fc.setFileFilter(fc.getChoosableFileFilters()[2]);
        
        int returnVal = fc.showSaveDialog(parent);
        if(returnVal != 0) return null;
        String ext = ""; 
        String extension = fc.getFileFilter().getDescription();
        switch(extension)
        {
            case "GIF Image Format": ext = "gif";
                break;
            case "JPEG Image Format": ext = "jpeg";
                break;
            case "PNG Image Format": ext = "png";
                break;
            case "TIFF Image Format": ext = "tiff";
                break;
        }
        
        return new Utilities.FileObject(fc.getSelectedFile(), ext);
    }
    
    public static JImagePanel getImagePanel(RGBPixelArray pixelArray)
    {
        JImagePanel imagePanel = new JImagePanel(pixelArray);
        imagePanel.setPreferredSize(new Dimension(pixelArray.getWidth(),pixelArray.getLength()));
        return imagePanel;
    }
    
    public static JImageScrollPane getScrollWindow(JImagePanel imagePanel)
    {
        
        JImageScrollPane jSP = new JImageScrollPane(imagePanel);
        jSP.setPreferredSize(imagePanel.getPreferredSize());

        jSP.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
        jSP.getHorizontalScrollBar().setPreferredSize(new Dimension(0,0));
        jSP.setWheelScrollingEnabled(false);
        
        MouseScrollingHandler.addMouseScrolling(jSP);
        
        return jSP;
    }

    public static void errorMessage(String errorMessage, boolean exit) {
         JOptionPane.showMessageDialog(new JFrame(), errorMessage,"Error",JOptionPane.INFORMATION_MESSAGE);
         if(exit) System.exit(1);
    }
}

class ImageFilter extends FileFilter {

    //Accept all directories and all gif, jpg, tiff, or png files.
    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = Utils.getExtension(f);
        if (extension != null) {
            return extension.equals(Utils.tiff) ||
                    extension.equals(Utils.tif) ||
                    extension.equals(Utils.gif) ||
                    extension.equals(Utils.jpeg) ||
                    extension.equals(Utils.jpg) ||
                    extension.equals(Utils.png);
        }

        return false;
    }

    //The description of this filter
    @Override
    public String getDescription() {
        return "*.tiff, *.tif, *.gif, *.jpeg, *.jpg, *.png";
    }
}