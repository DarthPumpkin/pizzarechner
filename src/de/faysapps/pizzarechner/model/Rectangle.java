package de.faysapps.pizzarechner.model;

import java.io.Serializable;

class Rectangle implements Shape, Serializable {

    private final double width;
    private final double height;

    public Rectangle(double width, double height) {
        if (Math.min(width, height) <= 0) {
            throw new IllegalArgumentException("dimensions must be positive");
        }
        this.width = width;
        this.height = height;
    }
    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public String dimensionString() {
        return width + "cm x " + height + "cm";
    }

    @Override
    public Geometry geometry() {
        return Geometry.RECTANGLE;
    }
}
