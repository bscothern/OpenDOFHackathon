package opendof.sql;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class MainSQLProvider
{
	public static void main(String[] args)
	{
		
		DOFAbstraction providerDofAbstraction = new DOFAbstraction();
        providerDofAbstraction.createDOF();
        providerDofAbstraction.createServer("0.0.0.0", 3567);
        //providerDofAbstraction.createConnection("155.99.175.143", 3567);
        SQLProvider provider = new SQLProvider(providerDofAbstraction.createDOFSystem("provider"));
        System.out.println("Running provider server");
        
        Scanner sc = new Scanner(System.in);
        String in = "";
        while (!(in = sc.nextLine()).equals("stop"));
        	

	}

}
