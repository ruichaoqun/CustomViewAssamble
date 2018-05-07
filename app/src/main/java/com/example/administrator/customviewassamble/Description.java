package com.example.administrator.customviewassamble;

/**
 * Created by Administrator on 2018/5/5.
 */

public class Description {
    private String name;
    private String description;

    public Description(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
