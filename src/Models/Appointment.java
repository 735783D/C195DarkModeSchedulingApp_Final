package Models;

import java.time.LocalDate;
import java.time.LocalDateTime;

/** This class is an object constructor used to create appointment objects in the database and get/set their values.*/
public class Appointment {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDate startDate;
    private LocalDateTime startTime;
    private LocalDate endDate;
    private LocalDateTime endTime;
    private int customerId;
    private int userId;
    private int contactId;


    /** @param appointmentId Int value of Appointment ID
     * @param title String value of Title
     * @param description String value of Description
     * @param location String value of Location
     * @param type String value of Type
     * @param startDate LocalDate value of Start Date
     * @param startTime LocalDateTime value of Start Time
     * @param endDate LocalDate value of End Date
     * @param endTime LocalDateTime value of End Time
     * @param customerId Int value of Customer ID
     * @param userId Int value of User ID
     * @param contactId Int value of Contact ID */
    public Appointment(int appointmentId,
                    String title,
                    String description,
                    String location,
                    String type,
                    LocalDate startDate,
                    LocalDateTime startTime,
                    LocalDate endDate,
                    LocalDateTime endTime,
                    int customerId,
                    int userId,
                    int contactId
    ) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
     }


    public Appointment(int appointment_id, String title, String description, String location, int customer_id, String type, LocalDate start, LocalDateTime start1, LocalDate end, LocalDateTime end1, int user_id, int contact_id) {

    }

    /** Gets Appointment ID parameter from database.
     * @return appointmentId Integer value of Appointment ID*/
    public int getAppointmentId() {
        return appointmentId;
    }

    /** Sets Appointment ID parameter in database.
     * @param appointmentId Integer value of Appointment ID*/
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /** Gets Appointment Title parameter from database.
     * @return title String value of Appointment Name */
    public String getTitle() {
        return title;
    }

    /** Sets Appointment Title parameter in database.
     * @param title String value of Appointment Title*/
    public void setTitle(String title) {
        this.title = title;
    }

    /** Gets Appointment Description parameter from database.
     * @return description String value of Appointment Description */
    public String getDescription() {
        return description;
    }

    /** Sets Appointment Description parameter in database.
     * @param description String value of Appointment Description*/
    public void setDescription(String description) {
        this.description = description;
    }

    /** Gets Appointment Location parameter from database.
     * @return location String value of Appointment Location */
    public String getLocation() {
        return location;
    }

    /** Sets Appointment Location parameter in database.
     * @param location String value of Appointment Location*/
    public void setLocation(String location) {
        this.location = location;
    }

    /** Gets Appointment Type parameter from database.
     * @return type String value of Appointment Type */
    public String getType() {
        return type;
    }

    /** Sets Appointment Type parameter in database.
     * @param type String value of Appointment Type*/
    public void setType(String type) {
        this.type = type;
    }

    /** Gets Start Date parameter from database.
     * @return startDate LocalDate value of Appointment Start Date */
    public LocalDate getStartDate() {
        return startDate;
    }

    /** Sets Appointment Start Date parameter in database.
     * @param startDate LocalDate value of Appointment Start Date*/
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /** Gets Start Time parameter from database.
     * @return startTime LocalDateTime value of Appointment Start Time */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /** Sets Appointment Start Time parameter in database.
     * @param startTime LocalDateTime value of Appointment Start Time*/
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /** Gets End Date parameter from database.
     * @return endDate LocalDate value of Appointment End Date */
    public LocalDate getEndDate() {
        return endDate;
    }

    /** Sets Appointment End Date parameter in database.
     * @param endDate LocalDate value of Appointment End Date*/
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /** Gets End Time parameter from database.
     * @return endTime LocalDateTime value of Appointment End Time */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /** Sets Appointment End Time parameter in database.
     * @param endTime LocalDateTime value of Appointment End Time*/
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
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

    /** Gets User ID parameter from database.
     * @return userId Integer value of User ID*/
    public int getUserId() {
        return userId;
    }

    /** Sets User ID parameter in database.
     * @param userId Integer value of User ID*/
    public void setUserId(int userId) {
        this.userId = userId;
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
}