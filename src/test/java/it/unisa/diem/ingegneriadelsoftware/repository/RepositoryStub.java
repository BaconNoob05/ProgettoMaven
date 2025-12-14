package it.unisa.diem.ingegneriadelsoftware.repository;
import it.unisa.diem.ingegneriadelsoftware.repository.*;
import it.unisa.diem.ingegneriadelsoftware.model.*;
import java.util.*;

/**
 * @brief Implementazione Stub dell'InterfaceRepository.
 * @class RepositoryStub
 */
public class RepositoryStub<T extends InterfaceID> implements InterfaceRepository<T> {

    /**
     * @brief Lista che serve per memorizzare i dati.
     */
    private List<T> lista = new ArrayList<>();

    
    /**
     * @brief Carica i dati nella lista  dello Stub.
     */
    @Override
    public void caricaTutti(List<T> dati) {
        if (dati == null) return;
        lista.addAll(dati);
    }

    /**
     * @brief Simula il salvataggio dei dati su file.
     */
    @Override
    public void salvaSuFile() { }

    /**
     * @brief Inserisce un nuovo elemento o aggiorna un elemento esistente.
     */
    @Override
    public void inserisciOAggiorna(T elemento) {
        elimina(elemento.getId());
        lista.add(elemento);
    }

    /**
     * @brief Rimuove un elemento dal repository tramite ID.
     */
    @Override
    public void elimina(String id) {
        lista.removeIf(e -> e.getId().equals(id));
    }

    /**
     * @brief Cerca un elemento nel repository tramite ID.
     */
    @Override
    public T cerca(String id) {
        return lista.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * @brief Restituisce una copia degli elementi del repository.
     */
    @Override
    public List<T> getAll() { return new ArrayList<>(lista); }
}
