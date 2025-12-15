package it.unisa.diem.ingegneriadelsoftware.repository;
import java.util.List;
import it.unisa.diem.ingegneriadelsoftware.model.InterfaceID;
import java.util.ArrayList;


/**
 * @file Repository.java
 * @class Repository
 * @brief Coordina le modifiche temporanee ai dati e il loro salvataggio definitivo nei file.
 * @tparam T Tipo di dato che implementa InterfaceID.
 */
public class Repository<T extends InterfaceID> implements InterfaceRepository<T> {
    /**
     * @brief Mantiene una copia locale degli oggetti per consentire operazioni veloci.
     */
    private List<T> lista;

    /**
     * @brief Rappresenta il nome del file su cui i dati vengono salvati o caricati.
     */
    private String nomeFile;

    /**
     * @brief Oggetto responsabile per la lettura e la scrittura dei dati sul file di sistema.
     */
    private InterfaceGestoreIO<T> gestoreIO;

    /**
     * @brief Costruttore del repository.
     * Carica i dati dal file specificato.
     * @param [in] nomeFile Il nome del file.
     * @param [in] gestoreIO Il gestore per l'I/O.
     */
    public Repository(String nomeFile, InterfaceGestoreIO<T> gestoreIO) {
        this.nomeFile = nomeFile;
        this.gestoreIO = gestoreIO;
        this.lista = new ArrayList<>();
        caricaTutti(lista);
    }

    /**
     * @brief Avviene il salvataggio dello stato attuale della lista su file.
     */
    @Override
    public void salvaSuFile() {
        gestoreIO.salvaDati(nomeFile, lista);
    }

    /**
     * @brief Carica tutti gli elementi dal file alla memoria locale del repository.
     * @param [in] lista 
     * @details Popola la lista interna leggendo i dati tramite il gestore di I/O.
     */
    @Override 
    public void caricaTutti(List<T> lista) {
        this.lista = gestoreIO.caricaDati(nomeFile);
        if (this.lista == null) {
            this.lista = new ArrayList<>();
        }
    }

    /**
     * @brief Inserisce un nuovo elemento o ne aggiorna uno esistente.
     * Se esiste già un elemento con lo stesso ID, questo viene sovrascritto.
     * Altrimenti, il nuovo elemento viene aggiunto alla lista.
     * @param [in] elemento L'oggetto da inserire o aggiornare.
     * @throws IllegalArgumentException Se l'elemento è null o ha un ID che assume un valore null, viene lanciata un'eccezione.
     * @pre L'elemento non deve essere null e deve avere un ID valido.
     * @post La lista contiene l'elemento aggiornato.
     */
    @Override
    public void inserisciOAggiorna(T elemento) {
        if (elemento == null || elemento.getId() == null) {
            throw new IllegalArgumentException("L'elemento da inserire non può essere nullo.");
        }
        T elementoEsistente = cerca(elemento.getId());
        if (elementoEsistente != null) {
            int index = lista.indexOf(elementoEsistente);
            if (index != -1) {
                lista.set(index, elemento);
            }
        } else {
            lista.add(elemento);
        }
        salvaSuFile();
    }

    /**
     * @brief Elimina un elemento dato il suo ID.
     * @param [in] id L'identificativo dell'elemento da rimuovere.
     * @post L'elemento viene rimosso dalla lista.
     */
    @Override
    public void elimina(String id) {
        T elementoDaRimuovere = cerca(id);
        if (elementoDaRimuovere != null) {
            lista.remove(elementoDaRimuovere);
            salvaSuFile();
        }
    }

    /**
     * @brief Cerca un elemento tramite il suo ID.
     * @param [in] id L'identificativo da cercare.
     * @return L'elemento trovato, altrimenti restituisce un valore nullo.
     */
    @Override
    public T cerca(String id) {
        if (id == null) return null;
        for (T elemento : lista) {
            if (id.equals(elemento.getId())) {
                return elemento;
            }
        }
        return null;
    }

    /**
     * @brief Restituisce la lista completa degli elementi gestiti dal repository.
     * @return La lista di tutti gli elementi T presenti nel repository, altrimenti restituisce una lista vuota.
     * @see InterfaceRepository#getAll()
     */
    @Override
    public List<T> getAll() {
        return new ArrayList<>(lista);
    }
}