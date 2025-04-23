package at.fhv.sys.hotel.service;

import at.fhv.sys.hotel.models.RoomAvailabilityModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class RoomAvailabilityService {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void addAvailability(RoomAvailabilityModel availability) {
        entityManager.persist(availability);
    }

    @Transactional
    public void removeAvailability(RoomAvailabilityModel availability) {
        entityManager.remove(availability);
    }

    @Transactional
    public void removeAvailability(String roomId, LocalDate startDate, LocalDate endDate) {
        entityManager.createQuery(
            "DELETE FROM RoomAvailabilityModel a WHERE a.roomId = :roomId " +
            "AND a.startDate = :startDate AND a.endDate = :endDate")
            .setParameter("roomId", roomId)
            .setParameter("startDate", startDate)
            .setParameter("endDate", endDate)
            .executeUpdate();
    }

    @Transactional
    public void removeAvailabilityFromDate(String roomId, LocalDate date) {
        entityManager.createQuery(
            "DELETE FROM RoomAvailabilityModel a WHERE a.roomId = :roomId " +
            "AND a.startDate >= :date")
            .setParameter("roomId", roomId)
            .setParameter("date", date)
            .executeUpdate();
    }

    @Transactional
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM RoomAvailabilityModel").executeUpdate();
    }

    public List<RoomAvailabilityModel> findAvailableRooms(LocalDate startDate, LocalDate endDate) {
        return entityManager.createQuery(
            "SELECT r FROM RoomAvailabilityModel r WHERE " +
            "r.startDate <= :startDate AND r.endDate >= :endDate", RoomAvailabilityModel.class)
            .setParameter("startDate", startDate)
            .setParameter("endDate", endDate)
            .getResultList();
    }

    public List<RoomAvailabilityModel> findAdjacentAvailability(String roomId, LocalDate startDate, LocalDate endDate) {
        return entityManager.createQuery(
            "SELECT r FROM RoomAvailabilityModel r WHERE r.roomId = :roomId AND " +
            "((r.endDate = :startDate) OR (r.startDate = :endDate))", RoomAvailabilityModel.class)
            .setParameter("roomId", roomId)
            .setParameter("startDate", startDate)
            .setParameter("endDate", endDate)
            .getResultList();
    }

    public List<RoomAvailabilityModel> findOverlappingAvailability(String roomId, LocalDate startDate, LocalDate endDate) {
        return entityManager.createQuery(
            "SELECT r FROM RoomAvailabilityModel r WHERE r.roomId = :roomId AND " +
            "((r.startDate <= :startDate AND r.endDate > :startDate) OR " +
            "(r.startDate < :endDate AND r.endDate >= :endDate) OR " +
            "(r.startDate >= :startDate AND r.endDate <= :endDate))", RoomAvailabilityModel.class)
            .setParameter("roomId", roomId)
            .setParameter("startDate", startDate)
            .setParameter("endDate", endDate)
            .getResultList();
    }

    public boolean isRoomAvailable(String roomId, LocalDate startDate, LocalDate endDate) {
        return !findOverlappingAvailability(roomId, startDate, endDate).isEmpty();
    }
} 