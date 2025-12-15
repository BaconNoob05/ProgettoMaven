package it.unisa.diem.ingegneriadelsoftware.repository;
import it.unisa.diem.ingegneriadelsoftware.model.DatiStub;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
/**
 * @brief Classe di test per Repository.
 * @class RepositoryTest
 */

public class RepositoryTest {
    
    /**
     * @brief Istanza del Repository .
     */
    private Repository<DatiStub> repository;
    
    /**
     * @brief Stub  per simulare il salvataggio dati.
     */
    private GestoreFileStub<DatiStub> gestore;
    
    /**
     * @brief Nom del file.
     */
    private final String nomeFile = "test.txt";

    /**
     * @brief Oggetto per i test.
     */
    private DatiStub dato1;
    
    /**
     * @brief Oggetto per i test .
     */
    private DatiStub dato2;

    /**
     * @brief Metodo eseguito prima di ogni test.
     */
    @BeforeEach
    void setUp() {
        gestore = new GestoreFileStub<>();
        repository = new Repository<>(nomeFile, gestore);

        dato1 = new DatiStub("ID_1");
        dato2 = new DatiStub("ID_2");
    }
    
    /**
     * @brief Cancella tutti i file usati durante i test.
     */
    @AfterEach
    void eliminaFile() {
        File file = new File(nomeFile);
        if (file.exists()) {
            file.delete();
        }
    }
    
    /**
     * @brief Testa il costruttore verificando il caricamento dei dati all'inizializzazione del repository.
     */
    @Test /*caso di test 67 */
    void testCostruttore_CaricamentoDatiIniziali() {
        GestoreFileStub<DatiStub> gestorePreriempito = new GestoreFileStub<>();
        List<DatiStub> datiVecchi = Arrays.asList(new DatiStub("ID_VECCHIO_1"), new DatiStub("ID_VECCHIO_2"));

        gestorePreriempito.salvaDati(nomeFile, datiVecchi); 

        Repository<DatiStub> repoConDati = new Repository<>(nomeFile, gestorePreriempito);
        

        List<DatiStub> dati = repoConDati.getAll();
        
        assertEquals(2, dati.size());
        assertEquals("ID_VECCHIO_1", dati.get(0).getId());
    }
    
    /**
     * @brief Testa il costruttore per assicurare che il repository venga inizializzato
     * correttamente (lista vuota) quando non ci sono dati preesistenti.
     */
    @Test /*caso di test 68 */
    void testCostruttore_InizializzazioneVuota() {

        assertTrue(repository.getAll().isEmpty());
    }
    
    /**
     * @brief Testa l'inserimento di un nuovo elemento nel repository.
     */
    @Test /*caso di test 69 */
    void testInserisci_NuovoElemento() {
        repository.inserisciOAggiorna(dato1);
        List<DatiStub> risultati = repository.getAll();
        
        assertEquals(1, risultati.size());
        assertEquals(dato1, repository.cerca("ID_1"));
    }

    /**
     * @brief Testa l'aggiornamento di un elemento esistente.
     */
    @Test /*caso di test 70 */
    void testInserisci_AggiornamentoElemento() {
        repository.inserisciOAggiorna(dato1);
        DatiStub dato1_Aggiornato = new DatiStub("ID_1"); 
        repository.inserisciOAggiorna(dato1_Aggiornato);
        List<DatiStub> risultati = repository.getAll();
        
        assertEquals(1, risultati.size());
        assertTrue(risultati.contains(dato1_Aggiornato));
    }

    /**
     * @brief Testa l'inserimento di un elemento nullo.
     */
    @Test /*caso di test 71 */
    void testInserisci_ElementoNull() {
        //Inseriamo un elemento nullo
        assertThrows(IllegalArgumentException.class, () -> {repository.inserisciOAggiorna(null);});
    }

    /**
     * @brief Testa l'inserimento di un elemento con ID nullo.
     */
    @Test /*caso di test 72 */
    void testInserisci_ElementoConIdNull() {
        DatiStub datoConIdNull = new DatiStub(null);
        //Inseriamo un elemento con ID nullo.
        assertThrows(IllegalArgumentException.class, () -> {repository.inserisciOAggiorna(datoConIdNull);});
    }

