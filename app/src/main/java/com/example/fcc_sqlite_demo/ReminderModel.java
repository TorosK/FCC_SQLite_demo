package com.example.fcc_sqlite_demo;

public class ReminderModel {
    private int id;
    private String title;
    private int date;
    private boolean isImportant;

    public ReminderModel(int id, String title, int date, boolean isImportant) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.isImportant = isImportant;
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

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public void setImportant(boolean important) {
        isImportant = important;
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
            return date + "  |  " + "VIKTIGT  |  " + title;
        } else {

        }
            return date + "  |  " + "                  |  " + title;
    }

}
