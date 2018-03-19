//package index;

import java.util.ArrayList;

public class resultOfRe {
	private ArrayList<String> terms;
	private ArrayList<Integer> start;
	
	//The results of Re.
	public resultOfRe(ArrayList<String> terms, ArrayList<Integer> start) {
		this.terms = terms;
		this.start = start;
	}
	
	//Get terms.
	public ArrayList<String> getTerms(){
		return terms;
	}
	
	//Get where the terms start.
	public ArrayList<Integer> getStart() {
		return  start;
	}
	
	public void changeTerms(ArrayList<String> terms) {
		this.terms = terms;
	}
	
}
