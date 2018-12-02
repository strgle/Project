
package com.starlims.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="actionID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="parameters" type="{http://www.starlims.com/webservices/}ArrayOfAnyType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "actionID",
    "parameters"
})
@XmlRootElement(name = "RunAction")
public class RunAction {

    protected String actionID;
    protected ArrayOfAnyType parameters;

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

}
