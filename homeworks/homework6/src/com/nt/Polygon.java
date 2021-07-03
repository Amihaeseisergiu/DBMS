package com.nt;

import java.io.Serializable;

public class Polygon implements Serializable {

	private static final long serialVersionUID = 1L;
	int nrSides;
	int stroke;
	int size;
	String color;
	
	public Polygon(int nrSides, int stroke, int size, String color)
	{
		this.nrSides = nrSides;
		this.stroke = stroke;
		this.size = size;
		this.color = color;
	}
	
	@Override
	public String toString()
	{
		return nrSides + " " +  stroke + " " + size + " " + color;
	}
}
