package it.unisa.diem.ingegneriadelsoftware.controller;

import it.unisa.diem.ingegneriadelsoftware.view.UtenteView;
import it.unisa.diem.ingegneriadelsoftware.service.UtenteService;
import it.unisa.diem.ingegneriadelsoftware.model.Utente;
import java.util.Comparator; 
import java.util.List; 
import javafx.scene.control.Button;


/**
 * @class UtenteController
 * @brief Controller specifico per la gestione degli utenti.
 * @details Estende CrudController per gestire il ciclo di vita degli utenti.
 *
 * @see CrudController
 * @see UtenteView
 */
public class UtenteController extends CrudController<Utente> {

    /**
     * @brief Costruttore.
     * @param [in] view La view specifica per gli utenti.
     * @param [in] service Il service specifico per gli utenti.
     */
    public UtenteController(UtenteView view, UtenteService service){
        super(view,service);
    }

    /**
     * @brief Aggiorna la view recuperando tutti gli elementi dal service ed applica l'ordinamento.
     * @see InterfaceController#aggiornaVista()
     * @see InterfaceService#getAll()
     */
    @Override
    public void aggiornaVista() {
        List<Utente> lista = service.getAll();
        
        //ordina gli utenti prima per cognome poi per nome(non case sensitive)
        lista.sort(Comparator
                .comparing(Utente::getCognome, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(Utente::getNome, String.CASE_INSENSITIVE_ORDER)
        );
        
        view.mostraLista(lista);
    }

    /**
     * @brief Inizializza il controller e collega i listener ai pulsanti della view.
     * @see BaseController#init()
     */
    @Override
    public void init() {
        //carica i dati iniziali e configura la view
        super.init();
        
        UtenteView view = getSpecificView();
        
        //pulsante ok (salva/modifica)
        view.getOkButton().setOnAction(e -> {
            Button pulsante = view.getOkButton();
            String azione;
            Runnable operazione;

            if (view.getTableView().getSelectionModel().getSelectedItem() != null) {  
                //modifica
                Utente modificato = view.getUtenteModificato();
                if (modificato == null) { view.resetConferma(); return; }
                azione = "aggiornare l'utente selezionato";
                operazione = () -> modifica(modificato);
            } else {
                //salva
                Utente nuovo = view.getUtenteNuovo();
                if (nuovo == null) { view.resetConferma(); return; }
                azione = "salvare il nuovo utente";
                operazione = () -> salva(nuovo);
            }
            
            //doppio click per conferma
            view.richiediConferma(pulsante, operazione, "Clicca di nuovo per " + azione);
        });
        
        //pulsante elimina
        view.getCancellaButton().setOnAction(e -> {
            Button pulsante = view.getCancellaButton();

            if (view.getElementoSelezionato() == null) {
                view.mostraMessaggio("Seleziona un utente da eliminare.");
                return;
            }
            
            //doppio click per conferma
            view.richiediConferma(pulsante, this::elimina, "Clicca di nuovo per confermare l'eliminazione.");
        });
        
        
        //pulsante anulla
        view.getAnnullaButton().setOnAction(e -> view.pulisciDettagli());
        
        view.getCercaField().textProperty().addListener((observable, oldValue, newValue) -> {
            cerca();
            view.resetConferma();
        });
        
        //pulsante annulla ricerca
        view.getAnnullaCercaButton().setOnAction(e -> {
            view.getCercaField().clear();
            aggiornaVista();
            view.mostraMessaggio("Ricerca annullata.");
            view.resetConferma();
        });
    }

    /**
     * @brief Interpreta la view come un'istanza di UtenteView.
     * @return L'istanza di UtenteView associata al controller altrimenti il valore null.
     */
    private UtenteView getSpecificView() {
        return (UtenteView) view;
    }
    
    /**
     * @brief Gestisce l'acquisizione dei dati e il salvataggio di un nuovo utente.
     * @pre I campi obbligatori devono essere compilati.
     * @post L'utente viene salvato nel sistema.
     * @see UtenteView#getUtenteNuovo()
     */
    public void salvaUtente(){
        Utente nuovo = getSpecificView().getUtenteNuovo();
        if (nuovo == null) return; 

        salva(nuovo);
    }

    /**
     * @brief Gestisce l'acquisizione dei dati e l'aggiornamento di un utente.
     * @see UtenteView#getUtenteModificato()
     */
    public void modificaUtente(){
        Utente modificato = getSpecificView().getUtenteModificato();
        if (modificato == null) return;

        modifica(modificato);
    }

    /**
     * @brief Implementazione del salvataggio utente.
     * @param [in] nuovo L'utente da salvare.
     */
    @Override
    public void salva(Utente nuovo){
        eseguiOperazione(() -> service.salva(nuovo), "Utente inserito correttamente.");
    }

    /**
     * @brief Implementazione della modifica utente.
     * @param [in] elemento L'utente aggiornato.
     */
    @Override
    public void modifica(Utente elemento){
        eseguiOperazione(() -> service.modifica(elemento), "Dati utente aggiornati correttamente.");
    }
}