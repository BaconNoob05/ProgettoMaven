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

    private LibroService service;
    private RepositoryStub<Libro> repo;
    
    // Oggetti Libro di test
    private Libro libroCleanCode;
    private Libro libroDesignPatterns;
    private Libro libroMobyDick;
    
    private List<String> autoriEsempio;

    @BeforeEach
    public void setUp() {
        
        autoriEsempio = Arrays.asList("I. Sommerville", "Stephen King");
        
        libroCleanCode = new Libro("Ingegneria del Software", autoriEsempio, 1951,"978-88-8080-123-4", 5);
        libroDesignPatterns = new Libro("Ingegneria del Software", autoriEsempio, 1951,"978-88-8080-123-4", 5);
        libroMobyDick = new Libro("Ingegneria del Software", autoriEsempio, 1951,"978-88-8080-123-4", 5);

        // 2. Inizializzazione dello Stub e caricamento dei dati iniziali
        repo = new RepositoryStub<Libro>();
        List<Libro> datiIniziali = Arrays.asList(libroCleanCode, libroDesignPatterns, libroMobyDick);
        repo.caricaTutti(datiIniziali);

        // 3. Inizializzazione del System Under Test (SUT)
        service = new LibroService(repo);
    }
    

    @Test
    void testCercaPerTitolo_MatchSingolo() {
        final String FILTRO_TITOLO = "Moby"; 

        // Esecuzione
        List<Libro> risultati = service.cercaPerTitolo(FILTRO_TITOLO);

        // Verifica
        assertEquals(1, risultati.size());
        assertEquals(libroMobyDick.getId(), risultati.get(0).getId());
    }


    @Test
    void testCercaPerTitolo_MatchMultiplo() {
        final String FILTRO_TITOLO = "Code";

        // Esecuzione
        List<Libro> risultati = service.cercaPerTitolo(FILTRO_TITOLO);

        // Verifica
        assertEquals(2, risultati.size()); // Clean Code e Design Patterns (contiene 'Code' nel sottotitolo)
        assertTrue(risultati.contains(libroCleanCode));
        assertTrue(risultati.contains(libroDesignPatterns));
    }


    @Test
    void testCercaPerTitolo_NessunMatch() {
        final String FILTRO_TITOLO = "Matematica";

        // Esecuzione
        List<Libro> risultati = service.cercaPerTitolo(FILTRO_TITOLO);

        // Verifica
        assertTrue(risultati.isEmpty());
    }
    

    @Test
    void testCercaPerTitolo_FiltroNullo() {
        // Assumendo che l'implementazione del Service lanci un'eccezione come suggerito
        assertThrows(IllegalArgumentException.class, () -> {
            service.cercaPerTitolo(null);
        });
    }
    

    @Test
    void testCercaPerAutore_MatchSingolo() {
        final String FILTRO_AUTORE = "Melville"; 

        // Esecuzione
        List<Libro> risultati = service.cercaPerAutore(FILTRO_AUTORE);

        // Verifica
        assertEquals(1, risultati.size());
        assertEquals(libroMobyDick.getId(), risultati.get(0).getId());
    }


    @Test
    void testCercaPerAutore_MatchMultiplo() {
        final String FILTRO_AUTORE = "Ralph Johnson";

        // Esecuzione
        List<Libro> risultati = service.cercaPerAutore(FILTRO_AUTORE);

        // Verifica
        assertEquals(1, risultati.size()); // Solo Design Patterns
        assertTrue(risultati.contains(libroDesignPatterns));
    }
    

    @Test
    void testCercaPerAutore_MatchAutoreConPiuLibri() {
       
        List<String> autoriEsempio = Arrays.asList("I. Sommerville", "Stephen King");
        Libro libroSecondoCleanCode = new Libro("Ingegneria del Software", autoriEsempio, 1951,"978-88-8080-123-4", 5);
        repo.inserisciOAggiorna(libroSecondoCleanCode);
        
        final String FILTRO_AUTORE = "Martin"; 

        // Esecuzione
        List<Libro> risultati = service.cercaPerAutore(FILTRO_AUTORE);

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
        assertTrue(service.cercaPerAutore(null).isEmpty());
        assertTrue(service.cercaPerAutore("").isEmpty());
    }
}
