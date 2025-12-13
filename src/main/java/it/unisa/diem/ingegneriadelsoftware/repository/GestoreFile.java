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
     * @param [in] nomeFile Il percorso/nome del file di destinazione.
     * @param [in] dati La lista di oggetti da salvare.
     * @pre La lista passata può essere vuota, ma non null evitando così il NullPointerException.
     * @post Il file viene creato o sovrascritto con i dati della lista.
     */
    @Override
    public void salvaDati(String nomeFile, List<T> dati) {
        try (FileOutputStream streamBase = new FileOutputStream(nomeFile);
             ObjectOutputStream streamOggettiOut = new ObjectOutputStream(streamBase)) {

            streamOggettiOut.writeObject(dati);

        } catch (IOException problemaScrittura) {
            System.err.println("ERRORE di Salvataggio: Impossibile salvare i dati nel file '" + nomeFile + "'.");
            System.err.println("Dettagli Tecnici: Si è verificato un problema di serializzazione/scrittura. Causa: " + problemaScrittura.getLocalizedMessage());
        }
    }

    /**
     * @brief Carica una lista di oggetti da file.
     * @param [in] nomeFile Il percorso/nome del file da leggere.
     * @return La lista di oggetti caricata, altrimenti restituisce una lista vuota.
     */
    @Override
    public List<T> caricaDati(String nomeFile) {
        List<T> listaRisultato = new ArrayList<>();

        try (FileInputStream streamInputFile = new FileInputStream(nomeFile);
             ObjectInputStream streamOggettiIn = new ObjectInputStream(streamInputFile)) {

            Object oggettoGenericoLetto = streamOggettiIn.readObject();

            if (oggettoGenericoLetto instanceof List) {
                listaRisultato = (List<T>) oggettoGenericoLetto;
            } else {
                System.err.println("AVVISO di Lettura: Il file '" + nomeFile + "' non contiene una lista di oggetti valida.");
            }

        } catch (FileNotFoundException assenzaFile) {
            System.out.println("INFORMAZIONE: File di dati non trovato: '" + nomeFile + "'. Verrà utilizzata una lista vuota.");

        } catch (IOException e) {
            System.err.println("ERRORE CRITICO di I/O: Impossibile leggere il contenuto del file '" + nomeFile + "'.");
            System.err.println("Verifica se il file è corrotto o i permessi di accesso. Dettagli: " + e.getMessage());

        } catch (ClassNotFoundException e) {
            System.err.println("ERRORE DI COMPATIBILITÀ: Il file '" + nomeFile + "' contiene oggetti di un tipo sconosciuto o non compatibile.");
            System.err.println("Assicurati che le classi originali siano presenti. Dettagli: " + e.getMessage());
        }

        return listaRisultato;
    }
}
