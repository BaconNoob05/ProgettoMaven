package it.unisa.diem.ingegneriadelsoftware.repository; 
import java.util.*;

// Stub interfaccia gestoreIO
public class GestoreIOStub<T> implements InterfaceGestoreIO<T> {

    // Lista per registrare l'output del Repository
    private List<T> datiSalvatoInFile = new ArrayList<>();
    
    // Lista per fornire l'input al Repository
    private List<T> datiDaCaricare;


    public void setDatiIniziali(List<T> dati) {
        this.datiDaCaricare = dati;
    }

    public List<T> getDatiSalvatoInFile() {
        return datiSalvatoInFile;
    }

    @Override
    public void salvaDati(String nomeFile, List<T> dati) {
        // Registra il tentativo di salvataggio
        this.datiSalvatoInFile = new ArrayList<>(dati);
    }

    @Override
    public List<T> caricaDati(String nomeFile) {
        // Restituisce i dati predefiniti dallo Stub
        return datiDaCaricare != null ? datiDaCaricare : new ArrayList<>();
    }
}