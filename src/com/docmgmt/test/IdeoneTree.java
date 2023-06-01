package com.docmgmt.test;
import java.util.ArrayList;
import java.util.List;
 
/* Name of the class has to be "Main" only if the class is public. */
class IdeoneTree{
	public static class Node{
		int level;
		List<Node> children = new ArrayList<Node>();
		Node(int level){ this.level = level;}
	}
 
	public static void main (String[] args) throws java.lang.Exception{
		String[] h = {"H1", "H1", "H2", "H3", "H3", "H5", "H2", "H2", "H3",
		                    "H4", "H2", "H2", "H2", "H1", "H2", "H2", "H3",
		                    "H4", "H4", "H2"};
 
		Node[] mostRecent = new Node[6];
		mostRecent[0] = new Node(0);      // root node
 
		for(int i = 0; i < h.length; i++){
			int level = Integer.parseInt("" + h[i].charAt(1));
			Node n = new Node(level);
			mostRecent[level] = n;
 
			int pLevel = level - 1;
			while(mostRecent[pLevel] == null) --pLevel;
			mostRecent[pLevel].children.add(n);
 
			for(int j = 0; j < pLevel; j++)
				System.out.print("\t");
			System.out.println(h[i]);
		} 
	}
}