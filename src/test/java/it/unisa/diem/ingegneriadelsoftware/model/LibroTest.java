/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.ingegneriadelsoftware.model;
import java.util.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
/**
 *
 * @author Utente
 */
public class LibroTest {

    private Libro libro;
    
    private final String TITOLO = "Ingegneria del Software";
    private List<String> autoriEsempio;
    private final int ANNO=1951;
    private final String ISBN_TEST = "978-88-8080-123-4";
    private final int COPIE=5;


    @BeforeEach
    void setUp() {
        autoriEsempio = Arrays.asList("I. Sommerville", "Stephen King");

        libro = new Libro(TITOLO, autoriEsempio, ANNO, ISBN_TEST, COPIE);
    }



    @Test
    void testCostruttoreEGetter() {
        assertEquals(TITOLO, libro.getTitolo());
        assertEquals(ANNO, libro.getAnno());
        assertEquals(ISBN_TEST, libro.getIsbn());
        assertEquals(COPIE, libro.getCopieDisponibili());
    }

    @Test
    void testGetId() {

        assertEquals(ISBN_TEST, libro.getId());
    }

    @Test
    void testGetAutoriString() {
        String autoriAttesi = "I. Sommerville";
        assertTrue(libro.getAutoriString().contains(autoriAttesi));
    }



    @Test
    void testSetTitolo() {
        String nuovoTitolo = "Ciclo di Dune";
        libro.setTitolo(nuovoTitolo);
        assertEquals(nuovoTitolo, libro.getTitolo());
    }

    @Test
    void testSetAnno() {
        int nuovoAnno = 2023;
        libro.setAnno(nuovoAnno);
        assertEquals(nuovoAnno, libro.getAnno());
    }

    @Test
    void testSetCopieDisponibili() {
        libro.setCopieDisponibili(15);
        assertEquals(15, libro.getCopieDisponibili());
    }
    
    @Test
    void testSetCopieDisponibili_Nessuna() {
        libro.setCopieDisponibili(0);
        assertEquals(0, libro.getCopieDisponibili());
    }


    @Test
    void testDecrementaCopie_Successo() {
        int copieIniziali = libro.getCopieDisponibili();        //5
        libro.decrementaCopie();
        assertEquals(copieIniziali - 1, libro.getCopieDisponibili());
    }

    @Test
    void testDecrementaCopie_LimiteZero() {
        // Porta le copie a zero
        Libro libroZeroCopie = new Libro(TITOLO, autoriEsempio, ANNO, ISBN_TEST, 1);
        libroZeroCopie.decrementaCopie(); // Copie = 0

        // Test dell'operazione quando le copie sono già zero
        // Assumiamo che in questo caso venga lanciata una IllegalStateException (non ci sono copie disponibili)
        assertThrows(IllegalStateException.class, () -> {
            libroZeroCopie.decrementaCopie();
        });
    }

    @Test
    void testIncrementaCopie() {
        int copieIniziali = libro.getCopieDisponibili(); // 5
        libro.incrementaCopie();
        assertEquals(copieIniziali + 1, libro.getCopieDisponibili());
    }



    @Test
    void testIsValido() {
        assertTrue(libro.isValido());
    }

    @Test
    void testIsValido_TitoloMancante() {
        Libro nonValido = new Libro(null, autoriEsempio, ANNO, ISBN_TEST, COPIE);
        assertFalse(nonValido.isValido());
    }

    @Test
    void testIsValido_AutoriMancanti() {
        Libro nonValido = new Libro(TITOLO, Collections.emptyList(), ANNO, ISBN_TEST, COPIE);
        assertFalse(nonValido.isValido());
    }

    @Test
    void testIsValido_AnnoNonCorretto() {
        // Ad esempio un anno troppo nel futuro 
        Libro nonValido = new Libro(TITOLO, autoriEsempio, 2050, ISBN_TEST, COPIE);
        assertFalse(nonValido.isValido());
    }
    
    @Test
    void testIsValido_CopieNegative() {
        // Anche se il costruttore dovrebbe impedirlo (o isValido dovrebbe fallire)
        Libro LibroNonValido = new Libro(TITOLO, autoriEsempio, ANNO, ISBN_TEST, -1);
        assertFalse(LibroNonValido.isValido());
    }
    
    @Test
    void testIsValido_IsbnNullo() {
        Libro nonValido = new Libro(TITOLO, autoriEsempio, ANNO, null, COPIE);
        assertFalse(nonValido.isValido());
    }


    @Test
    void testToString_FormatoCorretto() {
        String stringaLibro = libro.toString();
        
        assertNotNull(stringaLibro);
        
        // Verifica la presenza dei dati nel formato stringa
        assertTrue(stringaLibro.contains(TITOLO));
        assertTrue(stringaLibro.contains(Integer.toString(ANNO)));
        assertTrue(stringaLibro.contains(Integer.toString(COPIE)));
        assertTrue(stringaLibro.contains(ISBN_TEST));
    }
}
