package it.unisa.diem.ingegneriadelsoftware.service;

import it.unisa.diem.ingegneriadelsoftware.repository.InterfaceRepository;
import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;


package it.unisa.diem.ingegneriadelsoftware.service;

import it.unisa.diem.ingegneriadelsoftware.repository.InterfaceRepository;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class TestBaseService {

    private BaseService<Utente> service;
    private RepositoryStub<Utente> repo;
    Utente u1,u2;

    @BeforeEach
    void setup() {
        repo = new RepositoryStub<>();
        service = new BaseService<>(repo);
        u1 = new Utente("Luigi","Bianchi","0612709564","gigi@uni.it");
        u2 = new Utente("Marco","Verdi","0612709783","verdi@uni.it");
        service.salva(u1);
        service.salva(u2);
    }


    @Test
    void testCercaIdEsistente() {
       Utente utenteTrovato = service.cerca("0612709564");
        assertNotNull(utenteTrovato);
        assertEquals("0612709564", utenteTrovato.getId());
    }

@Test
    void testCercaIdNonEsistente() {
       Utente utenteTrovato = service.cerca("0000000000");
        assertNull(utenteTrovato);
    }

@Test
    void testCercaIdNull() {
        Utente utenteTrovato = service.cerca(null);
        assertNull(utenteTrovato);
    }

@Test
    void testCercaIdVuoto() {
        Utente utenteTrovato = service.cerca("");
        assertNull(utenteTrovato);
    }

@Test
    void testCercaRepositoryVuoto() {

        RepositoryStub<Utente> emptyRepo = new RepositoryStub<>();
        BaseService<Utente> emptyService = new BaseService<>(emptyRepo);
        
        Utente utenteTrovato = emptyService.cerca("*");

        assertNull(utenteTrovato);
    }

@Test
    void testCercaPiùElementiPresenti() {
        
        assertEquals(u1, service.cerca("0612709564"));
        assertEquals(u2, service.cerca("0612709783"));
    }

@Test
    void testCercaElementoAggiornato() {
        Utente u = new Utente("Mario", "Rossi", "0612709783", "rossi@uni.it");
        service.salva(u);

        Utente uAggiornato = new Utente("Mario", "Rossi", "0612709783", "mariorossi@uni.it");
        service.modifica(uAggiornato); // usa inserisciOAggiorna nel repo

        Utente utenteTrovato = service.cerca("0612709783");

        assertEquals("mariorossi@uni.it", utenteTrovato.getEmail());
    }

}
