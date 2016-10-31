 package edu.iastate.cs228.hw2;

/**
 *  
 * @author Samuel Guenette
 *
 */

public class Point implements Comparable<Point>
{
	private int x; 
	private int y;
	
	public Point()  // default constructor
	{
		x = 0;
		y = 0;
	}
	
	public Point(int x, int y)
	{
		this.x = x;  
		this.y = y;   
	}
	
	public Point(Point p) { // copy constructor
		x = p.getX();
		y = p.getY();
	}

	public int getX()   
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || obj.getClass() != this.getClass())
		{
			return false;
		}
    
		Point other = (Point) obj;
		return x == other.x && y == other.y;   
	}

	/**
	 * Compare this point with a second point q in the left-to-right order. 
	 * @param 	q 
	 * @return  -1  if this.x < q.x || (this.x == q.x && this.y < q.y)
	 * 		    0   if this.x == q.x && this.y == q.y 
	 * 			1	otherwise 
	 */
	public int compareTo(Point q)
	{
		if(this.x < q.x || (this.x == q.x && this.y < q.y)) // Point is less than q
		{
			return -1;
		}
		else if(this.x == q.x && this.y == q.y )   //Point is equal to q
		{
			return 0;
		}
		else								//Point is greater than  
		{
			return 1;
		}
		
	}
	
	
	/**
	 * Output a point in the standard form (x, y). 
	 */
	@Override
    public String toString() 
	{
		String pt  = "(" + x + ", " + y + ")";
		return pt; 
	}
}
