package it.unisa.diem.ingegneriadelsoftware.repository;

import java.util.List;
import java.io.*;
import java.util.ArrayList;

/**
 * @class GestoreFile
 * @brief Classe per la gestione dei dati su file.
 * Si occupa di serializzare e deserializzare liste di oggetti.
 * @tparam T Tipo di dato da gestire.
 * @see InterfaceGestoreIO
 */
public class GestoreFile<T> implements InterfaceGestoreIO<T> {

    /**
     * @brief Salva una lista di oggetti su file.
     * @param [in] nomeFile Il nome del file di destinazione.
     * @param [in] dati La lista di oggetti da salvare.
     * @pre La lista passata può essere vuota ma non null, evitando così il NullPointerException.
     * @post Il file viene creato o sovrascritto con i dati della lista.
     */
    @Override
    public void salvaDati(String nomeFile, List<T> dati) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeFile))) {

            oos.writeObject(dati);

        } catch (IOException ex) {
            System.err.println("Impossibile salvare i dati nel file '" + nomeFile + "'.");
            System.err.println("Si è verificato un problema di serializzazione/scrittura. Dettagli: " + ex.getMessage());
        }
    }

    /**
     * @brief Carica una lista di oggetti da file.
     * @param [in] nomeFile Il nome del file da leggere.
     * @return La lista di oggetti caricata, altrimenti restituisce una lista vuota.
     */
    @Override
    public List<T> caricaDati(String nomeFile) {
        List<T> risultato = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeFile))) {

            Object oggettoLetto = ois.readObject();

            if (oggettoLetto instanceof List) {
                risultato = (List<T>) oggettoLetto;
            } else {
                System.err.println("Il file '" + nomeFile + "' non contiene una lista di oggetti valida.");
            }

        } catch (FileNotFoundException ex) {
            System.out.println("File di dati non trovato: '" + nomeFile + "'. Verrà utilizzata una lista vuota.");

        } catch (IOException ex) {
            System.err.println("Impossibile leggere il contenuto del file '" + nomeFile + "'.\nVerifica se il file è corrotto o i permessi di accesso. Dettagli: " + ex.getMessage());

        } catch (ClassNotFoundException ex) {
            System.err.println("Il file '" + nomeFile + "' contiene oggetti di un tipo sconosciuto o non compatibile.");
            System.err.println("Assicurati che le classi originali siano presenti. Dettagli: " + ex.getMessage());
        }
        
        return risultato;
    }
}