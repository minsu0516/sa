package co.kr.citus;


import java.util.ArrayList;
import java.util.Collections;

public class Tour {

    // Holds our tour of places
    private ArrayList<ITourPlace> tour = new ArrayList<>();
    // Cache
    private double cost = 0;
    // TourBuilder
    private TourBuilder builder;

    // Constructs a blank tour
    public Tour(TourBuilder builder) {
        this.builder = builder;
        for (int i = 0; i < builder.numberOfPlaces(); i++) {
            tour.add(null);
        }
    }

    // Constructs a tour from another tour
    Tour(TourBuilder builder, ArrayList tour) {
        this.builder = builder;
        this.tour = (ArrayList) tour.clone();
    }

    // Returns tour information
    ArrayList getTour() {
        return tour;
    }

    // Creates a shuffle individual
    Tour shuffle() {
        // Randomly reorder the tour
        if (builder.hasStartEndPlaces()) {
            ITourPlace e = tour.remove(tour.size() - 1);
            ITourPlace s = tour.remove(0);
            Collections.shuffle(tour);
            tour.add(0, s);
            tour.add(e);
        } else {
            Collections.shuffle(tour);
        }
        return this;
    }

    // Gets a place from the tour
    ITourPlace getPlace(int tourPosition) {
        return tour.get(tourPosition);
    }

    // Sets a place in a certain position within a tour
    void setPlace(int tourPosition, ITourPlace place) {
        tour.set(tourPosition, place);
        // If the tours been altered we need to reset the fitness and cost
        cost = 0;
    }

    // Gets the total cost of the tour
    double getCost() {
      //  if (cost == 0) {
            double tourCost = 0;
            // Loop through our tour's places
            for (int placeIndex = 0; placeIndex < tourSize(); placeIndex++) {
                // Get place we're traveling from
                ITourPlace fromPlace = getPlace(placeIndex);
                // Place we're traveling to
                ITourPlace destinationPlace;
                // Check we're not on our tour's last place, if we are set our
                // tour's final destination place to our starting place
                if (placeIndex + 1 < tourSize()) {
                    destinationPlace = getPlace(placeIndex + 1);
                } else {
                    if (builder.hasStartEndPlaces()) {
                        continue;
                    }
                    destinationPlace = getPlace(0);
                }
                // Get the cost between the two places
                tourCost += fromPlace.costTo(destinationPlace);
            }
            cost = tourCost;
      //  }
        return cost;
    }

    // Get number of places on our tour
    int tourSize() {
        return tour.size();
    }

    void swap(int pos1, int pos2) {
        Collections.swap(tour, pos1, pos2);
    }

    @Override
    public String toString() {
        String geneString = "| ";
        for (int i = 0; i < tourSize(); i++) {
            geneString += getPlace(i) + " | ";
        }
        return geneString;
    }
}