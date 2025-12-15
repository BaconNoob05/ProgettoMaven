package it.unisa.diem.ingegneriadelsoftware.service;

import it.unisa.diem.ingegneriadelsoftware.model.Utente;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * @brief Implementazione Stub per UtenteService.
 * @class UtenteServiceStub
 */
public class UtenteServiceStub extends UtenteService{
    
    /**
     * @brief Lista per il salvataggio dei dati.
     */
    public List<Utente> lista = new ArrayList<>();
    
    /**
     * @brief Flag per i metodi.
     */
    public boolean salvaChiamato = false;
    
    /**
     * @brief @brief Flag per i metodi.
     */
    public boolean modificaChiamato = false;

    /**
     * @brief Costruttore dello Stub.
     */
    public UtenteServiceStub() {
        // CORREZIONE: Chiamata al costruttore di UtenteService che ora richiede
        // InterfaceRepository<Utente> e PrestitoService.
        super(null, null); 
    }

    /**
     * @brief Simula il salvataggio di un utente.
     */
    @Override
    public void salva(Utente utente) {
        this.salvaChiamato = true;
        lista.add(utente);
    }

    /**
     * @brief Simula la modifica di un utente.
     */
    @Override
    public void modifica(Utente utente) {
        this.modificaChiamato = true;
        lista.removeIf(u -> u.getId().equals(utente.getId()));
        lista.add(utente);
    }
        
    /**
     * @brief Simula l'eliminazione di un utente.
     */
    @Override
    public void elimina(Utente utente) {
        // La logica di eliminazione qui è semplificata e non esegue il controllo sui prestiti attivi, 
        // che è responsabilità di UtenteService, non dello stub.
        lista.removeIf(u -> u.getId().equals(utente.getId()));
    }
        
    /**
     * @brief Simula la ricerca di utenti con un filtro .
     */
    @Override
    public List<Utente> cercaGenerico(String filtro) {
    if (filtro == null || filtro.isEmpty()) return getAll();
        //Da vedere
        return lista.stream()
                .filter(u -> u.toString().toLowerCase().contains(filtro.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * @brief Restituisce tutti gli utenti in memoria.
     */
    @Override
    public List<Utente> getAll() {
        return new ArrayList<>(lista);
    }
}