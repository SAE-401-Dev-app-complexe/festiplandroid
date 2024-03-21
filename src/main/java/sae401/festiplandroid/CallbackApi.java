/*
 * CallbackApi.java                                      18 mar. 2024
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */
package sae401.festiplandroid;

/**
 * Gère les résultats d'un appel d'api.
 * Utiliser avec {@link sae401.festiplandroid.ApiManager}.
 * Permet de faire la logique sur la requête asynchrone
 *
 * @author Enzo Cluzel
 * @author Lucas Descriaud
 * @author Loïc Faugières
 * @author Simon Guiraud
 */
public interface CallbackApi<T> {

    /**
     * Gestion d'une réponse positive de l'API contenant un objet json.
     *
     * @param reponseApi L'éventuelle réponse de l'API.
     */
    void onReponsePositive(T reponseApi);

    /**
     * Gestion d'une réponse négative de l'API.
     *
     * @param erreur Le message d'erreur renvoyé par l'API.
     */
    void onReponseErreur(String erreur);
}
