
package com.starlims.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "userLoginResult"
})
@XmlRootElement(name = "UserLoginResponse")
public class UserLoginResponse {

    @XmlElement(name = "UserLoginResult")
    protected boolean userLoginResult;

    /**
     * ��ȡuserLoginResult���Ե�ֵ��
     * 
     */
    public boolean isUserLoginResult() {
        return userLoginResult;
    }

    /**
     * ����userLoginResult���Ե�ֵ��
     * 
     */
    public void setUserLoginResult(boolean value) {
        this.userLoginResult = value;
    }

}
