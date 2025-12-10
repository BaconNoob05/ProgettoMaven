package it.unisa.diem.ingegneriadelsoftware.repository; 
import java.util.*;

// Stub interfaccia gestoreIO
class GestoreFileStub<T> implements InterfaceGestoreIO<T> {

    private List<T> memoria = new ArrayList<>();

    @Override
    public void salvaDati(String nomeFile, List<T> dati) {
        memoria = new ArrayList<>(dati);
    }

    @Override
    public List<T> caricaDati(String nomeFile) {
        return new ArrayList<>(memoria);
    }
}
