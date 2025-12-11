package it.unisa.diem.ingegneriadelsoftware.service;

import it.unisa.diem.ingegneriadelsoftware.model.*;
import it.unisa.diem.ingegneriadelsoftware.repository.*;
import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;


public class UtenteServiceTest {

    private RepositoryStub<Utente> utenteRepoStub;
    private UtenteService utenteService;


    private Utente utenteLorenzoTrovato;
    private Utente utenteAlessandroPicariello;
    private Utente utenteMatteoIandiorio;
    private Utente utenteDanieleManzo;

    @BeforeEach
    public void setUp() {

        utenteLorenzoTrovato = new Utente("Lorenzo", "Trovato", "0612709999", "sonoTrovato@uni.com"); 
        utenteAlessandroPicariello = new Utente("alessandro", "picariello", "0612709975", "picapics@uni.com"); 
        utenteMatteoIandiorio = new Utente("matteo", "iandiorio", "0612709968", "dior@uni.it"); 
        utenteDanieleManzo = new Utente("daniele", "manzo", "0612709967", "ciaomanzo@uni.it"); 
        
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


    @Test
    void testCercaPerCognome_MatchMultiplo_Nessuno() {
        final String COGNOME_FILTRO = "Rossi";

        List<Utente> risultati = utenteService.cercaPerCognome(COGNOME_FILTRO);

        assertNotNull(risultati);
        assertEquals(0, risultati.size());
    }


    @Test
    void testCercaPerCognome_MatchSingolo() {
        final String COGNOME_FILTRO = "iandiorio";

        List<Utente> risultati = utenteService.cercaPerCognome(COGNOME_FILTRO);

        assertNotNull(risultati);
        assertEquals(1, risultati.size());
        assertEquals(utenteMatteoIandiorio.getId(), risultati.get(0).getId());
    }


    @Test
    void testCercaPerCognome_NessunMatch() {
        final String COGNOME_FILTRO = "Bianchi";

        List<Utente> risultati = utenteService.cercaPerCognome(COGNOME_FILTRO);

        assertNotNull(risultati);
        assertTrue(risultati.isEmpty());
    }
    
    @Test
    void testCercaPerCognome_FiltroNullo() {
        assertThrows(IllegalArgumentException.class, () -> {
            utenteService.cercaPerCognome(null);
        });
    }

    @Test
    void testCercaPerCognome_CaseSensitivityEsatta() {
        final String COGNOME_FILTRO = "Trovato";

        List<Utente> risultati = utenteService.cercaPerCognome(COGNOME_FILTRO);

        assertNotNull(risultati);
        assertEquals(1, risultati.size());
        assertTrue(risultati.contains(utenteLorenzoTrovato));
    }

    @Test
    void testCercaPerCognome_CaseSensitivityMinuscola() {
        final String COGNOME_FILTRO = "manzo";

        List<Utente> risultati = utenteService.cercaPerCognome(COGNOME_FILTRO);

        assertNotNull(risultati);
        assertEquals(1, risultati.size());
        assertTrue(risultati.contains(utenteDanieleManzo));
    }
    
    @Test
    void testCercaPerCognome_FiltroVuoto() {
        final String COGNOME_FILTRO = "";

        List<Utente> risultati = utenteService.cercaPerCognome(COGNOME_FILTRO);

        assertNotNull(risultati);
        assertTrue(risultati.isEmpty());
    }

    @Test
    void testCercaPerCognome_FiltroSpazi() {
        final String COGNOME_FILTRO = " ";

        List<Utente> risultati = utenteService.cercaPerCognome(COGNOME_FILTRO);

        assertNotNull(risultati);
        assertTrue(risultati.isEmpty());
    }

    @Test
    void testCercaPerCognome_AltroSingolo() {
        final String COGNOME_FILTRO = "Trovato";
        
        Utente utenteSecondoTrovato = new Utente("Giulia", "Trovato", "0612701111", "g.t@uni.com"); 
        utenteRepoStub.inserisciOAggiorna(utenteSecondoTrovato);
        
        List<Utente> risultati = utenteService.cercaPerCognome(COGNOME_FILTRO);

        assertNotNull(risultati);
        assertEquals(2, risultati.size());
        assertTrue(risultati.contains(utenteLorenzoTrovato));
        assertTrue(risultati.contains(utenteSecondoTrovato));
    }
    
    @Test
    void testCercaPerCognome_AltroSingoloMinuscolo() {
        final String COGNOME_FILTRO = "picariello";

        List<Utente> risultati = utenteService.cercaPerCognome(COGNOME_FILTRO);

        assertNotNull(risultati);
        assertEquals(1, risultati.size());
        assertTrue(risultati.contains(utenteAlessandroPicariello));
    }
}
