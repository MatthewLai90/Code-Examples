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

import FilterProcessor.FilterProcessor;
import Utilities.RGBPixelArray;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author Matthew Lai
 */
public class FrameController {
    
    MouseScrollingHandler mSH;
    ArrayList<SingleImageFrame> frames;
    FilterProcessor filterProcessor;
    int frameCount;
    private static final String[] NUMBERS = {"One", "Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten",
        "Eleven","Twelve","Thirteen","Fourteen","Fifteen","Sixteen","Seventeen","Eighteen","Nineteen","Twenty"};
    
    public FrameController() 
    {
        filterProcessor = new FilterProcessor();
        frames = new ArrayList<>();
        mSH = new MouseScrollingHandler();
        frameCount = 0;
    }
    
    public boolean closeFrame(SingleImageFrame frame)
    {
        delinkFrames();
        boolean removed = frames.remove(frame);
        frame.dispose();
        linkFrames();
        if(frames.size() <= 0) System.exit(0);
        return removed;
    }
    
    public void closeApplication()
    {
        for(SingleImageFrame frame : frames) frame.dispose();
        System.exit(0);
    }
    
    public void linkFrames()
    {
        System.out.println("linking frames");
        for(SingleImageFrame frame : frames)
            mSH.addNewPaneToLinkedMouseScrolling(frame.getImageScrollPane());
    }
    
    public void delinkFrames()
    {
        System.out.println("delinking frames");
        mSH.removeAllLinkScrolling();
    }
        
    public String getNewFrameName() {
        String ret = "Frame ";
        frameCount++;
        if(frameCount <= 20) ret += NUMBERS[frameCount];
        else ret += "" + frameCount;
        return ret;
    }
    
    public void newFrame(RGBPixelArray image)
    {
        if(image != null) 
        {
            frames.add(new SingleImageFrame(image, this));
            frames.get(frames.size()-1).setVisible(true);
        }
        else new SingleImageFrame(this).setVisible(true);
        System.out.println("reached");
    }
    
    public FilterProcessor getFilterProcessor()
    {
        return filterProcessor;
    }
    
    public String getFilterCategory(String filterName)
    {
        return filterProcessor.getFilterCategory(filterName);
    }
    
    public void runFilter(String filterName, RGBPixelArray originalImage)
    {
        RGBPixelArray result = filterProcessor.runFilter(filterName, originalImage);
        if(result != null) newFrame(result);
    }
    
    public static void main(String[] args) 
    {
//        JOptionPane optionPane = new JOptionPane("What Type of GrayScaling Do you want to try?", JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, new Object[] {""});
        FrameController fC = new FrameController();
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } 
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) 
            { java.util.logging.Logger.getLogger(SingleImageFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex); }

        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                fC.newFrame(null);
            }
            
        });
    }

    
}
