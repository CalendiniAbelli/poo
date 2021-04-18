package pandemie;

import static pandemie.State.*;

import java.util.*;

/**
 * A simple predator-prey simulator, based on a rectangular field containing
 * rabbits and foxes.
 *
 * @author David J. Barnes and Michael Kölling
 * @author Peter Sander
 * @version 2017.03.24
 *
 * @author Tommy Calendini
 * @version 2021.04.11
 */
class Simulator {
    static final int DEFAULT_WIDTH = 120;
    static final int DEFAULT_DEPTH = 80;
    static final int ITERATIONS = 5000;

    static final double INFECTED_CREATION_PROBABILITY=0.008;
    static final double SUSCEPTIBLE_CREATION_PROBABILITY=0.08;

    private final List<Sapiens> sapiens;
    private final Field field;
    private int step;
    private final List<SimulatorView> views = new ArrayList<>();

    /**
     * Construct a simulation field with default size.
     */
    Simulator(SimulatorView... views) {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH, views);
    }

    /**
     * Create a simulation field with the given size.
     *
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    Simulator(int depth, int width, SimulatorView... views) {
        if (width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        sapiens = new ArrayList<>();
        field = new Field(depth, width);
        Arrays.asList(views).forEach(v -> this.views.add(v));
        reset();
    }

    boolean isViable(int step) {
        return step <= ITERATIONS && views.get(0).isViable(field);
    }

    /**
     * Run the simulation from its current state for a single step. Iterate over
     * the whole field updating the state of each animal.
     *
     * @return Current step.
     */
    int simulateOneStep() {
        step++;
        for (Iterator<Sapiens> it = sapiens.iterator(); it.hasNext(); ) {
            Sapiens sapien = it.next();
            if(sapien.getState()==INFECTED){
                sapien.infectedLife();
            }
            // Si l'individu n'est pas mort, on appelle act()
            // La vaccination n'arrive qu'à partir du 20ème rang
            else if(sapien.getState()!=DEAD) {
                sapien.act();
                if (step > 20) {
                    sapien.vacciner();
                }
            }
        }
        updateViews();
        return step;
    }

    /**
     * Reset the simulation to a starting position.
     */
    void reset() {
        step = 0;
        sapiens.clear();
        views.forEach(v -> v.reset());
        populate();
        updateViews();
    }

    /**
     * Update all existing views.
     */
    private void updateViews() {
        views.forEach(v -> v.showStatus(step, field));
    }

    /**
     * Randomly populate the field with sapiens
     * Si rand est inférieur ou égal à INFECTED_CREATION_PROBABILITY,
     * l'individu devient un "infected".
     * Si rand est inférieur ou égale à SUSCEPTIBLE_CREATION_PROBABILITY,
     * il devient "suceptible".
     */
    private void populate() {
        Random rand = Randomizer.getRandom();
        field.clear();
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                if (rand.nextDouble() <= INFECTED_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Sapiens infecte= new Sapiens( field, location, INFECTED);
                    sapiens.add(infecte);
                } else if (rand.nextDouble() <= SUSCEPTIBLE_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Sapiens susceptible= new Sapiens( field, location, SUSCEPTIBLE);
                    sapiens.add(susceptible);
                }
                // else leave the location empty.
            }
        }
    }
}
