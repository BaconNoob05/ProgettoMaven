package it.unisa.diem.ingegneriadelsoftware.controller;
import it.unisa.diem.ingegneriadelsoftware.repository.*;
import it.unisa.diem.ingegneriadelsoftware.service.*;
import it.unisa.diem.ingegneriadelsoftware.model.*;
import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


class PrestitoControllerTest {

    private PrestitoController controller;
    private PrestitoViewStub vistaFittizia;
    private PrestitoService servizioPrestiti;

    private RepositoryStub<Prestito> repositoryPrestiti;
    private LibroService servizioLibri;
    private RepositoryStub<Libro> repositoryLibri;
    
    private Utente utenteAdegutato;
    private Utente utenteImpossibile;
    private Libro libroDisponibile; 
    private Libro libroEsaurito; 

    @BeforeEach
    void setup() {
        repositoryPrestiti = new RepositoryStub<>();
        repositoryLibri = new RepositoryStub<>();
        
        servizioLibri = new LibroService(repositoryLibri);
        servizioPrestiti = new PrestitoService(repositoryPrestiti, servizioLibri);
        
        vistaFittizia = new PrestitoViewStub();
        controller = new PrestitoController(vistaFittizia, servizioPrestiti);
        
        List<String> autoriDeSio = List.of("Claudio De Sio Cesari");
        List<String> autoriDickens = List.of("Charles Dickens");
        
        libroDisponibile = new Libro("Il nuovo Java. Guida definitiva", autoriDeSio, 2023, "978-8868945620", 12);
        libroEsaurito = new Libro("A Christmas Carol", autoriDickens, 1843, "978-0141439247", 0);
        
        utenteAdegutato = new Utente("Lorenzo", "Trovato", "0612709999", "sonoTrovato@uni.com");
        utenteImpossibile = new Utente("alessandro", "picariello", "0612709975", "picapics@uni.com");
        
        repositoryLibri.inserisciOAggiorna(libroDisponibile);
        repositoryLibri.inserisciOAggiorna(libroEsaurito);
    }

    @Test
    void testRegistraPrestito_CasoDiSuccesso() {
        Prestito pRichiesto = new Prestito(utenteAdegutato, libroDisponibile, LocalDate.now().plusDays(30));
        vistaFittizia.setPrestitoNuovo(pRichiesto);

        controller.registraPrestito();

        assertAll("Verifica Registrazione Prestito Riuscita",
            () -> assertEquals(1, repositoryPrestiti.getAll().size()),
            () -> assertEquals(11, libroDisponibile.getCopieDisponibili()),
            () -> assertNull(vistaFittizia.getMessaggioMostrato())
        );
    }

    @Test
    void testRegistraPrestito_FallimentoPerLibroEsaurito() {
        Prestito pRichiesto = new Prestito(utenteAdegutato, libroEsaurito, LocalDate.now().plusDays(30));
        vistaFittizia.setPrestitoNuovo(pRichiesto);

        controller.registraPrestito();
        
        assertAll("Verifica Fallimento Prestito per Libro Esaurito",
            () -> assertEquals(0, repositoryPrestiti.getAll().size()),
            () -> assertNotNull(vistaFittizia.getMessaggioMostrato()),
            () -> assertTrue(vistaFittizia.getMessaggioMostrato().contains("disponibili"))
        );
    }
    
    @Test
    void testRegistraPrestito_FallimentoPerDatiMancantiDaVista() {
        vistaFittizia.setPrestitoNuovo(null); 

        controller.registraPrestito();

        assertAll(
            () -> assertEquals(0, repositoryPrestiti.getAll().size()),
            () -> assertNotNull(vistaFittizia.getMessaggioMostrato()),
            () -> assertTrue(vistaFittizia.getMessaggioMostrato().contains("dati"))
        );
    }

