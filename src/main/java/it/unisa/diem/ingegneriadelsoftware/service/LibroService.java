package it.unisa.diem.ingegneriadelsoftware.service;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import it.unisa.diem.ingegneriadelsoftware.repository.InterfaceRepository; 
import it.unisa.diem.ingegneriadelsoftware.model.Libro; 

/**
 * @class LibroService
 * @brief Classe per la gestione specifica del catalogo libri.
 * Estende la logica generica di BaseService specializzandola per l'entità Libro.
 */
public class LibroService extends BaseService<Libro> {

    // Riferimento per i controlli incrociati (IF-1.1.2)
    private PrestitoService prestitoService;

    /**
     * @brief Costruttore.
     * @param [in] repository Il repository dei libri.
     */
    public LibroService(InterfaceRepository<Libro> repository) {
        super(repository);
    }
    
    /**
     * @brief Imposta il servizio prestiti per i controlli incrociati (usato in Main.java).
     * @param [in] prestitoService Il servizio prestiti.
     */
    public void setPrestitoService(PrestitoService prestitoService) {
        this.prestitoService = prestitoService;
    }

    /**
     * @brief Elimina un elemento utilizzando il suo ID.
     * @param [in] elemento L'oggetto da eliminare.
     * @pre L'elemento non deve essere null.
     * @post L'elemento viene rimosso dal repository.
     * @see InterfaceRepository#elimina(String)
     */
    @Override
    public void elimina(Libro elemento) {
        if (elemento != null && elemento.getId() != null) {
            
            if (prestitoService != null) {
                boolean haPrestitiAttivi = prestitoService.listaPrestitiAttivi().stream()
                        .anyMatch(p -> p.getLibro().getId().equals(elemento.getId()));
                
                if (haPrestitiAttivi) {
                    throw new IllegalStateException("Impossibile eliminare il libro: sono presenti prestiti attivi associati.");
                }
            }
            repository.elimina(elemento.getId());
        }
    }


    /**
     * @brief Cerca i libri che corrispondono a un determinato titolo.
     * @param [in] titolo Il titolo da cercare.
     * @return Una lista di oggetti Libro che corrispondono al criterio di ricerca, altrimenti restituisce una lista vuota.
     * @pre Il parametro 'titolo' non deve essere nullo.
     * @post Lo stato del repository rimane invariato.
     * @see BaseService#cercaGenerico(String)
     */
    public List<Libro> cercaPerTitolo(String titolo) {
        if (titolo == null || titolo.trim().isEmpty())
            return new ArrayList<>();

        String filtro = titolo.toLowerCase();

        return getAll().stream()
            .filter(l -> l.getTitolo().toLowerCase().contains(filtro))
            .collect(Collectors.toList());
    }


    /**
     * @brief Cerca i libri scritti da un determinato autore.
     * @param [in] autore Il nome dell'autore da cercare.
     * @return Una lista di oggetti Libro associati all'autore specificato, altrimenti restituisce una lista vuota.
     * @pre Il parametro 'autore' deve essere una stringa valida.
     * @post Nessuna modifica ai dati.
     * @see Libro#getAutoriString()
     */
   public List<Libro> cercaPerAutore(String autore) {
       if (autore == null || autore.trim().isEmpty())
           return new ArrayList<>();

       String filtro = autore.toLowerCase();

       return getAll().stream()
           .filter(l -> l.getAutoriString().toLowerCase().contains(filtro))
           .collect(Collectors.toList());
   }


    /**
     * @brief Esegue una ricerca generica filtrando per titolo o autore.
     * @param [in] filtro La stringa di ricerca inserita dall'utente.
     * @return La lista dei libri che contengono la stringa inserita all'interno del titolo o della lista degli autori.
     * @note La ricerca è case-insensitive.
     */
    @Override
    public List<Libro> cercaGenerico(String filtro) {
        if (filtro == null || filtro.trim().isEmpty()) {
            return getAll();
        }
        
        String filtroLowerCase = filtro.toLowerCase();

        return getAll().stream()
               .filter(l -> l.getTitolo().toLowerCase().contains(filtroLowerCase) || 
                            l.getAutoriString().toLowerCase().contains(filtroLowerCase) ||
                            l.getId().toLowerCase().equals(filtroLowerCase))
               .collect(Collectors.toList());
    }
}