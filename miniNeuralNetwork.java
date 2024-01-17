public class miniNeuralNetwork {
    private final int nbNeuronesEntree = 4;
    private final int nbNeuronesHidden = 4;
    private final int nbNeuronesSortie = 2;
    /**
     * Couches de neurones
     */
    neuronLayer input = new neuronLayer(nbNeuronesEntree);
    neuronLayer hidden = new neuronLayer(nbNeuronesHidden);
    neuronLayer output = new neuronLayer(nbNeuronesSortie);

    /**
     * Tableaux de poids des connexions
     */
    private double[][] poidsHidden;
    private double[][] poidsOutput;
    /**
     * Tableaux pour stocker les somme des poids sur chaque neurone
     */
    private double[] sommeHidden;
    private double[] sommeOutput;
    /**
     * Taux d'apprentissage et tableau cible de la valeur de sortie
     */
    private double tauxApprentissage;
    neuronLayer cible = new neuronLayer(nbNeuronesSortie);
    /**
     * Tableau de stockage du taux d'erreur entre les output reçus et les output attendus
     */
    private double[] erreur;
    /**
     * Tableaux des valeurs des gradients d'erreurs
     */
    private double[][] gradErreurHidden;
    private double[][] gradErreurOutput;

    /**
     * Accesseurs
     */
    public int getNbNeuronesEntree(){
        return this.nbNeuronesEntree;
    }
    public int getNbNeuronesHidden(){
        return this.nbNeuronesHidden;
    }
    public int getNbNeuronesSortie(){
        return this.nbNeuronesSortie;
    }
    public double getValeurPoidsSynaptique(int i, int j, int couche){
        if (couche == 0){
            return this.poidsHidden[i][j];
        }
        else{
            return this.poidsOutput[i][j];
        }
    }

    public double getTauxApprentissage(){
        return this.tauxApprentissage;
    }
    public void setTauxApprentissage(double t){
        this.tauxApprentissage = t;
    }

    /**
     * Initialisation du réseau de neurones et des tableaux nécessaires
     */
    public miniNeuralNetwork(double[] valeurInput, double[] valeurAttenduesOutput, double tauxApprentissage){
        /*
        * Initialisation de la couche de neurones d'entrée, cachée, et de sortie
        */
        input.setTab(valeurInput);
        hidden.initialisation(0);
        output.initialisation(0);

        /*
        * Initialisation du tableau de poids synaptiques entre la couche d'entrée et la couche cachée
        */
        poidsHidden = new double[nbNeuronesHidden][nbNeuronesEntree];
        for (int i=0; i<nbNeuronesHidden; i++){
            for (int j=0; j<nbNeuronesEntree; j++){
                //A VERIFIER QUE MATH.RANDOM EST OK CAR LA MOYENNE N'EST PAS NULLE
                poidsHidden[i][j] = Math.random();
            }
        }
        /*
         * Initialisation du tableau de poids synaptiques entre la couche cachée et la couche de sortie
         */
        poidsOutput = new double[nbNeuronesSortie][nbNeuronesHidden];
        for (int i=0; i<nbNeuronesSortie; i++){
            for (int j=0; j<nbNeuronesHidden; j++){
                poidsOutput[i][j] = Math.random();
            }
        }

        /*
        * Init du taux d'apprentissage et du tableu de valeurs attendues en sortie
        */
        this.tauxApprentissage = tauxApprentissage;
        cible.setTab(valeurAttenduesOutput);

        /*
        * Init du tableau d'erreur
        */
        erreur = new double[nbNeuronesSortie];
        for (int i=0; i<nbNeuronesSortie; i++){
            erreur[i] = 0;
        }
        /*
        * Init des tableaux des gradients d'erreur
        */
        gradErreurHidden = new double[nbNeuronesHidden][nbNeuronesEntree];
        gradErreurOutput = new double[nbNeuronesSortie][nbNeuronesHidden];

        System.out.println("Apres initialisation de notre réseau de neurones :");
        this.affichage();
    }

    public void reinitialisation(){
        hidden.initialisation(0);
        output.initialisation(0);
        for (int i=0; i<nbNeuronesHidden; i++){
            for (int j=0; j<nbNeuronesEntree; j++){
                //A VERIFIER QUE MATH.RANDOM EST OK CAR LA MOYENNE N'EST PAS NULLE
                poidsHidden[i][j] = Math.random();
            }
        }
        for (int i=0; i<nbNeuronesSortie; i++){
            for (int j=0; j<nbNeuronesHidden; j++){
                poidsOutput[i][j] = Math.random();
            }
        }
        for (int i=0; i<nbNeuronesSortie; i++){
            erreur[i] = 0;
        }
    }

    public void affichage(){
        System.out.println("-- INPUT : --");
        input.affichage();
        System.out.println("-- OUTPUT ATTENDU : --");
        cible.affichage();
        System.out.println("-- HIDDEN : --");
        hidden.affichage();
        System.out.println("-- OUTPUT : --");
        output.affichage();
        System.out.println("-- ERREUR ENTRE OUTPUT ET OUTPUT ATTENDU --");
        for (int i=0; i<nbNeuronesSortie; i++){
            System.out.print(erreur[i]+"|");
        }
        System.out.println("\n-- POIDS HIDDEN : --");
        for (int i=0; i<nbNeuronesHidden; i++){
            for (int j=0; j<nbNeuronesEntree; j++){
                System.out.print(poidsHidden[i][j] + "|");
            }
            System.out.println();
        }
        System.out.println("-- POIDS OUTPUT : --");
        for (int i=0; i<nbNeuronesSortie; i++) {
            for (int j = 0; j < nbNeuronesHidden; j++) {
                System.out.print(poidsOutput[i][j] + "|");
            }
            System.out.println();
        }
    }

    /**
     * Calcule des erreurs entre les valeurs d'output reçus et celles attendues
     */
    public void calculeErreur(neuronLayer coucheSortie, neuronLayer coucheSortieAttendue){
        for (int i=0; i<coucheSortie.getTab().length; i++){
            this.erreur[i] = coucheSortieAttendue.getTab()[i] - coucheSortie.getTab()[i];
        }
    }

    /**
     * Calcule des gradients d'erreurs pour pouvoir corriger les poids synaptiques entre deux couches de neurones
     */
    public void calcGradErreur(){
        /*
         * On commence par calculer les gradients d'erreurs entre la couche cachée et la couche de sortie
         */
        for (int i=0; i<output.getTab().length; i++){
            for (int j=0; j<hidden.getTab().length; j++){
                gradErreurOutput[i][j] = -erreur[i] * output.getTab()[i] * (1 - output.getTab()[i] * hidden.getTab()[j]);
            }
        }
        /*
         * Puis : - on rétropropage l'erreur de sortie jusqu'aux neurones de la couche cachée (proportionnellement à leurs poids synaptiques)
         *        - on calcule les gradients d'erreur de la couche d'entrée vers la couche cachée
         */
        for (int i=0; i<hidden.getTab().length; i++){
            for (int j=0; j<input.getTab().length; j++){
                double e=0;
                for (int k=0; k<output.getTab().length; k++){
                    e += poidsOutput[k][i] * erreur[k];
                    gradErreurHidden[i][j] = -e * hidden.getTab()[i] * (1 - hidden.getTab()[i]) * input.getTab()[j];
                }
            }
        }
    }

    /**
     * Mise a jour des poids synaptiques entre la couche 'couche' et la couche 'couchePrecedente'
     * @param couche
     * @param couchePrecedente
     * @param poids
     * @param gradErreur
     */
    public void majPoidsSynaptiques(neuronLayer couche, neuronLayer couchePrecedente, double[][] poids, double[][] gradErreur){
        for (int i=0; i<couche.getTab().length; i++){
            for (int j=0; j<couchePrecedente.getTab().length; j++){
                poids[i][j] -= tauxApprentissage * gradErreur[i][j];
            }
        }
    }

    /**
     * Algo d'apprentissage
     */
    public void retroPropagation(){
        //Calcule l'erreur entre l'output et l'output attendu (cible)
        this.calculeErreur(output, cible);
        //Calcule le gradient d'erreur des poids synaptiques entre les couches
        this.calcGradErreur();
        //Mise a jour des poids synaptiques
        this.majPoidsSynaptiques(output, hidden, poidsOutput, gradErreurOutput);
        this.majPoidsSynaptiques(hidden, input, poidsHidden, gradErreurHidden);
    }

    /**
     * Propagation des valeurs dans le réseau de neurones
     */
    public void propagationNN(){
        /*
         * Propagation de la couche d'entrée à la couché cachée
         */
        sommeHidden = this.input.propagation(this.hidden, this.poidsHidden);
        /*
         * Application de la fonction d'activation sur les neurones de la couche cachée avec en paramètres la somme des poids des connexions arrivant sur le neurone
         */
        this.hidden.activation(sommeHidden);
        /*
         * Propagation de la couche cachée à la couche de sortie
         */
        sommeOutput = this.hidden.propagation(this.output, this.poidsOutput);
        /*
        * Idem pour la couche de sortie
        */
        this.output.activation(sommeOutput);
    }

    public void apprentissage(){
        for (int k=0; k<50; k++){
            // Exemple 1 : 0101 -> 01
            input.getTab()[0] = 0;
            input.getTab()[1] = 1;
            input.getTab()[2] = 0;
            input.getTab()[3] = 1;

            cible.getTab()[0] = 0;
            cible.getTab()[1] = 1;

            this.propagationNN();
            this.retroPropagation();
            this.affichage();

            // Exemple 2 : 1010 -> 10
            input.getTab()[0] = 1;
            input.getTab()[1] = 0;
            input.getTab()[2] = 1;
            input.getTab()[3] = 0;

            cible.getTab()[0] = 1;
            cible.getTab()[1] = 0;

            this.propagationNN();
            this.retroPropagation();
            this.affichage();
        }
    }

    public void prediction(neuronLayer newInput, neuronLayer res){
        neuronLayer cacher = new neuronLayer(nbNeuronesHidden);
        //Copie de la méthode de propagation

        /*
         * Propagation de la couche d'entrée à la couché cachée
         */
        sommeHidden = newInput.propagation(cacher, this.poidsHidden);
        /*
         * Application de la fonction d'activation sur les neurones de la couche cachée avec en paramètres la somme des poids des connexions arrivant sur le neurone
         */
        cacher.activation(sommeHidden);
        /*
         * Propagation de la couche cachée à la couche de sortie
         */
        sommeOutput = cacher.propagation(res, this.poidsOutput);
        /*
         * Idem pour la couche de sortie
         */
        res.activation(sommeOutput);
    }
}
