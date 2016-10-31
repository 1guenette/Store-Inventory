package edu.iastate.cs228.hw2;

/**
 *  
 * @Samuel Guenette
 *
 */

import java.util.Comparator;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.PrintWriter;



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
		
		points = new Point[pts.length];
		for(int i = 0; i<pts.length; i++)
		{
			points[i] = pts[i];
		}
		sortingTime = 0;
		lowestPoint = points[0];
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
		File file = new File(inputFileName); 
		
		int length = 0;
		Scanner lengthReader = new Scanner(file);
		while(lengthReader.hasNextInt())
		{
			lengthReader.nextInt();
			length++;
		}
		lengthReader.close();
		
		length/=2;
		points = new Point[length];
		
		Scanner reader = new Scanner(file);
		 int xPt = 0;
		 int yPt = 0;
		 int loc = 0; //Used as a reference for array location
		 while (reader.hasNextInt())
		 {
			 xPt = reader.nextInt();
			 yPt = reader.nextInt();
			 points[loc] = new Point(xPt, yPt);
			 loc++;
		 }
		reader.close();
		sortingTime = 0;
		lowestPoint = points[0];
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
		String val = algorithm+ "\t" + points.length + "\t" + sortingTime;
		return val; 
	}
	
	
	/**
	 * Write points[] to a string.  When printed, the points will appear in order of increasing
	 * index with every point occupying a separate line.  The x and y coordinates of the point are 
	 * displayed on the same line with exactly one blank space in between. 
	 */
	@Override
	public String toString()
	{
		String list = "";
		for(int i = 0; i<points.length;i++)
		{
			int x = points[i].getX();
			int y = points[i].getY();
			list += "(" + x + "," + y + ")" ;
			
		}
		return list;
	}

	/**
	 * This method is called after sorting for visually check whether the result is correct.  You  
	 * just need to generate a list of points and a list of segments, depending on the value of 
	 * sortByAngle, as detailed in Section 4.1. Then create a Plot object to call the method myFrame().  
	 */
	public void draw()
	{		
		int numSegs = 0;  // number of segments to draw 
		
		// Based on Section 4.1, generate the line segments to draw for display of the sorting result.
		// Assign their number to numSegs, and store them in segments[] in the order. 
		Segment[] segments = new Segment[numSegs]; 
		
		if(sortByAngle == true)
		{
			numSegs = (points.length-1) + (points.length-2);
			segments = new Segment[numSegs];
			int ref = 0;
			
			for(int i = 1; i<points.length; i++)
			{
				ref++;
				if(points[i]!= points[i-1])
				{
					segments[i-1] = new Segment(points[i-1], points[i]);
				}
			}
			
			for(int i = 2; i<points.length; i++)
			{
					segments[ref] = new Segment(points[0], points[i]);
					ref++;
			}
			//segments[numSegs-1] = new Segment(points[0], points[points.length-1]);
		}
		else
		{
			numSegs = points.length-1;
			segments = new Segment[numSegs];
			
			for(int i = 1; i<points.length; i++)
			{
				if(points[i]!= points[i-1])
				{
					segments[i-1] = new Segment(points[i-1], points[i]);
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
		//CHECK
		if(order == 1)
		{
			sortByAngle = false;
			for(int i = 0; i<points.length; i++)	//Finds lowest point
			{
				if(points[i].getX()<lowestPoint.getX() || (points[i].getX()==lowestPoint.getX() && points[i].getY()<lowestPoint.getY()))
				{
					lowestPoint = points[i];
				}
			}
			
			pointComparator = new Comparator<Point>()//Sets Comparator
			{
				@Override
				public int compare(Point p1, Point p2)
				{	
					return p1.compareTo(p2);
				}
			};			
		}
		else if(order == 2)
		{
			Point ref = points[0];
			for(int i = 1; i<points.length; i++)//Finds reference point
			{
				if(points[i].getY()<ref.getY() || (points[i].getY()==ref.getY() && points[i].getX()<ref.getX()))
				{
					ref = points[i];
					lowestPoint = points[i];
				} 
			}
			sortByAngle = true;
			
			pointComparator  = new PolarAngleComparator(ref);
		}
	}

	
	/**
	 * Swap the two elements indexed at i and j respectively in the array points[]. 
	 * 
	 * @param i
	 * @param j
	 */
	protected void swap(int i, int j)
	{
		Point temp = points[i];
		points[i] = points[j];
		points[j] = temp;
		
	}	
	
	 /**
	  * This method, called after sorting, writes point data into a file by outputFileName.<br>
	  * The format of data in the file is the same as printed out from toString().<br>
	  * The file can help you verify the full correctness of a sorting result and debug the underlying algorithm.
	  * 
	  * @throws FileNotFoundException
	  */
	 public void writePointsToFile(String fileName) throws FileNotFoundException 
	 {
		 File file = new File(fileName);
		 PrintWriter writer = new PrintWriter(file);
	
			 String val = points.toString();
			 writer.println(val);
		 
		 writer.close();
		 
	 }
}
