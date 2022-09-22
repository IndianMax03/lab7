package gui.input;

import base.*;
import gui.listeners.CityListener;
import gui.util.DialogFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Typer {
    private final JFrame frame = DialogFrame.getFrame();

    public Typer() {
        JPanel mainPanel = new JPanel();
        Font font = DialogFrame.getFont();
        frame.add(mainPanel);

        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints name = new GridBagConstraints();
        name.insets = new Insets(6,6,6,6);

        name.gridx = 0;
        name.gridy = 0;
        JLabel nameLabel = new JLabel("Имя города:");
        nameLabel.setFont(font);
        mainPanel.add(nameLabel, name);
        name.gridx = 1;
        name.gridy = 0;
        JTextField nameField = new JTextField(20);
        nameField.setFont(font);
        mainPanel.add(nameField, name);

        name.gridx = 0;
        name.gridy = 1;
        JLabel coordsLabel = new JLabel("Координаты в формате [x;y]:");
        coordsLabel.setFont(font);
        mainPanel.add(coordsLabel, name);
        name.gridx = 1;
        name.gridy = 1;
        JTextField coordField = new JTextField(20);
        coordField.setFont(font);
        mainPanel.add(coordField, name);

        name.gridx = 0;
        name.gridy = 2;
        JLabel areaLabel = new JLabel("Площадь:");
        areaLabel.setFont(font);
        mainPanel.add(areaLabel, name);
        name.gridx = 1;
        name.gridy = 2;
        JTextField areaField = new JTextField(20);
        areaField.setFont(font);
        mainPanel.add(areaField, name);

        name.gridx = 0;
        name.gridy = 3;
        JLabel populationLabel = new JLabel("Население:");
        populationLabel.setFont(font);
        mainPanel.add(populationLabel, name);
        name.gridx = 1;
        name.gridy = 3;
        JTextField populationField = new JTextField(20);
        populationField.setFont(font);
        mainPanel.add(populationField, name);

        name.gridx = 0;
        name.gridy = 4;
        JLabel maslLabel = new JLabel("Высота над уровнем моря:");
        maslLabel.setFont(font);
        mainPanel.add(maslLabel, name);
        name.gridx = 1;
        name.gridy = 4;
        JTextField maslField = new JTextField(20);
        maslField.setFont(font);
        mainPanel.add(maslField, name);

        name.gridx = 0;
        name.gridy = 5;
        JLabel climateLabel = new JLabel("Климат:");
        climateLabel.setFont(font);
        mainPanel.add(climateLabel, name);
        name.gridx = 1;
        name.gridy = 5;
        JComboBox<Climate> climateField = new JComboBox<>(Climate.values());
        climateField.setFont(font);
        mainPanel.add(climateField, name);

        name.gridx = 0;
        name.gridy = 6;
        JLabel governmentLabel = new JLabel("Тип правления:");
        governmentLabel.setFont(font);
        mainPanel.add(governmentLabel, name);
        name.gridx = 1;
        name.gridy = 6;
        JComboBox<Government> governmentField = new JComboBox<>(Government.values());
        governmentField.setFont(font);
        mainPanel.add(governmentField, name);

        name.gridx = 0;
        name.gridy = 7;
        JLabel standardLabel = new JLabel("Уровень жизни:");
        standardLabel.setFont(font);
        mainPanel.add(standardLabel, name);
        name.gridx = 1;
        name.gridy = 7;
        JComboBox<StandardOfLiving> standardField = new JComboBox<>(StandardOfLiving.values());
        standardField.setFont(font);
        mainPanel.add(standardField, name);

        name.gridx = 0;
        name.gridy = 8;
        JLabel leaderLabel = new JLabel("Правитель:");
        leaderLabel.setFont(font);
        mainPanel.add(leaderLabel, name);
        name.gridx = 1;
        name.gridy = 8;
        JComboBox<Leaders> leaderField = new JComboBox<>(Leaders.values());
        leaderField.setFont(font);
        mainPanel.add(leaderField, name);

        name.gridx = 0;
        name.gridy = 9;
        name.gridwidth = 2;
        JButton acceptButton = new JButton("Подтвердить");
        acceptButton.setFont(font);
        mainPanel.add(acceptButton, name);

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = Validator.validateName(nameField.getText());
                Coordinates coordinates = Validator.validateCoordinates(coordField.getText());
                Float area = Validator.validateArea(areaField.getText());
                Integer population = Validator.validatePopulation(populationField.getText());
                Float masl = Validator.validateMetersAboveSeaLevel(maslField.getText());
                Climate climate = (Climate) climateField.getSelectedItem();
                Government government = (Government) governmentField.getSelectedItem();
                StandardOfLiving standard = (StandardOfLiving) standardField.getSelectedItem();
                Leaders cityLeader = (Leaders) leaderField.getSelectedItem();
                Human human = Human.newHumanByLeader(cityLeader);

                try {
                    notifyCityListeners(new City(name, coordinates, area, population, masl, climate, government, standard, human));
                } catch (NullPointerException ex) {
                    showError("Плохо создали");
                }
            }
        });

        frame.setVisible(true);
        frame.revalidate();
    }

    private final List<CityListener> cityListeners = new ArrayList<>();

    public void addCityListener(CityListener cityListener) {
        cityListeners.add(cityListener);
    }

    private void notifyCityListeners(City city) {
        for (CityListener cityListener : cityListeners) {
            cityListener.created(city);
        }
    }

    public void showError(String error) {
        JOptionPane.showMessageDialog(frame, error, "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    public void hide() {
        frame.setVisible(false);
    }

    public void setTitle(String title) {
        frame.setTitle(title);
    }

}
