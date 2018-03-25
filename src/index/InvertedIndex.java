package index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InvertedIndex { 
	
	private String FilePath;

	public InvertedIndex(String FilePath) {
		this.FilePath=FilePath;
	}
	
	public void readAndDeal(String FilePath) throws IOException {
        FileReader fr=new FileReader(FilePath);
        BufferedReader br=new BufferedReader(fr);
        String line="";
        String[] arrs=null;
        while ((line=br.readLine())!=null) {
            arrs=line.split(" ");
            System.out.println(arrs[0] + " : " + arrs[1] + " : " + arrs[2]);
        }
        br.close();
        fr.close();
    }
}
