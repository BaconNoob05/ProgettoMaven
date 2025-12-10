/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.ingegneriadelsoftware.repository;
import it.unisa.diem.ingegneriadelsoftware.repository.*;
import it.unisa.diem.ingegneriadelsoftware.model.Libro;
import java.util.*;


public class LibroRepositoryStub implements InterfaceRepository<Libro> {


    private final Map<String, Libro> libri = new HashMap<>();


    public void clearAndLoad(List<Libro> initialData) {
        libri.clear();
        for (Libro l : initialData) {
            libri.put(l.getId(), l); // L'ID del Libro è l'ISBN
        }
    }
    

    @Override
    public List<Libro> getAll() { 
        return new ArrayList<>(libri.values()); 
    }
    

    @Override public void inserisciOAggiorna(Libro elemento) {  }
    @Override public Libro cerca(String id) { return libri.get(id); }
    @Override public void elimina(String id) { libri.remove(id); }
    @Override public void caricaTutti() { }
    @Override public void salvaSuFile() { }
}
