/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package FilterHistory;

import java.util.LinkedList;

/**
 *
 * @author Matthew Lai
 */
public class FilterHistory 
{
    LinkedList<FilterState> filterHistory;
    public FilterHistory(int[][] image)
    {
        filterHistory = new LinkedList<>();
        filterHistory.add(new FilterState(image));
    }
    
    //Add to History
    public void newAppliedFilter(int[][] newImage, String filterName)
    {
        filterHistory.add(new FilterState(newImage, filterName, filterHistory.peekLast()));
    }
    
    //List Filter History
    public String[] getFilterHistory()
    {
        String[] filterList = new String[filterHistory.size() + 1];
        int index = 0;
        for(FilterState fS : filterHistory) filterList[index] = fS.getAppliedFilter();
        filterList[0] = "Original";
        filterList[filterList.length-1] = "Current";
        
        return filterList;
    }
    
    //Revert History - Return new "Current"
    public void revertHistoryTo(int index)
    {
        if(index < filterHistory.size()) 
        {
            int popNum = filterHistory.size() - 1 - index;
            for(int i = 0; i < popNum; i++) filterHistory.pollLast();
        }
    }
    
    public int[][] retrieveImage(int index)
    {
        return filterHistory.get(index).getImage();
    }
}
