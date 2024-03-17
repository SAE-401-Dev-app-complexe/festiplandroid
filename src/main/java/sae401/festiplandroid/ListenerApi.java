package sae401.festiplandroid;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Gére les résultats d'un appel d'api. Utiliser avec apiManager.
 */
public interface ListenerApi {

    /**
     * Réponse positive de l'api. Ajoutées la logique lors du succés de l'appel
     * @param reponseApi la réponse de l'api, null si l'appel n'était pas en GET
     */
    void onReponsePositive(JSONArray reponseApi);

    /**
     * Réponse erreur de l'api. Ajoutées la logique lors de l'erreur de l'appel
     * @param erreur le message d'erreur envoyé par l'appel
     */
    abstract void onReponseErreur(String erreur);
}
