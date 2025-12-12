/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.ingegneriadelsoftware.controller;

import it.unisa.diem.ingegneriadelsoftware.model.Utente;
import it.unisa.diem.ingegneriadelsoftware.service.UtenteServiceStub;
import it.unisa.diem.ingegneriadelsoftware.view.UtenteViewStub;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class UtenteControllerTest {
    private UtenteController controller;
    private UtenteViewStub view;
    private UtenteServiceStub service;

    @BeforeEach
    void setUp() {
        
        view = new UtenteViewStub(); 
        service = new UtenteServiceStub();
        
        controller = new UtenteController(view, service);
    }

    @Test
    void testSalvaUtente() {
        Utente nuovoUtente = new Utente("Daniele", "Manzo", "0612709967", "danielemanzo@uni.it");
        view.setInputNuovo(nuovoUtente);

        controller.salvaUtente();

        assertTrue(service.salvaChiamato);
        assertEquals(1, service.db.size());
        
        assertNotNull(view.listaRicevuta);
        assertEquals(1, view.listaRicevuta.size());
        assertEquals("0612709967", view.listaRicevuta.get(0).getId());
    }

    @Test
    void testSalvaUtente_InputNonValido() {
        view.setInputNuovo(null); 

        controller.salvaUtente();

        assertFalse(service.salvaChiamato);
        assertTrue(service.db.isEmpty());
    }
    
    
    @Test
    void testSalva() {
        
        Utente utente = new Utente("Matteo", "Iandiorio", "061709000", "matteoiandiorio@uni.it");
        
        controller.salva(utente);
        
        assertTrue(service.salvaChiamato);
        
        assertEquals(utente, service.db.get(0));
    }

    @Test
    void testModificaUtente() {
        
        Utente esistente = new Utente("Lorenzo", "Trovato", "0612709999", "lorenzotrovato@uni.it");
        service.db.add(esistente);
        
        view.setSelezionato(esistente);
        
        Utente modificato = new Utente("Lorenzo", "Trovato", "0612709999", "trovatolorenzo@uni.it");
        view.setInputModificato(modificato);

        controller.modificaUtente();

        assertTrue(service.modificaChiamato);
        assertEquals("trovatolorenzo@uni.it", service.db.get(0).getEmail());
        
        assertNotNull(view.listaRicevuta);
        assertEquals("trovatolorenzo@uni.it", view.listaRicevuta.get(0).getEmail());
    }

    @Test
    void testModificaUtente_NessunaSelezione() {
        
        view.setSelezionato(null);
        view.setInputModificato(null);

        controller.modificaUtente();

        assertFalse(service.modificaChiamato);
        assertNotNull(view.ultimoMessaggio);
    }
    
    @Test
    void testModificaUtente_InputNull() {
        
        Utente utente = new Utente("Vincenzo", "Raimo", "0612709555", "vincenzoraimo@uni.it");
        service.db.add(utente);
        view.setSelezionato(utente);
        
        view.setInputModificato(null);
        
        controller.modificaUtente();

        assertEquals("vincenzoraimo@uni.it", service.db.get(0).getEmail());
    }

    @Test
    void testModifica() {
        
        Utente u = new Utente("Alessandro", "Picariello", "0612709696", "alessandropicariello@uni.it");
        service.db.add(u);
        
        Utente uMod = new Utente("Alessandro", "Picariello", "0612709696", "picarielloalessandro@uni.it");
        controller.modifica(uMod);
        
        assertTrue(service.modificaChiamato);
        assertEquals("picarielloalessandro@uni.it", service.db.get(0).getEmail());
    }
    
    @Test
    void testElimina() {
        
        Utente u = new Utente("Nicola", "Picarella", "0612709444", "nicolapicarella@uni.it");
        service.db.add(u);

        view.setSelezionato(u);

        controller.elimina();

        assertTrue(service.db.isEmpty());
        assertTrue(view.listaRicevuta.isEmpty());
    }

    @Test
    void testElimina_NessunaSelezione() {
        Utente u = new Utente("Nicolò", "Lisena", "061709333", "nicololisena@uni.it");
        service.db.add(u);
        
        view.setSelezionato(null);

        controller.elimina();

        assertEquals(1, service.db.size());
        assertNotNull(view.ultimoMessaggio);
    }

    @Test
    void testCerca_FiltroCorrispondente() {
        service.db.add(new Utente("Vincenzo", "Raimo", "0612709555", "vincenzoraimo@uni.it"));
        service.db.add(new Utente("Sofia", "Mancini", "0612709666", "sofiamancini@uni.it"));

        view.setTestoCerca("Raimo");

        controller.cerca();

        assertNotNull(view.listaRicevuta);
        assertEquals(1, view.listaRicevuta.size());
        assertEquals("0612709555", view.listaRicevuta.get(0).getId());
    }

    @Test
    void testCerca_FiltroVuoto() {
        service.db.add(new Utente("Daniele", "Manzo", "0612709876", "danielemanzo@uni.it"));
        service.db.add(new Utente("Enrica", "Avitabile", "0612701234", "enricaavitabile@uni.it"));

        view.setTestoCerca(""); // Campo vuoto

        controller.cerca();

        assertEquals(2, view.listaRicevuta.size());
    }

    @Test
    void testCerca_NessunaCorrispondenza() {
        service.db.add(new Utente("Angelo", "Palladino", "0612709567", "angelopalladino@uni.it"));

        view.setTestoCerca("Manzo"); // Non esiste

        controller.cerca();

        assertTrue(view.listaRicevuta.isEmpty());
    }
    
    @Test
    void testAggiornaVista_DatiPresenti() {
        Utente utente1 = new Utente("Alessandro", "Picariello", "0612707542", "alepica@uni.it");
        Utente utente2 = new Utente("Matteo", "Iandiorio", "0612702574", "mattiand@uni.it");
        
        service.db.add(utente1);
        service.db.add(utente2);

        controller.aggiornaVista();

        assertNotNull(view.listaRicevuta);
        
        assertEquals(2, view.listaRicevuta.size());
        
        assertEquals("0612707542", view.listaRicevuta.get(0).getId());
        
        assertEquals("0612702574", view.listaRicevuta.get(1).getId());
    }

    @Test
    void testAggiornaVista_NessunDato() {
        service.db.clear();

        controller.aggiornaVista();

        assertNotNull(view.listaRicevuta);
        assertTrue(view.listaRicevuta.isEmpty());
    }
}
