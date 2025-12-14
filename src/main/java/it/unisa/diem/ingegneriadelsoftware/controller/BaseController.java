package it.unisa.diem.ingegneriadelsoftware.controller;

import it.unisa.diem.ingegneriadelsoftware.view.InterfaceView;
import it.unisa.diem.ingegneriadelsoftware.service.InterfaceService;
import java.util.List;

/**
 * @class BaseController
 * @brief Classe base astratta per i controller generici.
 * @details Implementa la logica di comunicazione tra View e Service.
 * Gestisce un'entità generica di tipo T.
 *
 * @tparam T Il tipo dell'entità gestita dal controller.
 * @see InterfaceController
 */
public abstract class BaseController<T> implements InterfaceController {

    /**
     * @brief Riferimento all'interfaccia della view.
     */
    protected InterfaceView<T> view;

    /**
     * @brief Riferimento all'interfaccia del service.
     */
    protected InterfaceService<T> service;

    /**
     * @brief Costruttore del BaseController.
     * @param [in] view L'istanza della view.
     * @param [in] service L'istanza del service.
     */
    public BaseController(InterfaceView<T> view, InterfaceService<T> service){
        this.view=view;
        this.service=service;
    }

    /**
     * @brief Inizializza il controller.
     * @see InterfaceController#init()
     */
    @Override
    public void init(){
        aggiornaVista();
    }

    /**
     * @brief Aggiorna la view recuperando tutti gli elementi dal service.
     * @see InterfaceController#aggiornaVista()
     * @see InterfaceService#getAll()
     */
    @Override
    public void aggiornaVista(){
        List<T> lista = service.getAll();
        view.mostraLista(lista);
    }

    /**
     * @brief Esegue un'operazione generica gestendo eccezioni e messaggi utente.
     * @param [in] operazione L'azione da eseguire.
     * @param [in] messaggioDiConferma Il messaggio da mostrare alla view in caso di successo.
     * @pre L'operazione non deve essere null.
     * @post Se l'operazione ha successo, la view viene aggiornata e viene mostrato il messaggio.
     * @note Cattura le eccezioni generate dall'operazione e le mostra come messaggi di errore alla view.
     */
    public void eseguiOperazione(Runnable operazione, String messaggioDiConferma){
        try {
            operazione.run();
            
            aggiornaVista();
            if (messaggioDiConferma != null && !messaggioDiConferma.isEmpty()) {
                view.mostraMessaggio(messaggioDiConferma);
            }
        } catch (IllegalArgumentException ex) {
            view.mostraMessaggio("Errore: " + ex.getMessage());
        } catch (Exception ex) {
            view.mostraMessaggio("Errore imprevisto: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * @brief Recupera l'elemento attualmente selezionato nella view generica.
     * @return L'istanza di tipo T selezionata, altrimenti il valore null in assenza di selezioni attive.
     * @see InterfaceView#getElementoSelezionato()
     */
    public T getSelezionato(){ return view.getElementoSelezionato(); }
}