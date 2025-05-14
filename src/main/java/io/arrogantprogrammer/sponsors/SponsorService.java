package io.arrogantprogrammer.sponsors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class SponsorService {

    @Inject
    SponsorRepository sponsorRepository;

    @Transactional
    public Sponsor createSponsor(Sponsor sponsor) {
        sponsorRepository.persist(sponsor);
        return sponsor;
    }

    public List<Sponsor> getAllSponsors() {
        return sponsorRepository.listAll();
    }

    public Sponsor getSponsorById(Long id) {
        return sponsorRepository.findByIdOptional(id)
                .orElseThrow(() -> new WebApplicationException("Sponsor not found", Response.Status.NOT_FOUND));
    }

    @Transactional
    public Sponsor updateSponsor(Long id, Sponsor sponsor) {
        Sponsor existingSponsor = getSponsorById(id);
        existingSponsor.name = sponsor.name;
        existingSponsor.tier = sponsor.tier;
        existingSponsor.logo = sponsor.logo;
        existingSponsor.website = sponsor.website;
        existingSponsor.description = sponsor.description;
        return existingSponsor;
    }

    @Transactional
    public void deleteSponsor(Long id) {
        Sponsor sponsor = getSponsorById(id);
        sponsorRepository.delete(sponsor);
    }
} 