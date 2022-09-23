package Models;

/** This class is an object constructor used to create contact objects in the database and get/set their values.*/
public class Contact {

    private int contactId;
    private String contactName;
    private String contactEmail;

    /** @param contactId Int value of Contact ID
     * @param contactName String value of Contact Name
     * @param contactEmail String value of Contact Email
     */
    public Contact (int contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }
    /** Gets Contact ID parameter from database.
     * @return contactId Integer value of Contact ID*/
    public int getContactId() {
        return contactId;
    }

    /** Sets Contact ID parameter in database.
     * @param contactId Integer value of Contact ID*/
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /** Gets Contact Name parameter from database.
     * @return contactName String value of Contact Name*/
    public String getContactName() {
        return contactName;
    }

    /** Sets Contact Name parameter in database.
     * @param contactName String value of Contact Name*/
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /** Gets Contact Email parameter from database.
     * @return contactEmail String value of Contact Email*/
    public String getContactEmail() {
        return contactEmail;
    }

    /** Sets Contact Email parameter in database.
     * @param contactEmail String value of Contact Email*/
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}

