package it.unisa.diem.ingegneriadelsoftware.view;

import it.unisa.diem.ingegneriadelsoftware.model.Utente;
import java.util.List;
/*
 * @brief Implementazione Stub per UtenteView.
 * @class UtenteViewStub
 */
public class UtenteViewStub extends UtenteView {
    
    /**
     * @brief Dati  per un nuovo utente.
     */
    private Utente utenteNuovo;
    
    /**
     * @brief Dati  per la modifica di un utente.
     */
    private Utente utenteModificato;
    
    /**
     * @brief Utente s selezionato nella vista.
     */
    private Utente elementoSelezionato;
    
    /**
     * @brief Testo  inserito nel campo di ricerca .
     */
    private String testoCerca = "";
    
    /**
     * @brief Lista di utenti per l'aggiornamento della vista.
     */
    public List<Utente> listaRicevuta;
        
    /**
     * @brief Ultimo messaggio di notifica o errore inviato dal Controller.
     */
    public String ultimoMessaggio = null;

    /**
     * @brief Costruttore base dello Stub.
     */
    public UtenteViewStub() {
        super(); 
    }

    /**
     * @brief Simula il recupero dei dati per un nuovo utente.
     */
    @Override
    public Utente getUtenteNuovo() {
        return utenteNuovo;
    }

    /**
     * @brief Simula il recupero dei dati per la modifica di un utente.
     */
    @Override
    public Utente getUtenteModificato() {
        return utenteModificato;
    }

    /**
     * @brief Simula il recupero dell'utente selezionato nella vista.
     */
    @Override
    public Utente getElementoSelezionato() {
        return elementoSelezionato;
    }

    /**
     * @brief Visualizza il messaggio inviato dal Controller.
     */
    @Override
    public void mostraMessaggio(String messaggio) {
        this.ultimoMessaggio = messaggio;
    }

    /**
     * @brief  Visualizza  la lista inviata dal Controller per l'aggiornamento della lista.
   
     */
    @Override
    public void mostraLista(List<Utente> lista) {
        this.listaRicevuta=lista;
    }
        
   
    /**
     * @brief Simula il set per un nuovo utente.
     */
    public void setInputNuovo(Utente u) { this.utenteNuovo = u; }
    
    /**
     * @brief  Simula il set per  un utente modificato.
     */
    public void setInputModificato(Utente u) { this.utenteModificato = u; }
    
    /**
     * @brief  Simula il set per  la selezione di un elemento.
     */
    public void setSelezionato(Utente u) { this.elementoSelezionato = u; }
    
    /**
     * @brief  Simula il set per l'inserimento del testo di ricerca.
     */
    public void setTestoCerca(String s) { this.testoCerca = s; }
}
