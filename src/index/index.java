package index;
import java.util.Hashtable;
import java.util.regex.*;
import java.io.*;
public class index {
	//main function
	public static void main( String args[]) {
		//start
		long startTime=System.currentTimeMillis();
		String pattern ="(<TEXT>([\\s\\S]*?)</TEXT>)|(<HEADLINE>([\\s\\S]*?)</HEADLINE>)|(<DOCNO>([\\s\\S]*?)</DOCNO>)";
		//read file.
		String textFile = readFileByChars("/Users/Jason/desktop/javaMelb/Index Tool/src/index/latimes");
		System.out.println("read successful");
		InvertedIndex invertedindex = new InvertedIndex();
		try {
			matchFile(pattern, textFile, invertedindex);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//String textFile = readFileByChars("/Users/Jason/desktop/javaMelb/Index Tool/src/index/latimes");
		int i = 1;
		if(i==2) {
			System.out.println("Start stopping");
			String filename = "/Users/Jason/desktop/javaMelb/Index Tool/src/index/stoplist";			
			Hashtable<String, Integer> stoppers = new Hashtable<String, Integer>();
			try {
				stoppers = readAndHash(filename);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			//stoppers.containsKey(words[i]);
			try {
				invertedindex.writeResultWithStoppers(stoppers);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
		else {
		try {
			invertedindex.writeResult();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("match and deal successfully");
		long endTime=System.currentTimeMillis();
		System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
	}
	}
	
	
	
	public static void matchFile(String pattern, String text, InvertedIndex invertedindex) throws IOException {
		String terms = "";
		Pattern r=Pattern.compile(pattern);
		Matcher m=r.matcher(text);
		Integer count = 0;
		FileWriter map = new FileWriter("map.txt",false);
		while(m.find()) {
			if(m.group(6)!=null) {
				map.write(count+" "+m.group(6)+"\t\n");
				map.flush();
				count++;
				terms=terms+"FileNo"+count.toString()+" ";
		}
			
			if(m.group(2) != null) {
				String[] words =m.group(2).replaceAll("\\t|\r|\n|<.*?>|\\pP", "").toLowerCase().split(" ");
				invertedindex.readAndDeal(words,count);
				
				//System.out.println(m.group(2));
			}
			if(m.group(4)!=null) {
				String[] words =m.group(4).replaceAll("\\t|\r|\n|<.*?>|\\pP", "").toLowerCase().split(" ");
				invertedindex.readAndDeal(words,count);
				//System.out.println(m.group(4));
			}
			}
		map.close();
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