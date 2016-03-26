package opendof.sql;

import java.io.IOException;
import java.io.InputStream;

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
        
        InputStream in = System.in;
        try
		{
			in.read();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
