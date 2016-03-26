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
	 static String IPADDRESS = "128.110.78.153"; 
	 static int PORT = 3567;

	public static void main(String[] args){
	    
		System.out.println("Connecting to DOFDB... Make sure SQLDB is running");
		requestorDofAbstraction = new DOFAbstraction();
        requestorDofAbstraction.createDOF();
        requestorDofAbstraction.createConnection("128.110.78.153", PORT);
        requestor = new SQLRequestor(requestorDofAbstraction.createDOFSystem("requestor"));
        
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.println("======WELCOME TO DOFdb=====");
        System.out.println("Enter Number. Do you want to communicate with..");
        System.out.println("1. SQL");
        System.out.println("2. Twitter");
        
        
        String userInput = "";
        int val = 0;
        
        val = checkIntReponse(userInput, val, in);
        
        if(val < 1 || val > 2){
        	System.out.println("Invalid Number");
        	return;
        }
        
        if(val == 1){
        
        
        List<DOFValue> d = null;
        try {
			d = requestor.sendSelectQuery("UserTable|");
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
        
        }else if(val==2){
        	 List<DOFValue> d = null;
        try {
			d = requestor.sendSelectQuery("getByName|admin");
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
