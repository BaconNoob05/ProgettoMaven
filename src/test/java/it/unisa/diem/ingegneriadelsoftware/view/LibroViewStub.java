package it.unisa.diem.ingegneriadelsoftware.view;

import it.unisa.diem.ingegneriadelsoftware.model.Libro;
import java.util.List;
/**
 * @brief Implementazione Stub per LibroView.
 * @class LibroViewStub
 */
public class LibroViewStub extends LibroView {
    
    /**
     * @brief Dati inseriti dall'utente per un nuovo libro.
     */
    private Libro libroNuovo;
    
    /**
     * @brief Dati  inseriti dall'utente per la modifica di un libro.
     */
    private Libro libroModificato;
    
    /**
     * @brief Libro  selezionato nella vista.
     */
    private Libro elementoSelezionato;
    
    /**
     * @brief Testo  inserito nel campo di ricerca.
     */
    private String testoCerca="";

    /**
     * @brief Lista di libri per l'aggiornamento.
     */
    public List<Libro> listaRicevuta;
    
    /**
     * @brief Ultimo messaggio di notifica o errore inviato dal Controller.
     */
    public String ultimoMessaggio = null;

    /**
     * @brief Costruttore base dello Stub.
     */
    public LibroViewStub() {
        super();
    }
    
    /**
     * @brief Simula il recuper del libro da salvare.
     */
    @Override
    public Libro getLibroNuovo() {
        return libroNuovo;
    }

    /**
     * @brief Simula il recupero del libro con i campi modificati.
     */
    @Override 
    public Libro getLibroModificato() {
        return libroModificato;
    }

    /**
     * @brief Simula il recupero del libro selezionato nella vista.
     */
    @Override
    public Libro getElementoSelezionato() {
        return elementoSelezionato;
    }

    /**
     * @brief Simula il recupero del testo di ricerca inserito dall'utente.
     */
    public String getTestoCerca() {
        return testoCerca;
    }

    /**
     * @brief Visualizza il messaggio inviato dal Controller.
     */
    @Override 
    public void mostraMessaggio(String messaggio) {
        this.ultimoMessaggio = messaggio;
    }

    /**
     * @brief Visualizza la lista inviata dal Controller per l'aggiornamento di essa.
     */
    @Override
    public void mostraLista(List<Libro> lista) {
        this.listaRicevuta = lista;
    }

    /**
     * @brief Simula il set per l'input di un nuovo libro.
     */
    public void setInputNuovo (Libro l) { this.libroNuovo = l; }
    
    /**
     * @brief  Simula il set per l'input di un libro modificato.
     */
    public void setInputModificato (Libro l) { this.libroModificato = l; }
    
    /**
     * @brief Simula il set per la selezione di un elemento.
     */
    public void setSelezionato (Libro l) { this.elementoSelezionato = l; }
    
    /**
     * @brief Simula il set per l'inserimento del testo di ricerca.
     */
    public void setTestoCerca (String s) { this.testoCerca = s; }
    
}


