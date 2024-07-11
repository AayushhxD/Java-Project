package com.dietplanner.Controller;


public class FoodItem {
    private String foodName;
    private int calories;
    private int totalFat;

    public FoodItem(String foodName, int calories, int totalFat) {
        this.foodName = foodName;
        this.calories = calories;
        this.totalFat = totalFat;
    }

    public FoodItem(String string, int i, int j, int k) {
    }

    public String getFoodName() {
        return foodName;
    }

    public int getCalories() {
        return calories;
    }

    public int getTotalFat() {
        return totalFat;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setTotalFat(int totalFat) {
        this.totalFat = totalFat;
    }
}

