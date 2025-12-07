package it.unisa.diem.ingegneriadelsoftware.service;
import java.util.List;
import java.util.Comparator;
import com.tuoprogetto.biblioteca.repository.InterfaceRepository;
import com.tuoprogetto.biblioteca.model.InterfaceID;

/**
 * @class BaseService
 * @brief Implementazione base astratta per i servizi.
 * @tparam T Il tipo di dato gestito, che deve implementare InterfaceID per garantire l'accesso all'ID.
 */
public abstract class BaseService<T extends InterfaceID> implements InterfaceService<T> {

    /**
     * @brief Riferimento al repository per il salvataggio dei dati.
     */
    protected InterfaceRepository<T> repository;

    /**
     * @brief Costruttore della classe base.
     * @param [in] repo Il repository specifico da associare al servizio.
     */
    public BaseService(InterfaceRepository<T> repo) { }

    /**
     * @brief Salva o aggiorna un elemento.
     * @param [in] elemento L'oggetto da salvare.
     * @pre L'elemento deve essere valido.
     * @post L'elemento è presente tramite il repository.
     * @see InterfaceRepository#inserisciOAggiorna(Object)
     */
    @Override
    public void salva(T elemento) { }

    /**
     * @brief Modifica un elemento esistente.
     * @param [in] elemento L'oggetto modificato.
     * @pre L'elemento deve esistere.
     * @post Il repository viene aggiornato.
     * @see InterfaceRepository#inserisciOAggiorna(Object)
     */
    @Override
    public void modifica(T elemento) { }

    /**
     * @brief Elimina un elemento utilizzando il suo ID.
     * @param [in] elemento L'oggetto da eliminare.
     * @pre L'elemento non deve essere null.
     * @post L'elemento viene rimosso dal repository.
     * @see InterfaceRepository#elimina(String)
     */
    @Override
    public void elimina(T elemento) { }

    /**
     * @brief Cerca un elemento per ID delegando al repository.
     * @param [in] id L'identificativo da cercare.
     * @return L'oggetto trovato, altrimenti restituisce un valore nullo.
     * @pre ID valido.
     * @see InterfaceRepository#cerca(String)
     */
    @Override
    public T cerca(String id) { }

    /**
     * @brief Esegue una ricerca generica.
     * @param [in] filtro La stringa di ricerca.
     * @return La lista filtrata, altrimenti restituisce una lista vuota.
     */
    @Override
    public List<T> cercaGenerico(String filtro) { }

    /**
     * @brief Recupera tutti gli elementi dal repository.
     * @return La lista completa, altrimenti restituisce una lista vuota.
     * @pre Repository inizializzato.
     * @see InterfaceRepository#getAll()
     */
    @Override
    public List<T> getAll() { }

    /**
     * @brief Ordina la lista degli elementi.
     * @param [in] comparatore Il criterio di ordinamento.
     * @pre Comparatore non nullo.
     * @post L'ordine di visualizzazione cambia.
     */
    @Override
    public void ordina(Comparator<T> comparatore) { }
}
