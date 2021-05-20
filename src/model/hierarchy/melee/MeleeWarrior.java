package model.hierarchy.melee;

import model.hierarchy.Warrior;

public abstract class MeleeWarrior extends Warrior {
    public int stamina;

    public MeleeWarrior(String name) {
        super(name);
    }
}
