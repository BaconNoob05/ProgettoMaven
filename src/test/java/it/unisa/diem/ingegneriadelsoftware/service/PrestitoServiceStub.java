
package it.unisa.diem.ingegneriadelsoftware.service;

import it.unisa.diem.ingegneriadelsoftware.model.Prestito;
import it.unisa.diem.ingegneriadelsoftware.model.Utente; 
import it.unisa.diem.ingegneriadelsoftware.model.Libro;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
/**
 * @brief Implementazione Stub per PrestitoService.
 * @class PrestitoServiceStub
 */
public class PrestitoServiceStub extends PrestitoService {
    
    /**
     * @brief Lista per il salvataggio dati.
     */
    public List<Prestito> list = new ArrayList<>();
    
    /**
     * @brief Flag per un metodo .
     */
    public boolean registraPrestitoChiamato = false;
    
    /**
    * @brief Flag per un metodo .
     */
    public boolean registraRestituzioneChiamato = false;
    
    /**
     * @brief Costruttore dello Stub.
     */
    public PrestitoServiceStub() { 
        super(null, null); 
    }

    /**
     * @brief Simula la registrazione di un nuovo prestito.
     */
    @Override
    public void registraPrestito(Utente utente, Libro libro, LocalDate dataPrevista) {
        this.registraPrestitoChiamato = true;
        Prestito nuovo = new Prestito(utente, libro, dataPrevista);
        list.add(nuovo);
    }

    /**
     * @brief Simula la registrazione della restituzione di un prestito.
     */
    @Override
    public void registraRestituzione(Prestito prestito, LocalDate dataEffettiva) {
        this.registraRestituzioneChiamato = true;

        for (Prestito p : list) {
          
            if (p.getId() != null && p.getId().equals(prestito.getId())) {
                p.registraRestituzione(dataEffettiva);
                break;
            }
        }
    }

    /**
     * @brief Restituisce solo i prestiti attivi .
     */
    @Override
    public List<Prestito> listaPrestitiAttivi() {
        List<Prestito> attivi = new ArrayList<>();
        for (Prestito p : list) {
            if (p.getDataEffettiva() == null) 
                attivi.add(p);
        }
        return attivi;
    }
    
    /**
     * @brief Restituisce tutti i prestiti presenti in memoria .
     */
    @Override
    public List<Prestito> getAll() {
        return new ArrayList<>(list);
    }
}
