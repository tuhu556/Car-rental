/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.FeedbackDTO;
import dto.UserDTO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import utils.DBUtils;

/**
 *
 * @author Admin
 */
public class UserDAO {

    private static final Logger LOG = Logger.getLogger(UserDAO.class);

    public UserDTO checkLogin(String email, String password) throws ClassNotFoundException, SQLException {
        UserDTO result = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getCon();
            String sql = "select name, address, phone, roleID, status, code FROM tblUsers WHERE email =? AND password =?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                String role = rs.getString("roleID");
                boolean status = rs.getBoolean("status");
                String code = rs.getString("code");
                result = new UserDTO(email, password, name, phone, address, role, status, code);
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

    public void create(UserDTO dto) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtils.getCon();
            String sql = "INSERT INTO tblUsers (email, name, password, createDate, address, phone, roleID, status, code) VALUES(?,?,?,?,?,?,?,?,?)";
            Date createDate = Date.valueOf(LocalDate.now());
            ps = conn.prepareStatement(sql);
            ps.setString(1, dto.getEmail());
            ps.setString(2, dto.getName());
            ps.setString(3, dto.getPassword());
            ps.setDate(4, createDate);
            ps.setString(5, dto.getAddress());
            ps.setString(6, dto.getPhone());
            ps.setString(7, dto.getRoleID());
            ps.setBoolean(8, dto.isStatus());
            ps.setString(9, dto.getCode());
            ps.executeUpdate();

        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

    }

    public boolean checkEmail(String email) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getCon();
            if (conn != null) {
                String sql = "Select email FROM tblUsers WHERE email = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, email);
                rs = stm.executeQuery();
                while (rs.next()) {
                    return false;
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
        return true;
    }

    public UserDTO getEmail(String email) throws ClassNotFoundException, SQLException {
        UserDTO result = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getCon();
            String sql = "select name, address, phone, roleID, status, code FROM tblUsers WHERE email =?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                String role = rs.getString("roleID");
                boolean status = rs.getBoolean("status");
                String code = rs.getString("code");
                result = new UserDTO(email, "", name, phone, address, role, status, code);
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

    public void confirm(String email) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getCon();
            String sql = "UPDATE tblUsers set status=1 where email=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
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
    }

    public String getCode(String email) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String code = null;
        try {
            conn = DBUtils.getCon();
            if (conn != null) {
                String sql = "Select code FROM tblUsers WHERE email = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, email);
                rs = stm.executeQuery();
                while (rs.next()) {
                    code = rs.getString("code");
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
        return code;
    }

    public List<FeedbackDTO> getFeedback(String email, String ID) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<FeedbackDTO> list = null;
        try {
            conn = DBUtils.getCon();
            String sql = "SELECT feedbackID, feedbackContent, rating, carID, email, createDate FROM tblFeedback WHERE email = ? AND carID=?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, email);
            stm.setString(2, ID);
            rs = stm.executeQuery();
            while (rs.next()) {
                String feedbackID = rs.getString(1);
                String feedbackContent = rs.getString(2);
                int rating = rs.getInt(3);
                String carID = rs.getString(4);
                email = rs.getString(5);
                String createDate = rs.getString(6);
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(new FeedbackDTO(feedbackID, feedbackContent, rating, email, carID, createDate));
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

    public boolean createFeedback(FeedbackDTO dto) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getCon();
            String sql = "INSERT INTO tblFeedback (feedbackContent, rating, carID, email, createDate) VALUES(?,?,?,?,?)";
            Date createDate = Date.valueOf(LocalDate.now());
            boolean status = true;
            stm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, dto.getContent());
            stm.setInt(2, dto.getRating());
            stm.setString(3, dto.getCarID());
            stm.setString(4, dto.getEmail());
            stm.setDate(5, createDate);
            stm.execute();

            rs = stm.getGeneratedKeys();
            int feedbackID = 0;
            if (rs.next()) {
                feedbackID = rs.getInt("feedbackID");
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
        return true;
    }
}
