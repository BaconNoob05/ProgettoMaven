package it.unisa.diem.ingegneriadelsoftware.service;
import it.unisa.diem.ingegneriadelsoftware.model.Libro;
import it.unisa.diem.ingegneriadelsoftware.model.Prestito;
import it.unisa.diem.ingegneriadelsoftware.model.*;
import it.unisa.diem.ingegneriadelsoftware.repository.InterfaceRepository;
import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class UtenteServiceTest {
        
        private UtenteService UtenteService;
        private InterfaceRepositoryStub<Utente> StubRepository;

        @BeforeEach
        void setup() {
            StubRepository = new InterfaceRepositoryStub<>(Utente.class);
            UtenteService = new UtenteService(StubRepository);
            
            StubRepository.salva(new UtenteStub("Lorenzo", "trovato", "0612708922", "l.trovato1@studenti.unisa.it"));
            StubRepository.salva(new UtenteStub("Lorenzo", "trovato", "0612708922", "l.trovato1@studenti.unisa.it"));
            StubRepository.salva(new UtenteStub("Lorenzo", "trovato", "0612708922", "l.trovato1@studenti.unisa.it"));
        }

        @Test
        void TrovatoCercaPerCognome() {
            List<Utente> risultato = UtenteService.cercaPerCognome("Rossi");
            assertNotNull(risultato);
            assertEquals(2, risultato.size());
            assertEquals("Rossi", risultato.get(0).getCognome());
        }

        @Test
        void NonTrovatoCercaPerCognome() {
            List<Utente> risultato = UtenteService.cercaPerCognome("Verdi");
            assertNotNull(risultato);
            assertTrue(risultato.isEmpty());
        }

        @Test
        void NullCercaperCognome() {
            List<Utente> risultato = UtenteService.cercaPerCognome(null);
            assertNotNull(risultato);
            assertTrue(risultato.isEmpty());
        }

    }
