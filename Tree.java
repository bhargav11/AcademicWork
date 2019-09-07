package structures;

import java.util.*;

/**
 * This class implements an HTML DOM Tree. Each node of the tree is a TagNode, with fields for
 * tag/text, first child and sibling.
 * 
 */
public class Tree {
	
	/**
	 * Root node
	 */
	TagNode root=null;
	
	/**fr33333
	 * Scanner used to read input HTML file when building the tree
	 */
	Scanner sc;
	
	/**
	 * Initializes this tree object with scanner for input HTML file
	 * 
	 * @param sc Scanner for input HTML file
	 */
	public Tree(Scanner sc) {
		this.sc = sc;
		root = null;
	}
	
	private TagNode makeBuild()
	{
		
		int length;
		String scan = null;
		boolean isNextLine = sc.hasNextLine();
		
		
		if (isNextLine == true)
			scan = sc.nextLine();
		else
			return null; 
		
		length = scan.length();
		boolean x = false;
		
		if (scan.charAt(0) == '<'){						
			scan = scan.substring(1, length - 1);
			if (scan.charAt(0) == '/')						
				return null;
			else {
				x = true; 
			}
		}
	
		TagNode tempNode = new TagNode (scan, null, null);
		if(x == true)
			tempNode.firstChild = makeBuild();											
		tempNode.sibling = makeBuild();												
		return tempNode;
	}
	
		
	public void build() {
		/** COMPLETE THIS METHOD **/
			
			root = makeBuild();
			
	}
		
			
		


	
	
