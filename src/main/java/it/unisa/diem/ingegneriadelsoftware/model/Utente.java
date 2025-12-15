package it.unisa.diem.ingegneriadelsoftware.model;

/**
 * @class Utente
 * @brief Nel sistema questa classe è l'entità Utente.
 * Le informazioni che possiede sono i seguenti dati: nome, cognome, matricola ed email.
 * @see Dati
 */
public class Utente extends Dati {

    private static final String EMAIL_SUFFIX = "@studenti.unisa.it"; 

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
     * @throws IllegalArgumentException Se uno tra i parametri nome, cognome e matricola sono nulli o vuoti o se l'email non è valida, viene lanciata un'eccezione.
     */
    public Utente(String nome, String cognome, String matricola, String email) {
        
        //validazione campi obbligatori
        if (nome == null || nome.trim().isEmpty() ||
            cognome == null || cognome.trim().isEmpty() ||
            matricola == null || matricola.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome, cognome e matricola sono obbligatori.");
        }
        
        this.nome = nome;
        this.cognome = cognome;
        this.matricola = matricola;
        
        
        //validazione email (deve finire con @studenti.unisa.it)
        if (email == null || !email.endsWith(EMAIL_SUFFIX)) {
             throw new IllegalArgumentException("L'email deve terminare con " + EMAIL_SUFFIX);
        }
        this.email = email;
    }

    /**
     * @brief Verifica che i dati dell'utente siano validi.
     * @return Vero se i dati sono validi, altrimenti restituisce falso.
     */
    public boolean isValido() {
        return nome != null && !nome.trim().isEmpty() && 
               cognome != null && !cognome.trim().isEmpty() && 
               matricola != null && !matricola.trim().isEmpty() &&
               email != null && email.endsWith(EMAIL_SUFFIX);
    }

    /**
     * @brief Ottiene la matricola dell'utente.
     * @return La matricola dell'utente, altrimenti restituisce il valore null.
     * @see Dati#getId()
     */
    @Override 
    public String getId() {
        return matricola;
    }

 
    /**
     * @brief Restituisce il nome dell'utente.
     * @return Il nome dell'utente, altrimenti restituisce il valore null.
     */
    public String getNome() {
        return nome;
    }

    /** * @brief Restituisce il cognome dell'utente.
     * @return Il cognome dell'utente, altrimenti restituisce il valore null.
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * @brief Restituisce la matricola dell'utente.
     * @return La matricola dell'utente, altrimenti restituisce il valore null.
     */
    public String getMatricola() {
        return matricola;
    }

    /**
     * @brief Restituisce l'email dell'utente.
     * @return L'email dell'utente, altrimenti restituisce il valore null.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @brief Imposta il nome dell'utente.
     * @param nuovoNome Il nuovo nome.
     * @pre nuovoNome non deve assumere il valore null.
     * @post Il nome è uguale a nuovoNome.
     */
    public void setNome(String nuovoNome) {
        this.nome = nuovoNome;
    }

    /**
     * @brief Imposta il cognome dell'utente.
     * @param [in] nuovoCognome Il nuovo cognome.
     * @pre nuovoCognome non deve assumere il valore null.
     * @post Il cognome è uguale a nuovoCognome.
     */
    public void setCognome(String nuovoCognome) {
        this.cognome = nuovoCognome;
    }

    /**
     * @brief Imposta l'indirizzo email dell'utente.
     * @param [in] nuovaEmail La nuova email.
     * @pre nuovaEmail non deve assumere il valore null.
     * @throws IllegalArgumentException Se l'email non rispetta il formato richiesto, viene lanciata un'eccezione.
     * @post L' email è uguale a nuovaEmail.
     */
    public void setEmail(String nuovaEmail) {
        if (nuovaEmail == null || !nuovaEmail.endsWith(EMAIL_SUFFIX)) {
            throw new IllegalArgumentException("L'email deve terminare con " + EMAIL_SUFFIX);
        }
        this.email = nuovaEmail;
    }
    
    /**
     * @brief Imposta la matricola dell'utente.
     * @param [in] nuovaMatricola La nuova matricola.
     * @pre nuovaMatricola non deve assumere il valore null o essere vuota.
     * @throws IllegalArgumentException Se la matricola è vuota o nulla, viene lanciata un'eccezione
     * @post La matricola è uguale a nuovaMatricola.
     */
    public void setMatricola(String nuovaMatricola) {
        if (nuovaMatricola == null || nuovaMatricola.trim().isEmpty()) {
            throw new IllegalArgumentException("La matricola non può essere vuota.");
        }
        this.matricola = nuovaMatricola;
    }


    /**
     * @brief Restituisce una rappresentazione in formato stringa dell'utente.
     * @details Genera una stringa contenente i dati anagrafici dell'utente,
     * quali nome, cognome, matricola ed email.
     * @return Una stringa formattata con i dettagli dell'utente, altrimenti restituisce il valore null.
     * @pre L'oggetto Utente deve essere stato inizializzato.
     * @post I dati interni dell'oggetto non subiscono modifiche.
     * @see Dati#toString()
     */
    @Override
    public String toString() {
        return String.format("Matricola: %s | Nome: %s | Cognome: %s | Email: %s", matricola, nome, cognome, email);
    }
}