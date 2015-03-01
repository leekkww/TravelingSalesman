package tspProject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class GeneticTSP {

    private static ArrayList<Vector> list = new ArrayList<Vector>(); // the numbered vertices with their locations
    private static ArrayList<ArrayList<Vector>> paths = new ArrayList<ArrayList<Vector>>();


    public static void main(String[] args) {
        // input positions of cities. must be positive-valued.

        list.add(new Vector(0, 0));
        list.add(new Vector(1, 0));
        list.add(new Vector(2, 0));
        list.add(new Vector(3, 0));
        list.add(new Vector(4, 0));

        int initPaths = 10;  // change magic numbers later?
        int iterations = 10;
        ArrayList<Vector> darwin = TSP(initPaths, iterations);

        for(Vector v: darwin) {
            System.out.println(v);
        }
        System.out.println("Length: " + length(darwin));

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
        paths.add(mutated); // do we want to keep the not mutated copy?
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
        // path.add(path.get(0)); last vertex is already in path
        double length = 0;
        for(int i = 1; i < path.size(); i++) {
            length += path.get(i).subtract(path.get(i - 1)).magnitude(); //finds distance
        }
        return length;

    }

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