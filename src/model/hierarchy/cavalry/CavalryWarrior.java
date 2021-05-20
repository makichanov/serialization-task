package model.hierarchy.cavalry;

import model.hierarchy.agregation.Horse;
import model.hierarchy.Warrior;

import java.io.Serializable;

public abstract class CavalryWarrior extends Warrior implements Serializable {
    public Horse horse;

    public CavalryWarrior() {

    }

    public CavalryWarrior(String name) {
        super(name);
    }
}
