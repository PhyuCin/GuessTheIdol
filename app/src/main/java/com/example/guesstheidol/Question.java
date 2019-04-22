package com.example.guesstheidol;

public class Question {
    final int number;
    final String[] choices;

    public Question(int number, String[] choices){
        this.number = number;
        this.choices = choices;
    }
}
