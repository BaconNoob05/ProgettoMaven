package it.unisa.diem.ingegneriadelsoftware.controller;
import it.unisa.diem.ingegneriadelsoftware.model.*;

import it.unisa.diem.ingegneriadelsoftware.view.*;
import it.unisa.diem.ingegneriadelsoftware.service.*;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.*;
import javafx.application.Platform;

/**
 * @brief Classe di test per PrestitoController.
 * @class PrestitoControllerTest
 */
public class PrestitoControllerTest {
    
    /**
     * @brief L'istanza del controller.
     */
    private PrestitoController controller;
    
    /**
     * @brief La stub della view per simulare il comportamento con l'utente e l'output.
     */
    private PrestitoViewStub viewStub;
    
    /**
     * @brief La stub del service per simulare l'accesso ai dati.
     */
    private PrestitoServiceStub serviceStub;
    
    /**
     * @brief Oggetto Utente di esempio.
     */
    private Utente u;
    
    /**
     * @brief Oggetto Libro di esempio.
     */
    private Libro l;

    
    
   /**
     * @brief Metodo eseguito prima di ogni test.
     */
    @BeforeEach
    void setUp() {
        viewStub = new PrestitoViewStub();
        serviceStub = new PrestitoServiceStub();
        controller = new PrestitoController(viewStub, serviceStub);
        
        u = new Utente("Mario", "Rossi", "M01", "a@a.it");
        l = new Libro("Java", Arrays.asList("Autore"), 2022, "ISBN1", 5);
    }




    /**
     * @brief Testa la  registrazione di un prestito con valori validi.
     */
    @Test
    void testRegistraPrestito_InputValido_Successo() {

        
        Prestito input = new Prestito(u, l, LocalDate.now().plusDays(15));
        viewStub.setPrestitoNuovo(input);


        controller.registraPrestito();

        assertEquals(1, serviceStub.list.size());
        assertEquals("ISBN1", serviceStub.list.get(0).getLibro().getIsbn());

        
        assertNotNull(viewStub.listaRicevuta);
    }

    /**
     * @brief Testa la registrazione di un prestito con input nullo.
     */
    @Test
    void testRegistraPrestito_InputNull_NessunaAzione() {

        viewStub.setPrestitoNuovo(null);


        controller.registraPrestito();

        assertTrue(serviceStub.list.isEmpty());
    }

    /**
     * @brief Testa la registrazione della restituzione di un prestito attivo.
     */
    @Test
    void testRegistraRestituzione_Valida_Successo() {

        
        
        Prestito pAttivo = new Prestito(u, l, LocalDate.now());
        serviceStub.list.add(pAttivo);
        

        viewStub.setElementoSelezionato(pAttivo);
        LocalDate dataRest = LocalDate.now();
        viewStub.setDataRestituzione(dataRest);


        controller.registraRestituzione();

        // 4. Verifica: Il prestito nel DB risulta chiuso
        Prestito nelDB = serviceStub.list.get(0);
        assertEquals(dataRest, nelDB.getDataEffettiva());
    }

    /**
     * @brief Testa la registrazione della restituzione senza aver selezionato un prestito.
     */
    @Test
    void testRegistraRestituzione_NessunaSelezione_Errore() {

        
        viewStub.setElementoSelezionato(null);
        viewStub.setDataRestituzione(LocalDate.now());


        controller.registraRestituzione();

        assertNotNull(viewStub.ultimoMessaggio);
    }

    /**
     * @brief Testa la registrazione della restituzione con la data di restituzione assente.
     */
    @Test
    void testRegistraRestituzione_DataMancante_Errore() {

        Prestito p = new Prestito(u, l, LocalDate.now());
        serviceStub.list.add(p);
        viewStub.setElementoSelezionato(p);
        viewStub.setDataRestituzione(null); // con null


        controller.registraRestituzione();


        assertNull(serviceStub.list.get(0).getDataEffettiva());
        assertNotNull(viewStub.ultimoMessaggio);
    }

    /**
     * @brief Testa il metodo aggiornaPrestiti.
     */
    @Test
    void testAggiornaPrestiti_FiltraSoloAttivi() {

        
        Prestito attivo = new Prestito(u, l, LocalDate.now());
        
        Prestito chiuso = new Prestito(u, l, LocalDate.now());
        chiuso.registraRestituzione(LocalDate.now()); 

        serviceStub.list.add(attivo);
        serviceStub.list.add(chiuso);


        controller.aggiornaPrestiti();


        assertNotNull(viewStub.listaRicevuta);
        assertEquals(1, viewStub.listaRicevuta.size());
        assertEquals(attivo, viewStub.listaRicevuta.get(0));
    }

    
    /**
     * @brief Testa il metodo init.
     */
    @Test
    void testInit_CaricaDatiIniziali() {
        
        Prestito p = new Prestito(u, l, LocalDate.now());
        serviceStub.list.add(p);

        controller.init();

        assertNotNull(viewStub.listaRicevuta, "init() deve popolare la vista.");
        assertEquals(1, viewStub.listaRicevuta.size());
    }


    /**
     * @brief Testa il metodo aggiornaVista.
     */
    @Test
    void testAggiornaVista_MostraTutto() {
        
        Prestito attivo = new Prestito(u, l, LocalDate.now());
        Prestito chiuso = new Prestito(u, l, LocalDate.now());
        chiuso.registraRestituzione(LocalDate.now());

        serviceStub.list.add(attivo);
        serviceStub.list.add(chiuso);


        controller.aggiornaVista();


        assertEquals(2, viewStub.listaRicevuta.size());
    }

    
    /**
     * @brief Testa il recupero dell'elemento selezionato dalla view.
     */
    @Test
    void testGetSelezionato_FunzionaCorrettamente() {
        Prestito p = new Prestito(u, l, LocalDate.now());
        viewStub.setElementoSelezionato(p);


        Prestito risultato = controller.getSelezionato();

        assertEquals(p, risultato);
    }


    /**
     * @brief Testa il recupero dell'elemento selezionato quando la selezione non è presente.
     */
    @Test
    void testGetSelezionato_NullSeNessunaSelezione() {
        
        viewStub.setElementoSelezionato(null);
        assertNull(controller.getSelezionato());
    }


    /**
     * @brief Testa eseguiOperazione.
     */
    @Test
    void testEseguiOperazione_EccezioneGestita() {
        
        
      
        controller.eseguiOperazione(() -> {
            throw new IllegalArgumentException("Errore simulato");
        }, "Successo");

        assertNotNull(viewStub.ultimoMessaggio);

        
        assertNotEquals("Successo", viewStub.ultimoMessaggio); 
    }
}
