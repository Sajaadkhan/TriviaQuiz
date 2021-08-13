package com.example.triviaquiz;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class Question  {

    @SerializedName("question")
    private String Question;

    @SerializedName("correct_answer")
    private String CorrectAnswer;

    @SerializedName("incorrect_answers")
    private ArrayList<String> IncorrectAnswers;

    private int correctAnswerIndex;
    private ArrayList<String> allAnswers;

    public String getCorrectAnswer()   {
        return CorrectAnswer;
    }

    public String getQuestion()    {
        return Question;
    }

    public ArrayList<String> getAnswers()   {
        if (allAnswers == null)    {
            int answerCount = IncorrectAnswers.size() + 1;
            Random random = new Random();
            /* Choose a random position for the correct answer to be at in the list */
            correctAnswerIndex = random.nextInt(answerCount);
            ArrayList<String> answersToDisplay = new ArrayList<>();
            answersToDisplay.addAll(IncorrectAnswers);
            answersToDisplay.add(correctAnswerIndex, CorrectAnswer);
            allAnswers = answersToDisplay;
        }
        return allAnswers;
    }

    public static ArrayList<Question> fromJson(JSONArray jsonArray) {
        JSONObject questionJson;
        ArrayList<Question> businesses = new ArrayList<Question>(jsonArray.length());
        // Process each result in json array, decode and convert to business object
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                questionJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }

        return businesses;
    }

}
