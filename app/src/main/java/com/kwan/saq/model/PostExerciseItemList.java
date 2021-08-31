package com.kwan.saq.model;

import java.util.List;

public class PostExerciseItemList {
    private List<PostExerciseItem> postExerciseItemList;

    public PostExerciseItemList(List<PostExerciseItem> postExerciseItemList) {
        this.postExerciseItemList = postExerciseItemList;
    }

    public List<PostExerciseItem> getPostExerciseItemList() {
        return postExerciseItemList;
    }

    public void setPostExerciseItemList(List<PostExerciseItem> postExerciseItemList) {
        this.postExerciseItemList = postExerciseItemList;
    }
}
