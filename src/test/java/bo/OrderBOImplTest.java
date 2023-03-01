package bo;

import bo.exception.BOException;
import dao.OrderDAO;
import dto.Order;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Or;

import java.sql.SQLException;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

public class OrderBOImplTest {
    public static final int ORDER_ID = 123;
    @Mock
    OrderDAO dao;

    private OrderBOImpl bo;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        bo = new OrderBOImpl();
        bo.setDao(dao);
    }

    @Test
    public void placeOrder_Should_Create_An_Order() throws SQLException, BOException {
        Order order = new Order();
        when(dao.create(any(Order.class))).thenReturn(1);

        assertTrue(bo.placeOrder(order));
        verify(dao, atLeast(1)).create(order);
    }

    @Test
    public void placeOrder_Should_Not_Create_An_Order() throws SQLException, BOException {
        Order order = new Order();
        when(dao.create(order)).thenReturn(0);

        assertFalse(bo.placeOrder(order));
        verify(dao).create(order);
    }

    @Test(expected = BOException.class)
    public void placeOrder_Should_Throw_BOException() throws SQLException, BOException {
        Order order = new Order();
        when(dao.create(order)).thenThrow(SQLException.class);

        assertFalse(bo.placeOrder(order));
    }

    @Test
    public void cancelOrder_Should_Cancel_Order() throws SQLException, BOException {
        Order order = new Order();
        when(dao.read(123)).thenReturn(order);
        when(dao.update(order)).thenReturn(1);

        assertTrue(bo.cancelOrder(123));
        verify(dao).read(123);
        verify(dao).update(order);
    }

    @Test
    public void cancelOrder_Should_Not_Cancel_Order() throws SQLException, BOException {
        Order order = new Order();
        when(dao.read(ORDER_ID)).thenReturn(order);
        when(dao.update(order)).thenReturn(0);

        assertFalse(bo.cancelOrder(ORDER_ID));
        verify(dao).read(ORDER_ID);
        verify(dao).update(order);
    }

    @Test(expected = BOException.class)
    public void cancelOrder_Should_Throw_BOException() throws SQLException, BOException {
        when(dao.read(anyInt())).thenThrow(SQLException.class);
        bo.cancelOrder(ORDER_ID);
    }

    @Test(expected = BOException.class)
    public void cancelOrder_Should_Throw_BOException_On_Update() throws SQLException, BOException {
        Order order = new Order();
        when(dao.read(ORDER_ID)).thenReturn(order);
        when(dao.update(order)).thenThrow(SQLException.class);
        bo.cancelOrder(ORDER_ID);
    }

    @Test
    public void deleteOrder_Deletes_The_Order() throws SQLException, BOException {
        when(dao.delete(ORDER_ID)).thenReturn(1);
        assertTrue(bo.deleteOrder(ORDER_ID));

        verify(dao).delete(ORDER_ID);
    }

    @Test
    public void deleteOrder_Does_Not_Delete_The_Order() throws SQLException, BOException {
        when(dao.delete(ORDER_ID)).thenReturn(0);
        assertFalse(bo.deleteOrder(ORDER_ID));

        verify(dao).delete(ORDER_ID);
    }

    @Test(expected = BOException.class)
    public void deleteOrder_Should_Throw_Exception() throws SQLException, BOException {
        when(dao.delete(ORDER_ID)).thenThrow(SQLException.class);
        bo.deleteOrder(ORDER_ID);
        verify(dao).delete(ORDER_ID);
    }
}