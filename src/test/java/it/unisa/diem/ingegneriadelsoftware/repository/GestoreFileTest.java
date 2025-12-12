/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.ingegneriadelsoftware.repository;

import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import it.unisa.diem.ingegneriadelsoftware.model.*;


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

        List<DatiStub> risultato = gestore.caricaDati(fileTest);

        assertEquals(datiTest.size(), risultato.size());

        
        assertEquals(datiTest.get(0).toString(), risultato.get(0).toString()); 
    }

    @Test
    void testSalvaDati_ListaVuota() {
        
        fileTest = "test_lista_vuota.txt";
        List<DatiStub> listaVuota = new ArrayList<>();
        

        gestore.salvaDati(fileTest, listaVuota);

        List<DatiStub> risultato = gestore.caricaDati(fileTest);
        assertNotNull(risultato);
        assertTrue(risultato.isEmpty());
    }

    @Test
    void testSalvaDati_ListaNull() {
        fileTest = "test_lista_null.txt";

        assertDoesNotThrow(() -> gestore.salvaDati(fileTest, null));
        
        List<DatiStub> risultato = gestore.caricaDati(fileTest);
        
        assertNotNull(risultato);
        assertTrue(risultato.isEmpty());
    }

    @Test
    void testSalvaDati_Sovrascrittura() {
        
        fileTest = "test_sovrascrittura.txt";

        gestore.salvaDati(fileTest, datiTest);
        

        List<DatiStub> nuoviDati = new ArrayList<>();
        nuoviDati.add(new DatiStub("ID_NUOVO_1"));
        gestore.salvaDati(fileTest, nuoviDati);
        

        
        List<DatiStub> caricati = gestore.caricaDati(fileTest);
        

        assertEquals(1, caricati.size());
        assertEquals(nuoviDati.get(0).toString(), caricati.get(0).toString());
    }
    
    @Test
    void testCaricaDati() {
        
        String fileTest = "test_carica.txt";
        

        gestore.salvaDati(fileTest, datiTest);

        List<DatiStub> risultato = gestore.caricaDati(fileTest);

        assertNotNull(risultato);
        assertEquals(datiTest.size(), risultato.size());
        assertEquals(datiTest.get(0).toString(), risultato.get(0).toString());
    }

    @Test
    void testCaricaDati_FileInesistente() {
        
        String fileInesistente = "file_inesistente.txt";
        

        List<DatiStub> risultato = gestore.caricaDati(fileInesistente);
        
        assertNotNull(risultato);
        assertTrue(risultato.isEmpty());
        
    }
}