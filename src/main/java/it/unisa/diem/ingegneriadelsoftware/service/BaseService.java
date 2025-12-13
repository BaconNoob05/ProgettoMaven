package it.unisa.diem.ingegneriadelsoftware.service;





    /**
     * @brief Riferimento al repository per il salvataggio dei dati.
     */
    protected InterfaceRepository<T> repository;

    /**
     * @brief Costruttore della classe base.
     * @param [in] repo Il repository specifico da associare al servizio.
     */
    public BaseService(InterfaceRepository<T> repo) { 
            this.repository = repo;
        }

    /**
     * @brief Salva o aggiorna un elemento.
     * @param [in] elemento L'oggetto da salvare.
     * @pre L'elemento deve essere valido.
     * @post L'elemento è presente tramite il repository.
     * @see InterfaceRepository#inserisciOAggiorna(Object)
     */
    @Override
    public void salva(T elemento) { 
        if (elemento != null) {
            repository.inserisciOAggiorna(elemento);
        }
    }

    /**
     * @brief Modifica un elemento esistente.
     * @param [in] elemento L'oggetto modificato.
     * @pre L'elemento deve esistere.
     * @post Il repository viene aggiornato.
     * @see InterfaceRepository#inserisciOAggiorna(Object)
     */
    @Override
    public void modifica(T elemento) { 
        if (elemento != null) {
            repository.inserisciOAggiorna(elemento);
        }
    }

    /**
     * @brief Elimina un elemento utilizzando il suo ID.
     * @param [in] elemento L'oggetto da eliminare.
     * @pre L'elemento non deve essere null.
     * @pre L'ID non deve essere null.
     * @post L'elemento viene rimosso dal repository.
     * @see InterfaceRepository#elimina(String)
     */
    @Override
    public void elimina(T elemento) {
        if (elemento != null && elemento.getId() != null) {
            repository.elimina(elemento.getId());
        }
    }

    /**
     * @brief Cerca un elemento per ID delegando al repository.
     * @param [in] id L'identificativo da cercare.
     * @return L'oggetto trovato, altrimenti restituisce un valore nullo.
     * @pre ID valido.
     * @see InterfaceRepository#cerca(String)
     */
    @Override
    public T cerca(String id) { 
        if (id == null || id.trim().isEmpty()) {
            return null;
        }
        return repository.cerca(id);
    }

    /**
     * @brief Esegue una ricerca generica.
     * @param [in] filtro La stringa di ricerca.
     * @return La lista dei risultati di ricerca 
     * @note L'implementazione nella classe base restituisce la lista contenente tutti gli elementi
     */
    @Override
    public List<T> cercaGenerico(String filtro) { 
        return getAll();        
    }

    /**
     * @brief Recupera tutti gli elementi dal repository.
     * @return La lista completa, altrimenti restituisce una lista vuota.
     * @pre Repository inizializzato.
     * @see InterfaceRepository#getAll()
     */
    @Override
    public List<T> getAll() { 
        return repository.getAll();
    }

    /**
     * @brief Ordina la lista degli elementi.
     * @param [in] comparatore Il criterio di ordinamento.
     * @pre Comparatore non nullo.
     * @post L'ordine di visualizzazione cambia.
     */
    @Override
    public void ordina(Comparator<T> comparatore) { 
            
    }
}



package it.unisa.diem.ingegneriadelsoftware.controller;


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
     * @brief Aggiorna la vista recuperando tutti gli elementi dal servizio.
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
     * @param operazione L'azione logica da eseguire .
     * @param messaggioConferma Il messaggio da mostrare alla vista in caso di successo.
     * @pre L'operazione non deve essere nulla.
     * @post Se l'operazione ha successo, la vista viene aggiornata e viene mostrato il messaggio.
     */
    public void eseguiOperazione(Runnable operazione, String messaggioConferma){
        try {
            operazione.run();
            
            aggiornaVista();
            if (messaggioConferma != null && !messaggioConferma.isEmpty()) {
                view.mostraMessaggio(messaggioConferma);
            }
        } catch (IllegalArgumentException e) {
            view.mostraMessaggio("Errore: " + e.getMessage());
        } catch (Exception e) {
            view.mostraMessaggio("Errore imprevisto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @brief Recupera l'elemento attualmente selezionato nella vista generica.
     * @return L'istanza di tipo T selezionata oppure nulla.
     * @see InterfaceView#getElementoSelezionato()
     */
    public T getSelezionato(){ return view.getElementoSelezionato(); }
}
