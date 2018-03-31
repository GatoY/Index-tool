package index;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

public class InvertedList {
	private Hashtable<Integer, Integer> docAndNo=new Hashtable<Integer, Integer>();
	
	public InvertedList(Integer docNo) {	
		this.docAndNo.put(docNo, 1);	
	}
	
	public void add(Integer docNo) {
		Integer count = docAndNo.get(docNo);
		if(count!=null) {
			count=count+1;
		}
		else {
			count=1;
		}
		docAndNo.put(docNo, count);
	}
	
	public String result() {
		String resultOfWord = "";
		Integer count=0;
		for(Entry<Integer, Integer> entry:docAndNo.entrySet()){
			Integer docNo = entry.getKey();
			Integer num = entry.getValue();
			count = count+1;
			resultOfWord=resultOfWord+" "+docNo.toString()+" "+num.toString();
			}
		resultOfWord=count.toString()+resultOfWord;
		return resultOfWord;
	}
}
