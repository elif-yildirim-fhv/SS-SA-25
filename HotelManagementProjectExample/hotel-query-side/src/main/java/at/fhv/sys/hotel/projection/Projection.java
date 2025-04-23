package at.fhv.sys.hotel.projection;

public interface Projection {
    void processEvent(Object event);
    void clearState();
} 