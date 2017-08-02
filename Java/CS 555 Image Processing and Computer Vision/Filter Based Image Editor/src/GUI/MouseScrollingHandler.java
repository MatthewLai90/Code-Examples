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

import GUI.GUIUtilities;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 *
 * @author Matthew Lai
 */
public class MouseScrollingHandler {
    
    private ArrayList<MouseAdapter> existingMouseAdapters;
    private HashSet<JScrollPane> linkedPanes;
    public MouseScrollingHandler()
    {
        existingMouseAdapters = new ArrayList<>();
        linkedPanes = new HashSet<>();
    }
    
    private void addAndLinkMouseScrolling(JScrollPane[] panes)
    {
        ArrayList<JScrollPane> newPanes = new ArrayList<>();
        ArrayList<MouseAdapter> newMouseAdapters = new ArrayList<>();
        if(panes.length < 1) return;
        Dimension size = panes[0].getPreferredSize();
        for(JScrollPane pane : panes) 
        {
//            if(!pane.getPreferredSize().equals(size))
//            {
//                GUIUtilities.errorMessage("Your images must be the same size to link.", false);
//                return;
//            }
            if(!linkedPanes.contains(pane)) newPanes.add(pane);
            pane.getViewport().setViewPosition(new Point(0, 0));
        }
        for(JScrollPane pane : newPanes) 
        {
            linkedPanes.add(pane);
            newMouseAdapters.add(getMouseScrollAdapter(pane));
            for(MouseAdapter oldAdapter : existingMouseAdapters)
            {
                pane.addMouseListener(oldAdapter);
                pane.addMouseMotionListener(oldAdapter);
            }
        }
        for(MouseAdapter newAdapter : newMouseAdapters)
        {
            existingMouseAdapters.add(newAdapter);
            for(JScrollPane pane : panes)
            {
                pane.addMouseListener(newAdapter);
                pane.addMouseMotionListener(newAdapter);
            }
        }
    }
    
    public void removeAllLinkScrolling()
    {
        Iterator<JScrollPane> i = linkedPanes.iterator();
        while(i.hasNext())
        {
            JScrollPane current = i.next();
            for(MouseAdapter currentAdapter : existingMouseAdapters)
            {
                current.removeMouseListener(currentAdapter);
                current.removeMouseMotionListener(currentAdapter);
            }
        }
        
        existingMouseAdapters = new ArrayList<>();
        linkedPanes = new HashSet<>();
    }
    
    public void addNewPaneToLinkedMouseScrolling(JScrollPane newPane)
    {
        
        JScrollPane[] panes = new JScrollPane[linkedPanes.size() + 1];
        int index = 0;
        Iterator<JScrollPane> it = linkedPanes.iterator();
        while(it.hasNext()) panes[index++] = it.next();
        panes[index] = newPane;
        addAndLinkMouseScrolling(panes);
    }
    
//    public static void addAndLinkMouseScrolling(JScrollPane one, JScrollPane two)
//    {
//        if(!one.getPreferredSize().equals(two.getPreferredSize()))
//        {
//            System.out.println(one.getSize() + "\t\t" + two.getSize());
//            JOptionPane.showMessageDialog(new JFrame(), "Your two images must be the same size to link.","Error",JOptionPane.INFORMATION_MESSAGE);
//            return;
//        }
//        MouseAdapter mA = getMouseScrollAdapter(one);
//        one.addMouseListener(mA);
//        one.addMouseMotionListener(mA);
//        two.addMouseListener(mA);
//        two.addMouseMotionListener(mA);
//        
//        MouseAdapter mA2 = getMouseScrollAdapter(two);
//        one.addMouseListener(mA2);
//        one.addMouseMotionListener(mA2);
//        two.addMouseListener(mA2);
//        two.addMouseMotionListener(mA2);
//    }
    
    public static void addMouseScrolling(JScrollPane jSP)
    {
        MouseAdapter mA = getMouseScrollAdapter(jSP);
        jSP.addMouseListener(mA);
        jSP.addMouseMotionListener(mA);
    }
    
    private static MouseAdapter getMouseScrollAdapter(JScrollPane scrollPaneToScroll)
    {
        JPanel panelToScroll = (JPanel)scrollPaneToScroll.getViewport().getView();
        MouseAdapter mouseScrollAdapter = new MouseAdapter() {
            private final Point pressPoint = new Point();
            @Override
            public void mouseDragged(MouseEvent e) {
                JViewport viewPort = scrollPaneToScroll.getViewport();
                Point currentPoint = e.getPoint();
                Point viewPoint = viewPort.getViewPosition();
                viewPoint.translate((pressPoint.x - currentPoint.x), (pressPoint.y - currentPoint.y));
                panelToScroll.scrollRectToVisible(new Rectangle(viewPoint, viewPort.getSize()));
                pressPoint.setLocation(e.getPoint());
            }
            @Override
            public void mousePressed(MouseEvent e)
            {
                panelToScroll.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                pressPoint.setLocation(e.getPoint());
            }
            @Override
            public void mouseReleased(MouseEvent e)
            {
                panelToScroll.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        };
        return mouseScrollAdapter;
    }
}
