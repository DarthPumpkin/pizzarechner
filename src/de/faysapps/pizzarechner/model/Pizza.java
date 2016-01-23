package de.faysapps.pizzarechner.model;

import java.io.Serializable;

public class Pizza implements Serializable {
    /**
     * [cm^2] area of a 30cm pizza required to feed an average adult.
     */
    public static final double STANDARD_SIZE = 706;

    private Shape shape;
    private double prize;
    private static final long serialVersionUID = 1L;

    public Pizza(Shape shape, double prize) {
        if (shape == null) {
            throw new NullPointerException("shape must not be null");
        }
        if (prize <= 0) {
            throw new IllegalArgumentException("prize must be > 0");
        }
        this.shape = shape;
        this.prize = prize;
    }

    public double getPrizePerSquareCm() {
        return prize / shape.getArea();
    }

    public String prizeString() {
        return prize + "\u20AC";
    }

    public String toString() {
        return shape.dimensionString() + ", " + prizeString();
    }

    public double getPrize() {
        return prize;
    }

    public Shape getShape() {
        return shape;
    }
}
