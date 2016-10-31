package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException;
import java.util.InputMismatchException;
import java.lang.IllegalArgumentException; 


/**
 *  
 * @author Samuel Guenette
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
		algorithm = "Selection Sort";
		sortByAngle = false;
		outputFileName = "select.txt";
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
		algorithm = "Selection Sort";
		sortByAngle = false;
		outputFileName = "select.txt";
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
	{// CHECK
		sortingTime = System.nanoTime();
		setComparator(order);
			//sortByAngle = false;
			
			for(int i = 0; i<points.length-1;i++)
			{
				int min = i;
				for(int j = i+1; j<points.length; j++)
				{
					//if(points[j].compareTo(points[min])<=0)
					if(pointComparator.compare(points[j], points[min])<=0)
					{
						min = j;
					}
				}
				Point temp = points[i];
				points[i] = points[min];
				points[min] = temp;
				
			}
		sortingTime -= System.nanoTime();
		sortingTime = Math.abs(sortingTime);
	}	
}
