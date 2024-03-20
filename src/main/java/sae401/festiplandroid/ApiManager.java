/*
 * ApiManager.java                                       18 mar. 2024
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */
package sae401.festiplandroid;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Gestion de la logique des appels d'API.
 *
 * @author Enzo Cluzel
 * @author Lucas Descriaud
 * @author Loïc Faugières
 * @author Simon Guiraud
 */
public class ApiManager {

    public final static int TIMEOUT_MS = 5000;

    public final static String AUCUNE_CONNEXION
    = "Aucune connexion internet n'est détectée !";

    public final static String CONNEXION_ECHOUEE
    = "La connexion à l'API a échoué !\nVeuillez vérifier votre connexion"
      + " internet ou réessayer plus tard.";

    public final static String ERR_MAUVAISE_REQUETE
    = "La requête à l'API est incorrecte !\nLes développeurs règleront ce soucis" +
      " au plus vite.\nCode : %s\nDétails : %s";

    private static RequestQueue fileRequete;

    private static String cleApi;

    /**
     * Récupère la file de requête pour les appels API.
     * @param app L'activité appelante
     * @return La file de requête
     */
    private static RequestQueue getFileRequete(AppCompatActivity app) {
        if (fileRequete == null) {
            fileRequete = Volley.newRequestQueue(app);
        }
        return fileRequete;
    }

    /**
     * Appel à l'API attendant un objet dont l'url est passée en paramètre
     * via une requête dont la méthode est passée en paramètre.
     *
     * @param url L'url de l'API.
     * @param app L'activité appelante.
     * @param resultat L'interface de réponse à l'API.
     * @param donnees Les données à envoyer à l'API.
     * @param methode La méthode de la requête.
     */
    public static void appelApiObjet(String url, AppCompatActivity app,
                                     ListenerApi resultat, JSONObject donnees,
                                     int methode) {
        JsonObjectRequest requeteVolley;

        if (reseauDisponible(app)) {
            requeteVolley = new JsonObjectRequest(methode, url, donnees,
                reponse -> resultat.onReponsePositive(reponse),
                erreur -> gestionErreur(erreur, resultat)
            ) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<String, String>();
                    if (cleApi != null)
                        params.put("APIKEY", cleApi);
                    return params;
                }
            };

            requeteVolley.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            getFileRequete(app).add(requeteVolley);
        } else {
            resultat.onReponseErreur(AUCUNE_CONNEXION);
        }
    }

    /**
     * Appel à l'API attendant un tableau dont l'url est passée en paramètre
     * via une requête dont la méthode est passée en paramètre.
     *
     * @param url L'url de l'API.
     * @param app L'activité appelante.
     * @param resultat L'interface de réponse à l'API.
     * @param donnees Les données à envoyer à l'API.
     * @param methode La méthode de la requête.
     */
    public static void appelApiArray(String url, AppCompatActivity app,
                                     ListenerApi resultat, JSONArray donnees,
                                     int methode) {
        JsonArrayRequest requeteVolley;

        if (reseauDisponible(app)) {
            requeteVolley = new JsonArrayRequest(methode, url, donnees,
                reponse -> resultat.onReponsePositive(reponse),
                erreur -> gestionErreur(erreur, resultat)
            ) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<String, String>();
                    if (cleApi != null)
                        params.put("APIKEY", cleApi);
                    return params;
                }
            };

            requeteVolley.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            getFileRequete(app).add(requeteVolley);
        } else {
            resultat.onReponseErreur(AUCUNE_CONNEXION);
        }
    }

    /**
     * Gestion d'une erreur volley d'appel à l'API.
     * @param erreur L'erreur volley.
     * @param resultat L'interface de réponse à l'API.
     */
    private static void gestionErreur(VolleyError erreur, ListenerApi resultat) {
        boolean erreurPrevue = false;
        String messageErreur;

        try {
            String reponse = new String(erreur.networkResponse.data, "utf-8");
            System.out.println("Erreur renvoyée par l'API : " + reponse);

            JSONObject donnees = new JSONObject(reponse);

            String error = donnees.getString("error");
            String details = donnees.getString("details");

            erreurPrevue = true;
            messageErreur = String.format(ERR_MAUVAISE_REQUETE, error, details);

            resultat.onReponseErreur(messageErreur);
        } catch (Exception e) {
            System.out.println("Erreur de volley : " + erreur);
        }

        if (!erreurPrevue)
            resultat.onReponseErreur(CONNEXION_ECHOUEE);
    }

    /**
     * Vérifie qu'une connexion internet est disponible.
     *
     * @param app L'activité appelante.
     * @return true si une connexion internet est disponible sinon false.
     */
    private static boolean reseauDisponible(AppCompatActivity app) {
        try {
            ConnectivityManager gestionnaireConnexion =
                (ConnectivityManager) app.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo informationReseau = gestionnaireConnexion.getActiveNetworkInfo();

            if (informationReseau == null || !informationReseau.isConnected()) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Définit la clé API pour les appels à l'API.
     * @param cleApi La clé API.
     */
    public static void setCleApi(String cleApi) {
        ApiManager.cleApi = cleApi;
    }

    public static String getCle() {
        return cleApi;
    }
}
