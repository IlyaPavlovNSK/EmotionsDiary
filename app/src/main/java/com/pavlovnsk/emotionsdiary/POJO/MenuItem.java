package com.pavlovnsk.emotionsdiary.POJO;

public class MenuItem {

    private String title;
    private int idIcon;

    public MenuItem(String title, int idIcon) {
        this.title = title;
        this.idIcon = idIcon;
    }

    public String getTitle() {
        return title;
    }

    public int getIdIcon() {
        return idIcon;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIdIcon(int idIcon) {
        this.idIcon = idIcon;
    }
}
