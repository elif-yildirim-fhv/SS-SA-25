package at.fhv.sys.eventbus.repository;

import at.fhv.sys.eventbus.services.EventEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class EventEntityPanache {
    public List<EventEntity> findAll() {return EventEntity.listAll();}

    @Transactional
    public void createEvent(EventEntity eventEntity) {
        eventEntity.persist();
    }
}
