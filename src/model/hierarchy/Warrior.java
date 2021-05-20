package model.hierarchy;

import model.hierarchy.agregation.Weapon;

import java.io.Serializable;

public abstract class Warrior implements Serializable {
    public String name;
    public Weapon weapon;
    public int armorClass;
    public int hiringCost;

    public Warrior() {

    }

    public Warrior(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
