package org.example.dao;

import org.example.model.Food;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class FoodDAO extends DAO {
    public FoodDAO() {
        super();
    }

    public boolean insert(Food food) throws SQLException {
        boolean status = false;
        openConnection();
        try {
            String sql = "INSERT INTO Food (name, price, quantity, date_of_fabrication, valid_date) "
                    + "VALUES (?, ?, ?, ?, ?);";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, food.getName());
            st.setFloat(2, food.getPrice());
            st.setInt(3, food.getQuantity());
            st.setTimestamp(4, Timestamp.valueOf(food.getDateOfFabrication()));
            st.setDate(5, Date.valueOf(food.getValidDate()));
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        closeConnection();
        return status;
    }

    public Food get(int id) throws SQLException {
        Food food = null;
        openConnection();
        try {
            Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM Food WHERE id=" + id;
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                food = new Food(rs.getInt("id"), rs.getString("name"), (float) rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getTimestamp("date_of_fabrication").toLocalDateTime(),
                        rs.getDate("valid_date").toLocalDate());
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        closeConnection();
        return food;
    }

    public List<Food> get() throws SQLException {
        return get("");
    }

    public List<Food> getOrderByID() throws SQLException {
        return get("id");
    }

    public List<Food> getOrderByName() throws SQLException {
        return get("name");
    }

    public List<Food> getOrderByPreco() throws SQLException {
        return get("price");
    }

    private List<Food> get(String orderBy) throws SQLException {
        List<Food> foods = new ArrayList<Food>();
        openConnection();
        try {
            Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM Food" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Food p = new Food(rs.getInt("id"), rs.getString("name"),
                        (float) rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getTimestamp("date_of_fabrication").toLocalDateTime(),
                        rs.getDate("valid_date").toLocalDate());
                foods.add(p);
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        closeConnection();
        return foods;
    }

    public boolean update(Food food) throws SQLException {
        boolean status = false;
        openConnection();
        try {
            String sql = "UPDATE Food SET name = ?, price = ?, quantity = ?, date_of_fabrication = ?, valid_date = ? WHERE id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, food.getName());
            st.setFloat(2, food.getPrice());
            st.setInt(3, food.getQuantity());
            st.setTimestamp(4, Timestamp.valueOf(food.getDateOfFabrication()));
            st.setDate(5, Date.valueOf(food.getValidDate()));
            st.setInt(6, food.getId());
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        closeConnection();
        return status;
    }

    public boolean delete(int id) throws SQLException {
        boolean status = false;
        openConnection();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate("DELETE FROM Food WHERE id = " + id);
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        closeConnection();
        return status;
    }
}
