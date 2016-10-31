package edu.iastate.cs228.hw3;

import java.io.File;
import java.io.FileNotFoundException; 
import java.util.Scanner;


import java.util.Comparator;

/**
 *10/28/2016 
 * @author Samuel Guenette

 *
 */

/**
 * IMPORTANT: In the case of any minor discrepancy between the comments before a method
 * and its description in the file proj3.pdf, use the version from the file. 
 *
 */

public class DoublySortedList 
{
	private int size;     			// number of different kinds of fruits	 
	private Node headN; 			// dummy node as the head of the sorted linked list by fruit name 
	private Node headB; 			// dummy node as the head of the sorted linked list by bin number

	/**
	 *  Default constructor constructs an empty list. Initialize size. Set the fields nextN and 
	 *  previousN of headN to the node itself. Similarly, set the fields nextB and previousB of 
	 *  headB. 
	 */
	public DoublySortedList()
	{
		size = 0;
		headN = new Node();
		headB = new Node();
	}

	/**
	 * Constructor over an inventory file consists of lines in the following format  
	 * 
	 * <fruit>  <quantity>  <bin> 
	 * 
	 * Throws an exception if the file is not found. 
	 * 
	 * You are asked to carry out the following operations: 
	 * 
	 *     1. Scan line by line to construct one Node object for each fruit.  
	 *     2. Create the two doubly-linked lists, by name and by bin number, respectively,
	 *        on the fly as the scan proceeds. 
	 *     3. Perform insertion sort on the two lists. Use the provided BinComparator and 
	 *        NameComparator classes to generate comparator objects for the sort.
	 *        
	 * @inventoryFile    name of the input file 
	 */
	public DoublySortedList(String inventoryFile) throws FileNotFoundException
	{
		//PASS
		headN = new Node();
		headN.nextN = headN;
		headN.previousN = headN;

		headB = new Node();
		headB.nextB = headB;
		headB.previousB = headB;
		size = 0;

		//PASS
		File file = new File(inventoryFile);
		Scanner reader = new Scanner(file);

		Node temp = null;		

		while(reader.hasNext())
		{
			String input = reader.nextLine(); 

			Scanner lineReader = new Scanner(input);
			String name = lineReader.next();
			int quantity = lineReader.nextInt();
			int binNum = lineReader.nextInt(); 
			lineReader.close();

			if(size == 0)
			{
				Node newFruit = new Node(name, quantity, binNum, headN, headN, headB, headB);
				insertN(newFruit, headN, headN);
				insertB(newFruit, headB, headB);
				size++;
				temp= newFruit;
			}
			else
			{
				Node newFruit = new Node(name, quantity, binNum, temp, headN, temp, headB);
				insertN(newFruit, headN, temp);
				insertB(newFruit, headB, temp);
				temp = newFruit;
				size++; 
			}
		}
		NameComparator compN = new NameComparator();
		BinComparator compB = new BinComparator();
		this.insertionSort(true, compN);
		this.insertionSort(false, compB);
		reader.close();
	}


	/**
	 * Called by split() and also used for testing.  
	 * 
	 * Precondition: The doubly sorted list has already been created. 
	 * 
	 * @param size
	 * @param headN
	 * @param headB
	 */
	public DoublySortedList(int size, Node headN, Node headB)
	{
		this.size = size; 
		this.headN = headN;
		this.headB = headB;
		this.headN.nextN = headN;
		this.headB.nextB = headB;
		this.headB.previousB = headB;
		this.headN.previousN = headN;
	}


	public int size()
	{
		return size; 
	}


