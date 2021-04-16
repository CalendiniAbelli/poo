package pandemie;

import static pandemie.State.*;
import static pandemie.Event.*;
import static pandemie.Transition.*;

import java.util.Iterator;
import java.util.List;

public class Sapiens {
    private static final int MAX_CONTAMINATION = 50;
    private static final int FIRST_VACCINATION = 20;
    private int debutContamination;
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


    Location getLocation() {
        return location;
    }


    void setLocation(Location newLocation) {
        if (location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    Field getField() {
        return field;
    }

    State getState() {
        return etat;
    }
    void setState(State newstate) {
        etat=newstate;
    }

    void act(){

        debutContamination++;

        if(this.getState()==INFECTED){
            incrementAge();
        }
        if(this.getState()!=DEAD) {
            if(this.getState()==SUSCEPTIBLE) {
                Field field = getField();
                List<Location> adjacent = field.adjacentLocations(getLocation());
                Iterator<Location> it = adjacent.iterator();
                while (it.hasNext()) {
                    Location where = it.next();
                    Sapiens sapiens = field.getSapiensAt(where);
                    if(sapiens!=null){
                        if(sapiens.getState()==INFECTED) {
                                State newState = nextState(SUSCEPTIBLE,CONTACT_HOT);
                                setState(newState);
                            }

                        }
                    }
                }
                Location newLocation = getField().freeAdjacentLocation(getLocation());
                setLocation(newLocation);
            }
            Location newLocation = getField().freeAdjacentLocation(getLocation());
            setLocation(newLocation);
        }

    void vacciner(){

        if(this.getState()==SUSCEPTIBLE) {

            if (debutContamination > FIRST_VACCINATION) {

                State newState = nextState(SUSCEPTIBLE, VACCIN);
                setState(newState);
            }
        }

    }

    private void incrementAge() {

        if (debutContamination > MAX_CONTAMINATION) {
            State newState=nextState(INFECTED,TIMEOUT);
            setState(newState);
        }
    }

}
