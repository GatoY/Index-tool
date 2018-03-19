import java.util.ArrayList;
import java.util.regex.*;
import java.io.*;
public class index {
	//main function
	public static void main( String args[]) {
		//match pattern
		String pattern1 = "^<HEADLINE>.*</HEADLINE>$";
		String pattern2 = "^<TEXT>.*</TEXT>$";
		//Pattern r1 = Pattern.compile(pattern1);
		//Pattern r2 = Pattern.compile(pattern2);
		//get file data
		String textFile = readFileByChars(args[0]);
		//Matcher m1 = r1.matcher(text);
		//Matcher m2 = r2.matcher(text);
		//if (m1.find()) {
			//pass
		//} else {
		ArrayList<String> headline = match(pattern1, textFile);
		ArrayList<String> text = match(pattern2, textFile);
		
		//}
	}
	//read file;
	public static ArrayList<String> match(String pattern, String text) {
		ArrayList<String> result = new ArrayList<String>();
		Pattern r=Pattern.compile(pattern);
		Matcher m=r.matcher(text);
		if (m.find()) {
			for(int i = 0; i<m.groupCount(); i++) {
				result.add(m.group(i));
			}
		}
		return result;
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
	
	public static ArrayList<String> removeAndCF(ArrayList<String> result){
		return result;
	}
	public static void mapInfo() {
		
	}
	
}
