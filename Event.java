package pandemie;

public enum Event {

    CONTACT(0.2), TIMEOUT(0.5);
    /** 60 correspond à 60min : le temps affecté
     *  et 01 à 1m : la distance minimale entre les individus
     */

    protected double probability;

    /**constructeur
     *
     * @param proba
     */
    private Event(double proba) {
        this.probability= proba;
    }

    public double getProba() {
        return probability;
    }
}