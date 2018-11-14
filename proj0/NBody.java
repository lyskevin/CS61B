/** Simulates a given universe along with its planets and stars */

public class NBody {

    // Returns the radius of the universe in a given file   
    public static double readRadius(String fileName) {
        In in = new In(fileName);
        int numPlanets = in.readInt();
        double universeRadius = in.readDouble();
        return universeRadius;
    } // End readRadius

    // Returns an array of planets from the given data file
    public static Planet[] readPlanets(String fileName) {
        In in = new In(fileName);
        int numPlanets = in.readInt();
        double universeRadius = in.readDouble();
        Planet[] planets = new Planet[numPlanets];
        for (int i = 0; i < planets.length; i++) {
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String imgFileName = in.readString();
            planets[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
        }
        return planets;     
    } // End readPlanets

    public static void main(String[] args) {

        // Read details from input file
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        Planet[] planets = readPlanets(filename);

        // Simulate and draw universe
        double universeRadius = readRadius(filename);
        StdDraw.setScale(-universeRadius, universeRadius);
        StdDraw.enableDoubleBuffering();

        // Update animation every dt (given) seconds
        for (int time = 0; time <= T; time += dt) {
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];

            // Calculate forces acting on each planet
            for (int i = 0; i < planets.length; i++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }

            // Update each planet's position, velocity and acceleration
            for (int j = 0; j < planets.length; j++) {
                planets[j].update(dt, xForces[j], yForces[j]);
            }

            // Draw background
            StdDraw.picture(0, 0, "images/starfield.jpg");

            // Draw planets
            for (Planet p : planets) {
                p.draw();
            }

            // Draw given universe and pause the animation every ten milliseconds
            StdDraw.show();
            StdDraw.pause(10);
        }

        // Print final state of universe
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", universeRadius);
        for (int i = 0; i < planets.length; i++) {
        StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                      planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                      planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
        }    
        
    } // End main

} // End NBody class