
Contenuti in evidenza della cartella
Servizi Java che estendono BaseService per gestire Utente, Libro e Prestito, inclusa la logica per prestiti attivi.

package it.unisa.diem.ingegneriadelsoftware.service;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import it.unisa.diem.ingegneriadelsoftware.repository.InterfaceRepository;
import it.unisa.diem.ingegneriadelsoftware.model.Utente;


/**
 * @class UtenteService
 * @brief Classe per gestire i dati utenti specifici.
 * Estende la logica generica di BaseService specializzandola per l'entità Utente.
 */
public class UtenteService extends BaseService<Utente> {
    
    /**
     * @brief Costruttore.
     * @param [in] repository Il repository degli utenti.
     */
    public UtenteService(InterfaceRepository<Utente> repository) {
        super(repository);
    }

    /**
     * @brief Cerca gli utenti che corrispondono a un determinato cognome.
     * @param [in] cognome Il cognome da cercare.
     * @return Una lista di oggetti Utente che rispettano il criterio di ricerca, altrimenti restituisce una lista vuota.
     * @throws IllegalArgumentException Se il cognome è null.
     * @pre La stringa 'cognome' non deve essere null.
     * @post Nessuna modifica viene apportata ai dati.
     * @see BaseService#cercaGenerico(String)
     */
    public List<Utente> cercaPerCognome(String cognome) {

        if (cognome == null) {
            throw new IllegalArgumentException("Filtro nullo");
        }

        if (cognome.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String filtro = cognome.toLowerCase();

        return getAll().stream()
            .filter(u -> u.getCognome().toLowerCase().contains(filtro))
            .collect(Collectors.toList());
    }


    /**
     * @brief Necessario per il funzionamento della barra di ricerca del Controller.
     * Collega la ricerca generica alla ricerca specifica per cognome.
     * @param [in] filtro La stringa di ricerca.
     * @return La lista degli utenti che hanno un cognome corrispondente al filtro.
     */
    @Override
    public List<Utente> cercaGenerico(String filtro) {
        if (filtro == null || filtro.trim().isEmpty()) {
            return getAll();
        }
        
        String filtroLowerCase = filtro.toLowerCase();
        
        // CORREZIONE: Ricerca per Cognome O Matricola
        return getAll().stream()
               .filter(u -> u.getCognome().toLowerCase().contains(filtroLowerCase) || 
                            u.getMatricola().toLowerCase().contains(filtroLowerCase))
               .collect(Collectors.toList());
    }
}