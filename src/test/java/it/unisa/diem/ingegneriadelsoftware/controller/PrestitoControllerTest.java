package it.unisa.diem.ingegneriadelsoftware.controller;
import it.unisa.diem.ingegneriadelsoftware.model.Libro;
import it.unisa.diem.ingegneriadelsoftware.model.Prestito;
import it.unisa.diem.ingegneriadelsoftware.model.Utente;
import it.unisa.diem.ingegneriadelsoftware.view.PrestitoView;
import it.unisa.diem.ingegneriadelsoftware.service.PrestitoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class PrestitoControllerTest {
private PrestitoController controller;
    private PrestitoViewStub viewStub;
    private PrestitoServiceStub serviceStub;
    
    // Oggetti Dummy
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

    
    // A. TEST METODI SPECIFICI (PrestitoController)


    @Test
    void testRegistraPrestito_InputValido_Successo() {
        // 1. Setup: Utente inserisce dati validi
        Prestito input = new Prestito(u, l, LocalDate.now().plusDays(15));
        viewStub.setPrestitoNuovoInput(input);

        // 2. Azione
        controller.registraPrestito();

        // 3. Verifica: Salvataggio avvenuto
        assertEquals(1, serviceStub.fakeDB.size());
        assertEquals("ISBN1", serviceStub.fakeDB.get(0).getLibro().getIsbn());
        // 4. Verifica: Vista aggiornata
        assertNotNull(viewStub.listaRicevuta);
    }

    @Test
    void testRegistraPrestito_InputNull_NessunaAzione() {
        // 1. Setup: View ritorna null (campi vuoti o errore)
        viewStub.setPrestitoNuovoInput(null);

        // 2. Azione
        controller.registraPrestito();

        // 3. Verifica
        assertTrue(serviceStub.fakeDB.isEmpty());
    }

    @Test
    void testRegistraRestituzione_Valida_Successo() {
        // 1. Setup: Esiste un prestito attivo
        Prestito pAttivo = new Prestito(u, l, LocalDate.now());
        serviceStub.fakeDB.add(pAttivo);
        
        // 2. Simulazione input
        viewStub.setElementoSelezionato(pAttivo);
        LocalDate dataRest = LocalDate.now();
        viewStub.setDataRestituzioneInput(dataRest);

        // 3. Azione
        controller.registraRestituzione();

        // 4. Verifica: Il prestito nel DB risulta chiuso
        Prestito nelDB = serviceStub.fakeDB.get(0);
        assertEquals(dataRest, nelDB.getDataEffettiva());
    }

    @Test
    void testRegistraRestituzione_NessunaSelezione_Errore() {
        // 1. Setup
        viewStub.setElementoSelezionato(null);
        viewStub.setDataRestituzioneInput(LocalDate.now());

        // 2. Azione
        controller.registraRestituzione();

        // 3. Verifica: Messaggio errore mostrato
        assertNotNull(viewStub.ultimoMessaggio, "Deve mostrare errore se non selezioni nulla.");
    }

    @Test
    void testRegistraRestituzione_DataMancante_Errore() {
        // 1. Setup
        Prestito p = new Prestito(u, l, LocalDate.now());
        serviceStub.fakeDB.add(p);
        viewStub.setElementoSelezionato(p);
        viewStub.setDataRestituzioneInput(null); // Data null

        // 2. Azione
        controller.registraRestituzione();

        // 3. Verifica: Non deve aver modificato il DB
        assertNull(serviceStub.fakeDB.get(0).getDataEffettiva());
        assertNotNull(viewStub.ultimoMessaggio);
    }

    @Test
    void testAggiornaPrestiti_FiltraSoloAttivi() {
        // 1. Setup: Uno attivo, uno chiuso
        Prestito attivo = new Prestito(u, l, LocalDate.now());
        
        Prestito chiuso = new Prestito(u, l, LocalDate.now());
        chiuso.registraRestituzione(LocalDate.now()); // Lo chiudiamo

        serviceStub.fakeDB.add(attivo);
        serviceStub.fakeDB.add(chiuso);

        // 2. Azione: Metodo specifico per mostrare i prestiti attivi
        controller.aggiornaPrestiti();

        // 3. Verifica
        assertNotNull(viewStub.listaRicevuta);
        assertEquals(1, viewStub.listaRicevuta.size());
        assertEquals(attivo, viewStub.listaRicevuta.get(0));
    }

    
    // B. TEST METODI EREDITATI (BaseController)
    

    /**
     * Test del metodo init(). Deve inizializzare la vista caricando i dati.
     */
    @Test
    void testInit_CaricaDatiIniziali() {
        Prestito p = new Prestito(u, l, LocalDate.now());
        serviceStub.fakeDB.add(p);

        controller.init();

        assertNotNull(viewStub.listaRicevuta, "init() deve popolare la vista.");
        assertEquals(1, viewStub.listaRicevuta.size());
    }

    /**
     * Test del metodo aggiornaVista(). Deve mostrare TUTTI i dati (attivi e storici),
     * a differenza di aggiornaPrestiti che mostra solo gli attivi.
     */
    @Test
    void testAggiornaVista_MostraTutto() {
        Prestito attivo = new Prestito(u, l, LocalDate.now());
        Prestito chiuso = new Prestito(u, l, LocalDate.now());
        chiuso.registraRestituzione(LocalDate.now());

        serviceStub.fakeDB.add(attivo);
        serviceStub.fakeDB.add(chiuso);

        // Chiamata al metodo ereditato da BaseController
        controller.aggiornaVista();

        // Verifica: getAll() del service restituisce tutto
        assertEquals(2, viewStub.listaRicevuta.size());
    }

    /**
     * Test del metodo getSelezionato(). Deve restituire ciò che la view ha selezionato.
     */
    @Test
    void testGetSelezionato_FunzionaCorrettamente() {
        Prestito p = new Prestito(u, l, LocalDate.now());
        viewStub.setElementoSelezionato(p);

        // Chiamata al metodo ereditato
        Prestito risultato = controller.getSelezionato();

        assertEquals(p, risultato);
    }

    /**
     * Test del metodo getSelezionato() quando non c'è selezione.
     */
    @Test
    void testGetSelezionato_NullSeNessunaSelezione() {
        viewStub.setElementoSelezionato(null);
        assertNull(controller.getSelezionato());
    }

    /**
     * Test del metodo eseguiOperazione().
     * Questo metodo ereditato gestisce try-catch e messaggi di conferma.
     */
    @Test
    void testEseguiOperazione_Successo() {
        // Usiamo AtomicBoolean per verificare che il Runnable venga eseguito
        AtomicBoolean operazioneEseguita = new AtomicBoolean(false);
        String messaggioConferma = "Operazione OK";

        // Chiamata
        controller.eseguiOperazione(() -> {
            operazioneEseguita.set(true);
        }, messaggioConferma);

        // Verifica
        assertTrue(operazioneEseguita.get(), "Il Runnable deve essere eseguito.");
        // Verifica indiretta: aggiornaVista viene chiamato alla fine di eseguiOperazione
        // (dipende dall'implementazione di BaseController, ma solitamente è così)
        // Sicuramente deve mostrare il messaggio
        assertEquals(messaggioConferma, viewStub.ultimoMessaggio);
    }

    @Test
    void testEseguiOperazione_EccezioneGestita() {
        // Simuliamo un'operazione che fallisce
        controller.eseguiOperazione(() -> {
            throw new IllegalArgumentException("Errore simulato");
        }, "Successo");

        // Verifica che l'eccezione sia stata catturata e mostrata nella view
        // Invece di crashare, BaseController dovrebbe fare view.mostraMessaggio(e.getMessage())
        // o qualcosa di simile. Controlliamo se ultimoMessaggio contiene il testo dell'errore.
        // Nota: se il tuo BaseController stampa solo su console, questo test fallirà l'assert sul messaggio.
        // Assumendo un BaseController ben fatto che usa view.mostraMessaggio:
        assertNotNull(viewStub.ultimoMessaggio);
        // Spesso il messaggio di errore non è quello di conferma
        assertNotEquals("Successo", viewStub.ultimoMessaggio); 
    }
}