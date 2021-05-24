package serialization;

import filejob.FileRW;
import model.hierarchy.*;
import model.hierarchy.agregation.Horse;
import model.hierarchy.agregation.Weapon;
import model.hierarchy.cavalry.CavalrySpearman;

import java.io.*;
import java.util.LinkedList;

public class TextSerialization implements Serialization {
    private static TextSerialization textSerialization;
    private TextSerialization() {
    }

    public static TextSerialization getInstance() {
        if (textSerialization == null) {
            textSerialization = new TextSerialization();
        }
        return textSerialization;
    }

    @Override
    public void serialize(File file, LinkedList<CavalrySpearman> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (CavalrySpearman cavalrySpearman : list) {
            stringBuilder.append(cavalrySpearman.toString());
        }
        FileRW.getFileJob().write(file, stringBuilder.toString());
    }

    @Override
    public LinkedList<CavalrySpearman> deserialize(File file) {
        LinkedList<CavalrySpearman> linkedList = new LinkedList<>();
        String readStrings = FileRW.getFileJob().read(file);
        for (String s : readStrings.split("\n")) {
            linkedList.add(parseCavalrySpearman(s));
        }
        return linkedList;
    }

    private CavalrySpearman parseCavalrySpearman(String cavalrySpearman) {
        CavalrySpearman warrior = new CavalrySpearman();
        warrior.weapon = new Weapon();
        warrior.horse = new Horse();

        String[] objs = cavalrySpearman.split(";");

        String[] csFields = objs[0].substring(objs[0].indexOf("{")).split(",");
        warrior.name = csFields[0].substring(csFields[0].indexOf("=")+1);
        warrior.armorClass = Integer.parseInt(csFields[1].substring(csFields[1].indexOf("=")+1));
        warrior.hiringCost = Integer.parseInt(csFields[2].substring(csFields[2].indexOf("=")+1));
        warrior.penetrationDamage = Integer.parseInt(csFields[3].substring(csFields[3].indexOf("=")+1));

        String[] weaponFields = objs[1].substring(objs[1].indexOf("{w"), objs[1].indexOf("}")).split(",");
        warrior.weapon.name = weaponFields[0].substring(weaponFields[0].indexOf("=")+1);
        warrior.weapon.damage = Integer.parseInt(weaponFields[1].substring(weaponFields[1].indexOf("=")+1));
        warrior.weapon.price = Integer.parseInt(weaponFields[2].substring(weaponFields[2].indexOf("=")+1));
        warrior.weapon.type = Weapon.parseType(weaponFields[3].substring(weaponFields[3].indexOf("=")+1));

        String[] horseFields = objs[2].substring(objs[2].indexOf("{h"), objs[2].indexOf("}}")).split(",");
        warrior.horse.name = horseFields[0].substring(horseFields[0].indexOf("=")+1);
        warrior.horse.speed = Integer.parseInt(horseFields[1].substring(horseFields[1].indexOf("=")+1));
        warrior.horse.breed = horseFields[2].substring(horseFields[2].indexOf("=")+1);

        return warrior;
    }
}
