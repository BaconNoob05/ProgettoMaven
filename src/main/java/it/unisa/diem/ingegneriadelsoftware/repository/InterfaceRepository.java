package it.unisa.diem.ingegneriadelsoftware.repository;
import java.util.List;
import it.unisa.diem.ingegneriadelsoftware.model.InterfaceID;

/**
 * @interface InterfaceRepository
 * @brief Interfaccia generica per la gestione del repository dei dati.
 * @details Definisce le operazioni come la creazione o l'eliminazione 
 * di collezioni di oggetti che possiedono un identificativo univoco.
 * @tparam T Il tipo di dato gestito che deve implementare InterfaceID.
 */
public interface InterfaceRepository<T extends InterfaceID> {

    /**
     * @brief Carica tutti i dati dal file.
     * @param[in] lista La lista dove vengono caricati i dati.
     * @pre Il gestore di I/O deve essere stato correttamente inizializzato.
     * @post La lista interna del repository contiene tutti i dati presenti su file.
     */
    void caricaTutti(List<T> lista);

    /**
     * @brief Salva lo stato corrente del repository su file.
     * @post I dati vengono memorizzati correttamente.
     */
    void salvaSuFile();

    /**
     * @brief Inserisce un nuovo elemento o aggiorna un elemento esistente.
     * @param [in] elemento L'oggetto T da inserire o aggiornare.
     * @pre L'elemento non deve essere null e deve restituire un ID valido.
     * @post L'elemento è presente nella lista.
     */
    void inserisciOAggiorna(T elemento);

    /**
     * @brief Elimina un elemento dal repository in base al suo ID.
     * @param [in] id L'identificativo univoco dell'elemento da rimuovere.
     * @pre L'ID non deve essere null.
     * @post Se l'elemento esisteva, viene rimosso, altrimenti lo stato del repository rimane invariato.
     */
    void elimina(String id);

    /**
     * @brief Cerca un singolo elemento tramite il suo ID.
     * @param [in] id L'identificativo dell'elemento da recuperare.
     * @return L'oggetto trovato, altrimenti restituisce un valore nullo.
     * @pre L'ID non deve essere null.
     * @post Lo stato del repository non viene modificato.
     */
    T cerca(String id);

    /**
     * @brief Restituisce tutti gli elementi presenti nel repository.
     * @return La lista completa degli oggetti T, altrimenti restituisce una lista vuota.
     */
    List<T> getAll();
}
