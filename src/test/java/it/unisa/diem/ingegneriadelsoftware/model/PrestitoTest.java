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
    private final LocalDate DATA_SCADUTA = LocalDate.of(2050, 1, 10); 
    
    /**
     * @brief Data di Prestito per i test (storica).
     */
    private final LocalDate DATA_PRESTITO_STORICA = LocalDate.of(2049, 12, 1); 
    
    /**
     * @brief Lista di autori per i test.
     */
    private List<String> autoriEsempio;

    /**
     * @brief Metodo eseguito prima di ogni test.
     */
    @BeforeEach
    void setup() {
        autoriEsempio = Arrays.asList("I. Sommerville", "Stephen King");
        
        utente = new Utente("Lorenzo", "Trovato", "0612708922", "l.trovato@studenti.unisa.it");
        libro = new Libro("Ingegneria del Software", autoriEsempio, 1951, "978-88-8080-123-4", 5);
        prestito = new Prestito(utente, libro, DATA_SCADUTA, DATA_PRESTITO_STORICA); 
    }

     /**
     * @brief Verifica che il costruttore inizializzi correttamente tutti i campi.
     */
    @Test
    void testCostruttore() {
        assertNotNull(prestito.getUtente());
        assertNotNull(prestito.getLibro());
        assertEquals(DATA_SCADUTA, prestito.getDataPrevista()); 
        assertEquals(DATA_PRESTITO_STORICA, prestito.getDataPrestito()); 
        assertNull(prestito.getDataEffettiva());
    }
    

    /**
     * @brief Testa che il costruttore lanci un'IllegalArgumentException se l'Utente risulta avere un valore nullo.
     */
    @Test
    void testCostruttore_UtenteNullo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Prestito(null, libro, DATA_SCADUTA, DATA_PRESTITO_STORICA);
        });
    }

    /**
     * @brief Testa che il costruttore lanci un'IllegalArgumentException se il Libro risulta avere un valore nullo.
     */
    @Test
    void testCostruttore_LibroNullo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Prestito(utente, null, DATA_SCADUTA, DATA_PRESTITO_STORICA);
        });
    }
    
    /**
     * @brief Testa che il costruttore lanci un'IllegalArgumentException se la data di prestito è nulla. (AGGIUNTO)
     */
    @Test
    void testCostruttore_DataPrestitoNulla() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Prestito(utente, libro, DATA_SCADUTA, null);
        });
    }

    /**
     * @brief Testa l'aggiornamento della data di restituzione.
     */
    @Test
    void testRegistraRestituzione() {
        LocalDate restituzione = LocalDate.of(2050, 1, 15); 
        prestito.registraRestituzione(restituzione);
        assertEquals(restituzione, prestito.getDataEffettiva());
    }
    

    /**
     * @brief Testa che registraRestituzione.
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
        //Forziamo il contesto temporale nel passato per verificare la scadenza
        LocalDate dataPrevistaPassata = LocalDate.now().minusDays(10);
        Prestito pScaduto = new Prestito(utente, libro, dataPrevistaPassata, dataPrevistaPassata.minusDays(5));
        assertTrue(pScaduto.isScaduto());
    }

    /**
     * @brief Testa isScaduto. (Testa la scadenza al giorno stesso)
     */
    @Test
    void testIsScaduto_GiornoDiScadenza() {
        //Simula la data prevista come oggi.
        LocalDate oggi = LocalDate.now();
        Prestito p = new Prestito(utente, libro, oggi, oggi.minusDays(5));
        assertFalse(p.isScaduto()); 
    }

    /**
     * @brief Testa isScaduto. (Testa la scadenza nel futuro)
     */
    @Test
    void testIsScaduto_NonScaduto() {
        //Simula la data prevista come futura.
        LocalDate futura = LocalDate.now().plusDays(3);
        Prestito p = new Prestito(utente, libro, futura, LocalDate.now().minusDays(5));
        assertFalse(p.isScaduto()); 
    }

    /**
     * @brief Testa che un prestito chiuso non sia scaduto.
     */
    @Test
    void testIsScaduto_DopoRestituzione() {
        //Usiamo date passate per assicurarci che sia logicamente scaduto prima della restituzione
        LocalDate dataPrestito = LocalDate.now().minusMonths(2);
        LocalDate dataPrevista = LocalDate.now().minusDays(10);
        
        Prestito p = new Prestito(utente, libro, dataPrevista, dataPrestito);
        
        assertTrue(p.isScaduto());
        p.registraRestituzione(LocalDate.now());
        assertFalse(p.isScaduto());
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
        assertTrue(id.contains(DATA_PRESTITO_STORICA.toString())); 
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
        LocalDate restituzione = LocalDate.of(2050, 1, 15); 
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

        LocalDate dataDiPrestito = LocalDate.now().minusDays(10);
        LocalDate dataPrevista = dataDiPrestito.plusDays(10); 
        LocalDate restituzioneAnticipata = dataPrevista.minusDays(1);
        
        Prestito pNonScaduto = new Prestito(utente, libro, dataPrevista, dataDiPrestito);
        
        assertFalse(pNonScaduto.isScaduto()); 
        
        pNonScaduto.registraRestituzione(restituzioneAnticipata);
        
        assertFalse(pNonScaduto.isScaduto());
        
        assertEquals(restituzioneAnticipata, pNonScaduto.getDataEffettiva());
    }

    /**
     * @brief Testa che l'ID generato sia unico per i diversi prestiti .
     */
    @Test
    void testGetId_Unico() {
        String id1 = prestito.getId(); 

        Utente utente2 = new Utente("Marco", "Rossi", "0000000001", "m.rossi@studenti.unisa.it");

        Prestito prestito2 = new Prestito(utente2, libro, DATA_SCADUTA.plusDays(1), DATA_PRESTITO_STORICA.plusDays(1));
        String id2 = prestito2.getId();

        assertNotEquals(id1, id2);
    }

    @Test
    void testCostruttore_DefaultDataPrestito() {
        LocalDate dataPrevista = LocalDate.now().plusDays(10);

        Prestito nuovoPrestito = new Prestito(utente, libro, dataPrevista); 
        
        assertEquals(LocalDate.now(), nuovoPrestito.getDataPrestito());
        assertEquals(dataPrevista, nuovoPrestito.getDataPrevista());
    }
}