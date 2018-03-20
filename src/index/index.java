package index;
import java.util.ArrayList;
import java.util.regex.*;
import java.io.*;
public class index {
	//main function
	public static void main( String args[]) {
		//match HEADLINE.
		String pattern2 = "(<HEADLINE>([\\s\\S]*?)</HEADLINE>)";
		//match TEXT.
		String pattern1 = "|(<TEXT>([\\s\\S]*?)</TEXT>)";
		//match DOCNO
		String pattern3 = "|(<DOCNO>([\\s\\S]*?)</DOCNO>)";
		String textFile = readFileByChars("/Users/Jason/desktop/javaMelb/Index Tool/src/index/latimes");
		//Matcher m1 = r1.matcher(text);
		//Matcher m2 = r2.matcher(text);
		//if (m1.find()) {
			//pass
		//} else {
		System.out.println("read successful");
		//get result of Re.
		
		matchFile(pattern1+pattern2+pattern3, textFile);
		System.out.println("match successfully");
		//resultOfRe text = matchFile(pattern2, textFile);
		//System.out.println("match headline successfully");
		//resultOfRe pagination = matchFile(pattern3, textFile);
		//System.out.println("match docno successfully");
		
	}
	//read file;
	public static void matchFile(String pattern, String text) {
		ArrayList<String> text_terms = new ArrayList<String>();
		ArrayList<Integer> text_start = new ArrayList<Integer>();
		ArrayList<String> headline_terms = new ArrayList<String>();
		ArrayList<Integer> headline_start = new ArrayList<Integer>();
		ArrayList<String> doc_terms = new ArrayList<String>();
		ArrayList<Integer> doc_start = new ArrayList<Integer>();
		Pattern r=Pattern.compile(pattern);
		Matcher m=r.matcher(text);
		while(m.find()) {
			if(m.group(1) != null) {
				text_terms.add(m.group(1));
				text_start.add(m.start(1));
			}
			if(m.group(2)!=null) {
				headline_terms.add(m.group(2));
				headline_start.add(m.start(2));
			}
			if(m.group(3)!=null) {doc_terms.add(m.group(3));
				doc_start.add(m.start(3));
		}
			}
		resultOfRe text_outcome = new resultOfRe(text_terms, text_start);
		resultOfRe headline_outcome = new resultOfRe(headline_terms, text_start);
		resultOfRe doc_outcome = new resultOfRe(doc_terms, doc_start);
		try {
			removeAndCF(text_outcome, headline_outcome, doc_outcome);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		return content.toString();
	}
	
	public static void removeAndCF(resultOfRe text, resultOfRe headline, resultOfRe docNo) throws IOException{
		ArrayList<String> terms_text = text.getTerms();
		ArrayList<Integer> start_text = text.getStart();
		ArrayList<String> terms_headline = headline.getTerms();
		ArrayList<Integer> start_headline = headline.getStart();
		ArrayList<String> terms_doc = docNo.getTerms();
		ArrayList<Integer> start_doc = docNo.getStart();
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
				//System.out.println("headline write");
				file_headline.write(Integer.toString(i)+terms_headline.get(w).replaceAll("\\t|\r|\n|<.*?>|\\pP", "").toLowerCase());
				w++;
				while(w<size_headline){ 
					
						//System.out.println("deal with headline");
					if(start_headline.get(w)>start_doc.get(i+1)){
						break;
					}	
					file_headline.write(terms_headline.get(w).replaceAll("\\t|\r|\n|<.*?>|\\pP", "").toLowerCase());
					w++;
				}
				}
			
			//System.out.println("Start deal")
			//System.out.println(i);		
			file_map.write(Integer.toString(i)+"-"+terms_doc.get(i)+"\\n");
			file_text.flush();
			file_headline.flush();
			file_map.flush();
			System.out.println(i);
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
	
	//public static void mapInfo() {
		
	//}
	
	//public static void writeFileByFileWriter(String filePath, String content) throws IOException{  
      //  File file = new File(filePath);
        //synchronized (file) {  
          //  FileWriter  = new FileWriter(filePath);  
            //fw.write(content);  
            //fw.close();
        //}  
    }  
	
