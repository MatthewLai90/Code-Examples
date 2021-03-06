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
import Utilities.FileObject;
import Utilities.RGBPixelArray;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

/**
 *
 * @author Matthew Lai
 */
public class SingleImageFrame extends javax.swing.JFrame {
    
    /**
     * Creates new form SingleImageFrame
     */
    
    private JImageScrollPane imageScrollPane;
    private Dimension preferredDimensions;
    private FrameController controller;
    public SingleImageFrame(FrameController controller) {
        this.controller = controller;
//        preferredDimensions = new Dimension(640, 360);
        setToBestDimensions();
        imageScrollPane = null;
        this.setTitle("No Image");
        initComponents();
    }
    
    
    public SingleImageFrame(RGBPixelArray image, FrameController controller) {
        this.controller = controller;
        imageScrollPane = GUIUtilities.getScrollWindow(GUIUtilities.getImagePanel(image));
        scrollPane = imageScrollPane;
//        preferredDimensions = imageScrollPane.getSize();
//        this.setSize(preferredDimensions);
        this.setTitle(controller.getNewFrameName());
        initComponents();
        setToBestDimensions();
    }
    
    public JScrollPane getImageScrollPane()
    {
        return imageScrollPane;
    }
    
    private void setToBestDimensions()
    {
        if(imageScrollPane != null) {
            int width = imageScrollPane.getPreferredSize().width+15;
            int height = imageScrollPane.getPreferredSize().height;
            if(width > Toolkit.getDefaultToolkit().getScreenSize().width) width = Toolkit.getDefaultToolkit().getScreenSize().width;
            if(height > Toolkit.getDefaultToolkit().getScreenSize().height-40) height = Toolkit.getDefaultToolkit().getScreenSize().height-40;
            setSize(width, height);
        }
        else setSize(640, 360);
    }
        

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        MenuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        LoadImage = new javax.swing.JMenuItem();
        SaveImage = new javax.swing.JMenuItem();
        ExitApp = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        CloseWindow = new javax.swing.JMenuItem();
        LinkImages = new javax.swing.JCheckBoxMenuItem();
        ImageOps = new javax.swing.JMenu();
        opsMenu = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(preferredDimensions);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        if(imageScrollPane != null) scrollPane = imageScrollPane;

        jMenu1.setText("File");

        LoadImage.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        LoadImage.setText("Load Image");
        LoadImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoadImageActionPerformed(evt);
            }
        });
        jMenu1.add(LoadImage);

        SaveImage.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        SaveImage.setText("Save Image");
        SaveImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveImageActionPerformed(evt);
            }
        });
        jMenu1.add(SaveImage);

        ExitApp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        ExitApp.setText("Exit Application");
        ExitApp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitAppActionPerformed(evt);
            }
        });
        jMenu1.add(ExitApp);

        MenuBar.add(jMenu1);

        jMenu2.setText("Window");

        CloseWindow.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        CloseWindow.setText("Close Window");
        CloseWindow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseWindowActionPerformed(evt);
            }
        });
        jMenu2.add(CloseWindow);

        LinkImages.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        LinkImages.setSelected(false);
        LinkImages.setText("LinkImages");
        LinkImages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LinkImagesActionPerformed(evt);
            }
        });
        jMenu2.add(LinkImages);

        MenuBar.add(jMenu2);

        ImageOps.setText("Image Operations");
        ImageOps.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ImageOpsMouseEntered(evt);
            }
        });

        opsMenu.setText("Operations");
        ImageOps.add(opsMenu);

        MenuBar.add(ImageOps);

        setJMenuBar(MenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) controller.closeFrame(this);
    }//GEN-LAST:event_formKeyReleased

    private void CloseWindowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseWindowActionPerformed
        // TODO add your handling code here:
        controller.closeFrame(this);
    }//GEN-LAST:event_CloseWindowActionPerformed

    private void LoadImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoadImageActionPerformed
        File f = GUIUtilities.getImageFileDialog(this);
        System.out.println(f);
        RGBPixelArray image = ImageUtilities.loadImage(f);
        JImagePanel imagePanel = GUIUtilities.getImagePanel(image);
        if(image == null) return;
//        new SingleImageFrame(image, controller).setVisible(true);
        controller.newFrame(image);
        
        if(imageScrollPane == null) controller.closeFrame(this);
        
    }//GEN-LAST:event_LoadImageActionPerformed

    private void LinkImagesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LinkImagesActionPerformed
        // TODO add your handling code here:
        if(LinkImages.isSelected()) controller.linkFrames();
        else controller.delinkFrames();
    }//GEN-LAST:event_LinkImagesActionPerformed
java.awt.event.MouseEvent mouseEventTracker;
    java.util.ArrayList<javax.swing.JMenu> opCatList = new java.util.ArrayList<>();
    java.util.ArrayList<String> opCatNameList = new java.util.ArrayList<>();
    private void ImageOpsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ImageOpsMouseEntered
        String[] filterNames = controller.getFilterProcessor().getFilterNameList();
        String[] filterDesc = controller.getFilterProcessor().getFilterDescriptionList();
        opsMenu.removeAll();
        opCatList = new java.util.ArrayList<>();
        opCatNameList = new java.util.ArrayList<>();
        for(int i = 0; i < filterNames.length; i++)
        {
            String categoryName = controller.getFilterCategory(filterNames[i]);
            javax.swing.JMenu category;// = new javax.swing.JMenu();
            if(opCatNameList.contains(categoryName)) category = opCatList.get(opCatNameList.indexOf(categoryName)); 
            else 
            {
                category = new javax.swing.JMenu(categoryName);
                opCatList.add(category);
                opCatNameList.add(categoryName);
                opsMenu.add(category);
            }
            JMenuItem menuItem = new JMenuItem(filterNames[i]);
            menuItem.setToolTipText(filterDesc[i]);
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(imageScrollPane != null) controller.runFilter(e.getActionCommand(), imageScrollPane.getImage());
                    else GUIUtilities.errorMessage("There is no Image Loaded.", false);
//                    System.out.println("RUNNING FILTER: " + e.getActionCommand());
                }
            });
            category.add(menuItem);
        }
        
    }//GEN-LAST:event_ImageOpsMouseEntered

    private void SaveImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveImageActionPerformed
        FileObject fTuple = GUIUtilities.saveFileDialog(this);
        ImageUtilities.SaveImage(imageScrollPane.getImage(), fTuple.getExt(), fTuple.getFile());
    }//GEN-LAST:event_SaveImageActionPerformed

    private void ExitAppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitAppActionPerformed
        controller.closeApplication();
    }//GEN-LAST:event_ExitAppActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        controller.closeFrame(this);
    }//GEN-LAST:event_formWindowClosed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem CloseWindow;
    private javax.swing.JMenuItem ExitApp;
    private javax.swing.JMenu ImageOps;
    private javax.swing.JCheckBoxMenuItem LinkImages;
    private javax.swing.JMenuItem LoadImage;
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JMenuItem SaveImage;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu opsMenu;
    private javax.swing.JScrollPane scrollPane;
    // End of variables declaration//GEN-END:variables
}
