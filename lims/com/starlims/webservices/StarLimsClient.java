
package com.starlims.webservices;

import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;


public final class StarLimsClient {

    private static final QName SERVICE_NAME = new QName("http://www.starlims.com/webservices/", "GenericServices");

    private StarLimsClient() {
    	
    }
    
    public static boolean checkUser(String userName,String password){
    	URL wsdlURL = GenericServices.wsdlUrl();
        GenericServices ss = new GenericServices(wsdlURL, SERVICE_NAME);
        GenericServicesSoap port = ss.getGenericServicesSoap();  
        return port.userLogin(userName, password);
    }
    
    public static Object batchId(){
    	URL wsdlURL = GenericServices.wsdlUrl();
        GenericServices ss = new GenericServices(wsdlURL, SERVICE_NAME);
        GenericServicesSoap port = ss.getGenericServicesSoap();  
        return port.runActionDirect("ws.batchid", null,"SYSADM","LIMS");
    }
    
    public static Object folderNo(String yyMMdd){
    	URL wsdlURL = GenericServices.wsdlUrl();
        GenericServices ss = new GenericServices(wsdlURL, SERVICE_NAME);
        GenericServicesSoap port = ss.getGenericServicesSoap();  
        ArrayOfAnyType array = new ArrayOfAnyType();
        List<Object> list = array.getAnyType();
        list.add("E");
        list.add(yyMMdd);
        return port.runActionDirect("ws.nextFolderno", array,"SYSADM","LIMS");
    }
    
    public static void main(String[] args){
    	System.out.println(StarLimsClient.batchId());
    }
   

}
