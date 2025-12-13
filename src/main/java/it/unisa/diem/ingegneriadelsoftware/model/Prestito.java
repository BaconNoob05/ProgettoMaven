package it.unisa.diem.ingegneriadelsoftware.model;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    /** 
     * @brief Riferimento all'utente che ha effettuato il prestito. 
    */
    private Utente utente;
    
    /**
    * @brief Riferimento al libro prestato. 
    */
    private Libro libro;
    
    /**
    * @brief Data in cui è stato effettuato il prestito (NUOVO CAMPO). 
    */
    private LocalDate dataPrestito; 
    
    /** 
    * @brief 
    Data entro la quale il libro deve essere restituito. 
    */
    private LocalDate dataPrevista;
    
    /** 
    *@brief Data effettiva di restituzione
    */
    private LocalDate dataEffettiva;

    /**
     * @brief Costruttore per creare un nuovo prestito.
     * @param [in] utente L'utente che effettua il prestito.
     * @param [in] libro Il libro oggetto del prestito.
     * @param [in] dataPrevista La data prevista per la restituzione.
     * @pre L'oggetto Utente e l'oggetto Libro non devono avere un valore nullo.
     * @post Viene creato un oggetto Prestito con dataEffettiva impostata a valore nullo e dataPrestito impostata a LocalDate.now().
     */
    public Prestito(Utente utente, Libro libro, LocalDate dataPrevista) {
        
        if (utente == null) {
            throw new IllegalArgumentException("L'utente non può essere nullo.");
        }
        // Correzione per testCostruttore_LibroNullo
        if (libro == null) {
            throw new IllegalArgumentException("Il libro non può essere nullo.");
        }
        if (dataPrevista == null) {
            throw new IllegalArgumentException("La data prevista di restituzione non può essere nulla.");
        }
    
        this.utente = utente;
        this.libro = libro;
        this.dataPrevista = dataPrevista;
        this.dataEffettiva = null;

        this.dataPrestito = LocalDate.now(); 
    }

    /**
     * @brief Verifica se il prestito è in ritardo rispetto alla data prevista per la restituzione del libro.
     * @return Vero se il libro non è stato restituito e la data attuale è successiva alla data prevista, altrimenti restituisce falso.
     */
    public boolean isScaduto() {
        return dataEffettiva == null && LocalDate.now().isAfter(dataPrevista);
    }

   /**
     * @brief Registra che la restituzione del libro deve essere effettuata alla data scelta.
     * @param [in] data La data in cui avviene la restituzione.
     * @post dataEffettiva != null
     */
    public void registraRestituzione(LocalDate data) {
        if (data == null) {
            throw new IllegalArgumentException("La data di restituzione non può essere nulla.");
        }
        this.dataEffettiva = data;
    }

    /**
     * @brief Ottiene l'identificativo univoco del prestito.
     * @details L'ID è generato combinando gli identificativi di Utente e Libro e la data del prestito
     * per garantire l'univocità all'interno del sistema (Utilizzando dataPrestito).
     * @return L'ID del prestito altrimenti restituisce un valore nullo.
     * @see Dati#getId()
     */
    @Override 
    public String getId() {

        
        if (utente == null || libro == null || dataPrestito == null) { 
            return null; 
        }
        return utente.getId() + "_" + libro.getId() + "_" + dataPrestito.toString();
    }

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
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String stato = (dataEffettiva == null) ? "ATTIVO (Prevista: " + dataPrevista.format(formatter) + ")" : "CHIUSO (Restituito: " + dataEffettiva.format(formatter) + ")";
        
        return String.format("[%s] Prestito: %s | Utente: %s | Libro: %s | Stato: %s",getId(), dataPrestito.format(formatter), getNomeUtente(), getTitoloLibro(), stato);
    }

   /**
     * @brief Restituisce la data in cui il prestito è stato effettuato.
     * @return La data del prestito altrimenti restituisce un valore nullo.
     */
    public LocalDate getDataPrestito() {
        return dataPrestito;
    }


    /**
     * @brief Restituisce il titolo del libro prestato. 
     * @returnIl nome completo dell'utente altrimenti restituisce un valore nullo.
     */
    public String getNomeUtente() {
        return (utente != null) ? utente.getCognome() + " " + utente.getNome() : "N/D";
    }

    /** 
     *@brief Restituisce il titolo del libro prestato
     * @return Il titolo del libro prestato altrimenti restituisce un valore nullo. 
     */
    public String getTitoloLibro() {
        return (libro != null) ? libro.getTitolo() : "N/D";
    }

    /** 
     * @brief Restituisce la data prevista per la restituzione del libro
     * @return La data prevista per la restituzione altrimenti restituisce un valore nullo. 
     */
    public LocalDate getDataPrevista() {
        return dataPrevista;
    }

    /** 
     * @brief Restituisce la data effettiva di restituzione del libro
     * @return La data effettiva di restituzione altrimenti restituisce un valore nullo. */
    public LocalDate getDataEffettiva() {
        return dataEffettiva;
    }

    /**
     * @brief Restituisce l'oggetto dell' utente associato 
     * @return L'oggetto Utente associato altrimenti restituisce un valore nullo. 
     */
    public Utente getUtente() {
        return utente;
    }

    /**
     * @brief Restituisce l'oggeto del libro associato.
     * @return L'oggetto Libro associato altrimenti restituisce un valore nullo. 
     */
    public Libro getLibro() {
        return libro;
    }
    /**
     * @brief Imposta la data di restituzione del libro.
     * @param [in] dataEffettiva La data in cui il libro è stato restituito.
     * @pre La data fornita non deve essere nulla e dovrebbe essere successiva alla data del prestito.
     * @post Il campo dataEffettiva assume il valore della  data specificata.
     */
     public void setDataEffettiva(LocalDate dataEffettiva) { 
        this.dataEffettiva=dataEffettiva;
     }
}