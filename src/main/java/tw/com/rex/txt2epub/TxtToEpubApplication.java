package tw.com.rex.txt2epub;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import tw.com.rex.txt2epub.panel.MainPanel;

public class TxtToEpubApplication {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.add(new MainPanel(), BorderLayout.CENTER);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("txt轉EPUB");
            frame.pack();
            frame.setVisible(true);
        });
    }

}