	/**
	 * Replaces all occurrences of an old tag in the DOM tree with a new tag
	 * 
	 * @param oldTag Old tag
	 * @param newTag Replacement tag
	 */
	private void replaceTaghelper(TagNode item, String oldTag, String newTag)
	{
		/*steps: 
		 * 1.check if null
		 * 1. find old tag
		 * 2.check for equality
		 * 2. recurse through each node and change from old to new
		 * 
		 * */
		TagNode currentNode = item; 
		
		if(currentNode == null)
		{
			return;
		}
		
		if (currentNode.tag.equals(oldTag)) {
			
			currentNode.tag = newTag;
			
		}
		
		replaceTaghelper(currentNode.firstChild, oldTag, newTag);
		replaceTaghelper(currentNode.sibling, oldTag, newTag);
	}
private TagNode boldrowhelper2(TagNode currentNode) { 
		// Base Case
		if (currentNode == null)
			return null; 
		
		TagNode nodetemp = null;
		String strtemp = currentNode.tag;
		
		if(strtemp.equals("table")) { 
			nodetemp = currentNode; 
			return nodetemp;
		} 
		
		
		if(nodetemp == null) {
			nodetemp = boldrowhelper2(currentNode.firstChild);
		}
		
		if(nodetemp == null) { 
			nodetemp = boldrowhelper2(currentNode.sibling);
		} 
		
		
		
		return nodetemp;
	}
	private void removeTaghelper(TagNode previousNode, TagNode currentNode, String Tag2) {
		/*
		 * 1. null
		 * 2. Category 1: p, em and b tags, all occurrences of such a tag are deleted from the tree
		 * 3. Category 2: ol or ul tags, all occurrences of such a tag are deleted from the tree
		 *  all li tags immediately under are converted to p tags
		 * 
		 */
			//check if null
			if (currentNode == null)
			{
				return;
			}
			
			if(currentNode.tag.equals("em") || currentNode.tag.equals("p") || currentNode.tag.equals("b")) {
			if (currentNode.tag.equals(Tag2)) {
				
				if (previousNode.firstChild != null && previousNode.firstChild.tag.equals(currentNode.tag)) {						
					
					if(currentNode.sibling != null) 
					{
					
						if (currentNode.firstChild.sibling != null) 
						{												
							TagNode tempNode = currentNode.firstChild;
							previousNode.sibling = tempNode;
							while (tempNode.sibling != null)
							{
								tempNode = tempNode.sibling;
							}
							tempNode.sibling = currentNode.sibling;
							currentNode.firstChild = null;
							currentNode.sibling = null;
						}
						else {																				
							currentNode.firstChild.sibling = currentNode.sibling;
							previousNode.firstChild = currentNode.firstChild;
						}
					}
				
					else {
						
						previousNode.firstChild = currentNode.firstChild;							
					}
				}
				
				else if (previousNode.sibling != null) {
					
					if(currentNode.sibling != null) {
					if (currentNode.firstChild.sibling != null) {
						TagNode tempNode = currentNode.firstChild;
						previousNode.sibling = tempNode;
						while (tempNode.sibling != null) {
							tempNode = tempNode.sibling;
						}
						tempNode.sibling = currentNode.sibling;
						currentNode.firstChild = null;
						currentNode.sibling = null;
					}
						
					else {
						currentNode.firstChild.sibling = currentNode.sibling;					
						previousNode.sibling = currentNode.firstChild;
					}
					}
					
					else {
						
						previousNode.sibling = currentNode.firstChild;
					}
				}
			}
			}
			
			
			
			else if(currentNode.tag.equals("ol") || currentNode.tag.equals("ul")) {
						
					
					if (currentNode.tag.equals(Tag2)) {
						
						if (previousNode.firstChild != null && previousNode.firstChild.tag.equals(currentNode.tag)) {										// After the && is to prevent if 2 tags alike are next to each other
							
							if(currentNode.sibling != null) {
								if (currentNode.firstChild.sibling != null) {
									TagNode tempNode = currentNode.firstChild;
									while (tempNode.sibling != null) {
										if (tempNode.tag.equals("li"))
											tempNode.tag = "p";
										tempNode = tempNode.sibling;
									}
									
									if (tempNode.tag.equals("li"))
										tempNode.tag = "p";
									tempNode.sibling = currentNode.sibling;
									previousNode.firstChild = currentNode.firstChild;
								}
								else {
									if (currentNode.firstChild.tag.equals("li")) 
										currentNode.firstChild.tag = "p";
									currentNode.firstChild.sibling = currentNode.sibling;
									previousNode.firstChild = currentNode.firstChild;
								}
							}
							
							else {
								if (currentNode.firstChild.sibling != null) {
									TagNode tempNode = currentNode.firstChild;
									while(tempNode.sibling != null) {
										if (tempNode.tag.equals("li"))
											tempNode.tag = "p";
										tempNode = tempNode.sibling;
									}
									if (tempNode.tag.equals("li"))
										tempNode.tag = "p";
									previousNode.firstChild = currentNode.firstChild;
								}
								else {
									if (currentNode.firstChild.tag.equals("li")) 
										currentNode.firstChild.tag = "p";	
									previousNode.firstChild = currentNode.firstChild;							
								}
							}
						}
						
						else if (previousNode.sibling != null) {
							
							if(currentNode.sibling != null) {
								
								if (currentNode.firstChild.tag.equals("li"))
									currentNode.firstChild.tag = "p";
							
								if (currentNode.firstChild.sibling != null) {												
									TagNode tempNode = currentNode.firstChild;
									previousNode.sibling = tempNode;
									while (tempNode.sibling != null) {
										if (tempNode.tag.equals("li"))
											tempNode.tag = "p";
										tempNode = tempNode.sibling;
									}
									if (tempNode.tag.equals("li"))
										tempNode.tag = "p";
									tempNode.sibling = currentNode.sibling;
									currentNode.firstChild = null;
									currentNode.sibling = null;
								}
								else {
									currentNode.firstChild.sibling = currentNode.sibling;
									previousNode.sibling = currentNode.firstChild;
								}
							}
							
							else {
								if (currentNode.firstChild.sibling != null) {
									TagNode tempNode = currentNode.firstChild;
									while(tempNode.sibling != null) {
										if (tempNode.tag.equals("li"))
											tempNode.tag = "p";
										tempNode = tempNode.sibling;
									}
									if (tempNode.tag.equals("li"))
										tempNode.tag = "p";
									previousNode.sibling = currentNode.firstChild;
								}
								else {
									if (currentNode.firstChild.tag.equals("li"))
										currentNode.firstChild.tag = "p";
									previousNode.sibling = currentNode.firstChild;
								}
							}
						}
					}
					}
			
			
			removeTaghelper(currentNode, currentNode.firstChild, Tag2);
			removeTaghelper(currentNode, currentNode.sibling, Tag2);
		}
	private void addTaghelper(TagNode previousNode, TagNode currentNode, String wordToBeTagged, String Tag2) {
		
		
		if (currentNode == null)
			return;
		
		
		if(previousNode != null && previousNode.tag.equals(Tag2)){
			return;
		}
		
		if (Tag2.equals("html") || Tag2.equals("body") || Tag2.equals("p") || Tag2.equals("em") || Tag2.equals("b") ||
				Tag2.equals("table") || Tag2.equals("tr") || Tag2.equals("td") || Tag2.equals("ol") || Tag2.equals("ul") || Tag2.equals("li")) {
		
			if(currentNode.tag.equals("html") || currentNode.tag.equals("body") || currentNode.tag.equals("p") || currentNode.tag.equals("em") || currentNode.tag.equals("b") ||
					currentNode.tag.equals("table") || currentNode.tag.equals("tr") || currentNode.tag.equals("td") || currentNode.tag.equals("ol") || currentNode.tag.equals("ul") ||
					currentNode.tag.equals("li")) {
				}
		
		else {
		
			
			String[] array = currentNode.tag.split(" ");
			int len = array.length;
			
			//System.out.println(len);
			String before = "";
			String target = "";
			String after = "";
			TagNode tempNode = new TagNode(Tag2, null, null);
			
			if (len == 1) {
				for (int i = 0; i < len; i++) {
					
					if (wordCheck(array[i], wordToBeTagged)) {
						
						if (previousNode.firstChild == currentNode) {
							if (currentNode.sibling != null) {
								
								previousNode.firstChild = tempNode;
								tempNode.firstChild = currentNode;
								tempNode.sibling = currentNode.sibling;
								currentNode.sibling = null;
							}
							else {
							
								previousNode.firstChild = tempNode;
								tempNode.firstChild = currentNode;
							}
						}
						if (previousNode.sibling == currentNode) {
							if (currentNode.sibling != null) {
							
								previousNode.sibling = tempNode;
								tempNode.firstChild = currentNode;
								tempNode.sibling = currentNode.sibling;
								currentNode.sibling = null;
							}
							else {
						
								previousNode.sibling = tempNode;
								tempNode.firstChild = currentNode;
							}
						}
					}
				}	
			}
				else {
					TagNode headptr = null;
					TagNode tailptr = null;
					boolean checkBefore = true;
					boolean checkTarget = true;
					boolean checkAfter = true;
					while (checkAfter == true) {
						TagNode beforeTargetNode = new TagNode(null, null, null);
						TagNode targetTargetNode = new TagNode(null, null, null);
						TagNode afterTargetNode = new TagNode(null, null, null);
						before = "";
						target = "";
						after = "";
						for (int n = 0; n < len && (checkTarget == true); n++) {
							System.out.println(array[n]);
							if (wordCheck(array[n], wordToBeTagged)) {
								
								checkBefore = false;
								checkTarget = false;
								
								target = array[n];
								targetTargetNode.tag = target;
							
								if (n != len - 1) {
								for (int i = n + 1; i < len; i++) {
									
									after = after + array[i] + "";
									
								}
								afterTargetNode.tag = after;
								}
							}
							else if (checkBefore == true) {
								before = before + array[n] + "";
								beforeTargetNode.tag = before;
								
							}
						}
						
				
						if (checkTarget == true){
							if (previousNode.firstChild == currentNode)
								previousNode.firstChild = beforeTargetNode;
							if (previousNode.sibling == currentNode)
								previousNode.sibling = beforeTargetNode;
							break;
						}
					
						if (beforeTargetNode.tag != null && targetTargetNode.tag != null && afterTargetNode.tag != null) {
							beforeTargetNode.sibling = tempNode;
							tempNode.firstChild = targetTargetNode;
							tempNode.sibling = afterTargetNode;
						}
						else if (beforeTargetNode.tag != null && targetTargetNode.tag != null) {
							beforeTargetNode.sibling = tempNode;
							tempNode.firstChild = targetTargetNode;
							
						}
						else if (afterTargetNode.tag != null) {
							tempNode.firstChild = targetTargetNode;
							tempNode.sibling = afterTargetNode;
						}
						
						if (headptr == null && beforeTargetNode.tag != null)
							headptr = beforeTargetNode;
						else if (headptr == null && beforeTargetNode.tag == null) {
							tempNode.firstChild = targetTargetNode;
							headptr = tempNode;
						}
						else
							tailptr = targetTargetNode;
						
						if (afterTargetNode.tag != null) {
							checkAfter = true;
							array = afterTargetNode.tag.split(" ");
							len = array.length;
							if (headptr == null && beforeTargetNode.tag != null)
								headptr = beforeTargetNode;
							else if (headptr == null && beforeTargetNode.tag == null) {
								tempNode.firstChild = targetTargetNode;
								headptr = tempNode;
							}
							else
								tailptr = afterTargetNode;
						}
						else
							checkAfter = false;
					}
					
					if (previousNode.firstChild == currentNode)
						previousNode.firstChild = headptr;
					else if (previousNode.sibling == currentNode)
						previousNode.sibling = headptr;
					if(currentNode.sibling == null) {
						
					}
					else {
						tailptr.sibling = currentNode.sibling;
						currentNode.sibling = null;
					}
				}

			//aaaa
		}
		//System.out.println(root);
		
		addTaghelper(currentNode, currentNode.firstChild, wordToBeTagged, Tag2);
		addTaghelper(currentNode, currentNode.sibling,  wordToBeTagged, Tag2);
		
		}
	}
	private boolean wordCheck (String currentWord, String wordToBeFound) {
		String Lcurrword = currentWord.toLowerCase();
		String Ltargetword = wordToBeFound.toLowerCase();
		
	
		if(Lcurrword.equals(Ltargetword))
		{
			return true;
		}
	
		
		
		char last = currentWord.charAt(currentWord.length() - 1);
		//System.out.println(Ltargetword);
		//System.out.println(Lcurrword.substring(0, currentWord.length()-1));
		
		if (Character.isLetter(last))
		
			return false;
		
		
		else if (Ltargetword.equals(Lcurrword.substring(0, currentWord.length()-1)))
			return true;
		else
			return false;
	}
	private void boldRowhelper(int row)
	{
		TagNode tempNode;
		TagNode currentNode = new TagNode(null, null, null);										
		
		
		
		currentNode = boldrowhelper2(root);
		
		if (currentNode == null) {
			System.out.println("No table found");
			return;
		}
		
		
		currentNode = currentNode.firstChild;
		
	
		for(int i = 1; i < row; i++) {
			currentNode = currentNode.sibling;
		} 
		

		for (tempNode = currentNode.firstChild; tempNode != null; tempNode = tempNode.sibling) {
			tempNode.firstChild = new TagNode("b", tempNode.firstChild, null);
			
		}
	}
	
