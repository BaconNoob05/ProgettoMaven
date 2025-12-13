package it.unisa.diem.ingegneriadelsoftware.controller;

import it.unisa.diem.ingegneriadelsoftware.view.UtenteView;
import it.unisa.diem.ingegneriadelsoftware.service.UtenteService;
import it.unisa.diem.ingegneriadelsoftware.model.Utente;
import java.util.Comparator; 
import java.util.List; 


/**
 * @class UtenteController
 * @brief Controller specifico per la gestione degli Utenti.
 * @details Estende CrudController per gestire il ciclo di vita degli utenti.
 *
 * @see CrudController
 * @see UtenteView
 */
public class UtenteController extends CrudController<Utente> {

    /**
     * @brief Costruttore.
     * @param view La vista specifica utenti.
     * @param service Il servizio specifico utenti.
     */
    public UtenteController(UtenteView view, UtenteService service){
        super(view,service);
    }

    /**
     * @brief Aggiorna la vista recuperando tutti gli elementi dal servizio e applicando l'ordinamento (FC-2.2.1, FC-2.2.2).
     * @see InterfaceController#aggiornaVista()
     * @see InterfaceService#getAll()
     */
    @Override
    public void aggiornaVista() {
        List<Utente> lista = service.getAll();
        
        // FC-2.2.1, FC-2.2.2: Ordinamento primario per Cognome, secondario per Nome
        lista.sort(Comparator
                .comparing(Utente::getCognome, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(Utente::getNome, String.CASE_INSENSITIVE_ORDER)
        );
        
        view.mostraLista(lista);
    }

    /**
     * @brief Inizializza il controller e collega i listener ai pulsanti della vista.
     */
    @Override
    public void init() {
        super.init();
        
        UtenteView view = getSpecificView();
        
        // 1. Listener per Inserimento (OK) e Modifica (OK)
        view.getOkButton().setOnAction(e -> {
            // Se un elemento è selezionato, si assume Modifica. Altrimenti, Salva nuovo.
            if (view.getTableView().getSelectionModel().getSelectedItem() != null) {
                modificaUtente();
            } else {
                salvaUtente();
            }
        });
        
        // 2. Listener per Cancellazione
        view.getCancellaButton().setOnAction(e -> elimina());
        
        // 3. Listener per la Ricerca (CORREZIONE: Aggiorna al cambiamento del testo)
        view.getCercaField().textProperty().addListener((observable, oldValue, newValue) -> {
            cerca();
        });
        
        // Rimosso: Listener per il pulsante "Nuovo"
    }

    //DA RIVEDERE DOXYGEN
    /**
     * @brief  Interpreta la vista come un'istanza di UtenteView.
     * @return L'istanza di UtenteView associata al controller altrimenti un valore nullo.
     */
    private UtenteView getSpecificView() {
        return (UtenteView) view;
    }
    
    /**
     * @brief Gestisce l'acquisizione dati e il salvataggio di un nuovo utente.
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
     * @brief Gestisce l'acquisizione dati e l'aggiornamento di un utente.
     * @see UtenteView#getUtenteModificato()
     */
    public void modificaUtente(){
        Utente modificato = getSpecificView().getUtenteModificato();
        if (modificato == null) return;

        modifica(modificato);
    }

    /**
     * @brief Implementazione del salvataggio utente.
     * @param nuovo L'utente da salvare.
     */
    @Override
    public void salva(Utente nuovo){
        eseguiOperazione(() -> service.salva(nuovo), "Utente inserito correttamente.");
    }

    /**
     * @brief Implementazione  della modifica utente.
     * @param elemento L'utente aggiornato.
     */
    @Override
    public void modifica(Utente elemento){
        eseguiOperazione(() -> service.modifica(elemento), "Dati utente aggiornati correttamente.");
    }
}