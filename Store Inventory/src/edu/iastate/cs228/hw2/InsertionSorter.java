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
		algorithm = "Insertion Sort";
		sortByAngle = false;
		outputFileName = "insert.txt";
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
		algorithm = "Insertion Sort";
		sortByAngle = false;
	}
	
	
	/** 
	 * Perform insertion sort on the array points[] of the parent class AbstractSorter.  
	 * 
	 * @param order  1   by x-coordinate 
	 * 			     2   by polar angle 
	 */
	@Override 
	public void sort(int order)
	{// CHECK
		sortingTime = System.nanoTime()	;	
		setComparator(order);
			
			int length = points.length;
				for (int i = 1; i< length; i++)
				{ 
					Point temp = points[i];
					int j = i - 1;											
					while (j > -1 && (pointComparator.compare(temp,points[j]) <= 0))
					{
						points[j+1] = points[j];
						--j;
						points[j+1] = temp;
					}
				}
		sortingTime -= System.nanoTime();
		sortingTime = Math.abs(sortingTime);
	}		
}
