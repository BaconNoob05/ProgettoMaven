package it.unisa.diem.ingegneriadelsoftware.repository;
import java.util.List;

/**
 * @interface InterfaceGestoreIO
 * @brief Interfaccia per la gestione dell'Input/Output dei dati.
 * @tparam T Il tipo di dato da gestire.
 */
public interface InterfaceGestoreIO<T> {

    /**
     * @brief Salva una lista di oggetti sul file.
     * @param [in] nomeFile Il percorso o nome del file di destinazione.
     * @param [in] dati La lista di oggetti generici T da salvare.
     * @pre La lista dati non deve essere null.
     * @post I dati sono stati salvati correttamente.
     */
    void salvaDati(String nomeFile, List<T> dati);

    /**
     * @brief Carica una lista di oggetti dal file.
     * @param [in] nomeFile Il percorso o nome del file da leggere.
     * @return Una lista contenente gli oggetti caricati, altrimenti restituisce una lista vuota.
     * @pre Il parametro 'nomeFile' deve essere una stringa valida.
     * @post Viene restituita un'istanza di List.
     */
    List<T> caricaDati(String nomeFile);

}
