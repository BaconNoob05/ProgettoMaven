package it.unisa.diem.ingegneriadelsoftware.service;

import it.unisa.diem.ingegneriadelsoftware.model.*;
import it.unisa.diem.ingegneriadelsoftware.repository.*;
import org.junit.jupiter.api.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
/**
 * @brief Classe di test per LibroService.
 * @class LibroServiceTest
 */
public class LibroServiceTest {

    /**
     * @brief Istanza del servizio.
     */
    private LibroService service;
    
    /**
     * @brief Stub del repository.
     */
    private RepositoryStub<Libro> repo;
    
    /**
     * @brief Oggetto Libro per i test.
     */
    private Libro libroNuovoJava;
    
    /**
     * @brief Oggetto Libro per i test.
     */
    private Libro libroSoftwareEng;
    
    /**
     * @brief Oggetto Libro per i test.
     */
    private Libro libroChristmasCarol;
    
    /**
     * @brief Oggetto Libro per i test.
     */
    private Libro libroCriticaRagionPura;
    
    /**
     * @brief Oggetto Libro per i test.
     */
    private Libro libroEssereETempo;
    
 /**
     * @brief Metodo eseguito prima di ogni test.
     */
    @BeforeEach
    public void setUp() {
        
        List<String> autoriDeSio = Arrays.asList("Claudio De Sio Cesari");
        
        List<String> autoriSommerville = Arrays.asList("Ian Sommerville");
        List<String> autoriDickens = Arrays.asList("Charles Dickens");
        
        List<String> autoriKant = Arrays.asList("Immanuel Kant");
        List<String> autoriHeidegger = Arrays.asList("Martin Heidegger");
        

        libroNuovoJava = new Libro("Il nuovo Java. Guida definitiva", autoriDeSio, 2023, "978-8868945620", 12);
        libroSoftwareEng = new Libro("Software Engineering", autoriSommerville, 2020, "978-0133943030", 15);
        libroChristmasCarol = new Libro("A Christmas Carol", autoriDickens, 1843, "978-0141439247", 8);
        libroCriticaRagionPura = new Libro("Critica della ragion pura", autoriKant, 1781, "978-8842095810", 3);
        libroEssereETempo = new Libro("Essere e tempo", autoriHeidegger, 1927, "978-8842095902", 4);

        repo = new RepositoryStub<Libro>();
        
        List<Libro> datiIniziali = Arrays.asList(
            libroNuovoJava, 
            libroSoftwareEng, 
            libroChristmasCarol, 
            libroCriticaRagionPura, 
            libroEssereETempo
        );
        repo.caricaTutti(datiIniziali);

        service = new LibroService(repo);
    }
    
    /**
     * @brief Testa la ricerca per titolo in base a un filtro con un risultato.
     */
    @Test
    void testCercaPerTitolo_MatchSingolo() {
        
        final String FILTRO_TITOLO = "Christmas"; 
        List<Libro> risultati = service.cercaPerTitolo(FILTRO_TITOLO);

        assertEquals(1, risultati.size());
        assertEquals(libroChristmasCarol.getId(), risultati.get(0).getId());
    }

    /**
     * @brief Testa la ricerca per titolo in base a un filtro con più risultati .
     */
    @Test
    void testCercaPerTitolo_MatchMultiplo() {
        
        final String FILTRO_TITOLO = "Ragion";
        List<Libro> risultati = service.cercaPerTitolo(FILTRO_TITOLO);

        assertEquals(1, risultati.size()); 
        assertTrue(risultati.contains(libroCriticaRagionPura));
    }

    /**
     * @brief Testa la ricerca per titolo in base a un filtro con zero risultati.
     */
    @Test
    void testCercaPerTitolo_NessunMatch() {
        final String FILTRO_TITOLO = "Astrologia";
        List<Libro> risultati = service.cercaPerTitolo(FILTRO_TITOLO);

        assertTrue(risultati.isEmpty());
    }
    
