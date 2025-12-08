package it.unisa.diem.ingegneriadelsoftware.service;

import java.time.LocalDate;
import java.util.List;
import it.unisa.diem.ingegneriadelsoftware.repository.InterfaceRepository;
import it.unisa.diem.ingegneriadelsoftware.model.Prestito;
import it.unisa.diem.ingegneriadelsoftware.model.Libro;
import it.unisa.diem.ingegneriadelsoftware.model.Utente;
import it.unisa.diem.ingegneriadelsoftware.repository.Repository;

/**
 * @class PrestitoService
 * @brief Classe per la gestione delle operazioni legate al prestito.
 * @details Estende BaseService per le operazioni ad esempio di decremento.
 * @see BaseService
 */
public class PrestitoService extends BaseService<Prestito> {

    /**
     * @brief Riferimento al servizio dei libri.
     * Necessario per aggiornare il numero di copie disponibili quando avviene un prestito o una restituzione.
     */
    private LibroService libroService;
    /**
     * @brief Repository specifico per l'accesso ai dati dei prestiti.
     */
    private Repository<Prestito> repository;
    
    /**
     * @brief Costruttore per la gestione dei prestiti.
     * @param [in] repo Il repository specifico per l'entità Prestito.
     * @param [in] libroService Il servizio per la gestione dei libri.
     */
    public PrestitoService(InterfaceRepository<Prestito> repo, LibroService libroService) {
        super(repo);
    }

    /**
     * @brief Registra un nuovo prestito nel sistema.
     * @param [in] utente L'oggetto Utente che richiede il prestito.
     * @param [in] libro L'oggetto Libro da prestare.
     * @param [in] dataPrevista La data entro cui il libro deve essere restituito.
     * @pre L'utente e il libro non devono essere nulli.
     * @pre Il libro deve avere copie disponibili.
     * @post Un nuovo oggetto Prestito viene salvato nel repository.
     * @post Il numero di copie disponibili del libro viene decrementato di 1.
     * @see LibroService
     */
    public void registraPrestito(Utente utente, Libro libro, LocalDate dataPrevista) { }
 
    /**
     * @brief Registra la restituzione di un libro chiudendo il prestito.
     * @param [in] prestito L'oggetto Prestito da chiudere.
     * @param [in] dataEffettiva La data in cui avviene la restituzione fisica del libro.
     * @pre Il prestito deve essere attivo.
     * @post Il campo dataEffettiva del prestito viene impostato.
     * @post Il numero di copie disponibili del libro viene incrementato di 1.
     */
    public void registraRestituzione(Prestito prestito, LocalDate dataEffettiva) { }

    /**
     * @brief Restituisce la lista dei prestiti ancora attivi.
     * @return Una lista di prestiti non ancora chiusi, altrimenti restituisce una lista vuota.
     * @pre Il repository deve essere inizializzato.
     * @post Lo stato dei dati non viene modificato.
     */
    public List<Prestito> listaPrestitiAttivi() { 
    
        return null;
    }
}