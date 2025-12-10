package it.unisa.diem.ingegneriadelsoftware.model;

import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;



/**
 * Classe di test per prestito
 */


public class PrestitoTest {

    private UtenteStub utente;
    private LibroStub libro;
    private Prestito prestito;
    private final LocalDate DATA_SETUP = LocalDate.of(2024, 1, 10);
    private final LocalDate DATA_ATTUALE = LocalDate.of(2024, 1, 12);
    
    private List<String> autoriEsempio;

    @BeforeEach
    void setup() {
        
        
        autoriEsempio = Arrays.asList("I. Sommerville", "Stephen King");
        
        utente = new UtenteStub("Lorenzo", "trovato", "0612708922", "l.trovato1@studenti.unisa.it");
        libro = new LibroStub("Ingegneria del Software",autoriEsempio, 1951,"978-88-8080-123-4", 5);
        prestito = new Prestito(utente, libro, DATA_SETUP);
    }

    @Test
    void testCostruttore() {
        assertNotNull(prestito.getUtente());
        assertNotNull(prestito.getLibro());
        assertEquals(DATA_SETUP, prestito.getDataPrevista());
        assertNull(prestito.getDataEffettiva());
    }
    
    /* DA VEDERE MEGLIO
    @Test
    void testCostruttore_UtenteNullo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Prestito(null, libro, DATA_SETUP);
        });
    }

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
    void testIsScaduto() {
        assertTrue(prestito.isScaduto());
    }

    @Test
    void testIsScaduto_GiornoDiScadenza() {
        Prestito p = new Prestito(utente, libro, DATA_ATTUALE);
        assertFalse(p.isScaduto());
    }

    @Test
    void testIsScaduto_NonScaduto() {
        Prestito p = new Prestito(utente, libro, DATA_ATTUALE.plusDays(3));
        assertFalse(p.isScaduto());
    }

    @Test
    void testIsScaduto_DopoRestituzione() {
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
    void testGetter() {
        assertEquals("Lorenzo Trovato", prestito.getNomeUtente());
        assertEquals("Fondamenti di Programmazione", prestito.getTitoloLibro());
    }

    @Test
    void testToString_NotNull() {
        assertNotNull(prestito.toString());
        assertTrue(prestito.toString().contains("Lorenzo Trovato"));
    }
    
    @Test
    void testToString_DopoRestituzione() {
        LocalDate restituzione = LocalDate.of(2024, 1, 15);
        prestito.registraRestituzione(restituzione);
        
        String stringaPrestito = prestito.toString();
        
        assertNotNull(stringaPrestito);
        
        //Verifica se la data è compresa in toString
        assertTrue(stringaPrestito.contains(restituzione.toString()));
    }
    
    @Test
    void testRestituzionePrestito_InAnticipo() {
        LocalDate restituzioneAnticipata = DATA_SETUP.minusDays(1); // 09/01/2024
        
        
        //deve essere scaduto perchè la data attuale è 12/01/2024
        assertTrue(prestito.isScaduto()); 
        
        prestito.registraRestituzione(restituzioneAnticipata);
        
        //non deve risultare piu scaduto
        assertFalse(prestito.isScaduto());
        
        assertEquals(restituzioneAnticipata, prestito.getDataEffettiva());
    }

    @Test
    void testGetId_Unico() {
        
        //primo id
        String id1 = prestito.getId(); 

        //secondo id (stessa persona ,cambia solo la data)
        Prestito prestito2 = new Prestito(utente, libro, DATA_SETUP.plusDays(1)); // 11/01/2024
        String id2 = prestito2.getId();
        
        //deve verificare che l'id sia unico
        assertNotEquals(id1, id2);
    }
    
    
    
    
}