    /**
     * @brief Testa la ricerca per titolo con  l'insensibilità case .
     */
    @Test
    void testCercaPerTitolo_FiltroCaseInsensitive() {
        
        final String FILTRO_TITOLO = "guida";
        List<Libro> risultati = service.cercaPerTitolo(FILTRO_TITOLO);
        
        assertEquals(1, risultati.size()); 
        assertTrue(risultati.contains(libroNuovoJava));
    }
    
    /**
     * @brief Testa la ricerca per titolo con un filtro nullo e restituisce una lista vuota.
     */
    @Test
    void testCercaPerTitolo_FiltroNullo_ComportamentoAtteso() {
        assertTrue(service.cercaPerTitolo(null).isEmpty());
    }

    /**
     * @brief Testa la ricerca per autore completo con una sola corrispondenza .
     */
    @Test
    void testCercaPerAutore_MatchSingoloAutoreCompleto() {
        final String FILTRO_AUTORE = "Charles Dickens"; 
        List<Libro> risultati = service.cercaPerAutore(FILTRO_AUTORE);

        assertEquals(1, risultati.size());
        assertEquals(libroChristmasCarol.getId(), risultati.get(0).getId());
    }

    /**
     * @brief Testa la ricerca per autore parziale con una sola corrispondenza.
     */
    @Test
    void testCercaPerAutore_MatchMultiplo_NomeParziale() {
        final String FILTRO_AUTORE = "Kant";
        List<Libro> risultati = service.cercaPerAutore(FILTRO_AUTORE);

        assertEquals(1, risultati.size()); 
        assertTrue(risultati.contains(libroCriticaRagionPura));
    }
    
    /**
     * @brief Testa la ricerca per autore quando è associato a più libri.
     */
    @Test
    void testCercaPerAutore_MatchMultiplo_StessoAutore() {
        
        List<String> autoriDeSio = Arrays.asList("Claudio De Sio Cesari");
        
        Libro libroJavaAdvanced = new Libro("Java Advanced", autoriDeSio, 2022, "978-8868945637", 6);
        repo.caricaTutti(Arrays.asList(libroJavaAdvanced)); 

        final String FILTRO_AUTORE = "Claudio De Sio Cesari";
        List<Libro> risultati = service.cercaPerAutore(FILTRO_AUTORE);

        assertEquals(2, risultati.size());
        assertTrue(risultati.contains(libroNuovoJava));
        assertTrue(risultati.contains(libroJavaAdvanced));
    }

    /**
     * @brief Testa la ricerca per autore con zero risultati.
     */
    @Test
    void testCercaPerAutore_NessunMatch() {
        
        final String FILTRO_AUTORE = "Stephen King";
        List<Libro> risultati = service.cercaPerAutore(FILTRO_AUTORE);

        assertTrue(risultati.isEmpty());
    }
    
    /**
     * @brief Testa la ricerca per autore con l'insensibilità case.
     */
    @Test
    void testCercaPerAutore_FiltroCaseInsensitive() {
        
        final String FILTRO_AUTORE = "martin heidegger";
        List<Libro> risultati = service.cercaPerAutore(FILTRO_AUTORE);

        assertEquals(1, risultati.size()); 
        assertTrue(risultati.contains(libroEssereETempo));
    }
    
    /**
     * @brief Testa la ricerca per autore con filtri vuoti o nulli e  restituiscono una lista vuota.
     */
    @Test
    void testCercaPerAutore_FiltroVuotoONullo_ComportamentoAtteso() {
        
        assertTrue(service.cercaPerAutore(null).isEmpty());
        assertTrue(service.cercaPerAutore("").isEmpty());
        assertTrue(service.cercaPerAutore(" ").isEmpty());
        
    }
    
    /**
     * @brief Test per verificare che il repository stub non abbia problemi.
     */
    @Test
    void testDebugRepository() {
        assertEquals(5, repo.getAll().size());
    }
    
    
    
    
}
