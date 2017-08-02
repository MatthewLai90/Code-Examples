///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package Utilities;
//
//import GUI.MouseScrollingHandler;
//import FilterInterface.ImageFilter;
//import TestFilters.ManipFilter;
//import GUI.GUIUtilities;
//import GUI.JImagePanel;
//import ImageProcessor.ImageUtilities;
//import static ImageProcessor.ImageUtilities.*;
//import java.awt.Dimension;
//import java.awt.Toolkit;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.io.File;
//import javax.swing.JFrame;
//import javax.swing.JScrollPane;
//import javax.swing.UIManager;
//
///**
// *
// * @author Matthew Lai
// */
//public class TestFile {
//    
//    
//    public static void main(String[] args) throws Exception {
//        
//        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        
//        File f = new File("C:\\Users\\Matthew Lai\\Pictures\\029.jpg");
//        JFrame frame = new JFrame("Image One");
//        JFrame frame2 = new JFrame("Image two");
//        JFrame frame3 = new JFrame("Image three");
//        
////        f = GUIUtilities.getImageFileDialog(frame);
////        File imageFile = GUIUtilities.getImageFileDialog(frame);
////        Utilities.FileTuple fT = GUIUtilities.saveFileDialog(frame);
////        SaveImage(loadImage(imageFile), fT.getExt() , fT.getFile());
//        int[][] img = ImageUtilities.loadImage(f);
//        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
//        int height = Toolkit.getDefaultToolkit().getScreenSize().height - 40;
//        if(width > ImageUtilities.getBufferedImage(img).getWidth()+20) width = ImageUtilities.getBufferedImage(img).getWidth()+20;
//        if(height > ImageUtilities.getBufferedImage(img).getHeight()) height = ImageUtilities.getBufferedImage(img).getHeight();
//        
//        ManipFilter tF = new ManipFilter();
//        width = 800; height = 800;
//        JImagePanel p1 = GUIUtilities.getImagePanel(tF.filterImage(img, 0));
//        JImagePanel p2 = GUIUtilities.getImagePanel(tF.filterImage(img, 1));
//        JImagePanel p3 = GUIUtilities.getImagePanel(tF.filterImage(img, 2));
//        JScrollPane jSP = GUIUtilities.getScrollWindow(p1);
//        JScrollPane jSP2 = GUIUtilities.getScrollWindow(p2);
//        JScrollPane jSP3 = GUIUtilities.getScrollWindow(p3);
//        
//        KeyAdapter kL = new KeyAdapter() {
//            @Override
//            public void keyReleased(KeyEvent e)
//            {
//                if(e.getKeyCode() == KeyEvent.VK_ESCAPE) frame.dispose();
//                if(e.getKeyCode() == KeyEvent.VK_ESCAPE) frame2.dispose();
//                if(e.getKeyCode() == KeyEvent.VK_ESCAPE) frame3.dispose();
//            }
//        };
//        MouseScrollingHandler mSH = new MouseScrollingHandler();
////        mSH.addAndLinkMouseScrolling(new JScrollPane[]{jSP, jSP2});
////        Utilities.MouseScrollingHandler.addMouseScrolling(jSP3);
//        mSH.addNewPaneToLinkedMouseScrolling(jSP);
//        frame.add(jSP);
//        frame.addKeyListener(kL);
//        frame.setVisible(true);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(new Dimension(width, height));
//        
////        Thread.sleep(5000);
//        mSH.addNewPaneToLinkedMouseScrolling(jSP2);
//        frame2.add(jSP2);
//        frame2.addKeyListener(kL);
//        frame2.setVisible(true);
//        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame2.setSize(new Dimension(width, height));
//        
////        Thread.sleep(5000);
//        mSH.addNewPaneToLinkedMouseScrolling(jSP3);
//        frame3.add(jSP3);
//        frame3.addKeyListener(kL);
//        frame3.setVisible(true);
//        frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame3.setSize(new Dimension(width, height));
//        
////        GUIUtilities.errorMessage("test", false);
//    }
//}
