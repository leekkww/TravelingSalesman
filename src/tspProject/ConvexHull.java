package tspProject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ConvexHull {

    private static final double gridSize = 100;
	private static ArrayList<Vector> points = new ArrayList<Vector>();
	private static ArrayList<Vector> hull = new ArrayList<Vector>();
	
	public static void main(String[] args) {
		generatePoints(30);
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
		
		//constructs convex hull
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
		
		//adds points on the interior to the hull by checking for the closest point and working up. BUGGED
		while(!points.isEmpty()) {
			candidate = points.get(0);
			double minDistance = distance(candidate);
			for(Vector v: points) {
				if(distance(v) < minDistance) {
					candidate = v;
				}
			}
			hull.add(hull.indexOf(distanceVector(candidate)), candidate);
			points.remove(candidate);
		}

        //prints out results
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
			return Vector.angleBetween(cartesian, new Vector(1, 0));
		}
		else if(cartesian.getY() <= 0) {
			return 2*Math.PI - Vector.angleBetween(cartesian, new Vector(1, 0));
		}
		else return 2*Math.PI;
	}
	
	public static double distance(Vector v) {
		Vector least = hull.get(1);
		double distance = Math.abs((hull.get(1).getY() - hull.get(0).getY())*v.getX() - (hull.get(1).getX() - hull.get(0).getX())*v.getY() + hull.get(1).getX()*hull.get(0).getY() - hull.get(1).getY()*hull.get(0).getX())/Math.sqrt(Math.pow(hull.get(1).getY() - hull.get(0).getY(), 2) + Math.pow(hull.get(1).getX() - hull.get(0).getX(), 2));
		for(int i = 1; i < hull.size(); i++) {
			double d = Math.abs((hull.get(i).getY() - hull.get(i - 1).getY())*v.getX() - (hull.get(i).getX() - hull.get(i - 1).getX())*v.getY() + hull.get(i).getX()*hull.get(i - 1).getY() - hull.get(i).getY()*hull.get(i - 1).getX())/Math.sqrt(Math.pow(hull.get(i).getY() - hull.get(i - 1).getY(), 2) + Math.pow(hull.get(i).getX() - hull.get(i - 1).getX(), 2));
			if(d < distance) {
				least = hull.get(i);
				distance = d;
			}
		}
		return distance;
	}
	
	public static Vector distanceVector(Vector v) { //returns the vector on the hull that is the second of the two vectors whose distance from the vector is least
		Vector least = hull.get(1);
		double distance = Math.abs((hull.get(1).getY() - hull.get(0).getY())*v.getX() - (hull.get(1).getX() - hull.get(0).getX())*v.getY() + hull.get(1).getX()*hull.get(0).getY() - hull.get(1).getY()*hull.get(0).getX())/Math.sqrt(Math.pow(hull.get(1).getY() - hull.get(0).getY(), 2) + Math.pow(hull.get(1).getX() - hull.get(0).getX(), 2));
		for(int i = 1; i < hull.size(); i++) {
			double d = Math.abs((hull.get(i).getY() - hull.get(i - 1).getY())*v.getX() - (hull.get(i).getX() - hull.get(i - 1).getX())*v.getY() + hull.get(i).getX()*hull.get(i - 1).getY() - hull.get(i).getY()*hull.get(i - 1).getX())/Math.sqrt(Math.pow(hull.get(i).getY() - hull.get(i - 1).getY(), 2) + Math.pow(hull.get(i).getX() - hull.get(i - 1).getX(), 2));
			if(d < distance) {
				least = hull.get(i);
				distance = d;
			}
		}
		return least;
	}
	
	public static void addPoints() {
		Scanner scan = new Scanner(System.in);
		System.out.println("How many points would you like to add?");
		int numberOfPoints = scan.nextInt();
		System.out.println("Enter points in format 'xcoord ycoord zcoord'.");
		for(int i = 0; i < numberOfPoints; i++) {
			double xCoord = scan.nextDouble();
			double yCoord = scan.nextDouble();
			points.add(new Vector(xCoord, yCoord));
		}
	}

    public static void generatePoints(int numberOfPoints) {
        PointGenerator pg = new PointGenerator(new Vector(gridSize, gridSize));
        for(int i = 0; i < numberOfPoints; i++) {
            points.add(pg.getVector());
        }
    }
}
