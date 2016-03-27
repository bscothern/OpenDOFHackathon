package opendof.fileIO;

import java.util.List;

import org.opendof.core.oal.DOFException;
import org.opendof.core.oal.DOFValue;

import opendof.sql.DOFAbstraction;

public class MainReaderRequestor
{
	
	

	public static void main(String[] args) throws DOFException
	{
		DOFAbstraction dof = new DOFAbstraction();
		dof.createDOF();
		dof.createConnection("localhost", 3567);
		Requestor requestor = new Requestor(dof.createDOFSystem("requestor"));
		
		List<DOFValue> fileContents = requestor.getFileContents("abc.txt");
		String contents = fileContents.get(0).toString();
		
		System.out.println(contents);
	}

	

}
