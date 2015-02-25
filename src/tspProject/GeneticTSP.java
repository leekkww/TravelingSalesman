package tspProject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GeneticTSP {
	
	private static ArrayList<Vector> list = new ArrayList<Vector>();
	private static ArrayList<ArrayList<Vector>> paths = new ArrayList<ArrayList<Vector>>();
	
	public static void main(String[] args) {
		int initPaths = 1000;
		int iterations = 10000;
		for(int i = 0; i < initPaths; i++) {
			addRandomPath(list.get(0));
		}
		for(int i = 0; i < iterations; i++) {
			mutation();
			cull();
		}
		ArrayList<Vector> darwin = largestPercentage();
		for(Vector v: darwin) {
			System.out.println(darwin);
		}
	}
	
	public static void addRandomPath(Vector start) {
		ArrayList<Vector> echo = list;
		ArrayList<Vector> path = new ArrayList<Vector>();
		echo.remove(start);
		Collections.shuffle(echo);
		echo.add(0, start);
		echo.add(list.size(), start);
		paths.add(echo);
	}
	
	public static void mutation() {
		Random random = new Random();
		int rand = random.nextInt(paths.size());
		ArrayList<Vector> mutated= paths.get(rand);
		int mutPoint1 = 0;
		int mutPoint2 = 0;
		while(mutPoint1 == mutPoint2) {
			mutPoint1 = random.nextInt(mutated.size());
			mutPoint2 = random.nextInt(mutated.size());
		}
		Vector mut1 = mutated.get(mutPoint1);
		Vector mut2 = mutated.get(mutPoint2);
		mutated.set(mutPoint1, mut2);
		mutated.set(mutPoint2, mut1);
		paths.add(mutated);
	}
	
	public static void cull() {
		ArrayList<Vector> weak = paths.get(0);
		double length = length(weak);
		for(ArrayList<Vector> path: paths) {
			if(length < length(path)) {
				weak = path;
				length = length(path);
			}
		}
		paths.remove(weak);
	}
	
	public static double length(ArrayList<Vector> path) {
		path.add(path.get(0));
		double length = 0;
		for(int i = 1; i < path.size(); i++) {
			length += path.get(i).subtract(path.get(i - 1)).magnitude();
		}
		return length;
	}
	
	public static ArrayList<Vector> largestPercentage() {
		ArrayList<Vector> frequent = paths.get(0);
		int frequency = Collections.frequency(paths, frequent);
		for(int i = 0; i < paths.size(); i++) {
			if(frequency < Collections.frequency(paths, paths.get(i))) {
				frequent = paths.get(i);
				frequency = Collections.frequency(paths, paths.get(i));
			}
		}
		double percentage = frequency*1./paths.size();
		System.out.println("percentage: " + percentage);
		return frequent;
	}
}
