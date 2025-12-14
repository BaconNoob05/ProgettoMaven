
package it.unisa.diem.ingegneriadelsoftware.view;


import it.unisa.diem.ingegneriadelsoftware.model.Prestito;
import java.util.List;
import java.time.LocalDate;
/**
 * @brief Implementazione Stub per PrestitoView.
 * @class PrestitoViewStub
 */
public class PrestitoViewStub extends PrestitoView {

    
    /**
     * @brief Dati per un nuovo prestito.
     */
    private Prestito prestitoNuovo;
    
    /**
     * @brief Prestito  per la restituzione.
     */
    private Prestito elementoSelezionato;
    
    /**
     * @brief Data di restituzione .
     */
    private LocalDate dataRestituzione;

    
    /**
     * @brief Lista di prestiti per l'aggiornamento della vista.
     */
    public List<Prestito> listaRicevuta;
    
    /**
     * @brief Ultimo messaggio di notifica o errore inviato dal Controller.
     */
    public String ultimoMessaggio;

    /**
     * @brief Costruttore base dello Stub.
     */
    public PrestitoViewStub() { 
        super(); 
    }

    /**
     * @brief Visualizza la lista inviata dal Controller per l'aggiornamento della lista.
     */
    @Override
    public void mostraLista(List<Prestito> lista) {
        this.listaRicevuta = lista;
    }

    /**
     * @brief Visualizza il messaggio inviato dal Controller.
     */
    @Override
    public void mostraMessaggio(String messaggio) {
        this.ultimoMessaggio = messaggio;
    }
    
    /**
     * @brief Simula il recupero dei dati per un nuovo prestito..
     */
    public Prestito getPrestitoNuovo() { 
        return prestitoNuovo; 
    }

    /**
     * @brief Simula il recupero del prestito selezionato nella vista.
     */
    @Override
    public Prestito getElementoSelezionato() { 
        return elementoSelezionato; 
    }
    
    /**
     * @brief Simula il recupero del campo di ricerca.
     */
    @Override
    public String getCampoCerca() { 

        return "";
    }

    /**
     * @brief Simula il recupero della data di restituzione inserita dall'utente.
     */
    public LocalDate getDataRestituzione() { return 
        dataRestituzione; 
    }

    /**
     * @brief Simula il set per un nuovo prestito.
     */
    public void setPrestitoNuovo(Prestito p) { this.prestitoNuovo = p; }
    
    /**
     * @brief Simula il set per la selezione di un elemento.
     */
    public void setElementoSelezionato(Prestito p) { this.elementoSelezionato = p; }
    
    /**
     * @brief Simula il set per l'inserimento della data di restituzione.
     */
    public void setDataRestituzione(LocalDate d) { this.dataRestituzione = d; }
}
