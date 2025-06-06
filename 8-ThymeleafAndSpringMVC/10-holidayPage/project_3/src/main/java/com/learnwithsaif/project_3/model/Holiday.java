package com.learnwithsaif.project_3.model;


public class Holiday {
    private final String day;
    private final String reason;
    private final Type type;

    // Enum for Holiday Type
    public enum Type {
        FESTIVAL, FEDERAL
    }

    // Constructor
    public Holiday(String day, String reason, Type type) {
        this.day = day;
        this.reason = reason;
        this.type = type;
    }

    // Getters
    public String getDay() {
        return day;
    }

    public String getReason() {
        return reason;
    }

    public Type getType() {
        return type;
    }
}