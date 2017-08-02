/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package FilterUtilities;

/**
 *
 * @author Matthew Lai
 */
public class RGBObject 
{
    private int red, green, blue;
    public RGBObject(int intRGB)
    {
        red = (intRGB >> 16) & 0xFF;
        green = (intRGB >> 8) & 0xFF;
        blue = intRGB & 0xFF;
    }
    
    public RGBObject(int red, int green, int blue)
    {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
    
    public int getRed()
    {
        return red;
    }
    
    public int getGreen()
    {
        return green;
    }
    public int getBlue()
    {
        return blue;
    }
    
    public int getIntRGB()
    {
        return (((red << 8) + green) << 8) + blue;
    }
}
