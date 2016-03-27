package opendof.sql;

import java.util.Scanner;

public class MainSQLProvider
{
	static String url = "jdbc:mysql://georgia.eng.utah.edu/cs5530db";
	static String pass = "soc5530@16";
	static String username = "cs5530";
	
	
	public static void main(String[] args) throws Exception
	{
		
		DOFAbstraction providerDofAbstraction = new DOFAbstraction();
        providerDofAbstraction.createDOF();
        providerDofAbstraction.createServer("0.0.0.0", 3567);
        SQLProvider provider = new SQLProvider(providerDofAbstraction.createDOFSystem("provider"), url, username, pass, new DemoConstructor(), new DemoValidator());
        System.out.println("Running provider server");
        
        Scanner sc = new Scanner(System.in);
        String in = "";
        while (!(in = sc.nextLine()).equals("stop"))
        	System.exit(1);

	}

}
