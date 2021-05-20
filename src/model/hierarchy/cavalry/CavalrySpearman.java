package model.hierarchy.cavalry;

import model.hierarchy.agregation.Horse;
import model.hierarchy.agregation.Weapon;

import java.io.Serializable;

public class CavalrySpearman extends CavalryWarrior implements Serializable {

    public int penetrationDamage;

    public CavalrySpearman() {
        horse = new Horse();
        weapon = new Weapon();
    }

    public CavalrySpearman(String name) {
        super(name);
        horse = new Horse();
        weapon = new Weapon();
    }

    @Override
    public String toString() {
        String line = "CavalrySpearman{" +
                "name=" + name +
                ",armorClass=" + armorClass +
                ",hiringCost=" + hiringCost +
                ",penetrationDamage=" + penetrationDamage +
                ";Weapon{" +
                "weaponName=" + weapon.name +
                ",weaponDamage=" + weapon.damage +
                ",weaponPrice=" + weapon.price +
                ",weaponType=" + weapon.type +
                '}' +
                ";Horse{" +
                "horseName=" + horse.name +
                ",weaponSpeed=" + horse.speed +
                ",horseBreed=" + horse.breed +
                '}' +
                "}\n";
        int size = line.length();
        return (size + line);
    }
}
