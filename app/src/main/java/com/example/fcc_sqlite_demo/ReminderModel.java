package com.example.fcc_sqlite_demo;

public class ReminderModel {
    private int id;
    private String title;
    private String date;
    private String time;
    private int level;
    private String scannedCode;
    private boolean isImportant;


    public ReminderModel(int id, String title, String date, String time, int level, String scannedCode, boolean isImportant) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.time = time;
        this.level = level;
        this.scannedCode = scannedCode;
        this.isImportant = isImportant;

    }

    //public ReminderModel() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public void setImportant(boolean important) {
        isImportant = important;
    }

    public String getScannedCode() {
        return scannedCode;
    }

    public void setScannedCode(String scannedCode) {
        this.scannedCode = scannedCode;
    }

    /*
    @Override
    public String toString() {
        return "ReminderModel{" +
                "title='" + title + '\'' +
                ", date=" + date +
                ", isImportant=" + isImportant +
                '}';
    }
    */

    /*
    @Override
    public String toString() {
        if (isImportant) {
            return "titel: " + title + "  |  " + "datum: " + date + "  |  " + "tid: " + time + "  |  " +"våning: " + level + "  |  " + "kod: " + scannedCode + "  |  " + "VIKTIGT";
        } else {

        }
            return "titel: " + title + "  |  " + "datum: " + date + "  |  " + "tid: " + time + "  |  " +"våning: " + level + "  |  " + "kod: " + scannedCode;
    }
    */

    @Override
    public String toString() {
        if (isImportant) {
            return "                    OBS! VIKTIGT!" + "\n" +
                    "titel:         " + title + "\n" +
                    "datum:     " + date + "\n" +
                    "tid:           " + time + "\n" +
                    "våning:    " + level + "\n" +
                    "kod:       " + scannedCode + "\n";
        } else {
            return "titel:          " + title + "\n" +
                    "datum:  " + date + "\n" +
                    "tid:           " + time + "\n" +
                    "våning:    " + level + "\n" +
                    "kod:         " + scannedCode + "\n";
        }
    }
}