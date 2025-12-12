/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.ingegneriadelsoftware.service;

import it.unisa.diem.ingegneriadelsoftware.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LibroServiceStub extends LibroService{
    public List<Libro> lista = new ArrayList<>();
    public boolean salvaChiamato = false;
    public boolean modificaChiamato = false;

    public LibroServiceStub() {
        super(null); 
    }

    @Override
    public void salva(Libro libro) {
        this.salvaChiamato = true;
        lista.add(libro);
    }

    @Override
    public void modifica(Libro libro) {
        this.modificaChiamato = true;
        lista.removeIf(u -> u.getId().equals(libro.getId()));
        lista.add(libro);
    }
        
    @Override
    public void elimina(Libro libro) {
        lista.removeIf(u -> u.getId().equals(libro.getId()));
    }
        
    @Override
    public List<Libro> cercaGenerico(String filtro) {
    if (filtro == null || filtro.isEmpty()) return getAll();
        //Da vedere
        return lista.stream()
                .filter(u -> u.toString().toLowerCase().contains(filtro.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Libro> getAll() {
        return new ArrayList<>(lista);
    }
}
