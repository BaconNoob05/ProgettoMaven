package it.unisa.diem.ingegneriadelsoftware.controller;

import it.unisa.diem.ingegneriadelsoftware.view.LibroView;
import it.unisa.diem.ingegneriadelsoftware.service.LibroService;
import it.unisa.diem.ingegneriadelsoftware.model.Libro;
import java.util.Comparator; 
import java.util.List; 

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
     * @param view La vista specifica per i libri.
     * @param service Il servizio specifico per i libri.
     */
    public LibroController(LibroView view, LibroService service){
        super(view,service);
    }
    
   /**
     * @brief Interpreta la vista come un'istanza di LibroView.
     * @return La vista specifica del libro altrimenti restituisce un valore nullo.
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
     * @param nuovo Il libro da salvare.
     */
    @Override
    public void salva(Libro nuovo){
        eseguiOperazione(() -> service.salva(nuovo), "Libro inserito nel catalogo correttamente.");
    }

    /**
     * @brief Implementazione  della modifica.
     * @param elemento Il libro aggiornato.
     */
    @Override
    public void modifica(Libro elemento){
        eseguiOperazione(() -> service.modifica(elemento), "Dati libro aggiornati correttamente.");
    }

    
    /**
     * @brief Inizializza il controller e collega i listener ai pulsanti della vista.
     */
    @Override
    public void init() {
        super.init();
        
        LibroView view = getSpecificView();
        
        // 1. Listener per Inserimento (OK) e Modifica (OK)
        view.getOkButton().setOnAction(e -> {
            // Se un elemento è selezionato, si assume Modifica. Altrimenti, Salva nuovo.
            if (view.getTableView().getSelectionModel().getSelectedItem() != null) {
                modificaLibro();
            } else {
                salvaLibro();
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
}