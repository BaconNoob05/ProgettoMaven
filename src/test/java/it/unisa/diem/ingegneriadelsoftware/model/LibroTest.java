
package it.unisa.diem.ingegneriadelsoftware.model;
import java.util.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


/**
 * @class LibroTest
 * @brief Classe di test per verificare il comportamento di Libro.
 */
public class LibroTest {
    
    /**
     * @brief Istanza del libro utilizzata per i test.
     */
    private Libro libro;
    
    /**
     * @brief Valore costante per il titolo.
     */
    private final String TITOLO = "Ingegneria del Software";
    
    /**
     * @brief Lista di autori.
     */
    private List<String> autoriEsempio;
    
    /**
     * @brief Valore costante per l'anno di pubblicazione.
     */
    private final int ANNO=1951;
    
    /**
     * @brief Valore costante per l'ISBN.
     */
    private final String ISBN_TEST = "978-88-8080-123-4";
    
    /**
     * @brief Valore costante per il numero di copie.
     */
    private final int COPIE=5;

    /**
     * @brief Metodo eseguito prima di ogni test.
     */
    @BeforeEach
    void setUp() {
        autoriEsempio = Arrays.asList("I. Sommerville", "Stephen King");

        libro = new Libro(TITOLO, autoriEsempio, ANNO, ISBN_TEST, COPIE);
    }

    /**
     * @brief Verifica che il costruttore inizializzi correttamente tutti i campi.
     */
    @Test
    void testCostruttoreEGetter() {
        assertEquals(TITOLO, libro.getTitolo());
        assertEquals(ANNO, libro.getAnno());
        assertEquals(ISBN_TEST, libro.getIsbn());
        assertEquals(COPIE, libro.getCopieDisponibili());
    }
    
    /**
     * @brief Verifica che l'ID restituito sia l'ISBN.
     
     */
    @Test
    void testGetId() {

        assertEquals(ISBN_TEST, libro.getId());
    }
    
    /**
     * @brief Verifica che la stringa degli autori sia corretta.
     */
    @Test
    void testGetAutoriString() {
        String autoriAttesi = "I. Sommerville";
        assertTrue(libro.getAutoriString().contains(autoriAttesi));
    }

    /**
     * @brief Testa che setTitolo funzioni correttamente.
     */
    @Test
    void testSetTitolo() {
        String nuovoTitolo = "Ciclo di Dune";
        libro.setTitolo(nuovoTitolo);
        assertEquals(nuovoTitolo, libro.getTitolo());
    }
    
    /**
     * @brief Testa che settAnno funzioni correttamente. 
     */
    @Test
    void testSetAnno() {
        int nuovoAnno = 2023;
        libro.setAnno(nuovoAnno);
        assertEquals(nuovoAnno, libro.getAnno());
    }
    
    /**
     * @brief Testa l'aggiornamento del numero di copie disponibili.
     */
    @Test
    void testSetCopieDisponibili() {
        libro.setCopieDisponibili(15);
        assertEquals(15, libro.getCopieDisponibili());
    }
    
    /**
     * @brief Testa che il numero di copie sia zero.
    */
    @Test
    void testSetCopieDisponibili_Nessuna() {
        libro.setCopieDisponibili(0);
        assertEquals(0, libro.getCopieDisponibili());
    }

    /**
     * @brief Testa che il numero di copie diminuisce.
     */
    @Test
    void testDecrementaCopie() {
        int copieIniziali = libro.getCopieDisponibili();
        libro.decrementaCopie();
        assertEquals(copieIniziali - 1, libro.getCopieDisponibili());
    }

    /**
     * @brief Testa che venga lanciata `IllegalStateException` quando si tenta di decrementare il numero di copie quando quest'ultimo sia zero.
     */
    @Test
    void testDecrementaCopie_LimiteZero() {
        //Porta le copie a zero
        Libro libroZeroCopie = new Libro(TITOLO, autoriEsempio, ANNO, ISBN_TEST, 1);
        libroZeroCopie.decrementaCopie();


        assertThrows(IllegalStateException.class, () -> {
            libroZeroCopie.decrementaCopie();
        });
    }

    /**
     * @brief Testa che il numero di copie aumenta.
     */
    @Test
    void testIncrementaCopie() {
        int copieIniziali = libro.getCopieDisponibili();
        libro.incrementaCopie();
        assertEquals(copieIniziali + 1, libro.getCopieDisponibili());
    }

    /**
     * @brief Verifica che l'oggetto Libro sia valido.
     */
    @Test
    void testIsValido() {
        assertTrue(libro.isValido());
    }
    
    /**
     * @brief Testa la validazione con titolo nullo.
     */
    @Test
    void testIsValido_TitoloMancante() {
        Libro nonValido = new Libro(null, autoriEsempio, ANNO, ISBN_TEST, COPIE);
        assertFalse(nonValido.isValido());
    }
    
    /**
     * @brief Testa la validazione con lista autori vuota.
     */
    @Test
    void testIsValido_AutoriMancanti() {
        Libro nonValido = new Libro(TITOLO, Collections.emptyList(), ANNO, ISBN_TEST, COPIE);
        assertFalse(nonValido.isValido());
    }
    
    /**
     * @brief Testa la validazione con anno di pubblicazione non corretto.
     */
    @Test
    void testIsValido_AnnoNonCorretto() {
        //Ad esempio un anno troppo nel futuro 
        Libro nonValido = new Libro(TITOLO, autoriEsempio, 2050, ISBN_TEST, COPIE);
        assertFalse(nonValido.isValido());
    }
    /**
     * @brief Testa la validazione con un numero negativo di copie.
     */
    
    @Test
    void testIsValido_CopieNegative() {
        Libro LibroNonValido = new Libro(TITOLO, autoriEsempio, ANNO, ISBN_TEST, -1);
        assertFalse(LibroNonValido.isValido());
    }
    
    /**
     * @brief Testa la validazione con ISBN nullo.
     */
    @Test
    void testIsValido_IsbnNullo() {
        Libro nonValido = new Libro(TITOLO, autoriEsempio, ANNO, null, COPIE);
        assertFalse(nonValido.isValido());
    }

    /**
     * @brief Verifica che il metodo toString() funzioni correttamente.
     */
    @Test
    void testToString_FormatoCorretto() {
        String stringaLibro = libro.toString();
        
        assertNotNull(stringaLibro);
        
        assertTrue(stringaLibro.contains(TITOLO));
        assertTrue(stringaLibro.contains(Integer.toString(ANNO)));
        assertTrue(stringaLibro.contains(Integer.toString(COPIE)));
        assertTrue(stringaLibro.contains(ISBN_TEST));
    }
}
