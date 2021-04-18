package pandemie;

import static pandemie.State.*;
import static pandemie.Event.*;
import static pandemie.Transition.*;

import java.util.Iterator;
import java.util.List;

/**
 * @author Laetitia Abelli
 * @version 2021.04.18
 */


public class Sapiens {

    private static final int MAX_CONTAMINATION = 50;
    private int debutContamination; // début de la pandémie
    private State etat;

    private pandemie.Field field;

    private pandemie.Location location;

    Sapiens(Field field, Location location, State state) {
        if(state==INFECTED){
            etat=INFECTED;
            debutContamination=1;
        }
        else{
            etat = SUSCEPTIBLE;
            debutContamination=0;
        }
        this.field = field;
        setLocation(location);
    }

    /**
     *
     * @return location (Location)
     */
    Location getLocation() {
        return location;
    }

    /**
     *
     * @param newLocation
     * no return
     */
    void setLocation(Location newLocation) {
        if (location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    /**
     *
     * @return fiel (Field)
     */
    Field getField() {
        return field;
    }

    /**
     *
     * @return etat (State)
     */
    State getState() {
        return etat;
    }

    /**
     *
     * @param newstate (State)
     *
     * Permet d'initialiser l'état courant par l'état passé en paramètre
     */
    void setState(State newstate) {
        etat=newstate;
    }

    /**
     * méthode qui définit l'action des individus
     *
     * Pour un certain individu "susceptible" , si celui d'à côté est "infected",
     * il devient "infected" selon la probabilité de contact
     *
     * Pour un certain individu qui a déjà attrapé la maladie , si celui d'à côté est "infected",
     * il devient "infected" une nouvelle fois selon la probabilité de recontact
     */
    void act() {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while (it.hasNext()) {
            Location where = it.next();
            Sapiens sapiens = field.getSapiensAt(where);
            if (sapiens != null ) {
                if(sapiens.getState() == INFECTED) {
                    if (this.getState() == SUSCEPTIBLE) {
                        State newState = nextState(SUSCEPTIBLE, CONTACT);
                        setState(newState);
                    }
                    if (this.getState() == RECOVERED) {
                        State newState = nextState(RECOVERED, RECONTACT);
                        setState(newState);
                    }
                }

            }

        }
        Location newLocation = getField().freeAdjacentLocation(getLocation());
        setLocation(newLocation);
    }

    /**
     * méthode permettant d'assurer la vaccination
     *
     * On ne peut être vacciné que si on est "susceptible" ou "recovered"
     */
    void vacciner(){

        if(this.getState()==SUSCEPTIBLE) {
            State newState = nextState(SUSCEPTIBLE, VACCIN);
            setState(newState);
        }
        else if (this.getState()==RECOVERED){
            State newState = nextState(RECOVERED, VACCIN);
            setState(newState);
        }

    }

    /**
     * @author Tommy Calendini
     * @version 2021.04.18
     *
     * méthode codant la vie des individus "infected"
     */
    void infectedLife() {
        debutContamination++;
        if (debutContamination > MAX_CONTAMINATION) {
            State newState=nextState(INFECTED,TIMEOUT);
            setState(newState);
        }
        Location newLocation = getField().freeAdjacentLocation(getLocation());
        setLocation(newLocation);
    }

}
