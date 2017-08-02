/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package FilterUtilities;

/**
 *
 * @author Matthew Lai
 */
public class QuicksortMedian {

	private static int []a;
        
        public static void main(String[] args) {
            int[] sorted = sort(new int[]{8,1,8541,85,46,4,4168,41,514,476,54168,416,341,864,48,83});
            
            for (int i = 0; i < sorted.length; i++) {
                System.out.print(sorted[i] + "\t");
            }
            System.out.println("");
        }
	
        public static int[] sort(int[] array)
        {
            a = array.clone();
            sort();
            return a;
        }
	
	// This method sort an array internally it calls to quickSort 
	private static void sort(){
		int left = 0;
		int right = a.length-1;
			
		quickSort(left, right);
	}
	
	// This method is used to sort the array using quicksort algorithm.
	// It takes left and the right end of the array as two cursors.
	private static void quickSort(int left,int right){
		
		// If both cursor scanned the complete array quicksort exits
		if(left >= right)
			return;
		
		// Pivot using median of 3 approach
		int pivot = getMedian(left, right);
		int partition = partition(left, right, pivot);
		
		// Recursively, calls the quicksort with the different left and right parameters of the sub-array
		quickSort(0, partition-1);
		quickSort(partition+1, right);
	}
	
	// This method is used to partition the given array and returns the integer which points to the sorted pivot index
	private static int partition(int left,int right,int pivot){
		int leftCursor = left-1;
		int rightCursor = right;
		while(leftCursor < rightCursor){
			while(a[++leftCursor] < pivot);
			while(rightCursor > 0 && a[--rightCursor] > pivot);
			if(leftCursor >= rightCursor){
				break;
			}else{
				swap(leftCursor, rightCursor);
			}
		}
		swap(leftCursor, right);
		return leftCursor;
	}
	
	public static int getMedian(int left,int right){
		int center = (left+right)/2;
		
		if(a[left] > a[center])
			swap(left,center);
		
		if(a[left] > a[right])
			swap(left, right);
		
		if(a[center] > a[right])
			swap(center, right);
		
		swap(center, right);
		return a[right];
	}
	
	// This method is used to swap the values between the two given index
	public static void swap(int left,int right){
		int temp = a[left];
		a[left] = a[right];
		a[right] = temp;
	}
	
	public static void printArray(){
		for(int i : a){
			System.out.print(i+" ");
		}
	}
	
	public static int[] getArray(){
		int size=10;
		int []array = new int[size];
		int item = 0;
		for(int i=0;i<size;i++){
			item = (int)(Math.random()*100); 
			array[i] = item;
		}
		return array;
	}


    
}
