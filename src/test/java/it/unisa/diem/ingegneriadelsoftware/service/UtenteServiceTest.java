package it.unisa.diem.ingegneriadelsoftware.service;
import it.unisa.diem.ingegneriadelsoftware.model.Libro;
import it.unisa.diem.ingegneriadelsoftware.model.Prestito;
import it.unisa.diem.ingegneriadelsoftware.model.Utente;
import it.unisa.diem.ingegneriadelsoftware.repository.InterfaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
static class UtenteServiceTest {
        
        private UtenteService UtenteService;
        private InterfaceRepositoryStub<Utente> StubRepository;

        @BeforeEach
        void setup() {
            StubRepository = new InterfaceRepositoryStub<>(Utente.class);
            UtenteService = new UtenteService(StubRepository);
            
            StubRepository.salva(new UtenteStub("Rossi"));
            StubRepository.salva(new UtenteStub("Bianchi"));
            StubRepository.salva(new UtenteStub("Rossi"));
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
