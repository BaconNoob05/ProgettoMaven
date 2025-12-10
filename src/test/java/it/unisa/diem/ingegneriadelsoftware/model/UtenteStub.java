/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.ingegneriadelsoftware.model;

public class UtenteStub extends Utente {

    public UtenteStub(String nome, String cognome, String matricola, String email) {
        super(nome, cognome, matricola,email);
    }

    @Override
    public String getId() {
        return "UTENTESTUB";
    }
}
