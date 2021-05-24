package controller;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.ResourceBundle;

import compression.Compression;
import filejob.CompressionFileRW;
import filejob.DefaultFileRW;
import filejob.FileRW;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import model.hierarchy.Warrior;
import model.hierarchy.agregation.Horse;
import model.hierarchy.agregation.Weapon;
import model.hierarchy.cavalry.CavalrySpearman;
import model.hierarchy.ranged.RangedSorcerer;
import model.hierarchy.ranged.RangedWarrior;
import serialization.BinarySerialization;
import serialization.JSONAdapter;
import serialization.Serialization;
import serialization.TextSerialization;

import java.beans.XMLEncoder;
import java.beans.XMLDecoder;

public class Controller {

    private Serialization serialization;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<CavalrySpearman> list;

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button removeButton;

    @FXML
    private StackPane infoPanes;

    @FXML
    private GridPane infoGrid;

    @FXML
    private Label nameLabel;

    @FXML
    private Label armorLabel;

    @FXML
    private Label costLabel;

    @FXML
    private Label penetrLabel;

    @FXML
    private Label weaonName;

    @FXML
    private Label weaponDamage;

    @FXML
    private Label weaponPrice;

    @FXML
    private Label weaponType;

    @FXML
    private Label horseName;

    @FXML
    private Label horseSpeed;

    @FXML
    private Label horseBreed;

    @FXML
    private GridPane editGrid;

    @FXML
    private TextField weaponNameField;

    @FXML
    private TextField weaponDamageField;

    @FXML
    private TextField weaponPriceField;

    @FXML
    private ComboBox<Weapon.Type> weaponTypeBox;

    @FXML
    private TextField horseNameField;

    @FXML
    private TextField horseSpeedField;

    @FXML
    private TextField horseBreedField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField armorField;

    @FXML
    private TextField hiringField;

    @FXML
    private TextField penetrField;

    @FXML
    private Button confirmEditButton;

    @FXML
    private Button confirmAddButton;

    @FXML
    private ComboBox<String> serializeMethod;

    @FXML
    private Button serializeButton;

    @FXML
    private Button deserializeButton;

    @FXML
    private CheckBox compressionCheckBox;

    private void changeCompression() {
        if (compressionCheckBox.isSelected()) {
            Class<? extends Compression> compressionClass = CompressionFileRW.compressionClasses.get(0);
            try {
                FileRW.setFileJob(new CompressionFileRW(compressionClass.getDeclaredConstructor().newInstance()));
            } catch (InstantiationException | InvocationTargetException |
                    NoSuchMethodException | IllegalAccessException e) {
                e.printStackTrace();
            }
            System.out.println("compress");
        }
        else FileRW.setFileJob(new DefaultFileRW());
    }

    @FXML
    void handleAdd(ActionEvent event) {
        infoGrid.setVisible(false);
        editGrid.setVisible(true);
        confirmEditButton.setVisible(false);
        confirmAddButton.setVisible(true);
        confirmAddButton.toFront();
        editGrid.toFront();
    }

    @FXML
    void handleEdit(ActionEvent event) {
        if (list.getSelectionModel().getSelectedItem() == null)
            return;
        infoGrid.setVisible(false);
        editGrid.setVisible(true);
        confirmAddButton.setVisible(false);
        confirmEditButton.setVisible(true);
        confirmEditButton.toFront();
        editGrid.toFront();

        CavalrySpearman selected = list.getSelectionModel().getSelectedItem();

        nameField.setText(selected.name);
        armorField.setText(Integer.toString(selected.armorClass));
        hiringField.setText(Integer.toString(selected.hiringCost));
        penetrField.setText(Integer.toString(selected.penetrationDamage));

        weaponNameField.setText(selected.weapon.name);
        weaponDamageField.setText(Integer.toString(selected.weapon.damage));
        weaponPriceField.setText(Integer.toString(selected.weapon.price));
        weaponTypeBox.setValue(selected.weapon.type);

        horseNameField.setText(selected.horse.name);
        horseSpeedField.setText(Integer.toString(selected.horse.speed));
        horseBreedField.setText(selected.horse.breed);

    }

    @FXML
    void handleConfirmAdd(ActionEvent event) {
        CavalrySpearman chel = new CavalrySpearman(nameField.getText());

        setValues(chel);

        warriorsList.add(chel);
    }

    @FXML
    void handleConfirmEdit(ActionEvent event) {
        CavalrySpearman selected = list.getSelectionModel().getSelectedItem();

        selected.name = nameField.getText();
        setValues(selected);

        editGrid.setVisible(false);
        infoGrid.setVisible(true);

        confirmEditButton.setVisible(false);
    }

    @FXML
    void handleDelete(ActionEvent event) {
        CavalrySpearman selected = list.getSelectionModel().getSelectedItem();
        warriorsList.remove(selected);
    }

