package it.unisa.diem.ingegneriadelsoftware.controller;

import it.unisa.diem.ingegneriadelsoftware.view.PrestitoView;
import it.unisa.diem.ingegneriadelsoftware.service.PrestitoService;
import it.unisa.diem.ingegneriadelsoftware.model.Prestito;
import it.unisa.diem.ingegneriadelsoftware.view.InterfaceView;
import java.time.LocalDate;
import java.util.List;
import javafx.scene.control.Button; // Necessario per il tipo Button


/**
 * @class PrestitoController
 * @brief Controller specifico per la gestione dei Prestiti.
 * @see CrudController
 * @see PrestitoService
 */
public class PrestitoController extends CrudController<Prestito> {

    /**
     * @brief Costruttore.
     * @param [in] view La view specifica per i prestiti.
     * @param [in] service Il service specifico per i prestiti.
     */
    public PrestitoController(PrestitoView view, PrestitoService service){
        super(view, service);
    }
    
    /**
     * @brief Interpreta la view come un'istanza di PrestitoView.
     * @return La view specifica per i prestiti altrimenti il valore null.
     */
    private PrestitoView getSpecificView() {
        return (PrestitoView) view;
    }

    /**
     * @brief Trasforma il service generico nel service specifico PrestitoService.
     * @return L'istanza di PrestitoService altrimenti il valore null.
     */
    private PrestitoService getSpecificService() {
        return (PrestitoService) service;
    }
    
    /**
     * @brief Implementazione base del salvataggio richiesta da CrudController.
     * @details Nel flusso standard si fa uso di registraPrestito, ma questa è necessaria per la compilazione.
     * @param [in] nuovo Il prestito da salvare.
     */
    @Override
    public void salva(Prestito nuovo) {
        eseguiOperazione(() -> service.salva(nuovo), "Prestito salvato (uso non standard).");
    }

    /**
     * @brief Implementazione base della modifica richiesta da CrudController.
     * @details Nel flusso standard si impiega registraRestituzione, ma questa è necessaria per la compilazione.
     * @param [in] elemento Il prestito aggiornato.
     */
    @Override
    public void modifica(Prestito elemento) {
        eseguiOperazione(() -> service.modifica(elemento), "Prestito modificato (uso non standard).");
    }

    /**
     * @brief Inizializza il controller e collega i listener ai pulsanti specifici e ai campi base.
     * @see BaseController#init()
     */
    @Override
    public void init() {
        super.init(); 
        
        PrestitoView view = getSpecificView();

        // Listener per REGISTRA PRESTITO con conferma (doppio click)
        view.getRegistraPrestitoButton().setOnAction(e -> {
            Button pulsante = view.getRegistraPrestitoButton();
            Prestito datiInseriti = view.getPrestitoNuovo();
            
            // Se i dati non sono validi, la view ha già mostrato un errore, resettiamo lo stato di conferma
            if (datiInseriti == null) { 
                view.resetConferma();
                return;
            }
            
            view.richiediConferma(pulsante, this::registraPrestito, "Clicca di nuovo per confermare il prestito.");
        });
        
        // Listener per RESTITUISCI LIBRO con conferma (doppio click)
        view.getRestituisciLibroButton().setOnAction(e -> {
            Button pulsante = view.getRestituisciLibroButton();
            
            if (view.getElementoSelezionato() == null || view.getElementoSelezionato().getDataEffettiva() != null) {
                view.mostraMessaggio("Errore: Seleziona un prestito attivo per la restituzione.");
                view.resetConferma();
                return;
            }
            if (view.getDataRestituzione() == null) {
                view.mostraMessaggio("Errore: Inserisci una data di restituzione valida.");
                view.resetConferma();
                return;
            }

            view.richiediConferma(pulsante, this::registraRestituzione, "Clicca di nuovo per confermare la restituzione.");
        });     

        // Listener per ELIMINA con conferma (doppio click)
        view.getCancellaButton().setOnAction(e -> {
            Button pulsante = view.getCancellaButton();
            
            if (view.getElementoSelezionato() == null) {
                view.mostraMessaggio("Seleziona un prestito da eliminare.");
                return;
            }
            
            // L'eliminazione viene eseguita solo per i prestiti chiusi o non ancora attivi (logica standard Crud)
            view.richiediConferma(pulsante, this::elimina, "Clicca di nuovo per confermare l'eliminazione del prestito.");
        });
        
        // L'annulla è gestito in DatiBaseView

        view.getCercaField().textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                aggiornaLista(); 
            } else {
                cerca(); 
            }
            view.resetConferma(); // Resetta lo stato di conferma se si cambia filtro
        });
        

        view.getAnnullaCercaButton().setOnAction(e -> {
            view.getCercaField().clear();
            aggiornaLista(); 
            view.mostraMessaggio("Ricerca annullata. Visualizzazione di tutti i prestiti.");
            view.resetConferma();
        });
    }


    /**
     * @brief Aggiorna la view recuperando tutti i prestiti e applicando l'ordinamento richiesto.
     * @see InterfaceController#aggiornaVista()
     * @see PrestitoService#getAll()
     */
    @Override
    public void aggiornaVista(){
        aggiornaLista();
    }
    
    /**
     * @brief Gestisce la logica di registrazione di un nuovo prestito.
     * @details Recupera l'utente, il libro e la data di restituzione prevista dalla view, tramite getPrestito()
     * @pre La view deve fornire un Utente valido e un Libro con copie disponibili.
     * @post Le copie del libro vengono decrementate.
     * @see PrestitoView#getPrestitoNuovo()
     * @see PrestitoService#registraPrestito(Utente, Libro, java.time.LocalDate)
     */
    public void registraPrestito(){
        Prestito datiInseriti = getSpecificView().getPrestitoNuovo();
        
        // Nota: questa verifica viene fatta due volte (qui e nel listener), ma è necessaria
        // per il flusso di esecuzione di eseguiOperazione in caso di successo.
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
     * @details Recupera il prestito selezionato e la data di restituzione effettiva dalla view
     * @pre Un prestito attivo deve essere selezionato nella lista.
     * @post Il prestito risulta concluso e le copie del libro vengono incrementate.
     * @see PrestitoView#getDataRestituzione()
     * @see PrestitoService#registraRestituzione(Prestito, java.time.LocalDate)
     */
    public void registraRestituzione(){
        Prestito prestitoSelezionato = getSpecificView().getElementoSelezionato();
        LocalDate dataRestituzione = getSpecificView().getDataRestituzione();

        if (prestitoSelezionato == null || dataRestituzione == null) return;
        
        eseguiOperazione(() -> {
            getSpecificService().registraRestituzione(prestitoSelezionato, dataRestituzione);
        }, "Restituzione registrata. Prestito chiuso.");
    }

    /**
     * @brief Aggiorna la vista recuperando tutti i prestiti e applicando l'ordinamento.
     * @details Effettua l'ordinamento dei prestiti secondo il seguente criterio:
     * 1. Scaduti  
     * 2. Non restituiti, in base alla data prevista più prossima 
     * 3. Restituiti, ordinati per data di restituzione effettiva decrescente.
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