	/**
	 * Add one type of fruits in given quantity (n). 
	 * 
	 *     1. Search for the fruit. 
	 *     2. If already stored in some node, simply increase the quantity by n
	 *     3. Otherwise, create a new node to store the fruit at the first available bin.
	 *        add it to both linked lists by calling the helper methods insertN() and insertB(). 
	 *        
	 * The case n == 0 should result in no operation.  The case n < 0 results in an 
	 * exception thrown. 
	 * 
	 * @param fruit                      name of the fruit to be added 
	 * @param n	                      quantity of the fruit
	 * @throws IllegalArgumentException  if n < 0 
	 */
	public void add(String fruit, int n) throws IllegalArgumentException
	{
		// PASS
		if(n>0)
		{
			Node val = headN.nextN;

			if(size !=0) //adds to existing fruit
			{
				while(val != headN)
				{
					if(val.fruit.equals(fruit))
					{
						val.quantity = val.quantity + n;

						return;
					}
					val= val.nextN;
				}	 
			}

			//IF NODE IS NOT FOUND
			Node newFruit = new Node();

			if(size == 0) //If node list is empty
			{
				newFruit = new Node(fruit, n, 1, headN, headN, headB, headB);
				this.insertN(newFruit, headN, headN); 
				this.insertB(newFruit, headB, headB); 

				size++;
			}
			else
			{
				val = headB.nextB;
				boolean addEnd = true;
				int counter = 1;

				while(val != headB)
				{
					if(val.bin != counter)
					{
						newFruit = new Node(fruit,n,counter, val, val.previousN, val, val.previousB);
						insertB(newFruit, val.previousB, val);
						addEnd = false;
						break; //Escapes loop

					}				 
					counter++;
					val = val.nextB;
				}


				if(addEnd == true) //If no available bin number between nodes 
				{
					newFruit = new Node(fruit,n,counter, val, val.previousN, val, val.previousB);
					insertB(newFruit, val.previousB, val); 
				}


				//Updates the Alphabetical Node List  
				boolean end = true; 
				NameComparator comp = new NameComparator();
				val = headN.nextN;
				while(val != headN)
				{
					if(comp.compare(newFruit,val)<0)
					{
						insertN(newFruit, val.previousN, val);
						end = false;
						break; //escapes loop
					}				 
					val = val.nextN;
				}

				if(end == true)
				{
					insertN(newFruit, headN.previousN, headN );
				}


				size++;

			}
		}
	}


	/**
	 * The fruit list is not sorted.  For efficiency, you need to sort by name using quickSort. 
	 * Maintain two arrays fruitName[] and fruitQuant[].  
	 * 
	 * After sorting, add the fruits to the doubly-sorted list (see project description) using 
	 * the algorithm described in Section 3.2 of the project description. 
	 * 
	 * Ignore a fruit if its quantity in fruitFile is zero.  
	 * 
	 * @param fruitFile                  list of fruits with quantities. one type of fruit 
	 *                                   followed by its quantity per line.
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException  if the quantity specified for some fruit in fruitFile 
	 *                                   is negative.
	 */
	public void restock(String fruitFile) throws FileNotFoundException, IllegalArgumentException
	{		
		//PASS
		int length = 0;
		File file = new File(fruitFile);
		Scanner scan = new Scanner(file);
		while(scan.hasNextLine()) 
		{
			scan.nextLine();
			length++;
		}
		scan.close();

		Integer[]fruitQuant = new Integer[length];
		String[] fruitName = new String[length];

		Scanner reader = new Scanner(file); //reinitialize to scan again	
		int i = 0;
		while(reader.hasNextLine()) 
		{
			String line = reader.nextLine();
			Scanner lineReader = new Scanner(line);
			String name = lineReader.next();
			int quant = lineReader.nextInt();	
			lineReader.close();
			fruitQuant[i] = quant;
			fruitName[i] = name;	
			i++;
		}
		scan.close();

		this.quickSort(fruitName, fruitQuant, length-1);
		for(int j = 0; j<length; j++)
		{
			add(fruitName[j], fruitQuant[j]);  //can't use sell
		}


	}


	/**
	 * Remove a fruit from the inventory. 
	 *  
	 *     1. Search for the fruit on the N-list.  
	 *     2. If no existence, make no change. 
	 *     3. Otherwise, call the private method remove() on the node that stores 
	 *        the fruit to remove it. 
	 * 
	 * @param fruit
	 */
	public void remove(String fruit)
	{
		//PASS
		Node val = headN.nextN;

		while(val != headN)
		{
			if(val.fruit.equals(fruit))
			{
				remove(val);	
			} 
			val = val.nextN;
		}



	}


	/**
	 * Remove all fruits stored in the bin.  Essentially, remove the node.  The steps are 
	 * as follows: 
	 * 
	 *     1. Search for the node with the bin in the B-list.
	 *     2. No change if it is not found.
	 *     3. Otherwise, call remove() on the found node. 
	 * 
	 * @param bin   
	 * @throws IllegalArgumentException  if bin < 1
	 */
	public void remove(int bin) throws IllegalArgumentException
	{
		//PASS
		Node val = headB.nextB;

		for(int i = 0; i<size; i++)
		{	if(val.bin == bin)
		{
			remove(val);
		}
		val = val.nextB;
		}

	}