    @FXML
    private void handleSerialize() {
        String fileName = "";
        switch (serializeMethod.getValue()) {
            case "Binary": {
                fileName = "output.bin";
                serialization = BinarySerialization.getInstance();
                break;
            }
            case "Object" : {
                fileName = "output.json";
                serialization = JSONAdapter.getInstance();
                break;
            }
            case "Text" : {
                fileName = "output.txt";
                serialization = TextSerialization.getInstance();
                break;
            }
        }
        serialization.serialize(new File(fileName),
                new LinkedList<>(warriorsList));
    }

    @FXML
    private void handleDeserialize() {
        String fileName = "";
        switch (serializeMethod.getValue()) {
            case "Binary" :
                fileName = "output.bin";
                serialization = BinarySerialization.getInstance();
                break;
            case "Object" :
                fileName = "output.json";
                serialization = JSONAdapter.getInstance();
                break;
            case "Text" :
                fileName = "output.txt";
                serialization = TextSerialization.getInstance();
                break;
        }
        warriorsList.addAll(serialization.deserialize(new File(fileName)));
        list.setItems(warriorsList);
    }

    @FXML
    void _handleDeserialize(ActionEvent event) {
        switch (serializeMethod.getValue()) {
            case "Binary":
                try {
                    FileInputStream fis = new FileInputStream("binary.out");
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    CavalrySpearman warrior;
                    LinkedList<CavalrySpearman> deserialized = (LinkedList<CavalrySpearman>) ois.readObject();
                    System.out.println(deserialized.get(0).name);
                    warriorsList.addAll(deserialized);
                    ois.close();
                    fis.close();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case "Object":
                try {
                    XMLDecoder d = new XMLDecoder(new BufferedInputStream(new FileInputStream("object.xml")));
                    LinkedList<CavalrySpearman> deserialized = (LinkedList<CavalrySpearman>) d.readObject();
                    warriorsList.addAll(deserialized);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;

            case "Text":
                readText();
                break;
        }
    }

    public ObservableList<CavalrySpearman> warriorsList = FXCollections.observableArrayList();

    @FXML
    void initialize() {

        FileRW.setFileJob(new DefaultFileRW());

        infoGrid.setVisible(false);
        editGrid.setVisible(false);
        confirmAddButton.setVisible(false);
        confirmEditButton.setVisible(false);

        list.setItems(warriorsList);

        MultipleSelectionModel<CavalrySpearman> model = list.getSelectionModel();

        model.selectedItemProperty().addListener((observableValue, cavalrySpearman, t1) -> {
            if (warriorsList.size() != 0) {
                editGrid.setVisible(false);
                infoGrid.setVisible(true);
                infoGrid.toFront();
                nameLabel.setText(t1.name);
                armorLabel.setText(Integer.toString(t1.armorClass));
                costLabel.setText(Integer.toString(t1.hiringCost));
                penetrLabel.setText(Integer.toString(t1.penetrationDamage));
                weaonName.setText(t1.weapon.name);
                weaponDamage.setText(Integer.toString(t1.weapon.damage));
                weaponPrice.setText(Integer.toString(t1.weapon.price));
                weaponType.setText(t1.weapon.type.toString());
                horseName.setText(t1.horse.name);
                horseSpeed.setText(Integer.toString(t1.horse.speed));
                horseBreed.setText(t1.horse.breed);
            } else {
                infoGrid.setVisible(false);
            }
        });

        weaponTypeBox.setItems(FXCollections.observableArrayList(Weapon.Type.BOW,
                Weapon.Type.KNIFE,
                Weapon.Type.SPEAR,
                Weapon.Type.STAFF,
                Weapon.Type.SWORD,
                Weapon.Type.MUSKET,
                Weapon.Type.HEAVY_SWORD));

        serializeMethod.setItems(FXCollections.observableArrayList("Binary", "Object", "Text"));
        serializeMethod.setValue("Binary");

        list.setCellFactory(i -> new ListCell<CavalrySpearman>() {
            @Override
            protected void updateItem(CavalrySpearman item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.name);
                }
            }

        });

        compressionCheckBox.setOnAction(event -> changeCompression());

    }

    private void setValues(CavalrySpearman spearman) {
        spearman.armorClass = Integer.parseInt(armorField.getText());
        spearman.hiringCost = Integer.parseInt(hiringField.getText());
        spearman.penetrationDamage = Integer.parseInt(penetrField.getText());

        spearman.weapon.name = weaponNameField.getText();
        spearman.weapon.damage = Integer.parseInt(weaponDamageField.getText());
        spearman.weapon.price = Integer.parseInt(weaponPriceField.getText());
        spearman.weapon.type = weaponTypeBox.getValue();

        spearman.horse.name = horseNameField.getText();
        spearman.horse.speed = Integer.parseInt(horseSpeedField.getText());
        spearman.horse.breed = horseBreedField.getText();
    }

    void readText() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("text"));
            String line;
            while ((line = reader.readLine()) != null) {
                warriorsList.add(parseCavalrySpearman(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    CavalrySpearman parseCavalrySpearman(String cavalrySpearman) {
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
