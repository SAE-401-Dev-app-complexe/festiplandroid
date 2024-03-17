package sae401.festiplandroid;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Gére la logique sur les appels d'api.
 * Utiliser cette classe pour faire des appels d'api.
 * Les url ne seront pas complétées alors les valeurs devront être encodées avant un appel d'api.
 * Exemples : https://geo.api.gouv.fr/regions/12 non https://geo.api.gouv.fr/regions/%e
 */
public class ApiManager   {

    public final static String APPEL_REUSSI = "L'appel est un succés";

    public final static String APPEL_NON_REUSSI = "L'appel n'a pas fonctionné";
    private static RequestQueue fileRequete;


    private static RequestQueue getFileRequete(AppCompatActivity app) {
        if (fileRequete == null) {
            fileRequete = Volley.newRequestQueue(app);
        }
        return fileRequete;
    }

    /**
     * Appel Api en GET. Récupére les données de l'appel sur l'url
     *
     * @param url la requete demandé. Url compléte
     * @param app L'appelant de la méthode
     * @param reponseApi Le listener de l'api à définir
     */
    public static void appelApiGet(String url,AppCompatActivity app,ListenerApi reponseApi) {
        if (reseauDisponible(app)) {
            JsonArrayRequest requeteVolley = new JsonArrayRequest(Request.Method.GET, url,
                    null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    reponseApi.onReponsePositive(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError erreur) {
                    reponseApi.onReponseErreur(erreur.toString());
                }
            });
            ApiManager.getFileRequete(app).add(requeteVolley);

        }else {
            String erreur = String.valueOf(R.string.connexion_erreur_appel_api);
            reponseApi.onReponseErreur(erreur);
        }
    }

    /**
     * Vérifie que une connexion internet est disponible
     * @param app
     * @return true si une connexion internet est disponible sinon false
     */
    private static boolean reseauDisponible(AppCompatActivity app) {
        ConnectivityManager gestionnaireConnexion =
                (ConnectivityManager) app.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo informationReseau = gestionnaireConnexion.getActiveNetworkInfo();
        if (informationReseau == null || ! informationReseau.isConnected()) {
            return false;
        }
        return  true;
    }
}
