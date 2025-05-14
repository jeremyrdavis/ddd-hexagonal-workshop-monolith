package io.arrogantprogrammer.agenda;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.util.List;
import com.conference.sessions.Session;
import com.conference.sessions.SessionRepository;
import io.arrogantprogrammer.speakers.Speaker;

@Path("/api/agenda")
@Produces(MediaType.APPLICATION_JSON)
public class AgendaResource {
    @Inject
    AgendaService agendaService;

    @GET
    public List<AgendaItem> getFullAgenda() {
        return agendaService.getFullAgenda();
    }

    @GET
    @Path("/day/{date}")
    public List<AgendaItem> getAgendaForDay(@PathParam("date") String date) {
        LocalDate localDate = LocalDate.parse(date);
        return agendaService.getAgendaForDay(localDate);
    }

    @GET
    @Path("/track/{track}")
    public List<AgendaItem> getAgendaForTrack(@PathParam("track") String track) {
        return agendaService.getAgendaForTrack(track);
    }
} 