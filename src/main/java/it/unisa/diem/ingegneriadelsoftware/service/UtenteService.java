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
    
    private PrestitoService prestitoService;

    /**
     * @brief Costruttore.
     * @param [in] repository Il repository degli utenti.
     * @param [in] prestitoService Il servizio per la gestione dei prestiti.
     */
    public UtenteService(InterfaceRepository<Utente> repository, PrestitoService prestitoService) {
        super(repository);
        this.prestitoService = prestitoService;
    }
    
    /**
     * @brief Elimina un elemento utilizzando il suo ID.
     * @param [in] elemento L'oggetto da eliminare.
     * @pre L'elemento non deve essere null.
     * @post L'elemento viene rimosso dal repository.
     * @throws IllegalStateException Se l'utente ha prestiti attivi in corso, non può essere eliminato.
     * @see InterfaceRepository#elimina(String)
     */
    @Override
    public void elimina(Utente elemento) {
        if (elemento != null && elemento.getId() != null) {
            
            if (prestitoService != null) {
                // Controllo se l'utente ha prestiti attivi
                boolean haPrestitiAttivi = prestitoService.listaPrestitiAttivi().stream()
                        .anyMatch(p -> p.getUtente().getId().equals(elemento.getId()));
                
                if (haPrestitiAttivi) {
                    throw new IllegalStateException("Impossibile eliminare l'utente: sono presenti prestiti attivi associati.");
                }
            }
            repository.elimina(elemento.getId());
        }
    }


    /**
     * @brief Cerca gli utenti che corrispondono a un determinato cognome.
     * @param [in] cognome Il cognome da cercare.
     * @return Una lista di oggetti Utente che corrispondono al cognome specificato, altrimenti restituisce una lista vuota.
     * @post Nessuna modifica viene apportata ai dati.
     * @see BaseService#cercaGenerico(String)
     */
    public List<Utente> cercaPerCognome(String cognome) {
        if (cognome == null) {
            throw new IllegalArgumentException("Il cognome di ricerca non può essere nullo.");
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