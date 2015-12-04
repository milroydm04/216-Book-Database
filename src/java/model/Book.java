package model;

import java.io.Serializable;

/**
 *
 * @author Dylan Lozo
 */
public class Book implements Serializable {

    private String bookName;
    private String bookAuthor;
    private String bookSection;
    private String iSBN;
    private int copyrightYear;

    public Book() {
    }

    public Book(String bookName, String bookAuthor, String bookSection, 
            String iSBN, int copyrightYear) {
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookSection = bookSection;
        this.iSBN = iSBN;
        this.copyrightYear = copyrightYear;
    }

    public String inHTMLRowFormat() {
        return "<tr><td>" + bookName + "</td>"
                + "<td>" + bookAuthor + "</td>"
                + "<td>" + bookSection + "</td>"
                + "<td>" + iSBN + "</td>"
                + "<td>" + copyrightYear + "</td></tr>\n";
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookSection() {
        return bookSection;
    }

    public void setBookSection(String bookSection) {
        this.bookSection = bookSection;
    }

    public String getiSBN() {
        return iSBN;
    }

    public void setiSBN(String iSBN) {
        this.iSBN = iSBN;
    }

    public int getCopyrightYear() {
        return copyrightYear;
    }

    public void setCopyrightYear(int copyrightYear) {
        this.copyrightYear = copyrightYear;
    }

    @Override
    public String toString() {
        return "Book{" + "bookName=" + bookName + ", bookAuthor=" + bookAuthor 
                + ", bookSection=" + bookSection + ", ISBN=" 
                + iSBN + ", Copyright Year=" + copyrightYear + '}';
    }

    
}
