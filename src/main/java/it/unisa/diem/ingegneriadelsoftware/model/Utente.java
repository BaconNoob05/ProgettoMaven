package it.unisa.diem.ingegneriadelsoftware.model;

/**
 * @class Utente
 * @brief Nel sistema questa classe è l'entità Utente.
 * Le informazioni che possiede sono i seguenti dati: nome, cognome, matricola ed email.
 * @see Dati
 */
public class Utente extends Dati {
    /**
     * @brief Identificativo univoco per la serializzazione. 
     * @details Garantisce la compatibilità tra l'oggetto serializzato e la classe 
     * caricata durante la fase di deserializzazione. Se questo ID non corrisponde 
     * a quello dell'oggetto salvato, viene lanciata una InvalidClassException.
     */
     private static final long serialVersionUID = 1L;

   /**
     * @brief Nome dell'utente.
     */
    private String nome;
    
    /**
     * @brief Cognome dell'utente.
     */
    private String cognome;
    
   /**
     * @brief Matricola univoca dell'utente.
     */
    private String matricola;
    
     /**
     * @brief Indirizzo email dell'utente.
     */
    private String email;
    
    /**
     * @brief Costruttore della classe Utente.
     * @param [in] nome Il nome dell'utente.
     * @param [in] cognome Il cognome dell'utente.
     * @param [in] matricola La matricola univoca.
     * @param [in] email L'indirizzo email.
     */
    public Utente(String nome, String cognome, String matricola, String email) {
        // La validazione qui sotto è quella che causa l'errore nei vecchi test
        if (nome == null || nome.trim().isEmpty() ||
            cognome == null || cognome.trim().isEmpty() ||
            matricola == null || matricola.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome, cognome e matricola sono obbligatori.");
        }
        
        this.nome = nome;
        this.cognome = cognome;
        this.matricola = matricola;
        this.email = email;
    }

     /**
     * @brief Verifica che i dati dell'utente siano validi.
     * @return Vero se i dati sono validi, altrimenti restituisce falso.
     */
    public boolean isValido() {
        return nome != null && !nome.trim().isEmpty() && cognome != null && !cognome.trim().isEmpty() && matricola != null && !matricola.trim().isEmpty();
    }

    /**
     * @brief Ottiene la matricola dell'utente.
     * @return La matricola dell'utente, altrimenti restituisce un valore nullo.
     * @see Dati#getId()
     */
    @Override 
    public String getId() {
        return matricola;
    }

 
    /**
     * @brief Restituisce il nome dell'utente.
     * @return Il nome dell'utente, altrimenti restituisce un valore nullo.
     */
    public String getNome() {
        return nome;
    }

    /** * @brief Restituisce il cognome dell'utente.
     * @return Il cognome dell'utente, altrimenti restituisce un valore nullo.
     */

    public String getCognome() {
        return cognome;
    }

   /**
     * @brief Restituisce la matricola dell'utente.
     * @return La matricola dell'utente, altrimenti restituisce un valore nullo.
     */
    public String getMatricola() {
        return matricola;
    }

    /**
     * @brief Restituisce l'email dell'utente.
     * @return L'email dell'utente, altrimenti restituisce un valore nullo.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @brief Imposta il nome dell'utente.
     * @param n Il nuovo nome.
     * @pre n non deve essere nullo.
     * @post Il nome è uguale a n.
     */
    public void setNome(String n) {
        this.nome = n;
    }

   /**
     * @brief Imposta il cognome dell'utente.
     * @param c Il nuovo cognome.
     * @pre c non deve essere nullo.
     * @post Il cognome è uguale a c.
     */
    public void setCognome(String c) {
        this.cognome = c;
    }

   /**
     * @brief Imposta l'indirizzo email dell'utente.
     * @param e La nuova email.
     * @pre e non deve essere nullo.
     * @post L' email è uguale ad e.
     */
    public void setEmail(String e) {
        this.email = e;
    }

    /**
     * @brief Restituisce una rappresentazione in formato stringa dell'utente.
     * @details Genera una stringa contenente i dati anagrafici dell'utente,
     * quali nome, cognome, matricola ed email.
     * @return Una stringa formattata con i dettagli dell'utente, altrimenti restituisce un valore nullo.
     * @pre L'oggetto Utente deve essere stato inizializzato.
     * @post I dati interni dell'oggetto non subiscono modifiche.
     * @see Dati#toString()
     */
    @Override
    public String toString() {
        return String.format("Matricola: %s | Nome: %s |Cognome :%s | Email: %s", matricola, nome, cognome, email);
    }
}