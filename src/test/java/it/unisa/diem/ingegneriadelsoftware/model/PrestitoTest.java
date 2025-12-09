package it.unisa.diem.ingegneriadelsoftware.model;

import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classi Stub
 * Per simulare classi esterne ed evitare la dipendenza da Libro e Utente
 */
class UtenteStub extends Utente {

    public UtenteStub() {
        super("Lorenzo", "Trovato", "0612708922", "l.trovato1@studenti.unisa.it");
    }

    @Override
    public String getId() {
        return "UTENTESTUB";
    }
}


class LibroStub extends Libro {

    public LibroStub() {
        super("Libro Stub", Arrays.asList("Autore Stub"), 2024, "ISBNSTUB", 1);
    }

    @Override
    public String getId() {
        return "LIBROSTUB";
    }
}



/**
 * Classe di test per prestito
 */


public class PrestitoTest {

    private UtenteStub utente;
    private LibroStub libro;
    private Prestito prestito;
    private final LocalDate DATA_SETUP = LocalDate.of(2024, 1, 10);
    private final LocalDate DATA_ATTUALE = LocalDate.of(2024, 1, 12);

    @BeforeEach
    void setup() {
        utente = new UtenteStub();
        libro = new LibroStub();
        prestito = new Prestito(utente, libro, DATA_SETUP);
    }

    @Test
    void testCostruttore() {
        assertNotNull(prestito.getUtente());
        assertNotNull(prestito.getLibro());
        assertEquals(DATA_SETUP, prestito.getDataPrevista());
        assertNull(prestito.getDataEffettiva());
    }

    @Test
    void testCostruttore_UtenteNullo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Prestito(null, libro, DATA_SETUP);
        });
    }

    /* DA VEDERE MEGLIO
    @Test
    void testCostruttore_LibroNullo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Prestito(utente, null, DATA_SETUP);
        });
    }
    */
    
    @Test
    void testRegistraRestituzione() {
        LocalDate restituzione = LocalDate.of(2024, 1, 15);
        prestito.registraRestituzione(restituzione);
        assertEquals(restituzione, prestito.getDataEffettiva());
    }
    
    /* DA VEDERE MEGLIO
    @Test
    void testRegistraRestituzione_DataNulla() {
        assertThrows(IllegalArgumentException.class, () -> {
            prestito.registraRestituzione(null);
        });
    }
    */

    @Test
    void testIsScadutoQuandoInRitardo() {
        assertTrue(prestito.isScaduto());
    }

    @Test
    void testIsScadutoAlGiornoDiScadenza() {
        Prestito p = new Prestito(utente, libro, DATA_ATTUALE);
        assertFalse(p.isScaduto());
    }

    @Test
    void testIsScadutoNonScaduto() {
        Prestito p = new Prestito(utente, libro, DATA_ATTUALE.plusDays(3));
        assertFalse(p.isScaduto());
    }

    @Test
    void testIsScadutoDopoRestituzione() {
        assertTrue(prestito.isScaduto());
        prestito.registraRestituzione(DATA_ATTUALE);
        assertFalse(prestito.isScaduto());
    }

    @Test
    void testGetId() {
        String id = prestito.getId();
        assertNotNull(id);
        assertTrue(id.contains("UTENTESTUB"));
        assertTrue(id.contains("LIBROSTUB"));
        assertTrue(id.contains(DATA_SETUP.toString()));
    }

    @Test
    void testGetterNomeUtenteTitoloLibro() {
        assertEquals("Lorenzo Trovato", prestito.getNomeUtente());
        assertEquals("Titolo generico", prestito.getTitoloLibro());
    }

    @Test
    void testToStringNotNull() {
        assertNotNull(prestito.toString());
        assertTrue(prestito.toString().contains("Lorenzo Trovato"));
    }
}
