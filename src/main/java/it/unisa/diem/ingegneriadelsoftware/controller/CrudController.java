package it.unisa.diem.ingegneriadelsoftware.controller;

import com.tuoprogetto.biblioteca.model.InterfaceID;
import com.tuoprogetto.biblioteca.view.InterfaceView;
import com.tuoprogetto.biblioteca.service.InterfaceService;

/**
 * @class CrudController
 * @brief Controller astratto che estende le funzionalità base per le operazioni.
 * @tparam T Il tipo dell'entità, che deve estendere InterfaceID per garantire l'identificabilità.
 * @see BaseController
 */
public abstract class CrudController<T extends InterfaceID> extends BaseController<T> {

    /**
     * @brief Costruttore.
     * @param view La vista generica.
     * @param service Il servizio generico.
     */
    public CrudController(InterfaceView<T> view, InterfaceService<T> service);

    /**
     * @brief Metodo astratto per la logica di salvataggio di un nuovo elemento.
     * @details Le sottoclassi devono implementare questo metodo per gestire l'inserimento.
     * @param nuovo L'oggetto T da salvare.
     */
    public abstract void salva(T nuovo);

    /**
     * @brief Metodo astratto per la logica di modifica di un elemento esistente.
     * @details Le sottoclassi devono implementare questo metodo per gestire l'aggiornamento.
     * @param elemento L'oggetto T con i dati aggiornati.
     */
    public abstract void modifica(T elemento);

    /**
     * @brief Gestisce l'eliminazione dell'elemento selezionato.
     * @pre Un elemento valido deve essere selezionato nella vista.
     * @post L'elemento viene rimosso dal repository tramite il servizio e la vista aggiornata.
     * @see InterfaceService#elimina(Object)
     */
    public void elimina();

    /**
     * @brief Esegue la ricerca degli elementi basata sul filtro della vista.
     * @post La vista visualizza solo gli elementi che corrispondono al criterio di ricerca.
     * @see InterfaceView#getStringaCerca()
     * @see InterfaceService#cercaGenerico(String)
     */
    public void cerca();

}
