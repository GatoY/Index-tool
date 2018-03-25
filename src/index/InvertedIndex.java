package index;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;

public class InvertedIndex { 
	private Hashtable<String, InvertedList> wordList=new Hashtable<String, InvertedList>();
	public InvertedIndex() {		
	}
	public void readAndDeal(String[] words, Integer FileNo) throws IOException {
	    for(int i =0;i<words.length;i++) {
			if(!this.wordList.containsKey(words[i])) {
				InvertedList word = new InvertedList(FileNo);
				this.wordList.put(words[i], word);
			}
			else {
				this.wordList.get(words[i]).add(FileNo);
			}
		}    
	}
	
	public void writeResult() throws IOException {
		System.out.println("start write");
		Iterator<String> keyList = this.wordList.keySet().iterator();
		FileWriter lexicon = new FileWriter("lexicon.txt",false);
		FileWriter invlists = new FileWriter("invlists.txt",false);
		//FileWriter map = new FileWriter("map.txt",false);
		//countOfInvertedList shows which line of invertedList of the word lies in invlists.txt
		Integer countOfInvertedList=1;
		System.out.println(wordList.size());
		while(keyList.hasNext()) {
			System.out.println(countOfInvertedList);
			String word = keyList.next();
			String result = this.wordList.get(word).result();
			lexicon.write(word+countOfInvertedList.toString()+"\t\n");
			invlists.write(result+"\t\n");
			countOfInvertedList++;
		}
		
		lexicon.close();
		invlists.close();
		System.out.println("write done");
	}
	
	public void writeResultWithStoppers(Hashtable<String, Integer> stoppers) throws IOException {
		System.out.println("start write");
		Iterator<String> keyList = this.wordList.keySet().iterator();
		FileWriter lexicon = new FileWriter("lexicon.txt",false);
		FileWriter invlists = new FileWriter("invlists.txt",false);
		//FileWriter map = new FileWriter("map.txt",false);
		//countOfInvertedList shows which line of invertedList of the word lies in invlists.txt
		Integer countOfInvertedList=1;
		System.out.println(wordList.size());
		while(keyList.hasNext()) {
			String word = keyList.next();
			String result = this.wordList.get(word).result();
			if(stoppers.containsKey(word)) {
				continue;
			}
			System.out.println(word);
			lexicon.write(word+countOfInvertedList.toString()+"\t\n");
			invlists.write(result+"\t\n");
			countOfInvertedList++;
		}
		
		lexicon.close();
		invlists.close();
		System.out.println("write done");
	}
	
}