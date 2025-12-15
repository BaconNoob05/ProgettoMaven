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
     * @brief Riferimento all'utente che ha effettuato il prestito. 
     */
    private Utente utente;
    
    /** 
     * @brief Riferimento al libro prestato. 
     */
    private Libro libro;
    
    /** 
     * @brief Data in cui è stato effettuato il prestito. 
     */
    private LocalDate dataPrestito; 
    
    /** 
     * @brief Data entro la quale il libro deve essere restituito. 
     */
    private LocalDate dataPrevista;
    
    /** 
     * @brief Data effettiva di restituzione.
     */
    private LocalDate dataEffettiva;
    

    /**
     * @brief Costruttore per creare un nuovo prestito con data Prestito impostata a oggi.
     * @param [in] utente L'utente che effettua il prestito.
     * @param [in] libro Il libro oggetto del prestito.
     * @param [in] dataPrevista La data prevista per la restituzione.
     * @pre L'oggetto Utente e l'oggetto Libro non devono assumere il valore null.
     * @post Viene creato un oggetto Prestito con dataEffettiva impostata al valore null e dataPrestito impostata a LocalDate.now().
     */
    public Prestito(Utente utente, Libro libro, LocalDate dataPrevista) {
        if (utente == null) {
            throw new IllegalArgumentException("L'oggetto Utente non può essere nullo.");
        }
        if (libro == null) {
            throw new IllegalArgumentException("L'oggetto Libro non può essere nullo.");
        }
        
        this.utente = utente;
        this.libro = libro;
        this.dataPrevista = dataPrevista;
        this.dataEffettiva = null;
        this.dataPrestito = LocalDate.now();
    }


    /**
     * @brief Costruttore completo per creare un nuovo prestito, permettendo di specificare la data di inizio.
     * @param [in] utente L'utente che effettua il prestito.
     * @param [in] libro Il libro oggetto del prestito.
     * @param [in] dataPrevista La data prevista per la restituzione.
     * @param [in] dataPrestito La data in cui il prestito è stato effettivamente registrato.
     * @pre L'oggetto Utente, l'oggetto Libro e le date non devono assumere il valore null.
     * @post Viene creato un oggetto Prestito con dataEffettiva impostata al valore null.
     */
    public Prestito(Utente utente, Libro libro, LocalDate dataPrevista, LocalDate dataPrestito) {
        if (utente == null) {
            throw new IllegalArgumentException("L'oggetto Utente non può essere nullo.");
        }
        if (libro == null) {
            throw new IllegalArgumentException("L'oggetto Libro non può essere nullo.");
        }
        if (dataPrestito == null) {
            throw new IllegalArgumentException("La data di prestito non può essere nulla.");
        }
        
        this.utente = utente;
        this.libro = libro;
        this.dataPrevista = dataPrevista;
        this.dataEffettiva = null;
        this.dataPrestito = dataPrestito; 
    }

    /**
     * @brief Verifica se il prestito è in ritardo rispetto alla data prevista per la restituzione del libro.
     * @return Vero se il libro non è stato restituito e la data attuale è successiva alla data prevista, altrimenti restituisce falso.
     */
    public boolean isScaduto() {
        // I metodi isAfter e isBefore confrontano la data su cui viene chiamata il metodo con la data passata come parametro.
        return dataEffettiva == null && LocalDate.now().isAfter(dataPrevista);
    }

    /**
     * @brief Registra che la restituzione del libro deve essere effettuata entro la data indicata.
     * @param [in] data La data in cui avviene la restituzione.
     * @throws IllegalArgumentException Se la data di registrazione del prestito inserita è null, viene lanciata un'eccezione.
     * @post Il campo dataEffettiva viene aggiornato.
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
     * per garantire l'univocità all'interno del sistema.
     * @return L'ID del prestito, altrimenti restituisce il valore null se i dati sono incompleti.
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
     * informazioni su utente, libro, data prevista e data di restituzione.
     * @return Una stringa formattata con i dettagli del prestito altrimenti restituisce il valore null.
     * @pre L'oggetto deve essere istanziato correttamente.
     * @post Lo stato dell'oggetto non viene modificato.
     * @see Dati#toString()
     */
    @Override
    public String toString() {
        DateTimeFormatter formattatore = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String stato = (dataEffettiva == null) ? "ATTIVO (Prevista: " + dataPrevista.format(formattatore) + ")" : "CHIUSO (Restituito: " + dataEffettiva.format(formattatore) + ")";
        
        return String.format("[%s] Prestito: %s | Utente: %s | Libro: %s | Stato: %s",getId(), dataPrestito.format(formattatore), getNomeUtente(), getTitoloLibro(), stato);
    }

    /**
     * @brief Restituisce la data in cui il prestito è stato effettuato.
     * @return La data del prestito, altrimenti restituisce il valore null.
     */
    public LocalDate getDataPrestito() {
        return dataPrestito;
    }


    /**
     * @brief Restituisce il nome completo dell'utente che ha effettuato il prestito. 
     * @return Il nome completo dell'utente, altrimenti restituisce la stringa "N/D".
     */
    public String getNomeUtente() {
        return (utente != null) ? utente.getCognome() + " " + utente.getNome() : "N/D";
    }

    /** * @brief Restituisce il titolo del libro prestato.
     * @return Il titolo del libro prestato, altrimenti restituisce la stringa "N/D". 
     */
    public String getTitoloLibro() {
        return (libro != null) ? libro.getTitolo() : "N/D";
    }

    /** * @brief Restituisce la data prevista per la restituzione del libro.
     * @return La data prevista per la restituzione, altrimenti restituisce il valore null. 
     */
    public LocalDate getDataPrevista() {
        return dataPrevista;
    }

    /** * @brief Restituisce la data effettiva di restituzione del libro.
     * @return La data effettiva di restituzione, altrimenti restituisce il valore null. 
     */
    public LocalDate getDataEffettiva() {
        return dataEffettiva;
    }

    /**
     * @brief Restituisce l'oggetto dell'utente associato. 
     * @return L'oggetto Utente associato, altrimenti restituisce il valore null. 
     */
    public Utente getUtente() {
        return utente;
    }

    /**
     * @brief Restituisce l'oggetto del libro associato.
     * @return L'oggetto Libro associato, altrimenti restituisce il valore null. 
     */
    public Libro getLibro() {
        return libro;
    }
    
    /**
     * @brief Imposta la data di restituzione del libro.
     * @param [in] dataEffettiva La data in cui il libro è stato restituito.
     * @pre La data fornita non deve assumere il valore null e deve essere successiva alla data del prestito.
     * @post Il campo dataEffettiva assume il valore della data specificata.
     */
    public void setDataEffettiva(LocalDate dataEffettiva) { 
        this.dataEffettiva=dataEffettiva;
    }
}