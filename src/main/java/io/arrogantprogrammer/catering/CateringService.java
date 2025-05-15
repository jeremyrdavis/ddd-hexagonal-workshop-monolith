package io.arrogantprogrammer.catering;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class CateringService implements PanacheRepository<DietaryRequirement> {

    @Transactional
    public DietaryRequirement createDietaryRequirement(Long attendeeId, String preference, String specialRequests) {
        DietaryRequirement requirement = new DietaryRequirement();
        requirement.setAttendeeId(attendeeId);
        requirement.setPreference(preference);
        requirement.setSpecialRequests(specialRequests);
        persist(requirement);
        return requirement;
    }

    public List<DietaryRequirement> getAllDietaryRequirements() {
        return listAll();
    }

    public List<DietaryRequirement> getDietaryRequirementsByAttendee(Long attendeeId) {
        return find("attendeeId", attendeeId).list();
    }
} 