package io.arrogantprogrammer.merchandise;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class MerchandiseService implements PanacheRepository<MerchandiseOrder> {

    @Transactional
    public MerchandiseOrder createOrder(Long attendeeId, String tShirtSize) {
        MerchandiseOrder order = new MerchandiseOrder();
        order.setAttendeeId(attendeeId);
        order.setTShirtSize(tShirtSize);
        persist(order);
        return order;
    }

    public List<MerchandiseOrder> getAllOrders() {
        return listAll();
    }

    public List<MerchandiseOrder> getOrdersByAttendee(Long attendeeId) {
        return find("attendeeId", attendeeId).list();
    }

    @Transactional
    public void updateOrderStatus(Long orderId, String status) {
        MerchandiseOrder order = findById(orderId);
        if (order != null) {
            order.setOrderStatus(status);
        }
    }
} 