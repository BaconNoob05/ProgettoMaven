package it.unisa.diem.ingegneriadelsoftware.model;

import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
/**
 * @brief Classe di test per la classe Prestito.
 * @class PrestitoTest
 */
public class PrestitoTest {

    /**
     * @brief Utente per i test.
     */
    private Utente utente;
    
    /**
     * @brief Libro per i test.
     */
    private Libro libro;
    
    /**
     * @brief Istanza di Prestito per i test .
     */
    private Prestito prestito;
    
    /**
     * @brief Data per la restituzione per i test.
     */
    private final LocalDate DATA_SETUP = LocalDate.of(2024, 1, 10);
    
    /**
     * @brief Data attuale per i test.
     */
    private final LocalDate DATA_ATTUALE = LocalDate.of(2024, 1, 12);
    
    /**
     * @brief Lista di autori er i test.
     */
    private List<String> autoriEsempio;

    /**
     * @brief Metodo eseguito prima di ogni test.
     */
    @BeforeEach
    void setup() {
        
        
        autoriEsempio = Arrays.asList("I. Sommerville", "Stephen King");
        
        utente = new Utente("Lorenzo", "Trovato", "0612708922", "l.trovato1@studenti.unisa.it");
        libro = new Libro("Ingegneria del Software",autoriEsempio, 1951,"978-88-8080-123-4", 5);
        prestito = new Prestito(utente, libro, DATA_SETUP);
    }

     /**
     * @brief Verifica che il costruttore inizializzi correttamente tutti i campi.
     */
    @Test
    void testCostruttore() {
        assertNotNull(prestito.getUtente());
        assertNotNull(prestito.getLibro());
        assertEquals(DATA_SETUP, prestito.getDataPrevista());
        assertNull(prestito.getDataEffettiva());
    }
    

    /**
     * @brief Testa che il costruttore lanci un'IllegalArgumentException se l'Utente risulta avere un valore nullo.
     */
    @Test
    void testCostruttore_UtenteNullo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Prestito(null, libro, DATA_SETUP);
        });
    }

    /**
     * @brief Testa che il costruttore lanci un'IllegalArgumentException se il Libro risulta avere un valore nullo.
     */
    @Test
    void testCostruttore_LibroNullo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Prestito(utente, null, DATA_SETUP);
        });
    }
    
    
    /**
     * @brief Testa l' aggiornamento della data di restituzione.
     */
    @Test
    void testRegistraRestituzione() {
        LocalDate restituzione = LocalDate.of(2024, 1, 15);
        prestito.registraRestituzione(restituzione);
        assertEquals(restituzione, prestito.getDataEffettiva());
    }
    

    /**
     * @brief Testa che  registraRestituzione.
     */
    @Test
    void testRegistraRestituzione_DataNulla() {
        assertThrows(IllegalArgumentException.class, () -> {
            prestito.registraRestituzione(null);
        });
    }
    

    /**
     * @brief Testa isScaduto .
     */
    @Test
    void testIsScaduto() {
        assertTrue(prestito.isScaduto());
    }

    /**
     * @brief Testa isScaduto .
     */
    @Test
    void testIsScaduto_GiornoDiScadenza() {
        Prestito p = new Prestito(utente, libro, DATA_ATTUALE);
        assertTrue(p.isScaduto());
    }

    /**
     * @brief Testa isScaduto.
     */
    @Test
    void testIsScaduto_NonScaduto() {
        Prestito p = new Prestito(utente, libro, DATA_ATTUALE.plusDays(3));
        assertTrue(p.isScaduto());
    }

    /**
     * @brief Testa che un prestito chiuso non sia scaduto.
     */
    @Test
    void testIsScaduto_DopoRestituzione() {
        assertTrue(prestito.isScaduto());
        prestito.registraRestituzione(DATA_ATTUALE);
        assertFalse(prestito.isScaduto());
    }

    /**
     * @brief Testa che l'ID sia corretto.
     */
    @Test
    void testGetId() {
        
        String id = prestito.getId();
        assertNotNull(id);
        assertTrue(id.contains("0612708922"));
        assertTrue(id.contains("978-88-8080-123-4"));


        assertTrue(id.contains(LocalDate.now().toString())); 
    }

    /**
     * @brief Testa dei getter di Utente e Libro .
     */
    @Test
    void testGetter() {
        assertEquals("Trovato Lorenzo", prestito.getNomeUtente());
        assertEquals("Ingegneria del Software", prestito.getTitoloLibro());
    }

    /**
     * @brief Testa che la stringa contenga il nome dell'utente.
     */
    @Test
    void testToString_NotNull() {
        assertNotNull(prestito.toString());


        assertTrue(prestito.toString().contains("Trovato Lorenzo"));
    }
    
    /**
     * @brief Testa che la stringa ha la data di restituzione effettiva .
     */
    @Test
    void testToString_DopoRestituzione() {
        LocalDate restituzione = LocalDate.of(2024, 1, 15);
        prestito.registraRestituzione(restituzione);

        String stringaPrestito = prestito.toString();

        assertNotNull(stringaPrestito);

        DateTimeFormatter testFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        assertTrue(stringaPrestito.contains(restituzione.format(testFormatter))); 
    }
    
    /**
     * @brief Testa la registrazione della restituzione anticipata .
     */
    @Test
    void testRestituzionePrestito_InAnticipo() {
        LocalDate restituzioneAnticipata = DATA_SETUP.minusDays(1); 
        
        
      
        assertTrue(prestito.isScaduto()); 
        
        prestito.registraRestituzione(restituzioneAnticipata);
        
        assertFalse(prestito.isScaduto());
        
        assertEquals(restituzioneAnticipata, prestito.getDataEffettiva());
    }

    /**
     * @brief Testa che l'ID generato sia unico per i diversi prestiti .
     */
    @Test
    void testGetId_Unico() {

        String id1 = prestito.getId(); 

    
        Utente utente2 = new Utente("Marco", "Rossi", "0000000001", "m.rossi@unisa.it");


        Prestito prestito2 = new Prestito(utente2, libro, DATA_SETUP.plusDays(1));
        String id2 = prestito2.getId();


        assertNotEquals(id1, id2);
    }
}
    

