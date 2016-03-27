package opendof.fileIO;

import java.io.FileNotFoundException;
import java.util.Scanner;

import opendof.sql.DOFAbstraction;

public class MainReaderProvider
{
	static String path = "./";
	
	public static void main(String[] args) throws FileNotFoundException
	{
		DOFAbstraction dofAbstraction = new DOFAbstraction();
		dofAbstraction.createDOF();
        dofAbstraction.createServer("0.0.0.0", 3567);
        
        DOFReader providerWithReader = new DOFReader(dofAbstraction.createDOFSystem("provider"), path);
        Scanner sc = new Scanner(System.in);
    	while(!sc.nextLine().toLowerCase().equals("stop"));
    	sc.close();
        
	}

}
