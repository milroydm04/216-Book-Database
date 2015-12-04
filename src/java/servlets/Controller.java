package servlets;

import datastore.DAOSQLite;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Book;

/**
 * All of this application's web pages send their requests to this controller
 * which then updates the model / database as needed and transfers control with
 * data to one the the HTML/JSP view-oriented programs for display.
 *
 * @author Dylan Lozo
 */
public class Controller extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get real path to the sqlite db
        ServletContext sc = this.getServletContext();
        String dbPath = sc.getRealPath("/WEB-INF/superstar.db");

        // set default url
        String url = "/home.html";

        // get current action
        String action = request.getParameter("action");
        if (action == null) {
            action = "home";
        }

        // perform action and set url
        if (action.equalsIgnoreCase("home")) {
            System.out.println("controller:home");
            url = "/home.html";

        } else if (action.equalsIgnoreCase("createRecord")) {
            System.out.println("controller:createRecord");
            int copyrightYear = 0;

            // get parameters passed in from the request
            String bookName = request.getParameter("bookName");
            String bookAuthor = request.getParameter("bookAuthor");
            String bookSection = request.getParameter("bookSection");
            String iSBN = request.getParameter("iSBN");
            String copyrightYearS = request.getParameter("copyrightYear");

            // validate and convert rating string into an integer
            if (copyrightYearS == null || copyrightYearS.isEmpty()) {
                copyrightYear = 0;
            } else {
                copyrightYear = Integer.parseInt(copyrightYearS);
            }
            

            // store data in an Book object
            Book book = new Book(bookName, bookAuthor, bookSection, iSBN, copyrightYear);
            System.out.println("Controller:createRecord:book=" + book);

            // validate the parameters
            if (bookName == null || bookAuthor == null || bookSection == null
                    || bookName.isEmpty() || bookAuthor.isEmpty() || bookSection.isEmpty() 
                    ) {
                url = "/createRecord.jsp";
            } else {
                // insert this data record into the database
                DAOSQLite.createRecord(book, dbPath);
                url = "/home.html";
            }

        } else if (action.equalsIgnoreCase("report")) {
            System.out.println("controller:report");
            String bookName = request.getParameter("name");
            if (bookName == null || bookName.isEmpty()) {
                bookName = "%";
            }
            String bookAuthor = request.getParameter("author");
            String bookSection = request.getParameter("section");
            String iSBN = request.getParameter("iSBN");
            String copyrightYear = request.getParameter("copyrightYear");
            List<Book> mydata = DAOSQLite.retrieveAllRecords(dbPath);
            request.setAttribute("bookName", bookName);
            request.setAttribute("bookAuthor", bookAuthor);
            request.setAttribute("bookSection", bookSection);
            request.setAttribute("iSBN", iSBN);
            request.setAttribute("copyrightYear", copyrightYear);
            request.setAttribute("mydata", mydata);
            url = "/showRecords.jsp";

        } else if (action.equalsIgnoreCase("deleteRecord")) {
            System.out.println("controller:deleteRecord");
            String nameString = request.getParameter("name");
            if (nameString == null || nameString.isEmpty()) {
                url = "/deleteRecord.jsp";
            } else {
                DAOSQLite.deleteRecord(nameString, dbPath);
                url = "/home.html";
            }

        } else if (action.equalsIgnoreCase("makeDB")) {
            System.out.println("controller:makeDB");
            DAOSQLite.dropTable(dbPath);
            DAOSQLite.createTable(dbPath);
            DAOSQLite.populateTable(dbPath);
            url = "/home.html";
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);

        dispatcher.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Controller for Employee App";
    }// </editor-fold>

}
