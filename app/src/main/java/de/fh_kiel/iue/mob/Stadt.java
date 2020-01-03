package de.fh_kiel.iue.mob;

import java.util.Date;

public class Stadt {

    private String stadtName;
    private long dt;
    private long timezone;
    private Main main;
    private Wind wind;
    private Sys sys;
    private Cloud clouds;

    Stadt(String stadtName, Main main, Wind wind, Sys sys, Cloud cloud){
        this.stadtName = stadtName;
        this.main = main;
        this.wind = wind;
        this.sys = sys;
        this.clouds = cloud;
    }


    public static class Main{
        double temp;
        int pressure;
        int humidity;
        double temp_min;
        double temp_max;

        Main(double temp, int pressure, int humidity, double temp_min, double temp_max){
            this.temp = temp;
            this.pressure = pressure;
            this.humidity = humidity;
            this.temp_min = temp_min;
            this.temp_max = temp_max;
        }
    }

    public static class Wind{
        double speed;
        int deg;
        Wind(double speed,int deg){
            this.speed = speed;
            this.deg = deg;
        }
    }

    public static class Sys{
        long sunrise;
        long sunset;
        Sys(long sunrise,long sunset){
            this.sunrise = sunrise;
            this.sunset = sunset;
        }
    }
    public static class Cloud{
        int all;
        Cloud(int all){
            this.all = all;
        }
    }


    //Ein-/ Auslesen
    public String getStadtName (){
        return stadtName;
    }

    public void setStadtName (String stadtName){
        this.stadtName = stadtName;
    }

    public long getDt () {
        return dt;
    }

    public long getTimezone() {
        return timezone;
    }

    public double getTemp () {
        return main.temp;
    }

    public int getPressure () {
        return main.pressure;
    }

    public int getHumidity () {
        return main.humidity;
    }

    public double getTemp_min(){
        return main.temp_min;
    }

    public double getTemp_max(){
        return main.temp_max;
    }

    public double getSpeed(){
        return wind.speed;
    }

    public int getDeg(){
        return wind.deg;
    }

    public long getSunrise(){
        return sys.sunrise;
    }

    public long getSunset(){
        return sys.sunset;
    }

    public int getCloudAll(){
        return clouds.all;
    }

    public void setData (Stadt data){
        this.dt = data.dt;
        this.timezone = data.timezone;
        this.main = data.main;
        this.clouds = data.clouds;
        this.sys = data.sys;
        this.wind = data.wind;
    }
}

