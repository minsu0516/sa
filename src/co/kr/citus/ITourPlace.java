package co.kr.citus;

public interface ITourPlace {
    // Gets place's x coordinate
    double getLon();

    // Gets place's y coordinate
    double getLat();

    // Gets place's average duration time in seconds
    int getDuration();

    // Gets the distance to given place
    double costTo(ITourPlace place);

    String toString();
}
