package it.unisa.diem.ingegneriadelsoftware.controller;

import it.unisa.diem.ingegneriadelsoftware.view.PrestitoView;
import it.unisa.diem.ingegneriadelsoftware.service.PrestitoService;
import it.unisa.diem.ingegneriadelsoftware.model.Prestito;

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
    public PrestitoController(PrestitoView view, PrestitoService service){}

    /**
     * @brief Gestisce la logica di registrazione di un nuovo prestito.
     * @pre La vista deve fornire un Utente valido e un Libro con copie disponibili.
     * @post Le copie del libro vengono decrementate.
     * @see PrestitoView#getPrestitoNuovo()
     * @see PrestitoService#registraPrestito(Utente, Libro, java.time.LocalDate)
     */
    public void registraPrestito(){}

    /**
     * @brief Gestisce la logica di restituzione di un libro.
     * @pre Un prestito attivo deve essere selezionato nella lista.
     * @post Il prestito risulta concluso e le copie del libro vengono incrementate.
     * @see PrestitoView#getDataRestituzione()
     * @see PrestitoService#registraRestituzione(Prestito, java.time.LocalDate)
     */
    public void registraRestituzione(){}

    /**
     * @brief Aggiorna la vista dei prestiti.
     * @see PrestitoService#listaPrestitiAttivi()
     */
    public void aggiornaPrestiti(){}
}