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

class GestoreFileTest {

    private GestoreFile<String> gestore;
    private final String NOMEFILE = "test.txt";
    private List<String> datiTest;

    @BeforeEach
    void setUp() {
        gestore = new GestoreFile<>();
        datiTest = Arrays.asList("Riga Uno", "Riga Due", "Riga Tre");
    }

    @Test
    void testSalvaECarica() {
        //Salva la lista contenente i dati sul file di test
        gestore.salvaDati(NOMEFILE, datiTest);
        
        //Carica la lista contenente i dati dal file di test
        List<String> caricati = gestore.caricaDati(NOMEFILE);
        
        assertNotNull(caricati);
        
        //Verifica che il numero di linee di test sia uguale al numero di linee caricate
        assertEquals(datiTest.size(), caricati.size());
        
        //Verifica che la prima linea corriaponda alla prima del file di test
        assertEquals("Riga Uno", caricati.get(0));
        
        //Stessa cosa ma con l'ultima
        assertEquals("Riga Tre", caricati.get(2));
    }

    @Test
    void testCaricaFileInesistente() {
        //Verifica che non lanci eccezioni ma restituisca lista vuota
        List<String> risultato = gestore.caricaDati("file_che_non_esiste.txt");
        assertNotNull(risultato);
        assertTrue(risultato.isEmpty());
    }
    
}