package com.shopping_cart.models.service_models;

import com.shopping_cart.models.entities.BaseEntity;

public class ContactServiceModel extends BaseEntity {

    private String cityName;
    private String iframeUrl;
    private String address;
    private String tel;
    private String email;
    private String workingHoursWeek;
    private String workingHoursWeekend;

    public ContactServiceModel() {
    }

    public ContactServiceModel(String cityName, String iframeUrl, String address, String tel, String email, String workingHoursWeek, String workingHoursWeekend) {
        this.cityName = cityName;
        this.iframeUrl = iframeUrl;
        this.address = address;
        this.tel = tel;
        this.email = email;
        this.workingHoursWeek = workingHoursWeek;
        this.workingHoursWeekend = workingHoursWeekend;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getIframeUrl() {
        return iframeUrl;
    }

    public void setIframeUrl(String iframeUrl) {
        this.iframeUrl = iframeUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWorkingHoursWeek() {
        return workingHoursWeek;
    }

    public void setWorkingHoursWeek(String workingHoursWeek) {
        this.workingHoursWeek = workingHoursWeek;
    }

    public String getWorkingHoursWeekend() {
        return workingHoursWeekend;
    }

    public void setWorkingHoursWeekend(String workingHoursWeekend) {
        this.workingHoursWeekend = workingHoursWeekend;
    }
}
