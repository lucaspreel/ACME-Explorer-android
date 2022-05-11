package com.example.entregable1.resttypes;

import java.util.List;

public class WeatherResponse {
    private WeatherLatLong cooord;
    private List<Weather> weather;
    private String base;
    private WeatherConditions main;
    private WeatherWind wind;
    private long timezone;
    private long id;
    private String name;
    private int code;

    public WeatherResponse(WeatherLatLong cooord,
                           List<Weather> weather,
                           String base,
                           WeatherConditions main,
                           WeatherWind wind,
                           long timezone,
                           long id,
                           String name,
                           int code) {
        this.cooord = cooord;
        this.weather = weather;
        this.base = base;
        this.main = main;
        this.wind = wind;
        this.timezone = timezone;
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public WeatherLatLong getCooord() {
        return cooord;
    }

    public void setCooord(WeatherLatLong cooord) {
        this.cooord = cooord;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public WeatherConditions getMain() {
        return main;
    }

    public void setMain(WeatherConditions main) {
        this.main = main;
    }

    public WeatherWind getWind() {
        return wind;
    }

    public void setWind(WeatherWind wind) {
        this.wind = wind;
    }

    public long getTimezone() {
        return timezone;
    }

    public void setTimezone(long timezone) {
        this.timezone = timezone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
