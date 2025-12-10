package it.unisa.diem.ingegneriadelsoftware.service;
import it.unisa.diem.ingegneriadelsoftware.model.*;
import it.unisa.diem.ingegneriadelsoftware.repository.InterfaceRepository;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;




public class PrestitoServiceTest {
        
        private PrestitoService prestitoService;
        private PrestitoRepositoryStub stubRepositoryPrestito;
        private LibroServiceStub stubLibroService;
        private UtenteStub utenteCliente;
        private UtenteStub utenteStaff;
        private LibroStub libroDisponibile;
        private LibroStub libroEsaurito;
        private LocalDate dataCorrente;
        
        
        private List<String> autoriEsempio;

        @BeforeEach
        void setup() {
            
            autoriEsempio = Arrays.asList("I. Sommerville", "Stephen King");
        
            utenteCliente = new UtenteStub("Lorenzo", "trovato", "0612708922", "l.trovato1@studenti.unisa.it");
            utenteStaff = new UtenteStub("Lorenzo", "trovato", "0612708922", "l.trovato1@studenti.unisa.it");
            libroDisponibile = new LibroStub("Ingegneria del Software",autoriEsempio, 1951,"978-88-8080-123-4", 5);
            libroEsaurito = new LibroStub("Ingegneria del Software",autoriEsempio, 1951,"978-88-8080-123-4", 5);
            
            
            
            
            stubRepositoryPrestito = new PrestitoRepositoryStub<>(Prestito.class);
            PrestitoRepositoryStub<Libro> libroRepoStub = new PrestitoRepositoryStub<>(Libro.class); 
            stubLibroService = new LibroServiceStub(libroRepoStub); 
            prestitoService = new PrestitoService(stubRepositoryPrestito, stubLibroService);


            dataCorrente = LocalDate.now();
            
            stubRepositoryPrestito.salva(new PrestitoStub(utenteCliente, libroDisponibile, dataCorrente.plusDays(7), null));
            stubRepositoryPrestito.salva(new PrestitoStub(utenteStaff, libroDisponibile, dataCorrente.plusDays(10), null));
            stubRepositoryPrestito.salva(new PrestitoStub(utenteCliente, libroEsaurito, dataCorrente.plusDays(3), dataCorrente.minusDays(1)));
        }

        @Test
        void registraPrestito_decrementaCopieELoSalva() {
            LibroStub nuovoLibro = new LibroStub("Ingegneria del Software",autoriEsempio, 1951,"978-88-8080-123-4", 5);
            int copieIniziali = nuovoLibro.getCopieDisponibili();
            
            prestitoService.registraPrestito(utenteCliente, nuovoLibro, dataCorrente.plusDays(14));
            
            List<Prestito> prestiti = stubRepositoryPrestito.findAll();
            assertEquals(4, prestiti.size());
            assertEquals(copieIniziali - 1, nuovoLibro.getCopieDisponibili()); 
        }

        @Test
        void registraPrestito_fallisceSeLibroNonDisponibile() {
            int copiePrecedenti = libroEsaurito.getCopieDisponibili();
            
            assertThrows(RuntimeException.class, () -> { 
                prestitoService.registraPrestito(utenteCliente, libroEsaurito, dataCorrente.plusDays(14));
            });
            
            assertEquals(copiePrecedenti, libroEsaurito.getCopieDisponibili());
            assertEquals(3, stubRepositoryPrestito.findAll().size());
        }

        @Test
        void registraRestituzione_incrementaCopieEChiudePrestito() {
            Prestito prestitoAttivo = stubRepositoryPrestito.cercaGenerico("attivi").get(0);
            Libro libroRilevato = prestitoAttivo.getLibro(); 
            int copiePrecedenti = ((LibroStub) libroRilevato).getCopieDisponibili();
            
            prestitoService.registraRestituzione(prestitoAttivo, dataCorrente);
            
            assertEquals(dataCorrente, prestitoAttivo.getDataEffettiva());
            assertEquals(copiePrecedenti + 1, ((LibroStub) libroRilevato).getCopieDisponibili());
        }

        @Test
        void registraRestituzione_fallisceSePrestitoGiaChiuso() {
            Prestito prestitoChiuso = stubRepositoryPrestito.findAll().get(2);
            LocalDate dataEffettivaIniziale = prestitoChiuso.getDataEffettiva();
            Libro libroRilevato = prestitoChiuso.getLibro();
            int copiePrecedenti = ((LibroStub) libroRilevato).getCopieDisponibili();

            assertThrows(RuntimeException.class, () -> { 
                prestitoService.registraRestituzione(prestitoChiuso, dataCorrente.plusDays(1));
            });
            
            assertEquals(dataEffettivaIniziale, prestitoChiuso.getDataEffettiva());
            assertEquals(copiePrecedenti, ((LibroStub) libroRilevato).getCopieDisponibili());
        }

        @Test
        void listaPrestitiAttivi_restituisceSoloQuelliAperti() {
            List<Prestito> prestitiAperti = prestitoService.listaPrestitiAttivi();
            
            assertNotNull(prestitiAperti);
            assertEquals(2, prestitiAperti.size());
            assertTrue(prestitiAperti.stream().allMatch(p -> p.getDataEffettiva() == null));
        }

        @Test
        void listaPrestitiAttivi_restituisceListaVuotaSeNonCiSonoAttivi() {
            PrestitoRepositoryStub<Prestito> repoVuoto = new PrestitoRepositoryStub<>(Prestito.class);
            PrestitoService serviceVuoto = new PrestitoService(repoVuoto, stubLibroService);
            
            List<Prestito> prestitiAperti = serviceVuoto.listaPrestitiAttivi();
            
            assertNotNull(prestitiAperti);
            assertTrue(prestitiAperti.isEmpty());
        }
    }
