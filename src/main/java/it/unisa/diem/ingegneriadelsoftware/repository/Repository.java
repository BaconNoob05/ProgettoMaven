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
     * @brief Rappresenta il percorso o il nome del file su cui i dati vengono salvati o caricati.
     */
    private String nomeFile;

    /**
     * @brief Oggetto responsabile per la lettura e la scrittura dei dati sul file di sistema.
     */
    private InterfaceGestoreIO<T> gestoreIO;

    /**
     * @brief Costruttore del repository.
     * Carica i dati dal file specificato.
     * @param [in] file Il nome del file.
     * @param [in] gestore Il gestore per l'I/O.
     */
    public Repository(String file, InterfaceGestoreIO<T> gestore) {
        this.nomeFile = file;
        this.gestoreIO = gestore;
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
     * @brief Inserisce un nuovo elemento o aggiorna uno esistente.
     * Se esiste già un elemento con lo stesso ID, questo viene sovrascritto.
     * Altrimenti, il nuovo elemento viene aggiunto alla lista.
     * @param [in] elemento L'oggetto da inserire o aggiornare.
     * @pre L'elemento non deve essere nullo e deve avere un ID valido.
     * @post La lista contiene l'elemento aggiornato.
     */
    @Override
    public void inserisciOAggiorna(T elemento) {
        
        if (elemento == null || elemento.getId() == null) {
            throw new IllegalArgumentException("L'elemento da inserire non può essere nullo.");
        }


        T esistente = cerca(elemento.getId());

        if (esistente != null) {
            // Aggiorna: trova l'indice e sostituisce
            int index = lista.indexOf(esistente);
            if (index != -1) {
                lista.set(index, elemento);
            }
        } else {
            // Inserisce nuovo elemento
            lista.add(elemento);
        }
        salvaSuFile();
    }

    /**
     * @brief Elimina un elemento dato il suo ID.
     * @param [in] id L'identificativo dell'elemento da rimuovere.
     * @post Non esiste più alcun elemento nella lista con quell'ID.
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