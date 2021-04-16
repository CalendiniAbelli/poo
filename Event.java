package pandemie;

public enum Event {

    CONTACT_HOT(0.2), CONTACT_COLD(0.4), TIMEOUT(0.5), VACCIN(0.01);

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
