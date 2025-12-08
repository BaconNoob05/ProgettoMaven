package it.unisa.diem.ingegneriadelsoftware.controller;

import it.unisa.diem.ingegneriadelsoftware.view.LibroView;
import it.unisa.diem.ingegneriadelsoftware.service.LibroService;
import it.unisa.diem.ingegneriadelsoftware.model.Libro;

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
    public LibroController(LibroView view, LibroService service){};

    /**
     * @brief Avvia il salvataggio di un nuovo libro.
     * @pre I dati inseriti nel form devono essere validi.
     * @post Il nuovo libro viene validato e passato al servizio.
     * @see LibroView#getLibroNuovo()
     */
    public void salvaLibro(){};

    /**
     * @brief Avvia la modifica di un libro esistente.
     * @details Recupera i dati modificati da LibroView e invoca il metodo modifica.
     * @pre Un libro deve essere in fase di modifica.
     * @post I dati del libro vengono aggiornati.
     * @see LibroView#getLibroModificato()
     */
    public void modificaLibro(){};

    /**
     * @brief Implementazione del salvataggio.
     * @param nuovo Il libro da salvare.
     */
    @Override
    public void salva(Libro nuovo){};

    /**
     * @brief Implementazione  della modifica.
     * @param elemento Il libro aggiornato.
     */
    @Override
    public void modifica(Libro elemento){};
}