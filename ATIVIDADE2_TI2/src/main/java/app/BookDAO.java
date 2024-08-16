package app;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
	
    private Connection connection;
    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String user = "postgres";
    private final String password = "admin";

    public void BookDao () {
		this.connection = null;
	}
    
    public void openConnection() throws SQLException {
        if (this.connection == null || this.connection.isClosed()) {
        	this.connection = DriverManager.getConnection(url, user, password);
        }
    }

    public void closeConnection() throws SQLException {
        if (this.connection != null && !this.connection.isClosed()) {
        	this.connection.close();
        }
    }

    public List<Book> listBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        
        try {
            openConnection();
            
            Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                books.add(
                		new Book(
                				rs.getInt("id"), 
                				rs.getString("title"), 
                				rs.getString("author"), 
                				rs.getDouble("price"), 
                				rs.getInt("quantity")
                			)
                		);
            }
        } catch (SQLException e) {
            System.err.println("Error listing books: " + e.getMessage());
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
        
        return books;
    }

    public boolean insertBook(Book book) {
        String query = "INSERT INTO books (title, author, price, quantity) VALUES (?, ?, ?, ?)";
        int affectedRows = 0;
        
        try {
            openConnection();
            
            PreparedStatement pstmt = this.connection.prepareStatement(query);
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setDouble(3, book.getPrice());
            pstmt.setInt(4, book.getQuantity());
            
            affectedRows = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting book: " + e.getMessage());
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
        
        return affectedRows > 0;
    }

    public boolean deleteBook(int id) {
        String query = "DELETE FROM books WHERE id = ?";
        int affectedRows = 0;
        
        try {
            openConnection();
            
            PreparedStatement pstmt = this.connection.prepareStatement(query);
            pstmt.setInt(1, id);
            
            affectedRows = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting book: " + e.getMessage());
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
        
        return affectedRows > 0;
    }

    public boolean updateBook(Book book) {
        String query = "UPDATE books SET title = ?, author = ?, price = ?, quantity = ? WHERE id = ?";
        int affectedRows = 0;
        
        try {
            openConnection();
            
            PreparedStatement pstmt = this.connection.prepareStatement(query);
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setDouble(3, book.getPrice());
            pstmt.setInt(4, book.getQuantity());
            pstmt.setInt(5, book.getId());
            
            affectedRows = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating book: " + e.getMessage());
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
        
        return affectedRows > 0;
    }
}
