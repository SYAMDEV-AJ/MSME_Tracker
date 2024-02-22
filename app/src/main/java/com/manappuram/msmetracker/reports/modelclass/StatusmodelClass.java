package com.manappuram.msmetracker.reports.modelclass;

public class StatusmodelClass {

    private String menuname;

    public StatusmodelClass(String title) {

        this.menuname = title;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }
}
