package com.example.triviaquiz;

import java.util.ArrayList;

public class Model {

    ArrayList<results> results;

    public ArrayList<Model.results> getResults() {
        return results;
    }

    public void setResults(ArrayList<Model.results> results) {
        this.results = results;
    }

    public class results{
        String question;
        String correct_answer;
        ArrayList<String> incorrect_answers;

        public ArrayList<String> getIncorrect_answers() {
            return incorrect_answers;
        }

        public void setIncorrect_answers(ArrayList<String> incorrect_answers) {
            this.incorrect_answers = incorrect_answers;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getCorrect_answer() {
            return correct_answer;
        }

        public void setCorrect_answer(String correct_answer) {
            this.correct_answer = correct_answer;
        }
    }

}
