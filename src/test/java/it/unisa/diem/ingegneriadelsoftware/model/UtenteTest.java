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
    @Test /*caso di test 44 */
    void testCostruttoreEGetter(){
        
       assertEquals(NOME, utente.getNome());
       assertEquals(COGNOME, utente.getCognome());
       assertEquals(MATRICOLA, utente.getMatricola());
       assertEquals(EMAIL, utente.getEmail());
        
        
    }
    
    
    /**
     * @brief Testa il getter per l'id.
     */
    @Test /*caso di test 45 */
    void testGetID(){
        assertEquals(MATRICOLA, utente.getId());
    }
    
    /**
     * @brief Testa il setter per il nome.
     */
    @Test /*caso di test 46 */
    void testSetNome(){
        String nuovoNome="Alessandro";
        utente.setNome(nuovoNome);
        assertEquals(nuovoNome, utente.getNome());
    }
    
    /**
     * @brief Testa il setter per il cognome.
     */
    @Test /*caso di test 47 */
    void testSetCognome(){
        String nuovoCognome="Picariello";
        utente.setCognome(nuovoCognome);
        assertEquals(nuovoCognome, utente.getCognome());
    }
    
    /**
     * @brief Testa il setter per l'email.
     */
    @Test /*caso di test 48 */
    void testSetEmail(){
        String nuovaEmail = "a.picariello42@studenti.unisa.it";
        utente.setEmail(nuovaEmail);
        assertEquals(nuovaEmail, utente.getEmail());
    }

    /**
     * @brief Testa il setter per la matricola.
     */
    @Test /*caso di test 49 */
    void testSetMatricola(){
        String nuovaMatricola = "0000000001";
        utente.setMatricola(nuovaMatricola);
        assertEquals(nuovaMatricola, utente.getMatricola());
        assertEquals(nuovaMatricola, utente.getId());
    }

    /**
     * @brief Testa che il costruttore lanci un'IllegalArgumentException se l'email non è conforme.
     */
    @Test /*caso di test 50 */
    void testCostruttore_EmailNonConformeLanciaEccezione(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Utente(NOME, COGNOME, MATRICOLA, "email@sbagliata.it");
        });
    }

    /**
     * @brief Testa che il setter email lanci un'IllegalArgumentException se l'email non è conforme.
     */
    @Test /*caso di test 51 */
    void testSetEmail_NonConformeLanciaEccezione(){
        assertThrows(IllegalArgumentException.class, () -> {
            utente.setEmail("email@sbagliata.it");
        });
    }
    
    /**
     * @brief Testa isValido .
     */
    @Test /*caso di test 52 */
    void testIsValido(){
        assertTrue(utente.isValido());
    }
    
    /**
     * @brief Testa che il costruttore lanci un' IllegalArgumentException  se il nome è nulla.
     */
    @Test /*caso di test 53 */
    void testCostruttore_UtenteSenzaNomeLanciaEccezione(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Utente(null,COGNOME,MATRICOLA,EMAIL);
        });
    }   
    
    /**
     * @brief Testa che il costruttore lanci un' IllegalArgumentException se la matricola è nulla.
     */
    @Test /*caso di test 54 */
    void testCostruttore_UtenteSenzaMatricolaLanciaEccezione(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Utente(NOME,COGNOME,null,EMAIL);
        });
    }   

    
    /**
     * @brief Testa toString.
     */
    @Test /*caso di test 55 */
    void testToString(){
        String stringaUtente = utente.toString();
        assertNotNull(stringaUtente);
        
        assertTrue(stringaUtente.contains(NOME));
        assertTrue(stringaUtente.contains(COGNOME));
        assertTrue(stringaUtente.contains(MATRICOLA));
        assertTrue(stringaUtente.contains(EMAIL));
    }
    
    /**
     * @brief Testa che setMatricola lanci un'IllegalArgumentException se la matricola è nulla o vuota.
     */
    @Test /*caso di test 56 */
    void testSetMatricola_MatricolaNullaOVuota(){
        assertThrows(IllegalArgumentException.class, () -> {
            utente.setMatricola(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            utente.setMatricola("");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            utente.setMatricola(" ");
        });
    } 
    
    /**
     * @brief Testa il getter per il nome.
     */
    @Test /*caso di test 57 */
    void testGetNome() {
        assertEquals(NOME, utente.getNome());
    }

    /**
     * @brief Testa il getter per il cognome.
     */
    @Test /*caso di test 58 */
    void testGetCognome() {
        assertEquals(COGNOME, utente.getCognome());
    }

    /**
     * @brief Testa il getter per la matricola (oltre a testGetID).
     */
    @Test /*caso di test 59 */
    void testGetMatricola() {
        assertEquals(MATRICOLA, utente.getMatricola());
    }

    /**
     * @brief Testa il getter per l'email.
     */
    @Test /*caso di test 60 */
    void testGetEmail() {
        assertEquals(EMAIL, utente.getEmail());
    }
    
}
