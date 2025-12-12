package it.unisa.diem.ingegneriadelsoftware.view;

import it.unisa.diem.ingegneriadelsoftware.model.Libro;
import java.util.List;

public class LibroViewStub extends LibroView {
    private Libro libroNuovo;
    private Libro libroModificato;
    private Libro elementoSelezionato;
    private String testoCerca="";

    public List<Libro> listaRicevuta;
    public String ultimoMessaggio = null;

    public LibroViewStub() {
        super();
    }
    
    @Override
    public Libro getLibroNuovo() {
        return libroNuovo;
    }

    @Override 
    public Libro getLibroModificato() {
        return libroModificato;
    }

    @Override
    public Libro getElementoSelezionato() {
        return elementoSelezionato;
    }

    public String getTestoCerca() {
        return testoCerca;
    }

    @Override 
    public void mostraMessaggio(String messaggio) {
        this.ultimoMessaggio = messaggio;
    }

    @Override
    public void mostraLista(List<Libro> lista) {
        this.listaRicevuta = lista;
    }

    public void setInputNuovo (Libro l) { this.libroNuovo = l; }
    public void setInputModificato (Libro l) { this.libroModificato = l; }
    public void setSelezionato (Libro l) { this.elementoSelezionato = l; }
    public void setTestoCerca (String s) { this.testoCerca = s; }
    
}


