package opendof.fileIO;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.opendof.core.oal.DOFObject;
import org.opendof.core.oal.DOFObjectID;
import org.opendof.core.oal.DOFRequest;
import org.opendof.core.oal.DOFSystem;
import org.opendof.core.oal.DOFType;
import org.opendof.core.oal.DOFValue;
import org.opendof.core.oal.DOFErrorException;
import org.opendof.core.oal.DOFInterface.Method;
import org.opendof.core.oal.DOFInterface.Property;
import org.opendof.core.oal.DOFOperation.Provide;
import org.opendof.core.oal.value.DOFBoolean;
import org.opendof.core.oal.value.DOFString;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;

public class DOFReader
{
	// DOF Fields
	DOFSystem mySystem;
	DOFObject myObject;
	DOFObjectID myOID = DOFObjectID.create("[63:{12345678}]");

	public DOFReader(DOFSystem system)
	{
		mySystem = system;
		init();
	}

	private void init()
	{
		myObject = mySystem.createObject(myOID);
		myObject.beginProvide(DOFIOInterface.DEF, new ProviderListener());
	}

	public class ProviderListener extends DOFObject.DefaultProvider
	{
		public String getFileContents(String path)
		{
			File file = new File(path);
			try
			{
				Scanner scanner = new Scanner(file);
				String contents = "";
				while (scanner.hasNextLine())
					contents += scanner.nextLine() + "\n";
				scanner.close();
				return contents;
			}
			catch (FileNotFoundException e)
			{
				return null;
			}

		}

		@Override
		public void get(Provide operation, DOFRequest.Get request, Property property)
		{

		}

		@Override
		public void set(Provide operation, DOFRequest.Set request, Property property, DOFValue value)
		{

		}

		@Override
		public void invoke(Provide operation, DOFRequest.Invoke request, Method method, List<DOFValue> parameters)
		{
			int methodID = method.getItemID();
			if (methodID == DOFIOInterface.READ_FILE_ID)
			{
				String filename = DOFType.asString(parameters.get(0));
				String contents = getFileContents(filename);
				if (contents == null)
					request.respond(new DOFErrorException("File not found"));
				else
					request.respond(new DOFString(contents));
			}
			else
				request.respond(new DOFErrorException("Invalid method id"));

		}
	}
}
