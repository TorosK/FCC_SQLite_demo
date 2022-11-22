package com.example.fcc_sqlite_demo;

public class CustomerModel {
    private int id;
    private String name;
    private int birthYear;
    private boolean isActive;

    public CustomerModel(int id, String name, int birthYear, boolean isActive) {
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
        this.isActive = isActive;
    }

    public CustomerModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "CustomerModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthYear=" + birthYear +
                ", isActive=" + isActive +
                '}';
    }
}
