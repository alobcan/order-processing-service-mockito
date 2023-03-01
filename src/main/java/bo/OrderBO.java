package bo;

import bo.exception.BOException;
import dto.Order;

public interface OrderBO {
    boolean placeOrder(Order order) throws BOException;

    boolean cancelOrder(int id) throws BOException;

    boolean deleteOrder(int id) throws BOException;
}
