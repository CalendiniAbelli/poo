package pandemie;
import javafx.scene.paint.Color;

public enum State {

    SUSCEPTIBLE(Color.YELLOW), INFECTED(Color.RED), RECOVERED(Color.GREEN), DEAD(Color.BLACK), VACCINATED(Color.PINK);

    protected Color color;

    /**constructeur
     *
     * @param color
     */
    private State(Color color) {
        this.color= color;
    }

    public Color getColor() {
        return color;
    }

    public String toString(){
        String res="";
        if(color==Color.YELLOW){
            res="Susceptible";
        }
        else if(color==Color.GREEN){
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
