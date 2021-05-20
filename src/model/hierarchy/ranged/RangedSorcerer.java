package model.hierarchy.ranged;

public class RangedSorcerer extends RangedWarrior {
    public int mana;
    public Specification spellType;

    public RangedSorcerer(String name) {
        super(name);
    }

    enum Specification {
        AIR,
        AQUA,
        FLAME,
        GEo
    }
}
