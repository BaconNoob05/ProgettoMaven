package it.unisa.diem.ingegneriadelsoftware.service;

import it.unisa.diem.ingegneriadelsoftware.model.Prestito;
import it.unisa.diem.ingegneriadelsoftware.model.Utente; 
import it.unisa.diem.ingegneriadelsoftware.model.Libro;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class PrestitoServiceStub extends PrestitoService {
        public List<Prestito> list = new ArrayList<>();
        public boolean registraPrestitoChiamato = false;
        public boolean registraRestituzioneChiamato = false;
        
        public PrestitoServiceStub() { 
            super(null, null); 
        }

        @Override
        public void registraPrestito(Utente utente, Libro libro, LocalDate dataPrevista) {
            this.registraPrestitoChiamato = true;
            Prestito nuovo = new Prestito(utente, libro, dataPrevista);
            list.add(nuovo);
        }

        @Override
        public void registraRestituzione(Prestito prestito, LocalDate dataEffettiva) {
            this.registraRestituzioneChiamato = true;

            for (Prestito p : list) {
                //Impiego del metodo getId() della classe Prestito
                if (p.getId() != null && p.getId().equals(prestito.getId())) {
                    p.registraRestituzione(dataEffettiva);
                    break;
                }
            }
        }

        @Override
        public List<Prestito> listaPrestitiAttivi() {
            List<Prestito> attivi = new ArrayList<>();
            for (Prestito p : list) {
                if (p.getDataEffettiva() == null) 
                    attivi.add(p);
            }
            return attivi;
        }
        
        @Override
        public List<Prestito> getAll() {
            return new ArrayList<>(list);
        }
    }
