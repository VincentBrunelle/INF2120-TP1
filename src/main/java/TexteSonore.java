import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;


/**
 * Une classe pour contenir une suite de syllabe.
 *
 * Cette classe permet la gestion d'une suite de syllabe.
 *
 * @see SyllabeFrancais
 */
public class TexteSonore extends ArrayList< SyllabeFrancais > {
    /**
     * Le caractère utilisé pour séparé les syllabes lors de la lecture et de l'écriture.
     */
    public static final String SEPARATEUR = ".";


    public static final int DISTANCE_MINIMALE = 43;

    /**
     * Construit une suite de syllabe vide.
     */
    public TexteSonore() {}

    /**
     * Construit une suite de syllabes à partir du contenu d'un fichier.
     *
     * @param nomFichier Le nom du fichier qui contient la suite de syllabes.
     */
    public TexteSonore( String nomFichier ) {
        File fichier = new File( nomFichier );
        Scanner scanner = null;

        try{
            scanner = new Scanner( fichier );
        } catch( FileNotFoundException e ) {
            Erreur.FICHIER_INEXISTANT.lancer( "\"" + nomFichier +"\"" );
        }

        scanner.useDelimiter( "" );
        lire( scanner );
        scanner.close();
    }


    /**
     * Lit une suite de syllabe dans le {@code Scanner}.
     *
     * Consulte le {@code Scanner} pour lire une suite de syllabe séparé par le caractère {@code SEPARATEUR}.
     *
     * @param scanner le {@code Scanner} dans lequel la lecture est effectué.
     * @return le groupe de consonne lu.
     * @exception NoSuchElementException s'il n'y a pas de {@code API_Consonne} valide.
     * @exception IllegalStateException si le {@code Scanner} est fermé.
     */
    private void lire( Scanner scanner ) {
        try{
            while( scanner.hasNext() ) {
                add( SyllabeFrancais.lire( scanner ) );
                scanner.next( SEPARATEUR );
            }
        } catch( NoSuchElementException e ) {
        }
    }


    /**
     * Construit une chaîne de caractères contenant la suite de syllabe représenté par les symboles de l'API.
     *
     * @return la chaîne construite.  S'il n'y a pas de syllabe dans la suite, alors la chaîne sera vide.
     */
    @Override
    public String toString() {
        return stream().map( SyllabeFrancais::toString ).collect( Collectors.joining( SEPARATEUR ) );
    }

    public TexteSonore reduire () {
        for (int i = 0; i < this.size(); i++) {
            for (int j = i +1; j < this.size(); j++) {
                if (this.get(i).equals(this.get(j))){
                    this.get(i).setCompteur(this.get(i).getCompteur() + 1);
                    this.remove(j);
                }
            }
        }
        return this;
    }

    public int calculerNombreDeSons () {
        int nombreDeSons = 0;
        ArrayList<SyllabeFrancais> nombreSyllabeDifferentes = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            if (!nombreSyllabeDifferentes.contains(get(i))) {
                nombreSyllabeDifferentes.add(get(i));
            }
        }
return nombreSyllabeDifferentes.size();
    }

    public SyllabeFrancais[] trouverSyllabeForteFaible () {
        SyllabeFrancais [] syllabes = new SyllabeFrancais[2];
        int distance = DISTANCE_MINIMALE;
        syllabes[0] = get(0);
        syllabes[1] = get(1);
        for (int i = 0; i < size(); i++) {
            for (int j = i + 1; j < size(); j++) {
                if (!(get(i).equals(get(j))) && get(i).calculerDistanceSyllabe(get(j)) < distance) {
                    distance = get(i).calculerDistanceSyllabe(get(j));
                    syllabes[0] = get(i);
                    syllabes[1] = get(j);
                }
            }

        }
        return syllabes;
    }

    public SyllabeFrancais[] trierSyllabeParOccurence (SyllabeFrancais [] syllabes) {
        int compteSyllabe0 = 0;
        int compteSyllabe1 = 0;
        for (int i = 0; i < size(); i++) {
            if (get(i).equals(syllabes[0])) {
                compteSyllabe0++;
            } else if (get(i).equals(syllabes[1])) {
                compteSyllabe1++;
            }
        }
        if (compteSyllabe0 < compteSyllabe1) {
            SyllabeFrancais syllabeTemp = syllabes[0];
            syllabes[0] = syllabes[1];
            syllabes[1] = syllabeTemp;
        }
            return syllabes;
    }

    public void remplacerSyllabe (SyllabeFrancais [] syllabes) {
        for (int i= 0; i < size() ; i++) {
            if (get(i).equals(syllabes[0])) {
                set(i, syllabes[1]);
            }
        }
    }
}
