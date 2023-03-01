package bo;

import bo.exception.BOException;
import dao.OrderDAO;
import dto.Order;
import lombok.Getter;
import lombok.Setter;

import java.sql.SQLException;

@Getter
@Setter
public class OrderBOImpl implements OrderBO {
    private OrderDAO dao;
    @Override
    public boolean placeOrder(Order order) throws BOException {
        try {
            int result = dao.create(order);
            return result != 0;
        } catch (SQLException e) {
            throw new BOException(e);
        }
    }

    @Override
    public boolean cancelOrder(int id) throws BOException {
        try {
            Order order = dao.read(id);
            order.setStatus("cancelled");
            int update = dao.update(order);
            return update != 0;
        } catch (SQLException e) {
            throw new BOException(e);
        }
    }

    @Override
    public boolean deleteOrder(int id) throws BOException {
        try {
            int delete = dao.delete(id);
            return delete != 0;
        } catch (SQLException e) {
            throw new BOException(e);
        }
    }
}
