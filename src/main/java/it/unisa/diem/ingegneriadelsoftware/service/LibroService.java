package it.unisa.diem.ingegneriadelsoftware.service;

import java.util.List;
import it.unisa.diem.ingegneriadelsoftware.repository.InterfaceRepository; 
import it.unisa.diem.ingegneriadelsoftware.model.Libro; 

/**
 * @class LibroService
 * @brief Classe per la gestione specifica del catalogo libri.
 * Estende la logica generica di BaseService specializzandola per l'entità Libro.
 */
public class LibroService extends BaseService<Libro> {

    /**
     * @brief Costruttore.
     * @param [in] repo Il repository dei libri.
     */
    public LibroService(InterfaceRepository<Libro> repo) {
        super(repo);
    }

    /**
     * @brief Cerca i libri che corrispondono a un determinato titolo.
     * @param [in] titolo Il titolo da cercare.
     * @return Una lista di oggetti Libro che corrispondono al criterio di ricerca, altrimenti restituisce una lista vuota.
     * @pre Il parametro 'titolo' non deve essere nullo.
     * @post Lo stato del repository rimane invariato.
     * @see BaseService#cercaGenerico(String)
     */
    public List<Libro> cercaPerTitolo(String titolo) {}

    /**
     * @brief Cerca i libri scritti da un determinato autore.
     * @param [in] autore Il nome dell'autore da cercare.
     * @return Una lista di oggetti Libro associati all'autore specificato, altrimenti restituisce una lista vuota.
     * @pre Il parametro 'autore' deve essere una stringa valida.
     * @post Nessuna modifica ai dati.
     * @see Libro#getAutoriString()
     */
    public List<Libro> cercaPerAutore(String autore) {}
}