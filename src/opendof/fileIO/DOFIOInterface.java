package opendof.fileIO;

import org.opendof.core.oal.DOFInterface;
import org.opendof.core.oal.DOFInterfaceID;
import org.opendof.core.oal.DOFType;
import org.opendof.core.oal.value.DOFBoolean;
import org.opendof.core.oal.value.DOFString;

public class DOFIOInterface
{
	public static final DOFType FILE_NAME = new DOFString.Type(3, 141);
	public static final DOFType FILE_CONTENTS = new DOFString.Type(3, 1000000);
	public static final DOFType WRITE_SUCCESS = DOFBoolean.TYPE;

	public static final DOFInterface DEF;
	public static final DOFInterfaceID IID = DOFInterfaceID.create("[63:{87654321}]");

	public static final int READ_FILE_ID = 1;
	public static final int WRITE_FILE_ID = 2;

	public static final DOFInterface.Method METHOD_READ_FILE;
	public static final DOFInterface.Method METHOD_WRITE_FILE;

	static 
	{
		DEF = new DOFInterface.Builder(IID)
				.addMethod(READ_FILE_ID, new DOFType[] { FILE_NAME }, new DOFType[] { FILE_CONTENTS })
				.addMethod(WRITE_FILE_ID, new DOFType[] { FILE_NAME, FILE_CONTENTS } , new DOFType[] {WRITE_SUCCESS})
				.build();
		
		METHOD_READ_FILE = DEF.getMethod(READ_FILE_ID);
		METHOD_WRITE_FILE = DEF.getMethod(WRITE_FILE_ID);
	}

}
