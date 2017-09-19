package edu.iastate.cs228.hw2;

/**
 *  
 * @author ethan wieczorek
 *
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * This abstract class is extended by SelectionSort, InsertionSort, MergeSort, and QuickSort.
 * It stores the input (later the sorted) sequence and records the employed sorting algorithm, 
 * the comparison method, and the time spent on sorting. 
 *
 */


public abstract class AbstractSorter
{
	
	protected Point[] points;    // Array of points operated on by a sorting algorithm. 
	                             // The number of points is given by points.length.
	
	protected String algorithm = null; // "selection sort", "insertion sort", "mergesort", or
	                                   // "quicksort". Initialized by a subclass. 
									   // constructor.
	protected boolean sortByAngle;     // true if the last sorting was done by polar angle and  
									   // false if by x-coordinate 
	
	protected String outputFileName;   // "select.txt", "insert.txt", "merge.txt", or "quick.txt"
	
	protected long sortingTime; 	   // execution time in nanoseconds. 
	 
	protected Comparator<Point> pointComparator;  // comparator which compares polar angle if 
												  // sortByAngle == true and x-coordinate if 
												  // sortByAngle == false 
	
	private Point lowestPoint; 	    // lowest point in the array, or in case of a tie, the
									// leftmost of the lowest points. This point is used 
									// as the reference point for polar angle based comparison.

	
	// Add other protected or private instance variables you may need. 
	
