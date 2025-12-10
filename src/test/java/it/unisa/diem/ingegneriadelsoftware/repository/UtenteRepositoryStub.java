/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.ingegneriadelsoftware.repository;
import it.unisa.diem.ingegneriadelsoftware.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class UtenteRepositoryStub implements InterfaceRepository<Utente> {


    private final Map<String, Utente> utenti = new HashMap<>();


    public void resettaECarica(List<Utente> initialData) {
        utenti.clear();
        for (Utente u : initialData) {
            utenti.put(u.getId(), u);
        }
    }
    


    @Override
    public List<Utente> getAll() { 
        return new ArrayList<>(utenti.values()); 
    }
    
    @Override
    public void inserisciOAggiorna(Utente elemento) {
        if (elemento != null && elemento.getId() != null) {
            utenti.put(elemento.getId(), elemento);
        }
    }
    

    @Override public Utente cerca(String id) { return utenti.get(id); }
    @Override public void elimina(String id) { utenti.remove(id); }
    @Override public void caricaTutti() { }
    @Override public void salvaSuFile() { }
}