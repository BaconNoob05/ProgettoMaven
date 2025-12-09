package it.unisa.diem.ingegneriadelsoftware.service;

import java.util.List;
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
     * @param [in] repo Il repository degli utenti.
     */
    public UtenteService(InterfaceRepository<Utente> repo) {
        super(repo);
    }

    /**
     * @brief Cerca gli utenti che corrispondono a un determinato cognome.
     * @param [in] cognome Il cognome da cercare.
     * @return Una lista di oggetti Utente che rispettano il criterio di ricerca, altrimenti restituisce una lista vuota.
     * @pre La stringa 'cognome' non deve essere null.
     * @post Nessuna modifica viene apportata ai dati.
     * @see BaseService#cercaGenerico(String)
     */
    public List<Utente> cercaPerCognome(String cognome) { 
    
    
        return null;
    }
}