	public void replaceTag(String oldTag, String newTag) {
		/** COMPLETE THIS METHOD **/
		/*steps: 
		 * 1.check if null
		 * 1. find old tag
		 * while(2==2){
		 name:
		 {
		 while(1==1)
			 * {
			 * break name;
			 * }
		 }
			 * 
			 * 
			 }
		 * 
		 * 2. 
		 * */
		
		replaceTaghelper(root, oldTag, newTag);
	}
	
	/**
	 * Boldfaces every column of the given row of the table in the DOM tree. The boldface (b)
	 * tag appears directly under the td tag of every column of this row.
	 * 
	 * @param row Row to bold, first row is numbered 1 (not 0).
	 */
	public void boldRow(int row) {
		/** COMPLETE THIS METHOD **/
		boldRowhelper(row);
	}
	
	
	/**
	 * Remove all occurrences of a tag from the DOM tree. If the tag is p, em, or b, all occurrences of the tag
	 * are removed. If the tag is ol or ul, then All occurrences of such a tag are removed from the tree, and, 
	 * in addition, all the li tags immediately under the removed tag are converted to p tags. 
	 * 
	 * @param tag Tag to be removed, can be p, em, b, ol, or ul
	 */
	
	public void removeTag(String tag) {
		/** COMPLETE THIS METHOD **/
		/*
		 * if tag is null
		 */
		if(tag == null)
		{
			return;
		}
		removeTaghelper(null, root, tag);
	}
	
