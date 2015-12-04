package datastore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Book;

/**
 * DAOSQLite Data Access Object for an SQLite database
 *
 * @author Dylan Lozo
 * @version 0.3 on 2015-11-03 revised 2015-11-24
 */
public class DAOSQLite {

    protected final static String DRIVER = "org.sqlite.JDBC";
    protected final static String JDBC = "jdbc:sqlite";

    /**
     * Inserts an record into the database table. Note the use of a
     * parameterized query to prevent SQL Injection attacks.
     *
     * @param book the object to insert
     * @param dbPath the path to the SQLite database
     */
    public static void createRecord(Book book, String dbPath) {
        String q = "insert into book (bookName, bookAuthor, bookSection, iSBN, copyrightYear) "
                + "values (?, ?, ?, ?, ?)";
        try (Connection conn = getConnectionDAO(dbPath);
                PreparedStatement ps = conn.prepareStatement(q)) {
            ps.setString(1, book.getBookName());
            ps.setString(2, book.getBookAuthor());
            ps.setString(3, book.getBookSection());
            ps.setString(4, book.getiSBN());
            ps.setInt(5, book.getCopyrightYear());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOSQLite.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Retrieve all of the records in the database as a list sorted by
     * name. This method was replaced by a more advanced method.
     *
     * @param dbPath the path to the SQLite database
     * @return list of objects
     */
    public static List<Book> retrieveAllRecords(String dbPath) {
        String q = "select * from book order by bookName";
        List<Book> list = null;
        try (Connection conn = getConnectionDAO(dbPath);
                PreparedStatement ps = conn.prepareStatement(q)) {
            list = myQuery(conn, ps);
        } catch (SQLException ex) {
            Logger.getLogger(DAOSQLite.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
//    /**
//     * This is a much more advanced retrieve method. It can get all of the
//     * records from the database or a subset based on the various parameters
//     * passed in.
//     *
//     * @param dbPath the path to the SQLite database
//     * @param gameName - the name of the video game
//     * @return list of objects
//     */
//    public static List<Game> retrieveRecords(String dbPath, String gameName) {
//        String q = "select * from game order by gameName";
//
//        List<Game> list = null;
//        try (Connection conn = getConnectionDAO(dbPath);
//                PreparedStatement ps = conn.prepareStatement(q)) {
//            // the % sign is an sql wildcard so that we can search by just a few letters of the game name
//            ps.setString(1, gameName + "%");
//            System.out.println(q);
//            list = myQuery(conn, ps);
//        } catch (SQLException ex) {
//            Logger.getLogger(DAOSQLite.class
//                    .getName()).log(Level.SEVERE, null, ex);
//        }
//        return list;
//    }

    /**
     * Delete a record from the database given its name. Note the use of a
     * parameterized query to prevent SQL Injection attacks.
     *
     * @param bookName the name of the record to delete
     * @param dbPath the path to the SQLite database
     */
    public static void deleteRecord(String bookName, String dbPath) {
        String q = "delete from book where bookName = ?";
        try (Connection conn = getConnectionDAO(dbPath);
                PreparedStatement ps = conn.prepareStatement(q)) {
            ps.setString(1, bookName);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOSQLite.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Creates a new user table.
     *
     * @param dbPath the path to the SQLite database
     */
    public static void createTable(String dbPath) {
        String q = "create table book ("
                + "bookName varchar(50), "
                + "bookAuthor varchar(20) not null, "
                + "bookSection varchar(200) not null, "
                + "iSBN varchar(20) not null, "
                + "copyrightYear int not null);";
        System.out.println("createtable " + q);
        try (Connection conn = getConnectionDAO(dbPath);
                PreparedStatement ps = conn.prepareStatement(q)) {
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOSQLite.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Drops the user table erasing all of the data.
     *
     * @param dbPath the path to the SQLite database
     */
    public static void dropTable(String dbPath) {
        final String q = "drop table if exists book";
        try (Connection conn = getConnectionDAO(dbPath);
                PreparedStatement ps = conn.prepareStatement(q)) {
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOSQLite.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Populates the table with sample data records.
     *
     * @param dbPath the path to the SQLite database
     */
    public static void populateTable(String dbPath) {
        Book p;
        p = new Book("ASP.NET 4", "Stephen Walther, Kevin Hoffman, Nate Dudek", "ASP.NET", "978-0-672-33112-1", 2011);
        DAOSQLite.createRecord(p, dbPath);
        p = new Book("Intermediate Algebra with Applications", "Terry H. Wesner, Harry L.Nustad", "Math", "0-697-08571-31", 1992);
        DAOSQLite.createRecord(p, dbPath);
        p = new Book("Web Publishing with HTML 4 in 21 days", "Laura Lemay", "Html/Css", "0-672-31725-7", 2000);
        DAOSQLite.createRecord(p, dbPath);
        p = new Book("Network Administration", "Steve Wisniewski", "Networking", "0-13-015882-8", 2001);
        DAOSQLite.createRecord(p, dbPath);
        p = new Book("Guide to Telecommunications Technology", "Tamara Dean", "Networking", "0-619-03547-1", 2003);
        DAOSQLite.createRecord(p, dbPath);
        p = new Book("The Linux Programming Bible", "John Goerzen", "Linux", "0-7645-4657-0", 2000);
        DAOSQLite.createRecord(p, dbPath);
        p = new Book("Programming Perl", "Larry Wall, Tom Christiansen, Randel L. Schwatz", "Perl", "1-56592-149-6", 1996);
        DAOSQLite.createRecord(p, dbPath);
        p = new Book("The Complete Reference SQL", "James R. Groff, Pual N. Weinburg", "SQL", "0-07-211845-8", 1999);
        DAOSQLite.createRecord(p, dbPath);
        p = new Book("A practical Guide to The Unix System Third Edition", "Mark G. Somell", "Unix", "0-8053-7565-1", 1995);
        DAOSQLite.createRecord(p, dbPath);
        p = new Book("The unix Programming Enviroment", "Brian W. Kernighan, Rob Pike", "Unix", "0-13-937699-2", 1984);
        DAOSQLite.createRecord(p, dbPath);
        p = new Book("Design Basics", "David A. Lauer, Stephen PenTak", "Web", "0-15-508377-5", 2000);
        DAOSQLite.createRecord(p, dbPath);
        p = new Book("The complete Reference C++", "Herbert Schildt", "C Languages", "0-07-222680-3", 2003);
        DAOSQLite.createRecord(p, dbPath);
        p = new Book("programming and Problem Solving with C++", "Nell Dale, Chip Weems", "C Languages", "0-7637-0798-8", 2005);
        DAOSQLite.createRecord(p, dbPath);
        p = new Book("Concepts of database managment", "Phillip J. Pratt, Joseph J. Adamski", "Database", "0-619-06462-5", 2002);
        DAOSQLite.createRecord(p, dbPath);
        p = new Book("Java Software solutions foundation of programming design", "Louis and Loftus", "java", "0-321-24583-0", 2005);
        DAOSQLite.createRecord(p, dbPath);
        p = new Book("The web wizards guide to javascript", "Steven G. Estrella", "Java", "0-201-75833-4", 2002);
        DAOSQLite.createRecord(p, dbPath);
        p = new Book("Professional Apache", "Peter Wainright", "ASP.Net", "1-861003-02-1", 1999);
        DAOSQLite.createRecord(p, dbPath);
        p = new Book("MySQL", "Pual Dubois", "MySQL", "0-7357-0921-1", 2000);
        DAOSQLite.createRecord(p, dbPath);
        p = new Book("Statistics", "James T. McClave, Terry Sincich", "Math", "0-13-022574-6", 200);
        DAOSQLite.createRecord(p, dbPath);
        p = new Book("Intigrated Web design", "Molly E. Holzschlag", "Web", "0-7357-1233-6", 2003);
        DAOSQLite.createRecord(p, dbPath);
        p = new Book("Linux Pocket Guid", "Daniel J. Barrett", "Linux", "0-596-00628-4", 2004);
        DAOSQLite.createRecord(p, dbPath);
    }

    /**
     * A helper method that executes a prepared statement and returns the result
     * set as a list of objects.
     *
     * @param conn a connection to the database
     * @param ps a prepared statement
     * @return list of objects from the result set
     */
    protected static List<Book> myQuery(Connection conn, PreparedStatement ps) {
        List<Book> list = new ArrayList();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String bookName = rs.getString("bookName");
                String bookAuthor = rs.getString("bookAuthor");
                String bookSection = rs.getString("bookSection");
                String iSBN = rs.getString("iSBN");
                int copyrightYear = rs.getInt("copyrightYear");
                Book p = new Book(bookName, bookAuthor, bookSection, iSBN, copyrightYear);
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOSQLite.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    /**
     * Creates a connection to the SQLite database.
     *
     * @param dbPath the path to the SQLite database
     * @return connection to the database
     */
    protected static Connection getConnectionDAO(String dbPath) {
        Connection conn = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(JDBC + ":" + dbPath);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
}
