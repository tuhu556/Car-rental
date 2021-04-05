/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.CarDTO;
import dto.DiscountDTO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import utils.DBUtils;

/**
 *
 * @author Admin
 */
public class CarDAO {

    private static final Logger LOG = Logger.getLogger(CarDAO.class);

    public List<CarDTO> Search(int index, Date checkin, Date checkout, String search, String category, int amount) throws SQLException {
        List<CarDTO> list = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getCon();
            if (conn != null) {
                String sql = "SELECT carID, carName, categoryID, price, quantity, year, color, status, image FROM tblCars\n"
                        + "   WHERE status = 1 AND (carName like ? AND categoryID like ?) AND quantity >= ? AND ( \n"
                        + "   carID NOT IN (\n"
                        + "   SELECT c.carID FROM( SELECT d.carID, SUM(d.quantity)AS[total] FROM \n"
                        + "   tblOrder o JOIN tblOrderDetails d ON o.orderID = d.orderID \n"
                        + "   WHERE o.status = 1 AND ((checkIn BETWEEN ? AND ?) OR (checkOut BETWEEN ? AND ?))\n"
                        + "   GROUP BY d.carID) a JOIN tblCars c ON a.carID = c.carID WHERE quantity - total < ?\n"
                        + "   ))  order by carID offset ? rows fetch first 20 rows only";
                stm = conn.prepareStatement(sql);
                stm.setString(1, "%" + search + "%");
                stm.setString(2, "%" + category + "%");
                stm.setInt(3, amount);
                stm.setDate(4, checkin);
                stm.setDate(5, checkout);
                stm.setDate(6, checkin);
                stm.setDate(7, checkout);
                stm.setInt(8, amount);
                stm.setInt(9, (index - 1) * 20);
                rs = stm.executeQuery();

                while (rs.next()) {
                    String carID = rs.getString(1);
                    String carName = rs.getString(2);
                    String categoryID = rs.getString(3);
                    float price = rs.getFloat(4);
                    int quantity = rs.getInt(5);
                    int year = rs.getInt(6);
                    String color = rs.getString(7);
                    boolean status = rs.getBoolean(8);
                    String image = rs.getString(9);

                    if (list == null) {
                        list = new ArrayList<CarDTO>();
                    }
                    list.add(new CarDTO(carID, carName, categoryID, price, quantity, year, color, image, status));

                }
            }

        } catch (Exception e) {
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

    public int countCar(Date checkin, Date checkout, String name, String category, int amount) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getCon();
            if (conn != null) {
                String sql = "SELECT COUNT(carID) from tblCars WHERE carID\n"
                        + "NOT IN ( SELECT carID FROM tblOrder O JOIN tblOrderDetails D ON O.orderID = D.orderID WHERE ((checkIn BETWEEN ? AND ?) OR (checkOut BETWEEN ? AND ?))) \n"
                        + "AND status = 1 AND carName like ? AND categoryID like ? AND quantity>=?";
                stm = conn.prepareStatement(sql);
                stm.setDate(1, checkin);
                stm.setDate(2, checkout);
                stm.setDate(3, checkin);
                stm.setDate(4, checkout);
                stm.setString(5, "%" + name + "%");
                stm.setString(6, "%" + category + "%");
                stm.setInt(7, amount);
                rs = stm.executeQuery();

                while (rs.next()) {
                    int total = rs.getInt(1);
                    int countPage = 0;
                    countPage = total / 20;
                    if (total % 20 != 0) {
                        countPage++;
                    }
                    return countPage;
                }
            }

        } catch (Exception e) {
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
        return 0;
    }

    public int calculateQuantityOrder(Date checkin, Date checkout, String carID) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int total = 0;
        try {
            conn = DBUtils.getCon();
            if (conn != null) {
                String sql = "SELECT SUM(d.quantity) AS TOTAL FROM tblOrder O JOIN tblOrderDetails D ON O.orderID = D.orderID \n"
                        + "WHERE ((d.checkIn BETWEEN ? AND ?) OR d.checkIn <=?) AND ((d.checkOut BETWEEN ? AND ?) OR d.checkOut >=?) AND d.carID= ? AND o.status=1 GROUP BY d.carID";
                stm = conn.prepareStatement(sql);
                stm.setDate(1, checkin);
                stm.setDate(2, checkout);
                stm.setDate(3, checkin);
                stm.setDate(4, checkin);
                stm.setDate(5, checkout);
                stm.setDate(6, checkout);
                stm.setString(7, carID);
                rs = stm.executeQuery();

                while (rs.next()) {
                    total = rs.getInt(1);
                }
                return total;
            }

        } catch (Exception e) {
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
        return 0;
    }

    public Map<String, String> loadCategoriesMap() throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Map<String, String> categoryMap = new HashMap<>();
        try {
            conn = DBUtils.getCon();
            String sql = "SELECT categoryID,categoryName FROM tblCategory";
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                categoryMap.put(rs.getString("categoryID"), rs.getString("categoryName"));
            }
        } catch (Exception e) {
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
        return categoryMap;
    }

    public int getQuantity(String carID) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int quantity = 0;
        try {
            conn = DBUtils.getCon();
            String sql = "SELECT quantity FROM tblCars WHERE carID = ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, carID);
            rs = stm.executeQuery();
            while (rs.next()) {
                quantity = rs.getInt(1);
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
        return quantity;
    }

    public DiscountDTO checkDiscount(String code) throws ClassNotFoundException, SQLException {
        DiscountDTO result = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getCon();
            String sql = "select discountCode, discountName, value, startDate, endDate, status FROM tblDiscount WHERE discountCode=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, code);
            rs = ps.executeQuery();
            if (rs.next()) {
                String discountCode = rs.getString(1);
                String discountName = rs.getString(2);
                float value = rs.getFloat(3);
                Date startDate = rs.getDate(4);
                Date endDate = rs.getDate(5);
                boolean status = rs.getBoolean(6);
                result = new DiscountDTO(discountCode, discountName, value, startDate, endDate, status);
            }
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }
}
