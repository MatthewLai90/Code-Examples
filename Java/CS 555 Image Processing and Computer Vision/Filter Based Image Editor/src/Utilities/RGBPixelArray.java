
package Utilities;

/**
 *
 * @author Matthew Lai
 */
public class RGBPixelArray {
    
    final boolean isGrayscale;
    int[][] red;
    int[][] green;
    int[][] blue;
    int[][] grayscaleValues;
    final int maxValue;
    final int length, width;
    
    public RGBPixelArray(int[][] red, int[][] green, int[][] blue)
    {
        grayscaleValues = null;
        this.red = red;
        this.green = green;
        this.blue = blue;
        isGrayscale = false;
        width = red.length;
        length = red[0].length;
        if (red.length != green.length || red.length != blue.length || blue.length != green.length ||
            red[0].length != green[0].length || red[0].length != blue[0].length || blue[0].length != green[0].length)
            throw new RGBPixelArraySizeMismatchError("RED-GREEN-BLUE Array Sizes Are Not the Same.");
        
        
        int max = 0;
        for (int i = 0; i < red.length; i++) 
            for (int j = 0; j < red[i].length; j++) 
                if(red[i][j] > max) max = red[i][j];
        
        int tempMax = (int)Math.pow(2, 0);
        int c = 0;
        while(tempMax <= max) tempMax = (int)Math.pow(2, ++c);
        
//        maxValue = tempMax;
        maxValue = (int)Math.pow(2, 8);
    }
    
    public RGBPixelArray(RGBPixelArray original)
    {
        width = original.getWidth();
        length = original.getLength();
        int[][] origRed = original.getRed();
        int[][] origGreen = original.getGreen();
        int[][] origBlue = original.getBlue();
        int[][] origGS = original.getGrayscaleValues();
        if(origRed != null) red = new int[origRed.length][];
        else red = null;
        if(origGreen != null) green = new int[origGreen.length][];
        else green = null;
        if(origBlue != null) blue = new int[origBlue.length][];
        else blue = null;
        if(origGS != null) grayscaleValues = new int[origGS.length][];
        else grayscaleValues = null;
        for(int i = 0; i < origRed.length; i++)
        {
            if(red != null) red[i] = origRed[i].clone();
            if(green != null) green[i] = origGreen[i].clone();
            if(blue != null) blue[i] = origBlue[i].clone();
            if(grayscaleValues != null) grayscaleValues[i] = origGS[i].clone();
        }
        isGrayscale = original.isGrayscale;
        
        maxValue = original.maxValue;
    }
    
    public RGBPixelArray(int width, int length, boolean isGS)
    {
        isGrayscale = isGS;
        this.width = width;
        this.length = length;
        if(isGrayscale) {
            red = null;
            green = null;
            blue = null;
            grayscaleValues = new int[width][length];
        }
        else {
            red = new int[width][length];
            green = new int[width][length];
            blue = new int[width][length];
            grayscaleValues = null;
            
        }
        int tempMax = (int)Math.pow(2, 8);
//        maxValue = tempMax;
        maxValue = (int)Math.pow(2, 8);
        
    }
    
    public RGBPixelArray(int[][] pixelValues)
    {
        this.red = pixelValues;
        this.green = pixelValues;
        this.blue = pixelValues;
        this.grayscaleValues = pixelValues;
        isGrayscale = true;
        width = pixelValues.length;
        length = pixelValues[0].length;
        
        int max = 0;
        for (int i = 0; i < grayscaleValues.length; i++) 
            for (int j = 0; j < grayscaleValues[i].length; j++) 
                if(grayscaleValues[i][j] > max) max = grayscaleValues[i][j];
        
        int tempMax = (int)Math.pow(2, 0);
        int c = 0;
        while(tempMax <= max) tempMax = (int)Math.pow(2, ++c);
        
//        maxValue = tempMax;
        maxValue = (int)Math.pow(2, 8);
    }
    
    public void setPixelValue(int x, int y, int red, int green, int blue)
    {
        if(isGrayscale) return;
        this.red[x][y] = red;
        this.green[x][y] = green;
        this.blue[x][y] = blue;
        if(this.red[x][y] >= maxValue) this.red[x][y] = maxValue - 1;
        if(this.green[x][y] >= maxValue) this.green[x][y] = maxValue - 1;
        if(this.blue[x][y] >= maxValue) this.blue[x][y] = maxValue - 1;
        if(this.red[x][y] < 0) this.red[x][y] = 0;
        if(this.green[x][y] < 0) this.green[x][y] = 0;
        if(this.blue[x][y] < 0) this.blue[x][y] = 0;
    }
    
    public void setPixelValue(int x, int y, int value)
    {
        if(!isGrayscale) return;
        grayscaleValues[x][y] = value;
        if(grayscaleValues[x][y] >= maxValue) grayscaleValues[x][y] = maxValue - 1;
        if(grayscaleValues[x][y] < 0) grayscaleValues[x][y] = 0;
    }
    
    public void setPixelValue(int x, int y, RGBPixelArray o, int ox, int oy)
    {
        if(isGrayscale)
        {
            grayscaleValues[x][y] = o.getGrayscaleValues()[ox][oy];
            if(grayscaleValues[x][y] >= maxValue) grayscaleValues[x][y] = maxValue - 1;
            if(grayscaleValues[x][y] < 0) grayscaleValues[x][y] = 0;
        }
        else 
        {
            red[x][y] = o.getRed()[ox][oy];
            green[x][y] = o.getGreen()[ox][oy];
            blue[x][y] = o.getBlue()[ox][oy];
            if(this.red[x][y] >= maxValue) this.red[x][y] = maxValue - 1;
            if(this.green[x][y] >= maxValue) this.green[x][y] = maxValue - 1;
            if(this.blue[x][y] >= maxValue) this.blue[x][y] = maxValue - 1;
            if(this.red[x][y] < 0) this.red[x][y] = 0;
            if(this.green[x][y] < 0) this.green[x][y] = 0;
            if(this.blue[x][y] < 0) this.blue[x][y] = 0;
        }
    }
    
    public int getWidth()
    {
        return width;
    }
    
    public int getLength()
    {
        return length;
    }
    
    public int[][] getRed()
    {
        return red;
    }
    
    public int[][] getGreen()
    {
        return green;
    }
    
    public int[][] getBlue()
    {
        return blue;
    }
    
    public int[][] getGrayscaleValues()
    {
        return grayscaleValues;
    }
    
    public boolean isGrayscaleImage() { return isGrayscale; }

    public static class RGBPixelArraySizeMismatchError extends Error {

        public RGBPixelArraySizeMismatchError(String redgreenblue_Array_Sizes_Are_Not_the_Same) {
        }
    }
    
}
