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

package FilterHistory;

/**
 *
 * @author Matthew Lai
 */
public class FilterState {
    
    final int [][] image;
    final String previouslyAppliedFilter;
    final int filterNumber;
    FilterState previousFilter;
    
    public FilterState()
    {
        image = null;
        previouslyAppliedFilter = null;
        filterNumber = -1;
    }
    
    public FilterState(int [][] image)
    {
        this.image = image;
        filterNumber = 0;
        this.previousFilter = null;
        previouslyAppliedFilter = null;
    }
    
    public FilterState(int [][] image, String filterApplied, FilterState previousFilter)
    {
        this.previousFilter = previousFilter;
        this.image = image;
        filterNumber = previousFilter.getFilterNumber() + 1;
        previouslyAppliedFilter = filterApplied;
    }
    
    public int getFilterNumber() { return filterNumber; }
    
    public int[][] getImage() { return image; }
    
    public String getAppliedFilter() { return previouslyAppliedFilter; }
}
