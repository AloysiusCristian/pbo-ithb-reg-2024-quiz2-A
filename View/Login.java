package View;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Classes.User;
import DB.ConnectionManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Login {
    public static void login(){
        JFrame frame = new JFrame("Select KTP");
        frame.setBounds(400, 300, 640, 160);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        frame.add(panel);
        
        JLabel lEmail = new JLabel("Email");
        lEmail.setBounds(10, 5, 100, 30);
        JTextField email = new JTextField();
        email.setBounds(210, 5, 300, 30);
        panel.add(lEmail);
        panel.add(email);

        JLabel lPassword = new JLabel("Password");
        lPassword.setBounds(10, 40, 100, 30);
        JPasswordField password = new JPasswordField();
        password.setBounds(210, 40, 300, 30);
        panel.add(lPassword);
        panel.add(password);

        JButton login = new JButton("LOGIN");
        login.setBounds(400, 70, 100, 30);
        panel.add(login);
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = "SELECT * FROM users WHERE email = ? and password = ?";
                try (PreparedStatement st = ConnectionManager.connect().prepareStatement(query)) {
                    st.setString(1, email.getText());
                    st.setString(2, String.valueOf(password.getPassword()));

                    try (ResultSet rs = st.executeQuery()) {
                        if (rs.next()) {
                            User userLogin = new User(
                            rs.getInt("id"), 
                            rs.getString("name"), 
                            rs.getString("email"), 
                            rs.getString("password"));
                            frame.dispose();
                            Book.showBookList(userLogin);
                        } else {
                            JOptionPane.showMessageDialog(panel, "Login gagal. Periksa email/password Anda", "Login", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                } catch (SQLException er) {
                    JOptionPane.showMessageDialog(panel, "Error", "Error", JOptionPane.ERROR_MESSAGE);
                    er.printStackTrace();
                }
            }
        });

        frame.setVisible(true);
    }
}
