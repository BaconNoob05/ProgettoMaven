package it.unisa.diem.ingegneriadelsoftware.model;
import java.util.List;
import java.util.ArrayList;

/**
 * @class Libro
 * @brief Rappresenta un libro all'interno del sistema bibliotecario.
 * @see Dati
 */
public class Libro extends Dati {
    /**
     * @brief Identificativo univoco per la serializzazione.
     * @details Garantisce la compatibilità tra l'oggetto serializzato e la classe 
     * caricata durante la fase di deserializzazione. Se questo ID non corrisponde 
     * a quello dell'oggetto salvato, viene lanciata una InvalidClassException.
     */
    private static final long serialVersionUID = 1L;

    /** * @brief Titolo del libro. 
     */
    private String titolo;

    /** * @brief Lista degli autori del libro. 
     */
    private List<String> autori;

    /** * @brief Anno di pubblicazione. 
     */
    private int anno;

    /** * @brief Codice ISBN univoco. 
     */
    private String isbn;

    /** * @brief Numero di copie fisiche attualmente disponibili per il prestito. 
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
        return titolo != null && !titolo.trim().isEmpty() &&
               isbn != null && !isbn.trim().isEmpty() &&
               copie >= 0;
    }

    /**
     * @brief Decrementa il numero di copie disponibili quando un libro viene prestato.
     * @post Il numero di copie disponibili diminuisce di uno.
     * @details Questo metodo deve essere invocato quando bisogna effettuare l'azione del prestito.
     */
    public void decrementaCopie() {
        if (copie > 0) {
            copie--;
        }
    }

    /**
     * @brief Incrementa il numero di copie disponibili quando un libro viene restituito.
     * @pre Il libro deve avere almeno una copia disponibile.
     * @post Il numero di copie disponibili aumenta di uno.
     * @details Questo metodo deve essere invocato quando bisogna aggiungere una copia di un libro.
     */
    public void incrementaCopie() {
        copie++;
    }

    /**
     * @brief Ottiene l'ISBN del libro.
     * @return Il codice ISBN del libro altrimenti restituisce un valore nullo.
     * @see Dati#getId()
     */
    @Override 
    public String getId() {
        return isbn;
    }

    /** * @brief Restituisce il titolo del libro.
     * @return Il titolo del libro altrimenti restituisce un valore nullo. 
     */
    public String getTitolo() {
        return titolo;
    }

    /** * @brief Restituisce il codice ISBN.
     * @return Il codice ISBN altrimenti restituisce un valore nullo. 
     */
    public String getIsbn() {
        return isbn;
    }

    /** * @brief Restituisce l'anno di pubblicazione.
     * @return L'anno di pubblicazione altrimenti restituisce zero. 
     */
    public int getAnno() {
        return anno;
    }

    /** * @brief Restituisce il numero di copie.
     * @return Il numero di copie attualmente disponibili altrimenti restituisce zero. 
     */
    public int getCopieDisponibili() {
        return copie;
    }
    
    /** * @brief Restituisce un elenco di autori.
     * @return Gli autori in un'unica stringa altrimenti restituisce un valore nullo. 
     */
    public String getAutoriString() {
        return String.join(", ", autori);
    }

    /** * @brief Imposta il titolo del libro.
     * @param [in] t il nuovo titolo del libro. 
     */
    public void setTitolo(String t) {
        this.titolo = t;
    }

    /** * @brief Imposta la lista degli autori.
     * @param [in] a la nuova lista di autori. 
     */
    public void setAutori(List<String> a) {
        this.autori = a;
    }

    /** * @brief Imposta l'anno di pubblicazione.
     * @param [in] a il nuovo anno di pubblicazione. 
     */
    public void setAnno(int a) {
        this.anno = a;
    }

    /** * @brief Imposta il numero di copie disponibili.
     * @param [in] c il nuovo numero di copie. 
     * @pre L'argomento c deve essere un numero intero maggiore o uguale a zero.
     * @post il campo copieDisponibili assume esattamente il valore di c.
     */
    public void setCopieDisponibili(int c) {
        this.copie = c;
    }

    /**
     * @brief Restituisce una rappresentazione in formato stringa del libro.
     * @details Genera una stringa contenente i dettagli principali del libro:
     * titolo, autori, anno, ISBN e copie disponibili.
     * @return Una stringa formattata con i dati del libro altrimenti restituisce un valore nullo.
     * @pre L'oggetto Libro deve essere istanziato correttamente.
     * @post Lo stato dell'oggetto non viene modificato.
     * @see Dati#toString()
     */
    @Override
    public String toString() {
        return String.format("ISBN: %s | Titolo: %s | Autori: %s | Copie: %d",
                isbn, titolo, getAutoriString(), copie);
    }
    /**
     * @brief Restituisce l'elenco degli autori associati al libro.
     * @return Una lista di stringhe dove ci sono i nomi degli autori, altrimenti restituisce una lista vuota.
     * @pre L'oggetto Libro deve essere inizializzato con il costruttore.
     */
    public List<String> getAutori() { 
        return autori;
    }
}
