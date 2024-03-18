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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

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

    private static RequestQueue fileRequete;

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
     * Appel à l'API en méthode GET.
     *
     * @param url L'url de l'API appelée.
     * @param app L'activité appelante.
     * @param reponseApi La réponse de l'API nécessaire pour la suite du programme.
     */
    public static void appelApiGet(String url, AppCompatActivity app,
                                   ListenerApi reponseApi) {
        JsonArrayRequest requeteVolley;

        if (reseauDisponible(app)) {
            requeteVolley = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    reponseApi.onReponsePositive(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError erreur) {
                    reponseApi.onReponseErreur(CONNEXION_ECHOUEE);
                }
            });

            requeteVolley.setRetryPolicy(new DefaultRetryPolicy(
                    TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            getFileRequete(app).add(requeteVolley);
        } else {
            reponseApi.onReponseErreur(AUCUNE_CONNEXION);
        }
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
}
