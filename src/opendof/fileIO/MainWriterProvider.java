package opendof.fileIO;

import java.io.IOException;
import java.util.Scanner;

import opendof.sql.DOFAbstraction;

public class MainWriterProvider
{

	static String path = "./";
	
	public static void main(String[] args) throws IOException
	{
		DOFAbstraction dofAbstraction = new DOFAbstraction();
		dofAbstraction.createDOF();
        dofAbstraction.createServer("localhost", 3567);
        
        DOFWriter providerWithWriter = new DOFWriter(dofAbstraction.createDOFSystem("provider"), path);
        Scanner sc = new Scanner(System.in);
    	while(!sc.nextLine().toLowerCase().equals("stop"));
    	sc.close();
	}

}
