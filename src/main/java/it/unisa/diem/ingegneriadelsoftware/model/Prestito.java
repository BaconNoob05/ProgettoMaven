package it.unisa.diem.ingegneriadelsoftware.model;
import java.time.LocalDate;

/**
 * @class Prestito
 * @brief Rappresenta il prestito di un libro all'interno del sistema bibliotecario.
 * Controlla le date di prestito dei libri per l'effettiva 
 * restituzione oppure il calcolo di eventuali ritardi.
 * @see Dati
 */
public class Prestito extends Dati {
    
    /**
     * @brief Identificativo univoco per la serializzazione.
     */
    private static final long serialVersionUID = 1L;

    /** Riferimento all'utente che ha effettuato il prestito. */
    private Utente utente;
    
    /** Riferimento al libro prestato. */
    private Libro libro;
    
    /** Data entro la quale il libro deve essere restituito. */
    private LocalDate dataPrevista;
    
    /** Data effettiva di restituzione */
    private LocalDate dataEffettiva;

    /**
     * @brief Costruttore per creare un nuovo prestito.
     * @param [in] utente L'utente che effettua il prestito.
     * @param [in] libro Il libro oggetto del prestito.
     * @param [in] dataPrevista La data prevista per la restituzione.
     * @pre L'oggetto Utente e l'oggetto Libro non devono avere un valore nullo.
     * @post Viene creato un oggetto Prestito con dataEffettiva impostata a valore nullo.
     */
    public Prestito(Utente utente, Libro libro, LocalDate dataPrevista) { }

    /**
     * @brief Verifica se il prestito è in ritardo rispetto alla data prevista per la restituzione del libro.
     * @return Vero se il libro non è stato restituito e la data attuale è successiva alla data prevista, altrimenti restituisce falso.
     */
    public boolean isScaduto() { }

   /**
     * @brief Registra che la restituzione del libro deve essere effettuata alla data scelta.
     * @param [in] data La data in cui avviene la restituzione.
     * @post dataEffettiva != null
     */
    public void registraRestituzione(LocalDate data) {}

     /**
     * @brief Ottiene l'identificativo univoco del prestito.
     * @details L'ID è generato combinando gli identificativi di Utente e Libro e la data del prestito
     * per garantire l'univocità all'interno del sistema.
     * @return L'ID del prestito altrimenti restituisce un valore nullo.
     * @see Dati#getId()
     */
    @Override 
    public String getId() { }

    /**
     * @brief Restituisce una rappresentazione in formato stringa del prestito.
     * @details Fornisce una descrizione testuale completa del prestito, includendo
     * informazioni su utente, libro, data prevista e data di restituzione (se presente).
     * @return Una stringa formattata con i dettagli del prestito altrimenti restituisce un valore nullo.
     * @pre L'oggetto deve essere istanziato correttamente.
     * @post Lo stato dell'oggetto non viene modificato.
     * @see Dati#toString()
     */
    @Override
    public String toString() { }

    /** @return Il nome completo o identificativo dell'utente altrimenti restituisce un valore nullo. */
    public String getNomeUtente() { }

    /** @return Il titolo del libro prestato altrimenti restituisce un valore nullo. */
    public String getTitoloLibro() { }

    /** @return La data prevista per la restituzione altrimenti restituisce un valore nullo. */
    public LocalDate getDataPrevista() { }

    /** @return La data effettiva di restituzione altrimenti restituisce un valore nullo. */
    public LocalDate getDataEffettiva() { }

    /** @return L'oggetto Utente associato altrimenti restituisce un valore nullo. */
    public Utente getUtente() { }

    /** @return L'oggetto Libro associato altrimenti restituisce un valore nullo. */
    public Libro getLibro() { }
}