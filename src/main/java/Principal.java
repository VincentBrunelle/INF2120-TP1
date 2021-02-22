import org.w3c.dom.Text;

import java.util.Scanner;

/**
 * Écrivez vos nom ici :
 *
 * @nom
 * @code_permanent
 *
 * @nom
 * @code_permanent
 */

public class Principal {
    /**
     * Demande le nom du fichier dans lequel la suite de syllabe sera lu.
     *
     * @param scanner Indique l'endroit où le nom du fichier sera lu.
     * @return une chaîne de caractères contenant le nom du fichier.
     */
    public static String demanderNomFichier( Scanner scanner ) {
        String resultat = "";

        System.out.print( Textes.MSSG_DEMANDE_NOMFICHIER );
        resultat = scanner.nextLine();

        return resultat;
    }

    /**
     * Demande le nombre de syllabe cible que le logiciel doit utiliser pour faire la réduction.
     *
     * Cette valeur doit être plus grande ou égal à {@code Constantes.MIN_NOMBRE_SYLLABE}.
     *
     * @see Constantes
     * @param scanner  Indique l'endroit où la valeur sera lu.
     * @return l'entier lu dans le {@code Scanner}.
     */
    public static int demanderNombreDeSyllabe( Scanner scanner ) {
        int resultat = 0;

        System.out.print( Textes.MSSG_DEMANDE_NOMBRE_SYLLABE );
        resultat = scanner.nextInt();

        if( resultat < Constantes.MIN_NOMBRE_SYLLABE ) {
            Erreur.NOMBRE_SYLLABE.lancer( "  Valeur entrée : " + resultat );
        }

        return resultat;
    }


    /**
     * Programme principal de l'application
     *
     * @param args Les paramètres externe de l'application.
     */
    public static void main( String [] args ) {
        // cette partie du code lie les entrées.
        Scanner scanner = new Scanner( System.in );
        String nomFichier = demanderNomFichier( scanner );
        int nombreDeSyllabes = demanderNombreDeSyllabe( scanner );

        scanner.close();

        TexteSonore texteSonore = new TexteSonore( nomFichier );

        for (int i = 0; i < texteSonore.size() - 1 ; i++) {
            for (int j = i +1; j < texteSonore.size(); j++) {
                if (texteSonore.get(i).equals(texteSonore.get(j))){
                    texteSonore.get(i).setCompteur(texteSonore.get(i).getCompteur() + 1);
                    texteSonore.remove(j);
                }
            }
        }

        /*---------------------------------------------------------------------------------------------------*/

        SyllabeFrancais s = texteSonore.get(0);
        for (int i = 0; i < texteSonore.size(); i++) {
            if (s.equals(texteSonore.get(i))){
                System.out.println("oui!");
            } else {
                System.out.println("non");
            }
        }



        /*---------------------------------------------------------------------------------------------------*/
        // placer vos actions ici :


        // cette partie du code affiche les résultats, modifier au besoin.
        System.out.println( texteSonore );
        for (int i = 0; i < texteSonore.size(); i++) {
            System.out.println(texteSonore.get(i).getCompteur());
        }
        System.out.println( nombreDeSyllabes );
    }
}
