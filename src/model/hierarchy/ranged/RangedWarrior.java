package model.hierarchy.ranged;

import model.hierarchy.Warrior;

public abstract class RangedWarrior extends Warrior {
    public double accuracy;
    public int attackRange;

    public RangedWarrior(String name) {
        super(name);
    }
}
