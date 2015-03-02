package tspProject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JFrame;


public class GeneticTSP {
	
	private static ArrayList<Vector> list = new ArrayList<Vector>(); // the numbered vertices with their locations
	private static ArrayList<ArrayList<Vector>> paths = new ArrayList<ArrayList<Vector>>();

	
	public static void main(String[] args) {
		// input positions of cities. must be positive-valued.
        
		double gridSize = 2;
		int nVertices = 100;
		int radius = 1;
		PointGenerator pg = new PointGenerator(new Vector(gridSize,gridSize));
		for (int i = 0; i<nVertices; i++)
		{
			//list.add(new Vector(radius*Math.cos(2*i*Math.PI/nVertices)+1, radius*Math.sin(2*i*Math.PI/nVertices)+1 ));
			list.add(pg.getVector());
		}
		
		int initPaths = 100;  // change magic numbers later?
		int iterations = 100000;
		ArrayList<Vector> darwin = TSP(initPaths, iterations);
		
		for(Vector v: darwin) {
			System.out.println(v);
		}
		System.out.println("Length: " + length(darwin));
		darwin = removeCrossings(darwin);
		TSPVisualizer viz = new TSPVisualizer(darwin,gridSize,1);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(viz);
        frame.setSize(viz.getWidth(), viz.getHeight());
        frame.setVisible(true);
        

        System.out.println("New Length: " + length(darwin));
	}

	public static ArrayList<Vector> TSP(int initPaths, int iterations)
	{
		for(int i = 0; i < initPaths; i++) {
			addRandomPath(list.get(0));
		}
		
		for(int i = 0; i < iterations; i++) {
			mutation();
			cull();
			//no chromosome crossings
		}
		
		//ArrayList<Vector> darwin = largestPercentage();
		ArrayList<Vector> darwin = findBestFit();
		return darwin;
	}
	
	public static void addRandomPath(Vector start) {
		ArrayList<Vector> echo = new ArrayList<Vector>();
		echo.addAll(list);
		echo.remove(0);
		Collections.shuffle(echo);
		echo.add(0, start);
		echo.add(list.size(), start);
		paths.add(echo); 
	}
	
	public static ArrayList<Vector> removeCrossings(ArrayList<Vector> path) {
		boolean done = true;
		ArrayList<Vector> fixed = new ArrayList<Vector>(path);
		ArrayList<Vector> echo = new ArrayList<Vector>();
		ArrayList<Vector> previous = new ArrayList<Vector>();
		for(int i = 0; i < fixed.size(); i++) {
			echo.add(Vector.nullVector());
		}
		do {
			previous = new ArrayList<Vector>(fixed);
			for(int i = 0; i < fixed.size() - 1; i++) {
				Vector l1 = fixed.get(i);
				Vector h1 = fixed.get(i + 1);
				for(int j = 0; j < fixed.size() - 1; j++) {
					Vector l2 = fixed.get(j);
					Vector h2 = fixed.get(j + 1);
					if(l1.subtract(h1).magnitude() + l2.subtract(h2).magnitude() > l1.subtract(l2).magnitude() + h1.subtract(h2).magnitude()) {
						done = false;
						for(int k = 0; k <= i; k++) {
							echo.set(k, fixed.get(k));
						}
						int a = i + 1;
						for(int m = j; m >= i; m--) {
							echo.set(a, fixed.get(m));
							a++;
						}
						for(int n = j + 1; n < fixed.size(); n++) {
							echo.set(n, fixed.get(n));
						}
						fixed = new ArrayList<Vector>(echo);
						for(int z = 0; z < fixed.size(); z++) {
							echo.set(z, Vector.nullVector());
						}
					}
				}
			}
		} while(!previous.equals(fixed));
		return fixed;
	}
	
	public static void mutation() {
		Random random = new Random();
		int rand = random.nextInt(paths.size());
		ArrayList<Vector> mutated= new ArrayList<Vector>();
		mutated.addAll(paths.get(rand));
		int mutPoint1 = 0;
		int mutPoint2 = 0;
		while(mutPoint1 == mutPoint2 || mutPoint1 == 0 || mutPoint2 == 0 || mutPoint1 ==mutated.size()-1 || mutPoint2 == mutated.size()-1)
		//condition necessary to prevent the end points from getting switched out
		{
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
        double length = 0;
        for(int i = 1; i < path.size(); i++) {
            length += path.get(i).subtract(path.get(i - 1)).magnitude(); //finds distance
        }
        length += path.get(0).subtract(path.get(path.size() - 1)).magnitude();
        return length;
    }
	
	/**
     * finds the best path. this should work.
     */
    public static ArrayList<Vector> findBestFit() {
        ArrayList<Vector> fittest = paths.get(0);
        double length = length(fittest);
        for(ArrayList<Vector> path: paths) {
            if(length > length(path)) {
                fittest = path;
                length = length(path);
            }
        }
        return fittest;
    }
	
	
	public static ArrayList<Vector> findNextBestFit() {
        double maxLength = length(findBestFit());
        ArrayList<Vector> nextFittest = paths.get(0);
        int i = 0;

        //make sure that we don't pick the same path for nextFittest
        while(length(nextFittest) == maxLength) {
            i++;
            nextFittest = paths.get(i);
        }

        double length = length(nextFittest);

        for(ArrayList<Vector> path: paths) {
            if(maxLength < length(path) && length > length(path)) {
                nextFittest = path;
                length = length(path);
            }
        }
        return nextFittest;
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
		//checks for variation, likelihood of another solution
	}
	
}