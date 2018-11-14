/** Planet class */

public class Planet {

    // Declare G (constant of proportionality)
    private static final double G = 6.67e-11;

    // Instance variables
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    // Planet constructor (new planet)
    public Planet(double xP, double yP, double xV,
                  double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    } // End Planet constructor

    // Planet constructor (create a copy of a planet)
    public Planet(Planet p) {
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass = p.mass;
        this.imgFileName = p.imgFileName;
    } // End Planet constructor

    // Calculates the distance between two planets
    public double calcDistance(Planet p) {
        double xxDist = p.xxPos - this.xxPos;
        double yyDist = p.yyPos - this.yyPos;
        return Math.sqrt((xxDist * xxDist) + (yyDist * yyDist));
    } // End calcDistance

    // Calculates the force exerted on a given planet by another planet
    public double calcForceExertedBy(Planet p) {
        double dist = this.calcDistance(p);
        return (G * this.mass * p.mass) / (dist * dist);
    } // End calcForceExertedBy

    // Calculates the force exerted in the x-direction on a
    // given planet by another planet
    public double calcForceExertedByX(Planet p) {
        double netForce = this.calcForceExertedBy(p);
        double xxDist = p.xxPos - this.xxPos;
        double dist = this.calcDistance(p);
        return (netForce * xxDist) / dist;
    } // End calcForceExertedByX

    // Calculates the force exerted in the y-direction on a
    // given planet by another planet
    public double calcForceExertedByY(Planet p) {
        double netForce = this.calcForceExertedBy(p);
        double yyDist = p.yyPos - this.yyPos;
        double dist = this.calcDistance(p);
        return (netForce * yyDist) / dist;
    } // End calcForceExertedByY

    // Calculates the force exerted in the x-direction on a
    // given planets by the rest of the planets in its vicinity
    public double calcNetForceExertedByX(Planet[] allPlanets) {
        double netXForce = 0.0;    
        for (Planet p : allPlanets) {
            if (!this.equals(p)) {
                netXForce += this.calcForceExertedByX(p);
            }
        }
        return netXForce;
    } // End calcNetForceExertedByX

    // Calculates the force exerted in the y-direction on a
    // given planets by the rest of the planets in its vicinity
    public double calcNetForceExertedByY(Planet[] allPlanets) {
        double netYForce = 0.0;
        for (Planet p : allPlanets) {
            if (!this.equals(p)) {
                netYForce += this.calcForceExertedByY(p);
            }
        }
        return netYForce;
    } // End calcNetForceExertedByY

    // Determines how much the forces exerted on a planet will
    // cause it to accelerate, and the resulting change in its
    // velocity and position in a small period of time
    public void update(double dt, double fX, double fY) {
        double xxAcceleration = fX / this.mass;
        double yyAcceleration = fY / this.mass;
        this.xxVel += dt * xxAcceleration;
        this.yyVel += dt * yyAcceleration;
        this.xxPos += dt * this.xxVel;
        this.yyPos += dt * this.yyVel;
    } // End update

    // Draws a planet at its appropriate position
    public void draw() {
        StdDraw.picture(this.xxPos, this.yyPos, ("images/" + this.imgFileName));
    } // End draw

} // End Planet class