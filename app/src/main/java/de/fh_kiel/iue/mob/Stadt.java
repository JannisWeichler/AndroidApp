package de.fh_kiel.iue.mob;

public class Stadt {
    private String stadt;
    private int temp,luftfeuchtigkeit;

    Stadt(String dieStadt, int dietemp, int dieluftfeuchtigkeit){
        stadt = dieStadt;
        temp = dietemp;
        luftfeuchtigkeit = dieluftfeuchtigkeit;
    }

    public String getStadt(){
        return stadt;
    }

    public int getTemp(){
        return temp;
    }

    public int getLuftfeuchtigkeit(){
        return luftfeuchtigkeit;
    }
}

