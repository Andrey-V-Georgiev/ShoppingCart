package com.shopping_cart.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.shopping_cart.constants.ModelsMsgConstants.*;

@Entity
@Table(name = "contacts")
public class Contact extends BaseEntity {

    private String cityName;
    private String iframeUrl;
    private String address;
    private String tel;
    private String email;
    private String workingHoursWeek;
    private String workingHoursWeekend;

    public Contact() {
    }

    public Contact(String cityName, String iframeUrl, String address, String tel, String email, String workingHoursWeek, String workingHoursWeekend) {
        this.cityName = cityName;
        this.iframeUrl = iframeUrl;
        this.address = address;
        this.tel = tel;
        this.email = email;
        this.workingHoursWeek = workingHoursWeek;
        this.workingHoursWeekend = workingHoursWeekend;
    }

    @NotEmpty(message = CITY_NAME_NOT_EMPTY)
    @NotNull(message = CITY_NAME_NOT_NULL)
    @Size(min = 3, message = CITY_NAME_LENGTH)
    @Column(name = "city_name", unique = true )
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @NotEmpty(message = IFRAME_URL_NOT_EMPTY)
    @NotNull(message = IFRAME_URL_NOT_NULL)
    @Size(min = 3, message = IFRAME_URL_LENGTH)
    @Column(name = "iframe_url", columnDefinition="TEXT")
    public String getIframeUrl() {
        return iframeUrl;
    }

    public void setIframeUrl(String iframeUrl) {
        this.iframeUrl = iframeUrl;
    }

    @NotEmpty(message = CONTACT_ADDRESS_NOT_EMPTY)
    @NotNull(message = CONTACT_ADDRESS_NOT_NULL)
    @Size(min = 3,  message = CONTACT_ADDRESS_LENGTH)
    @Column(name = "address", columnDefinition="TEXT")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @NotEmpty(message = CONTACT_TEL_NOT_EMPTY)
    @NotNull(message = CONTACT_TEL_NOT_NULL)
    @Size(min = 3, message = CONTACT_TEL_LENGTH)
    @Column(name = "tel")
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @NotEmpty(message = CONTACT_EMAIL_NOT_EMPTY)
    @NotNull(message = CONTACT_EMAIL_NOT_NULL)
    @Pattern(regexp = USER_EMAIL_REGEX, message = CONTACT_EMAIL_REGEX_MSG)
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotEmpty(message = WH_WEEK_NOT_EMPTY)
    @NotNull(message = WH_WEEK_NOT_NULL)
    @Size(min = 3, message = WH_WEEK_LENGTH)
    @Column(name = "working_hours_week")
    public String getWorkingHoursWeek() {
        return workingHoursWeek;
    }

    public void setWorkingHoursWeek(String workingHoursWeek) {
        this.workingHoursWeek = workingHoursWeek;
    }

    @NotEmpty(message = WH_WEEKEND_NOT_EMPTY)
    @NotNull(message = WH_WEEKEND_NOT_NULL)
    @Size(min = 3, message = WH_WEEKEND_LENGTH)
    @Column(name = "working_hours_weekend")
    public String getWorkingHoursWeekend() {
        return workingHoursWeekend;
    }

    public void setWorkingHoursWeekend(String workingHoursWeekend) {
        this.workingHoursWeekend = workingHoursWeekend;
    }
}
