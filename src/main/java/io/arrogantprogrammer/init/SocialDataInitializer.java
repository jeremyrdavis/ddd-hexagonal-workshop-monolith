package io.arrogantprogrammer.init;

import io.arrogantprogrammer.social.SocialPost;
import io.arrogantprogrammer.social.SocialMediaService;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class SocialDataInitializer implements DataInitializer {

    @Inject
    SocialMediaService socialMediaService;

    @Override
    @Transactional
    public void initialize() {
        if (SocialPost.count() > 0) {
            Log.info("Social posts already initialized, skipping...");
            return;
        }

        List<SocialPost> posts = Arrays.asList(
            new SocialPost("Welcome to our annual tech conference! We're excited to have you all here. #TechConf2024",
                "Conference Bot", "Twitter", 1L),
                
            new SocialPost("Don't forget to check out the amazing lineup of speakers we have today! #TechConf2024",
                "Conference Bot", "Twitter", 2L),
                
            new SocialPost("The keynote session is about to begin! Join us in the Main Hall. #TechConf2024",
                "Conference Bot", "Twitter", 3L),
                
            new SocialPost("Great discussions happening in the Microservices Architecture session! #TechConf2024",
                "Conference Bot", "Twitter", 4L),
                
            new SocialPost("Time for a coffee break! Network with fellow attendees in the Lobby. #TechConf2024",
                "Conference Bot", "Twitter", 5L),
                
            new SocialPost("Exciting cloud-native development talks coming up next! #TechConf2024",
                "Conference Bot", "Twitter", 6L),
                
            new SocialPost("Lunch is served! Enjoy the delicious food and great company. #TechConf2024",
                "Conference Bot", "Twitter", 7L),
                
            new SocialPost("AI and Machine Learning session is packed! Don't miss out on the insights. #TechConf2024",
                "Conference Bot", "Twitter", 8L),
                
            new SocialPost("DevOps and CI/CD pipelines - learn from the experts! #TechConf2024",
                "Conference Bot", "Twitter", 9L),
                
            new SocialPost("Security best practices session is a must-attend! #TechConf2024",
                "Conference Bot", "Twitter", 10L),
                
            new SocialPost("Frontend development trends - stay ahead of the curve! #TechConf2024",
                "Conference Bot", "Twitter", 11L),
                
            new SocialPost("Database design and optimization tips from the pros! #TechConf2024",
                "Conference Bot", "Twitter", 12L),
                
            new SocialPost("Blockchain development workshop is about to start! #TechConf2024",
                "Conference Bot", "Twitter", 13L),
                
            new SocialPost("Mobile app development strategies - learn from the best! #TechConf2024",
                "Conference Bot", "Twitter", 14L),
                
            new SocialPost("API design and documentation best practices session is next! #TechConf2024",
                "Conference Bot", "Twitter", 15L),
                
            new SocialPost("Testing strategies for modern applications - don't miss this! #TechConf2024",
                "Conference Bot", "Twitter", 16L),
                
            new SocialPost("Closing remarks coming up! Join us in the Main Hall. #TechConf2024",
                "Conference Bot", "Twitter", 17L),
                
            new SocialPost("Thank you all for making this conference a success! See you next year! #TechConf2024",
                "Conference Bot", "Twitter", 18L)
        );

        posts.forEach(post -> post.persist());
        Log.info("Initialized " + posts.size() + " social posts");
    }

    @Override
    public int getOrder() {
        return 5;
    }
} 