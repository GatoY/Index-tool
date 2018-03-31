package index;
import java.util.Hashtable;
import java.util.regex.*;
import java.io.*;
public class index {
	//main function
	public static void main( String args[]) {
        //variable decides whether to use stoplist.
		int stopOrNot=0;
		String stoplistPath="";
		String sourcefile="";
        
        //parse the commandline
		if(args[0]=="-s") {
			stopOrNot=1;
			stoplistPath=args[1];
			sourcefile=args[3];
		}
		else {
			sourcefile=args[1];
		}
		
		//start
		//long startTime=System.currentTimeMillis();
        
        //match pattern
		String pattern ="(<TEXT>([\\s\\S]*?)</TEXT>)|(<HEADLINE>([\\s\\S]*?)</HEADLINE>)|(<DOCNO>([\\s\\S]*?)</DOCNO>)";
		//read file.
		String textFile = readFileByChars(sourcefile);
		InvertedIndex invertedindex = new InvertedIndex();
		try {
            //match file.
			matchFile(pattern, textFile, invertedindex);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if(stopOrNot==1) {
			System.out.println("Start stopping");			
			Hashtable<String, Integer> stoppers = new Hashtable<String, Integer>();
			try {
                //get the words needed to stop.
				stoppers = readAndHash(stoplistPath);
                //wrtie
				invertedindex.writeResultWithStoppers(stoppers);
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
		else {
		try {
            //write
			invertedindex.writeResult();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	}
	
	public static void matchFile(String pattern, String text, InvertedIndex invertedindex) throws IOException {
		String terms = "";
		Pattern r=Pattern.compile(pattern);
		Matcher m=r.matcher(text);
		Integer count = 0;
		FileWriter map = new FileWriter("map",false);
		while(m.find()) {
			if(m.group(6)!=null) {
                //map info.
				map.write(count+m.group(6)+"\n");
				map.flush();
				count++;
				terms=terms+"FileNo"+count.toString()+" ";
		}
			if(m.group(2) != null) {
                //text
				String[] words =m.group(2).replaceAll("\\t|\r|\n|<.*?>|\\pP", "").toLowerCase().split(" ");
				invertedindex.readAndDeal(words,count);
			}
			if(m.group(4)!=null) {
                //headline
				String[] words =m.group(4).replaceAll("\\t|\r|\n|<.*?>|\\pP", "").toLowerCase().split(" ");
				invertedindex.readAndDeal(words,count);
			}
			}
		map.close();
	}

    //read file.
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
	
    //record stoppers in hashtable
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
        return stoppers;
    }
}  	
