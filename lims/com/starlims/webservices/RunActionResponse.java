
package com.starlims.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "runActionResult"
})
@XmlRootElement(name = "RunActionResponse")
public class RunActionResponse {

    @XmlElement(name = "RunActionResult")
    protected Object runActionResult;

    public Object getRunActionResult() {
        return runActionResult;
    }

    public void setRunActionResult(Object value) {
        this.runActionResult = value;
    }

}
