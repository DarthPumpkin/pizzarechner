package de.faysapps.pizzarechner;

import java.io.Serializable;

public class Pizza implements Serializable {
	/**
	 * default serialVersion UID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * [cm^2] area of a 30cm pizza required to feed an average adult.
	 */
	public static final double STANDARD_SIZE = 450;
	public double diameter, width, length, prize;
	public Pizza(double diameter, double prize) {
		this.diameter = diameter;
		this.prize = prize;
		this.width = 0;
		this.length = 0;
	}
	public Pizza(double width, double length, double prize) {
		this.width = width;
		this.length = length;
		this.prize = prize;
		this.diameter = 0;
	}
	public double getArea() {
		//the result of one of the brackets is 0, depending on which constructor was used.
		return (0.5 * Math.PI * diameter * diameter) + (width * length);
	}
	public double getPrizePerSquareCm() {
		return prize / getArea();
	}
	@Override
	public String toString() {
		if (diameter == 0) {
			return width + "cm x " + length + "cm, " + prize + "Û";
		} else {
			return "¿" + diameter + "cm, " + prize + "Û";
		}
		
	}
	public double getDiameter() {
		return diameter;
	}
	public double getWidth() {
		return width;
	}
	public double getLength() {
		return length;
	}
	public double getPrize() {
		return prize;
	}
};
