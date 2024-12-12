package View;

import Classes.User;
import DB.ConnectionManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Transaction {
    public static void showTransactions(User user){
        JFrame frame = new JFrame("Select KTP");
        frame.setBounds(400, 300, 640, 160);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        frame.add(panel);
        
        String query = "SELECT * FROM transactions inner join users as u on u.id = t.user_id inner join books as b on b.id = t.book_id where t.user_id = ?";
        try (PreparedStatement st = ConnectionManager.connect().prepareStatement(query)) {
            st.setInt(1, user.getId());

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                } else {
                    JOptionPane.showMessageDialog(panel, "Login gagal. Periksa email/password Anda", "Login", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException er) {
            JOptionPane.showMessageDialog(panel, "Error", "Error", JOptionPane.ERROR_MESSAGE);
            er.printStackTrace();
        }

    }
}
