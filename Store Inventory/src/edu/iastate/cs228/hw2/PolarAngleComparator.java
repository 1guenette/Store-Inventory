package edu.iastate.cs228.hw2;

/**
 *  
 * @author Samuel Guenette
 *
 */

import java.util.Comparator;

/**
 * 
 * This class compares two points p1 and p2 by polar angle with respect to a reference point.  
 * It is known that the reference point is not above either p1 or p2, and in the case that
 * either or both of p1 and p2 have the same y-coordinate, not to their right. 
 *
 */
public class PolarAngleComparator implements Comparator<Point>
{
	private Point referencePoint; 
	
	/**
	 * 
	 * @param p reference point
	 */
	public PolarAngleComparator(Point p)
	{
		referencePoint = p; 
	}
	
	/**
	 * Use cross product and dot product to implement this method.  Do not take square roots 
	 * or use trigonometric functions. See the PowerPoint notes on how to carry out cross and 
	 * dot products. Calls private methods crossProduct() and dotProduct(). 
	 * 
	 * Call comparePolarAngle() and compareDistance(). 
	 * 
	 * @param p1
	 * @param p2
	 * @return  0 if p1 and p2 are the same point
	 *         -1 otherwise, if one of the following three conditions holds: 
	 *                a) p1 and referencePoint are the same point (hence p2 is a different point); 
	 *                b) neither p1 nor p2 equals referencePoint, and the polar angle of 
	 *                   p1 with respect to referencePoint is less than that of p2; 
	 *                c) neither p1 nor p2 equals referencePoint, p1 and p2 have the same polar 
	 *                   angle w.r.t. referencePoint, and p1 is closer to referencePoint than p2. 
	 *   
	 *          1  otherwise. p2 polar angle is less than p1
	 *                   
	 */
	public int compare(Point p1, Point p2)
	{
		if(p1.equals(p2))//Same Point
		{
			return 0;
		}
		else if(p1.equals(referencePoint)) //p2 is different point, p1 and ref are same
		{
			return -1;
		}
		else if(!p1.equals(referencePoint) && !p2.equals(referencePoint)&& comparePolarAngle(p1,p2)==-1) //p1Angle< p2Angle
		{
			return -1;
		
		}
		else if(!p1.equals(referencePoint) && !p2.equals(referencePoint)&& comparePolarAngle(p1,p2)==0 && compareDistance(p1,p2) == -1) //Same Angle
		{
			return -1;
		}
		return 1;
	}
	
	
	/**
	 * Compare the polar angles of two points p1 and p2 with respect to referencePoint.  Use 
	 * cross products.  Do not use trigonometric functions. 
	 * 
	 * Ought to be private but made public for testing purpose. 
	 * 
	 * @param p1
	 * @param p2
	 * @return    0  if p1 and p2 have the same polar angle.
	 * 			 -1  if p1 equals referencePoint or its polar angle with respect to referencePoint
	 *               is less than that of p2. 
	 *            1  otherwise. 
	 */
    public int comparePolarAngle(Point p1, Point p2) 
    {
    	Point vector1 = new Point(p1.getX()-referencePoint.getX(), p1.getY()-referencePoint.getY());
    	Point vector2 = new Point(p2.getX()-referencePoint.getX(), p2.getY()-referencePoint.getY());
    	

    	if(crossProduct(vector1,vector2) == 0 && ((p1.getX() != 0 && p1.getY() != 0) || (p2.getX()!=0 && p2.getY()!=0))&& crossProduct(vector1,vector2)==0) //same angle
    	{
    		return 0;
    	} 
    	else if(crossProduct(vector1,vector2)>0 || p1.equals(referencePoint))
    	{
    		return -1;
    	}
    	else  
    	{
    		return 1;
    	}
    
    }
    
    
    /**
     * Compare the distances of two points p1 and p2 to referencePoint.  Use dot products. 
     * Do not take square roots. 
     * 
     * Ought to be private but made public for testing purpose.
     * 
     * @param p1
     * @param p2
     * @return    0   if p1 and p2 are equidistant to referencePoint
     * 			 -1   if p1 is closer to referencePoint 
     *            1   otherwise (i.e., if p2 is closer to referencePoint)
     */
    public int compareDistance(Point p1, Point p2)
    {
    	double val1 = dotProduct(p1,p1);
    	double val2 = dotProduct(p1,p2);
    	
    	if(val1==val2)
    	{
    		return 0;
    	}
    	else if(val1<val2)
    	{
    		return -1;
    	}
    	else
    	{
    		return 0;
    	}
    	
    	
    }
    

    /**
     * 
     * @param p1
     * @param p2
     * @return cross product of two vectors p1 - referencePoint and p2 - referencePoint
     */
    private int crossProduct(Point p1, Point p2)
    {
    	int x1 = p1.getX();
    	int y1 = p1.getY();
    	int x2 = p2.getX();
    	int y2 = p2.getY();

    	int val = (x1*y2) - (x2*y1);

    	return val; 
    }

    /**
     * 
     * @param p1
     * @param p2
     * @return dot product of two vectors p1 - referencePoint and p2 - referencePoint
     */
    private int dotProduct(Point p1, Point p2)
    {
    	
    	int x1 = p1.getX();
    	int y1 = p1.getY();
    	int x2 = p2.getX();
    	int y2 = p2.getY();
   
    	int val = (x1*x2) + (y1*y2);
    	
    	return val; 
    }
}
