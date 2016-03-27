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
			return "select * from users";	
		case "getByName":
			return "select * from user where name like '%"+ split[1] + "%'";
		default:
			break;
		}
		
		return null;
	}
}
