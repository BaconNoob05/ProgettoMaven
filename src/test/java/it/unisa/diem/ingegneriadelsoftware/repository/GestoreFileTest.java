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
    private List<String> fileDaCancellare;

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
        fileDaCancellare = new ArrayList<>();
    }

    /**
     * @brief Cancella tutti i file usati durante il test.
     */
    @AfterEach
    void eliminaFile() {
        for (String nomeFile : fileDaCancellare) {
            File file = new File(nomeFile);
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
        String nomeFile = "test_salva.txt";
        fileDaCancellare.add(nomeFile);

        gestore.salvaDati(nomeFile, datiTest);
        List<DatiStub> risultato = gestore.caricaDati(nomeFile);

        assertNotNull(risultato);
        assertEquals(datiTest.size(), risultato.size());
        assertEquals(datiTest.get(0).toString(), risultato.get(0).toString());
    }

    /**
     * @brief Testa il salvataggio di una lista vuota.
     */
    @Test
    void testSalvaListaVuota() {
        String nomeFile = "test_lista_vuota.txt";
        fileDaCancellare.add(nomeFile);

        gestore.salvaDati(nomeFile, new ArrayList<>());
        List<DatiStub> risultato = gestore.caricaDati(nomeFile);

        assertNotNull(risultato);
        assertTrue(risultato.isEmpty());
    }

    /**
     * @brief Testa il salvataggio con una lista di valori nulli.
     */
    @Test
    void testSalvaListaNull() {
        String nomeFile = "test_lista_null.txt";
        fileDaCancellare.add(nomeFile);

        gestore.salvaDati(nomeFile, null);
        List<DatiStub> risultato = gestore.caricaDati(nomeFile);

        assertNotNull(risultato);
        assertTrue(risultato.isEmpty());
    }

    /**
     * @brief Verifica che i dati caricati siano gli ultimi salvati.
     */
    @Test
    void testSovrascritturaFile() {
        String nomeFile = "test_sovrascrittura.txt";
        fileDaCancellare.add(nomeFile);

        gestore.salvaDati(nomeFile, datiTest);

        List<DatiStub> nuoviDati = Collections.singletonList(new DatiStub("ID_NUOVO_1"));
        gestore.salvaDati(nomeFile, nuoviDati);

        List<DatiStub> caricati = gestore.caricaDati(nomeFile);

        assertEquals(1, caricati.size());
        assertEquals(nuoviDati.get(0).toString(), caricati.get(0).toString());
    }

    /**
     * @brief Testa il caricamento dati.
     */
    @Test
    void testCaricaDati() {
        String nomeFile = "test_carica.txt";
        fileDaCancellare.add(nomeFile);

        gestore.salvaDati(nomeFile, datiTest);
        List<DatiStub> risultato = gestore.caricaDati(nomeFile);

        assertNotNull(risultato);
        assertEquals(datiTest.size(), risultato.size());
        assertEquals(datiTest.get(0).toString(), risultato.get(0).toString());
    }

    /**
     * @brief Testa il caricamento di un file che non esiste.
     */
    @Test
    void testCaricaFileInesistente() {
        String nomeFile = "test_file_inesistente.txt";

        List<DatiStub> risultato = gestore.caricaDati(nomeFile);

        assertNotNull(risultato);
        assertTrue(risultato.isEmpty());
    }
}
