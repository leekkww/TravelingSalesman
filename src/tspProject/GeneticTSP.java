package tspProject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GeneticTSP {
	
	private static ArrayList<Vector> list = new ArrayList<Vector>();
	private static ArrayList<ArrayList<Vector>> paths = new ArrayList<ArrayList<Vector>>();
	
	public static void main(String[] args) {
		list.add(new Vector(0, 0));
		list.add(new Vector(1, 0));
		list.add(new Vector(2, 0));
		list.add(new Vector(0, 1));
		list.add(new Vector(0, 2));
		list.add(new Vector(0, 8));
		list.add(new Vector(4, 4));
		list.add(new Vector(1, 3));
		
		int initPaths = 1000;
		int iterations = 10000;
		for(int i = 0; i < initPaths; i++) {
			addRandomPath();
		}
		for(int i = 0; i < iterations; i++) {
			mutation();
			cull();
		}
		ArrayList<Vector> darwin = new ArrayList<Vector>(largestPercentage());
		for(Vector v: darwin) {
			System.out.println(v);
		}
	}
	
	public static void addRandomPath() { //constructs random path with starting point at first vector in list
		ArrayList<Vector> echo = new ArrayList<Vector>(list);
		Vector start = echo.get(0);
		echo.remove(0);
		Collections.shuffle(echo);
		echo.add(0, start);
		echo.add(echo.size(), start);
		paths.add(echo);
	}
	
	public static void mutation() {
		Random random = new Random();
		int rand = random.nextInt(paths.size());
		ArrayList<Vector> mutated= new ArrayList<Vector>(paths.get(rand));
		int mutPoint1 = 0;
		int mutPoint2 = 0;
		while(mutPoint1 == mutPoint2) {
			mutPoint1 = random.nextInt(mutated.size() - 2) + 1;
			mutPoint2 = random.nextInt(mutated.size() - 2) + 1;
		}
		Vector mut1 = mutated.get(mutPoint1);
		Vector mut2 = mutated.get(mutPoint2);
		mutated.set(mutPoint1, mut2);
		mutated.set(mutPoint2, mut1);
		paths.add(mutated);
	}
	
	public static void cull() {
		ArrayList<Vector> weak = new ArrayList<Vector>(paths.get(0));
		double length = length(weak);
		//int index = 0;
		for(ArrayList<Vector> path: paths) {
			if(length < length(path)) {
				weak = path;
				length = length(path);
				//index++;
			}
		}
		paths.remove(weak);
	}
	
	public static double length(ArrayList<Vector> path) {
		ArrayList<Vector> echo = new ArrayList<Vector>(path);
		echo.add(echo.get(0));
		double length = 0;
		for(int i = 1; i < echo.size(); i++) {
			length += echo.get(i).subtract(echo.get(i - 1)).magnitude();
		}
		return length;
	}
	
	public static ArrayList<Vector> largestPercentage() {
		ArrayList<Vector> frequent = new ArrayList<Vector>(paths.get(0));
		int frequency = Collections.frequency(paths, frequent);
		for(int i = 0; i < paths.size(); i++) {
			if(frequency < Collections.frequency(paths, paths.get(i))) {
				frequent = new ArrayList<Vector>(paths.get(i));
				frequency = Collections.frequency(paths, paths.get(i));
			}
		}
		double percentage = frequency*100./paths.size();
		System.out.println("percentage: " + percentage);
		return frequent;
	}
}
