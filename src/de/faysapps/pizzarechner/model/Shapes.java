package de.faysapps.pizzarechner.model;

public final class Shapes {

    public static Shape rectangle(double width, double height) {
        return new Rectangle(width, height);
    }

    public static Shape circle(double diameter) {
        return new Circle(diameter);
    }
}
