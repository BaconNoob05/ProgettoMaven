package it.unisa.diem.ingegneriadelsoftware.controller;

import it.unisa.diem.ingegneriadelsoftware.view.LibroView;
import it.unisa.diem.ingegneriadelsoftware.service.LibroService;
import it.unisa.diem.ingegneriadelsoftware.model.Libro;
import java.util.Comparator; 
import java.util.List; 
import javafx.scene.control.Button; // Necessario per il tipo Button

/**
 * @class LibroController
 * @brief Controller specifico per la gestione dei Libri.
 * @details Estende CrudController specializzando le operazioni per l'entità Libro.
 * Si occupa di interagire con LibroView per recuperare i dati specifici dei libri.
 *
 * @see CrudController
 * @see LibroView
 */
public class LibroController extends CrudController<Libro> {

    /**
     * @brief Costruttore.
     * @param [in] view La view specifica per i libri.
     * @param [in] service Il service specifico per i libri.
     */
    public LibroController(LibroView view, LibroService service){
        super(view,service);
    }
    
    /**
     * @brief Interpreta la view come un'istanza di LibroView.
     * @return La view specifica del libro altrimenti restituisce il valore null.
     */
    private LibroView getSpecificView() {
        return (LibroView) view;
    }

    /**
     * @brief Avvia il salvataggio di un nuovo libro.
     * @pre I dati inseriti nel form devono essere validi.
     * @post Il nuovo libro viene validato e passato al servizio.
     * @see LibroView#getLibroNuovo()
     */
    public void salvaLibro(){
        Libro nuovo = getSpecificView().getLibroNuovo();
        if (nuovo == null) return;

        salva(nuovo);
    }
        
    /**
     * @brief Avvia la modifica di un libro esistente.
     * @details Recupera i dati modificati da LibroView e invoca il metodo modifica.
     * @pre Un libro deve essere in fase di modifica.
     * @post I dati del libro vengono aggiornati.
     * @see LibroView#getLibroModificato()
     */
    public void modificaLibro(){
        Libro modificato = getSpecificView().getLibroModificato();
        if (modificato == null) return;

        modifica(modificato);
    }

    /**
     * @brief Implementazione del salvataggio.
     * @param [in] nuovo Il libro da salvare.
     */
    @Override
    public void salva(Libro nuovo){
        eseguiOperazione(() -> service.salva(nuovo), "Libro inserito nel catalogo correttamente.");
    }

    /**
     * @brief Implementazione della modifica.
     * @param [in] elemento Il libro aggiornato.
     */
    @Override
    public void modifica(Libro elemento){
        eseguiOperazione(() -> service.modifica(elemento), "Dati libro aggiornati correttamente.");
    }

    
    /**
     * @brief Inizializza il controller e collega i listener ai pulsanti della view.
     * @see BaseController#init()
     */
    @Override
    public void init() {
        super.init();
        
        LibroView view = getSpecificView();
        
        view.getOkButton().setOnAction(e -> {
            Button pulsante = view.getOkButton();
            String azione;
            Runnable operazione;

            if (view.getTableView().getSelectionModel().getSelectedItem() != null) {
                Libro modificato = view.getLibroModificato();
                if (modificato == null) { view.resetConferma(); return; }
                azione = "aggiornare il libro selezionato";
                operazione = () -> modifica(modificato);
            } else {
                Libro nuovo = view.getLibroNuovo();
                if (nuovo == null) { view.resetConferma(); return; }
                azione = "salvare il nuovo libro";
                operazione = () -> salva(nuovo);
            }
            
            view.richiediConferma(pulsante, operazione, "Clicca di nuovo per " + azione);
        });
        
        view.getCancellaButton().setOnAction(e -> {
            Button pulsante = view.getCancellaButton();
            
            if (view.getElementoSelezionato() == null) {
                view.mostraMessaggio("Seleziona un libro da eliminare.");
                return;
            }
            
            view.richiediConferma(pulsante, this::elimina, "Clicca di nuovo per confermare l'eliminazione.");
        });
        
        view.getAnnullaButton().setOnAction(e -> view.pulisciDettagli());
        
        view.getCercaField().textProperty().addListener((observable, oldValue, newValue) -> {
            cerca();
            view.resetConferma();
        });
        
        view.getAnnullaCercaButton().setOnAction(e -> {
            view.getCercaField().clear();
            aggiornaVista();
            view.mostraMessaggio("Ricerca annullata.");
            view.resetConferma();
        });
    }  
}