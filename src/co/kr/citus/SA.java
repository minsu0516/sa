package co.kr.citus;

// Simulated Annealing algorithm for finding solution of travelling salesperson problem.
public class SA {

    public static void main(String[] args) {

        TourBuilder tourBuilder = new TourBuilder()
                .addPlace(new Place(37.581151, 126.979038))
                .addPlace(new Place(37.580325, 126.984649))
                .addPlace(new Place(37.582797, 126.991206))

                //.addPlace(new Place(200, 160))
               // .setStartPlace(new Place(140, 140))
             //   .setEndPlace(new Place(40, 120))
                .setTemperature(1000)
                .setCoolingRate(0.003);

        Tour currentSolution = tourBuilder.randomTour();
        System.out.println("Initial tour: " + currentSolution.toString());
        System.out.println("Initial solution distance: " + currentSolution.getCost());

        long startTime = System.currentTimeMillis();
        Tour best = tourBuilder.build(currentSolution);

        System.out.println("Finding best tour route in " + (System.currentTimeMillis() - startTime) + "ms");
        System.out.println("Final solution distance: " + best.getCost());
        System.out.println("Tour: " + best);
    }
}