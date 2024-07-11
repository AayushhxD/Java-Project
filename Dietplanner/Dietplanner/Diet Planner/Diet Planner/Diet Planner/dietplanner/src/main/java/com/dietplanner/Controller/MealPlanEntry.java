package com.dietplanner.Controller;

public class MealPlanEntry {
    private String day;
    private String breakfast;
    private String lunch;
    private String dinner;

    public MealPlanEntry(String day, String breakfast, String lunch, String dinner) {
        this.day = day;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
    }

    public String getDay() {
        return day;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public String getLunch() {
        return lunch;
    }

    public String getDinner() {
        return dinner;
    }
}
