package com.example.triviaquiz;

public class QuizScore {
    public String name,categoryName,level;
    public Integer score;


    public QuizScore(){}


    public QuizScore(String name, String categoryName, String level, Integer score) {

        this.name = name;
        this.categoryName = categoryName;
        this.level = level;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
