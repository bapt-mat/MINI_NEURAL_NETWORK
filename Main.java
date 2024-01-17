import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        double[] valeurInput = new double[4];
        valeurInput[0] = 0;
        valeurInput[1] = 1;
        valeurInput[2] = 0;
        valeurInput[3] = 1;

        double[] valeurSortie = new double[2];
        valeurSortie[0] = 0;
        valeurSortie[1] = 1;

        miniNeuralNetwork NN = new miniNeuralNetwork(valeurInput, valeurSortie, 0.2);

        //panneau SWING
        JFrame fenetre = new JFrame("Mini réseau de neurones");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setPreferredSize(new Dimension(1400,800));
        fenetre.setLocation(100,50);

        affichageSwing panneau = new affichageSwing(NN);
        panneau.setPreferredSize(new Dimension(1400,800));
        panneau.setBackground(Color.GRAY);

        fenetre.getContentPane().add(panneau, BorderLayout.SOUTH);
        fenetre.setResizable(false);
        fenetre.pack();
        fenetre.setVisible(true);
    }
}