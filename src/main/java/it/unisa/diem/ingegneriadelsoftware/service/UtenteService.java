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
     * @post Nessuna modifica viene apportata ai dati.
     * @see BaseService#cercaGenerico(String)
     */
    public List<Utente> cercaPerCognome(String cognome) {

        if (cognome == null || cognome.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String filtro = cognome.toLowerCase();

        return getAll().stream()
            .filter(u -> u.getCognome().toLowerCase().contains(filtro))
            .collect(Collectors.toList());
    }


    /**
     * @brief Esegue una ricerca generica, filtrata per cognome o matricola.
     * @param [in] filtro La stringa di ricerca.
     * @return La lista degli utenti che contengono la stringa inserita nel cognome o all'interno della matricola. In alternativa, restituisce una lista vuota.
     * @note La ricerca è case-insensitive.
     */
    @Override
    public List<Utente> cercaGenerico(String filtro) {
        if (filtro == null || filtro.trim().isEmpty()) {
            return getAll();
        }
        
        String filtroLowerCase = filtro.toLowerCase();
        
        return getAll().stream()
               .filter(u -> u.getCognome().toLowerCase().contains(filtroLowerCase) || 
                            u.getMatricola().toLowerCase().contains(filtroLowerCase))
               .collect(Collectors.toList());
    }
}