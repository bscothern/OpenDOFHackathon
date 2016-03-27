package opendof.fileIO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

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

public class DOFWriter
{
	// DOF Fields
	DOFSystem mySystem;
	DOFObject myObject;
	DOFObjectID myOID = DOFObjectID.create("[63:{12345678}]");
	String lastOp = "undefined";

	private String path;

	public DOFWriter(DOFSystem system, String _path) throws IOException {
		File f = new File(_path);
		if (!f.canWrite()) {
			throw new IOException("Cannot write to path: " + f.getCanonicalPath());
		}

		// General DOF init
		mySystem = system;
		init();

		// DOFWriter Init
		path = _path;
		if (path.charAt(path.length() -1) != '/') {
			path += "/";
		}
	}

	private void init()
	{
		myObject = mySystem.createObject(myOID);
		myObject.beginProvide(DOFIOInterface.DEF, new ProviderListener());
	}

	public String getPath() {
		return path;
	}

	public String getLastOperation()
	{
		if (lastOp != null)
			return lastOp;
		else
			return "undefined";
	}

	public class ProviderListener extends DOFObject.DefaultProvider
	{
		@Override
		public void get(Provide operation, DOFRequest.Get request, Property property)
		{
			lastOp = "get";
		}

		@Override
		public void set(Provide operation, DOFRequest.Set request, Property property, DOFValue value)
		{
			lastOp = "set";
		}

		@Override
		public void invoke(Provide operation, DOFRequest.Invoke request, Method method, List<DOFValue> parameters)
		{
			System.out.println("Method invoked");
			String outFileName = DOFType.asString(parameters.get(0));
			DOFWriterOp op = DOFWriterOp.values()[DOFType.asInt(parameters.get(1))];

			try {
				File f = new File(path + outFileName);
				if (!f.canWrite()) {
					throw new IOException("Cannot write to path: " + f.getCanonicalPath());
				}
				
				if (op == DOFWriterOp.Overwrite && !f.exists()) {
					f.delete();
				}

				Files.write(f.toPath(), DOFType.asString(parameters.get(2)).getBytes(), op.toOption());
			}
			catch (Exception e) {
				request.respond(new DOFErrorException(e.getMessage()));
				return;
			}
			finally {
			}
		}
	}
}
