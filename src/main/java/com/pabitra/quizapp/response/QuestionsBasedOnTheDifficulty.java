package com.pabitra.quizapp.response;

import lombok.Data;

@Data
public class QuestionsBasedOnTheDifficulty {
    private Long id;
    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String rightAnswer;
    private String category;

    public QuestionsBasedOnTheDifficulty(Long id, String questionTitle, String option1,
                                         String option2, String option3, String option4,
                                         String rightAnswer, String category) {
        this.id = id;
        this.questionTitle = questionTitle;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.rightAnswer = rightAnswer;
        this.category = category;
    }
}
