package it.unisa.diem.ingegneriadelsoftware.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import it.unisa.diem.ingegneriadelsoftware.repository.InterfaceRepository;
import it.unisa.diem.ingegneriadelsoftware.model.Prestito;
import it.unisa.diem.ingegneriadelsoftware.model.Libro;
import it.unisa.diem.ingegneriadelsoftware.model.Utente;
import it.unisa.diem.ingegneriadelsoftware.repository.Repository;

/**
 * @class PrestitoService
 * @brief Classe per la gestione delle operazioni legate al prestito.
 * @details Estende BaseService per le operazioni, come il decremento.
 * @see BaseService
 */
public class PrestitoService extends BaseService<Prestito> {

    /**
     * @brief Riferimento al servizio dei libri.
     * Necessario per aggiornare il numero di copie disponibili quando avviene un prestito o una restituzione.
     */
    private LibroService libroService;
    
    /**
     * @brief Costruttore per la gestione dei prestiti.
     * @param [in] repo Il repository specifico per l'entità Prestito.
     * @param [in] libroService Il servizio per la gestione dei libri.
     */
    public PrestitoService(InterfaceRepository<Prestito> repo, LibroService libroService) {
        super(repo);
        this.libroService = libroService;
    }


    /**
     * @brief Registra un nuovo prestito nel sistema.
     * @param [in] utente L'oggetto Utente che richiede il prestito.
     * @param [in] libro L'oggetto Libro da prestare.
     * @param [in] dataPrevista La data entro cui il libro deve essere restituito.
     * @pre L'utente, il libro e la data di restituzione non devono essere nulli.
     * @pre Il libro deve avere copie disponibili.
     * @post Un nuovo oggetto Prestito viene salvato nel repository.
     * @post Il numero di copie disponibili del libro viene decrementato di 1.
     * @see LibroService
     */
    public void registraPrestito(Utente utente, Libro libro, LocalDate dataPrevista) { 
        if (utente == null || libro == null) {
            throw new IllegalArgumentException("Utente o libro nullo");
        }

        if (dataPrevista == null || dataPrevista.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Data prevista non valida");
        }

        if (libro.getCopieDisponibili() <= 0) {
            throw new IllegalStateException("Nessuna copia disponibile");
        }
            Prestito nuovoPrestito = new Prestito(utente, libro, dataPrevista);
            libro.decrementaCopie();
            libroService.modifica(libro);
            this.salva(nuovoPrestito);  
       
    }
 
    /**
     * @brief Registra la restituzione di un libro chiudendo il prestito.
     * @param [in] prestito L'oggetto Prestito da chiudere.
     * @param [in] dataEffettiva La data in cui avviene la restituzione fisica del libro.
     * @pre Il prestito deve essere ancora attivo.
     * @post Il campo dataEffettiva del prestito viene impostato.
     * @post Il numero di copie disponibili del libro viene incrementato di 1.
     */
    public void registraRestituzione(Prestito prestito, LocalDate dataEffettiva) { 
        if (prestito == null) {
            throw new IllegalArgumentException("Prestito nullo");
        }

        if (prestito.getDataEffettiva() != null) {
            throw new IllegalStateException("Prestito già chiuso");
        }
         
        prestito.registraRestituzione(dataEffettiva);
        this.modifica(prestito);

        Libro libro = prestito.getLibro();
        if (libro != null) 
        {
            libro.incrementaCopie();
            libroService.modifica(libro);
        }
        }

    /**
     * @brief Restituisce la lista dei prestiti ancora attivi.
     * @return Una lista di prestiti non ancora chiusi, altrimenti restituisce una lista vuota.
     * @pre Il repository deve essere inizializzato.
     * @post Lo stato dei dati non viene modificato.
     */
    public List<Prestito> listaPrestitiAttivi() { 
        return getAll().stream().filter(p -> p.getDataEffettiva() == null).collect(Collectors.toList());
    }
      

     /**
     * @brief Ricerca generica per Prestito (es. per nome utente o titolo libro).
     */
    @Override
    public List<Prestito> cercaGenerico(String filtro) {
        if (filtro == null || filtro.isEmpty()) 
            return getAll();
     
        String filtroLowerCase = filtro.toLowerCase();
        
        return getAll().stream()
                .filter(p -> p.getNomeUtente().toLowerCase().contains(filtroLowerCase) || p.getTitoloLibro().toLowerCase().contains(filtroLowerCase))
                .collect(Collectors.toList());
    }
}


