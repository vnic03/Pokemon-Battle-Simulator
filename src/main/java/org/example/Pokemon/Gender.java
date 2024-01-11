package org.example.Pokemon;

public enum Gender {
    MALE('♂', "blue"),
    FEMALE('♀', "red"),
    NONE('-', "black");

    private final char symbol;
    private final String color;

    Gender(char symbol,String color) {
        this.symbol = symbol;
        this.color = color;
    }
    public String getSymbol() {
        return String.valueOf(symbol);
    }

    public String getStyledSymbol() {
        return "-fx-text-fill: " + color + ";";
    }
}
