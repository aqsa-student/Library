
package librarymanagementsystem1;

import java.util.ArrayList;
import javax.swing.JOptionPane;

class Student extends User {
    public Student(String username, String password) {
        super(username, password);
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

    public void issueBook(ArrayList<Book> books, String isbn) {
        for (Book book : books) {
            if (book.isbn.equals(isbn)) {
                books.remove(book);
                JOptionPane.showMessageDialog(null, "Book issued: " + book);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Book not found with ISBN: " + isbn);
    }

    public void returnBook(ArrayList<Book> books, Book book) {
        books.add(book);
        JOptionPane.showMessageDialog(null, "Book returned: " + book);
    }
}
