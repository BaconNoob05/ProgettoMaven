package it.unisa.diem.ingegneriadelsoftware;
import java.io.Serializable;

/**
 * @file Dati.java
 * @brief Scheletro base per i modelli di dominio.
 */
public abstract class Dati implements InterfaceID, Serializable {
    /**
     * @brief Identificativo univoco per la serializzazione.
     * @details Garantisce la compatibilità tra l'oggetto serializzato e la classe 
     * caricata durante la fase di deserializzazione. Se questo ID non corrisponde 
     * a quello dell'oggetto salvato, viene lanciata una InvalidClassException.
     */
    private static final long serialVersionUID = 1L;
     /**
     * @brief Restituisce l'identificativo univoco dell'oggetto.
     * Metodo astratto che deve essere utilizzato da tutte le sottoclassi
     * per garantire che ogni entità abbia una chiave univoca.
     * @return L'ID univoco dell'oggetto altrimenti un valore nullo.
     */
    @Override
    public abstract String getId();
