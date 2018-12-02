
package com.starlims.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "actionID",
    "parameters",
    "userName",
    "password"
})
@XmlRootElement(name = "RunActionDirect")
public class RunActionDirect {

    protected String actionID;
    protected ArrayOfAnyType parameters;
    @XmlElement(name = "UserName")
    protected String userName;
    @XmlElement(name = "Password")
    protected String password;

    public String getActionID() {
        return actionID;
    }

    public void setActionID(String value) {
        this.actionID = value;
    }

    public ArrayOfAnyType getParameters() {
        return parameters;
    }

    public void setParameters(ArrayOfAnyType value) {
        this.parameters = value;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String value) {
        this.userName = value;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String value) {
        this.password = value;
    }

}
