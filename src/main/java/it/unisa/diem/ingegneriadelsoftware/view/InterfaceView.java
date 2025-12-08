package it.unisa.diem.ingegneriadelsoftware.view;

import java.util.List;

/**
 * @interface InterfaceView
 * @brief Interfaccia generica che definisce il contratto per le viste.
 * @tparam T Il tipo di dato visualizzato nella vista.
 */
public interface InterfaceView<T> {

    /**
     * @brief Visualizza una lista di elementi nella vista.
     * @param [in] lista La lista di oggetti T da mostrare.
     * @pre La lista non può essere null.
     * @post La vista riflette il contenuto della lista passata.
     */
    void mostraLista(List<T> lista);

    /**
     * @brief Recupera l'elemento attualmente selezionato dall'utente.
     * @return L'oggetto selezionato, altrimenti restituisce un valore nullo.
     * @pre La vista deve essere inizializzata.
     * @post Lo stato della selezione non cambia.
     */
    T getElementoSelezionato();

    /**
     * @brief Ottiene il testo inserito nel campo di ricerca.
     * @return Il testo contenuto nel campo di ricerca, altrimenti restituisce un valore nullo.
     * @pre Il componente di ricerca deve esistere.
     * @post Nessuna modifica alla UI.
     */
    String getCampoCerca();

    /**
     * @brief Mostra un messaggio all'utente.
     * @param [in] messaggio Il testo da visualizzare.
     * @pre Il messaggio non deve essere null.
     * @post L'utente visualizza il responso.
     */
    void mostraMessaggio(String messaggio);
}