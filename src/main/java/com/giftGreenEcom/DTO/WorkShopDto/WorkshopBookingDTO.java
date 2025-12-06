package com.giftGreenEcom.DTO.WorkShopDto;

import java.time.LocalDate;
import java.time.LocalTime;

public class WorkshopBookingDTO {
    private Long bookingId;
   // private Long workshopId;
    private String workshopName;
    private LocalDate workshopDate;
    private LocalTime workshopTime;
    private String price;

   // private Long userId;
    private String userName;
    private String userEmail;
    private String phone;
    private LocalDate bookingDate;

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

//    public Long getWorkshopId() {
//        return workshopId;
//    }
//
//    public void setWorkshopId(Long workshopId) {
//        this.workshopId = workshopId;
//    }

    public String getWorkshopName() {
        return workshopName;
    }

    public void setWorkshopName(String workshopName) {
        this.workshopName = workshopName;
    }

    public LocalDate getWorkshopDate() {
        return workshopDate;
    }

    public void setWorkshopDate(LocalDate workshopDate) {
        this.workshopDate = workshopDate;
    }

    public LocalTime getWorkshopTime() {
        return workshopTime;
    }

    public void setWorkshopTime(LocalTime workshopTime) {
        this.workshopTime = workshopTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

//    public Long getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
