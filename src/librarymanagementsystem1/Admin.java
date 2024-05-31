
package librarymanagementsystem1;

import java.util.ArrayList;
import javax.swing.JOptionPane;

class Admin extends User {
    public Admin(String username, String password) {
        super(username, password);
    }

    public void addBook(ArrayList<Book> books, Book book) {
        books.add(book);
        JOptionPane.showMessageDialog(null, "Book added: " + book);
    }

    public void removeBook(ArrayList<Book> books, String isbn) {
        books.removeIf(book -> book.isbn.equals(isbn));
        JOptionPane.showMessageDialog(null, "Book removed with ISBN: " + isbn);
    }

    public void updateBook(ArrayList<Book> books, String isbn, String newTitle, String newAuthor) {
        for (Book book : books) {
            if (book.isbn.equals(isbn)) {
                book.title = newTitle;
                book.author = newAuthor;
                JOptionPane.showMessageDialog(null, "Book updated: " + book);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Book not found with ISBN: " + isbn);
    }

    @Override
    public void searchBook(ArrayList<Book> books, String searchTerm, String searchType) {
        for (Book book : books) {
            if ((searchType.equals("title") && book.title.equalsIgnoreCase(searchTerm)) ||
                (searchType.equals("isbn") && book.isbn.equals(searchTerm)) ||
                (searchType.equals("author") && book.author.equalsIgnoreCase(searchTerm))) {
                JOptionPane.showMessageDialog(null, book);
            }
        }
    }
}
