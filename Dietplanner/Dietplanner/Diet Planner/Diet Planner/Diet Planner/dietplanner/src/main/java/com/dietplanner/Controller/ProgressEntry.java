package com.dietplanner.Controller;

import java.time.LocalDate;

public class ProgressEntry {
    private LocalDate date;
    private double weight;
    private int caloriesConsumed;
    private int exerciseDuration;

    public ProgressEntry(LocalDate date, double weight, int caloriesConsumed, int exerciseDuration) {
        this.date = date;
        this.weight = weight;
        this.caloriesConsumed = caloriesConsumed;
        this.exerciseDuration = exerciseDuration;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getCaloriesConsumed() {
        return caloriesConsumed;
    }

    public void setCaloriesConsumed(int caloriesConsumed) {
        this.caloriesConsumed = caloriesConsumed;
    }

    public int getExerciseDuration() {
        return exerciseDuration;
    }

    public void setExerciseDuration(int exerciseDuration) {
        this.exerciseDuration = exerciseDuration;
    }
}
