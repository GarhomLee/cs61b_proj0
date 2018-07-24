public class Planet{
    public double xxPos; //in meter
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass; //in kg
    public String imgFileName;

    public Planet(double xP, double yP, double xV, double yV, double m, String img){
        /** constructor */
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }
    public Planet(Planet p){
        /** constructor */
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }
    public double calcDistance(Planet p){
        /** calculate the distance between this planet and planet p */
        double xDistance = p.xxPos - this.xxPos;
        double yDistance = p.yyPos - this.yyPos;
        double distance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);
        return distance; //it's scalar
    }
    public double calcForceExertedBy(Planet p){
        /**calculate the force on this planet exerted by planet p */
        double G = 6.67e-11;
        double force = (G * this.mass * p.mass) / (this.calcDistance(p) * this.calcDistance(p));
        return force; //it's scalar
    }
    public double calcForceExertedByX(Planet p){
        /** calculate the force exerted in the X direction */
        double distance = this.calcDistance(p);
        double xDistance = p.xxPos - this.xxPos;
        double force = this.calcForceExertedBy(p);
        double forceX = (force * xDistance) / distance;
        return forceX; //signed vector
    }
    public double calcForceExertedByY(Planet p){
        /** calculate the force exerted in the Y direction */
        double distance = this.calcDistance(p);
        double yDistance = p.yyPos - this.yyPos;
        double force = this.calcForceExertedBy(p);
        double forceY = (force * yDistance) / distance;
        return forceY; //signed vector
    }
    public double calcNetForceExertedByX(Planet[] allPlanets){
        /** calculate the net X force exerted by all planets in the array upon the current Planet */
        double netForceX = 0;
        for(Planet p: allPlanets){
            if(!this.equals(p)){
                netForceX += calcForceExertedByX(p);
            }
        }
        return netForceX;
    }
    public double calcNetForceExertedByY(Planet[] allPlanets){
        /** calculate the net Y force exerted by all planets in the array upon the current Planet */
        double netForceY = 0;
        for(Planet p: allPlanets){
            if(!this.equals(p)){
                netForceY += calcForceExertedByY(p);
            }
        }
        return netForceY;
    }
    public void update(double dt, double forceX, double forceY){
        /** update the current position and velocity given the time increment, force in X and Y directions*/
        double netAccelerationX = forceX / mass;
        xxVel = xxVel + dt * netAccelerationX; //update velocity in X direction
        xxPos = xxPos + dt * xxVel; //update position in X direction
        double netAccelerationY = forceY / mass;
        yyVel = yyVel + dt * netAccelerationY; //update velocity in Y direction
        yyPos = yyPos + dt * yyVel; //update position in Y direction
    }
    public void draw(){
        StdDraw.picture(xxPos, yyPos, imgFileName);
    }
}