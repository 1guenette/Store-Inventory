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
		algorithm = "merge sort";
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
		algorithm = "MergeSort";
		sortByAngle = false;
		outputFileName = "merge.txt";
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
		sortingTime = System.nanoTime();
		setComparator(order);
		mergeSortRec(points);
		sortingTime -= System.nanoTime();
		sortingTime = Math.abs(sortingTime);
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
		int n = pts.length;
				if (n == 1) 
					{
						return;
					}
				 int m = n/2;
				
				 Point[] left = new Point[m];
				 for(int i = 0; i<left.length; i++)
				 {
					 left[i] = pts[i];
				 }
				 	 
				Point right[] = new Point[n-m];
				int rLoc = 0;
				for(int i = m; i<n;i++)
				{
					right[rLoc] = pts[i];
					rLoc++;
				}
				 
				 mergeSortRec(left);
				 mergeSortRec(right);
				Point[] temp = merge(left, right);
				 //points = merge(left, right);
			for(int i = 0; i<temp.length; i++)
			{
				pts[i] = temp[i];
			}
	}

	
	// Other private methods in case you need ...
	
	private Point[] merge(Point[] left, Point[] right) //static is removed, should it be???
	{

		 int p = left.length;
		 int q = right.length;
		//create an empty array comb of length p+q
		 Point[] comb = new Point[p+q];
		 int i=0; //left pt 
		 int j=0;//right pt
		 
		 int z = 0;//pt on comb;
		 
				 while (i < p && j < q)
				 {
					 if(pointComparator.compare(left[i],right[j])<=0)
					 {	 
						 //append left[i] to comb
						 comb[z] = left[i];
						 i++;
						 z++;
					 }
					 else
					 {
						 //append right[j] to comb
						 comb[z] = right[j];
						 j++;
						 z++;
					 }
				 }
				
				 if (i >= p)
				 {
					 //append right[j],...,right[q-1] to comb
					 for(int r = j; r<q; r++)
					 {
						comb[z] = right[r];
						z++;
					 }
				 }
				 else
				 {
					 //append left[i],...,left[p-1] to comb
					 for(int r = i; r<p; r++)
					 {
						comb[z] = left[r];
						z++;
					 }
				 }
				return comb;	
	}

}
