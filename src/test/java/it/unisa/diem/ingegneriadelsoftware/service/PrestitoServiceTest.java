package it.unisa.diem.ingegneriadelsoftware.service;

import it.unisa.diem.ingegneriadelsoftware.model.*;
import it.unisa.diem.ingegneriadelsoftware.repository.*;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class PrestitoServiceTest {
    
    private PrestitoService service;
    private RepositoryStub<Prestito> repo;
    private LibroService libroService;
    private RepositoryStub<Libro> repoLibri;
    
    private Libro l1;
    private Libro l2;
    private Utente u1;
    private Utente u2;
        
    @BeforeEach
    void setup() {
        
        repo = new RepositoryStub<>();
        repoLibri = new RepositoryStub<>();
        libroService = new LibroService(repoLibri);
        service = new PrestitoService(repo, libroService);
        
        List<String> autoriDeSio = Arrays.asList("Claudio De Sio Cesari");
        List<String> autoriDickens = Arrays.asList("Charles Dickens");

        l1 = new Libro("Il nuovo Java. Guida definitiva", autoriDeSio, 2023, "978-8868945620", 12);
        l2 = new Libro("A Christmas Carol", autoriDickens, 1843, "978-0141439247", 8); 
        
        u1 = new Utente("Lorenzo", "Trovato", "0612709999", "sonoTrovato@uni.com");
        u2 = new Utente("alessandro", "picariello", "0612709975", "picapics@uni.com");
        
        repoLibri.inserisciOAggiorna(l1);
        repoLibri.inserisciOAggiorna(l2);
    }

    @Test
    void testRegistraPrestito_Successo() {
        LocalDate dataPrevista = LocalDate.now().plusDays(30);

        service.registraPrestito(u1, l1, dataPrevista);

        assertEquals(1, repo.getAll().size());
        assertEquals(11, l1.getCopieDisponibili());
        
        Prestito prestitoSalvato = repo.getAll().get(0);
        assertEquals(u1.getId(), prestitoSalvato.getUtente().getId());
        assertEquals(l1.getId(), prestitoSalvato.getLibro().getId());
        assertNull(prestitoSalvato.getDataEffettiva());
    }
    
    @Test
    void testRegistraPrestito_LibroConPocheCopie() {
        service.registraPrestito(u2, l2, LocalDate.now().plusDays(30)); 
        
        assertEquals(1, repo.getAll().size());
        assertEquals(7, l2.getCopieDisponibili());
    }
    
    @Test
    void testRegistraPrestito_UtenteNullo() {
        assertThrows(IllegalArgumentException.class, () -> {
             service.registraPrestito(null, l1, LocalDate.now().plusDays(30));
        });
        assertEquals(12, l1.getCopieDisponibili());
        assertEquals(0, repo.getAll().size());
    }
    
    @Test
    void testRegistraPrestito_LibroNullo() {
        assertThrows(IllegalArgumentException.class, () -> {
             service.registraPrestito(u1, null, LocalDate.now().plusDays(30));
        });
        assertEquals(0, repo.getAll().size());
    }
    
    @Test
    void testRegistraPrestito_DataPrevistaPassata() {
        LocalDate dataPassata = LocalDate.now().minusDays(1);
        
        assertThrows(IllegalArgumentException.class, () -> {
             service.registraPrestito(u1, l1, dataPassata);
        });
        assertEquals(0, repo.getAll().size());
        assertEquals(12, l1.getCopieDisponibili());
    }

    @Test
    void testRegistraRestituzione_Successo() {
        Prestito p = new Prestito(u1, l1, LocalDate.now().plusDays(30));
        repo.inserisciOAggiorna(p);
        l1.setCopieDisponibili(11);

        service.registraRestituzione(p, LocalDate.now());

        assertNotNull(p.getDataEffettiva());
        assertEquals(12, l1.getCopieDisponibili());
    }

    @Test
    void testRegistraRestituzione_PrestitoGiaChiuso() {
        Prestito p = new Prestito(u1, l1, LocalDate.now().plusDays(30));
        p.setDataEffettiva(LocalDate.now().minusDays(5));
        repo.inserisciOAggiorna(p);
        l1.setCopieDisponibili(12);

        assertThrows(IllegalStateException.class, () -> {
            service.registraRestituzione(p, LocalDate.now());
        });
        
        assertEquals(12, l1.getCopieDisponibili());
    }
    
    @Test
    void testRegistraRestituzione_PrestitoNullo() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.registraRestituzione(null, LocalDate.now());
        });
    }

    @Test
    void testListaPrestitiAttivi_UnoAttivo() {
        Prestito pAttivo = new Prestito(u1, l1, LocalDate.now().plusDays(30));
        repo.inserisciOAggiorna(pAttivo);

        List<Prestito> attivi = service.listaPrestitiAttivi();
        assertEquals(1, attivi.size());
        assertTrue(attivi.contains(pAttivo));
    }

    @Test
    void testListaPrestitiAttivi_NessunAttivo() {
        Prestito pChiuso = new Prestito(u1, l1, LocalDate.now().plusDays(30));
        pChiuso.setDataEffettiva(LocalDate.now());
        repo.inserisciOAggiorna(pChiuso);

        List<Prestito> attivi = service.listaPrestitiAttivi();
        assertTrue(attivi.isEmpty());
    }
    
    @Test
    void testListaPrestitiAttivi_Misti() {
        Prestito pAttivo1 = new Prestito(u1, l1, LocalDate.now().plusDays(30));
        repo.inserisciOAggiorna(pAttivo1);
        
        Prestito pChiuso = new Prestito(u2, l2, LocalDate.now().plusDays(10));
        pChiuso.setDataEffettiva(LocalDate.now().minusDays(1)); 
        repo.inserisciOAggiorna(pChiuso);
        
        Prestito pAttivo2 = new Prestito(u2, l1, LocalDate.now().plusDays(5));
        repo.inserisciOAggiorna(pAttivo2);

        List<Prestito> attivi = service.listaPrestitiAttivi();
        
        assertEquals(2, attivi.size());
        assertTrue(attivi.contains(pAttivo1));
        assertTrue(attivi.contains(pAttivo2));
        assertFalse(attivi.contains(pChiuso));
    }
    
    @Test
    void testListaPrestitiAttivi_RepositoryVuoto() {
        List<Prestito> attivi = service.listaPrestitiAttivi();
        assertTrue(attivi.isEmpty());
    }
}
