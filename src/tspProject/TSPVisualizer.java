package tspProject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * @author Peter Dong
 * Edited by Joanne Lee
 * This class visualizes the Particles in a ParticleContainer in a two-dimensional projection 
 */
public class TSPVisualizer extends JPanel implements ActionListener {

    /**
     * I add this because otherwise Eclipse gets mad at me.  It is not used.
     */
    private static final long serialVersionUID = 1L;

    private ArrayList<Vector> grid;
    private double size;

    /**
     * @param grid The ParticleContainer that is being visualized
     * @param timeIncrement The amount of time between each update, in real physics time
     */
    public TSPVisualizer(ArrayList<Vector> grid, double size, double timeIncrement) {
        this.grid = grid;
        this.size = size;

        setBackground(Color.black);

        final int width = 600;
        final int height = 600;
        this.setSize(width, height);

        int incrementInMils = (int)(timeIncrement * 1000);
        Timer time = new Timer(incrementInMils, this);
        time.start();
    }

    /**
     * The radius, in pixels, of each location
     */
    private static final int DOT_SIZE = 4;

    @Override
    public void paint(Graphics arg0) {
        super.paint(arg0);
        Vector previousCoord = null;
        for (Vector element : grid) {
            int xCoord = getXCoord(element.getX());
            int yCoord = getYCoord(element.getY());

            arg0.setColor(Color.blue);
            arg0.fillOval(xCoord, yCoord, DOT_SIZE, DOT_SIZE);

            if(previousCoord != null) {
                arg0.setColor(Color.red);
                arg0.drawLine(xCoord + DOT_SIZE / 2,yCoord + DOT_SIZE / 2,(int)previousCoord.getX() + DOT_SIZE / 2,(int)(previousCoord.getY()) + DOT_SIZE / 2);
            }

            previousCoord = new Vector(xCoord,yCoord);
        }

        arg0.setColor(Color.red);
        arg0.drawLine(getXCoord(grid.get(0).getX()) + DOT_SIZE / 2,getYCoord(grid.get(0).getY()) + DOT_SIZE / 2, (int)(previousCoord.getX()) + DOT_SIZE / 2,(int)(previousCoord.getY()) + DOT_SIZE / 2);
    }

    private int getXCoord(double x) {
        return (int)Math.round(x / size * (int)getSize().getWidth());
    }

    private int getYCoord(double y) {
        return (int)getSize().getHeight() - (int)Math.round(y / size * (int)getSize().getHeight());
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        repaint();
    }

}
