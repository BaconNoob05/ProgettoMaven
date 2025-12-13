package it.unisa.diem.ingegneriadelsoftware.controller;

/**
 * @interface InterfaceController
 * @brief Interfaccia base per i controller dell'applicazione.
 */
public interface InterfaceController {

    /**
     * @brief Inizializza il controller e configura la vista associata.
     * @pre Il controller e la vista devono essere stati istanziati.
     * @post Il controller è pronto per gestire l'interazione utente.
     */
    void init();

    /**
     * @brief Aggiorna i dati mostrati nella vista.
     * @pre Il servizio deve essere accessibile.
     * @post La vista riflette lo stato attuale dei dati.
     */
    void aggiornaVista();
}
