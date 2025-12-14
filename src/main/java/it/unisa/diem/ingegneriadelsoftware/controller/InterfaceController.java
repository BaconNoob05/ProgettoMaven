package it.unisa.diem.ingegneriadelsoftware.controller;

/**
 * @interface InterfaceController
 * @brief Interfaccia base per i controller dell'applicazione.
 */
public interface InterfaceController {

    /**
     * @brief Inizializza il controller e configura la view associata.
     * @pre Il controller e la view devono essere stati istanziati.
     * @post Il controller è pronto per gestire l'interazione con il gestore.
     */
    void init();

    /**
     * @brief Aggiorna i dati mostrati nella view.
     * @pre Il service deve essere accessibile.
     * @post La view riflette lo stato attuale dei dati.
     */
    void aggiornaVista();
}
