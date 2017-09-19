package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;


/**
 *  
 * @author ethan wieczorek
 *
 */

/**
 * 
 * This class implements insertion sort.   
 *
 */

public class InsertionSorter extends AbstractSorter 
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
	public InsertionSorter(Point[] pts) 
	{
		super(pts);
		super.algorithm = "insertion sort";
		super.outputFileName = "insert.txt";
	}	

	
	/**
	 * Constructor reads points from a file. 
	 * 
	 * @param inputFileName  name of the input file
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException 
	 */
	public InsertionSorter(String inputFileName) throws InputMismatchException, FileNotFoundException 
	{
		super(inputFileName);
		super.algorithm = "insertion sort";
		super.outputFileName = "insert.txt";
	}
	
	
	/** 
	 * Perform insertion sort on the array points[] of the parent class AbstractSorter.  
	 * 
	 * @param order  1   by x-coordinate 
	 * 			     2   by polar angle 
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
		int insertpoint = 0;
		
		//All of the variables were instantiated before the timer because they shouldn't be counted as time taken to sort
		starttime = System.nanoTime();
		try{
		for(insertpoint = 0; insertpoint<this.points.length; insertpoint++){
			for (int i = insertpoint; i > 0 && (this.pointComparator.compare(this.points[i-1], this.points[i]) == 1); i--) {
				this.swap(i-1, i);
			}
		}	
		}catch(NullPointerException n2){
			
		}
		stoptime = System.nanoTime();
		this.sortingTime = stoptime - starttime;
	}		
}
