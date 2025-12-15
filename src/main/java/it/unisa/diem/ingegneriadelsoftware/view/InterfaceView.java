package it.unisa.diem.ingegneriadelsoftware.view;

import java.util.List;

/**
 * @interface InterfaceView
 * @brief Interfaccia generica che definisce il contratto per le view.
 * @tparam T Il tipo di dato visualizzato nella view.
 */
public interface InterfaceView<T> {

    
    /**
     * @brief Visualizza una lista di elementi nella view.
     * @param [in] lista La lista di oggetti T da mostrare.
     * @pre La lista non può essere null.
     * @throws IllegalArgumentException Se la lista è null, viene lanciata un'eccezione.
     * @post La view riflette il contenuto della lista passata.
     */
    void mostraLista(List<T> lista);

    
    
    /**
     * @brief Recupera l'elemento attualmente selezionato dall'amministratore.
     * @return L'oggetto selezionato, altrimenti restituisce il valore null.
     * @pre La view deve essere inizializzata.
     * @post Lo stato della selezione non cambia.
     */
    T getElementoSelezionato();

    /**
     * @brief Ottiene il testo inserito nel campo di ricerca.
     * @return Il testo contenuto nel campo di ricerca, altrimenti restituisce il valore null.
     * @pre L'elemento da cercare deve esistere.
     * @post Nessuna modifica alla UI.
     */
    String getCampoCerca();

    
    
    /**
     * @brief Mostra un messaggio.
     * @param [in] messaggio Il testo da visualizzare.
     * @pre Il messaggio non deve essere null.
     * @post L'amministratore visualizza il responso.
     */
    void mostraMessaggio(String messaggio);
}