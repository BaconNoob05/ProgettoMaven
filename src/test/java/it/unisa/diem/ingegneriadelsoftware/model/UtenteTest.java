package it.unisa.diem.ingegneriadelsoftware.model;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
/**
 * @brief Classe di test per la classe Utente.
 * @class UtenteTest
 */
public class UtenteTest {
    
    /**
     * @brief Istanza di Utente.
     */
    private Utente utente;
    
    /**
     * @brief Valore costante per nome.
     */
    private final String NOME="Lorenzo";
    
    /**
     * @brief Valore costante per cognome.
     */
    private final String COGNOME="Trovato";
    
    /**
     * @brief Valore costante per matricola.
     */
    private final String MATRICOLA="0612708922";
    
    /**
     * @brief Valore costante per email.
     */
    private final String EMAIL="l.trovato@studenti.unisa.it"; // AGGIORNATO
    
   
     /**
     * @brief Metodo eseguito prima di ogni test.
     */
    @BeforeEach
    void setUp(){
     
        utente=new Utente(NOME,COGNOME,MATRICOLA,EMAIL); 
    }
    
    /**
     * @brief Testa sia il costruttore sia i getter per vedere se funzionino correttamente,
     */
    @Test
    void testCostruttoreEGetter(){
        
       assertEquals(NOME, utente.getNome());
       assertEquals(COGNOME, utente.getCognome());
       assertEquals(MATRICOLA, utente.getMatricola());
       assertEquals(EMAIL, utente.getEmail());
        
        
    }
    
    
    /**
     * @brief Testa getId .
     */
    @Test
    void testGetID(){
        assertEquals(MATRICOLA, utente.getId());
    }
    
    /**
     * @brief Testa il setter per il nome.
     */
    @Test
    void testSetNome(){
        String nuovoNome="Alessandro";
        utente.setNome(nuovoNome);
        assertEquals(nuovoNome, utente.getNome());
    }
    
    /**
     * @brief Testa il setter per il cognome.
     */
    @Test
    void testSetCognome(){
        String nuovoCognome="Picariello";
        utente.setCognome(nuovoCognome);
        assertEquals(nuovoCognome, utente.getCognome());
    }
    
    /**
     * @brief Testa il setter per l'email.
     */
    @Test
    void testSetEmail(){
        String nuovaEmail = "a.picariello42@studenti.unisa.it"; // AGGIORNATO
        utente.setEmail(nuovaEmail);
        assertEquals(nuovaEmail, utente.getEmail());
    }

    /**
     * @brief Testa il setter per la matricola.
     */
    @Test
    void testSetMatricola(){
        String nuovaMatricola = "0000000001";
        utente.setMatricola(nuovaMatricola);
        assertEquals(nuovaMatricola, utente.getMatricola());
        assertEquals(nuovaMatricola, utente.getId());
    }

    /**
     * @brief Testa che il costruttore lanci un'IllegalArgumentException se l'email non è conforme.
     */
    @Test
    void testCostruttore_EmailNonConformeLanciaEccezione(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Utente(NOME, COGNOME, MATRICOLA, "email@sbagliata.it");
        });
    }

    /**
     * @brief Testa che il setter email lanci un'IllegalArgumentException se l'email non è conforme.
     */
    @Test
    void testSetEmail_NonConformeLanciaEccezione(){
        assertThrows(IllegalArgumentException.class, () -> {
            utente.setEmail("email@sbagliata.it");
        });
    }
    
    /**
     * @brief Testa isValido .
     */
    @Test
    void testIsValido(){
        //aggiungo un utente valido
        assertTrue(utente.isValido());
    }
    
    /**
     * @brief Testa che il costruttore lanci un' IllegalArgumentException  se il nome è nulla.
     */
    @Test
    void testCostruttore_UtenteSenzaNomeLanciaEccezione(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Utente(null,COGNOME,MATRICOLA,EMAIL);
        });
    }   
    
    /**
     * @brief Testa che il costruttore lanci un' IllegalArgumentException se la matricola è nulla.
     */
    @Test
    void testCostruttore_UtenteSenzaMatricolaLanciaEccezione(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Utente(NOME,COGNOME,null,EMAIL);
        });
    }   

    
    /**
     * @brief Testa toString.
     */
    @Test
    void testToString(){
        String stringaUtente = utente.toString();
        assertNotNull(stringaUtente);
       
        assertTrue(stringaUtente.contains(NOME));
        assertTrue(stringaUtente.contains(COGNOME));
        assertTrue(stringaUtente.contains(MATRICOLA));
        assertTrue(stringaUtente.contains(EMAIL));
        
        
    }
    
}