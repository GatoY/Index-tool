package index;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;

public class InvertedIndex { 
	
	private String FilePath;
	private Hashtable<String, InvertedList> wordList=new Hashtable<String, InvertedList>();
	private String toFilePath;
	

	public InvertedIndex(String fromFilePath, String toFilePath) {
		this.FilePath=fromFilePath;
		this.toFilePath=toFilePath;
	}
	public void readAndDeal() throws IOException {
		//System.out.println("Start readAndDeal");
        FileReader fr=new FileReader(this.FilePath);
        BufferedReader br=new BufferedReader(fr);
        String line="";
        String[] words;
        Integer FileNo = 0;
        while ((line=br.readLine())!=null) {
        		//System.out.println(line.length());
            words=line.split(" ");
            //System.out.println("words split done");
            //System.out.println(line);
            //System.out.println(words.length);
            //System.out.println(words[0]);
            for(int i =0;i<words.length;i++) {
            		//System.out.println(words[i]);
            		if(words[i].length()>=6) {
            			//System.out.println(words[i].substring(0, 6));
            			if(words[i].substring(0, 6).equals("FileNo")) {
            				//System.out.println("continue");
            				FileNo++;
            				continue;
            			}
            		}
            		if(!this.wordList.containsKey(words[i])) {
            			
            			InvertedList word = new InvertedList(FileNo);
            			//System.out.println("1");
            			this.wordList.put(words[i], word);
            		}
          		else {
            			this.wordList.get(words[i]).add(FileNo);
            		}
       		}    
        }
        System.out.println("read and deal done");
	}
	
	public void writeResult() throws IOException {
		System.out.println("start write");
		Iterator<String> keyList = this.wordList.keySet().iterator();
		FileWriter lexicon = new FileWriter(toFilePath+"lexicon.txt",false);
		FileWriter invlists = new FileWriter(toFilePath+"invlists.txt",false);
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
}