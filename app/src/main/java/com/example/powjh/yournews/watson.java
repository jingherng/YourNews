package com.example.powjh.yournews;

import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class watson {
    //Gradle: compile 'com.ibm.watson.developer_cloud:java-sdk:5.1.1'
    private String input;
    private NaturalLanguageUnderstanding service;

    public watson() {
        this.service = new NaturalLanguageUnderstanding(
                "2018-03-16",
                "14cfe0c5-e36f-49e8-924d-040e1ceb1fbc",
                "yOjlKNwRoAgu"
        );
    }

    public String getQueryKeyword(String input){

        String result = "";

        KeywordsOptions keywordsOptions = new KeywordsOptions.Builder()
                .emotion(false)
                .sentiment(false)
                .limit(5)
                .build();

        Features features = new Features.Builder()
                .keywords(keywordsOptions)
                .build();

        AnalyzeOptions parameters = new AnalyzeOptions.Builder()
                .text(input)
                .features(features)
                .build();

        AnalysisResults response = service
                .analyze(parameters)
                .execute();

        List<KeywordsResult> keywordResults = response.getKeywords();
        for (KeywordsResult keyword : keywordResults) {
            result += keyword.getText() +" OR ";
        }
        result = result.substring(0,Math.min(result.length()-4, result.length()));
        return result;
    }
}