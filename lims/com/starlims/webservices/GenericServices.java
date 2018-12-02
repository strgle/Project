package com.starlims.webservices;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;

import pers.czf.commonUtils.ProjectUtils;

@WebServiceClient 
public class GenericServices extends Service {
    public static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("http://www.starlims.com/webservices/", "GenericServices");
    public final static QName GenericServicesSoap = new QName("http://www.starlims.com/webservices/", "GenericServicesSoap");

    public GenericServices(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    @WebEndpoint(name = "GenericServicesSoap")
    public GenericServicesSoap getGenericServicesSoap() {
        return super.getPort(GenericServicesSoap, GenericServicesSoap.class);
    }
    
    public static URL wsdlUrl(){
    	if(WSDL_LOCATION==null){
    		Properties p = new Properties();
    		String filePath = ProjectUtils.classFilePath("default.properties");
    		InputStream ins = null;
    		try {
    			ins = new FileInputStream(filePath);
    			p.load(ins);
    			String starLimsWsdl = p.getProperty("STARLIMS_WSDL");
    			WSDL_LOCATION = new URL(starLimsWsdl);
    		}catch (IOException e) {//022701
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}finally{
    			try {
    				if(ins!=null){
    					ins.close();
    				}
    			} catch (IOException e) {
    					// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		}
    	}
    	return WSDL_LOCATION;
    }
    

}
