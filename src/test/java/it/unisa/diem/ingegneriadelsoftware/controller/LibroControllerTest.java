package it.unisa.diem.ingegneriadelsoftware.controller;

import it.unisa.diem.ingegneriadelsoftware.model.Libro;
import it.unisa.diem.ingegneriadelsoftware.service.*;
import it.unisa.diem.ingegneriadelsoftware.view.LibroViewStub;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class LibroControllerTest {
    
    private LibroController controller;
    private LibroViewStub view;
    private LibroServiceStub service;

    @BeforeEach
    void setUp() {
        view = new LibroViewStub(); 
        service = new LibroServiceStub();
        controller = new LibroController(view, service); 
    }

    @Test
    void testSalvaLibro() {
        List<String> autoriLibro = Arrays.asList("Malcolm Gladwell");
        Libro libro = new Libro("Fuoriclasse", autoriLibro, 2008, "978-8852047695", 4);
        view.setInputNuovo(libro); 
        
        controller.salvaLibro();
        
        assertTrue(service.salvaChiamato);
        
        
        assertEquals(1, service.dB.size()); 
        assertEquals("978-8852047695", service.dB.get(0).getIsbn());
        
        assertNotNull(view.listaRicevuta);
        
        
        assertEquals(1, view.listaRicevuta.size());
    }

    @Test
    void testSalvaLibro_InputNonValido() {
        view.setInputNuovo(null); 
        controller.salvaLibro();
        assertFalse(service.salvaChiamato);
        assertTrue(service.dB.isEmpty());
    }
    
    @Test
    void testModificaLibro() {
        List<String> autoriLibro = Arrays.asList("Leonardo Sciascia");
        Libro libroEsistente = new Libro("A ciascuno il suo", autoriLibro, 1966, "978-8845915147", 1);
        service.dB.add(libroEsistente);
        view.setSelezionato(libroEsistente);
        
        Libro libroModificato = new Libro("A ciascuno il suo", autoriLibro, 1966, "978-8845915147", 5);
        view.setInputModificato(libroModificato);
        
        controller.modificaLibro();
        
        assertTrue(service.modificaChiamato);
        assertEquals(5, service.dB.get(0).getCopieDisponibili());
        assertNotNull(view.listaRicevuta);
    }

    @Test
    void testModificaLibro_NessunaSelezione() {
        view.setSelezionato(null);
        view.setInputModificato(null);

        controller.modificaLibro();

        assertFalse(service.modificaChiamato);
        assertNotNull(view.ultimoMessaggio); 
    }
    
    @Test
    void testElimina() {
        List<String> autoriLibro = Arrays.asList("Giuseppe Parini");
        Libro libro = new Libro("Il giorno", autoriLibro, 1763, "978-8804284024", 2);
        service.dB.add(libro);

        view.setSelezionato(libro);

        controller.elimina(); 

        assertTrue(service.dB.isEmpty());
        assertTrue(view.listaRicevuta.isEmpty());
    }

    @Test
    void testElimina_NessunaSelezione() {
        List<String> autoriLibro = Arrays.asList("Giacomo Leopardi");
        Libro libro = new Libro("L'infinito", autoriLibro, 1826, "978-8815008763", 6);
        service.dB.add(libro);
        
        view.setSelezionato(null);

        controller.elimina();

      
        assertEquals(1, service.dB.size());
        assertNotNull(view.ultimoMessaggio);
    }

    @Test
    void testCerca_FiltroCorrispondente() {
        List<String> autoriLibro1 = Arrays.asList("Daniel Goleman");
        List<String> autoriLibro2 = Arrays.asList("Giovanni Pascoli");
        service.dB.add(new Libro("Intelligenza emotiva", autoriLibro1, 1995, "978-8817050166", 5));
        service.dB.add(new Libro("Myricae", autoriLibro2, 1891, "978-8817083911", 1));

        view.setTestoCerca("Intelligenza emotiva"); 

        controller.cercaLibri(); 

        assertNotNull(view.listaRicevuta);
        assertEquals(1, view.listaRicevuta.size());
        assertEquals("978-8817050166", view.listaRicevuta.get(0).getIsbn());

        view.setTestoCerca("Giovanni Pascoli"); 
        
        controller.cercaLibri(); 

        assertNotNull(view.listaRicevuta, "La lista ricevuta (autore) non deve essere null");
        assertEquals(1, view.listaRicevuta.size(), "La ricerca per autore deve portare ad un numero di risultati pari a: 1");
        
        assertEquals("978-8817083911", view.listaRicevuta.get(0).getIsbn());
        assertEquals("Myricae", view.listaRicevuta.get(0).getTitolo());
    }

    @Test
    void testCerca_FiltroVuoto() {
        List<String> autoriLibro1 = Arrays.asList("Nassim Nicholas Taleb");
        List<String> autoriLibro2 = Arrays.asList("Carlo Rovelli");
        service.dB.add(new Libro("Antifragile. Prosperare nel disordine", autoriLibro1, 2012, "978-8842819172", 3));
        service.dB.add(new Libro("Sette brevi lezioni di fisica", autoriLibro2, 2014, "978-8845929250", 1));

        view.setTestoCerca(""); 

        controller.cercaLibri();

        assertEquals(2, view.listaRicevuta.size());
    }

    @Test
    void testAggiornaVista_DatiPresenti() {
        List<String> autoriLibro1 = Arrays.asList("Karl Marx");
        List<String> autoriLibro2 = Arrays.asList("Georg Wilhelm Friedrich Hegel");
        Libro libro1 = new Libro("Il Capitale", autoriLibro1 , 1867, "978-8854180499", 4);
        Libro libro2 = new Libro("Fenomenologia dello spirito", autoriLibro2, 1807, "978-8805024728", 1);
        
        service.dB.add(libro1);
        service.dB.add(libro2);

        controller.aggiornaVista(); 

        assertNotNull(view.listaRicevuta);
        assertEquals(2, view.listaRicevuta.size());
        assertEquals("978-8854180499", view.listaRicevuta.get(0).getIsbn());
    }
}