package com.example.fcc_sqlite_demo;

public class ReminderModel {
    private int id;
    private String title;
    private int level;
    private boolean isImportant;
    private String scannedCode;

    public ReminderModel(int id, String title, int level, boolean isImportant, String scannedCode) {
        this.id = id;
        this.title = title;
        this.level = level;
        this.isImportant = isImportant;
        this.scannedCode = scannedCode;
    }

    public ReminderModel() {
    }

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int date) {
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

    @Override
    public String toString() {
        if (isImportant) {
            return "våning: " + level + "  |  " + "VIKTIGT  |  " + "titel: " + title + "  |  " + "kod: " + scannedCode;
        } else {

        }
            return "våning: " + level + "  |  " + "                 |  " + "titel: " + title + "  |  " + "kod: " + scannedCode;
    }

}
