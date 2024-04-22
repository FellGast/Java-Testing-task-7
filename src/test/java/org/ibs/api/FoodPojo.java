package org.ibs.api;

public class FoodPojo {
    private String name;
    private String type;
    private boolean exotic;

    public FoodPojo() {
    }

    public FoodPojo(String name, String type, boolean exotic) {
        this.name = name;
        this.type = type;
        this.exotic = exotic;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isExotic() {
        return exotic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setExotic(boolean exotic) {
        this.exotic = exotic;
    }
}
