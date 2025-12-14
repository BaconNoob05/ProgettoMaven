package it.unisa.diem.ingegneriadelsoftware.controller;

import it.unisa.diem.ingegneriadelsoftware.model.Utente;
import it.unisa.diem.ingegneriadelsoftware.service.UtenteServiceStub;
import it.unisa.diem.ingegneriadelsoftware.view.UtenteViewStub;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
/**
 * @brief Classe di test per UtenteController.
 * @class UtenteControllerTest
 */
public class UtenteControllerTest {
    
    /**
     * @brief L'istanza del controller .
     */
    private UtenteController controller;
    
    /**
     * @brief La stub della view per simulare il comportamento con l'utente e l'output.
     */
    private UtenteViewStub view;
    
    /**
     * @brief La stub del service per simulare l'accesso ai dati .
     */
    private UtenteServiceStub service;

      /**
     * @brief Metodo eseguito prima di ogni test.
     */
    @BeforeEach
    void setUp() {
        
        view = new UtenteViewStub(); 
        service = new UtenteServiceStub();
        
        controller = new UtenteController(view, service);
    }

    /**
     * @brief Testa l'aggiornamento della vista in caso di salvataggio di Utente.
     */
    @Test
    void testSalvaUtente() {
        Utente nuovoUtente = new Utente("Daniele", "Manzo", "0612709967", "danielemanzo@uni.it");
        view.setInputNuovo(nuovoUtente);

        controller.salvaUtente();

        assertTrue(service.salvaChiamato);
        assertEquals(1, service.lista.size());
        
        assertNotNull(view.listaRicevuta);
        assertEquals(1, view.listaRicevuta.size());
        assertEquals("0612709967", view.listaRicevuta.get(0).getId());
    }

    /**
     * @brief Testa il salvataggio Utente con valori non validi.
     */
    @Test
    void testSalvaUtente_InputNonValido() {
        view.setInputNuovo(null); 

        controller.salvaUtente();

        assertFalse(service.salvaChiamato);
        assertTrue(service.lista.isEmpty());
    }
    
    
    /**
     * @brief Testa il metodo salva() .
     */
    @Test
    void testSalva() {
        
        Utente utente = new Utente("Matteo", "Iandiorio", "061709000", "matteoiandiorio@uni.it");
        
        controller.salva(utente);
        
        assertTrue(service.salvaChiamato);
        
        assertEquals(utente, service.lista.get(0));
    }

    /**
     * @brief Testal'aggiornamento della vista in caso di modifica Utente.
     */
    @Test
    void testModificaUtente() {
        
        Utente esistente = new Utente("Lorenzo", "Trovato", "0612709999", "lorenzotrovato@uni.it");
        service.lista.add(esistente);
        
        view.setSelezionato(esistente);
        
        Utente modificato = new Utente("Lorenzo", "Trovato", "0612709999", "trovatolorenzo@uni.it");
        view.setInputModificato(modificato);

        controller.modificaUtente();

        assertTrue(service.modificaChiamato);
        assertEquals("trovatolorenzo@uni.it", service.lista.get(0).getEmail());
        
        assertNotNull(view.listaRicevuta);
        assertEquals("trovatolorenzo@uni.it", view.listaRicevuta.get(0).getEmail());
    }

    /**
     * @brief Testa la modifica Utente senza aver selezionato un elemento.
     */
    @Test
    void testModificaUtente_NessunaSelezione() {
        
        view.setSelezionato(null);
        view.setInputModificato(null);

        controller.modificaUtente();

        assertFalse(service.modificaChiamato);
        assertNotNull(view.ultimoMessaggio);
    }
    
    /**
     * @brief Testa la modifica Utente con valore modificato nullo.
     */
    @Test
    void testModificaUtente_InputNull() {
        
        Utente utente = new Utente("Vincenzo", "Raimo", "0612709555", "vincenzoraimo@uni.it");
        service.lista.add(utente);
        view.setSelezionato(utente);
        
        view.setInputModificato(null);
        
        controller.modificaUtente();

        assertEquals("vincenzoraimo@uni.it", service.lista.get(0).getEmail());
    }

