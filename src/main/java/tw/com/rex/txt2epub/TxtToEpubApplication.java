package tw.com.rex.txt2epub;

import tw.com.rex.txt2epub.panel.MainPanel;

import javax.swing.*;
import java.awt.*;

public class TxtToEpubApplication {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int height = screenSize.height;
            int width = screenSize.width;

            JFrame frame = new JFrame();
            frame.add(new MainPanel(), BorderLayout.CENTER);
            frame.setSize(width / 2, height / 2);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("txt轉EPUB");
            frame.pack();
            frame.setVisible(true);
        });
    }

}
