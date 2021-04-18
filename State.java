package pandemie;
import javafx.scene.paint.Color;

/**
 * @author Laetitia Abelli
 * @version 2021.04.18
 */

public enum State {

    SUSCEPTIBLE(Color.YELLOW), INFECTED(Color.RED), RECOVERED(Color.BLUE), DEAD(Color.BLACK), VACCINATED(Color.PINK);

    protected Color color;

    /**constructeur
     *
     * @param color
     */
    private State(Color color) {
        this.color= color;
    }

    /**
     *
     * @return color (Color)
     */
    public Color getColor() {
        return color;
    }

    /**
     *
     * @return res (String)
     *
     * Chaque couleur correspond à un type de Sapiens
     * afin de pouvoir les reconnaître sur la simulation
     *
     */
    public String toString(){
        String res="";
        if(color==Color.YELLOW){
            res="Susceptible";
        }
        else if(color==Color.BLUE){
            res="Recovered";
        }
        if(color==Color.BLACK){
            res="Dead";
        }
        if(color==Color.RED){
            res="Infected";
        }
        if(color==Color.PINK){
            res="Vaccinated";
        }
        return res;
    }
}
