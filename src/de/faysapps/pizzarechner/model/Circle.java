package de.faysapps.pizzarechner.model;

import java.io.Serializable;

class Circle implements Shape, Serializable {

    private final double diameter;

    public Circle(double diameter) {
        this.diameter = diameter;
    }

    @Override
    public double getArea() {
        return 0.25 * Math.PI * diameter * diameter;
    }

    @Override
    public String dimensionString() {
        return diameter + "cm";
    }

    @Override
    public Geometry geometry() {
        return Geometry.CIRCLE;
    }
}
