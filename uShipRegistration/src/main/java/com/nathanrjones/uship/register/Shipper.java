package com.nathanrjones.uship.register;

/**
 * Created by nathan on 11/18/13.
 */
public class Shipper {
    private String ShipperType;
    private String FirstName;
    private String LastName;
    private String EmailAddress;
    private String CompanyName;
    private String Password;

    public Shipper(){
    }
    
    public Shipper(String shipperType, String firstName, String lastName,
                   String emailAddress, String companyName, String password){
        ShipperType = shipperType;
        FirstName = firstName;
        FirstName = lastName;
        EmailAddress = emailAddress;
        CompanyName = companyName;
        Password = password;
    }

    public String getShipperType() {
        return ShipperType;
    }

    public void setShipperType(String shipperType) {
        ShipperType = shipperType;
    }

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

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
