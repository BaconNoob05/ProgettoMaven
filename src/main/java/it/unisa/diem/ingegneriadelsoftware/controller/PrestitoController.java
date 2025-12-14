package it.unisa.diem.ingegneriadelsoftware.controller;

import it.unisa.diem.ingegneriadelsoftware.view.PrestitoView;
import it.unisa.diem.ingegneriadelsoftware.service.PrestitoService;
import it.unisa.diem.ingegneriadelsoftware.model.Prestito;
import it.unisa.diem.ingegneriadelsoftware.view.InterfaceView;
import java.time.LocalDate;
import java.util.List;

/**
 * @class PrestitoController
 * @brief Controller specifico per la gestione dei Prestiti.
 * @see CrudController
 * @see PrestitoService
 */
public class PrestitoController extends CrudController<Prestito> {

    /**
     * @brief Costruttore.
     * @param view La vista specifica prestiti.
     * @param service Il servizio specifico prestiti.
     */
    public PrestitoController(PrestitoView view, PrestitoService service){
        super(view, service);
    }
    
    /**
     * @brief Interpreta la vista come un'istanza di PrestitoView.
     * @return La vista specifica per i prestiti altrimenti un valore nullo..
     */
    private PrestitoView getSpecificView() {
        return (PrestitoView) view;
    }

    /**
     * @brief Trasforma il servizio generico nel servizio specifico PrestitoService.
     * @return L'istanza di PrestitoService altrimenti un valore nullo.
     */
    private PrestitoService getSpecificService() {
        return (PrestitoService) service;
    }
    
    /**
     * @brief Implementazione base del salvataggio richiesta da CrudController.
     * @details Nel flusso standard, si usa registraPrestito, ma questa è necessaria per la compilazione.
     * @param nuovo Il prestito da salvare.
     */
    @Override
    public void salva(Prestito nuovo) {
        eseguiOperazione(() -> service.salva(nuovo), "Prestito salvato (uso non standard).");
    }

    /**
     * @brief Implementazione base della modifica richiesta da CrudController.
     * @details Nel flusso standard, si usa registraRestituzione, ma questa è necessaria per la compilazione.
     * @param elemento Il prestito aggiornato.
     */
    @Override
    public void modifica(Prestito elemento) {
        eseguiOperazione(() -> service.modifica(elemento), "Prestito modificato (uso non standard).");
    }

    /**
     * @brief Inizializza il controller e collega i listener ai pulsanti specifici e ai campi base.
     */
    @Override
    public void init() {
        super.init(); 
        
        PrestitoView view = getSpecificView();

        view.getRegistraPrestitoButton().setOnAction(e -> registraPrestito());
        

        view.getRestituisciLibroButton().setOnAction(e -> registraRestituzione());     

        view.getAnnullaButton().setOnAction(e -> view.pulisciDettagli());
        

        view.getCercaField().textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                aggiornaLista(); 
            } else {
                cerca(); 
            }
        });
        

        view.getCancellaButton().setOnAction(e -> elimina());
        

        view.getAnnullaCercaButton().setOnAction(e -> {
            view.getCercaField().clear();
            aggiornaLista(); 
            view.mostraMessaggio("Ricerca annullata. Visualizzazione di tutti i prestiti.");
        });
        
        
    }


    /**
     * @brief Aggiorna la vista recuperando TUTTI i prestiti e applicando l'ordinamento richiesto.
     * @see InterfaceController#aggiornaVista()
     * @see PrestitoService#getAll()
     */
    @Override
    public void aggiornaVista(){
        aggiornaLista();
    }
    
    /**
     * @brief Gestisce la logica di registrazione di un nuovo prestito.
     * @param [in] datiInseriti L'oggetto Prestito temporaneo con Utente, Libro e Data Prevista.
     * @pre La vista deve fornire un Utente valido e un Libro con copie disponibili.
     * @post Le copie del libro vengono decrementate.
     * @see PrestitoView#getPrestitoNuovo()
     * @see PrestitoService#registraPrestito(Utente, Libro, java.time.LocalDate)
     */
    public void registraPrestito(){
        Prestito datiInseriti = getSpecificView().getPrestitoNuovo();
        
        if (datiInseriti == null) return;

        eseguiOperazione(() -> {
            getSpecificService().registraPrestito(
                datiInseriti.getUtente(), 
                datiInseriti.getLibro(), 
                datiInseriti.getDataPrevista()
            );
        }, "Prestito registrato con successo.");
    }

    /**
     * @brief Gestisce la logica di restituzione di un libro.
     * @pre Un prestito attivo deve essere selezionato nella lista.
     * @post Il prestito risulta concluso e le copie del libro vengono incrementate.
     * @see PrestitoView#getDataRestituzione()
     * @see PrestitoService#registraRestituzione(Prestito, java.time.LocalDate)
     */
    public void registraRestituzione(){
        Prestito prestitoSelezionato = getSpecificView().getElementoSelezionato();
        LocalDate dataRestituzione = getSpecificView().getDataRestituzione();

        if (prestitoSelezionato == null) {
            view.mostraMessaggio("Errore: Seleziona un prestito attivo dalla lista.");
            return;
        }

        if (dataRestituzione == null) {
            view.mostraMessaggio("Errore: Inserisci una data di restituzione valida.");
            return;
        }

        eseguiOperazione(() -> {
            getSpecificService().registraRestituzione(prestitoSelezionato, dataRestituzione);
        }, "Restituzione registrata. Prestito chiuso.");
    }

    /**
     * @brief Aggiorna la vista recuperando TUTTI i prestiti e applicando l'ordinamento.
     * @details Ordina i prestiti per: 1. Scaduti (prima), 2. Non restituiti (in base alla data prevista più vicina), 3. Restituiti (alla fine, ordinati per data effettiva decrescente).
     * @see PrestitoService#getAll()
     */
    public void aggiornaLista(){
        List<Prestito> tutti = service.getAll();
        

        tutti.sort((p1, p2) -> {
            boolean p1Restituito = p1.getDataEffettiva() != null;
            boolean p2Restituito = p2.getDataEffettiva() != null;
            boolean p1Scaduto = p1.isScaduto();
            boolean p2Scaduto = p2.isScaduto();


            if (p1Scaduto && !p2Scaduto) return -1;
            if (!p1Scaduto && p2Scaduto) return 1;


            if (p1Restituito && !p2Restituito) return 1;
            if (!p1Restituito && p2Restituito) return -1;
            

            if (!p1Restituito && !p2Restituito) {
                return p1.getDataPrevista().compareTo(p2.getDataPrevista());
            }


            if (p1Restituito && p2Restituito) {

                return p2.getDataEffettiva().compareTo(p1.getDataEffettiva());
            }

            return 0;
        });
        
        view.mostraLista(tutti);
    }
}