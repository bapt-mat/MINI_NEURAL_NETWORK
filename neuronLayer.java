import java.util.Arrays;

public class neuronLayer {
    private final double[] tab;
    public neuronLayer(int taille){
        tab = new double[taille];
    }
    public double[] getTab(){
        return this.tab;
    }
    public void setTab(double[] newTab){
        System.arraycopy(newTab, 0, this.tab, 0, newTab.length);
    }
    public void initialisation(double valeur){
        Arrays.fill(this.tab, valeur);
    }
    public void affichage(){
        for (double i : this.tab) {
            System.out.print(i + "|");
        }
        System.out.println();
    }

    /**
     * Propagation des valeurs de la couche A(this) a la couche B en appliquant les poids de chaque connexion entre les neurones
     * @param layerB couche suivante
     * @param poids tableau des poids des connexions entre les neurones de A(this) et ceux de B(couche suivante)
    */
    public double[] propagation(neuronLayer layerB, double[][] poids){
        /*
         * Somme cumulée pour chaque neurone de la couche B
         */
        double[] sommeLayer = new double[layerB.getTab().length];
        /*
        * Applique le poids de chaque connexion entre chaque neurone de la couche A (this) et la couche B
        */
        for (int i=0; i<layerB.getTab().length; i++){
            for (int j=0; j<this.getTab().length; j++){
                sommeLayer[i] += poids[i][j] * this.tab[j];
            }
        }
        return sommeLayer;
    }

    /**
    * Fonction d'activation
    */
    public double sigmoid(double x){
        return 1/(1+Math.pow(Math.E, (-1 * x)));
    }

    public double tanh(double x) {
        return Math.tanh(x);
    }

    /**
     * Fonction d'activation d'une couche de neurones
     * @param sommeCouche somme des poids de la connexion entre chaque neurone précédent et chaque neurone de la couche
     */
    public void activation(double[] sommeCouche){
        /*
        * Applique la fonction sigmoide sur chaque neurone pour avoir la valeur de sortie
        */
        for (int i=0; i<this.tab.length; i++) {
            this.tab[i] = this.sigmoid(sommeCouche[i]);
        }
    }
}
