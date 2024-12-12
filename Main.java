import DB.ConnectionManager;
import com.toedter.calendar.JCalendar;
import java.awt.Frame;
import java.awt.Panel;
public class Main {
    public static void main(String[] args) {
        ConnectionManager.connect();
        Frame myFrame = new Frame();
        myFrame.setBounds(0, 0, 1600, 900);
        Panel panel = new Panel();
        myFrame.add(panel);
        JCalendar calendar = new JCalendar();
        calendar.setBounds(310, 110, 300, 30);
        panel.add(calendar);
        myFrame.setVisible(true);
    }
}