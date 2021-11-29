package com.example.questmanager;

import android.text.Spanned;

public class HelpItem {
    // every item on the help screen will have a question/header and the answer/explanatory text
    private final String questionText;
    private final Spanned answerText;
    // answerText is a different class to allow for html formatting in the strings.xml file

    public HelpItem(String qText, Spanned aText) { // constructor for the class
        this.questionText = qText;
        this.answerText = aText;
    }

    public String getQuestion() {
        return this.questionText;
    } // allows the qText to be called
    public Spanned getAnswer() {
        return this.answerText;
    } // allows the aText to be called
}
