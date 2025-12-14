package it.unisa.diem.ingegneriadelsoftware.repository; 
import java.util.*;

/**
 * @brief Implementazione Stub di InterfaceGestoreIO.
 * @class GestoreFileStub
 */
class GestoreFileStub<T> implements InterfaceGestoreIO<T> {

    /**
     * @brief Una lista utilizzata per simulare il salvataggio dei dati.
     */
    private List<T> memoria = new ArrayList<>();

    /**
     * @brief Simula il salvataggio dei dati.
     */
    @Override
    public void salvaDati(String nomeFile, List<T> dati) {
        memoria = new ArrayList<>(dati);
    }

    /**
     * @brief Simula il caricamento dei dati.
     */
    @Override
    public List<T> caricaDati(String nomeFile) {
        return new ArrayList<>(memoria);
    }
}
