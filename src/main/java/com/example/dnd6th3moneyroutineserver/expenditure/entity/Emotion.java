package com.example.dnd6th3moneyroutineserver.expenditure.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Emotion {
    GOOD, SOSO, BAD;

    @JsonCreator
    public static Emotion from(String s) {
        return Emotion.valueOf(s.toUpperCase());
    }
}
