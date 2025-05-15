package io.arrogantprogrammer.init;

import io.arrogantprogrammer.speakers.Speaker;
import io.arrogantprogrammer.speakers.SpeakerRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class SpeakerDataInitializer implements DataInitializer {

    @Inject
    SpeakerRepository speakerRepository;

    @Override
    @Transactional
    public void initialize() {
        if (speakerRepository.count() > 0) {
            Log.info("Speakers already initialized, skipping...");
            return;
        }

        List<Speaker> speakers = Arrays.asList(
            createSpeaker("Sarah Chen", "CTO at TechVision", 
                "Leading AI initiatives and cloud architecture", 
                "https://randomuser.me/api/portraits/women/1.jpg",
                "@sarahchen", "sarah.chen@techvision.com"),
                
            createSpeaker("Marcus Johnson", "Principal Engineer at CloudScale", 
                "Microservices and distributed systems expert", 
                "https://randomuser.me/api/portraits/men/2.jpg",
                "@marcusj", "marcus.j@cloudscale.com"),
                
            createSpeaker("Priya Patel", "Head of Product at DataFlow", 
                "Product strategy and user experience", 
                "https://randomuser.me/api/portraits/women/3.jpg",
                "@priyap", "priya.patel@dataflow.com"),
                
            createSpeaker("David Kim", "Senior Developer Advocate at CodeCraft", 
                "Developer experience and community building", 
                "https://randomuser.me/api/portraits/men/4.jpg",
                "@davidkim", "david.kim@codecraft.com"),
                
            createSpeaker("Emma Rodriguez", "Security Lead at SecureNet", 
                "Cybersecurity and threat modeling", 
                "https://randomuser.me/api/portraits/women/5.jpg",
                "@emmar", "emma.r@securenet.com"),
                
            createSpeaker("James Wilson", "Data Scientist at AI Insights", 
                "Machine learning and predictive analytics", 
                "https://randomuser.me/api/portraits/men/6.jpg",
                "@jamesw", "james.w@aiinsights.com"),
                
            createSpeaker("Aisha Khan", "DevOps Engineer at CloudOps", 
                "Infrastructure as code and automation", 
                "https://randomuser.me/api/portraits/women/7.jpg",
                "@aishak", "aisha.k@cloudops.com"),
                
            createSpeaker("Michael Chang", "Frontend Architect at WebCraft", 
                "Modern web development and performance", 
                "https://randomuser.me/api/portraits/men/8.jpg",
                "@michaelc", "michael.c@webcraft.com"),
                
            createSpeaker("Sophie Martin", "Backend Developer at API Solutions", 
                "API design and scalability", 
                "https://randomuser.me/api/portraits/women/9.jpg",
                "@sophiem", "sophie.m@apisolutions.com"),
                
            createSpeaker("Raj Patel", "Mobile Developer at AppWorks", 
                "Cross-platform mobile development", 
                "https://randomuser.me/api/portraits/men/10.jpg",
                "@rajp", "raj.p@appworks.com"),
                
            createSpeaker("Lisa Thompson", "UX Designer at DesignHub", 
                "User research and interface design", 
                "https://randomuser.me/api/portraits/women/11.jpg",
                "@lisat", "lisa.t@designhub.com"),
                
            createSpeaker("Alex Wong", "Blockchain Developer at ChainTech", 
                "Smart contracts and decentralized systems", 
                "https://randomuser.me/api/portraits/men/12.jpg",
                "@alexw", "alex.w@chaintech.com"),
                
            createSpeaker("Maria Garcia", "QA Lead at TestPro", 
                "Test automation and quality assurance", 
                "https://randomuser.me/api/portraits/women/13.jpg",
                "@mariag", "maria.g@testpro.com"),
                
            createSpeaker("John Smith", "System Architect at Enterprise Systems", 
                "Enterprise architecture and integration", 
                "https://randomuser.me/api/portraits/men/14.jpg",
                "@johns", "john.s@enterprisesystems.com"),
                
            createSpeaker("Yuki Tanaka", "Game Developer at GameCraft", 
                "Game development and graphics programming", 
                "https://randomuser.me/api/portraits/women/15.jpg",
                "@yukit", "yuki.t@gamecraft.com"),
                
            createSpeaker("Carlos Mendez", "Database Administrator at DataBase", 
                "Database optimization and scaling", 
                "https://randomuser.me/api/portraits/men/16.jpg",
                "@carlosm", "carlos.m@database.com"),
                
            createSpeaker("Anna Kowalski", "Cloud Architect at CloudBuild", 
                "Cloud infrastructure and migration", 
                "https://randomuser.me/api/portraits/women/17.jpg",
                "@annak", "anna.k@cloudbuild.com"),
                
            createSpeaker("Thomas Lee", "Security Researcher at SecureLab", 
                "Vulnerability research and security testing", 
                "https://randomuser.me/api/portraits/men/18.jpg",
                "@thomasl", "thomas.l@securelab.com"),
                
            createSpeaker("Nina Patel", "AI Researcher at DeepMind", 
                "Natural language processing and AI ethics", 
                "https://randomuser.me/api/portraits/women/19.jpg",
                "@ninap", "nina.p@deepmind.com"),
                
            createSpeaker("Robert Chen", "Performance Engineer at SpeedTest", 
                "Application performance and optimization", 
                "https://randomuser.me/api/portraits/men/20.jpg",
                "@robertc", "robert.c@speedtest.com"),
                
            createSpeaker("Laura Martinez", "Mobile Architect at AppArch", 
                "Mobile architecture and design patterns", 
                "https://randomuser.me/api/portraits/women/21.jpg",
                "@lauram", "laura.m@apparch.com"),
                
            createSpeaker("Daniel Kim", "DevOps Lead at CloudScale", 
                "CI/CD and infrastructure automation", 
                "https://randomuser.me/api/portraits/men/22.jpg",
                "@danielk", "daniel.k@cloudscale.com"),
                
            createSpeaker("Sofia Rodriguez", "Frontend Developer at WebCraft", 
                "Modern JavaScript frameworks and web performance", 
                "https://randomuser.me/api/portraits/women/23.jpg",
                "@sofiar", "sofia.r@webcraft.com"),
                
            createSpeaker("Kevin Wang", "Backend Developer at API Solutions", 
                "RESTful APIs and microservices", 
                "https://randomuser.me/api/portraits/men/24.jpg",
                "@kevinw", "kevin.w@apisolutions.com"),
                
            createSpeaker("Rachel Green", "UX Researcher at DesignHub", 
                "User research and usability testing", 
                "https://randomuser.me/api/portraits/women/25.jpg",
                "@rachelg", "rachel.g@designhub.com")
        );

        speakers.forEach(speakerRepository::persist);
        Log.info("Initialized " + speakers.size() + " speakers");
    }

    @Override
    public int getOrder() {
        return 1;
    }

    private Speaker createSpeaker(String name, String title, String bio, 
                                String headshotUrl, String twitterHandle, String email) {
        Speaker speaker = new Speaker();
        speaker.name = name;
        speaker.title = title;
        speaker.bio = bio;
        speaker.headshot = headshotUrl;
        speaker.company = title.split(" at ")[1];
        return speaker;
    }
} 