	/**
	 * Sell n units of a fruit. 
	 * 
	 * Search the N-list for the fruit.  Return in the case no fruit is found.  Otherwise, 
	 * a Node node is located.  Perform the following: 
	 * 
	 *     1. if n >= node.quantity, call remove(node). 
	 *     2. else, decrease node.quantity by n. 
	 *     
	 * @param fruit
	 * @param n	
	 * @throws IllegalArgumentException  if n < 0
	 */
	public void sell(String fruit, int n) throws IllegalArgumentException 
	{
		//PASS
		Node val = headN.nextN;
		while(val != headN)
		{	if(val.fruit.equals(fruit))
		{
			val.quantity -= n;
			if(val.quantity <= 0)
			{
				remove(val);
			}
		}
		val = val.nextN;
		}

	}


	/** 
	 * Process an order for multiple fruits as follows.  
	 * 
	 *     1. Sort the ordered fruits and their quantities by fruit name using the private method 
	 *        quickSort(). 
	 *     2. Traverse the N-list. Whenever a node with the next needed fruit is encountered, 
	 *        let m be the ordered quantity of this fruit, and do the following: 
	 *        a) if m < 0, throw an IllegalArgumentException; 
	 *        b) if m == 0, ignore. 
	 *        c) if 0 < m < node.quantity, decrease node.quantity by n. 
	 *        d) if m >= node.quanity, call remove(node).
	 * 
	 * @param fruitFile
	 */
	public void bulkSell(String fruitFile) throws FileNotFoundException, IllegalArgumentException
	{
		//PASS
		int length = 0;
		File file = new File(fruitFile);
		Scanner scan = new Scanner(file);
		while(scan.hasNextLine()) //works to get length?
		{
			scan.nextLine();
			length++;
		}
		scan.close();

		Integer[]fruitQuant = new Integer[length];
		String[] fruitName = new String[length];

		Scanner reader = new Scanner(file); //reinitialize to scan again	
		//for(int i=0; i<length; i++)
		int i = 0;
		while(reader.hasNextLine()) 
		{
			String line = reader.nextLine();
			Scanner lineReader = new Scanner(line);
			String name = lineReader.next();
			int quant = lineReader.nextInt();	
			lineReader.close();
			fruitQuant[i] = quant;
			fruitName[i] = name;	
			i++;
		}
		scan.close();

		this.quickSort(fruitName, fruitQuant, length-1);
		for(int j = 0; j<length; j++)
		{
			sell(fruitName[j], fruitQuant[j]);  //can't use sell
		}


	}


	/**
	 * 
	 * @param fruit
	 * @return quantity of the fruit (zero if not on stock)
	 */
	public int inquire(String fruit)
	{
		//PASS
		Node val = headN.nextN;
		int quant = 0;

		while(val != headN)
		{	if(val.fruit.equals(fruit))
		{
			quant = val.quantity; 
		}
		val = val.nextN;
		}
		return quant;
	}


	/**
	 * Output a string that gets printed out as the inventory of fruits by names.  
	 * The exact format is given in Section 5.1.  Here is a sample:   
	 *
	 *  
	 * fruit   	quantity    bin
	 * ---------------------------
	 * apple  			50  	5
	 * banana    		20      9
	 * grape     		100     8
	 * pear      		40      3 
	 */
	public String printInventoryN()
	{	 
		//PASS


		String output = String.format("%-16s%-16s%-16s\n", "fruit", "quantity", "bin\n");
		output += "--------------------------------------\n";

		Node val = headN.nextN;
		while(val != headN)
		{
			output += String.format("%-16s%-16d%-16d\n", val.fruit, val.quantity, val.bin);

			val = val.nextN;
		}
		return output; 
	}

	/**
	 * Output a string that gets printed out as the inventory of fruits by storage bin. 
	 * Use the same formatting rules for printInventoryN().  Below is a sample:  
	 * 
	 * bin 	fruit     	quantity      
	 * ----------------------------
	 * 3		pear      	40             
	 * 5		apple     	50            
	 * 8		grape     	100           
	 * 9		banana    	20  
	 *           	 
	 */
	public String printInventoryB()
	{
		//PASS
		String output = String.format("%-16s%-16s%-16s\n", "bin", "fruit", "quantity\n");
		output += "--------------------------------------\n";
		Node val = headB.nextB;
		while(val != headB)
		{
			output += String.format("%-16d%-16s%-16d\n", val.bin, val.fruit, val.quantity);
			val = val.nextB;
		}
		return output; 

	}


