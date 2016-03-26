package opendof.sql;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.opendof.training.security.DOFAbstraction;


public class MainSQLRequestor {
	
	 static DOFAbstraction requestorDofAbstraction; // Represents the whole DOF
	 static SQLRequestor requestor;
	 static String IPADDRESS = "128.110.78.153"; 
	 static int PORT = 3567;

	public static void main(String[] args){
	    
		requestorDofAbstraction = new DOFAbstraction();
        requestorDofAbstraction.createDOF();
        requestorDofAbstraction.createConnection("128.110.78.153", PORT);
        requestor = new SQLRequestor(requestorDofAbstraction.createDOFSystem("requestor"));
        
        System.out.println("HEY!!!");
        
        requestor.sendSetRequest(true);
        
        Scanner sc = new Scanner(System.in);
    	while(!sc.nextLine().toLowerCase().equals("stop"));
    	sc.close();
    	
        
	}
	
}
