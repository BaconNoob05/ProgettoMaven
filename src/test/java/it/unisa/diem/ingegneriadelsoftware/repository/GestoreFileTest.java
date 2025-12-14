package it.unisa.diem.ingegneriadelsoftware.repository;

import it.unisa.diem.ingegneriadelsoftware.model.DatiStub;
import org.junit.jupiter.api.*;
import java.io.File;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
/**
 * @brief Classe di test per GestoreFile.
 * @class GestoreFileTest
 */
public class GestoreFileTest {

    /**
     * @brief Istanza del gestore di file .
     */
    private GestoreFile<DatiStub> gestore;
    
    /**
     * @brief Lista di oggetti  usati per i test.
     */
    private List<DatiStub> datiTest;
    
    /**
     * @brief Lista dei file da cancellare.
     */
    private List<String> filesDaCancellare;

   /**
     * @brief Metodo eseguito prima di ogni test.
     */
    @BeforeEach
    void setUp() {
        
        gestore = new GestoreFile<>();
        datiTest = Arrays.asList(
                
            new DatiStub("ID_1"),
            new DatiStub("ID_2")
        );
        filesDaCancellare = new ArrayList<>();
    }

    /**
     * @brief Cancella tutti i file durante il test.
     */
    @AfterEach
    void tearDown() {
        
        for (String fileName : filesDaCancellare) {
            File file = new File(fileName);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * @brief Testa l'integrità dei dati caricati.
     */
    @Test
    void testSalvaECaricaDati() {
        
        String fileName = "test_salva.txt";
        filesDaCancellare.add(fileName);

        gestore.salvaDati(fileName, datiTest);
        List<DatiStub> risultato = gestore.caricaDati(fileName);

        assertNotNull(risultato);
        assertEquals(datiTest.size(), risultato.size());
        assertEquals(datiTest.get(0).toString(), risultato.get(0).toString());
    }

    /**
     * @brief Testa il salvataggio di una lista vuota.
     */
    @Test
    void testSalvaListaVuota() {
        
        String fileName = "test_lista_vuota.txt";
        filesDaCancellare.add(fileName);

        gestore.salvaDati(fileName, new ArrayList<>());
        List<DatiStub> risultato = gestore.caricaDati(fileName);

        assertNotNull(risultato);
        assertTrue(risultato.isEmpty());
    }

    /**
     * @brief Testa il salvataggio con una lista di valori nulli.
     */
    @Test
    void testSalvaListaNull() {
        
        String fileName = "test_lista_null.txt";
        filesDaCancellare.add(fileName);

        gestore.salvaDati(fileName, null);
        List<DatiStub> risultato = gestore.caricaDati(fileName);

        assertNotNull(risultato);
        assertTrue(risultato.isEmpty());
    }

    /**
     * @brief Verifica che i dati caricati siano gli ultimi salvati.
     */
    @Test
    void testSovrascritturaFile() {
        
        String fileName = "test_sovrascrittura.txt";
        filesDaCancellare.add(fileName);

        gestore.salvaDati(fileName, datiTest);

        List<DatiStub> nuoviDati = Collections.singletonList(new DatiStub("ID_NUOVO_1"));
        gestore.salvaDati(fileName, nuoviDati);

        List<DatiStub> caricati = gestore.caricaDati(fileName);

        assertEquals(1, caricati.size());
        assertEquals(nuoviDati.get(0).toString(), caricati.get(0).toString());
    }

    /**
     * @brief Testa il caricamento dati.
     */
    @Test
    void testCaricaDati() {
        
        String fileName = "test_carica.txt";
        filesDaCancellare.add(fileName);

        gestore.salvaDati(fileName, datiTest);
        List<DatiStub> risultato = gestore.caricaDati(fileName);

        assertNotNull(risultato);
        assertEquals(datiTest.size(), risultato.size());
        assertEquals(datiTest.get(0).toString(), risultato.get(0).toString());
    }

    /**
     * @brief Testa il caricamento di un file che non esiste.
     */
    @Test
    void testCaricaFileInesistente() {
        
        String fileName = "file_inesistente.txt";

        List<DatiStub> risultato = gestore.caricaDati(fileName);

        assertNotNull(risultato);
        assertTrue(risultato.isEmpty());
    }
}
