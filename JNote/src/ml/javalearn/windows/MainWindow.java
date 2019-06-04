package ml.javalearn.windows;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.LayerUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Scanner;

public class MainWindow extends JFrame {

    JList<String> jList;
    JScrollPane scrollList;
    JButton save;
    JButton newNote;
    JButton cancelChanges;
    JTextArea textArea = new JTextArea();
    JScrollPane scrollPane;
    JTextField nameNote = new JTextField(50);
    JPanel contentPanel;
    String[] arrayForJList;
    String getNameNote;
    String getTextNote;
    JLabel back = new JLabel(new ImageIcon("background.png"));

    public MainWindow() {
        init();
    }

    private void settingsFrame() {
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setTitle("JNote");
        setSize(new Dimension(600, 1035));
        setResizable(false);
        setVisible(true);
    }

    private void setLook() {

//        newNote.setBackground();
    }

    private void setContentPanel() {
        contentPanel = new JPanel();

        contentPanel.setLayout(null);
//        contentPanel.add(new PaintPanel());
        getContentPane().add(contentPanel);
    }

    private void setJList() {
        jList = new JList<String>(arrayForJList);
        scrollList = new JScrollPane(jList);

        scrollList.setBounds(10, 40, 145, 850);
        scrollList.setBorder(new LineBorder(Color.BLACK));
        scrollList.setFont(new Font("Arial", Font.PLAIN, 14));

        contentPanel.add(scrollList);
    }

    private void setButtons() {
        save = new JButton("✓"); //init save button
        cancelChanges = new JButton("✘"); //init delete button
        newNote = new JButton("➕"); //init create new note button

        save.setBackground(new Color(70, 117, 68));

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
                    createNewNote();
                    JOptionPane.showMessageDialog(null ,"File create successfully", "Information dialog", JOptionPane.INFORMATION_MESSAGE);

                    updateJList();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error (Note not create successfully)", "Information dialog", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    fileWriter();
                    JOptionPane.showMessageDialog(null, "Note saved successfully", "Information dialog", JOptionPane.INFORMATION_MESSAGE);

                    updateJList();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error (Note not save successfully", "Information dialog", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelChanges.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String setTitle = arrayForJList[jList.getSelectedIndex()];
                File file = new File(setTitle);
                System.out.println(setTitle);

                if (file.delete()) {
                    System.gc();
                    updateJList();
                    System.out.println("File deleted");
                } else {
                    System.gc();
                    System.out.println("error");
                }
            }
        });
    }

    private void createNewNote() throws IOException {
        getNameNote = nameNote.getText();

        FileWriter fileWriter = new FileWriter(getNameNote + ".txt");

        fileWriter.flush();
        fileWriter.close();
    }

    private void fileWriter() throws IOException {
        getNameNote = nameNote.getText();
        FileWriter fileWriter = new FileWriter(getNameNote + ".txt");

        getTextNote = textArea.getText();
        fileWriter.write(getTextNote);

        fileWriter.flush();
        fileWriter.close();

    }

    private void setScrollPane() {
        textArea.setText("Start writing...");
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        scrollPane = new JScrollPane(textArea); //init scroll pane and add in text area

        scrollPane.setBounds(165, 40, 410, 850);

        contentPanel.add(scrollPane);
    }

    private void setTextField() {
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

    private void updateJList() {
        getFilesForJList();
        DefaultListModel model = new DefaultListModel();
        for (int i = 0; i < arrayForJList.length; i++) {
            model.add(i, arrayForJList[i]);
        }
        jList.setModel(model);
    }

    private void listenerForList() {
        jList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (arrayForJList.length > 0) {
                    String setTitle = arrayForJList[jList.getSelectedIndex()];
                    nameNote.setText(setTitle);

                    try {
                        getTextFile(setTitle);
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    private void getTextFile(String setTitle) throws FileNotFoundException {
        File file = new File(setTitle);

        String txt = "";

        Scanner scanner = new Scanner(file);

        while (scanner.hasNext()) {
            txt += scanner.nextLine() + "\n";
        }

        textArea.setText(txt);
    }

    private void setBackground() {
        back.setBounds(-10, 0, 600, 1000);

        contentPanel.add(back);
    }

    private void init() {
        settingsFrame();
        setContentPanel();
        setJList();
        setButtons();
        setTextField();
        setScrollPane();
        actionListeners();
        listenerForList();
        updateJList();
        setBackground();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {}

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainWindow();
            }
        });
    }

}