    @Test
    void testRegistraPrestito_FallimentoPerDataDiRitornoScaduta() {
        Prestito pRichiesto = new Prestito(utenteAdegutato, libroDisponibile, LocalDate.now().minusDays(1)); 
        vistaFittizia.setPrestitoNuovo(pRichiesto);

        controller.registraPrestito();

        assertAll(
            () -> assertEquals(0, repositoryPrestiti.getAll().size()),
            () -> assertEquals(12, libroDisponibile.getCopieDisponibili()),
            () -> assertTrue(vistaFittizia.getMessaggioMostrato().contains("data"))
        );
    }

    @Test
    void testRegistraRestituzione_CasoDiSuccesso() {
        Prestito pAttivo = new Prestito(utenteAdegutato, libroDisponibile, LocalDate.now().plusDays(30));
        repositoryPrestiti.salva(pAttivo); 
        libroDisponibile.setCopieDisponibili(11); 

        vistaFittizia.setSelezionato(pAttivo);
        vistaFittizia.setDataRestituzione(LocalDate.now());

        controller.registraRestituzione();

        assertAll(
            () -> assertNotNull(pAttivo.getDataEffettiva()),
            () -> assertEquals(12, libroDisponibile.getCopieDisponibili()),
            () -> assertNull(vistaFittizia.getMessaggioMostrato())
        );
    }
    
    @Test
    void testRegistraRestituzione_FallimentoNessunPrestitoSelezionato() {
        vistaFittizia.setSelezionato(null);
        vistaFittizia.setDataRestituzione(LocalDate.now());

        controller.registraRestituzione();

        assertAll(
            () -> assertNotNull(vistaFittizia.getMessaggioMostrato()),
            () -> assertTrue(vistaFittizia.getMessaggioMostrato().contains("selezionato"))
        );
    }

    @Test
    void testRegistraRestituzione_FallimentoPrestitoGiaConcluso() {
        Prestito pChiuso = new Prestito(utenteImpossibile, libroDisponibile, LocalDate.now().plusDays(30));
        pChiuso.setDataEffettiva(LocalDate.now().minusDays(1));
        repositoryPrestiti.salva(pChiuso); 

        vistaFittizia.setSelezionato(pChiuso);
        vistaFittizia.setDataRestituzione(LocalDate.now());

        controller.registraRestituzione();

        assertAll(
            () -> assertNotNull(vistaFittizia.getMessaggioMostrato()),
            () -> assertTrue(vistaFittizia.getMessaggioMostrato().contains("concluso"))
        );
    }
    
    @Test
    void testRegistraRestituzione_FallimentoDataRestituzionePassata() {
        Prestito pAttivo = new Prestito(utenteAdegutato, libroDisponibile, LocalDate.now().plusDays(30));
        repositoryPrestiti.salva(pAttivo); 
        libroDisponibile.setCopieDisponibili(11);

        vistaFittizia.setSelezionato(pAttivo);
        vistaFittizia.setDataRestituzione(LocalDate.now().minusDays(5));

        controller.registraRestituzione();

        assertAll(
            () -> assertNull(pAttivo.getDataEffettiva()),
            () -> assertEquals(11, libroDisponibile.getCopieDisponibili()),
            () -> assertNotNull(vistaFittizia.getMessaggioMostrato()),
            () -> assertTrue(vistaFittizia.getMessaggioMostrato().contains("data di restituzione"))
        );
    }

    @Test
    void testAggiornaPrestiti_MostraListaNonVuota() {
        Prestito pAttivo = new Prestito(utenteAdegutato, libroDisponibile, LocalDate.now().plusDays(30));
        repositoryPrestiti.salva(pAttivo);

        controller.aggiornaPrestiti();

        assertAll(
            () -> assertEquals(1, vistaFittizia.getListaMostrata().size()),
            () -> assertTrue(vistaFittizia.getListaMostrata().contains(pAttivo))
        );
    }
    
    @Test
    void testAggiornaPrestiti_MostraListaVuota() {
        controller.aggiornaPrestiti();

        assertAll(
            () -> assertNotNull(vistaFittizia.getListaMostrata()),
            () -> assertTrue(vistaFittizia.getListaMostrata().isEmpty())
        );
    }
}