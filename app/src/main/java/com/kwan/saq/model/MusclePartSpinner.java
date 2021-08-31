package com.kwan.saq.model;

public class MusclePartSpinner {
    private int id;
    private String name;

    public MusclePartSpinner(int id, String name) {
        this.id = id;
        this.name = name;
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

    //to display object as a string in spinner
    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MusclePartSpinner){
            MusclePartSpinner c = (MusclePartSpinner )obj;
            if(c.getName().equals(name) && c.getId() == id ) return true;
        }

        return false;
    }

}
