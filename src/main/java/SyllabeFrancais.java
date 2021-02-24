import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;


/**
 * Représente l'unité de base pour la prononciation en français.
 *
 * Une syllabe est composé d'un groupe de syllabe (le noyau).
 * optionnelement, elle peu avoir deux groupes de consonne.
 * Le groupe de consonne avant le noyau est l'attaque de la syllabe.
 * Le groupe de consonne après le noyau est le coda de la syllabe.
 *
 * @see ConsonneFrancais
 * @see VoyelleFrancais
 * @see <a href="https://fr.wiktionary.org/wiki/Annexe:Prononciation/fran%C3%A7ais">référence</a>
 */
public class SyllabeFrancais {
    /**
     * Le groupe de consonne pour l'attaque de la syllabe.  S'il n'est pas présent, alors la valeur est à {@code null}.
     */
    protected ConsonneFrancais attaque = null;

    /**
     * Le groupe de voyelle pour la syllabe.  Ne doit pas être {@code null}.-
     */
    protected VoyelleFrancais noyau;

    /**
     * Le groupe de consonne pour le code de la syllabe.  S'il n'est pas présent, alors la valeur est à {@code null}.
     */
    protected ConsonneFrancais coda = null;

    /**
     * Le nombre d'occurence d'un élément SyllabeFrancais.
     */
    protected int compteur = 1;


    /**
     * Construit une syllabe avec un noyau seulement.
     *
     * @param noyau le groupe de voyelle utilisé pour la syllabe.  Ne doit pas être {@code null}.
     */
    public SyllabeFrancais( VoyelleFrancais noyau) {
        this.noyau = noyau;
    }


    /**
     * Construit une syllabe avec une attaque et un noyau.
     *
     * @param attaque le groupe de consonne utilisé pour la syllabe.
     * @param noyau le groupe de voyelle utilisé pour la syllabe.  Ne doit pas être {@code null}.
     */
    public SyllabeFrancais( ConsonneFrancais attaque, VoyelleFrancais noyau) {
        this.attaque = attaque;
        this.noyau = noyau;
    }


    /**
     * Construit une syllabe avec une attaque, un noyau et un coda.
     *
     * @param attaque le groupe de consonne utilisé pour la syllabe.
     * @param noyau le groupe de voyelle utilisé pour la syllabe.  Ne doit pas être {@code null}.
     * @param coda le groupe de consonne utilisé pour la syllabe.
     */
    public SyllabeFrancais(ConsonneFrancais attaque, VoyelleFrancais noyau, ConsonneFrancais coda ) {
        this.attaque = attaque;
        this.noyau = noyau;
        this.coda = coda;
    }


    /**
     * Construit une syllabe avec un noyau et un coda.
     *
     * @param noyau le groupe de voyelle utilisé pour la syllabe.  Ne doit pas être {@code null}.
     * @param coda le groupe de consonne utilisé pour la syllabe.
     */
    public SyllabeFrancais(VoyelleFrancais noyau, ConsonneFrancais coda ) {
        this.noyau = noyau;
        this.coda = coda;
    }


    /**
     * Lit une syllabe dans le {@code Scanner}.
     *
     * Cherche possiblement un groupe de consonne qui servira d'attaque, ensuite un groupe de voyelle qui
     * servira de noyau et finalement un autre groupe de consonne pour le coda.
     *
     * @param scanner le {@code Scanner} dans lequel la lecture est effectué.
     * @return la voyelle lu.
     * @exception NoSuchElementException s'il n'y a pas de {@code SyllabeFrancais} valide.
     * @exception IllegalStateException si le {@code Scanner} est fermé.
     */
    public static SyllabeFrancais lire( Scanner scanner ) {
        ConsonneFrancais attaque = null;
        VoyelleFrancais noyau;
        ConsonneFrancais coda = null;

        try {
            attaque = ConsonneFrancais.lire( scanner );
        } catch ( NoSuchElementException e ) {
        }

        noyau = VoyelleFrancais.lire( scanner );

        try {
            coda = ConsonneFrancais.lire( scanner );
        } catch ( NoSuchElementException e ) {
        }


        return new SyllabeFrancais( attaque, noyau, coda );
    }


    /**
     * Vérifie si deux instance SyllabeFrancais sont égales.
     * @param o l'instance à comparer.
     * @return true si les deux instances sont égales.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SyllabeFrancais that = (SyllabeFrancais) o;
        return Objects.equals(attaque, that.attaque) && Objects.equals(noyau, that.noyau) && Objects.equals(coda, that.coda);
    }


    /**
     * retourne une chaîne de caractère composée des phonèmes de la syllabe.
     *
     * @return la chaîne contenant les symboles des phonèmes de la syllabe.
     */
    @Override
    public String toString() {
        return "" + ( null == attaque ? "" : attaque )
                + noyau
                + ( null == coda ? "" : coda );
    }

    /**
     * retourne le nombre d'occurence d'un élément SyllabeFrancais.
     * @return le nombre d'occurence.
     */
    public int getCompteur() {
        return compteur;
    }

    /**
     * modifie le nombre d'occurence d'un élément SyllabeFrancais.
     * @param compteur la valeur a affecter.
     */
    public void setCompteur(int compteur) {
        this.compteur = compteur;
    }


    /**
     * Calcule la distance entre les attaques de deux SyllabeFrancais.
     * @param syllabe le SyllabeFrancias à comparer.
     * @return la distance entre les noyaux des deux instances.
     */
    public int calculerDistanceAttaque (SyllabeFrancais syllabe) {
        int distance = 0;
        if ((attaque == null ^ syllabe.attaque == null)) {
            distance = 12;
        } else if (attaque == null && syllabe.attaque == null) {
        } else {
            distance = attaque.calculerDistanceConsonne(syllabe.attaque);
        }
        return distance;
    }


    /**
     * Calcule la distance entre les noyaux de deux SyllabeFrancais.
     * @param syllabe le SyllabeFrancias à comparer.
     * @return la distance entre les noyaux des deux instances.
     */
    public int calculerDistanceNoyau (SyllabeFrancais syllabe) {
        return noyau.calculerDistanceVoyelle(syllabe.noyau);
    }


    /**
     * Calcule la distance entre les codas de deux SyllabeFrancais.
     * @param syllabe le SyllabeFrancias à comparer.
     * @return la distance entre les codas des deux instances.
     */
    public int calculerDistanceCoda (SyllabeFrancais syllabe) {
        int distance = 0;
        if ((coda == null ^ syllabe.coda == null)) {
            distance = 12;
        } else if (coda == null && syllabe.coda == null) {
        } else {
            distance = coda.calculerDistanceConsonne(syllabe.coda);
        }
        return distance;
    }


    /**
     * Calcule la distance entre les deux SyllabeFrancais.
     * @param syllabe le SyllabeFrancias à comparer.
     * @return la distance entre les deux instances.
     */
    public int calculerDistanceSyllabe (SyllabeFrancais syllabe) {
        int distance = 0;
        distance = calculerDistanceAttaque(syllabe) +
                calculerDistanceNoyau(syllabe) +
                calculerDistanceCoda(syllabe);
        return distance;
    }
}
