package org.example.Pokemon;

public enum Gender {
    MALE('♂', "\u001B[34m"),
    FEMALE('♀', "\u001B[31m"),
    NONE('-', "\u001B[0m");

    private final char symbol;
    private final String color;

    Gender(char symbol,String color) {
        this.symbol = symbol;
        this.color = color;
    }

    public String getSymbol() {
        return color + symbol + "\u001B[0m";
    }
}
