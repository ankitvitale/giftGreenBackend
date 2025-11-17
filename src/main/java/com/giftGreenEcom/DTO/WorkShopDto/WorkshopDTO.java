package com.giftGreenEcom.DTO.WorkShopDto;

import java.time.LocalDate;
import java.time.LocalTime;

public class WorkshopDTO {
    private Long id;
    private String nameOfWorkShop;
    private LocalDate date;
    private LocalTime time;
    private String price;

    public WorkshopDTO() {}

    public WorkshopDTO(Long id, String nameOfWorkShop, LocalDate date, LocalTime time, String price) {
        this.id = id;
        this.nameOfWorkShop = nameOfWorkShop;
        this.date = date;
        this.time = time;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameOfWorkShop() {
        return nameOfWorkShop;
    }

    public void setNameOfWorkShop(String nameOfWorkShop) {
        this.nameOfWorkShop = nameOfWorkShop;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
