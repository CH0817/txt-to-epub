package tw.com.rex.txt2epub;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class TxtToEpubApplication {

    public static void main(String[] args) {
        JFrame frame = new JFrame("txt to EPUB");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        // frame.setSize(400, 200);

        JButton chooseFileBtn = new JButton("請選擇檔案");
        chooseFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileUpload();
            }
        });

        JTextArea area = new JTextArea();
        area.setText("text");

        frame.add(chooseFileBtn, BorderLayout.NORTH);
        frame.add(area, BorderLayout.NORTH);

        frame.setVisible(true);
        frame.pack();
    }

    private static void fileUpload() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false);
        FileNameExtensionFilter txtFileFilter = new FileNameExtensionFilter("", "txt");
        fileChooser.setFileFilter(txtFileFilter);
        int dialog = fileChooser.showOpenDialog(null);
        if (dialog == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println(selectedFile);
        }
    }

}
