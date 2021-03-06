package pandemie;

import java.util.HashMap;

/**
 * This class collects and provides some statistical data on the state of a
 * field. It is flexible: it will create and maintain a counter for any class of
 * object that is found within the field.
 *
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29
 *
 * @author Tommy Calendini
 * @version 2021.04.18
 */
class FieldStats {
    // Counters for sapiens
    private final HashMap<State, Counter> counters;
    // Whether the counters are currently up to date.
    private boolean countsValid;

    /**
     * Construct a FieldStats object.
     */
    FieldStats() {
        // Set up a collection for counters for each type of spaiens that
        // we might find
        counters = new HashMap<>();
        countsValid = false;
    }

    /**
     * Get details of what is in the field.
     *
     * @return A string describing what is in the field.
     */
    String getPopulationDetails(Field field) {
        StringBuilder buffer = new StringBuilder();
        if (!countsValid) {
            generateCounts(field);
        }
        for (State key : counters.keySet()) {
            Counter info = counters.get(key);
            buffer.append(info.getName());
            buffer.append(": ");
            buffer.append(info.getCount());
            buffer.append(' ');
        }
        return buffer.toString();
    }

    /**
     * Get the number of individuals in the population of a given class.
     *
     * @return An int with the number for this class.
     */
    int getPopulationCount(Field field, State key) {
        if (!countsValid) {
            generateCounts(field);
        }

        Counter counter = counters.get(key);
        return counter.getCount();
    }

    /**
     * Invalidate the current set of statistics; reset all counts to zero.
     */
    void reset() {
        countsValid = false;
        for (State key : counters.keySet()) {
            Counter count = counters.get(key);
            count.reset();
        }
    }

    /**
     * Increment the count for one class of sapiens.
     *
     * @param etat The class of sapiens to increment.
     */
    void incrementCount(State etat) {
        Counter count = counters.get(etat);
        if (count == null) {
            // We do not have a counter for this species yet.
            // Create one.
            count = new Counter(etat.toString());
            counters.put(etat, count);
        }
        count.increment();
    }

    /**
     * Indicate that a sapiens count has been completed.
     */
    void countFinished() {
        countsValid = true;
    }

    /**
     * Determine whether the simulation is still viable. I.e., should it
     * continue to run.
     *
     * @return true If there is more than one species alive.
     */
    boolean isViable(Field field) {
        // How many counts are non-zero.
        int nonZero = 0;
        if (!countsValid) {
            generateCounts(field);
        }
        for (State key : counters.keySet()) {
            Counter info = counters.get(key);
            if (info.getCount() > 0) {
                nonZero++;
            }
        }
        return nonZero > 1;
    }

    /**
     * Generate counts of the number of sapiens. These are not kept up
     * to date as sapiens are placed in the field, but only when a
     * request is made for the information.
     *
     * @param field The field to generate the stats for.
     */
    private void generateCounts(Field field) {
        reset();
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Sapiens sapiens = field.getSapiensAt(row, col);
                if (sapiens != null) {
                    incrementCount(sapiens.getState());
                }
            }
        }
        countsValid = true;
    }
}
