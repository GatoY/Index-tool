package index;
import java.util.ArrayList;
import java.util.regex.*;
import java.io.*;
public class index {
	//main function
	public static void main( String args[]) {
		//match HEADLINE.
		String pattern1 = "<HEADLINE>([\\s\\S]*?)</HEADLINE>";
		//match TEXT.
		String pattern2 = "<TEXT>([\\s\\S]*?)</TEXT>";
		//match DOCNO
		String pattern3 = "<DOCNO>([\\s\\S]*?)</DOCNO>";
		String textFile = readFileByChars("/Users/Jason/desktop/javaMelb/Index Tool/src/index/latimes");
		//Matcher m1 = r1.matcher(text);
		//Matcher m2 = r2.matcher(text);
		//if (m1.find()) {
			//pass
		//} else {
		System.out.println("read successful");
		//get result of Re.
		resultOfRe headline = matchFile(pattern1, textFile);
		System.out.println("match text successfully");
		resultOfRe text = matchFile(pattern2, textFile);
		System.out.println("match headline successfully");
		resultOfRe pagination = matchFile(pattern3, textFile);
		System.out.println("match docno successfully");
		try {
			removeAndCF(text, headline, pagination);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//read file;
	public static resultOfRe matchFile(String pattern, String text) {
		ArrayList<String> terms = new ArrayList<String>();
		ArrayList<Integer> start = new ArrayList<Integer>();
		Pattern r=Pattern.compile(pattern);
		Matcher m=r.matcher(text);
		while(m.find()) {
			for(int i = 0; i<m.groupCount(); i++) {
				terms.add(m.group(i));
				start.add(m.start());
			}
		}
		resultOfRe outcome = new resultOfRe(terms, start);
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
			
			//System.out.println("Start deal");
			
			//System.out.println(i);
			i++;
			file_map.write(Integer.toString(i)+"-"+terms_doc.get(i)+"\\n");
			file_text.flush();
			file_headline.flush();
			file_map.flush();
		}
		while(j<size_text) {
			file_text.write(Integer.toString(i-1)+terms_text.get(j).replaceAll("\\t|\r|\n|<.*?>|\\pP", "").toLowerCase());
			j++;
		}
		while(w<size_headline) {
			file_headline.write(Integer.toString(i-1)+terms_headline.get(w).replaceAll("\\t|\r|\n|<.*?>|\\pP", "").toLowerCase());
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
	
