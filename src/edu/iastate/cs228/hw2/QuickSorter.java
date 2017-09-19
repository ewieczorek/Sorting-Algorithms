package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;


/**
 *  
 * @author ethan wieczorek
 *
 */

/**
 * 
 * This class implements the version of the quicksort algorithm presented in the lecture.   
 *
 */

public class QuickSorter extends AbstractSorter
{
	
	// Other private instance variables if you need ... 
		
	/**
	 * The two constructors below invoke their corresponding superclass constructors. They
	 * also set the instance variables algorithm and outputFileName in the superclass.
	 */

	/** 
	 * Constructor accepts an input array of points. 
	 *   
	 * @param pts   input array of integers
	 */
	public QuickSorter(Point[] pts)
	{
		super(pts);
		super.algorithm = "quicksort";
		super.outputFileName = "quick.txt";
	}
		

	/**
	 * Constructor reads points from a file. 
	 * 
	 * @param inputFileName  name of the input file
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException 
	 */
	public QuickSorter(String inputFileName) throws InputMismatchException, FileNotFoundException 
	{
		super(inputFileName);
		super.algorithm = "quicksort";
		super.outputFileName = "quick.txt";
	}


	/**
	 * Carry out quicksort on the array points[] of the AbstractSorter class.  
	 * 
	 * @param order  1   by x-coordinate 
	 * 			     2   by polar angle 
	 *
	 */
	@Override 
	public void sort(int order)
	{
		if (order > 2 || order < 1) {
			throw new IllegalArgumentException();
		}
		
		this.setComparator(order);
		
		long starttime = 0;
		long stoptime = 0;
		int size = this.points.length - 1;
		boolean sorted = true;
		
		//All of the variables were instantiated before the timer because they shouldn't be counted as time taken to sort
		starttime = System.nanoTime();

		for (int i = 0; i < size; i++) { //goes through to see if the points were already sorted
			if (this.pointComparator.compare(this.points[i], this.points[i + 1]) == 1) {
				sorted = false;
				break;
			}
		}

		if (sorted == false){
			quickSortRec(0, size);
		}

		stoptime = System.nanoTime();
		this.sortingTime = stoptime - starttime;
		
	}
	
	
	/**
	 * Operates on the subarray of points[] with indices between first and last. 
	 * 
	 * @param first  starting index of the subarray
	 * @param last   ending index of the subarray
	 */
	private void quickSortRec(int first, int last)
	{
		if (last - first >= 1){                   
			if (first < last) {
				int i = this.partition(first, last);
				if(i > 0){
					this.quickSortRec(first, i - 1);
				}
				if(i < (last - 1)){
					this.quickSortRec(i + 1, last);
				}
			}
		} else {
	            return;                     
	    }
	}
	
	
	/**
	 * Operates on the subarray of points[] with indices between first and last.
	 * 
	 * @param first
	 * @param last
	 * @return
	 */
	private int partition(int first, int last)
	{
		int i = first;
        int j = last;

        Point pivot = points[first];

        while (j > i)
        {
                while (this.pointComparator.compare(points[i], pivot) < 1 && i <= last && j > i)  
                	i++; 
                while (this.pointComparator.compare(points[j], pivot) == 1 && j >= first && j >= i) 
                    j--;
                if (j > i) this.swap(i, j);
        }
        this.swap(first, j);
        return j;

	}	
		


}

