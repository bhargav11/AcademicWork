package app;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import structures.Stack;

public class Expression {

	public static String delims = " \t*+-/()[]";
			
    /**
     * Populates the vars list with simple variables, and arrays lists with arrays
     * in the expression. For every variable (simple or array), a SINGLE instance is created 
     * and stored, even if it appears more than once in the expression.
     * At this time, values for all variables and all array items are set to
     * zero - they will be loaded from a file in the loadVariableValues method.
     * 
     * @param expr The expression
     * @param vars The variables array list - already created by the caller
     * @param arrays The arrays array list - already created by the caller
     */
    public static void makeVariableLists(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
    	/** COMPLETE THIS METHOD **/
    	/** DO NOT create new vars and arrays - they are already created before being sent in
    	 ** to this method - you just need to fill them in.
    	 **/
    	StringTokenizer token = new StringTokenizer(expr, delims); 
    	String str = ""; 
    	while (token.hasMoreTokens()){ 
    		str = token.nextToken(); 
 
			if (Character.isLetter(str.charAt(0)) == true){ 
				
				int LastindexChar = str.length() + expr.indexOf(str); 
				
				if (token.hasMoreTokens() == false){ 
					vars.add(new Variable(str)); 
					break;
				}
				else if (expr.charAt(LastindexChar) == '['){ 
					
					if (arrays.contains(new Array(str))){continue;} 
					else {
						
						arrays.add(new Array(str)); 
					} 
				}
				else {
					
					if (vars.contains(new Variable(str))){continue;} 
					else {

						vars.add(new Variable(str)); 
					} 
				}
			}
		}
    	System.out.print(vars);//to check output for variables
        System.out.print(arrays); // to check output for array
    }
    
