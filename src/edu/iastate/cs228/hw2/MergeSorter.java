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
 * This class implements the mergesort algorithm.   
 *
 */

public class MergeSorter extends AbstractSorter
{
	// Other private instance variables if you need ... 
	
	/**
	 * The two constructors below invoke their corresponding superclass constructors. They
	 * also set the instance variables algorithm and outputFileName in the superclass.
	 */

	/** 
	 * Constructor accepts an input array of points. 
	 * in the array. 
	 *  
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts) 
	{
		super(pts);
		super.algorithm = "mergesort";
		super.outputFileName = "merge.txt";
	}
	
	
	/**
	 * Constructor reads points from a file. 
	 * 
	 * @param inputFileName  name of the input file
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException 
	 */
	public MergeSorter(String inputFileName) throws InputMismatchException, FileNotFoundException 
	{
		super(inputFileName);
		super.algorithm = "mergesort";
		super.outputFileName = "merge.txt";
	}


	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter. 
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
			mergeSortRec(this.points);
		}

		stoptime = System.nanoTime();
		this.sortingTime = stoptime - starttime;
	}

	
	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted subarrays into pts[].   
	 * 
	 * @param pts	point array 
	 */
	private void mergeSortRec(Point[] pts)
	{
		if(pts.length > 1){
			int halfway = pts.length / 2;
			Point[] left = new Point[halfway];
			Point[] right = new Point[pts.length - halfway];
			int k = 0;
			for(int i = 0; i < left.length; i++){
				left[i] = pts[k];
				k++;
			}
			for(int j = 0; j < right.length; j++){
				right[j] = pts[k];
				k++;
			}
			mergeSortRec(left);
			mergeSortRec(right);
			
			int leftpoint = 0;
			int rightpoint = 0;
			int i = 0;
			while(leftpoint < left.length || rightpoint < right.length){
				if(leftpoint == left.length){
					pts[i] = right[rightpoint];
					i++;
					rightpoint++;
				}else if(rightpoint == right.length){
					pts[i] = left[leftpoint];
					i++;
					leftpoint++;
				}else if(this.pointComparator.compare(left[leftpoint], right[rightpoint]) < 1){
					pts[i] = left[leftpoint];
					i++;
					leftpoint++;
				}else{
					pts[i] = right[rightpoint];
					i++;
					rightpoint++;
				}
			}
			
		}
	}

	
	// Other private methods in case you need ...

}
