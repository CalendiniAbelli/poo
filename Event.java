package pandemie;

/** classe Event
 *
 * @author Laetitia Abelli
 * @version 2021.04.18
 *
 * définit la probabilité de contact, de mourir, d'être vaccinné, ou d'être recontaminé
 */

public enum Event {

    CONTACT(0.2), TIMEOUT(0.4), VACCIN(0.01),RECONTACT(0.05);

    protected double probability;

    /**constructeur privé
     *
     * @param proba
     */
    private Event(double proba) {
        this.probability= proba;
    }

    /** méthode getProba()
     * @return probability
     */
    public double getProba() {
        return probability;
    }
}
