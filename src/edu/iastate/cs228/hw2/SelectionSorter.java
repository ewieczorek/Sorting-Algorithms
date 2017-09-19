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
 * This class implements selection sort.   
 *
 */

public class SelectionSorter extends AbstractSorter
{
	// Other private instance variables if you need ... 
	
	/**
	 * The two constructors below invoke their corresponding superclass constructors. They
	 * also set the instance variables algorithm and outputFileName in the superclass.
	 */

	/**
	 * Constructor takes an array of points.
	 *  
	 * @param pts  
	 */
	public SelectionSorter(Point[] pts)  
	{
		super(pts);
		super.algorithm = "selection sort";
		super.outputFileName = "select.txt";
	}	

	
	/**
	 * Constructor reads points from a file. 
	 * 
	 * @param inputFileName  name of the input file
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException 
	 */
	public SelectionSorter(String inputFileName) throws InputMismatchException, FileNotFoundException 
	{
		super(inputFileName);
		super.algorithm = "selection sort";
		super.outputFileName = "select.txt";
	}
	
	
	/** 
	 * Apply selection sort on the array points[] of the parent class AbstractSorter.  
	 *
	 * @param order  1   by x-coordinate 
	 * 			     2   by polar angle 
	 *
	 */
	@Override 
	public void sort(int order)
	{ 
		if (order < 1 || order > 2) {
			throw new IllegalArgumentException();
		}

		this.setComparator(order);
		
		long starttime = 0;
		long stoptime = 0;
		starttime = System.nanoTime();
		for (int i = 0; i < points.length; i++)  
		{
			int lowest = i;
			for(int j = i; j < points.length; j++){
				if(this.pointComparator.compare(points[j], points[lowest]) == -1) {lowest = j;}
			}
			
		    this.swap(i, lowest);
		}  
		stoptime = System.nanoTime();
		this.sortingTime = stoptime - starttime;
	}	
}
