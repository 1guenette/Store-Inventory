package edu.iastate.cs228.hw2;
/**
 *  
 * @author Samuel Guenette
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Random; 


public class CompareSorters 
{
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Perform the four sorting algorithms over each sequence of integers, comparing 
	 * points by x-coordinate or by polar angle with respect to the lowest point.  
	 * 
	 * @param args
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException 
	 **/
	public static void main(String[] args) throws InputMismatchException, FileNotFoundException 
	{		
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
		int round = 0;
		boolean end = false;
		while(end == false)
			{
				round++;	
				startRound(round, end);
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
		
		Point[] pts = new Point[numPts];
		for(int i = 0; i<numPts; i++)
		{
			int x = rand.nextInt(101)-50;
			int y = rand.nextInt(101)-50;
			pts[i] = new Point(x,y);
			
		}
		return pts; 
	}
	
	/**
	 * This method executes a single round of asking the user for points to analyze
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param round  current round
	 * @param end    whether or not the the application should terminate 
	 * @throws InputMismatchException, FileNotFoundException < 1
	 */
	public static void startRound(int round, boolean end) throws InputMismatchException, FileNotFoundException
	{
		
		QuickSorter quickSort = null;
		InsertionSorter inSort = null;
		MergeSorter mergeSort = null;
		AbstractSorter selSort = null;	
		Point[] points = null;
		Random ran = new Random();
		
		System.out.println("Round " + round);
		System.out.println("1)Create Random Points   2)Import File   3)Exit");
		Scanner scan = new Scanner(System.in);
		
		int input = scan.nextInt();
		
		if(input == 1)
		{
			System.out.println("How many points?");
			input = scan.nextInt();
			points = new Point[input];
			points = generateRandomPoints(input, ran);
			
			quickSort = new QuickSorter(points);
			inSort = new InsertionSorter(points);
			mergeSort = new MergeSorter(points);
			selSort = new SelectionSorter(points);	
		}
		else if(input == 2)
		{
			System.out.println("File Name(must end in .txt):");
			String fileName = scan.next();

				quickSort = new QuickSorter(fileName);
				inSort = new InsertionSorter(fileName);
				mergeSort = new MergeSorter(fileName);
				selSort = new SelectionSorter(fileName);	
				
		}
		else if(input == 3)
		{
			System.out.println("Trial Terminated");
			end = true;
			scan.close();
			System.exit(0);
		}
		
		System.out.println("1) Sort By X-Coordingate   2)Sort By Polar-Angle");
		int order = scan.nextInt();
		
		selSort.sort(order);
		inSort.sort(order);
		mergeSort.sort(order);
		quickSort.sort(order);
		
		System.out.println("Algorithm\tSize\tTime");
		System.out.println("---------------------------------------");
		System.out.println(selSort.stats());
		selSort.draw();
		
		System.out.println(inSort.stats());
		inSort.draw();
		
		System.out.println(quickSort.stats());
		quickSort.draw();
		
		System.out.println(mergeSort.stats() + "\n");
		mergeSort.draw();
	}
}