	@Override
	/**
	 *  The returned string should be printed out according to the format in Section 5.1, 
	 *  exactly the same required for printInventoryN(). 
	 */
	public String toString()
	{
		//Pass

		String output = String.format("%-16s%-16s%-16s\n", "fruit", "quantity", "bin\n");
		output += "--------------------------------------\n";

		Node val = headN.nextN;
		while(val != headN)
		{
			//output += val.fruit + "\t" + val.quantity + "\t" + val.bin + "\n"; 
			output += String.format("%-16s%-16d%-16d\n", val.fruit, val.quantity, val.bin);

			val = val.nextN;
		}
		return output; 

	}

	/**
	 *  Relocate fruits to consecutive bins starting at bin 1.  Need only operate on the
	 *  B-list. 
	 */
	// 
	public void compactStorage()
	{
		//CHECK
		Node val = headB.nextB;
		int i =0;
		//for(int i = 0; i<size; i++)
		while(val!=headB)
		{	
			val.bin = i+1;
			val = val.nextB;
			i++;
		}
	}


	/**
	 *  Remove all nodes storing fruits from the N-list and the B-list.
	 */
	public void clearStorage()
	{
		headB.nextB = headB;
		headB.previousB = headB;
		headN.nextN = headN;
		headN.previousN = headN;
		size = 0; 
	}


	/** 
	 * Split the list into two doubly-sorted lists DST1 and DST2.  Let N be the total number of 
	 * fruit types. Then DST1 will contain the first N/2 types fruits in the alphabetical order.  
	 * DST2 will contain the remaining fruit types.  The algorithm works as follows. 
	 * 
	 *     1. Traverse the N-list to find the median of the fruits by name. 
	 *     2. Split at the median into two lists: DST1 and DST2.  
	 *     3. Traverse the B-list.  For every node encountered add it to the B-list of DST1 or 
	 *        DST2 by comparing node.fruit with the name of the median fruit. 
	 *   
	 * DST1 and DST2 must reuse the nodes from this list. You cannot make a copy of each node 
	 * from this list, and arrange these copy nodes into DST1 and DST2. 
	 * 
	 * @return   two doubly-sorted lists DST1 and DST2 as a pair. 
	 */
	public Pair<DoublySortedList> split()
	{
		// REVIEW 
		NameComparator comp = new NameComparator();
		int med = size/2;
		Node medianNode = headN.nextN;
		for(int i=0; i<med; i++)
		{
			medianNode = medianNode.nextN;
		}
		//Account for odd numbered Node

		Node headN1 = new Node();
		Node headB1 = new Node();
		Node headN2 = new Node();
		Node headB2 = new Node();

		DoublySortedList DST1 = new DoublySortedList(0, headN1, headB1);
		DoublySortedList DST2 = new DoublySortedList(0, headN2, headB2); 



		Node val = headB.nextB;
		Node last1 = DST1.headB;  //head1B
		Node last2 = DST2.headB;  //head2B

		while(val != headB)
		{
			if(comp.compare(val,medianNode)<=0)
			{

				val = val.nextB;
				DST1.insertB(val.previousB, DST1.headB, DST1.headB.nextB);	
				DST1.insertN(val.previousB, DST1.headN, DST1.headN.nextN);	
				DST1.size++;

				//last1 = val;
			}
			else
			{
				val = val.nextB;
				DST2.insertB(val.previousB, DST2.headB, DST2.headB.nextB);
				DST2.insertN(val.previousB, DST2.headN, DST2.headN.nextN);
				DST2.size++;
			}
		}

		DST1.insertionSort(true, comp);
		DST2.insertionSort(true, comp);

		BinComparator compB = new BinComparator();
		DST1.insertionSort(false, compB);
		DST2.insertionSort(false, compB);

		Pair<DoublySortedList> pair = new Pair(DST1,DST2);
		this.clearStorage();
		return pair; 
	}


	/**
	 * Perform insertion sort on the doubly linked list with head node using a comparator 
	 * object, which is of either the NameComparator or the Bincomparator class. 
	 * 
	 * Made a public method for testing by TA. 
	 * 
	 * @param sortNList   sort the N-list if true and the B-list otherwise. 
	 * @param comp
	 */
	public void insertionSort(boolean sortNList, Comparator<Node> comp)
	{
		// PASS
		if(sortNList == true)
		{
			Node ref = headN.nextN;
			while(!ref.equals(headN))
			{
				Node val = ref.previousN;
				while (val != headN && comp.compare(ref,val)<0)
				{
					swapNode(ref,val, true);

					val = ref.previousN;
				}	
				ref = ref.nextN;	
			}
		}
		else //This part won't work
		{
			Node ref = headB.nextB;	 	
			while(!ref.equals(headB))
			{  
				Node val = ref.previousB;
				while (val != headB && comp.compare(ref,val)<0)
				{
					swapNode(ref,val, false);

					val = ref.previousB;
				}	
				ref = ref.nextB;				
			}
		}
	}


