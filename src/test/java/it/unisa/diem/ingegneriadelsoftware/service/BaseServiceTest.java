package it.unisa.diem.ingegneriadelsoftware.service;

import it.unisa.diem.ingegneriadelsoftware.repository.InterfaceRepository;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class TestBaseService {

    private BaseService<Utente> userService;
    private RepositoryStub<Utente> userRepo;
    Utente u1,u2;

    private BaseService<Libro> bookService;
    private RepositoryStub<Libro> bookRepo;
    private Libro l1, l2;

    @BeforeEach
    void setup() {
        userRepo = new RepositoryStub<>();
        userService = new BaseService<>(userRepo);
        u1 = new Utente("Luigi","Bianchi","0612709564","gigi@uni.it");
        u2 = new Utente("Marco","Verdi","0612709783","verdi@uni.it");
        userService.salva(u1);
        userService.salva(u2);

        bookRepo = new RepositoryStub<>();
        bookService = new BaseService<>(bookRepo);

        List<String> autoriLibro1 = Arrays.asList("Michel Foucault");
        List<String> autoriLibro2 = Arrays.asList("Hannah Arendt");

        l1 = new Libro("Il potere e la parola", autoriLibro1,1978,"978-0356429785",2);
        l2 = new Libro("La banalità del male", autoriLibro2, 1963, "978-0299865341",10);
        bookService.salva(l1);
        bookService.salva(l2);
    }


//TEST PER IL METODO cerca(String id) :

//Test relativi alla ricerca di utenti

    @Test
    void testCercaIdEsistenteUtente() {
       Utente utenteTrovato = userService.cerca("0612709564");
        assertNotNull(utenteTrovato);
        assertEquals("0612709564", utenteTrovato.getId());
    }

@Test
    void testCercaIdNonEsistenteUtente() {
       Utente utenteTrovato = userService.cerca("0000000000");
        assertNull(utenteTrovato);
    }

@Test
    void testCercaIdNullUtente() {
        Utente utenteTrovato = userService.cerca(null);
        assertNull(utenteTrovato);
    }

@Test
    void testCercaIdVuotoUtente() {
        Utente utenteTrovato = userService.cerca("");
        assertNull(utenteTrovato);
    }

@Test
    void testCercaRepositoryVuotoUtente() {

        RepositoryStub<Utente> emptyRepo = new RepositoryStub<>();
        BaseService<Utente> emptyService = new BaseService<>(emptyRepo);
        
        Utente utenteTrovato = emptyService.cerca("*");
        assertNull(utenteTrovato);
    }

@Test
    void testCercaPiùUtentiPresenti() {
        
        assertEquals(u1, userService.cerca("0612709564"));
        assertEquals(u2, userService.cerca("0612709783"));
    }

@Test
    void testCercaUtenteAggiornato() {
        Utente u = new Utente("Mario", "Rossi", "0612709783", "rossi@uni.it");
        userService.salva(u);

        Utente uAggiornato = new Utente("Mario", "Rossi", "0612709783", "mariorossi@uni.it");
        userService.modifica(uAggiornato); // usa inserisciOAggiorna nel repo

        Utente utenteTrovato = userService.cerca("0612709783");

        assertEquals("mariorossi@uni.it", utenteTrovato.getEmail());
    }

//Test relativi alla ricerca di libri
@Test
    void testCercaIdEsistenteLibro() {
        Libro libroTrovato = libroService.cerca("978-0356429785");
        assertNotNull(libroTrovato);
        assertEquals("978-0356429785", libroTrovato.getId());
        assertEquals("Il potere e la parola", libroTrovato.getTitolo());
    }

@Test
    void testCercaIdNonEsistenteLibro() {
        Libro libroTrovato = libroService.cerca("000-0000000000");
        assertNull(libroTrovato);
    }

@Test
    void testCercaIdVuotoLibro() {
        Libro libroTrovato = libroService.cerca("");
        assertNull(libroTrovato);
    }

@Test
    void testCercaRepositoryVuotoLibro() {
        RepositoryStub<Libro> emptyRepo = new RepositoryStub<>();
        BaseService<Libro> emptyService = new BaseService<>(emptyRepo);

        Libro libroTrovato = emptyService.cerca("*");
        assertNull(libroTrovato);
    }

@Test
    void testCercaPiùLibriPresenti() {
        assertEquals(l1, bookService.cerca("978-0356429785"));
        assertEquals(l2, bookService.cerca("978-0299865341"));
    }

@Test
    void testCercaLibroAggiornato() {
        List<String> autoriLibro = Arrays.asList("Vittorio Alfieri");
        Libro l = new Libro("Vita scritta da Esso", autoriLibro, 1804, "978-0366218820", 3);
        bookService.salva(l);
        
        Libro lAggiornato = new Libro("Vita scritta da Esso", autoriLibro, 1806, "978-0366218820", 6);
        bookService.salva(lAggiornato);
        Libro lTrovato = libroService.cerca("978-0366218820");
        assertEquals(6, lTrovato.getCopie());
    }
}

