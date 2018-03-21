package index;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.*;
import java.io.*;
public class index {
	//main function
	public static void main( String args[]) {
		
		//start
		/*long startTime=System.currentTimeMillis();
		String pattern ="(<TEXT>([\\s\\S]*?)</TEXT>)|(<HEADLINE>([\\s\\S]*?)</HEADLINE>)|(<DOCNO>([\\s\\S]*?)</DOCNO>)";
		
		//read file.
		String textFile = readFileByChars("/Users/Jason/desktop/javaMelb/Index Tool/src/index/latimes");
		System.out.println("read successful");
		
		//get result of Re and deal with it.
		resultOfRe[] outcome =  matchFile(pattern, textFile);
		textFile=null;
		try {
			removeAndCF(outcome);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		outcome=null;
		*/
		
		//String textFile = readFileByChars("/Users/Jason/desktop/javaMelb/Index Tool/src/index/latimes");
		
		if(true) {
			System.out.println("Start stopping");
			String filename = "/Users/Jason/desktop/javaMelb/Index Tool/src/index/stoplist";
			try {
				Hashtable<String, Integer> stoppers = readAndHash(filename);			
				matchFileAndPrint(stoppers,"/Users/Jason/desktop/javaMelb/Index Tool/src/index/text.txt");
				matchFileAndPrint(stoppers, "/Users/Jason/desktop/javaMelb/Index Tool/src/index/headline.txt");
				}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		System.out.println("match and deal successfully");
		long endTime=System.currentTimeMillis();
		//System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
		
	}
	
	public static void matchFileAndPrint2(Hashtable<String, Integer> stoppers, String filePath) throws IOException {
		
		FileReader fr=new FileReader(filePath);
        BufferedReader br=new BufferedReader(fr);
        String line="";
        System.out.println("match and print start");
        while ((line=br.readLine())!=null) {
        		System.out.println(line.length());
        		break;
        }
        br.close();
        fr.close();
		System.out.println("hash dealing done");
	}
	
public static void matchFileAndPrint(Hashtable<String, Integer> stoppers, String filePath) throws IOException {
        File file = new File(filePath);
        if(!file.exists() || !file.isFile()) {
    			System.out.println("No file");
    			return;
    		}
    		try {
    			char[] temp = new char[1024];
    			FileInputStream fileInputStream = new FileInputStream(file);
    			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "GBK");
    		
    			while(inputStreamReader.read(temp) != -1) {
    				String[] words = new String(temp).split(" ");
    				//System.out.println(new String(temp));
    				//System.out.println(stoppers.get(words[0]));
    				for(int i=0; i<words.length;i++) {
    					//System.out.println(i);
    					//System.out.println(words[i]);
    					if(stoppers.get(words[i])==null) {
    						//System.out.println(2);
    						System.out.println(words[i]);
    					}
    				}
    				temp = new char[1024];
    		}
    		fileInputStream.close();
    		inputStreamReader.close();
    	}catch(FileNotFoundException e) {
    		e.printStackTrace();
    	}catch(IOException e) {
    		e.printStackTrace();
    	}   
		System.out.println("hash dealing done");
	}
	
	
	
	//match file;
	public static resultOfRe[] matchFile(String pattern, String text) {
		ArrayList<String> text_terms = new ArrayList<String>();
		ArrayList<Integer> text_start = new ArrayList<Integer>();
		ArrayList<String> headline_terms = new ArrayList<String>();
		ArrayList<Integer> headline_start = new ArrayList<Integer>();
		ArrayList<String> doc_terms = new ArrayList<String>();
		ArrayList<Integer> doc_start = new ArrayList<Integer>();
		Pattern r=Pattern.compile(pattern);
		Matcher m=r.matcher(text);
		while(m.find()) {
			
			if(m.group(2) != null) {
				text_terms.add(m.group(2));
				text_start.add(m.start(2));
				//System.out.println(m.group(2));
			}
			if(m.group(4)!=null) {
				headline_terms.add(m.group(4));
				headline_start.add(m.start(4));
				//System.out.println(m.group(4));
			}
			if(m.group(6)!=null) {
				doc_terms.add(m.group(6));
				doc_start.add(m.start(6));
		}
			}
		resultOfRe[] outcome = new resultOfRe[3];
		outcome[0] = new resultOfRe(text_terms, text_start);
		outcome[1] = new resultOfRe(headline_terms, headline_start);
		outcome[2] = new resultOfRe(doc_terms, doc_start);
		return outcome;
		
	}
	
