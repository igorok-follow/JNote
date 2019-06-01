package ml.javalearn.windows;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MainWindow extends JFrame {

    JList jList;
    JButton save;
    JButton newNote;
    JButton cancelChanges;
    JTextArea textArea;
    JScrollPane scrollPane;
    JTextField nameNote;
    JPanel contentPanel;
    String[] arrayForJList;
    String getNameNote = nameNote.getText();
    FileWriter fileCreator = new FileWriter(getNameNote + ".txt");

    public MainWindow() throws IOException {
        init();
    }

    private void settingsFrame() {
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setTitle("JNote");
        setSize(new Dimension(600, 940));
        setVisible(true);
    }

    private void setContentPanel() {
        contentPanel = new JPanel();

        contentPanel.setLayout(null);
        getContentPane().add(contentPanel);
    }

    private void setJList() {
        jList = new JList();
        jList.setBounds(10, 40, 145, 850);
        jList.setBorder(new LineBorder(Color.BLACK));

        contentPanel.add(jList);
    }

    private void setButtons() {
        save = new JButton("✓"); //init save button
        cancelChanges = new JButton("✘"); //init delete button
        newNote = new JButton("➕"); //init create new note button

        save.setBounds(10, 10, 45, 20); //save note button
        cancelChanges.setBounds(60  , 10, 45, 20);
        newNote.setBounds(110, 10, 45, 20);

        contentPanel.add(save);
        contentPanel.add(cancelChanges);
        contentPanel.add(newNote);
    }

    private void actionListeners() {
        newNote.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void setScrollPane() {
        textArea = new JTextArea();
        textArea.setText("Start writing...");
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        scrollPane = new JScrollPane(textArea); //init scroll pane and add in text area

        scrollPane.setBounds(165, 40, 410, 850);

        contentPanel.add(scrollPane);
    }

    private void setTextField() {
        nameNote = new JTextField(50); //init text field with note name

        nameNote.setBounds(165, 10, 410, 20);
        nameNote.setText("Untitled");
        nameNote.setFont(new Font("Arial", Font.PLAIN, 12));

        contentPanel.add(nameNote);
    }

    private void getFilesForJList() {
        String path = System.getProperty("user.dir");
        File folder = new File(path);

        arrayForJList = folder.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".txt");
            }
        });
    }

    private void creatorFiles() throws IOException {

//        PrintWriter fileCreator = new PrintWriter(filename + ".txt", "UTF-8");

        fileCreator.flush();
        fileCreator.close();
    }

    private void writerFiles() throws IOException {
        String getTextNote = textArea.getText();

        fileCreator.write(getTextNote);

        fileCreator.flush();
        fileCreator.close();
    }


    private void init() {
        settingsFrame();
        setContentPanel();
        setJList();
        setButtons();
        setTextField();
        setScrollPane();
        getFilesForJList();
        actionListeners();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {}

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new MainWindow();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