	/**
	 * Adds a tag around all occurrences of a word in the DOM tree.
	 * 
	 * @param word Word around which tag is to be added
	 * @param tag Tag to be added
	 */

	public void addTag(String word, String tag) {
		/** COMPLETE THIS METHOD **/
		/* work && Tag -> null
		 *  - return;
		 * word is null
		 * 	-can't tage anything so return
		 * tag is null
		 * 	-
		 */
	/*	if(word == null || tag == null)
		{
			return;
		}*/
		addTaghelper(null, root, word, tag);
		System.out.print("");
	}
	
	/**
	 * Gets the HTML represented by this DOM tree. The returned string includes
	 * new lines, so that when it is printed, it will be identical to the
	 * input file from which the DOM tree was built.
	 * 
	 * @return HTML string, including new lines. 
	 */
	public String getHTML() {
		StringBuilder sb = new StringBuilder();
		getHTML(root, sb);
		return sb.toString();
	}
	
	private void getHTML(TagNode root, StringBuilder sb) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			if (ptr.firstChild == null) {
				sb.append(ptr.tag);
				sb.append("\n");
			} else {
				sb.append("<");
				sb.append(ptr.tag);
				sb.append(">\n");
				getHTML(ptr.firstChild, sb);
				sb.append("</");
				sb.append(ptr.tag);
				sb.append(">\n");	
			}
		}
	}
	
	/**
	 * Prints the DOM tree. 
	 *
	 */
	public void print() {
		print(root, 1);
	}
	
	private void print(TagNode root, int level) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			for (int i=0; i < level-1; i++) {
				System.out.print("      ");
			};
			if (root != this.root) {
				System.out.print("|----");
			} else {
				System.out.print("     ");
			}
			System.out.println(ptr.tag);
			if (ptr.firstChild != null) {
				print(ptr.firstChild, level+1);
			}
		}
	}
}
