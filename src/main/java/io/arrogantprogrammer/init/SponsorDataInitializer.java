package io.arrogantprogrammer.init;

import io.arrogantprogrammer.sponsors.Sponsor;
import io.arrogantprogrammer.sponsors.SponsorRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class SponsorDataInitializer implements DataInitializer {

    @Inject
    SponsorRepository sponsorRepository;

    @Override
    @Transactional
    public void initialize() {
        if (sponsorRepository.count() > 0) {
            Log.info("Sponsors already initialized, skipping...");
            return;
        }

        List<Sponsor> sponsors = Arrays.asList(
            createSponsor("TechVision", 
                "Leading provider of cloud infrastructure and AI solutions.",
                "https://example.com/techvision-logo.png",
                "https://techvision.com",
                Sponsor.SponsorTier.PLATINUM),
                
            createSponsor("CloudScale", 
                "Enterprise cloud platform for scalable applications.",
                "https://example.com/cloudscale-logo.png",
                "https://cloudscale.com",
                Sponsor.SponsorTier.PLATINUM),
                
            createSponsor("DataFlow", 
                "Data analytics and business intelligence solutions.",
                "https://example.com/dataflow-logo.png",
                "https://dataflow.com",
                Sponsor.SponsorTier.GOLD),
                
            createSponsor("CodeCraft", 
                "Developer tools and productivity solutions.",
                "https://example.com/codecraft-logo.png",
                "https://codecraft.com",
                Sponsor.SponsorTier.GOLD),
                
            createSponsor("SecureNet", 
                "Cybersecurity and threat protection services.",
                "https://example.com/securenet-logo.png",
                "https://securenet.com",
                Sponsor.SponsorTier.GOLD),
                
            createSponsor("AI Insights", 
                "Artificial intelligence and machine learning platform.",
                "https://example.com/aiinsights-logo.png",
                "https://aiinsights.com",
                Sponsor.SponsorTier.SILVER),
                
            createSponsor("CloudOps", 
                "Cloud operations and management solutions.",
                "https://example.com/cloudops-logo.png",
                "https://cloudops.com",
                Sponsor.SponsorTier.SILVER),
                
            createSponsor("WebCraft", 
                "Web development and design tools.",
                "https://example.com/webcraft-logo.png",
                "https://webcraft.com",
                Sponsor.SponsorTier.SILVER),
                
            createSponsor("API Solutions", 
                "API development and management platform.",
                "https://example.com/apisolutions-logo.png",
                "https://apisolutions.com",
                Sponsor.SponsorTier.SILVER),
                
            createSponsor("AppWorks", 
                "Mobile application development platform.",
                "https://example.com/appworks-logo.png",
                "https://appworks.com",
                Sponsor.SponsorTier.BRONZE),
                
            createSponsor("DesignHub", 
                "UI/UX design and prototyping tools.",
                "https://example.com/designhub-logo.png",
                "https://designhub.com",
                Sponsor.SponsorTier.BRONZE),
                
            createSponsor("ChainTech", 
                "Blockchain development and consulting.",
                "https://example.com/chaintech-logo.png",
                "https://chaintech.com",
                Sponsor.SponsorTier.BRONZE),
                
            createSponsor("TestPro", 
                "Software testing and quality assurance tools.",
                "https://example.com/testpro-logo.png",
                "https://testpro.com",
                Sponsor.SponsorTier.BRONZE),
                
            createSponsor("Enterprise Systems", 
                "Enterprise software solutions and consulting.",
                "https://example.com/enterprisesystems-logo.png",
                "https://enterprisesystems.com",
                Sponsor.SponsorTier.BRONZE),
                
            createSponsor("GameCraft", 
                "Game development tools and services.",
                "https://example.com/gamecraft-logo.png",
                "https://gamecraft.com",
                Sponsor.SponsorTier.BRONZE)
        );

        sponsors.forEach(sponsorRepository::persist);
        Log.info("Initialized " + sponsors.size() + " sponsors");
    }

    @Override
    public int getOrder() {
        return 4;
    }

    private Sponsor createSponsor(String name, String description, String logo, 
                                String website, Sponsor.SponsorTier tier) {
        Sponsor sponsor = new Sponsor();
        sponsor.name = name;
        sponsor.description = description;
        sponsor.logo = logo;
        sponsor.website = website;
        sponsor.tier = tier;
        return sponsor;
    }
} 