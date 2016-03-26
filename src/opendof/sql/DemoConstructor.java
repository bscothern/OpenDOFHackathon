package opendof.sql;


public class DemoConstructor extends SQLConstructor
{
	@Override
	public String construction(String args)
	{
		if (args == null || !args.contains(":") ||args.isEmpty())
		{
			System.err.println("Args was invalid");
			return null;
		}

		String[] split = args.split(":");
		switch (split[0])
		{
		case "UserTable":
			return "select * from student";	
		case "getByName":
			return "select * from student where sname like '%"+ split[1] + "%'";
		default:
			break;
		}
		
		return null;
	}
}
