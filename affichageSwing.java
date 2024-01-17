import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class affichageSwing extends JPanel {
    miniNeuralNetwork NN;
    public affichageSwing(miniNeuralNetwork NN){
        this.NN = NN;

        JTextField tauxLearn = new JTextField(NN.getTauxApprentissage()+"");
        tauxLearn.setBounds(150,175, 80,30);
        tauxLearn.addActionListener(e -> {
            NN.setTauxApprentissage(Double.parseDouble(tauxLearn.getText()));
            affichageSwing.this.repaint();
        });
        this.add(tauxLearn, BorderLayout.EAST);

        JButton apprentissage = new JButton("Apprentissage");
        apprentissage.setPreferredSize(new Dimension(200,30));
        apprentissage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NN.apprentissage();
                affichageSwing.this.repaint();
            }
        });
        this.add(apprentissage, BorderLayout.EAST);

        JButton apprentissageRapide = new JButton("Apprentissage (x500)");
        apprentissageRapide.setPreferredSize(new Dimension(200,30));
        apprentissageRapide.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Timer timer = new Timer(25, new ActionListener() {
                    private int count = 0;
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (count < 500) {
                            NN.apprentissage();
                            affichageSwing.this.repaint();
                            count++;
                        } else {
                            ((Timer) e.getSource()).stop();
                        }
                    }
                });
                timer.start();
            }
        });
        this.add(apprentissageRapide, BorderLayout.EAST);

        JButton reinit = new JButton("Reinitialisation");
        reinit.setPreferredSize(new Dimension(200,30));
        reinit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NN.reinitialisation();
                affichageSwing.this.repaint();
            }
        });
        this.add(reinit, BorderLayout.NORTH);

        JButton propagate = new JButton("Propagate");
        propagate.setPreferredSize(new Dimension(200,30));
        propagate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NN.propagationNN();
                affichageSwing.this.repaint();
            }
        });
        this.add(propagate, BorderLayout.NORTH);

        JButton retropropagate = new JButton("Retro-Propagate");
        retropropagate.setPreferredSize(new Dimension(200,30));
        retropropagate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NN.retroPropagation();
                affichageSwing.this.repaint();
            }
        });
        this.add(retropropagate, BorderLayout.NORTH);

        JButton test = new JButton("test");
        test.setPreferredSize(new Dimension(200,30));
        test.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                neuronLayer nouveau = new neuronLayer(4);
                neuronLayer resultat = new neuronLayer(2);
                nouveau.getTab()[0] = 0;
                nouveau.getTab()[1] = 1;
                nouveau.getTab()[2] = 0;
                nouveau.getTab()[3] = 1;
                NN.prediction(nouveau, resultat);
                nouveau.affichage();
                resultat.affichage();
                affichageSwing.this.repaint();
            }
        });
        this.add(test, BorderLayout.NORTH);

        this.champsNeurones();
    }

    public void champsNeurones(){
        JTextField valNeurone1 = new JTextField(this.NN.input.getTab()[0]+"");
        valNeurone1.setBounds(150,175, 40,30);
        valNeurone1.addActionListener(e -> {
            this.NN.input.getTab()[0] = Double.parseDouble(valNeurone1.getText());
            affichageSwing.this.repaint();
        });
        this.add(valNeurone1);

        JTextField valNeurone2 = new JTextField(this.NN.input.getTab()[1]+"");
        valNeurone2.setBounds(150,325, 40,30);
        valNeurone2.addActionListener(e -> {
            this.NN.input.getTab()[1] = Double.parseDouble(valNeurone2.getText());
            affichageSwing.this.repaint();
        });
        this.add(valNeurone2);

        JTextField valNeurone3 = new JTextField(this.NN.input.getTab()[2]+"");
        valNeurone3.setBounds(150,475, 40,30);
        valNeurone3.addActionListener(e -> {
            this.NN.input.getTab()[2] = Double.parseDouble(valNeurone3.getText());
            affichageSwing.this.repaint();
        });
        this.add(valNeurone3);

        JTextField valNeurone4 = new JTextField(this.NN.input.getTab()[3]+"");
        valNeurone4.setBounds(150,625, 40,30);
        valNeurone4.addActionListener(e -> {
            this.NN.input.getTab()[3] = Double.parseDouble(valNeurone4.getText());
            affichageSwing.this.repaint();
        });
        this.add(valNeurone4);

        JTextField valNeurone1Cible = new JTextField(this.NN.cible.getTab()[0]+"");
        valNeurone1Cible.setBounds(150,625, 40,30);
        valNeurone1Cible.addActionListener(e -> {
            this.NN.cible.getTab()[0] = Double.parseDouble(valNeurone1Cible.getText());
            affichageSwing.this.repaint();
        });
        this.add(valNeurone1Cible);

        JTextField valNeurone2Cible = new JTextField(this.NN.cible.getTab()[1]+"");
        valNeurone2Cible.setBounds(150,625, 40,30);
        valNeurone2Cible.addActionListener(e -> {
            this.NN.cible.getTab()[1] = Double.parseDouble(valNeurone2Cible.getText());
            affichageSwing.this.repaint();
        });
        this.add(valNeurone2Cible);
    }

    public void paintComponent(Graphics g1){
        Graphics2D g = (Graphics2D) g1;
        super.paintComponent(g);
        g.setStroke(new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
        DecimalFormat f = new DecimalFormat();

        int y = 150;
        int tailleCercle = 80;
        for (int i=0; i<this.NN.getNbNeuronesEntree(); i++){
            g.setColor(Color.BLACK);
            g.drawOval(200, y+i*150, tailleCercle, tailleCercle);
            g.drawString(""+this.NN.input.getTab()[i], 230, (y+45)+i*150);
        }

        for (int i=0; i<this.NN.getNbNeuronesHidden(); i++){
            g.setStroke(new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
            g.setColor(Color.BLACK);
            g.drawOval(650, y+i*150, tailleCercle, tailleCercle);
            f.setMaximumFractionDigits(6);
            g.drawString(""+f.format(this.NN.hidden.getTab()[i]), 660, (y+45)+i*150);
            for (int j=0; j<this.NN.getNbNeuronesEntree(); j++) {
                g.setColor(Color.BLUE);
                if (this.NN.getValeurPoidsSynaptique(i,j,0) > 0){
                    g.setStroke(new BasicStroke((float) (1*Math.pow(3,this.NN.getValeurPoidsSynaptique(i,j,0))), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
                }
                else{
                    g.setStroke(new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
                }
                g.drawLine(280, (y + 45) + j * 150, 650, (y + 45) + i * 150 );
                f.setMaximumFractionDigits(4);
                g.drawString("N1 : "+j+" | N2 : "+i+" | "+ f.format(this.NN.getValeurPoidsSynaptique(i,j,0)), 280, (y + i*45) + j * 150);
            }

        }
        y=300;
        for (int i=0; i<this.NN.getNbNeuronesSortie(); i++){
            g.setStroke(new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
            g.setColor(Color.BLACK);
            g.drawOval(1100, y+i*150, tailleCercle, tailleCercle);
            f.setMaximumFractionDigits(6);
            g.drawString(""+f.format(this.NN.output.getTab()[i]), 1110, (y+45)+i*150);
            for (int j=0; j<this.NN.getNbNeuronesHidden(); j++) {
                g.setColor(Color.BLUE);
                if (this.NN.getValeurPoidsSynaptique(i,j,1) > 0){
                    g.setStroke(new BasicStroke((float) (1*Math.pow(3,this.NN.getValeurPoidsSynaptique(i,j,1))), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
                }
                else{
                    g.setStroke(new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
                }
                g.drawLine(730, (y-150 + 45) + j * 150, 1100,(y + 45) + i * 150  );
                f.setMaximumFractionDigits(4);
                g.drawString("N1 : "+j+" | N2 : "+i+" | "+ f.format(this.NN.getValeurPoidsSynaptique(i,j,1)), 730, (y - 125 + i*45) + j * 150);
            }
        }

    }
}
