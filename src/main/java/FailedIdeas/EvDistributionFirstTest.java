package FailedIdeas;

/*

import org.example.Pokemon.Pokemon;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;


public class EvDistributionFirstTest {

    public static void EvDistributionWindow(Pokemon pokemon) {

        JFrame frame = new JFrame("EV distribution for " + pokemon.getName());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(0, 3));

        JTextField hp = new JTextField("0", 3);
        JSlider hpSlider = createSlider();
        JTextField attack = new JTextField("0", 3);
        JSlider attackSlider = createSlider();
        JTextField defense = new JTextField("0", 3);
        JSlider defenseSlider = createSlider();
        JTextField spAttack = new JTextField("0", 3);
        JSlider spAttackSlider = createSlider();
        JTextField spDefense = new JTextField("0", 3);
        JSlider spDefenseSlider = createSlider();
        JTextField speed = new JTextField("0", 3);
        JSlider speedSlider = createSlider();

        JLabel remainingEvsLabel = new JLabel("Remaining Evs: 508");
        remainingEvsLabel.setForeground(Color.BLUE);

        JButton submit = new JButton("Assign EV spread");
        submit.setEnabled(false);

        submit.addActionListener(e -> {
            try {
                int hpEVs = Integer.parseInt(hp.getText());
                int attackEVs = Integer.parseInt(attack.getText());
                int defenseEVs = Integer.parseInt(defense.getText());
                int spAttackEVs = Integer.parseInt(spAttack.getText());
                int spDefenseEVs = Integer.parseInt(spDefense.getText());
                int speedEVs = Integer.parseInt(speed.getText());

                pokemon.setEvs(hpEVs, attackEVs, defenseEVs, spAttackEVs, spDefenseEVs, speedEVs);

                pokemon.getStats().calculateFinalStats(pokemon);
                frame.dispose();

            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(frame, "Invalid EVs", "Error", JOptionPane.ERROR_MESSAGE);

            } catch (IllegalArgumentException exception) {
                JOptionPane.showMessageDialog(frame, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        JSlider[] allSliders = {hpSlider, attackSlider, defenseSlider, spAttackSlider, spDefenseSlider, speedSlider};


        addSliderAndTextField(hpSlider, hp, remainingEvsLabel, submit, allSliders);
        addSliderAndTextField(attackSlider, attack, remainingEvsLabel, submit, allSliders);
        addSliderAndTextField(defenseSlider,defense , remainingEvsLabel, submit, allSliders);
        addSliderAndTextField(spAttackSlider,spAttack , remainingEvsLabel, submit, allSliders);
        addSliderAndTextField(spDefenseSlider,spDefense , remainingEvsLabel, submit, allSliders);
        addSliderAndTextField(speedSlider,speed , remainingEvsLabel, submit, allSliders);


        panel.add(new JLabel("HP EVs: "));
        panel.add(hpSlider);
        panel.add(hp);

        panel.add(new JLabel("Attack Evs: "));
        panel.add(attackSlider);
        panel.add(attack);

        panel.add(new JLabel("Defense EVs: "));
        panel.add(defenseSlider);
        panel.add(defense);

        panel.add(new JLabel("Sp.Attack EVs: "));
        panel.add(spAttackSlider);
        panel.add(spAttack);

        panel.add(new JLabel("SP.Defense Evs: "));
        panel.add(spDefenseSlider);
        panel.add(spDefense);

        panel.add(new JLabel("Speed Evs: "));
        panel.add(speedSlider);
        panel.add(speed);




        panel.add(remainingEvsLabel);
        panel.add(submit);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void addSliderAndTextField(JSlider slider, JTextField textField, JLabel remainingLabel, JButton submit, JSlider... allSliders) {

        ChangeListener changeListener = e -> {

            int value = slider.getValue();

            textField.setText(String.valueOf(value));

            updateTotalEvs(remainingLabel, submit, allSliders);

        };

        slider.addChangeListener(changeListener);

        textField.addActionListener(e -> {

            try {
                int value = Integer.parseInt(textField.getText());
                value = Math.min(value, 252);
                slider.setValue(value);
                updateTotalEvs(remainingLabel, submit, allSliders);
            } catch (NumberFormatException exception) {
                textField.setText("0");
                slider.setValue(0);
                updateTotalEvs(remainingLabel, submit, allSliders);
            }
        });

    }

    private static void updateTotalEvs(JLabel remainingLabel, JButton submit, JSlider... sliders) {

        int totalEvs = 0;
        boolean validEvs = true;

        for (JSlider slider : sliders) {

            totalEvs += slider.getValue();

            if (slider.getValue() > 252) {
                validEvs = false;
                break;
            }
        }


        int remainingEvs = 508 - totalEvs;
        remainingLabel.setText("Remaining Evs: " + remainingEvs);

        if (remainingEvs < 0 || !validEvs) {
            remainingLabel.setForeground(Color.RED);
            submit.setEnabled(false);
        } else {
            remainingLabel.setForeground(Color.BLUE);
            submit.setEnabled(true);
        }

    }


    private static JSlider createSlider() {
        JSlider slider = new JSlider(0, 252, 0);

        slider.setBackground(Color.LIGHT_GRAY);
        slider.setPaintLabels(true);

        return slider;
    }
}
 */


