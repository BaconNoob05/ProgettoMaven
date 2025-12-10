/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.ingegneriadelsoftware.service;

import it.unisa.diem.ingegneriadelsoftware.model.*;

import it.unisa.diem.ingegneriadelsoftware.repository.*;

import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

// Assumiamo che la classe Libro sia disponibile e abbia i metodi getTitolo() e getAutori()
public class LibroServiceTest {

    private LibroRepositoryStub libroRepoStub;
    private LibroServiceStub libroService;

    // Oggetti Libro di test
    private LibroStub libroCleanCode;
    private LibroStub libroDesignPatterns;
    private LibroStub libroMobyDick;
    
    private List<String> autoriEsempio;

    @BeforeEach
    public void setUp() {
        
        autoriEsempio = Arrays.asList("I. Sommerville", "Stephen King");
        
        libroCleanCode = new LibroStub("Ingegneria del Software", autoriEsempio, 1951,"978-88-8080-123-4", 5);
        libroDesignPatterns = new LibroStub("Ingegneria del Software", autoriEsempio, 1951,"978-88-8080-123-4", 5);
        libroMobyDick = new LibroStub("Ingegneria del Software", autoriEsempio, 1951,"978-88-8080-123-4", 5);

        // 2. Inizializzazione dello Stub e caricamento dei dati iniziali
        libroRepoStub = new LibroRepositoryStub();
        List<Libro> initialData = Arrays.asList(libroCleanCode, libroDesignPatterns, libroMobyDick);
        libroRepoStub.clearAndLoad(initialData);

        // 3. Inizializzazione del System Under Test (SUT)
        libroService = new LibroServiceStub(libroRepoStub);
    }
    

    @Test
    void testCercaPerTitolo_MatchSingolo() {
        final String FILTRO_TITOLO = "Moby"; // Match parziale

        // Esecuzione
        List<Libro> risultati = libroService.cercaPerTitolo(FILTRO_TITOLO);

        // Verifica
        assertEquals(1, risultati.size());
        assertEquals(libroMobyDick.getId(), risultati.get(0).getId());
    }


    @Test
    void testCercaPerTitolo_MatchMultiplo() {
        final String FILTRO_TITOLO = "Code";

        // Esecuzione
        List<Libro> risultati = libroService.cercaPerTitolo(FILTRO_TITOLO);

        // Verifica
        assertEquals(2, risultati.size()); // Clean Code e Design Patterns (contiene 'Code' nel sottotitolo)
        assertTrue(risultati.contains(libroCleanCode));
        assertTrue(risultati.contains(libroDesignPatterns));
    }


    @Test
    void testCercaPerTitolo_NessunMatch() {
        final String FILTRO_TITOLO = "Matematica";

        // Esecuzione
        List<Libro> risultati = libroService.cercaPerTitolo(FILTRO_TITOLO);

        // Verifica
        assertTrue(risultati.isEmpty());
    }
    

    @Test
    void testCercaPerTitolo_FiltroNullo() {
        // Assumendo che l'implementazione del Service lanci un'eccezione come suggerito
        assertThrows(IllegalArgumentException.class, () -> {
            libroService.cercaPerTitolo(null);
        }, "Il titolo di ricerca nullo dovrebbe generare un'eccezione.");
    }
    

    @Test
    void testCercaPerAutore_MatchSingolo() {
        final String FILTRO_AUTORE = "Melville"; 

        // Esecuzione
        List<Libro> risultati = libroService.cercaPerAutore(FILTRO_AUTORE);

        // Verifica
        assertEquals(1, risultati.size());
        assertEquals(libroMobyDick.getId(), risultati.get(0).getId());
    }


    @Test
    void testCercaPerAutore_MatchMultiplo() {
        final String FILTRO_AUTORE = "Ralph Johnson";

        // Esecuzione
        List<Libro> risultati = libroService.cercaPerAutore(FILTRO_AUTORE);

        // Verifica
        assertEquals(1, risultati.size()); // Solo Design Patterns
        assertTrue(risultati.contains(libroDesignPatterns));
    }
    

    @Test
    void testCercaPerAutore_MatchAutoreConPiuLibri() {
       
        List<String> autoriEsempio = Arrays.asList("I. Sommerville", "Stephen King");
        Libro libroSecondoCleanCode = new Libro("Ingegneria del Software", autoriEsempio, 1951,"978-88-8080-123-4", 5);
        libroRepoStub.inserisciOAggiorna(libroSecondoCleanCode);
        
        final String FILTRO_AUTORE = "Martin"; 

        // Esecuzione
        List<Libro> risultati = libroService.cercaPerAutore(FILTRO_AUTORE);

        // Verifica
        assertEquals(2, risultati.size()); 
        assertTrue(risultati.contains(libroCleanCode));
        assertTrue(risultati.contains(libroSecondoCleanCode));
    }
    
    /**
     * Test della pre-condizione: autore nullo o vuoto.
     */
    @Test
    void testCercaPerAutore_FiltroVuoto() {
        // Se il service restituisce una lista vuota per input non validi
        assertTrue(libroService.cercaPerAutore(null).isEmpty());
        assertTrue(libroService.cercaPerAutore("").isEmpty());
    }
}
