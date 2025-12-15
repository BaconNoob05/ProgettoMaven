

package it.unisa.diem.ingegneriadelsoftware.view;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @class BaseView
 * @brief Classe base astratta per le view dell'applicazione.
 * @details Implementa le funzionalità comuni definite in InterfaceView e mantiene
 * il riferimento locale ai dati visualizzati.
 * @tparam T Il tipo di dato gestito dalla view.
 */
public abstract class BaseView<T> implements InterfaceView<T> {

    /**
     * @brief Lista locale degli elementi visualizzati.
     * Utilizzata per mantenere lo stato dei dati mostrati a video.
     */
    protected List<T> elementi;
    
    

    /**
     * @brief Lista osservabile utilizzata dai dati per i componenti JavaFX .
     * @details Le modifiche a questa lista vengono automaticamente riflesse nella UI.
     */
    protected final ObservableList<T> dataList;

    
    /**
     * @brief Costruttore di base che funge da sorgente dati per i componenti UI di JavaFX.
     */
    public BaseView() {
        //L'ObservableList è la sorgente per la TableView.
        this.dataList = FXCollections.observableArrayList(); 
    }
    
    /**
     * @brief Mostra un messaggio all'utente.
     * @param [in] messaggio Il testo del messaggio.
     */
    @Override
    public void mostraMessaggio(String messaggio) {
        System.out.println("Messaggio: " + messaggio);
    }
    
    /**
     * @brief Aggiorna la lista degli elementi visualizzati.
     * @param [in] lista La nuova lista di dati da visualizzare.
     * @pre La lista non deve essere null.
     * @post L'attributo elementi viene aggiornato.
     * @throws IllegalArgumentException Se la lista passata come parametro è null, viene lanciata un'eccezione.
     * @see InterfaceView#mostraLista(List)
     */
    @Override
    public void mostraLista(List<T> lista) {
        if (lista == null) {
            throw new IllegalArgumentException("La lista da mostrare non può essere null.");
        }
        this.elementi = lista;
        this.dataList.clear();
        this.dataList.addAll(lista);
        System.out.println("Lista aggiornata: " + lista.size() + " elementi.");
    }

    /**
     * @brief Restituisce l'elemento selezionato.
     * @return L'elemento selezionato, altrimenti restituisce il valore null.
     * @pre La lista elementi deve essere popolata.
     * @see InterfaceView#getElementoSelezionato()
     */
    @Override
    public T getElementoSelezionato() {
        if (elementi != null && !elementi.isEmpty()) {
            return elementi.get(0); 
        }
        return null;
    }

    /**
     * @brief Ottiene la stringa di ricerca corrente.
     * @return Il testo da cercare, altrimenti restituisce il valore null.
     * @see InterfaceView#getCampoCerca()
     */
    @Override
    public String getCampoCerca() {
        return "";
    }
}