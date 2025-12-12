package it.unisa.diem.ingegneriadelsoftware.controller;

import it.unisa.diem.ingegneriadelsoftware.view.PrestitoView;
import it.unisa.diem.ingegneriadelsoftware.service.PrestitoService;
import it.unisa.diem.ingegneriadelsoftware.model.Prestito;
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
    
    //DA RIVEDERE DOXYGEN
    private PrestitoView getSpecificView() {
        return (PrestitoView) view;
    }

    //DA RIVEDERE DOXYGEN
    private PrestitoService getSpecificService() {
        return (PrestitoService) service;
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
            aggiornaPrestiti(); 
        }, "Restituzione registrata. Prestito chiuso.");
    }

    /**
     * @brief Aggiorna la vista dei prestiti.
     * @see PrestitoService#listaPrestitiAttivi()
     */
    public void aggiornaPrestiti(){
        List<Prestito> attivi = getSpecificService().listaPrestitiAttivi();
        view.mostraLista(attivi);
    }
}