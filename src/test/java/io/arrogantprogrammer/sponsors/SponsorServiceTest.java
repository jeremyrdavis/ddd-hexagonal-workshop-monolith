package io.arrogantprogrammer.sponsors;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.WebApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
public class SponsorServiceTest {

    @InjectMocks
    SponsorService sponsorService;

    @Mock
    SponsorRepository sponsorRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSponsor() {
        Sponsor sponsor = new Sponsor();
        sponsor.name = "Test Sponsor";
        sponsor.tier = Sponsor.SponsorTier.PLATINUM;
        
        doAnswer(invocation -> {
            Sponsor s = invocation.getArgument(0);
            return s;
        }).when(sponsorRepository).persist(any(Sponsor.class));
        
        Sponsor created = sponsorService.createSponsor(sponsor);
        
        assertNotNull(created);
        assertEquals("Test Sponsor", created.name);
        assertEquals(Sponsor.SponsorTier.PLATINUM, created.tier);
        verify(sponsorRepository).persist(sponsor);
    }

    @Test
    void testGetAllSponsors() {
        Sponsor sponsor1 = new Sponsor();
        sponsor1.name = "Sponsor 1";
        Sponsor sponsor2 = new Sponsor();
        sponsor2.name = "Sponsor 2";
        List<Sponsor> sponsors = Arrays.asList(sponsor1, sponsor2);
        
        when(sponsorRepository.listAll()).thenReturn(sponsors);
        
        List<Sponsor> result = sponsorService.getAllSponsors();
        
        assertEquals(2, result.size());
        verify(sponsorRepository).listAll();
    }

    @Test
    void testGetSponsorById() {
        Sponsor sponsor = new Sponsor();
        sponsor.id = 1L;
        sponsor.name = "Test Sponsor";
        
        when(sponsorRepository.findByIdOptional(1L)).thenReturn(Optional.of(sponsor));
        
        Sponsor result = sponsorService.getSponsorById(1L);
        
        assertNotNull(result);
        assertEquals(1L, result.id);
        assertEquals("Test Sponsor", result.name);
    }

    @Test
    void testGetSponsorByIdNotFound() {
        when(sponsorRepository.findByIdOptional(999L)).thenReturn(Optional.empty());
        
        assertThrows(WebApplicationException.class, () -> {
            sponsorService.getSponsorById(999L);
        });
    }

    @Test
    void testUpdateSponsor() {
        Sponsor existingSponsor = new Sponsor();
        existingSponsor.id = 1L;
        existingSponsor.name = "Old Name";
        existingSponsor.tier = Sponsor.SponsorTier.GOLD;
        
        Sponsor updatedSponsor = new Sponsor();
        updatedSponsor.name = "New Name";
        updatedSponsor.tier = Sponsor.SponsorTier.PLATINUM;
        
        when(sponsorRepository.findByIdOptional(1L)).thenReturn(Optional.of(existingSponsor));
        
        Sponsor result = sponsorService.updateSponsor(1L, updatedSponsor);
        
        assertEquals("New Name", result.name);
        assertEquals(Sponsor.SponsorTier.PLATINUM, result.tier);
    }

    @Test
    void testDeleteSponsor() {
        Sponsor sponsor = new Sponsor();
        sponsor.id = 1L;
        
        when(sponsorRepository.findByIdOptional(1L)).thenReturn(Optional.of(sponsor));
        doNothing().when(sponsorRepository).delete(sponsor);
        
        sponsorService.deleteSponsor(1L);
        
        verify(sponsorRepository).delete(sponsor);
    }
} 