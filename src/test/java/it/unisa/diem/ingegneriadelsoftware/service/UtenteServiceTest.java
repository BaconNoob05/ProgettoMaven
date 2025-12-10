package it.unisa.diem.ingegneriadelsoftware.service;

import it.unisa.diem.ingegneriadelsoftware.model.*;
import it.unisa.diem.ingegneriadelsoftware.repository.*;
import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;



public class UtenteServiceTest {

    private UtenteRepositoryStub utenteRepoStub;
    private UtenteService utenteService;


    private Utente utenteMarioRossi;
    private Utente utenteLucaRossi;
    private Utente utenteAnnaVerdi;

    @BeforeEach
    public void setUp() {


        utenteMarioRossi = new Utente("Mario", "Rossi", "M100", "m.r@uni.it"); 

        utenteLucaRossi = new Utente("Luca", "Rossi", "M101", "l.r@uni.it"); 

        utenteAnnaVerdi = new Utente("Anna", "Verdi", "M102", "a.v@uni.it"); 

        utenteRepoStub = new UtenteRepositoryStub();
        List<Utente> initialData = Arrays.asList(utenteMarioRossi, utenteLucaRossi, utenteAnnaVerdi);
        utenteRepoStub.resettaECarica(initialData);

        utenteService = new UtenteService(utenteRepoStub);
    }


    @Test
    void testCercaPerCognome_MatchMultiplo() {
        final String COGNOME_FILTRO = "Rossi";

        // Esecuzione
        List<Utente> risultati = utenteService.cercaPerCognome(COGNOME_FILTRO);

        // Verifica dello stato (Assert)
        assertNotNull(risultati);
        assertEquals(2, risultati.size());
        
        // Verifica che i risultati contengano gli utenti corretti
        assertTrue(risultati.contains(utenteMarioRossi));
        assertTrue(risultati.contains(utenteLucaRossi));
        assertFalse(risultati.contains(utenteAnnaVerdi));
    }


    @Test
    void testCercaPerCognome_MatchSingolo() {
        final String COGNOME_FILTRO = "Verdi";

        // Esecuzione
        List<Utente> risultati = utenteService.cercaPerCognome(COGNOME_FILTRO);

        // Verifica dello stato
        assertNotNull(risultati);
        assertEquals(1, risultati.size());
        assertEquals(utenteAnnaVerdi.getId(), risultati.get(0).getId());
    }


    @Test
    void testCercaPerCognome_NessunMatch() {
        final String COGNOME_FILTRO = "Bianchi";

        // Esecuzione
        List<Utente> risultati = utenteService.cercaPerCognome(COGNOME_FILTRO);

        // Verifica dello stato
        assertNotNull(risultati);
        assertTrue(risultati.isEmpty());
    }
    

    @Test
    void testCercaPerCognome_FiltroNullo() {

        
        // Se il service lancia una IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            utenteService.cercaPerCognome(null);
        }, "Chiamare con cognome nullo dovrebbe lanciare un'eccezione.");


    }
}
