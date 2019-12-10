package de.fh_kiel.iue.mob;

public class Stadt {

    private String stadtName;
    private Main main;
    private Wind wind;
    private Sys sys;
    private Cloud cloud;

    Stadt(String stadtName, Main main, Wind wind, Sys sys, Cloud cloud){
        this.stadtName = stadtName;
        this.main = main;
        this.wind = wind;
        this.sys = sys;
        this.cloud = cloud;
        /*new Main(main.temp,main.pressure,main.humidity,main.temp_min,main.temp_max);
        new Wind(wind.speed,wind.deg);
        new Sys(sys.sunrise,sys.sunset);
        new Cloud(cloud.all);*/
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

    public double getSunrise(){
        return sys.sunrise;
    }

    public double getSunset(){
        return sys.sunset;
    }

    public int getCloudAll(){
        return cloud.all;
    }
}

