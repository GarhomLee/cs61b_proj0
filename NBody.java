public class NBody{
    /** this is the class where we run the simulation */
    public static int N; //number of planets
    public static double R; //radius of the universe
    public static Planet[] allPlanets; //declare the planet array
    public static double readRadius(String filePath){
        /** get info about the radius of the universe from a text file */
        In in = new In(filePath);
        N = in.readInt();
        R = in.readDouble();
        return R;
    }
    public static Planet[] readPlanets(String filePath){
        /** get an array of planets from a text file */
        In in = new In(filePath);
        N = in.readInt();
        R = in.readDouble();
        allPlanets = new Planet[N]; //initialize the planet array, but each element points to null
        for(int i = 0; i < N; i++) {
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String imgFileName = in.readString();
            allPlanets[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, "images/"+imgFileName); //initialize each planet
        }
        return allPlanets;
    }
    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double universeRadius = NBody.readRadius(filename); //read in the radius
        Planet[] planets = NBody.readPlanets(filename); //read in the planets array

        StdDraw.setScale(-universeRadius, universeRadius); // set the scale so that it matches the radius of the universe
        String background = "images/starfield.jpg";

        StdDraw.enableDoubleBuffering();

        for (double time = 0; time < T; time += dt) {
            double[] xForce = new double[planets.length];
            double[] yForce = new double[planets.length];
            for (int i = 0; i < planets.length; i++) { //loop for each planet
                xForce[i] = planets[i].calcNetForceExertedByX(planets);
                yForce[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for (int i = 0; i < planets.length; i++) { //loop for each planet
                planets[i].update(dt, xForce[i], yForce[i]);
            }

            StdDraw.picture(0, 0, background); // draw the image starfield.jpg as the background
            for (Planet p : planets) { //draw all the planets
                p.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
        }

        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", universeRadius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);

        }
    }
}
