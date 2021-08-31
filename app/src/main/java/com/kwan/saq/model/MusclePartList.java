package com.kwan.saq.model;

import java.util.List;

public class MusclePartList {
    private List<MusclePart> musclePartList;

    public MusclePartList(List<MusclePart> musclePartList) {
        this.musclePartList = musclePartList;
    }

    public List<MusclePart> getMusclePartList() {
        return musclePartList;
    }

    public void setMusclePartList(List<MusclePart> musclePartList) {
        this.musclePartList = musclePartList;
    }
}
