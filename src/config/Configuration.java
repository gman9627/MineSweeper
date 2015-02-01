package config;

import grid.Grid;

public class Configuration 
{
	public static Grid getBeginner() {
		return new Grid(8, 8, 10);
	}
	
	public static Grid getIntermediate() {
		return new Grid(16, 16, 40);
	}
	
	public static Grid getExpert() {
		return new Grid(16, 31, 99);
	}
}
