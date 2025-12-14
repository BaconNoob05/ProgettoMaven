package it.unisa.diem.ingegneriadelsoftware.service;

import it.unisa.diem.ingegneriadelsoftware.model.*;
import it.unisa.diem.ingegneriadelsoftware.repository.*;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
/**
 * @brief Classe di test per PrestitoService.
 * @class PrestitoServiceTest
 */
public class PrestitoServiceTest {
    
    /**
     * @brief Istanza del servizio Prestito .
     */
    private PrestitoService service;
    
    /**
     * @brief Stub del Repository di prestito.
     */
    private RepositoryStub<Prestito> repo;
    
    /**
     * @brief Istanza di Libro.
     */
    private LibroService libroService;
    
    /**
     * @brief Stub del Repository  di libri.
     */
    private RepositoryStub<Libro> repoLibri;
    
    /**
     * @brief Libro per test.
     */
    private Libro l1;
    
    /**
     * @brief Libro per test.
     */
    private Libro l2;
    
    /**
     * @brief Utente per test.
     */
    private Utente u1;
    
    /**
     * @brief Utente per test.
     */
    private Utente u2;
        
   /**
     * @brief Metodo eseguito prima di ogni test.
     */
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

        u1 = new Utente("Lorenzo", "Trovato", "0612709999", "l.trovato@studenti.unisa.it");
        u2 = new Utente("alessandro", "picariello", "0612709975", "a.picariello@studenti.unisa.it");
        
        repoLibri.inserisciOAggiorna(l1);
        repoLibri.inserisciOAggiorna(l2);
    }

    /**
     * @brief Testa la registrazione di un prestito con dati validi.
     */
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
        assertEquals(LocalDate.now(), prestitoSalvato.getDataPrestito()); // VERIFICA DATA DI OGGI
    }
    
    /**
     * @brief Testa la registrazione di un prestito per un libro con poche copie.
     */
    @Test
    void testRegistraPrestito_LibroConPocheCopie() {
        service.registraPrestito(u2, l2, LocalDate.now().plusDays(30)); 
        
        assertEquals(1, repo.getAll().size());
        assertEquals(7, l2.getCopieDisponibili());
    }
    
    /**
     * @brief Testa la registrazione di un prestito con Utente nullo.
     */
    @Test
    void testRegistraPrestito_UtenteNullo() {
        assertThrows(IllegalArgumentException.class, () -> {
             service.registraPrestito(null, l1, LocalDate.now().plusDays(30));
        });
        assertEquals(12, l1.getCopieDisponibili());
        assertEquals(0, repo.getAll().size());
    }
    
    /**
     * @brief Testa la registrazione di un prestito con Libro nullo.
     */
    @Test
    void testRegistraPrestito_LibroNullo() {
        assertThrows(IllegalArgumentException.class, () -> {
             service.registraPrestito(u1, null, LocalDate.now().plusDays(30));
        });
        assertEquals(0, repo.getAll().size());
    }
    
    /**
     * @brief Testa la registrazione di un prestito con data passata.
     */
    @Test
    void testRegistraPrestito_DataPrevistaPassata() {
        LocalDate dataPassata = LocalDate.now().minusDays(1);
        
        assertThrows(IllegalArgumentException.class, () -> {
             service.registraPrestito(u1, l1, dataPassata);
        });
        assertEquals(0, repo.getAll().size());
        assertEquals(12, l1.getCopieDisponibili());
    }

    /**
     * @brief Testa la registrazione della restituzione di un prestito attivo.
     */
    @Test
    void testRegistraRestituzione_Successo() {

        LocalDate dataStorica = LocalDate.now().minusDays(5);
        Prestito p = new Prestito(u1, l1, LocalDate.now().plusDays(30), dataStorica);
        repo.inserisciOAggiorna(p);
        l1.setCopieDisponibili(11); // Stato iniziale dopo il prestito

        service.registraRestituzione(p, LocalDate.now());

        assertNotNull(p.getDataEffettiva());
        assertEquals(12, l1.getCopieDisponibili()); 
    }

    /**
     * @brief Testa la registrazione della restituzione per un prestito già chiuso.
  
     */
    @Test
    void testRegistraRestituzione_PrestitoGiaChiuso() {

        LocalDate dataStorica = LocalDate.now().minusDays(30);
        Prestito p = new Prestito(u1, l1, LocalDate.now().plusDays(30), dataStorica);
        p.setDataEffettiva(LocalDate.now().minusDays(5));
        repo.inserisciOAggiorna(p);
        l1.setCopieDisponibili(12); 

        assertThrows(IllegalStateException.class, () -> {
            service.registraRestituzione(p, LocalDate.now());
        });
        
        assertEquals(12, l1.getCopieDisponibili());
    }
    
    /**
     * @brief Testa la registrazione della restituzione con Prestito nullo.
     */
    @Test
    void testRegistraRestituzione_PrestitoNullo() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.registraRestituzione(null, LocalDate.now());
        });
    }

    /**
     * @brief Testa listaPrestitiAttivi.
     */
    @Test
    void testListaPrestitiAttivi_UnoAttivo() {

        LocalDate dataStorica = LocalDate.now().minusDays(5);
        Prestito pAttivo = new Prestito(u1, l1, LocalDate.now().plusDays(30), dataStorica);
        repo.inserisciOAggiorna(pAttivo);

        List<Prestito> attivi = service.listaPrestitiAttivi();
        assertEquals(1, attivi.size());
        assertTrue(attivi.contains(pAttivo));
    }

    /**
     * @brief Testa listaPrestitiAttivi.
     */
    @Test
    void testListaPrestitiAttivi_NessunAttivo() {

        LocalDate dataStorica = LocalDate.now().minusDays(5);
        Prestito pChiuso = new Prestito(u1, l1, LocalDate.now().plusDays(30), dataStorica);
        pChiuso.setDataEffettiva(LocalDate.now());
        repo.inserisciOAggiorna(pChiuso);

        List<Prestito> attivi = service.listaPrestitiAttivi();
        assertTrue(attivi.isEmpty());
    }
    
    /**
     * @brief listaPrestitiAttivi.
     */
    @Test
    void testListaPrestitiAttivi_Misti() {

        LocalDate dataStorica = LocalDate.now().minusDays(10);
        Prestito pAttivo1 = new Prestito(u1, l1, LocalDate.now().plusDays(30), dataStorica);
        repo.inserisciOAggiorna(pAttivo1);

        Prestito pChiuso = new Prestito(u2, l2, LocalDate.now().plusDays(10), dataStorica.minusDays(5));
        pChiuso.setDataEffettiva(LocalDate.now().minusDays(1)); 
        repo.inserisciOAggiorna(pChiuso);
        

        Prestito pAttivo2 = new Prestito(u2, l1, LocalDate.now().plusDays(5), dataStorica.minusDays(1));
        repo.inserisciOAggiorna(pAttivo2);

        List<Prestito> attivi = service.listaPrestitiAttivi();
        
        assertEquals(2, attivi.size());
        assertTrue(attivi.contains(pAttivo1));
        assertTrue(attivi.contains(pAttivo2));
        assertFalse(attivi.contains(pChiuso));
    }
    
    /**
     * @brief Testa  listaPrestitiAttivi.
     */
    @Test
    void testListaPrestitiAttivi_RepositoryVuoto() {
        List<Prestito> attivi = service.listaPrestitiAttivi();
        assertTrue(attivi.isEmpty());
    }
}