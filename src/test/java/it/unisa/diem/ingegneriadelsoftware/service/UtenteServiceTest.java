package it.unisa.diem.ingegneriadelsoftware.service;

import it.unisa.diem.ingegneriadelsoftware.model.*;
import it.unisa.diem.ingegneriadelsoftware.repository.*;
import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @brief Classe di test per UtenteService.
 * @class UtenteServiceTest
 */
public class UtenteServiceTest {

    /**
     * @brief Stub del Repository .
     */
    private RepositoryStub<Utente> utenteRepoStub;
    
    /**
     * @brief Istanza del servizio Utente .
     */
    private UtenteService utenteService;


    /**
     * @brief Utente per i test.
     */
    private Utente utenteLorenzoTrovato;
    
      /**
     * @brief Utente per i test.
     */
    private Utente utenteAlessandroPicariello;
    
     /**
     * @brief Utente per i test.
     */
    private Utente utenteMatteoIandiorio;
    
      /**
     * @brief Utente per i test.
     */
    private Utente utenteDanieleManzo;

   /**
     * @brief Metodo eseguito prima di ogni test.
     */
    @BeforeEach
    public void setUp() {

        utenteLorenzoTrovato = new Utente("Lorenzo", "Trovato", "0612709999", "l.trovato@studenti.unisa.it"); 
        utenteAlessandroPicariello = new Utente("alessandro", "picariello", "0612709975", "a.picariello@studenti.unisa.it"); 
        utenteMatteoIandiorio = new Utente("matteo", "iandiorio", "0612709968", "m.iandiorio@studenti.unisa.it"); 
        utenteDanieleManzo = new Utente("daniele", "manzo", "0612709967", "d.manzo@studenti.unisa.it"); 
        
        utenteRepoStub = new RepositoryStub<>();
        List<Utente> initialData = Arrays.asList(
            utenteLorenzoTrovato, 
            utenteAlessandroPicariello, 
            utenteMatteoIandiorio,
            utenteDanieleManzo
        );
        utenteRepoStub.caricaTutti(initialData);

        utenteService = new UtenteService(utenteRepoStub);
    }


    /**
     * @brief Testa la ricerca per cognome in base a un filtro con zero risultati.
     */
    @Test
    void testCercaPerCognome_MatchMultiplo_Nessuno() {
        final String COGNOME_FILTRO = "Rossi";

        List<Utente> risultati = utenteService.cercaPerCognome(COGNOME_FILTRO);

        assertNotNull(risultati);
        assertEquals(0, risultati.size());
    }


    /**
     * @brief Testa la ricerca per cognome in base a un filtro con un singolo risultato .
     */
    @Test
    void testCercaPerCognome_MatchSingolo() {
        final String COGNOME_FILTRO = "iandiorio";

        List<Utente> risultati = utenteService.cercaPerCognome(COGNOME_FILTRO);

        assertNotNull(risultati);
        assertEquals(1, risultati.size());
        assertEquals(utenteMatteoIandiorio.getId(), risultati.get(0).getId());
    }


    /**
     * @brief Testa la ricerca per cognome in base a un filtro con zero risultati .
     */
    @Test
    void testCercaPerCognome_NessunMatch() {
        final String COGNOME_FILTRO = "Bianchi";

        List<Utente> risultati = utenteService.cercaPerCognome(COGNOME_FILTRO);

        assertNotNull(risultati);
        assertTrue(risultati.isEmpty());
    }
    
    /**
     * @brief Testa la ricerca per cognome con un filtro nullo.
     */
    @Test
    void testCercaPerCognome_FiltroNullo() {
        assertThrows(IllegalArgumentException.class, () -> {
            utenteService.cercaPerCognome(null);
        });
    }

    /**
     * @brief Testa la ricerca per cognome con il case-insensitive.
     */
    @Test
    void testCercaPerCognome_CaseSensitivityEsatta() {
        final String COGNOME_FILTRO = "Trovato";

        List<Utente> risultati = utenteService.cercaPerCognome(COGNOME_FILTRO);

        assertNotNull(risultati);
        assertEquals(1, risultati.size());
        assertTrue(risultati.contains(utenteLorenzoTrovato));
    }

    /**
     * @brief Testa la ricerca per cognome con un filtro in minuscolo.
     */
    @Test
    void testCercaPerCognome_CaseSensitivityMinuscola() {
        final String COGNOME_FILTRO = "manzo";

        List<Utente> risultati = utenteService.cercaPerCognome(COGNOME_FILTRO);

        assertNotNull(risultati);
        assertEquals(1, risultati.size());
        assertTrue(risultati.contains(utenteDanieleManzo));
    }
    
    /**
     * @brief Testa la ricerca per cognome con un filtro vuoto.
     */
    @Test
    void testCercaPerCognome_FiltroVuoto() {
        final String COGNOME_FILTRO = "";

        List<Utente> risultati = utenteService.cercaPerCognome(COGNOME_FILTRO);

        assertNotNull(risultati);
        assertTrue(risultati.isEmpty());
    }

    /**
     * @brief Testa la ricerca per cognome con un filtro contenente solo spazi.
     */
    @Test
    void testCercaPerCognome_FiltroSpazi() {
        final String COGNOME_FILTRO = " ";

        List<Utente> risultati = utenteService.cercaPerCognome(COGNOME_FILTRO);

        assertNotNull(risultati);
        assertTrue(risultati.isEmpty());
    }

    /**
     * @brief Testa la ricerca per cognome con un filtro che ha corrispondenza per due utenti.
     */
    @Test
    void testCercaPerCognome_AltroSingolo() {
        final String COGNOME_FILTRO = "Trovato";
        
 
        Utente utenteSecondoTrovato = new Utente("Giulia", "Trovato", "0612701111", "g.trovato@studenti.unisa.it"); 
        utenteRepoStub.inserisciOAggiorna(utenteSecondoTrovato);
        
        List<Utente> risultati = utenteService.cercaPerCognome(COGNOME_FILTRO);

        assertNotNull(risultati);
        assertEquals(2, risultati.size());
        assertTrue(risultati.contains(utenteLorenzoTrovato));
        assertTrue(risultati.contains(utenteSecondoTrovato));
    }
    
    /**
     * @brief Testa la ricerca per cognome con un filtro che corrisponde a un utente il cui cognome è in minuscolo.
     */
    @Test
    void testCercaPerCognome_AltroSingoloMinuscolo() {
        final String COGNOME_FILTRO = "picariello";

        List<Utente> risultati = utenteService.cercaPerCognome(COGNOME_FILTRO);

        assertNotNull(risultati);
        assertEquals(1, risultati.size());
        assertTrue(risultati.contains(utenteAlessandroPicariello));
    }
}