	/**
	 * Sort an array of fruit names using quicksort.  After sorting, quant[i] is the 
	 * quantity of the fruit with name[i].  
	 * 
	 * Made a public method for testing by TA. 
	 * 
	 * @param size		number of fruit names 
	 * @param fruit   	array of fruit names 
	 * @param quant	array of fruit quantities 
	 */
	public void quickSort(String[] fruit, Integer quant[], int size)
	{
		quickSortRec(fruit, quant, 0, size);

	}

	private void quickSortRec(String[] fruit, Integer quant[],int first, int last)
	{
		if (first >= last) 
		{
			return;
		}
		int p = partition(fruit, quant,first, last);
		quickSortRec(fruit, quant,first, p - 1);  
		quickSortRec(fruit, quant,p + 1, last);


	}


	// --------------
	// helper methods 
	// --------------

	/**
	 * Add a node between two nodes prev and next in the N-list.   
	 * 
	 * @param node
	 * @param prev  preceding node after addition 
	 * @param next  succeeding node 
	 */
	private void insertN(Node node, Node prev, Node next)
	{
		//GOOD
		prev.nextN = node;
		next.previousN = node;
		node.previousN = prev;
		node.nextN = next;
	}


	/**
	 * Add a node between two nodes prev and next in the B-list.  
	 * 
	 * @param node
	 * @param prev  preceding node 
	 * @param next  succeeding node 
	 */
	private void insertB(Node node, Node prev, Node next)
	{	 
		//PASS
		prev.nextB = node;
		next.previousB = node;
		node.previousB = prev;
		node.nextB = next; 
	}


	/**
	 * Remove node from both linked lists. 
	 * 
	 * @param node
	 */
	private void remove(Node node)
	{
		//PASS
		node.previousB.nextB = node.nextB;
		node.nextB.previousB = node.previousB;

		node.previousN.nextN = node.nextN;
		node.nextN.previousN = node.previousN;
		size--;
	}


	/**
	 * 
	 * @param name		name[first, last] is the subarray of fruit names 
	 * @param bin		bin[first, last] is the subarray of bins storing the fruits.
	 * @param first
	 * @param last
	 */

	/**
	 * 
	 * @param fruit    array of fruit names 
	 * @param quant	array of fruit quantities corresponding to fruit[]
	 * @param first
	 * @param last
	 * @return
	 */
	private int partition(String fruit[], Integer quant[], int first, int last)
	{

		//String name = fruit[first];
		String pivot = fruit[last];
		int i = first - 1;
		for(int j = first; j<=last-1;j++)
		{
			//if(points[j].compareTo(pivot)<=0)
			if(fruit[j].compareTo(pivot)<=0)
			{
				i++;
				swap(i,j, fruit, quant);
			}
		}
		swap(i+1,last, fruit, quant);
		return i+1;
	}




	protected void swap(int i, int j,String fruit[], Integer quant[])
	{
		//GOOD
		String tempName = fruit[i];
		fruit[i] = fruit[j];
		fruit[j] = tempName;
		int tempQuant = quant[i];
		quant[i] = quant[j];
		quant[j] = tempQuant;
	}

	private void swapNode(Node a, Node b, boolean byN)
	{
		if(byN == true)
		{  
			//			Node tempA = new Node(a.fruit, a.quantity, a.bin, a.nextN,a.nextN, a.nextB, a.nextB);
			//			Node tempB = new Node(b.fruit, b.quantity, b.bin, b.nextN,b.nextN, b.nextB, b.nextB);
			//						
			//			a.fruit = tempA.fruit;
			//			a.quantity = tempA.quantity;
			//			a.bin = tempA.bin;
			//			
			//			b.fruit = tempB.fruit;
			//			b.quantity = tempB.quantity;
			//			b.bin = tempB.bin;
			//			
			//						
			//			a.nextN = tempA.nextN;
			//			b.nextN = tempB.nextN;
			//			
			//			a.previousN = tempA.previousN;
			//			a.previousN = tempA.previousN;



			a.previousN = b.previousN;
			a.previousN.nextN = a;

			b.nextN = a.nextN;
			b.previousN = a;

			b.nextN.previousN = b; // replace with B? 
			a.nextN = b;
		} 
		else
		{
			a.previousB = b.previousB;
			a.previousB.nextB = a;

			b.nextB = a.nextB;
			b.previousB = a;

			b.nextB.previousB = b; // replace with B? 
			a.nextB = b;
		}

	}



}
