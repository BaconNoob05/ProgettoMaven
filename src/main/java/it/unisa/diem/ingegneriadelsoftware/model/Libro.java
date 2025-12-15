package it.unisa.diem.ingegneriadelsoftware.model;
import java.util.List;
import java.util.ArrayList;
import java.time.Year;

/**
 * @class Libro
 * @brief Rappresenta un libro all'interno del sistema bibliotecario.
 * @see Dati
 */
public class Libro extends Dati {
    
    /** 
     * @brief Titolo del libro. 
     */
    private String titolo;

    /** 
     * @brief Lista degli autori del libro. 
     */
    private List<String> autori;

    /** 
     * @brief Anno di pubblicazione. 
     */
    private int anno;

    /** 
     * @brief Codice ISBN univoco. 
     */
    private String isbn;

    /** 
     * @brief Numero di copie fisiche attualmente disponibili per il prestito. 
     */
    private int copie;

    /**
     * @brief Costruttore della classe Libro.
     * @param [in] titolo Il titolo del libro.
     * @param [in] autori La lista degli autori.
     * @param [in] anno L'anno di pubblicazione.
     * @param [in] isbn Il codice ISBN.
     * @param [in] copie Il numero di copie.
     */
    public Libro(String titolo, List<String> autori, int anno, String isbn, int copie) {
        this.titolo = titolo;
        this.autori = (autori != null) ? autori : new ArrayList<>();
        this.anno = anno;
        this.isbn = isbn;
        this.copie = copie;
    }

    /**
     * @brief Controlla che i dati del libro siano corretti.
     * @return vero se i dati sono validi altrimenti restituisce falso.
     */

    public boolean isValido() {
        int annoCorrente = Year.now().getValue(); 
        
       
        return titolo != null && !titolo.trim().isEmpty() &&
               isbn != null && !isbn.trim().isEmpty() &&
               copie >= 0 && //numero di copie positivo
               autori != null && !autori.isEmpty() && //almeno un autore
               anno >0 &&  //anno positivo
               anno <= annoCorrente; //l'anno non puo essere nel futuro
    }

    /**
     * @brief Decrementa il numero di copie disponibili quando un libro viene prestato.
     * @post Il numero di copie disponibili diminuisce di uno.
     * @throws IllegalStateException Se il numero di copie non è un valore positivo, viene lanciata un'eccezione.
     * @details Viene invocato quando occorre effettuare un prestito.
     */

    public void decrementaCopie() {
        if (copie > 0) {
            copie--;
        } else {
            throw new IllegalStateException("Impossibile prestare: nessuna copia disponibile."); 
        }
    }

    /**
     * @brief Incrementa il numero di copie disponibili quando un libro viene restituito.
     * @post Il numero di copie disponibili aumenta di uno.
     * @details Questo metodo deve essere invocato quando bisogna effettuare una restituzione.
     */
    public void incrementaCopie() {
        copie++;
    }

    /**
     * @brief Ottiene l'ISBN del libro.
     * @return Il codice ISBN del libro altrimenti restituisce il valore null.
     * @see Dati#getId()
     */
    @Override 
    public String getId() {
        return isbn;
    }

    /** 
     * @brief Restituisce il titolo del libro.
     * @return Il titolo del libro altrimenti restituisce il valore null. 
     */
    public String getTitolo() {
        return titolo;
    }
    
    /**
     * @brief Restituisce l'elenco degli autori associati al libro.
     * @return Una lista di stringhe in cui sono presenti i nomi degli autori, altrimenti restituisce una lista vuota.
     * @pre L'oggetto Libro deve essere inizializzato con il costruttore.
     */
    public List<String> getAutori() { 
        return autori;
    }

    /** 
     * @brief Restituisce il codice ISBN.
     * @return Il codice ISBN altrimenti restituisce il valore null. 
     */
    public String getIsbn() {
        return isbn;
    }

    /** 
     * @brief Restituisce l'anno di pubblicazione.
     * @return L'anno di pubblicazione, altrimenti restituisce zero. 
     */
    public int getAnno() {
        return anno;
    }

    /** 
     * @brief Restituisce il numero di copie.
     * @return Il numero di copie attualmente disponibili, altrimenti restituisce zero. 
     */
    public int getCopieDisponibili() {
        return copie;
    }
    
    /** 
     * @brief Restituisce un elenco di autori.
     * @return Una stringa contenente l'elenco degli autori, altrimenti restituisce il valore null. 
     */
    public String getAutoriString() {
        return String.join(", ", autori);
    }

    /** 
     * @brief Imposta il titolo del libro.
     * @param [in] nuovoTitolo il nuovo titolo del libro. 
     * @post Il titolo del libro viene aggiornato.
     */
    public void setTitolo(String nuovoTitolo) {
        this.titolo = nuovoTitolo;
    }

    /** 
     * @brief Imposta la lista degli autori.
     * @param [in] nuovoAutori la nuova lista di autori. 
     * @post La lista degli autori viene aggiornata.
     */
    public void setAutori(List<String> nuovoAutori) {
        this.autori = nuovoAutori;
    }

    /**
     * @brief Imposta l'anno di pubblicazione.
     * @param [in] nuovoAnno il nuovo anno di pubblicazione. 
     * @post L'anno di pubblicazione viene aggiornato.
     */
    public void setAnno(int nuovoAnno) {
        this.anno = nuovoAnno;
    }

    /** 
     * @brief Imposta il numero di copie disponibili.
     * @param [in] nuovoCopie il nuovo numero di copie. 
     * @pre L'argomento nuovoCopie deve essere un numero intero maggiore o uguale a zero.
     * @post Il campo copie assume esattamente il valore di nuovoCopie.
     */
    public void setCopieDisponibili(int nuovoCopie) {
        this.copie = nuovoCopie;
    }

    /**
     * @brief Restituisce una rappresentazione in formato stringa del libro.
     * @details Genera una stringa contenente i dettagli principali del libro:
     * titolo, autori, anno, ISBN e copie disponibili.
     * @return Una stringa formattata con i dati del libro, altrimenti restituisce il valore null.
     * @pre L'oggetto Libro deve essere istanziato correttamente.
     * @post Lo stato dell'oggetto non viene modificato.
     * @see Dati#toString()
     */
    @Override
    public String toString() {
        return String.format("ISBN: %s | Titolo: %s | Autori: %s | Anno: %d | Copie: %d",
                isbn, titolo, getAutoriString(), anno, copie);
    }
    
}