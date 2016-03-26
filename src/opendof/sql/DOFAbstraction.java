package opendof.sql;

import java.util.Collection;
import java.util.HashMap;

import org.opendof.core.oal.DOF;
import org.opendof.core.oal.DOFAddress;
import org.opendof.core.oal.DOFConnection;
import org.opendof.core.oal.DOFException;
import org.opendof.core.oal.DOFServer;
import org.opendof.core.oal.DOFSystem;
import org.opendof.core.transport.inet.InetTransport;

public class DOFAbstraction {
    DOF dof;
    DOF.Config myDofConfig;
    HashMap<String, DOFSystem> systemMap = new HashMap<String, DOFSystem>(3);
    DOFConnection myConnection;
    DOFServer myServer;
    public static final int TIMEOUT = 5000;
    
    public DOFAbstraction(){

    }
    
    public DOF createDOF(){
        myDofConfig = new DOF.Config.Builder()
        .build();
    
        dof = new DOF(myDofConfig);
        return dof;
    }
    
    public DOF createRouterDOF(){
    	myDofConfig = new DOF.Config.Builder()
    			.setRouter(true)
    			.build();
    	dof = new DOF(myDofConfig);
    	return dof;
    }
    
    public DOFConnection createConnection(String address, int port){
        dof = new DOF(myDofConfig);
        DOFAddress myAddress = InetTransport.createAddress(address, port);
        DOFConnection.Config myConnectionConfig = new DOFConnection.Config.BuilderUnsecureStream(myAddress)
            .build();
        
        myConnection = dof.createConnection(myConnectionConfig);
        try {
            myConnection.connect(TIMEOUT);
        } catch (DOFException e) {
            // Handle exception
            System.out.println("Exception thrown when starting server: " + e.getMessage());
        }
        
        return myConnection;
    }
    
    public DOFServer createServer(String address, int port){
        DOFServer.Config myServerConfig;
        DOFAddress myAddress;
        myAddress = InetTransport.createAddress(address, port);
        myServerConfig = new DOFServer.Config.BuilderUnsecureStream(myAddress)
            .build();
        myServer = dof.createServer(myServerConfig);
        
        try {
            myServer.start(TIMEOUT);
        } catch (DOFException e) {
            //Handle exception
            System.out.println("Exception thrown when starting server: " + e.getMessage());
        }
        
        return myServer;
    }
    
    public DOFSystem createDOFSystem(String _name){
        DOFSystem returnSystem;
        DOFSystem.Config systemConfig;
        if(dof != null){
            systemConfig = new DOFSystem.Config.BuilderUnsecure()
                .setName(_name)
                .build();
            
            try {
                returnSystem = dof.createSystem(systemConfig, 0);
                systemMap.put(_name, returnSystem);
                return returnSystem;
            } catch (DOFException e) {
                
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public DOFSystem getDOFSystem(String _name){
        return systemMap.get(_name);
    }
    
    public void destroy(){
        Collection<DOFSystem> systemList = systemMap.values(); 
        
        for(DOFSystem system: systemList){
            system.destroy();
        }
        dof.setNodeDown();
        dof.destroy();
    }
}


