import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;


/**
 * Décrit le son d'un groupe de consonne en français.
 *
 * Cette description permet de contenir une ou deux consonnes afin de décrire le son des consonnes dans une syllabe.
 *
 * @see API_Consonne
 * @see SyllabeFrancais
 * @see <a href="https://fr.wiktionary.org/wiki/Annexe:Prononciation/fran%C3%A7ais">référence</a>
 */
public class ConsonneFrancais {
    /**
     * La consonne de base du groupe de consonne.
     * Ne doit pas être {@code null}.
     */
    protected API_Consonne consonne1;

    /**
     * La consonne secondaire du groupe de consonne.
     * La valeur {@code null} est utilisé pour indiquer qu'elle n'est pas présente dans le groupe.
     */
    protected API_Consonne consonne2 = null;


    /**
     * Construit un groupe avec une seule consonne.
     *
     * @param consonne1 La consonne du groupe.  Elle est placé comme consonne de base.  Ne doit pas être {@code null}.
     */
    public ConsonneFrancais( API_Consonne consonne1 ) {
        this.consonne1 = consonne1;
    }


    /**
     * Construit un groupe avec deux consonnes.
     *
     * @param consonne1 La consonne de base du groupe.  Ne doit pas être {@code null}.
     * @param consonne2 La consonne secondaire du groupe.
     */
    public ConsonneFrancais( API_Consonne consonne1, API_Consonne consonne2 ) {
        this.consonne1 = consonne1;
        this.consonne2 = consonne2;
    }



    /**
     * Lit un groupe de consonnes dans le {@code Scanner}.
     *
     * Vérifie si le prochain caractère du {@code scanner} représente une consonne.  Si oui, alors cette consonne
     * deviendra la consonne de base du groupe retourné.
     * Ensuite, vérifie si le prochain caractère représente une consonne.  Si oui, alors cette consonne deviendra la
     * consonne secondaire du groupe retourné.
     *
     * @param scanner le {@code Scanner} dans lequel la lecture est effectué.
     * @return le groupe de consonne lu.
     * @exception NoSuchElementException s'il n'y a pas de {@code API_Consonne} valide.
     * @exception IllegalStateException si le {@code Scanner} est fermé.
     */
    public static ConsonneFrancais lire( Scanner scanner ) {
        ConsonneFrancais resultat = null;
        API_Consonne consonne1 = API_Consonne.lire( scanner );
        API_Consonne consonne2;

        try {
            consonne2 = API_Consonne.lire( scanner );
            resultat = new ConsonneFrancais( consonne1, consonne2 );
        } catch ( NoSuchElementException e ) {
            resultat = new ConsonneFrancais( consonne1 );
        }

        return resultat;
    }


    /**
     * retourne une chaîne de caractère composée des consonnes du groupe.
     *
     * @return la chaîne contenant les symboles des consonnes du groupe.
     */
    @Override
    public String toString() {
        return "" + consonne1 + ( null == consonne2 ? "" : consonne2 );
    }


    /**
     * Vérifie si deux instance ConsonneFrancais sont égales.
     * @param o l'instance à comparer.
     * @return true si les deux instances sont égales.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsonneFrancais that = (ConsonneFrancais) o;
        return consonne1 == that.consonne1 && consonne2 == that.consonne2;
    }


    /**
     * Calcule la distance entre deux groupes de consonnes.
     * @param consonne le groupe de consonnes à comparer.
     * @return la distance entre les deux groupes de consonnes.
     */
    public int calculerDistanceConsonne (ConsonneFrancais consonne) {
        int distance = 0;
        if((consonne2 == null && consonne.consonne2 !=  null) || (consonne2 != null && consonne.consonne2 ==  null)) {
            distance = 6;
        } else if (consonne2 == null && consonne.consonne2 ==  null) {
        } else {
            distance = consonne2.calculerDistanceApiConsonne(consonne.consonne2);
        }
        distance += consonne1.calculerDistanceApiConsonne(consonne.consonne1);
        return distance;
    }
}
