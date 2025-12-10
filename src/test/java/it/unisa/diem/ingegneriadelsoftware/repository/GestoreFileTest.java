/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.ingegneriadelsoftware.repository;

import org.junit.jupiter.api.*;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import it.unisa.diem.ingegneriadelsoftware.model.*;
import java.util.ArrayList;

public class GestoreFileTest {
    
    private GestoreFile<DatiStub> gestore;
    private String fileTest;
    private List<DatiStub> datiTest;

    @BeforeEach
    void setUp() {
        gestore = new GestoreFile<>();
        datiTest = new ArrayList<>();
        datiTest.add(new DatiStub("ID_1"));
        datiTest.add(new DatiStub("ID_2"));
    }

    @Test
    void testSalvaDati() {
        fileTest = "test_salva.txt";

        gestore.salvaDati(fileTest, datiTest);

        // Usiamo caricaDati solo per confermare che il salvataggio è stato effettuato
        List<DatiStub> risultato = gestore.caricaDati(fileTest);
        
        assertEquals(datiTest.size(), risultato.size());
        assertEquals(datiTest.get(0), risultato.get(0).toString());
    }

    @Test
    void testSalvaDati_ListaVuota() {
        fileTest = "test_lista_vuota.txt";
        List<DatiStub> listaVuota = new ArrayList<>();
        
        //Salviamo una lista vuota nel file di test
        gestore.salvaDati(fileTest, listaVuota);

        List<DatiStub> risultato = gestore.caricaDati(fileTest);
        assertNotNull(risultato);
        assertTrue(risultato.isEmpty());
    }

    @Test
    void testSalvaDati_ListaNull() {
        fileTest = "test_lista_null.txt";

        //Passiamo null al posto della lista
        assertDoesNotThrow(() -> gestore.salvaDati(fileTest, null));
        
        List<DatiStub> risultato = gestore.caricaDati(fileTest);
        
        assertNotNull(risultato);
        assertTrue(risultato.isEmpty());
    }

    @Test
    void testSalvaDati_Sovrascrittura() {
        fileTest = "test_sovrascrittura.txt";

        gestore.salvaDati(fileTest, datiTest);
        
        //Creiamo e salviamo nuovi dati
        List<DatiStub> nuoviDati = new ArrayList<>();
        nuoviDati.add(new DatiStub("ID_NUOVO_1"));
        gestore.salvaDati(fileTest, nuoviDati);
        
        //Usiamo caricaDati solo per confermare che il salvataggio è stato effettuato
        List<DatiStub> caricati = gestore.caricaDati(fileTest);
        
        //Verichiamo che gli elementi caricati siano solo quelli dell'ultimo salvataggio
        assertEquals(1, caricati.size());
        assertEquals(nuoviDati.get(0).toString(), caricati.get(0).toString());
    }
    
    @Test
    void testCaricaDati() {
        String fileTest = "test_carica.txt";
        
        //Utilizziamo salvaDati solo per creare il file con i dati
        gestore.salvaDati(fileTest, datiTest);

        List<DatiStub> risultato = gestore.caricaDati(fileTest);

        assertNotNull(risultato);
        assertEquals(datiTest.size(), risultato.size());
        assertEquals(datiTest.get(0).toString(), risultato.get(0).toString());
    }

    @Test
    void testCaricaDati_FileInesistente() {
        String fileInesistente = "file_inesistente.txt";
        
        //Carichiamo dati da un file inesistente
        List<DatiStub> risultato = gestore.caricaDati(fileInesistente);
        
        assertNotNull(risultato);
        assertTrue(risultato.isEmpty());
    }
}