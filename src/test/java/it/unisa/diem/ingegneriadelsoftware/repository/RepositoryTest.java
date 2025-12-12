/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.ingegneriadelsoftware.repository;
import it.unisa.diem.ingegneriadelsoftware.model.DatiStub;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Utente
 */

public class RepositoryTest {
    private Repository<DatiStub> repository;
    private GestoreFileStub<DatiStub> gestore;
    private final String nomeFile = "test.txt";

    private DatiStub dato1;
    private DatiStub dato2;

    @BeforeEach
    void setUp() {
        gestore = new GestoreFileStub<>();
        
        // Il repository proverà a caricare i dati all'avvio (troverà lista vuota dallo stub)
        repository = new Repository<>(nomeFile, gestore);

        dato1 = new DatiStub("ID_1");
        dato2 = new DatiStub("ID_2");
    }
    
    @AfterEach
    void tearDown() {

        File file = new File(nomeFile);
        if (file.exists()) {
            file.delete();

        }

    }
    
    @Test
    void testInserisci_NuovoElemento() {
        repository.inserisciOAggiorna(dato1);
        
        List<DatiStub> risultati = repository.getAll();
        assertEquals(1, risultati.size());
        assertEquals(dato1, repository.cerca("ID_1"));
    }

    @Test
    void testInserisci_AggiornamentoElemento() {
        repository.inserisciOAggiorna(dato1);
        
        DatiStub dato1_Aggiornato = new DatiStub("ID_1"); 
        repository.inserisciOAggiorna(dato1_Aggiornato);

        List<DatiStub> risultati = repository.getAll();
        assertEquals(1, risultati.size());
        assertTrue(risultati.contains(dato1_Aggiornato));
    }

    @Test
    void testInserisci_ElementoNull() {
        //Inseriamo un elemento nullo
        assertThrows(IllegalArgumentException.class, () -> {repository.inserisciOAggiorna(null);});
    }

    @Test
    void testInserisci_ElementoConIdNull() {
        DatiStub datoConIdNull = new DatiStub(null);
        //Inseriamo un elemento con ID nullo
        assertThrows(IllegalArgumentException.class, () -> {repository.inserisciOAggiorna(datoConIdNull);});
    }

    @Test
    void testElimina_ElementoEsistente() {
        repository.inserisciOAggiorna(dato1);
        repository.inserisciOAggiorna(dato2);
        
        repository.elimina("ID_1");
        
        assertEquals(1, repository.getAll().size());
        assertNull(repository.cerca("ID_1"));
        assertNotNull(repository.cerca("ID_2"));
    }

    @Test
    void testElimina_ElementoInesistente() {
        repository.inserisciOAggiorna(dato1);
        
        //Eliminiamo un elemento con id inesistente
        repository.elimina("ID_CHE_NON_ESISTE");
        
        assertEquals(1, repository.getAll().size());
    }

    @Test
    void testElimina_IdNull() {
        repository.inserisciOAggiorna(dato1);
        
        //Eliminiamo un elemento con ID nullo
        assertDoesNotThrow(() -> repository.elimina(null));
        
        assertEquals(1, repository.getAll().size());
    }

    @Test
    void testCerca_ElementoPresente() {
        repository.inserisciOAggiorna(dato1);
        
        DatiStub risultato = repository.cerca("ID_1");
        
        assertNotNull(risultato);
        assertEquals("ID_1", risultato.getId());
    }

    @Test
    void testCerca_ElementoAssente() {
        repository.inserisciOAggiorna(dato1);
        
        //Cerchiamo un elemento con id che non esiste
        DatiStub risultato = repository.cerca("ID_CHE_NON_ESISTE");
        
        assertNull(risultato);
    }

    @Test
    void testCerca_IdNull() {
        DatiStub risultato = repository.cerca(null);
        assertNull(risultato);
    }

    @Test
    void testGetAll_ListaVuota() {
        assertTrue(repository.getAll().isEmpty());
    }

    @Test
    void testGetAll_ListaRiempita() {
        repository.inserisciOAggiorna(dato1);
        repository.inserisciOAggiorna(dato2);
        
        List<DatiStub> lista = repository.getAll();
        assertEquals(2, lista.size());
        assertTrue(lista.contains(dato1));
        assertTrue(lista.contains(dato2));
    }

    @Test
    void testGetAll_Incapsulamento() {
        //Verifichiamo che getAll restituisca una copia e non il riferimento diretto
        repository.inserisciOAggiorna(dato1);
        
        List<DatiStub> copiaLista = repository.getAll();
        copiaLista.clear();
        
        assertEquals(1, repository.getAll().size());
    }

    @Test
    void testSalvaSuFile() {
        repository.inserisciOAggiorna(dato1);
        repository.inserisciOAggiorna(dato2);
        
        repository.salvaSuFile();
        
        List<DatiStub> dati = gestore.caricaDati(nomeFile);
        
        assertEquals(2, dati.size());
        assertTrue(dati.contains(dato1));
    }

    @Test
    void testCaricaTutti() {
        GestoreFileStub<DatiStub> gestorePreriempito = new GestoreFileStub<>();
        List<DatiStub> datiVecchi = Arrays.asList(new DatiStub("ID_OLD_1"), new DatiStub("ID_OLD_2"));
        
        gestorePreriempito.salvaDati(nomeFile, datiVecchi);
        
        Repository<DatiStub> repoConDati = new Repository<>(nomeFile, gestorePreriempito);
        
        List<DatiStub> dati = repoConDati.getAll();
        
        assertEquals(2, dati.size());
        assertEquals("ID_OLD_1", dati.get(0).getId());
    }
}