    /**
     * Loads values for variables and arrays in the expression
     * 
     * @param sc Scanner for values input
     * @throws IOException If there is a problem with the input 
     * @param vars The variables array list, previously populated by makeVariableLists
     * @param arrays The arrays array list - previously populated by makeVariableLists
     */
    public static void loadVariableValues(Scanner sc, ArrayList<Variable> vars, ArrayList<Array> arrays) 
    throws IOException {
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
            int numTokens = st.countTokens();
            String tok = st.nextToken();
            Variable var = new Variable(tok);
            Array arr = new Array(tok);
            int vari = vars.indexOf(var);
            int arri = arrays.indexOf(arr);
            if (vari == -1 && arri == -1) {
            	continue;
            }
            int num = Integer.parseInt(st.nextToken());
            if (numTokens == 2) { // scalar symbol
                vars.get(vari).value = num;
            } else { // array symbol
            	arr = arrays.get(arri);
            	arr.values = new int[num];
                // following are (index,val) pairs
                while (st.hasMoreTokens()) {
                    tok = st.nextToken();
                    StringTokenizer stt = new StringTokenizer(tok," (,)");
                    int index = Integer.parseInt(stt.nextToken());
                    int val = Integer.parseInt(stt.nextToken());
                    arr.values[index] = val;              
                }
            }
        }
    }
    
    /**
     * Evaluates the expression.
     * 
     * @param vars The variables array list, with values for all variables in the expression
     * @param arrays The arrays array list, with values for all array items
     * @return Result of evaluation
     */
    
   

    private static Stack<Character> makeOpStack (Stack<Character>Op, Stack<Character> reOp, String expr){
    	
    	
    	for (int k = 0; k < expr.length(); k++) {
    		if (expr.charAt(k) == '+' || expr.charAt(k) == '-'|| expr.charAt(k) == '*'|| expr.charAt(k) == '/'){ 
    			Op.push(expr.charAt(k)); 
    		}
    	}
    	
    	
    	while (!Op.isEmpty()) {
     		reOp.push(Op.pop()); 
    	}
    	return reOp; 
    }

    private static float OrderOfOperations(char op, float first, float second) {
    	if(op == '+') 
    	{
    		return first + second;
    	} 
    	else if(op == '-') 
    	{
    		return first - second;
    	}
    	else if(op == '*') 
    	{
    		return first * second;
    	} 
    	else if(op == '/') 
    	{
    		return first / second;
    	}
    	else 
    	{
    		return 0; 
    	}
    }
    
    private static Stack<Float> NumStack( String expr, Stack<Float> rVal, ArrayList<Variable> vars, ArrayList<Array> arrays){
    	Boolean checkIfDigit = false; 
    	Float num2; 
    	Boolean isOp = false; 
    	String num =""; 
    	Stack<Float> items = new Stack<Float>(); 
    	
    	for(int i = 0; i<expr.length(); i++) {
    		if(expr.charAt(i)=='+'||expr.charAt(i)=='-'||expr.charAt(i)=='*'||expr.charAt(i)=='/')
    		{
    			isOp = true;
    		} 
    		else 
    		{
    			isOp = false;
    		} 
    		
    		if(Character.isDigit(expr.charAt(i)) && isOp==false) { 
    			checkIfDigit=true; 
    			
    			for(int j = i; j<expr.length(); j++) { 
    				if(expr.charAt(j)=='+'||expr.charAt(j)=='-'||expr.charAt(j)=='*'||expr.charAt(j)=='/')
    				{
    					isOp = true;
    				} 
    				else 
    				{
    					isOp = false;
    				} 
    				
    				if(Character.isDigit(expr.charAt(j)) && isOp == false)
    				{
    					num += expr.charAt(j); 
    				}
    				else 
    				{
    					break;
    				} 
    				
    				i++; 
    			}
    			if(checkIfDigit==true) { 
    				
    				num2 = Float.parseFloat(num); 
					items.push(num2); 
					num = ""; 		
				}
    		}
    		
    		
    		else if ( Character.isLetter(expr.charAt(i)) ){ 
    			
    			for (; i < expr.length(); i++) { 
    				if ( Character.isLetter(expr.charAt(i)) ) { 
    	    			num += expr.charAt(i); 
    	    		}
    				else 
    				{
    					break;
    				}
    			}
		    								
		    			if (i<expr.length() && expr.charAt(i) == '[' ) 
		    				{
		    					i++; 
		    				for (int a = 0; a < arrays.size(); a++) { 
		    					if (num.equals(arrays.get(a).name)){
		    						num = ""; 
		    						while(i < expr.length()){
		    							
		    							 
		    		    				if (Character.isDigit(expr.charAt(i))||expr.charAt(i) == '.')
		    		    				{
		    		    					num += expr.charAt(i);
		    		    				} 
		    		    	    		else {	    	    		    	    			
		    		    	    			
		    		    	    			items.push((float)arrays.get(a).values[(int)Float.parseFloat(num)]); 
		    		    	    			num = ""; 		
		    		    	    			break;
		    		    	    		}
		    		    				i++;
		    		    			}
		    						break;
		    					}
		    				}
		    			}
		    			
		    			else { 
		    				for (int x = 0; x < vars.size(); x++) { 
		    					if ( num.equals(vars.get(x).name) ){ 
		    						items.push( (float)vars.get(x).value ); 
		    						num = ""; 
		    						break;
		    					}
		    				}
		    			}
    		}
    	}
    	
    	while (!items.isEmpty()) { 
     		rVal.push(items.pop()); 
    	}
    	return rVal; 
    }
    

    private static boolean Pemdas(char OpOnTop, Stack<Character> opStack) {
    	Boolean isPemdasTrue = true; 
    	if(!opStack.isEmpty() && opStack.peek()!=null) { 
    		
    		if((OpOnTop == '+' || OpOnTop == '-')&&(opStack.peek()=='*' || opStack.peek()=='/'))
    		{
    			isPemdasTrue = false;
    		}
    	}
    	return isPemdasTrue; 
    }
    
    public static float evaluate(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) 
    {
    	/** COMPLETE THIS METHOD **/
  
    	expr = expr.trim(); 
    	
    
		Stack<Character> op = new Stack<Character>(); 
		Stack<Float> items = new Stack<Float>(); 
		Stack<Character> opReverse= new Stack<Character>(); 
		Stack<Float> valReverse = new Stack<Float>(); 
		
		 
		int Openingindex = 0;
		int Closingindex = 0;
		int leftcount = 0;
		int rightcount = 0; 
		float result = 0f;
		
		String expr2;
		String recursionEval; 
    	int Length = 0; 
    	boolean isParenth = false;
		boolean isBracket = false;
    	
    	
    	
    	for(int i = 0; i < expr.length(); i++) {
    		if(expr.charAt(i)=='[') { 
    			Openingindex = i; 
    			i++;
    			isBracket=true; 
    			while(i<expr.length()) {
    				if(expr.charAt(i)== '[') 
    				{
    					leftcount++;
    				} 
    				else if(expr.charAt(i)==']')
    				{
    					rightcount++;
    				} 
    				if(expr.charAt(i)==']' && rightcount>leftcount) { 
    					Closingindex = i; 
    					rightcount = 0;
    					leftcount = 0; 
    					break;
    				}
    				i++;
    			}
    		}
    		if(isBracket==true) { 
	    		expr2 = expr; 
	    		expr = expr.substring(Openingindex+1, Closingindex); 
	        	recursionEval = expr2.substring(Openingindex, Closingindex+1); 
	        	
	        	Length = recursionEval.length(); 
   	
	        	recursionEval = "" + evaluate(expr, vars, arrays); 
	        	
	        	isBracket=false;

	        	expr = expr2.substring(0, Openingindex) + "[" +recursionEval +"]"+ expr2.substring(Closingindex+1, expr2.length()); 
	        	i = i - (Length - recursionEval.length()); 
	        	Openingindex = 0;
	        	Closingindex = 0; 
    		}
    	}
    	
    	
    	for (int i = 0; i < expr.length(); i++) {
    		
    		if ( expr.charAt(i) == '(') { 
    			Openingindex = i; 
    			i++;
    			isParenth = true; 
    			for (int j=i; j < expr.length(); j++){
    	    		if ( expr.charAt(j) == '(')
    	    		{
    	    			leftcount++;
    	    		} 
    	    		else if (expr.charAt(j) == ')')
    	    		{
    	    			rightcount++;
    	    		} 
    	    		if ( expr.charAt(j) == ')' && rightcount > leftcount){ 
    	    			Closingindex = j; 
    	    			leftcount = 0;
    	    			rightcount = 0;
    	    			break;
    				}
    	    		
    	    		i++;
    	    	}
    			if(isParenth==true) {
	            	expr2 = expr; 
	            	expr = expr.substring(Openingindex+1, Closingindex); 
	            	recursionEval = expr2.substring(Openingindex, Closingindex+1); 
	            	
	            	Length = recursionEval.length(); 
	            	
	            	
	            	recursionEval = "" + (int)evaluate(expr, vars, arrays); 
	            	isParenth=false;
	            	
	            	expr = expr2.substring(0, Openingindex) + recursionEval + expr2.substring(Closingindex+1, expr2.length());
	            	
	            	i = i - (Length - recursionEval.length()); 
    			}
    		}
    	}
        
    	
    	
    	op = makeOpStack(op, opReverse, expr); 
    	

    	items = NumStack( expr, valReverse, vars, arrays); 
    	int opSize = op.size();
    	
    	if (op.isEmpty())
    	{
    		return items.pop();
    	} 
    	
    	int j=0; 
    	while(j<opSize) {
    		
    		char topOperator = op.pop();
	    	if(Pemdas(topOperator, op)) {
	    		float firstNum;
	    		float secondNum;
	    		firstNum=items.pop(); 
	    		System.out.println(firstNum);
	    		secondNum=items.pop(); 
	    		System.out.println(secondNum);
	    		result = OrderOfOperations(topOperator, firstNum, secondNum); 
	    		items.push(result); 
	    		j++; 
    		}
    		
    		else{
    			float tempNumber = items.pop(); 
    			
    			
	    		float firstNum=items.pop(); 
	    		 
	    		float secondNum=items.pop(); 
	    		
	    		
	    		result = OrderOfOperations(op.pop(), firstNum, secondNum);
	    		items.push(result); 
	    		
	    		items.push(tempNumber); 
	    		op.push(topOperator); 
	    		j++;
    		}
    	}
    	
    	
    	return result; 
    }
   
    }