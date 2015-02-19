package tspProject;

public class Vector {
	private double x;
	private double y;
	private double z;
	
	public Vector(double initialX, double initialY, double initialZ) {
		x = initialX;
		y = initialY;
		z = initialZ;
	}
	
	public Vector add(Vector v) {
		double newX = getX() + v.getX();
		double newY = getY() + v.getY();
		double newZ = getZ() + v.getZ();
		return new Vector(newX, newY, newZ);
	}
	
	public static Vector add(Vector v1, Vector v2) {
		double newX = v1.getX() + v2.getX();
		double newY = v1.getY() + v2.getY();
		double newZ = v1.getZ() + v2.getZ();
		return new Vector(newX, newY, newZ);
	}
	
	public Vector subtract(Vector v) {
		double newX = getX() - v.getX();
		double newY = getY() - v.getY();
		double newZ = getZ() - v.getZ();
		return new Vector(newX, newY, newZ);
	}
	
	public static Vector subtract(Vector v1, Vector v2) {
		double newX = v1.getX() - v2.getX();
		double newY = v1.getY() - v2.getY();
		double newZ = v1.getZ() - v2.getZ();
		return new Vector(newX, newY, newZ);
	}
	
	public Vector scale(double scalar) {
		double newX = scalar*getX();
		double newY = scalar*getY();
		double newZ = scalar*getZ();
		return new Vector(newX, newY, newZ);
	}
	
	public double dotProduct(Vector v) {
		return getX()*v.getX() + getY()*v.getY() + getZ()*v.getZ();
	}
	
	public static double angleBetween(Vector v1, Vector v2) {
		return Math.acos(Vector.dotProduct(v1, v2)/(v1.magnitude()*v2.magnitude()));
	}
	
	public static double dotProduct(Vector v1, Vector v2) {
		return v1.getX()*v2.getX() + v1.getY()*v2.getY() + v1.getZ()*v2.getZ();
	}
	
	public double magnitude() {
		return Math.sqrt(getX()*getX() + getY()*getY() + getZ()*getZ());
	}
	
	public Vector unitVector() {
		Vector v = new Vector(x, y, z);
		return v.scale(1/v.magnitude());
	}
	
	public static double crossProductMagnitude(Vector v1, Vector v2) {
		double angle = Math.acos(Vector.dotProduct(v1, v2)/(v1.magnitude()*v2.magnitude()));
		return v1.magnitude()*v2.magnitude()*Math.sin(angle);
	}
	
	public static double distance(Vector v1, Vector v2) {
		return Math.sqrt(Math.pow(v1.getX() - v2.getX(), 2) + Math.pow(v1.getY() - v2.getY(), 2) + Math.pow(v1.getZ() - v2.getZ(), 2));
	}
	
	public static Vector nullVector() {
		return new Vector(0, 0, 0);
	}
	
	public boolean equals(Vector v) {
		if(v.getX() == x && v.getY() == y && v.getZ() == z) {
			return true;
		}
		else return false;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getZ() {
		return z;
	}
	
	public String toString() {
		return x + "\t" + y + "\t" + z;
	}
}
