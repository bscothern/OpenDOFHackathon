package opendof.sql;

public class MainSQLProvider
{
	public static void main(String[] args)
	{
		
		DOFAbstraction providerDofAbstraction = new DOFAbstraction();
        providerDofAbstraction.createDOF();
        providerDofAbstraction.createConnection("155.99.175.143", 3567);
        SQLProvider provider = new SQLProvider(providerDofAbstraction.createDOFSystem("provider"));

	}

}
