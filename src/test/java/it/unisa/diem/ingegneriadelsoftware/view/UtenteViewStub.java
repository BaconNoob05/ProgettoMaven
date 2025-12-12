/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.ingegneriadelsoftware.view;

import it.unisa.diem.ingegneriadelsoftware.model.Utente;
import java.util.List;

public class UtenteViewStub extends UtenteView {
    private Utente utenteNuovo;
    private Utente utenteModificato;
    private Utente elementoSelezionato;
    private String testoCerca = "";
    
    public List<Utente> listaRicevuta;
        
    public String ultimoMessaggio = null;

    public UtenteViewStub() {
        super(); 
    }

    @Override
    public Utente getUtenteNuovo() {
        return utenteNuovo;
    }

    @Override
    public Utente getUtenteModificato() {
        return utenteModificato;
    }

    @Override
    public Utente getElementoSelezionato() {
        return elementoSelezionato;
    }

    @Override
    public void mostraMessaggio(String messaggio) {
        this.ultimoMessaggio = messaggio;
    }

    @Override
    public void mostraLista(List<Utente> lista) {
        this.listaRicevuta=lista;
    }
        
    //Metodi utili per i test
    public void setInputNuovo(Utente u) { this.utenteNuovo = u; }
    public void setInputModificato(Utente u) { this.utenteModificato = u; }
    public void setSelezionato(Utente u) { this.elementoSelezionato = u; }
    public void setTestoCerca(String s) { this.testoCerca = s; }
}
