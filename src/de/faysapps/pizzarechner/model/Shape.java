package de.faysapps.pizzarechner.model;

public interface Shape {

    enum Geometry {RECTANGLE, CIRCLE}

    double getArea();

    String dimensionString();

    Geometry geometry();
}
