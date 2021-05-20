package model.hierarchy.agregation;

import java.io.Serializable;

public class Weapon implements Serializable {
    public String name;
    public int damage;
    public int price;
    public Type type;

    public enum Type {
        SWORD,
        HEAVY_SWORD,
        SPEAR,
        BOW,
        STAFF,
        KNIFE,
        MUSKET
    }

    public static Type parseType(String type) {
        switch (type) {
            case "SWORD":
                return Type.SWORD;

            case "HEAVY_SWORD":
                return Type.HEAVY_SWORD;

            case "SPEAR":
                return Type.SPEAR;

            case "BOW":
                return Type.BOW;

            case "STAFF":
                return Type.STAFF;

            case "KNIFE":
                return Type.KNIFE;

            case "MUSKET":
                return Type.MUSKET;

            default:
                return null;
        }
    }
}