    /**
     * @brief Testa il metodo modifica() .
     */
    @Test
    void testModifica() {
        
        Utente u = new Utente("Alessandro", "Picariello", "0612709696", "alessandropicariello@uni.it");
        service.lista.add(u);
        
        Utente uMod = new Utente("Alessandro", "Picariello", "0612709696", "picarielloalessandro@uni.it");
        controller.modifica(uMod);
        
        assertTrue(service.modificaChiamato);
        assertEquals("picarielloalessandro@uni.it", service.lista.get(0).getEmail());
    }
    
    /**
     * @brief Testa l'eliminazione di un Utente selezionato.
     */
    @Test
    void testElimina() {
        
        Utente u = new Utente("Nicola", "Picarella", "0612709444", "nicolapicarella@uni.it");
        service.lista.add(u);

        view.setSelezionato(u);

        controller.elimina();

        assertTrue(service.lista.isEmpty());
        assertTrue(view.listaRicevuta.isEmpty());
    }

    /**
     * @brief Testa il tentativo di eliminazione senza selezione.
     */
    @Test
    void testElimina_NessunaSelezione() {
        Utente u = new Utente("Nicolò", "Lisena", "061709333", "nicololisena@uni.it");
        service.lista.add(u);
        
        view.setSelezionato(null);

        controller.elimina();

        assertEquals(1, service.lista.size());
        assertNotNull(view.ultimoMessaggio);
    }

    /**
     * @brief Testa la funzione di ricerca con un filtro per cognome.
     */
    @Test
    void testCerca_FiltroCorrispondente() {
        service.lista.add(new Utente("Vincenzo", "Raimo", "0612709555", "vincenzoraimo@uni.it"));
        service.lista.add(new Utente("Sofia", "Mancini", "0612709666", "sofiamancini@uni.it"));

        view.setTestoCerca("Raimo");

        controller.cerca();

        assertNotNull(view.listaRicevuta);
        assertEquals(1, view.listaRicevuta.size());
        assertEquals("0612709555", view.listaRicevuta.get(0).getId());
    }

    /**
     * @brief Testa la funzione di ricerca con un filtro vuoto.
     */
    @Test
    void testCerca_FiltroVuoto() {
        service.lista.add(new Utente("Daniele", "Manzo", "0612709876", "danielemanzo@uni.it"));
        service.lista.add(new Utente("Enrica", "Avitabile", "0612701234", "enricaavitabile@uni.it"));

        view.setTestoCerca("");

        controller.cerca();

        assertEquals(2, view.listaRicevuta.size());
    }

    /**
     * @brief Testa la funzione di ricerca senza risultati .
     */
    @Test
    void testCerca_NessunaCorrispondenza() {
        service.lista.add(new Utente("Angelo", "Palladino", "0612709567", "angelopalladino@uni.it"));

        view.setTestoCerca("Manzo"); 

        controller.cerca();

        assertTrue(view.listaRicevuta.isEmpty());
    }
    
    /**
     * @brief Testa l'aggiornamento della vista quando sono presenti dati nel service.
     */
    @Test
    void testAggiornaVista_DatiPresenti() {
        Utente utente1 = new Utente("Alessandro", "Picariello", "0612707542", "alepica@uni.it");
        Utente utente2 = new Utente("Matteo", "Iandiorio", "0612702574", "mattiand@uni.it");
        
        service.lista.add(utente1);
        service.lista.add(utente2);

        controller.aggiornaVista();

        assertNotNull(view.listaRicevuta);
        
        assertEquals(2, view.listaRicevuta.size());
        
        assertEquals("0612707542", view.listaRicevuta.get(0).getId());
        
        assertEquals("0612702574", view.listaRicevuta.get(1).getId());
    }

    /**
     * @brief Testa l'aggiornamento della vista quando non ci sono dati nel service.
     */
    @Test
    void testAggiornaVista_NessunDato() {
        service.lista.clear();

        controller.aggiornaVista();

        assertNotNull(view.listaRicevuta);
        assertTrue(view.listaRicevuta.isEmpty());
    }
}
