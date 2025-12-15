package it.unisa.diem.ingegneriadelsoftware.service;

import java.util.List;
import java.util.Comparator;

/**
 * @interface InterfaceService
 * @brief Interfaccia generica per i service di gestione.
 * @tparam T Il tipo di entità gestita dal service.
 */
public interface InterfaceService<T> {

    /**
     * @brief Salva un nuovo elemento o ne aggiorna uno esistente.
     * @param [in] elemento L'oggetto da salvare.
     * @pre L'elemento non deve essere null e deve essere valido.
     * @post L'elemento viene salvato sul file.
     */
    void salva(T elemento);

    /**
     * @brief Modifica i dati di un elemento esistente.
     * @param [in] elemento L'oggetto contenente i dati aggiornati.
     * @pre L'elemento deve esistere.
     * @post I dati dell'elemento nel repository risultano aggiornati.
     */
    void modifica(T elemento);

    /**
     * @brief Elimina un elemento dal sistema.
     * @param [in] elemento L'oggetto da eliminare.
     * @pre L'elemento deve essere presente nel sistema.
     * @post L'elemento non è più rintracciabile nel repository.
     */
    void elimina(T elemento);

    /**
     * @brief Cerca un singolo elemento tramite il suo identificativo.
     * @param [in] id La stringa identificativa dell'oggetto.
     * @return L'oggetto trovato, altrimenti restituisce il valore null.
     * @pre L'ID non deve essere null.
     * @post Lo stato del sistema non viene alterato.
     */
    T cerca(String id);

    /**
     * @brief Esegue una ricerca generica basata su un filtro.
     * @param [in] filtro La stringa da usare come criterio di ricerca.
     * @return Una lista di elementi che corrispondono al filtro, altrimenti restituisce una lista vuota.
     * @pre Il filtro non deve essere null.
     * @post Nessuna modifica ai dati.
     */
    List<T> cercaGenerico(String filtro);

    /**
     * @brief Restituisce tutti gli elementi gestiti dal service.
     * @return La lista completa degli oggetti, altrimenti restituisce una lista vuota.
     */
    List<T> getAll();

    /**
     * @brief Ordina gli elementi in memoria secondo un comparatore specifico.
     * @param [in] comparatore L'oggetto Comparator che definisce l'ordinamento.
     * @pre Il comparatore non deve essere null.
     * @post L'ordine degli elementi segue il criterio imposto.
     */
    void ordina(Comparator<T> comparatore);
}