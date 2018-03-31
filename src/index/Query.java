package query;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Hashtable;

//usage: args[0]: lexicon file; args[1]:invlists file;
//args[2] map file; args[3]-[n]: word you want to query.
public class Query {
	public static void main(String args[]) {
		String lexiconPath=args[0];
		String invlistsPath=args[1];
		String mapPath=args[2];
		String[]words=args;
		int length=words.length;
		System.out.println(args);
		Hashtable<String, String> lines=new Hashtable<String, String>(length);
		try {
			Hashtable<String,String> map = readMapByLines(mapPath);
			Hashtable<String,String> lexicon =readLexiconByLines(lexiconPath);
			for(int i=3;i<length;i++) {
				if(lexicon.get(words[i]) != null) {
					lines.put(lexicon.get(words[i]), words[i]);
				}
			}
			getInvlistsAndMap(lines, map, invlistsPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void getInvlistsAndMap(Hashtable<String, String> lines, Hashtable<String,String> map, String invlistsPath) throws IOException {
		File file = new File(invlistsPath);//filepath
        FileReader fileReader = new FileReader(file);  
        LineNumberReader reader = new LineNumberReader(fileReader); 
        String txt=reader.readLine();
        Integer count = 0;
        while (txt != null) {
            if (lines.containsKey(count.toString())) {  
                String[] invlist=txt.split(" ");
                System.out.println(lines.get(count.toString()));
                System.out.println(invlist[0]);
                for(int j=1;j<invlist.length;j=j+2) {
                		System.out.println(map.get(invlist[j])+" "+invlist[j+1]);
                }
            }
            count++;
            txt = reader.readLine();
        }  
        reader.close();  
        fileReader.close();
		
	}

    //record mapinfo in hashtable;
	public static Hashtable<String, String> readMapByLines(String filePath) throws IOException {
		File file = new File(filePath);
		FileReader fileReader = new FileReader(file);
		LineNumberReader reader = new LineNumberReader(fileReader);
		String txt=reader.readLine();
		Hashtable<String, String> mapList=new Hashtable<String, String>();
		while(txt!=null) {
			try {
				String w[]=txt.split(" ");
				mapList.put(w[0], w[1]);
				txt=reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
		reader.close();
		return mapList;
	}
	
    //record lexiconinfo in hashtable;
	public static Hashtable<String, String> readLexiconByLines(String filePath) throws IOException {
		File file = new File(filePath);
		FileReader fileReader = new FileReader(file);
		LineNumberReader reader = new LineNumberReader(fileReader);
		String txt = reader.readLine();
		Hashtable<String, String> mapList=new Hashtable<String, String>();
		while(txt!=null) {
				String w[]=txt.split("-");
				mapList.put(w[0], w[1]);
				txt=reader.readLine();
		}
		reader.close();
		return mapList;
	}
}


