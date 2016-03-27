package opendof.fileIO;

import java.util.List;

import org.opendof.core.oal.DOFException;
import org.opendof.core.oal.DOFValue;

import opendof.sql.DOFAbstraction;

public class MainWriterRequestor
{

	public static void main(String[] args) throws DOFException
	{
		DOFAbstraction dof = new DOFAbstraction();
		dof.createDOF();
		dof.createConnection("localhost", 3567);
		Requestor requestor = new Requestor(dof.createDOFSystem("requestor"));
		int op = DOFWriterOp.Append.ordinal();
		
		boolean successful = requestor.writeFileContents("def.txt", op, "Hello world");
		System.out.println(successful);
	}

}
