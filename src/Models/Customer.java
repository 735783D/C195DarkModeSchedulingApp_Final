package Models;

/** This class is an object constructor used to create customer objects in the database and get/set their values.*/
public class Customer {
    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private String division;
    private String country;
    private int divisionId;

    /** @param customerId Int value of Customer ID
     * @param customerName String value of Customer Name
     * @param address String value of Address
     * @param postalCode String value of Postal Code
     * @param phoneNumber String value of Phone Number
     * @param division String value of Division
     * @param country String value of Country
     * @param divisionId Int value of Division ID
     */
    public Customer(int customerId,
                    String customerName,
                    String address,
                    String postalCode,
                    String phoneNumber,
                    String division,
                    String country,
                    int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.division = division;
        this.country = country;
        this.divisionId = divisionId;
    }

    /** Gets Customer ID parameter from database.
     * @return customerId Integer value of Customer ID*/
    public int getCustomerId() {
        return customerId;
    }

    /** Sets Customer ID parameter in database.
     * @param customerId Integer value of Customer ID*/
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /** Gets Customer Name parameter from database.
     * @return customerName String value of Customer Name */
    public String getCustomerName() {
        return customerName;
    }

    /** Sets Customer Name parameter in database.
     * @param customerName String value of Customer Name*/
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /** Gets Address parameter from database.
     * @return address String value of Address */
    public String getAddress() {
        return address;
    }

    /** Sets Address parameter in database.
     * @param address String value of Address*/
    public void setAddress(String address) {
        this.address = address;
    }

    /** Gets Postal Code parameter from database.
     * @return postalCode String value of Postal Code */
    public String getPostalCode() {
        return postalCode;
    }

    /** Sets Postal Code parameter in database.
     * @param postalCode String value of Postal Code*/
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /** Gets Phone Number parameter from database.
     * @return phoneNumber String value of Phone Number */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /** Sets Phone Number parameter in database.
     * @param phoneNumber String value of Phone Number*/
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /** Gets Division parameter from database.
     * @return division String value of Division */
    public String getDivision() {
        return division;
    }

    /** Sets Division Name parameter in database.
     * @param division String value of Division Name*/
    public void setDivision(String division) {
        this.division = division;
    }

    /** Gets Country Name parameter from database.
     * @return country String value of Country Name */
    public String getCountry() {
        return country;
    }

    /** Sets Country Name parameter in database.
     * @param country String value of Country Name*/
    public void setCountry(String country) {
        this.country = country;
    }

    /** Gets Division ID parameter from database.
     * @return divisionId Integer value of Division ID*/
    public int getDivisionId() {
        return divisionId;
    }

    /** Sets Division ID parameter in database.
     * @param divisionId Integer value of Division ID*/
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
}
