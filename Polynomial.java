package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		if(poly1 == null)
		{
			return poly2;
		}
		if(poly2 == null)
		{
			return poly1;
		}
		
		Node sumptr= null;
		Node front = null;
		Node after = null;
		boolean ans = false;
		Node ptr1=poly1;  // prepare to loop starting with second item x
		Node ptr2=poly2; //y
		if(ptr1.term.degree < ptr2.term.degree )
		{
			front = ptr1;
			sumptr = front;
			ptr1 = ptr1.next;
		}
		else if(ptr1.term.degree > ptr2.term.degree )
		{
			front = ptr2;
			sumptr = front;
			ptr2 = ptr2.next;
		}
		else //they are equal
		{
			
			front = new Node(ptr1.term.coeff + ptr2.term.coeff,ptr2.term.degree,null);
			sumptr= front;
			ptr1 = ptr1.next;
			ptr2 = ptr2.next;
		}
		while(true)
		{
			if(ptr1 == null && ptr2 == null)
			{
				break;
			}
			else if(ptr1 == null)
			{
				sumptr.next = ptr2;
				ptr2 = ptr2.next;
				
			}
			else if(ptr2 == null)
			{
				sumptr.next = ptr1;
				ptr1 = ptr1.next;
				
			}
			else if(ptr1.term.degree < ptr2.term.degree )
			{
				sumptr.next = ptr1;

				ptr1 = ptr1.next;
			}
			else if(ptr1.term.degree > ptr2.term.degree )
			{
				sumptr.next = ptr2;

				ptr2 = ptr2.next;
			}
			else if(ptr1.term.degree == ptr2.term.degree)//they are equal
			{
				
				sumptr.next = new Node(ptr1.term.coeff + ptr2.term.coeff,ptr2.term.degree,null);
				ptr1 = ptr1.next;
				ptr2 = ptr2.next;
			}
			//System.out.print(sumptr.term.coeff + "  ");
			//System.out.println(sumptr.term.degree);
			sumptr = sumptr.next;
		}
		//delete Zeroes is below
				Node ans1 = front;
				boolean a = false;
				while(ans1 != null)
				{
					if(ans1.term.coeff != 0)
					{
						a = false;
					}
					else
					{
						a = true;
						break;
					}
					ans1 = ans1.next;
				}
				if(a == false)
				{
					return front;
				}
				
				Node front1 = null; 
		        // If head node itself holds the key to be deleted 
		       while(true)
		       {
		    	   if(front == null)
		    	   {
		    		   return null;
		    	   }
		    	   if(front.term.coeff != 0)
		    	   {
		    		  front1 = new Node(front.term.coeff,front.term.degree,null);
		    		  break;
		    	   }
		    	   front = front.next;
		       }
		       
			
		       while(true)
		       {
		    	   if(front.next == null)
		    	   {
		    		   return front1;
		    	   }
			    	   if((front.term.coeff != 0)&&(front.term.coeff != front1.term.coeff))
			    	   {
			    		  front1.next = new Node(front.term.coeff,front.term.degree,null);
			    		  break;
			    	   }
			    	   front = front.next;
		    	   
		       }
		        // Search for the key to be deleted, keep track of the 
		        // previous node as we need to change temp.next 
		       
				return front1;
			}
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		Node ptr1=poly1;  
		Node ptr2=poly2; 
		Node front = null;
		Node front1 = null;
		Node mult = null;
		Node prev = null;
		int count = 0;
		if(poly1 == null || poly2 == null)
		{
			return null;
		}
		
			front = new Node(ptr1.term.coeff*ptr2.term.coeff,ptr1.term.degree + ptr2.term.degree,null);
			mult = front;
			ptr2 = ptr2.next;
		while (ptr1 != null)
		{
			
			while(ptr2 != null)
			{
				
				mult.next= new Node(ptr1.term.coeff * ptr2.term.coeff,ptr1.term.degree + ptr2.term.degree,null);
			
				mult = mult.next;
				ptr2 = ptr2.next;
			}
			//System.out.print(front.term.coeff + " ");
			//System.out.println(front.term.degree);
				front1 = add(front,front1);
				ptr1 = ptr1.next; //DO NOT TOUCH
				ptr2 = poly2; //DO NOT TOUCH
				if(ptr1 != null && ptr2 != null)
				{
					front = null;
					front = new Node(ptr1.term.coeff * ptr2.term.coeff,ptr1.term.degree + ptr2.term.degree,null);
				}
				ptr2= ptr2.next;
				mult = front;
			
				
		}
		
		
		return front1;
	}

	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	
		

				
	

		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		int sum = 0;
		Node front = poly;
		Node ptr = front;
		while(ptr != null)
		{
			sum += (ptr.term.coeff)*(Math.pow((double)x, ptr.term.degree));
			
			ptr = ptr.next;
		}
		
		return (float)sum;
		
	
	}
	
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