	protected AbstractSorter()
	{
		// No implementation needed. Provides a default super constructor to subclasses. 
		// Removable after implementing SelectionSorter, InsertionSorter, MergeSorter, and QuickSorter.
	}
	
	
	/**
	 * This constructor accepts an array of points as input. Copy the points into the array points[]. 
	 * Sets the instance variable lowestPoint.
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	protected AbstractSorter(Point[] pts) throws IllegalArgumentException
	{
		if (pts == null) throw new IllegalArgumentException();
		
		if (pts.length == 0) throw new IllegalArgumentException();
		
		this.points = new Point[pts.length];
		this.lowestPoint = pts[0];
		
		for( int i = 0; i < pts.length; i++){
			this.points[i] = pts[i];
			if(pts[i].getY() < this.lowestPoint.getY()) this.lowestPoint = pts[i];
			if(pts[i].getY() == this.lowestPoint.getY() && pts[i].getX() < this.lowestPoint.getX()) this.lowestPoint = pts[i];
		}
		
	}

	
	/**
	 * This constructor reads points from a file. Sets the instance variables lowestPoint and 
	 * outputFileName.
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   when the input file contains an odd number of integers
	 */
	protected AbstractSorter(String inputFileName) throws FileNotFoundException, InputMismatchException
	{
		try{
			BufferedReader lineReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName)));
			List<Integer> temp = new ArrayList<Integer>();
			Pattern p = Pattern.compile("(-?)(\\d+)\\s?");
			String widthLine = "";
			try{
			while (!(widthLine = lineReader.readLine()).isEmpty()){
				Matcher m = p.matcher(widthLine); 
				while (m.find()) {					
					if(!m.group(1).isEmpty()){
						if(!m.group(2).isEmpty()){
							temp.add(Integer.parseInt(m.group(2))*-1);
						}
					}else if(m.group(1).isEmpty()){
						if(!m.group(2).isEmpty()){
							temp.add((!m.group(2).isEmpty()) ? Integer.parseInt(m.group(2)) : null);
						}
					}
				}
			}
			}catch(NullPointerException n1){
				
			}
			lineReader.close();
			
			if(temp.size() %2 != 0) {InputMismatchException e1 = new InputMismatchException(); System.out.println(e1.getMessage());}
			
			this.points = new Point[temp.size()/2];
			int j = 0;
			for(int i = 0; i < temp.size() - 1; i++){
				this.points[j] = new Point(temp.get(i), temp.get(i+1));
				i++;
				j++;
				
			}
			this.lowestPoint = new Point(50,50);
			for( int i = 0; i < points.length; i++){
				if(points[i].getY() < this.lowestPoint.getY()) this.lowestPoint = points[i];
				if(points[i].getY() == this.lowestPoint.getY() && points[i].getX() < this.lowestPoint.getX()) this.lowestPoint = points[i];
			}
			
		} catch (FileNotFoundException ex) {
		    System.out.println("File Not Found");
		} catch (IOException ex2) {
			System.out.println("IO Exception");
		}
	}
	

	/**
	 * Sorts the elements in points[]. 
	 * 
	 *     a) in the non-decreasing order of x-coordinate if order == 1
	 *     b) in the non-decreasing order of polar angle w.r.t. lowestPoint if order == 2 
	 *        (lowestPoint will be at index 0 after sorting)
	 * 
	 * Sets the instance variable sortByAngle based on the value of order. Calls the method 
	 * setComparator() to set the variable pointComparator and use it in sorting.    
	 * Records the sorting time (in nanoseconds) using the System.nanoTime() method. 
	 * (Assign the time spent to the variable sortingTime.)  
	 * 
	 * @param order  1   by x-coordinate 
	 * 			     2   by polar angle w.r.t lowestPoint 
	 *
	 * @throws IllegalArgumentException if order is less than 1 or greater than 2
	 */
	public abstract void sort(int order) throws IllegalArgumentException; 
	
	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * selection sort   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description. 
	 */
	public String stats()
	{ 
		return String.format("%-15s %10d %10d", algorithm, points.length, sortingTime); 
	}
	
	
	/**
	 * Write points[] to a string.  When printed, the points will appear in order of increasing
	 * index with every point occupying a separate line.  The x and y coordinates of the point are 
	 * displayed on the same line with exactly one blank space in between. 
	 */
	@Override
	public String toString()
	{
		String temp = "";
		for(int i = 0; i < points.length; i++){
			temp += this.points[i].getX() + " " + this.points[i].getY() + "\n";
		}
		return temp;
	}

	/**
	 * This method is called after sorting for visually check whether the result is correct.  You  
	 * just need to generate a list of points and a list of segments, depending on the value of 
	 * sortByAngle, as detailed in Section 4.1. Then create a Plot object to call the method myFrame().  
	 */
	public void draw()
	{		
		int numSegs = 0;  // number of segments to draw
		Segment[] segments = new Segment[0];
		
		for(int i = 0; i < (points.length - 1); i++){
			if(this.pointComparator.compare(points[i], points[i+1]) != 0) numSegs++;
		}

		if(this.sortByAngle == false){ 
			segments = new Segment[numSegs]; 
			int g = 0;
			for(int j = 0; j < points.length-1; j++){
				if(pointComparator.compare(points[j], points[j+1]) != 0){
					segments[g] = new Segment(points[j], points[j+1]);
					g++;
				}
				if(g == numSegs){
					break;
				}
			}
		}else if(this.sortByAngle == true){
			int numAngleSegs = 0;
			for(int i = 0; i < points.length; i++){
				if(this.pointComparator.compare(lowestPoint, points[i]) != 0) {numAngleSegs++;}
			} 
			segments = new Segment[numSegs+numAngleSegs];
			int g = 0;
			for(int j = 0; j < points.length-1; j++){
				if(pointComparator.compare(points[j], points[j+1]) != 0){
					segments[g] = new Segment(points[j], points[j+1]);
					g++;
				}
			}
			for(int h = 0; h < points.length; h++){
				if(this.pointComparator.compare(lowestPoint, points[h]) != 0){
					segments[g] = new Segment(lowestPoint, points[h]);
					g++;
				}
				if(g == numSegs + numAngleSegs){
					break;
				}
			}
		}

		// The following statement creates a window to display the sorting result.
		Plot.myFrame(points, segments, getClass().getName());
	}
		
	/**
	 * Generates a comparator on the fly that compares by polar angle if sortByAngle == true
	 * and by x-coordinate if sortByAngle == false. Set the protected variable pointComparator
	 * to it. Need to create an object of the PolarAngleComparator class and call the compareTo() 
	 * method in the Point class, respectively for the two possible values of sortByAngle.  
	 * 
	 * @param order
	 */
	protected void setComparator(int order) 
	{
		if (order == 2) {
			this.sortByAngle = true;
			this.pointComparator = new PolarAngleComparator(this.lowestPoint);
		} else {
			this.sortByAngle = false;
			this.pointComparator = new Comparator<Point>() {
				public int compare(Point p1, Point p2) {
					return p1.compareTo(p2);
				}
			};
		}
	}

	
	/**
	 * Swap the two elements indexed at i and j respectively in the array points[]. 
	 * @param i
	 * @param j
	 */
	protected void swap(int i, int j)
	{
		try{
			Point temp = points[i];
			points[i] = points[j];
			points[j] = temp;
		}
		catch (NullPointerException e){
			
		}
		
	}
	
	 /**
	  * This method, called after sorting, writes point data into a file by outputFileName.<br>
	  * The format of data in the file is the same as printed out from toString().<br>
	  * The file can help you verify the full correctness of a sorting result and debug the underlying algorithm.
	 * @throws IOException 
	  */
	 public void writePointsToFile() throws IOException {
		 try{
			 BufferedWriter output = null;
			 output = new BufferedWriter(new FileWriter(outputFileName));
			 for(int i = 0; i < points.length; i++){
				 output.write(points[i].toString());
			 }
			 output.close();
		 }catch(FileNotFoundException e1){
			 System.out.println("Output File Not Found");
		 }catch(IOException e2){
			 
		 }
	 }
}
