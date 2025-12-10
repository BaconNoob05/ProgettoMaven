/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.ingegneriadelsoftware.repository;
import it.unisa.diem.ingegneriadelsoftware.repository.*;
import it.unisa.diem.ingegneriadelsoftware.model.*;
import java.util.*;


public class RepositoryStub<T extends InterfaceID> implements InterfaceRepository<T> {

    private List<T> lista = new ArrayList<>();

    @Override
    public void caricaTutti(List<T> lista) { }

    @Override
    public void salvaSuFile() { }

    @Override
    public void inserisciOAggiorna(T elemento) {
        elimina(elemento.getId());
        lista.add(elemento);
    }

    @Override
    public void elimina(String id) {
        lista.removeIf(e -> e.getId().equals(id));
    }

    @Override
    public T cerca(String id) {
        return lista.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<T> getAll() { return new ArrayList<>(lista); }
}

