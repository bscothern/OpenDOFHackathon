package opendof.sql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

import org.opendof.core.oal.DOFException;
import org.opendof.core.oal.DOFValue;


public class MainSQLRequestor {
	
	 static DOFAbstraction requestorDofAbstraction; // Represents the whole DOF
	 static SQLRequestor requestor;
	 static String IPADDRESS = "198.69.31.59"; 
	 static int PORT = 3567;

	public static void main(String[] args){
	    
		
        
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.println("======WELCOME TO DOFdb=====");
        System.out.println("Enter Number. Do you want to communicate with..");
        System.out.println("1. SQL");
        System.out.println("2. DOFWrite File System");
        System.out.println("3. Twitter API");
        
        String userInput = "";
        int val = 0;
        
        val = checkIntReponse(userInput, val, in);
        
        if(val < 1 || val > 2){
        	System.out.println("Invalid Number");
        	return;
        }
        
        if(val == 1){
        
       System.out.println("Connecting to DOFDB... Make sure SQLDB is running");
       requestorDofAbstraction = new DOFAbstraction();
       requestorDofAbstraction.createDOF();
       requestorDofAbstraction.createConnection("128.110.78.153", PORT);
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
