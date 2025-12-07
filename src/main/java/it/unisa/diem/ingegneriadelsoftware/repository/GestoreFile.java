package com.tuoprogetto.biblioteca.repository;
import java.util.List;
import java.io.*;

/**
 * @class GestoreFile
 * @brief Classe per la gestione dei dati su file.
 * Si occupa di serializzare e deserializzare liste di oggetti.
 * @tparam T Tipo di dato da gestire.
 */
public class GestoreFile<T> implements InterfaceGestoreIO<T> {
    
    /**
     * @brief Salva una lista di oggetti su file.
     * @param [in] nomeFile Il percorso/nome del file di destinazione.
     * @param [in] dati La lista di oggetti da salvare.
     * @pre La lista passata può essere vuota, ma non null evitando così il NullPointerException.
     * @post Il file viene creato o sovrascritto con i dati della lista.
     */
    @Override
    public void salvaDati(String nomeFile, List<T> dati) { }

    /**
     * @brief Carica una lista di oggetti da file.
     * @param [in] nomeFile Il percorso/nome del file da leggere.
     * @return La lista di oggetti caricata, altrimenti restituisce una lista vuota.
     */
    @Override
    public List<T> caricaDati(String nomeFile) { return null; }
}
