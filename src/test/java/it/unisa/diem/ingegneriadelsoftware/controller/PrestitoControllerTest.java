package it.unisa.diem.ingegneriadelsoftware.controller;
import it.unisa.diem.ingegneriadelsoftware.model.*;

import it.unisa.diem.ingegneriadelsoftware.view.*;
import it.unisa.diem.ingegneriadelsoftware.service.*;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.*;


public class PrestitoControllerTest {
    
    private PrestitoController controller;
    private PrestitoViewStub viewStub;
    private PrestitoServiceStub serviceStub;
    

    private Utente u;
    private Libro l;

    @BeforeEach
    void setUp() {
        viewStub = new PrestitoViewStub();
        serviceStub = new PrestitoServiceStub();
        controller = new PrestitoController(viewStub, serviceStub);
        
        u = new Utente("Mario", "Rossi", "M01", "a@a.it");
        l = new Libro("Java", Arrays.asList("Autore"), 2022, "ISBN1", 5);
    }




    @Test
    void testRegistraPrestito_InputValido_Successo() {

        
        Prestito input = new Prestito(u, l, LocalDate.now().plusDays(15));
        viewStub.setPrestitoNuovo(input);


        controller.registraPrestito();

        assertEquals(1, serviceStub.fakeDB.size());
        assertEquals("ISBN1", serviceStub.fakeDB.get(0).getLibro().getIsbn());

        
        assertNotNull(viewStub.listaRicevuta);
    }

    @Test
    void testRegistraPrestito_InputNull_NessunaAzione() {

        viewStub.setPrestitoNuovo(null);


        controller.registraPrestito();

        assertTrue(serviceStub.fakeDB.isEmpty());
    }

    @Test
    void testRegistraRestituzione_Valida_Successo() {

        
        
        Prestito pAttivo = new Prestito(u, l, LocalDate.now());
        serviceStub.fakeDB.add(pAttivo);
        

        viewStub.setElementoSelezionato(pAttivo);
        LocalDate dataRest = LocalDate.now();
        viewStub.setDataRestituzione(dataRest);


        controller.registraRestituzione();

        // 4. Verifica: Il prestito nel DB risulta chiuso
        Prestito nelDB = serviceStub.fakeDB.get(0);
        assertEquals(dataRest, nelDB.getDataEffettiva());
    }

    @Test
    void testRegistraRestituzione_NessunaSelezione_Errore() {

        
        viewStub.setElementoSelezionato(null);
        viewStub.setDataRestituzione(LocalDate.now());


        controller.registraRestituzione();

        assertNotNull(viewStub.ultimoMessaggio);
    }

    @Test
    void testRegistraRestituzione_DataMancante_Errore() {

        Prestito p = new Prestito(u, l, LocalDate.now());
        serviceStub.fakeDB.add(p);
        viewStub.setElementoSelezionato(p);
        viewStub.setDataRestituzione(null); // con null


        controller.registraRestituzione();


        assertNull(serviceStub.fakeDB.get(0).getDataEffettiva());
        assertNotNull(viewStub.ultimoMessaggio);
    }

    @Test
    void testAggiornaPrestiti_FiltraSoloAttivi() {

        
        Prestito attivo = new Prestito(u, l, LocalDate.now());
        
        Prestito chiuso = new Prestito(u, l, LocalDate.now());
        chiuso.registraRestituzione(LocalDate.now()); 

        serviceStub.fakeDB.add(attivo);
        serviceStub.fakeDB.add(chiuso);


        controller.aggiornaPrestiti();


        assertNotNull(viewStub.listaRicevuta);
        assertEquals(1, viewStub.listaRicevuta.size());
        assertEquals(attivo, viewStub.listaRicevuta.get(0));
    }

    

    @Test
    void testInit_CaricaDatiIniziali() {
        
        Prestito p = new Prestito(u, l, LocalDate.now());
        serviceStub.fakeDB.add(p);

        controller.init();

        assertNotNull(viewStub.listaRicevuta, "init() deve popolare la vista.");
        assertEquals(1, viewStub.listaRicevuta.size());
    }


    @Test
    void testAggiornaVista_MostraTutto() {
        
        Prestito attivo = new Prestito(u, l, LocalDate.now());
        Prestito chiuso = new Prestito(u, l, LocalDate.now());
        chiuso.registraRestituzione(LocalDate.now());

        serviceStub.fakeDB.add(attivo);
        serviceStub.fakeDB.add(chiuso);


        controller.aggiornaVista();


        assertEquals(2, viewStub.listaRicevuta.size());
    }

    
    @Test
    void testGetSelezionato_FunzionaCorrettamente() {
        Prestito p = new Prestito(u, l, LocalDate.now());
        viewStub.setElementoSelezionato(p);


        Prestito risultato = controller.getSelezionato();

        assertEquals(p, risultato);
    }


    @Test
    void testGetSelezionato_NullSeNessunaSelezione() {
        
        viewStub.setElementoSelezionato(null);
        assertNull(controller.getSelezionato());
    }



    @Test
    void testEseguiOperazione_EccezioneGestita() {
        
        
        // Simuliamo un'operazione che fallisce
        controller.eseguiOperazione(() -> {
            throw new IllegalArgumentException("Errore simulato");
        }, "Successo");

        assertNotNull(viewStub.ultimoMessaggio);

        
        assertNotEquals("Successo", viewStub.ultimoMessaggio); 
    }
}