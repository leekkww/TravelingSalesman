package tspProject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ConvexHull {
	private static ArrayList<Vector> points = new ArrayList<Vector>();
	private static ArrayList<Vector> hull = new ArrayList<Vector>();
	
	public static void main(String[] args) {
		addPoints();
		System.out.println("Beginning...");
		Vector rightMostPoint = points.get(0);
		for(Vector v: points) {
			if(v.getX() > rightMostPoint.getX()) {
				rightMostPoint = v;
			}
			else if(v.getX() == rightMostPoint.getX() && v.getY() < rightMostPoint.getY()) {
				rightMostPoint = v;
			}
		}
		hull.add(0, rightMostPoint);
		
		Vector frontier = rightMostPoint;
		Vector candidate = Vector.nullVector();
		while(!candidate.equals(rightMostPoint)) {
			for(Vector v: points) {
				if(angle(frontier, v) < angle(frontier, candidate)) {
					candidate = v;
				}
				else if(angle(frontier, v) == angle(frontier, candidate) && Vector.distance(frontier, v) < Vector.distance(frontier, candidate)) {
					candidate = v;
				}
			}
			hull.add(candidate);
			frontier = candidate;
			points.remove(candidate);
		}
		for(Vector v: hull) {
			System.out.println(v);
		}
	}
	
	public static double angle(Vector origin, Vector v) {
		Vector cartesian = v.subtract(origin);
		if(cartesian.magnitude() == 0) {
			return 2*Math.PI;
		}
		else if(cartesian.getY() > 0) {
			return Vector.angleBetween(cartesian, new Vector(1, 0, 0));
		}
		else if(cartesian.getY() <= 0) {
			return 2*Math.PI - Vector.angleBetween(cartesian, new Vector(1, 0, 0));
		}
		else return 2*Math.PI;
	}
	
	public static void addPoints() {
		Scanner scan = new Scanner(System.in);
		System.out.println("How many points would you like to add?");
		int numberOfPoints = scan.nextInt();
		System.out.println("Enter points in format 'xcoord ycoord zcoord'.");
		for(int i = 0; i < numberOfPoints; i++) {
			double xCoord = scan.nextDouble();
			double yCoord = scan.nextDouble();
			double zCoord = scan.nextDouble();
			points.add(new Vector(xCoord, yCoord, zCoord));
		}
	}
}
