package it.unisa.diem.ingegneriadelsoftware.controller;

import it.unisa.diem.ingegneriadelsoftware.model.InterfaceID;
import it.unisa.diem.ingegneriadelsoftware.view.InterfaceView;
import it.unisa.diem.ingegneriadelsoftware.service.InterfaceService;
import java.util.List;

/**
 * @class CrudController
 * @brief Controller astratto che estende le funzionalità base per le operazioni.
 * @tparam T Il tipo dell'entità, che deve estendere InterfaceID per garantire l'identificabilità.
 * @see BaseController
 */
public abstract class CrudController<T extends InterfaceID> extends BaseController<T> {

    /**
     * @brief Costruttore.
     * @param [in] view La view generica.
     * @param [in] service Il service generico.
     */
    public CrudController(InterfaceView<T> view, InterfaceService<T> service){
        super(view,service);
    }

    /**
     * @brief Metodo astratto per la logica di salvataggio di un nuovo elemento.
     * @details Le sottoclassi devono implementare questo metodo per gestire l'inserimento.
     * @param [in] nuovo L'oggetto T da salvare.
     */
    public abstract void salva(T nuovo);

    /**
     * @brief Metodo astratto per la logica di modifica di un elemento esistente.
     * @details Le sottoclassi devono implementare questo metodo per gestire l'aggiornamento.
     * @param [in] elemento L'oggetto T con i dati aggiornati.
     */
    public abstract void modifica(T elemento);

    /**
     * @brief Gestisce l'eliminazione dell'elemento selezionato.
     * @pre Un elemento valido deve essere selezionato nella view.
     * @post L'elemento viene rimosso dal repository tramite il service e la view aggiornata.
     * @see InterfaceService#elimina(Object)
     */
    public void elimina(){
        T selezionato = view.getElementoSelezionato();
        
        if (selezionato == null) {
            view.mostraMessaggio("Seleziona un elemento da eliminare.");
            return;
        }

        eseguiOperazione(() -> service.elimina(selezionato), "Elemento eliminato con successo.");
    }

    /**
     * @brief Esegue la ricerca degli elementi basata sul filtro della view.
     * @post La vista visualizza solo gli elementi che corrispondono al criterio di ricerca.
     * @see InterfaceView#getCampoCerca()
     * @see InterfaceService#cercaGenerico(String)
     */
    public void cerca(){
        String filtro = view.getCampoCerca();
        
        List<T> risultati = service.cercaGenerico(filtro);
        view.mostraLista(risultati);
    }
}
