package it.unisa.diem.ingegneriadelsoftware.service;
import it.unisa.diem.ingegneriadelsoftware.model.*;
import it.unisa.diem.ingegneriadelsoftware.repository.*;


import it.unisa.diem.ingegneriadelsoftware.service.*;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;




public class PrestitoServiceTest {
    
    private PrestitoService service;
    private RepositoryStub<Prestito> repo;
    private LibroService libroService;
    private RepositoryStub<Libro> repoLibri;
    private Libro l;
    private Utente u;
    private List<String> autoriEsempio;
        
    @BeforeEach
    void setup() {
        repo = new RepositoryStub<>();
        repoLibri = new RepositoryStub<>();
        libroService = new LibroService(repoLibri);
        service = new PrestitoService(repo, libroService);
        
        autoriEsempio = Arrays.asList("I. Sommerville", "Stephen King");

        l = new Libro("Ingegneria del Software",autoriEsempio, 1951,"978-88-8080-123-4", 5);
        u = new Utente("A","B","M1","e@mail");
    }

    @Test
    void testRegistraPrestito() {
        Utente u = new Utente("A","B","M1","e@mail");


        service.registraPrestito(u, l, LocalDate.now());

        assertEquals(1, repo.getAll().size());
        assertEquals(0, l.getCopieDisponibili()); // decremento copie
    }

    @Test
    void testRegistraRestituzione() {


        Prestito p = new Prestito(u, l, LocalDate.now());
        service.salva(p);

        service.registraRestituzione(p, LocalDate.now());

        assertNotNull(p.getDataEffettiva());
        assertEquals(1, l.getCopieDisponibili()); // incrementato
    }

    @Test
    void testListaPrestitiAttivi() {

        Prestito p = new Prestito(u, l, LocalDate.now());
        repo.inserisciOAggiorna(p);

        List<Prestito> attivi = service.listaPrestitiAttivi();
        assertEquals(1, attivi.size());
    }
}
