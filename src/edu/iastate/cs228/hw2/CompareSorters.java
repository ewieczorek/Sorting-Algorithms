package edu.iastate.cs228.hw2;

/**
 *  
 * @author ethan wieczorek
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random; 






public class CompareSorters 
{
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Perform the four sorting algorithms over each sequence of integers, comparing 
	 * points by x-coordinate or by polar angle with respect to the lowest point.  
	 * 
	 * @param args
	 * @throws IOException 
	 **/
	public static void main(String[] args) throws IOException 
	{		
		// TODO 
		// 
		// Conducts multiple sorting rounds. In each round, performs the following: 
		// 
		//    a) If asked to sort random points, calls generateRandomPoints() to initialize an array 
		//       of random points. 
		//    b) Reassigns to elements in the array sorters[] (declared below) the references to the 
		//       four newly created objects of SelectionSort, InsertionSort, MergeSort and QuickSort. 
		//    c) Based on the input point order, carries out the four sorting algorithms in one for 
		//       loop that iterates over the array sorters[], to sort the randomly generated points
		//       or points from an input file.  
		//    d) Meanwhile, prints out the table of runtime statistics.
		// 
		// A sample scenario is given in Section 2 of the project description. 
		// 	
			
		Boolean exit = false;
		Random generator = new Random(); 
		
		while(!exit){
			AbstractSorter[] sorters = new AbstractSorter[4];
			//keys: 1 (random integers) 2 (file input) 3 (exit)
			//order: 1 (by x-coordinate) 2 (by polar angle) 
			System.out.println("Enter 1 to generate random points");
			System.out.println("Enter 2 to read points from an input file");
			System.out.println("Enter 3 to exit");
			
			BufferedReader r = new BufferedReader (new InputStreamReader (System.in));
			String userinput = null;	
			Integer userkey = null;
			Integer userorder = null;
			while (userkey == null){ //this is going to get the key from the user using a regular expression to make sure they input a number
				while (userinput == null){
					userinput = r.readLine();
					if(userinput.matches("\\d")) {
						if (Integer.parseInt(userinput) < 4 && Integer.parseInt(userinput) > 0){
							userkey = Integer.parseInt(userinput);
						}else{
					    	System.err.println("Please enter a valid number");
					    }
					    
					}else {
						 System.err.println("Please enter a valid number");
					}
				}
				userinput = null;
			}
			
			if(userkey == 3){ //if the user enters 3 the program closes
				System.exit(0);
			}
			
			userinput = null;
			System.out.println("Enter 1 to sort by x-coordinate");
			System.out.println("Enter 2 to sort by polar angle");
			while (userorder == null){ //this will get the order from the user by using a regular expression tom make sure they input a number
				while (userinput == null){
					userinput = r.readLine();
					if(userinput.matches("\\d")) {
						if (Integer.parseInt(userinput) < 3 && Integer.parseInt(userinput) > 0){
							userorder = Integer.parseInt(userinput);
						}else{
					    	System.err.println("Please enter a valid number");
					    }
					    
					}else {
						 System.err.println("Please enter a valid number");
					}
				}
				userinput = null;
			}
			userinput = null;
			
			if(userkey == 1){
				userkey = null;
				int numpoints = 0;
				System.out.println("Enter how many points you would like");	
				while (userinput == null || numpoints == 0){
					userinput = r.readLine();
					if(userinput.matches("\\d+?")) {
						numpoints = Integer.parseInt(userinput);
					}else {
						 System.err.println("Please enter a valid number");
					}
				}
				userinput = null;
				
				Point[] randompoints = generateRandomPoints(numpoints, generator);
				sorters[0] = new InsertionSorter(randompoints);
				sorters[1] = new MergeSorter(randompoints);
				sorters[2] = new QuickSorter(randompoints);
				sorters[3] = new SelectionSorter(randompoints);

			}else if(userkey == 2){
				userkey = null;
				System.out.println("Enter the name of the file in the project folder\n\".txt\" is added automatically");	
				String inputFileName = "";
				Boolean continues = false;
				while (userinput == null || continues == false){
					userinput = r.readLine();
					if(userinput.matches("\\w+")) {
						inputFileName = userinput + ".txt";
						continues = true;
					}else{
						System.err.println("Please enter a valid file name");
					}
				}
				userinput = null;
				
				sorters[0] = new InsertionSorter(inputFileName);
				sorters[1] = new MergeSorter(inputFileName);
				sorters[2] = new QuickSorter(inputFileName);
				sorters[3] = new SelectionSorter(inputFileName);
				
			}
			
			System.out.format("%-15s %10s %10s\n", "algorithm", "size", "time (ns)");
			System.out.println("--------------------------------------");
			for(int i = 0; i < 4; i++){
				sorters[i].sort(userorder);
				System.out.println(sorters[i].stats());
				sorters[i].draw();
				sorters[i].writePointsToFile();
			}
			System.out.println("--------------------------------------\n");
		}
		
		// Within a sorting round, have each sorter object call the sort and draw() methods
		// in the AbstractSorter class.  You can visualize the result of each sort. (Windows 
		// have to be closed manually before rerun.)  Also, print out the statistics table 
		// (cf. Section 2). 
		
	}
	
	
	/**
	 * This method generates a given number of random points to initialize randomPoints[].
	 * The coordinates of these points are pseudo-random numbers within the range 
	 * [-50,50] × [-50,50]. Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{ 
		if(numPts < 1){
			throw new IllegalArgumentException();
		}
		Point[] temp = new Point[numPts];
		for(int i = 0; i < numPts; i++){
			temp[i] = new Point((rand.nextInt(101) - 50), (rand.nextInt(101) - 50)); 
		}
			return temp;  
	}
}
