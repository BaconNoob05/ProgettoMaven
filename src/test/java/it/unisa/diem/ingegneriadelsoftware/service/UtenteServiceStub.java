/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.ingegneriadelsoftware.service;

import it.unisa.diem.ingegneriadelsoftware.model.Utente;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UtenteServiceStub extends UtenteService{
    public List<Utente> lista = new ArrayList<>();
    public boolean salvaChiamato = false;
    public boolean modificaChiamato = false;

    public UtenteServiceStub() {
        super(null); 
    }

    @Override
    public void salva(Utente utente) {
        this.salvaChiamato = true;
        lista.add(utente);
    }

    @Override
    public void modifica(Utente utente) {
        this.modificaChiamato = true;
        lista.removeIf(u -> u.getId().equals(utente.getId()));
        lista.add(utente);
    }
        
    @Override
    public void elimina(Utente utente) {
        lista.removeIf(u -> u.getId().equals(utente.getId()));
    }
        
    @Override
    public List<Utente> cercaGenerico(String filtro) {
    if (filtro == null || filtro.isEmpty()) return getAll();
        //Da vedere
        return lista.stream()
                .filter(u -> u.toString().toLowerCase().contains(filtro.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Utente> getAll() {
        return new ArrayList<>(lista);
    }
}
