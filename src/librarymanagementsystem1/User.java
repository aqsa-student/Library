
package librarymanagementsystem1;

import java.util.ArrayList;

abstract class User {
    String username;
    String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public abstract void searchBook(ArrayList<Book> books, String searchTerm, String searchType);
}