
package librarymanagementsystem1;

import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibraryManagementSystem1 extends JFrame {
    private static final String USER_FILE = "users.txt";
    static ArrayList<Book> books = new ArrayList<>();
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JComboBox<String> roleComboBox;

    public LibraryManagementSystem1() {
        setTitle("Library Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2));
        add(panel);

        JLabel usernameLabel = new JLabel("Username:");
        panel.add(usernameLabel);

        usernameField = new JTextField();
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        panel.add(passwordField);

        JLabel roleLabel = new JLabel("Role:");
        panel.add(roleLabel);

        roleComboBox = new JComboBox<>(new String[]{"admin", "student"});
        panel.add(roleComboBox);

        loginButton = new JButton("Login");
        panel.add(loginButton);

        registerButton = new JButton("Register");
        panel.add(registerButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });
    }

    private void register() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String role = (String) roleComboBox.getSelectedItem();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE, true))) {
            writer.write(username + "," + password + "," + role);
            writer.newLine();
            JOptionPane.showMessageDialog(this, "Registration successful.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error registering user: " + e.getMessage());
        }
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            boolean userFound = false;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(",");
                if (userDetails.length >= 3 && userDetails[0].equals(username) && userDetails[1].equals(password)) {
                    userFound = true;
                    if (userDetails[2].equalsIgnoreCase("admin")) {
                        new AdminPanel(new Admin(username, password)).setVisible(true);
                    } else if (userDetails[2].equalsIgnoreCase("student")) {
                        new StudentPanel(new Student(username, password)).setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid role.");
                    }
                    this.dispose();
                    break;
                }
            }
            if (!userFound) {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error logging in: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LibraryManagementSystem1().setVisible(true);
            }
        });
    }
}

class StudentPanel extends JFrame {
    private Student student;
    private JTextField isbnField, searchField;

    public StudentPanel(Student student) {
        this.student = student;
        setTitle("Student Panel");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(8, 2));
        add(panel);

        JLabel isbnLabel = new JLabel("ISBN:");
        panel.add(isbnLabel);
        isbnField = new JTextField();
        panel.add(isbnField);

        JButton issueButton = new JButton("Issue Book");
        panel.add(issueButton);
        issueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                student.issueBook(LibraryManagementSystem1.books, isbnField.getText());
            }
        });

        JLabel returnTitleLabel = new JLabel("Title:");
        panel.add(returnTitleLabel);
        JTextField titleField = new JTextField();
        panel.add(titleField);

        JLabel returnIsbnLabel = new JLabel("ISBN:");
        panel.add(returnIsbnLabel);
        JTextField returnIsbnField = new JTextField();
        panel.add(returnIsbnField);

        JLabel returnAuthorLabel = new JLabel("Author:");
        panel.add(returnAuthorLabel);
        JTextField authorField = new JTextField();
        panel.add(authorField);

        JButton returnButton = new JButton("Return Book");
        panel.add(returnButton);
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                student.returnBook(LibraryManagementSystem1.books, new Book(titleField.getText(), returnIsbnField.getText(), authorField.getText()));
            }
        });

        JLabel searchLabel = new JLabel("Search:");
        panel.add(searchLabel);
        searchField = new JTextField();
        panel.add(searchField);

        JButton searchButton = new JButton("Search Book");
        panel.add(searchButton);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                student.searchBook(LibraryManagementSystem1.books, searchField.getText(), "title");
            }
        });

        JButton logoutButton = new JButton("Logout");
        panel.add(logoutButton);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LibraryManagementSystem1().setVisible(true); // Show main login window
                dispose(); // Close current window
            }
        });
    }
}

class AdminPanel extends JFrame {
    private Admin admin;
    private JTextField titleField, isbnField, authorField, searchField, newTitleField, newAuthorField;

    public AdminPanel(Admin admin) {
        this.admin = admin;
        setTitle("Admin Panel");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(10, 2));
        add(panel);

        JLabel titleLabel = new JLabel("Title:");
        panel.add(titleLabel);
        titleField = new JTextField();
        panel.add(titleField);

        JLabel isbnLabel = new JLabel("ISBN:");
        panel.add(isbnLabel);
        isbnField = new JTextField();
        panel.add(isbnField);

        JLabel authorLabel = new JLabel("Author:");
        panel.add(authorLabel);
        authorField = new JTextField();
        panel.add(authorField);

        JButton addButton = new JButton("Add Book");
        panel.add(addButton);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.addBook(LibraryManagementSystem1.books, new Book(titleField.getText(), isbnField.getText(), authorField.getText()));
            }
        });

        JLabel removeIsbnLabel = new JLabel("Remove ISBN:");
        panel.add(removeIsbnLabel);
        isbnField = new JTextField();
        panel.add(isbnField);

        JButton removeButton = new JButton("Remove Book");
        panel.add(removeButton);
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.removeBook(LibraryManagementSystem1.books, isbnField.getText());
            }
        });

        JLabel updateIsbnLabel = new JLabel("Update ISBN:");
        panel.add(updateIsbnLabel);
        isbnField = new JTextField();
        panel.add(isbnField);

        JLabel newTitleLabel = new JLabel("New Title:");
        panel.add(newTitleLabel);
        newTitleField = new JTextField();
        panel.add(newTitleField);

        JLabel newAuthorLabel = new JLabel("New Author:");
        panel.add(newAuthorLabel);
        newAuthorField = new JTextField();
        panel.add(newAuthorField);

        JButton updateButton = new JButton("Update Book");
        panel.add(updateButton);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.updateBook(LibraryManagementSystem1.books, isbnField.getText(), newTitleField.getText(), newAuthorField.getText());
            }
        });

        JLabel searchLabel = new JLabel("Search:");
        panel.add(searchLabel);
        searchField = new JTextField();
        panel.add(searchField);

        JButton searchButton = new JButton("Search Book");
        panel.add(searchButton);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.searchBook(LibraryManagementSystem1.books, searchField.getText(), "title");
            }
        });

        JButton logoutButton = new JButton("Logout");
        panel.add(logoutButton);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LibraryManagementSystem1().setVisible(true); // Show main login window
                dispose(); // Close current window
            }
        });
    }
}

