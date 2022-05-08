package com.example.entregable1.Entity;

import com.example.entregable1.Constantes;

import java.util.Arrays;
import java.util.List;

public class Trip {
    private String destination;
    private String description;
    private int price;
    private String startDate;
    private String endDate;
    private String startPlace;
    private String urlImageViewTrip;
    private boolean isSelected;

    public Trip(String destination, String description, int price, String startDate, String endDate,
                String startPlace, String urlImageViewTrip, boolean isSelected) {
        this.destination = destination;
        this.description = description;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startPlace = startPlace;
        this.urlImageViewTrip = urlImageViewTrip;
        this.isSelected = false;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    public String getUrlImageViewTrip() {
        return urlImageViewTrip;
    }

    public void setUrlImageViewTrip(String urlImageViewTrip) {
        this.urlImageViewTrip = urlImageViewTrip;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public static List<Trip> listaViajes() {
        return Arrays.asList(Constantes.viajes);
    }

    @Override
    public String toString() {
        return "Trip{" +
                "destination='" + destination + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", startPlace='" + startPlace + '\'' +
                ", urlImageViewTrip='" + urlImageViewTrip + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
