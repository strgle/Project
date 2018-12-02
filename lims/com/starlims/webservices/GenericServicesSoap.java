package com.starlims.webservices;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


@WebService(targetNamespace = "http://www.starlims.com/webservices/", name = "GenericServicesSoap")
@XmlSeeAlso({ObjectFactory.class})
public interface GenericServicesSoap {
    
    @WebMethod(operationName = "UserLogin", action = "http://www.starlims.com/webservices/UserLogin")
    @RequestWrapper(localName = "UserLogin", targetNamespace = "http://www.starlims.com/webservices/", className = "com.starlims.webservices.UserLogin")
    @ResponseWrapper(localName = "UserLoginResponse", targetNamespace = "http://www.starlims.com/webservices/", className = "com.starlims.webservices.UserLoginResponse")
    @WebResult(name = "UserLoginResult", targetNamespace = "http://www.starlims.com/webservices/")
    public boolean userLogin(
        @WebParam(name = "UserName", targetNamespace = "http://www.starlims.com/webservices/")
        java.lang.String userName,
        @WebParam(name = "Password", targetNamespace = "http://www.starlims.com/webservices/")
        java.lang.String password
    );
    

    /**
     * Run any STARLIMS action. This web service method is here to provide the maximum flexibility in accessing STARLIMS functionality.
     */
    @WebMethod(operationName = "RunAction", action = "http://www.starlims.com/webservices/RunAction")
    @RequestWrapper(localName = "RunAction", targetNamespace = "http://www.starlims.com/webservices/", className = "com.starlims.webservices.RunAction")
    @ResponseWrapper(localName = "RunActionResponse", targetNamespace = "http://www.starlims.com/webservices/", className = "com.starlims.webservices.RunActionResponse")
    @WebResult(name = "RunActionResult", targetNamespace = "http://www.starlims.com/webservices/")
    public java.lang.Object runAction(
        @WebParam(name = "actionID", targetNamespace = "http://www.starlims.com/webservices/")
        java.lang.String actionID,
        @WebParam(name = "parameters", targetNamespace = "http://www.starlims.com/webservices/")
        com.starlims.webservices.ArrayOfAnyType parameters
    );
    
    /**
     * Run any STARLIMS action in one single call passing all information it requires together with STARLIMS Credentials. This web service method is here to provide the maximum flexibility in accessing STARLIMS functionality and is intended to be used from very rare calls to STARLIMS.
     */
    @WebMethod(operationName = "RunActionDirect", action = "http://www.starlims.com/webservices/RunActionDirect")
    @RequestWrapper(localName = "RunActionDirect", targetNamespace = "http://www.starlims.com/webservices/", className = "com.starlims.webservices.RunActionDirect")
    @ResponseWrapper(localName = "RunActionDirectResponse", targetNamespace = "http://www.starlims.com/webservices/", className = "com.starlims.webservices.RunActionDirectResponse")
    @WebResult(name = "RunActionDirectResult", targetNamespace = "http://www.starlims.com/webservices/")
    public java.lang.Object runActionDirect(
        @WebParam(name = "actionID", targetNamespace = "http://www.starlims.com/webservices/")
        java.lang.String actionID,
        @WebParam(name = "parameters", targetNamespace = "http://www.starlims.com/webservices/")
        com.starlims.webservices.ArrayOfAnyType parameters,
        @WebParam(name = "UserName", targetNamespace = "http://www.starlims.com/webservices/")
        java.lang.String userName,
        @WebParam(name = "Password", targetNamespace = "http://www.starlims.com/webservices/")
        java.lang.String password
    );

}
