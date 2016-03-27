package opendof.sql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

import org.opendof.core.oal.DOFException;
import org.opendof.core.oal.DOFValue;

import opendof.fileIO.DOFWriterOp;
import opendof.fileIO.Requestor;


public class MainSQLRequestor {
	
	 static DOFAbstraction requestorDofAbstraction; // Represents the whole DOF
	 static SQLRequestor requestor;
	 static String IPADDRESS = "128.110.78.153"; 
	 static int PORT = 3567;

	public static void main(String[] args){
	    
		if(args.length != 2){
			System.out.println("You need to enter the ip address and port of server");
			IPADDRESS = args[0];
			PORT = Integer.parseInt(args[1]);
			return;
		}
		
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.println("======WELCOME TO DOFdb=====");
        System.out.println("Enter Number. Do you want to communicate with..");
        System.out.println("1. SQLREQUESTOR");
      
        
        String userInput = "";
        int val = 0;
        
        val = checkIntReponse(userInput, val, in);
        
        if(val < 1 || val > 3){
        	System.out.println("Invalid Number");
        	return;
        }
        
        if(val == 1){
        
       System.out.println("Connecting to DOFDB... Make sure SQLDB is running");
       requestorDofAbstraction = new DOFAbstraction();
       requestorDofAbstraction.createDOF();
       requestorDofAbstraction.createConnection(IPADDRESS, PORT);
       requestor = new SQLRequestor(requestorDofAbstraction.createDOFSystem("requestor"));
            
       System.out.println("Enter Number. Which Query Search Do You Want?");
       System.out.println("1. Select all users data from student table");
       System.out.println("2. Select * From Users Where");
       
             
        userInput = "";
        val = checkIntReponse(userInput, val, in);	  
        if(val < 1 || val > 2){
        	System.out.println("Invalid Number");
        	return;
        }
        
        if(val ==1){
        List<DOFValue> d = null;
        try {
			d = requestor.sendSelectQuery("UserTable:");
			System.out.println("======================================================");
			System.out.println("====================Database QUERY====================");
			System.out.println("======================================================");
			for(DOFValue dv: d){
	        	System.out.println(dv.toString());
	        }
		} catch (DOFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }else{
       	 List<DOFValue> d = null;
         try {
        	 String name = "";
        	 System.out.println("Enter a name to search through database!");
        	 try {
				while ((name = in.readLine()) == null && name.length() == 0);
			} catch (IOException e) {
				System.out.println("Error with input.");
				return;
			}
        	String getbyname = "getByName:" + userInput; 
 			d = requestor.sendSelectQuery(getbyname);
 			System.out.println("======================================================");
 			System.out.println("====================Database QUERY====================");
 			System.out.println("======================================================");
 			for(DOFValue dv: d){
 	        	System.out.println(dv.toString());
 	        }
 			
 		} catch (DOFException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
        }
        
        }else if(val==2){
        	 
        System.out.println("Enter Number. What do you want to do with your File System?");
        System.out.println("1. Read file from connected user");
             System.out.println("2. Write file to connected User");
      
              userInput = "";
              val = checkIntReponse(userInput, val, in);	  
             
              if(val < 1 || val > 2){
              	System.out.println("Invalid Number");
              	return;
              }
              if(val == 1){
            	  
            		DOFAbstraction dof = new DOFAbstraction();
            		dof.createDOF();
            		dof.createConnection(IPADDRESS, PORT);
            		Requestor requestor = new Requestor(dof.createDOFSystem("requestor"));
            		 
            		
            		System.out.println("Enter name of file:");
            		String name = "";
            		try {
         				while ((name = in.readLine()) == null && name.length() == 0);
         			} catch (IOException e) {
         				System.out.println("Error with input.");
         				return;
         			}
            	  
            		
            		List<DOFValue> fileContents = null;
					try {
						fileContents = requestor.getFileContents(name);
					} catch (DOFException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            		String contents = fileContents.get(0).toString();
            		
            		System.out.println(contents);
            		
              }else if(val == 2){
            	  DOFAbstraction dof = new DOFAbstraction();
          		dof.createDOF();
          		dof.createConnection(IPADDRESS, PORT);
          		Requestor requestor = new Requestor(dof.createDOFSystem("requestor"));
          		int op = DOFWriterOp.Append.ordinal();
          		
          		System.out.println("Enter name of file:");
        		String name = "";
        		try {
     				while ((name = in.readLine()) == null && name.length() == 0);
     			} catch (IOException e) {
     				System.out.println("Error with input.");
     				return;
     			}
        		
        		
        		boolean successful = true;
				try {
					successful = requestor.writeFileContents("def.txt", op, "Hello world");
				} catch (DOFException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		System.out.println(successful);
              }
              
              
              
        }else if(val ==3){
        	 System.out.println("Enter Number. What do you want to do with Twitter?");
             System.out.println("1. Select all tweets from user");
             System.out.println("2. Post to twitter account");
      
              userInput = "";
              val = checkIntReponse(userInput, val, in);	  
             
              if(val < 1 || val > 2){
              	System.out.println("Invalid Number");
              	return;
              }
              
              if(val == 1){
            	  
              }else if(val == 2){
            	  
              }
              
              System.out.println("Our goal is to bridge the Twitter API into the DOF system");
              System.out.println("The full bridge is not implemented yet.. ");
              System.out.println("However the bridge would allow a person to put up a twitter bridge with a single instance of DOFdb ");
              System.out.println(" ");
        	
        }
        
        Scanner sc = new Scanner(System.in);
    	while(!sc.nextLine().toLowerCase().equals("stop"));
    	sc.close();
    	
        
	}
	
	public static int checkIntReponse(String userInput,int val, BufferedReader in){
		while(true){
        	try{
        		while ((userInput = in.readLine()) == null && userInput.length() == 0);
        		val= Integer.parseInt(userInput);
        		return val;
        		
        	}catch(Exception e){
        		System.out.print("Please Enter an Integer");
        	}
        	
        }
	}
	
}
