package edu.iastate.cs228.hw3;
//Atanasoff 123
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DSLTest 
{
	public static void main(String args[]) throws FileNotFoundException
	{

//1		
//		DoublySortedList list = new DoublySortedList();
//		//System.out.println(list.toString());
//		list.add("orange", 2);
//		list.add("kiwi", 3);
//		list.add("banana", 2234);
//		list.add("kiwi", 10);
//		list.remove("banana");
//		list.remove(2);
//		System.out.println(list.toString());
//		System.out.println("a".compareTo("b"));
		
//2		
		Scanner scan = new Scanner(System.in);
		System.out.println("Choose a File");
		String fileName = scan.next();
		DoublySortedList list = new DoublySortedList(fileName);
		scan.close();
		
//		System.out.println(list.size());
//		System.out.println(list.toString());
//		list.bulkSell("TestInput4.txt");
//		System.out.println(list.size());
//		System.out.println(list.toString());
		
		
//		list.clearStorage();
//		System.out.println(list.size());
//		System.out.println(list.toString());
		
		
		
//		System.out.println(list.size());
//		System.out.println(list.toString());
//		Pair x = list.split();
//		System.out.println(((DoublySortedList) x.getFirst()).printInventoryN());
//		System.out.println(((DoublySortedList) x.getSecond()).printInventoryN());
//		System.out.println(list.size());
//		System.out.println(list.toString());
		
//		
//		
//		list.compactStorage();
//		System.out.println(list.toString());
//		System.out.println(list.toString());
	}
}
