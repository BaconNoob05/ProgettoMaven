package it.unisa.diem.ingegneriadelsoftware.repository;

import it.unisa.diem.ingegneriadelsoftware.model.DatiStub;
import org.junit.jupiter.api.*;
import java.io.File;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class GestoreFileTest {

    private GestoreFile<DatiStub> gestore;
    private List<DatiStub> datiTest;
    private List<String> filesDaCancellare;

    @BeforeEach
    void setUp() {
        
        gestore = new GestoreFile<>();
        datiTest = Arrays.asList(
                
            new DatiStub("ID_1"),
            new DatiStub("ID_2")
        );
        filesDaCancellare = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        
        for (String fileName : filesDaCancellare) {
            File file = new File(fileName);
            if (file.exists()) {
                file.delete();
            }
        }
    }

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

    @Test
    void testSalvaListaVuota() {
        
        String fileName = "test_lista_vuota.txt";
        filesDaCancellare.add(fileName);

        gestore.salvaDati(fileName, new ArrayList<>());
        List<DatiStub> risultato = gestore.caricaDati(fileName);

        assertNotNull(risultato);
        assertTrue(risultato.isEmpty());
    }

    @Test
    void testSalvaListaNull() {
        
        String fileName = "test_lista_null.txt";
        filesDaCancellare.add(fileName);

        gestore.salvaDati(fileName, null);
        List<DatiStub> risultato = gestore.caricaDati(fileName);

        assertNotNull(risultato);
        assertTrue(risultato.isEmpty());
    }

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

    @Test
    void testCaricaFileInesistente() {
        
        String fileName = "file_inesistente.txt";

        List<DatiStub> risultato = gestore.caricaDati(fileName);

        assertNotNull(risultato);
        assertTrue(risultato.isEmpty());
    }
}
