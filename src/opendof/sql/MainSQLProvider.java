package opendof.sql;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class MainSQLProvider
{
	static String url = "jdbc:mysql://georgia.eng.utah.edu/cs5530db50";
	static String pass = "dv4arsa5";
	static String username = "cs5530u50";
	
	
	public static void main(String[] args) throws Exception
	{
		
		DOFAbstraction providerDofAbstraction = new DOFAbstraction();
        providerDofAbstraction.createDOF();
        providerDofAbstraction.createServer("0.0.0.0", 3567);
        //providerDofAbstraction.createConnection("155.99.175.143", 3567);
        //SQLProvider provider = new SQLProvider(providerDofAbstraction.createDOFSystem("provider"));
        SQLProvider provider = new SQLProvider(providerDofAbstraction.createDOFSystem("provider"), url, username, pass, null, null);
        System.out.println("Running provider server");
        
        Scanner sc = new Scanner(System.in);
        String in = "";
        while (!(in = sc.nextLine()).equals("stop"));
        	

	}

}
