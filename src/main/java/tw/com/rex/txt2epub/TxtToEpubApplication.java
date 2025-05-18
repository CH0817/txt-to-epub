package tw.com.rex.txt2epub;

import tw.com.rex.txt2epub.panel.MainPanel;

import javax.swing.*;
import java.awt.*;

public class TxtToEpubApplication {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.add(new MainPanel(), BorderLayout.CENTER);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("txt轉EPUB");
            frame.pack();
            frame.setVisible(true);
        });
    }

}
