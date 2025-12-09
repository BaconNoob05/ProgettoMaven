package it.unisa.diem.ingegneriadelsoftware.view;
import java.util.List;

/**
 * @class BaseView
 * @brief Classe base astratta per le viste dell'applicazione.
 * @details Implementa le funzionalità comuni definite in InterfaceView e mantiene
 * il riferimento locale ai dati visualizzati.
 * @tparam T Il tipo di dato gestito dalla vista.
 */
public abstract class BaseView<T> implements InterfaceView<T> {

    /**
     * @brief Lista locale degli elementi visualizzati.
     * Utilizzata per mantenere lo stato dei dati mostrati a video.
     */
    protected List<T> elementi;

    /**
     * @brief Mostra un messaggio all'utente.
     * @param [in] messaggio Il testo del messaggio.
     */
    @Override
    public void mostraMessaggio(String messaggio) { }

    /**
     * @brief Aggiorna la lista degli elementi visualizzati.
     * @param [in] lista La nuova lista di dati da visualizzare.
     * @pre La lista non deve essere null.
     * @post L'attributo elementi è aggiornato.
     * @see InterfaceView#mostraLista(List)
     */
    @Override
    public void mostraLista(List<T> lista) { }

    /**
     * @brief Restituisce l'elemento selezionato.
     * @return L'elemento selezionato, altrimenti restituisce un valore nullo.
     * @pre La lista elementi deve essere popolata.
     * @see InterfaceView#getElementoSelezionato()
     */
    @Override
    public T getElementoSelezionato() { 
    
        return null;
    }

    /**
     * @brief Ottiene la stringa di ricerca corrente.
     * @return Il testo da cercare, altrimenti restituisce un valore nullo.
     * @see InterfaceView#getCampoCerca()
     */
    @Override
    public String getCampoCerca() { 
    
    
        return null;
    }
}
