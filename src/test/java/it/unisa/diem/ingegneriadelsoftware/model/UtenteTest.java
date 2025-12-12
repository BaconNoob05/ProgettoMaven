/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.ingegneriadelsoftware.model;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;






public class UtenteTest {
    
    private Utente utente;
    private final String NOME="Lorenzo";
    private final String COGNOME="Trovato";
    private final String MATRICOLA="0612708922";
    private final String EMAIL="l.trovato@studenti.unisa.it";
    
    @BeforeEach
    void setUp(){
        utente=new Utente(NOME,COGNOME,MATRICOLA,EMAIL);
    }
    
    @Test
    void testCostruttoreEGetter(){
        
       assertEquals(NOME, utente.getNome());
       assertEquals(COGNOME, utente.getCognome());
       assertEquals(MATRICOLA, utente.getMatricola());
       assertEquals(EMAIL, utente.getEmail());
        
        
    }
    
    
    @Test
    void testGetID(){
        assertEquals(MATRICOLA, utente.getId());
    }
    
    @Test
    void testSetNome(){
        String nuovoNome="Alessandro";
        utente.setNome(nuovoNome);
        assertEquals(nuovoNome, utente.getNome());
    }
    
    @Test
    void testSetCognome(){
        String nuovoCognome="Picariello";
        utente.setCognome(nuovoCognome);
        assertEquals(nuovoCognome, utente.getCognome());
    }
    
    @Test
    void testSetEmail(){
        String nuovaEmail = "a.picariello42@studenti.unisa.it";
        utente.setEmail(nuovaEmail);
        assertEquals(nuovaEmail, utente.getEmail());
    }
    
    @Test
    void testIsValido(){
        //aggiungo un utente valido
        assertTrue(utente.isValido());
    }
    
    @Test
    void testIsValido_UtenteSenzaNome(){
        //aggiungo un utente con nome mancante
        Utente utenteSenzaNome=new Utente(null,COGNOME,MATRICOLA,EMAIL);
        assertFalse(utenteSenzaNome.isValido());
    }   
    
    @Test
    void testIsValido_UtenteSenzaMatricola(){
        //aggiungo un utente con matricola mancante
        Utente utenteSenzaMatricola=new Utente(NOME,COGNOME,null,EMAIL);
        assertFalse(utenteSenzaMatricola.isValido());
    }  

    /*
    @Test
    void testIsValido_EmailNonCorretta(){
        //aggiungo un utente con un email sbagliata (errore di sintassi)
        Utente utenteEmailSbagliata=new Utente(NOME,COGNOME,MATRICOLA, "l.trovato1@sudenti.unisa.it");;
        assertFalse(utenteEmailSbagliata.isValido());
    } 
    */
    
    @Test
    void testToString(){
         //Verifica che la stringa non sia null
        String stringaUtente = utente.toString();
        assertNotNull(stringaUtente);
        
        // Verifica che la stringa contenga i dati chiave
        assertTrue(stringaUtente.contains(NOME));
        assertTrue(stringaUtente.contains(COGNOME));
        assertTrue(stringaUtente.contains(MATRICOLA));
        assertTrue(stringaUtente.contains(EMAIL));
        
        
    }
    
    
    
}
