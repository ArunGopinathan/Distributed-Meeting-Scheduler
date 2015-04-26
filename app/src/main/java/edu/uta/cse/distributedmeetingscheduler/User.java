package edu.uta.cse.distributedmeetingscheduler;



import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.Collection;

@Root
public class User {
    @Element
    private String FirstName;
    @Element
    private String LastName;
    @Element
    private String MavEmail;
    @Element(required =false)
    private String AndroidDeviceId;
    @ElementList


    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getMavEmail() {
        return MavEmail;
    }

    public void setMavEmail(String mavEmail) {
        MavEmail = mavEmail;
    }

    public String getAndroidDeviceId() {
        return AndroidDeviceId;
    }

    public void setAndroidDeviceId(String androidDeviceId) {
        AndroidDeviceId = androidDeviceId;
    }
}
