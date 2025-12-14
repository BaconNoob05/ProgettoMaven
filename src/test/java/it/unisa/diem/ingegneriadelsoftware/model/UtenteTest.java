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
    private final String EMAIL="l.trovato@studenti.unisa.it";
    
   
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
        String nuovaEmail = "a.picariello42@studenti.unisa.it";
        utente.setEmail(nuovaEmail);
        assertEquals(nuovaEmail, utente.getEmail());
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

    /*
     * @brief Test per verificare la validità dell'utente con un'email con errori.
     */
    /*
    @Test
    void testIsValido_EmailNonCorretta(){
        //aggiungo un utente con un email sbagliata (errore di sintassi)
        Utente utenteEmailSbagliata=new Utente(NOME,COGNOME,MATRICOLA, "l.trovato1@sudenti.unisa.it");;
        assertFalse(utenteEmailSbagliata.isValido());
    } 
    */
    
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
