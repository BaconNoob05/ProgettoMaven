package it.unisa.diem.ingegneriadelsoftware.service;

import it.unisa.diem.ingegneriadelsoftware.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @brief Implementazione Stub per LibroService.
 * @class LibroServiceStub
 */
public class LibroServiceStub extends LibroService{
    
    /**
     * @brief Lista per il salvataggio dei dati.
     */
    public List<Libro> lista = new ArrayList<>();
    
    /**
     * @brief Flag per testare un metodo .
     */
    public boolean salvaChiamato = false;
    
    /**
     * @brief Flag per testare un metodo.
     */
    public boolean modificaChiamato = false;

    /**
     * @brief Costruttore dello Stub.
     */
    public LibroServiceStub() {
        super(null); 
    }

    /**
     * @brief Simula il salvataggio di un libro.
     */
    @Override
    public void salva(Libro libro) {
        this.salvaChiamato = true;
        lista.add(libro);
    }

    /**
     * @brief Simula la modifica di un libro.
     */
    @Override
    public void modifica(Libro libro) {
        this.modificaChiamato = true;
        lista.removeIf(u -> u.getId().equals(libro.getId()));
        lista.add(libro);
    }
        
    /**
     * @brief Simula l'eliminazione di un libro.
     */
    @Override
    public void elimina(Libro libro) {
        lista.removeIf(u -> u.getId().equals(libro.getId()));
    }
        
    /**
     * @brief Simula la ricerca di libri in base a un filtro .
     */
    @Override
    public List<Libro> cercaGenerico(String filtro) {
    if (filtro == null || filtro.isEmpty()) return getAll();
        //Da vedere
        return lista.stream()
                .filter(u -> u.toString().toLowerCase().contains(filtro.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * @brief Restituisce tutti i libri in memoria.
     */
    @Override
    public List<Libro> getAll() {
        return new ArrayList<>(lista);
    }
}
