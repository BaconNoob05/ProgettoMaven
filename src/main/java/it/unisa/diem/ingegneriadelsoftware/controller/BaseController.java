package it.unisa.diem.ingegneriadelsoftware.controller;

import it.unisa.diem.ingegneriadelsoftware.view.InterfaceView;
import it.unisa.diem.ingegneriadelsoftware.service.InterfaceService;

/**
 * @class BaseController
 * @brief Classe base astratta per i controller generici.
 * @details Implementa la logica di comunicazione tra View e Service .
 * Gestisce un'entità generica di tipo T.
 *
 * @tparam T Il tipo dell'entità gestita dal controller.
 * @see InterfaceController
 */
public abstract class BaseController<T> implements InterfaceController {

    /**
     * @brief Riferimento all'interfaccia della vista.
     */
    protected InterfaceView<T> view;

    /**
     * @brief Riferimento all'interfaccia del servizio.
     */
    protected InterfaceService<T> service;

    /**
     * @brief Costruttore del BaseController.
     * @param view L'istanza della vista .
     * @param service L'istanza del servizio.
     */
    public BaseController(InterfaceView<T> view, InterfaceService<T> service){};

    /**
     * @brief Inizializza il controller.
     * @see InterfaceController#init()
     */
    @Override
    public void init(){};

    /**
     * @brief Aggiorna la vista recuperando tutti gli elementi dal servizio.
     * @see InterfaceController#aggiornaVista()
     * @see InterfaceService#getAll()
     */
    @Override
    public void aggiornaVista(){};

    /**
     * @brief Esegue un'operazione generica gestendo eccezioni e messaggi utente.
     * @param operazione L'azione logica da eseguire .
     * @param messaggioConferma Il messaggio da mostrare alla vista in caso di successo.
     * @pre L'operazione non deve essere nulla.
     * @post Se l'operazione ha successo, la vista viene aggiornata e viene mostrato il messaggio.
     */
    public void eseguiOperazione(Runnable operazione, String messaggioConferma){};

    /**
     * @brief Recupera l'elemento attualmente selezionato nella vista generica.
     * @return L'istanza di tipo T selezionata oppure nulla.
     * @see InterfaceView#getElementoSelezionato()
     */
    public T getSelezionato(){return null;}
}