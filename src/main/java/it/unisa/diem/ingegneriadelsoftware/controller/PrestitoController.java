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
 * @see BaseController
 * @see PrestitoService
 */
public class PrestitoController extends BaseController<Prestito> {

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
     * @brief Inizializza il controller e collega i listener ai pulsanti specifici.
     */
    @Override
    public void init() {
        super.init(); 
        
        PrestitoView view = getSpecificView();

        // 1. Listener per Registra Prestito
        view.getRegistraPrestitoButton().setOnAction(e -> registraPrestito());
        
        // 2. Listener per Registra Restituzione
        view.getRestituisciLibroButton().setOnAction(e -> registraRestituzione());
        
        // 3. Listener per Annulla (Resetta il form a stato di nuovo prestito)
        view.getAnnullaButton().setOnAction(e -> view.pulisciDettagli());
    }


    /**
     * @brief Aggiorna la vista recuperando tutti i prestiti attivi.
     * @see InterfaceController#aggiornaVista()
     * @see PrestitoService#listaPrestitiAttivi()
     */
    @Override
    public void aggiornaVista(){
        aggiornaPrestiti();
    }
    
    /**
     * @brief Gestisce la logica di registrazione di un nuovo prestito.
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
            view.mostraMessaggio("Seleziona un prestito attivo dalla lista.");
            return;
        }

        if (dataRestituzione == null) {
            view.mostraMessaggio("Inserisci una data di restituzione valida.");
            return;
        }

        eseguiOperazione(() -> {
            getSpecificService().registraRestituzione(prestitoSelezionato, dataRestituzione);
        }, "Restituzione registrata. Prestito chiuso.");
        
        // La chiamata ad aggiornaVista() è implicita in eseguiOperazione.
    }

    /**
     * @brief Aggiorna la vista dei prestiti attivi, applicando l'ordinamento richiesto (FC-3.1.1).
     * @see PrestitoService#listaPrestitiAttivi()
     */
    public void aggiornaPrestiti(){
        List<Prestito> attivi = getSpecificService().listaPrestitiAttivi();
        
        // FC-3.1.1: Ordinamento per data: scaduti prima, poi per data prevista crescente
        attivi.sort((p1, p2) -> {
            boolean p1Scaduto = p1.isScaduto();
            boolean p2Scaduto = p2.isScaduto();

            // Se P1 è scaduto e P2 no, P1 va prima (-1)
            if (p1Scaduto && !p2Scaduto) {
                return -1; 
            }
            // Se P2 è scaduto e P1 no, P2 va prima (1)
            if (!p1Scaduto && p2Scaduto) {
                return 1; 
            }
            
            // Se entrambi scaduti o entrambi attivi, ordina per data prevista (più vicina prima)
            return p1.getDataPrevista().compareTo(p2.getDataPrevista());
        });
        
        view.mostraLista(attivi);
    }
}
