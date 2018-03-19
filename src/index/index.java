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
		
		String textFile = readFileByChars(args[0]);
		//Matcher m1 = r1.matcher(text);
		//Matcher m2 = r2.matcher(text);
		//if (m1.find()) {
			//pass
		//} else {
		
		//get result of Re.
		resultOfRe headline = match(pattern1, textFile);
		resultOfRe text = match(pattern2, textFile);
		resultOfRe pagination = match(pattern3, textFile);
		headline.changeTerms(removeAndCF(headline.getTerms()));
		text.changeTerms(removeAndCF(text.getTerms()));
		writeFileByFileWriter("Text.txt", text.getTerms());
		writeFileByFileWriter(String filePath, String content);
		
	}
	//read file;
	public static resultOfRe match(String pattern, String text) {
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
	
	public static void removeAndCF(resultOfRe text, resultOfRe headline, resultOfRe docNo){
		ArrayList<String> terms_text = text.getTerms();
		ArrayList<Integer> start_text = text.getStart();
		ArrayList<String> terms_headline = headline.getTerms();
		ArrayList<Integer> start_headline = headline.getStart();
		ArrayList<String> terms_doc = docNo.getTerms();
		ArrayList<Integer> start_doc = docNo.getStart();
		int size_text = terms_text.size();
		int size_headline = terms_headline.size();
		int size_doc=terms_doc.size();
		int i = 0, j=0, w=0;
		
		while(i<size_doc-1) {
			terms_text.set(j,Integer.toString(i)+terms_text.get(i).replaceAll("\\t|\r|\n|<.*?>|\\pP", "").toLowerCase());
			terms_headline.set(w,Integer.toString(i)+terms_headline.get(i).replaceAll("\\t|\r|\n|<.*?>|\\pP", "").toLowerCase());
			j++;
			w++;
			while(start_text.get(j)<start_doc.get(i+1)) {
				terms_text.set(j,terms_text.get(j).replaceAll("\\t|\r|\n|<.*?>|\\pP", "").toLowerCase());
				j++;
			}
			while(start_headline.get(j)<start_doc.get(i+1)) {
				terms_headline.set(i,terms_headline.get(i).replaceAll("\\t|\r|\n|<.*?>|\\pP", "").toLowerCase());
				w++;
			}
		i++;
		}
	}
	
	public static void mapInfo() {
		
	}
	
	public static void writeFileByFileWriter(String filePath, String content) throws IOException{  
        File file = new File(filePath);
        synchronized (file) {  
            FileWriter fw = new FileWriter(filePath);  
            fw.write(content);  
            fw.close();
        }  
    }  
	
}