    /**
     * @brief Testa l'eliminazione di un elemento esistente.
     */
    @Test /*caso di test 73 */
    void testElimina_ElementoEsistente() {
        repository.inserisciOAggiorna(dato1);
        repository.inserisciOAggiorna(dato2);
        repository.elimina("ID_1");
        
        assertEquals(1, repository.getAll().size());
        assertNull(repository.cerca("ID_1"));
        assertNotNull(repository.cerca("ID_2"));
    }

    /**
     * @brief Testa l'eliminazione di un elemento inesistente.
     */
    @Test /*caso di test 74 */
    void testElimina_ElementoInesistente() {
        repository.inserisciOAggiorna(dato1);
        //Eliminiamo un elemento che non esiste.
        repository.elimina("ID_INESISTENTE");
        
        assertEquals(1, repository.getAll().size());
    }

    /**
     * @brief Testa l'eliminazione con ID nullo.
     */
    @Test /*caso di test 75 */
    void testElimina_IdNull() {
        repository.inserisciOAggiorna(dato1);
 
        assertDoesNotThrow(() -> repository.elimina(null));
        assertEquals(1, repository.getAll().size());
    }

    /**
     * @brief Testa la ricerca di un elemento in base all'ID.
     */
    @Test /*caso di test 76 */
    void testCerca_ElementoPresente() {
        repository.inserisciOAggiorna(dato1);
        DatiStub risultato = repository.cerca("ID_1");
        
        assertNotNull(risultato);
        assertEquals("ID_1", risultato.getId());
    }

    /**
     * @brief Testa la ricerca di un elemento assente tramite ID.
     */
    @Test /*caso di test 77 */
    void testCerca_ElementoAssente() {
        repository.inserisciOAggiorna(dato1);
        //Cerchiamo un elemento che non esiste.
        DatiStub risultato = repository.cerca("ID_INESISTENTE");
        
        assertNull(risultato);
    }

    /**
     * @brief Testa la ricerca con ID nullo.
     */
    @Test /*caso di test 78 */
    void testCerca_IdNull() {
        //Cerchiamo un elemento con id null.
        DatiStub risultato = repository.cerca(null);
        
        assertNull(risultato);
    }

    /**
     * @brief Testa il recupero di tutti gli elementi quando la lista è vuota.
     */
    @Test /*caso di test 79 */
    void testGetAll_ListaVuota() {
        assertTrue(repository.getAll().isEmpty());
    }

    /**
     * @brief Testa il recupero di tutti gli elementi quando la lista è stata riempita.
     */
    @Test /*caso di test 80 */
    void testGetAll_ListaRiempita() {
        repository.inserisciOAggiorna(dato1);
        repository.inserisciOAggiorna(dato2);
        
        List<DatiStub> lista = repository.getAll();
        assertEquals(2, lista.size());
        assertTrue(lista.contains(dato1));
        assertTrue(lista.contains(dato2));
    }

    /**
     * @brief Testa getAll.
     */
    @Test /*caso di test 81 */
    void testGetAll_Incapsulamento() {
        repository.inserisciOAggiorna(dato1);
        List<DatiStub> copiaLista = repository.getAll();
        copiaLista.clear();
        
        assertEquals(1, repository.getAll().size());
    }

    /**
     * @brief Testa il salvataggio dei dati.
     */
    @Test /*caso di test 82 */
    void testSalvaSuFile() {
        repository.inserisciOAggiorna(dato1);
        repository.inserisciOAggiorna(dato2);
        repository.salvaSuFile();
        List<DatiStub> dati = gestore.caricaDati(nomeFile);
        
        assertEquals(2, dati.size());
        assertTrue(dati.contains(dato1));
    }

    /**
     * @brief Testa il caricamento dei dati quando si  inizializzazione il repository.
     */
    @Test /*caso di test 83 */
    void testCaricaTutti() {
        GestoreFileStub<DatiStub> gestorePreriempito = new GestoreFileStub<>();
        List<DatiStub> datiVecchi = Arrays.asList(new DatiStub("ID_VECCHIO_1"), new DatiStub("ID_VECCHIO_2"));
        gestorePreriempito.salvaDati(nomeFile, datiVecchi);
        Repository<DatiStub> repoConDati = new Repository<>(nomeFile, gestorePreriempito);
        List<DatiStub> dati = repoConDati.getAll();
        
        assertEquals(2, dati.size());
        assertEquals("ID_VECCHIO_1", dati.get(0).getId());
    }
}
