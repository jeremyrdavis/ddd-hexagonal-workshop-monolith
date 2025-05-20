package io.arrogantprogrammer.sessions;

import io.arrogantprogrammer.speakers.SpeakerEntity;
import io.arrogantprogrammer.speakers.SpeakerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class SessionService {
    @Inject
    SessionRepository sessionRepository;
    @Inject
    SpeakerRepository speakerRepository;

    public List<Session> getAllSessions() {
        return sessionRepository.listAll();
    }

    public Optional<Session> getSession(Long id) {
        return sessionRepository.findByIdOptional(id);
    }

    @Transactional
    public Session createSession(Session session) {
        sessionRepository.persist(session);
        return session;
    }

    @Transactional
    public Optional<Session> updateSession(Long id, Session session) {
        return sessionRepository.findByIdOptional(id)
                .map(existing -> {
                    existing.title = session.title;
                    existing.description = session.description;
                    existing.startTime = session.startTime;
                    existing.endTime = session.endTime;
                    existing.room = session.room;
                    return existing;
                });
    }

    @Transactional
    public boolean deleteSession(Long id) {
        return sessionRepository.deleteById(id);
    }

    @Transactional
    public Optional<Session> addSpeakerToSession(Long sessionId, Long speakerId) {
        Optional<Session> sessionOpt = sessionRepository.findByIdOptional(sessionId);
        Optional<SpeakerEntity> speakerOpt = speakerRepository.findByIdOptional(speakerId);
        if (sessionOpt.isPresent() && speakerOpt.isPresent()) {
            Session session = sessionOpt.get();
            SpeakerEntity speakerEntity = speakerOpt.get();
            if (!session.speakerEntities.contains(speakerEntity)) {
                session.speakerEntities.add(speakerEntity);
            }
            return Optional.of(session);
        }
        return Optional.empty();
    }

    @Transactional
    public Optional<Session> removeSpeakerFromSession(Long sessionId, Long speakerId) {
        Optional<Session> sessionOpt = sessionRepository.findByIdOptional(sessionId);
        Optional<SpeakerEntity> speakerOpt = speakerRepository.findByIdOptional(speakerId);
        if (sessionOpt.isPresent() && speakerOpt.isPresent()) {
            Session session = sessionOpt.get();
            SpeakerEntity speakerEntity = speakerOpt.get();
            boolean removed = session.speakerEntities.remove(speakerEntity);
            return removed ? Optional.of(session) : Optional.empty();
        }
        return Optional.empty();
    }

    public List<Session> findSessionsBySpeaker(Long speakerId) {
        return sessionRepository.find("SELECT s FROM Session s JOIN s.speakers sp WHERE sp.id = ?1", speakerId).list();
    }
} 