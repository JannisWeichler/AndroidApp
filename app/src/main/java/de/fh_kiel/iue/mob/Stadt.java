package de.fh_kiel.iue.mob;

public class Stadt {

    private String stadtName;
    private long timezone;
    private long letzteAkt;
    private Weather[] weather;
    private Main main;
    private Wind wind;
    private Sys sys;
    private Cloud clouds;

    Stadt(String stadtName, Weather[] weather, Main main, Wind wind, Sys sys, Cloud cloud){
        this.stadtName = stadtName;
        this.weather = weather;
        this.main = main;
        this.wind = wind;
        this.sys = sys;
        this.clouds = cloud;
    }

    static class Weather{
        String description;

        Weather(String description){
            this.description=description;
        }
    }

    static class Main{
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

    static class Wind{
        double speed;
        int deg;
        Wind(double speed,int deg){
            this.speed = speed;
            this.deg = deg;
        }
    }

    static class Sys{
        long sunrise;
        long sunset;
        Sys(long sunrise,long sunset){
            this.sunrise = sunrise;
            this.sunset = sunset;
        }
    }

    static class Cloud{
        int all;
        Cloud(int all){
            this.all = all;
        }
    }


    //Ein-/ Auslesen
    String getStadtName(){
        return stadtName;
    }

    long getLetzteAkt() {
        return letzteAkt;
    }

    void setLetzteAkt(long letzeAkt){
        this.letzteAkt = letzeAkt;
    }

    String getDescription() {
        return weather[0].description;
    }

    long getTimezone() {
        return timezone;
    }

    double getTemp() {
        return main.temp;
    }

    int getPressure() {
        return main.pressure;
    }

    int getHumidity() {
        return main.humidity;
    }

    double getTemp_min(){
        return main.temp_min;
    }

    double getTemp_max(){
        return main.temp_max;
    }

    double getSpeed(){
        return wind.speed;
    }

    int getDeg(){
        return wind.deg;
    }

    long getSunrise(){
        return sys.sunrise;
    }

    long getSunset(){
        return sys.sunset;
    }

    int getCloudAll(){
        return clouds.all;
    }

    void setData(Stadt data){
        this.timezone = data.timezone;
        this.letzteAkt = data.letzteAkt;
        this.weather = data.weather;
        this.main = data.main;
        this.clouds = data.clouds;
        this.sys = data.sys;
        this.wind = data.wind;
    }
}

