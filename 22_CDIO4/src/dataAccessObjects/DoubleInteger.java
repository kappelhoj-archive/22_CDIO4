package dataAccessObjects;

public class DoubleInteger {
	int x;
	int y;

	int getX(){return x;}
	int getY(){return y;}

	public DoubleInteger(int x, int y){this.x = x; this.y = y;}

	public String toString(){return x+"-"+y;}
}