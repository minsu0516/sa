package co.kr.citus;

public class Place implements ITourPlace {
    private double x;
    private double y;

    // Constructs a place at chosen x, y location
    Place(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Gets place's x coordinate
    public double getLon() {
        return this.x;
    }

    // Gets place's y coordinate
    public double getLat() {
        return this.y;

    }

    public int getDuration() {
        return 60 * 60;
    }

    // Gets the distance to given place
    public double costTo(ITourPlace place) {
        double xDistance = Math.abs(getLon() - place.getLon());
        double yDistance = Math.abs(getLat() - place.getLat());
        double distance = Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));

        return distance;
    }

    public String toString() {
        return "(" + getLon() + ", " + getLat() + ")";
    }
}
