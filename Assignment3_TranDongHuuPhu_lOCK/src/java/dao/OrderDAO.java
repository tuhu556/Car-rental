/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.CarDTO;
import dto.CartDTO;
import dto.DetailDTO;
import dto.OrderDTO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import utils.DBUtils;

/**
 *
 * @author Admin
 */
public class OrderDAO {

    private static final Logger LOG = Logger.getLogger(OrderDAO.class);

    public boolean createOrder(CartDTO cart, String discountCode, float totalPrice) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        boolean result = false;
        try {
            conn = DBUtils.getCon();
            if (conn != null) {
                String sql = "INSERT INTO tblOrder (email, totalPrice, orderDate, status, discountCode) VALUES(?,?,?,?,?)";
                String orderID[] = {"orderID"};
                Date orderDate = Date.valueOf(LocalDate.now());
                boolean status = true;
                stm = conn.prepareStatement(sql, orderID);
                stm.setString(1, cart.getEmail());
                stm.setFloat(2, totalPrice);
                stm.setDate(3, orderDate);
                stm.setBoolean(4, status);
                stm.setString(5, discountCode);
                stm.executeUpdate();
                rs = stm.getGeneratedKeys();
                if (rs.next()) {
                    String orderDetailSql = "INSERT INTO tblOrderDetails (orderID, carID, price, quantity, checkIn, checkOut, totalDays) VALUES (?,?,?,?,?,?,?)";
                    for (CarDTO dto : cart.getCart().values()) {
                        stm = conn.prepareStatement(orderDetailSql);
                        stm.setInt(1, rs.getInt(1));
                        stm.setString(2, dto.getCarID());
                        stm.setFloat(3, dto.getPrice());
                        stm.setInt(4, dto.getQuantity());
                        stm.setDate(5, Date.valueOf(dto.getStartDate()));
                        stm.setDate(6, Date.valueOf(dto.getEndDate()));
                        stm.setInt(7, dto.getTotalDays());
                        stm.executeUpdate();
                    }
                    result = true;
                }
            }

        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }

    public List<OrderDTO> getListOrder(String email) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<OrderDTO> list = null;
        try {
            conn = DBUtils.getCon();
            String sql = "SELECT orderID, email, totalPrice, orderDate, status, discountCode FROM tblOrder WHERE email LIKE ? AND status = 1 order by orderDate";
            stm = conn.prepareStatement(sql);
            stm.setString(1, "%" + email + "%");
            rs = stm.executeQuery();
            while (rs.next()) {
                String orderID = rs.getString("orderID");
                email = rs.getString("email");
                float totalPrice = rs.getFloat("totalPrice");
                String orderDate = rs.getString("orderDate");
                boolean status = rs.getBoolean("status");
                String discountCode = rs.getString("discountCode");
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(new OrderDTO(orderID, email, totalPrice, orderDate, discountCode, status));
            }
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    public List<OrderDTO> searchOrder(String search, String email) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<OrderDTO> list = null;
        try {
            conn = DBUtils.getCon();
            String sql = "SELECT orderID, email, totalPrice, orderDate, status, discountCode FROM tblOrder WHERE email LIKE ? AND orderDate like ? AND status = 1 order by orderDate";
            stm = conn.prepareStatement(sql);
            stm.setString(1, "%" + email + "%");
            stm.setString(2, "%" + search + "%");
            rs = stm.executeQuery();
            while (rs.next()) {
                String orderID = rs.getString("orderID");
                email = rs.getString("email");
                float totalPrice = rs.getFloat("totalPrice");
                String orderDate = rs.getString("orderDate");
                boolean status = rs.getBoolean("status");
                String discountCode = rs.getString("discountCode");
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(new OrderDTO(orderID, email, totalPrice, orderDate, discountCode, status));
            }
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    public List<DetailDTO> getListOrderDetail(String ID) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<DetailDTO> list = null;
        try {
            conn = DBUtils.getCon();
            String sql = "SELECT tblOrderDetails.orderID, tblOrderDetails.carID, tblCars.carName, tblCars.image, tblOrderDetails.quantity, tblOrderDetails.price, \n"
                    + "tblOrderDetails.checkIn, tblOrderDetails.checkOut, tblOrderDetails.totalDays\n"
                    + "FROM tblOrderDetails INNER JOIN tblCars ON tblOrderDetails.carID = tblCars.carID   WHERE orderID = ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, ID);
            rs = stm.executeQuery();
            while (rs.next()) {
                String orderID = rs.getString(1);
                String carID = rs.getString(2);
                String carName = rs.getString(3);
                String image = rs.getString(4);
                int quantity = rs.getInt(5);
                float price = rs.getFloat(6);
                String startDate = rs.getString(7);
                String endDate = rs.getString(8);
                int totalDays = rs.getInt(9);
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(new DetailDTO(orderID, carID, carName, image, quantity, price, startDate, endDate, totalDays));
            }
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    public List<DetailDTO> SearchDetail(String ID, String search) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<DetailDTO> list = null;
        try {
            conn = DBUtils.getCon();
            String sql = "SELECT tblOrderDetails.orderID, tblOrderDetails.carID, tblCars.carName, tblCars.image, tblOrderDetails.quantity, tblOrderDetails.price,\n"
                    + "                    tblOrderDetails.checkIn, tblOrderDetails.checkOut, tblOrderDetails.totalDays\n"
                    + "                    FROM tblOrderDetails INNER JOIN tblCars ON tblOrderDetails.carID = tblCars.carID   WHERE orderID = ? AND tblCars.carName like ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, ID);
            stm.setString(2, "%" + search + "%");
            rs = stm.executeQuery();
            while (rs.next()) {
                String orderID = rs.getString(1);
                String carID = rs.getString(2);
                String carName = rs.getString(3);
                String image = rs.getString(4);
                int quantity = rs.getInt(5);
                float price = rs.getFloat(6);
                String startDate = rs.getString(7);
                String endDate = rs.getString(8);
                int totalDays = rs.getInt(9);
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(new DetailDTO(orderID, carID, carName, image, quantity, price, startDate, endDate, totalDays));
            }
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    public void cancelOrder(String ID) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBUtils.getCon();
            String sql = "UPDATE tblOrder SET status= 0 WHERE orderID = ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, ID);
            stm.executeUpdate();
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
}
