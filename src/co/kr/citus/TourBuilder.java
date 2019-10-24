package co.kr.citus;

import java.util.ArrayList;

// Simulated Annealing algorithm for finding solution of travelling salesperson problem.
public class TourBuilder {

    // Holds our places
    private ArrayList<ITourPlace> destinationPlaces = new ArrayList<>();
    private ITourPlace start = null;
    private ITourPlace end = null;

    // Set initial temperature
    private double temperature = 1000;

    // Cooling rate
    private double coolingRate = 0.003;

    // Calculate the acceptance probability
    private static double acceptanceProbability(double energy, double newEnergy, double temperature) {
        // If the new solution is better, accept it
        if (newEnergy < energy) {
            return 1.0;
        }
        // If the new solution is worse, calculate an acceptance probability
        return Math.exp((energy - newEnergy) / temperature);
    }

    public TourBuilder setTemperature(double temp) {
        temperature = temp;
        return this;
    }

    public TourBuilder setCoolingRate(double rate) {
        coolingRate = rate;
        return this;
    }

    public boolean hasStartEndPlaces() {
        return (start != null && end != null);
    }

    // Adds a destination place
    public TourBuilder addPlace(ITourPlace place) {
        if (end != null) {
            destinationPlaces.add(destinationPlaces.size() - 1, place);
        } else {
            destinationPlaces.add(place);
        }
        return this;
    }

    // Get a place
    public ITourPlace getPlace(int index) {
        return destinationPlaces.get(index);
    }

    public TourBuilder setStartPlace(ITourPlace start) {
        this.start = start;
        destinationPlaces.add(0, start);
        return this;
    }

    public TourBuilder setEndPlace(ITourPlace end) {
        addPlace(end);
        this.end = end;
        return this;
    }

    // Get the number of destination places
    public int numberOfPlaces() {
        return destinationPlaces.size();
    }

    private Tour getAdjacentSolution(Tour currentSolution) {
        Tour newSolution = new Tour(this, currentSolution.getTour());

        // Get a shuffle positions in the tour except position 0
        int tourPos1;
        int tourPos2;
        if (hasStartEndPlaces()) {
            tourPos1 = (int) ((newSolution.tourSize() - 2) * Math.random() + 1);
            tourPos2 = (int) ((newSolution.tourSize() - 2) * Math.random() + 1);
        } else {
            tourPos1 = (int) (newSolution.tourSize() * Math.random());
            tourPos2 = (int) (newSolution.tourSize() * Math.random());
        }

        // Swap them
        newSolution.swap(tourPos1, tourPos2);

        return newSolution;
    }

    public Tour randomTour() {
        // Initialize initial solution
        Tour currentSolution = new Tour(this, destinationPlaces).shuffle();
        return currentSolution;
    }

    public Tour build(Tour currentSolution) {
        // Set as current best
        Tour best = new Tour(this, currentSolution.getTour());

        // Loop until system has cooled
        while (temperature > 1) {
            // Create new neighbour tour
            Tour newSolution = getAdjacentSolution(currentSolution);

            // Get energy of solutions
            double currentEnergy = currentSolution.getCost();
            double neighbourEnergy = newSolution.getCost();

            // Decide if we should accept the neighbour
            if (acceptanceProbability(currentEnergy, neighbourEnergy, temperature) > Math.random()) {
                currentSolution = new Tour(this, newSolution.getTour());
            }

            // Keep track of the best solution found
            if (currentSolution.getCost() < best.getCost()) {
                best = new Tour(this, currentSolution.getTour());
            }

            // Cool system
            temperature *= 1 - coolingRate;

        }

        return best;
    }

    @Override
    public String toString() {
        String geneString = "| ";
        for (int i = 0; i < destinationPlaces.size(); i++) {
            geneString += getPlace(i) + " | ";
        }
        return geneString;
    }

    /*public static void test() {
        TourBuilder tourBuilder = new TourBuilder()
                .addPlace(new Place(60, 200))
                .addPlace(new Place(180, 200))
                .addPlace(new Place(80, 180))
                .addPlace(new Place(140, 180))
                .addPlace(new Place(20, 160))
                .addPlace(new Place(100, 160))
                .addPlace(new Place(200, 160))
                .setStartPlace(new Place(140, 140))
                .setEndPlace(new Place(40, 120))
                .setTemperature(1000)
                .setCoolingRate(0.0003)
                ;

        Tour currentSolution = tourBuilder.randomTour();
        System.out.println("Initial tour: " + currentSolution.toString());
        System.out.println("Initial solution distance: " + currentSolution.getCost());

        long startTime = System.currentTimeMillis();
        Tour best = tourBuilder.build(currentSolution);

        System.out.println("Finding best tour route in " + (System.currentTimeMillis() - startTime) + "ms");
        System.out.println("Final solution distance: " + best.getCost());
        System.out.println("Tour: " + best);
    }*/
}