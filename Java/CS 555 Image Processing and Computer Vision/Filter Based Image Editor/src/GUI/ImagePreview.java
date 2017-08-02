/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *//*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

package GUI;

import ImageProcessor.ImageUtilities;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.*;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;

/* ImagePreview.java by FileChooserDemo2.java. */
public class ImagePreview extends JComponent
                          implements PropertyChangeListener {
    ImageIcon thumbnail = null;
    File file = null;
    JIconPanel jIP;
    JLabel metaDataLabel1;
    JLabel metaDataLabel2;
    JLabel metaDataLabel3;

    public ImagePreview(JFileChooser fc) {
//        BufferedImage bi = ImageUtilities.getBufferedImage(ImageUtilities.loadImage(new File("C:\\Users\\Matthew Lai\\Pictures\\Sasha.jpg")));
//        BufferedImage bi = ImageUtilities.getBufferedImage(ImageUtilities.loadImage(new File(System.getProperty("user.dir"))));
//        jIP = new JIconPanel(new ImageIcon(bi.getScaledInstance(180, -1, Image.SCALE_SMOOTH)));
        jIP = new JIconPanel(thumbnail);
        jIP.setSize(new Dimension(200,100));
        
        jIP.setAlignmentX(Component.CENTER_ALIGNMENT);
        jIP.setAlignmentY(Component.CENTER_ALIGNMENT);
        
        setPreferredSize(new Dimension(200, 150));
        fc.addPropertyChangeListener(this);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(jIP);
//        JLabel testLabel = new JLabel("TEST");
        metaDataLabel1 = new JLabel(" ");
        metaDataLabel1.setAlignmentX(LEFT_ALIGNMENT);
        metaDataLabel1.setAlignmentY(LEFT_ALIGNMENT);
        this.add(metaDataLabel1);
        metaDataLabel2 = new JLabel(" ");
        metaDataLabel2.setAlignmentX(LEFT_ALIGNMENT);
        metaDataLabel2.setAlignmentY(LEFT_ALIGNMENT);
        this.add(metaDataLabel2);
        metaDataLabel3 = new JLabel(" ");
        metaDataLabel3.setAlignmentX(LEFT_ALIGNMENT);
        metaDataLabel3.setAlignmentY(LEFT_ALIGNMENT);
        this.add(metaDataLabel3);
    }

    public void loadImage() {
        if (file == null) {
            thumbnail = null;
            metaDataLabel1.setText(" ");
            metaDataLabel2.setText(" ");
            metaDataLabel3.setText(" ");
            return;
        }

        //Don't use createImageIcon (which is a wrapper for getResource)
        //because the image we're trying to load is probably not one
        //of this program's own resources.
        ImageIcon tmpIcon = new ImageIcon(file.getPath());
        try {
            ImageInputStream iis = ImageIO.createImageInputStream(file);
            Iterator<ImageReader> iRs = ImageIO.getImageReaders(iis);
            ImageReader iR = iRs.next();
//            System.out.println(iR.getFormatName());
            metaDataLabel1.setText("Type: " + iR.getFormatName());
            metaDataLabel2.setText(tmpIcon.getIconWidth() + " by " + tmpIcon.getIconHeight());
            metaDataLabel3.setText("Size: " + sizeFormat(file.length()));
        } catch (IOException ex) { Logger.getLogger(ImagePreview.class.getName()).log(Level.SEVERE, null, ex); }
        
        if (tmpIcon != null) {
            if (tmpIcon.getIconWidth() > 190) {
                thumbnail = new ImageIcon(tmpIcon.getImage().
                                          getScaledInstance(190, -1,
                                                      Image.SCALE_SMOOTH));
            } else { //no need to miniaturize
                thumbnail = tmpIcon;
            }
        }
    }
    
    private String sizeFormat(long size)
    {
        float fileSize = size;
        int typeIndex = 0;
        String []types = {"B","KB","MB","GB","TB","PB","EB","ZB","YB"};
        while(fileSize > 1024 && typeIndex < 10)
        {
            fileSize  = fileSize/1024;
            typeIndex++;
        }
        String retString = "" + String.format("%.2f",fileSize) + " " + types[typeIndex];
        return retString;
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        boolean update = false;
        String prop = e.getPropertyName();

        //If the directory changed, don't show an image.
        if (JFileChooser.DIRECTORY_CHANGED_PROPERTY.equals(prop)) {
            file = null;
            update = true;

        //If a file became selected, find out which one.
        } else if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(prop)) {
            file = (File) e.getNewValue();
            update = true;
        }

        //Update the preview accordingly.
        if (update) {
            thumbnail = null;
            if (isShowing()) {
                loadImage();
                repaint();
            }
        }
    }
   /** Modify to Display:
    * Image Type
    * Image Size
    * Image Dimension
    */
    @Override
    protected void paintComponent(Graphics g) {
        if (thumbnail == null) {
            jIP.changeImage(null);
            loadImage();
        }
        if (thumbnail != null) {
            int x = getWidth()/2 - thumbnail.getIconWidth()/2;
            int y = getHeight()/2 - thumbnail.getIconHeight()/2;

            if (y < 0) {
                y = 0;
            }

            if (x < 5) {
                x = 5;
            }
//            thumbnail.paintIcon(this, g, x, y);
            
//            BufferedImage bi = ImageUtilities.getBufferedImage(ImageUtilities.loadImage(file));
//            bi.getScaledInstance(190, -1, Image.SCALE_SMOOTH);
            jIP.changeImage(thumbnail);
        }
    }
}