	public static String readFileByChars(String filePath) {
		File file = new File(filePath);
		if(!file.exists() || !file.isFile()) {
			System.out.println("No file");
			return null;
		}
		StringBuffer content = new StringBuffer();
		try {
			char[] temp = new char[1024];
			FileInputStream fileInputStream = new FileInputStream(file);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "GBK");
			
			while(inputStreamReader.read(temp) != -1) {
				content.append(new String(temp));
				temp = new char[1024];
			}
			fileInputStream.close();
			inputStreamReader.close();
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		System.out.println(filePath+"read successfully");
		return content.toString();
	}
	
	public static void removeAndCF(resultOfRe[] outcome) throws IOException{
		ArrayList<String> terms_text = outcome[0].getTerms();
		ArrayList<Integer> start_text = outcome[0].getStart();
		ArrayList<String> terms_headline = outcome[1].getTerms();
		ArrayList<Integer> start_headline = outcome[1].getStart();
		ArrayList<String> terms_doc = outcome[2].getTerms();
		ArrayList<Integer> start_doc = outcome[2].getStart();
		int size_text = terms_text.size();
		System.out.println("size of text"+Integer.toString(size_text));
		int size_headline = terms_headline.size();
		System.out.println("size of headline"+Integer.toString(size_headline));
		int size_doc=terms_doc.size();
		System.out.println("size of map" + Integer.toString(size_doc));
		int i = 0, j = 0, w = 0;
		FileWriter file_text = new FileWriter("text.txt",false);
		FileWriter file_headline = new FileWriter("headline.txt",false);
		FileWriter file_map = new FileWriter("map.txt",false);
		System.out.println("Start deal with files");
		while(i<size_doc-1) {
			if(j<size_text) {
				//System.out.println("text write");
			file_text.write(Integer.toString(i)+terms_text.get(j).replaceAll("\\t|\r|\n|<.*?>|\\pP", "").toLowerCase());
			j++;
			//System.out.println(j);
			//System.out.println(size_text);
			
			//	System.out.println(1);
			//	System.out.println(Integer.toString(start_text.get(j)));
			//System.out.println(Integer.toString(start_doc.get(i+1)));
				
				while(j<size_text){
					//System.out.println("deal with text");					
                    	if(start_text.get(j)>start_doc.get(i+1)) {          		
                    		break;
                    	}
                    	file_text.write(terms_text.get(j).replaceAll("\\t|\r|\n|<.*?>|\\pP", "").toLowerCase());
                    	j++;
            }
			}

			if(w<size_headline) {
				file_headline.write(Integer.toString(i)+terms_headline.get(w).replaceAll("\\t|\r|\n|<.*?>|\\pP", "").toLowerCase());
				w++;
				while(w<size_headline){ 
					if(start_headline.get(w)>start_doc.get(i+1)){
						break;
					}	
					//System.out.println("111111");
					file_headline.write(terms_headline.get(w).replaceAll("\\t|\r|\n|<.*?>|\\pP", "").toLowerCase());
					w++;
				}
			}
			
			file_map.write(Integer.toString(i)+"-"+terms_doc.get(i)+"\\n");
			file_text.flush();
			file_headline.flush();
			file_map.flush();
			i++;
		}
		
		if(j<size_text) {
			//System.out.println("text write");
			file_text.write(Integer.toString(i)+terms_text.get(j).replaceAll("\\t|\r|\n|<.*?>|\\pP", "").toLowerCase());
			j++;
		}
		
		if(w<size_headline) {
			//System.out.println("headline write");
			file_headline.write(Integer.toString(i)+terms_headline.get(w).replaceAll("\\t|\r|\n|<.*?>|\\pP", "").toLowerCase());
			w++;
		}
		
		
		while(j<size_text) {
			file_text.write(terms_text.get(j).replaceAll("\\t|\r|\n|<.*?>|\\pP", "").toLowerCase());
			j++;
		}
		
		while(w<size_headline) {
			file_headline.write(terms_headline.get(w).replaceAll("\\t|\r|\n|<.*?>|\\pP", "").toLowerCase());
			w++;
		}
		
		file_text.close();
		file_headline.close();
		file_map.close();
		System.out.println("finish indexing");
	}
	
	public static Hashtable<String, Integer> readAndHash(String filename) throws IOException {
		Hashtable<String, Integer> stoppers = new Hashtable<String, Integer>();
		FileReader fr=new FileReader(filename);
        BufferedReader br=new BufferedReader(fr);
        String line="";
        int value = 1;
        while ((line=br.readLine())!=null) {
            stoppers.put(line, value);
        }
        br.close();
        fr.close();
        System.out.println("get stoppers");
        return stoppers;
    }

	
}  	