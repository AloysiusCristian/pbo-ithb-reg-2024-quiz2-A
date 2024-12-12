package View;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Classes.User;
import DB.ConnectionManager;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Book {
    public static void showBookList(User user){
        ArrayList<Classes.Book> bookList = new ArrayList<Classes.Book>();

        String query = "SELECT * FROM books";
                try (PreparedStatement st = ConnectionManager.connect().prepareStatement(query)) {
                    try (ResultSet rs = st.executeQuery()) {
                        while (rs.next()) {
                            Classes.Book book = new Classes.Book(
                            rs.getInt("id"), 
                            rs.getInt("price"), 
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("genre"));
                            bookList.add(book);
                        } 
                    }
                } catch (SQLException er) {
                    JOptionPane.showMessageDialog(null, "Error", "Error", JOptionPane.ERROR_MESSAGE);
                    er.printStackTrace();
                }

        JFrame frame = new JFrame("List of Books");
        frame.setBounds(0, 0, 1600, 900);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0,3));
        frame.add(panel);
        for (Classes.Book book : bookList) {
            JPanel panelGrid = new JPanel();
            panelGrid.setLayout(new BoxLayout(panelGrid, BoxLayout.Y_AXIS));
            JLabel title = new JLabel(book.getTitle());
            title.setBounds(5, 5, 180, 30);
            panelGrid.add(title);
            JLabel author = new JLabel(book.getAuthor());
            author.setBounds(5, 40, 180, 30);
            panelGrid.add(author);
            JLabel genre = new JLabel(book.getGenre());
            genre.setBounds(5, 75, 180, 30);
            panelGrid.add(genre);
            JLabel price = new JLabel(Integer.toString(book.getPrice()));
            price.setBounds(5, 110, 180, 30);
            panelGrid.add(price);
            JButton buy = new JButton("Buy Book");
            buy.setBounds(5, 145, 100, 30);
            panelGrid.add(buy);
            buy.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String query = "SELECT * FROM transactions WHERE user_id = ? and book_id = ?";
                    try (PreparedStatement st = ConnectionManager.connect().prepareStatement(query)) {
                        st.setInt(1, user.getId());
                        st.setInt(2, book.getId());

                        try (ResultSet rs = st.executeQuery()) {
                            if (rs.next()) {
                                JOptionPane.showMessageDialog(panel, "Anda sudah membeli buku ini", "Beli Buku", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                String insertQuery = "INSERT INTO transactions(user_id, book_id) values (?, ?)";
                                try {
                                    PreparedStatement st2 = ConnectionManager.connect().prepareStatement(insertQuery);
                                    st2.setInt(1, user.getId());
                                    st2.setInt(2, book.getId());
            
                                    st2.execute();
                                } catch (SQLException er) {
                                    JOptionPane.showMessageDialog(panel, "Ada Error di Transaksi", "Error", JOptionPane.ERROR_MESSAGE);

                                }
                                JOptionPane.showMessageDialog(panel, "Buku berhasil dibeli", "Beli Buku", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    } catch (SQLException er) {
                        JOptionPane.showMessageDialog(panel, "Error", "Error", JOptionPane.ERROR_MESSAGE);
                        er.printStackTrace();
                    }
                    frame.dispose();
                }
            });
            panel.add(panelGrid);
        }
        frame.setVisible(true);
    }
}
