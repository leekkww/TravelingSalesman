package tspProject;

public class Vector {
	private double x;
	private double y;
	
	public Vector(double x, double y) {
		this.x = x;
        this.y = y;
	}
	
	public Vector add(Vector v) {
		return Vector.add(this,v);
	}
	
	public static Vector add(Vector v1, Vector v2) {
		double newX = v1.getX() + v2.getX();
		double newY = v1.getY() + v2.getY();
		return new Vector(newX, newY);
	}
	
	public Vector subtract(Vector v) {
		return Vector.subtract(this,v);
	}
	
	public static Vector subtract(Vector v1, Vector v2) {
		double newX = v1.getX() - v2.getX();
		double newY = v1.getY() - v2.getY();
		return new Vector(newX, newY);
	}

    public Vector scale(double scalar) {
        return Vector.scale(this,scalar);
    }
	
	public static Vector scale(Vector v, double scalar) {
		double newX = scalar * v.getX();
		double newY = scalar * v.getY();
		return new Vector(newX, newY);
	}
	
	public static double angleBetween(Vector v1, Vector v2) {
		return Math.acos(Vector.dotProduct(v1, v2)/(v1.magnitude()*v2.magnitude()));
	}

    public double dotProduct(Vector v) {
        return Vector.dotProduct(this,v);
    }
	
	public static double dotProduct(Vector v1, Vector v2) {
		return v1.getX()*v2.getX() + v1.getY()*v2.getY();
	}
	
	public double magnitude() {
		return Vector.distance(this,new Vector(0,0));
	}
	
	public Vector unitVector() {
		Vector v = new Vector(x, y);
		return v.scale(1/v.magnitude());
	}
	
	public static double distance(Vector v1, Vector v2) {
		return Math.sqrt(Math.pow(v1.getX() - v2.getX(), 2) + Math.pow(v1.getY() - v2.getY(), 2));
	}
	
	public static Vector nullVector() {
		return new Vector(0, 0);
	}
	
	public boolean equals(Vector v) {
		return Vector.equals(this,v);
	}

    public static boolean equals(Vector v1, Vector v2) {
        return (v1.getX() == v2.getX() && v1.getY() == v2.getY());
    }
	
	public boolean equals(Object o) {
		return (o instanceof Vector) && (equals((Vector)o));
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public String toString() {
		return x + "\t" + y;
	}
}
