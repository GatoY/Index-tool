import java.util.ArrayList;

public class resultOfRe {
	private ArrayList<String> terms;
	private int start;
	
	//The results of Re.
	public resultOfRe(ArrayList<String> terms, int start) {
		this.terms = terms;
		this.start = start;
	}
	
	//Get terms.
	public ArrayList<String> getTerms(){
		return terms;
	}
	
	//Get where the terms start.
	public int getStart() {
		return  start;
	}
	
}
