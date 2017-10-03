import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Notepad implements ActionListener, WindowListener {
    JFrame f;
    JMenuBar mb;
    JMenu file, edit, format, view, help;
    JMenuItem New, open, saveas, save, print, exit, undo, cut, copy, paste, selectAll, find, replace, timedate,font;
    JTextArea ta;

    Notepad() {

        f = new JFrame();

        New = new JMenuItem("New");
        open = new JMenuItem("Open");
        saveas = new JMenuItem("Save As");
        save = new JMenuItem("Save");
        print = new JMenuItem("Print");
        exit = new JMenuItem("Exit");

        undo = new JMenuItem("Undo");
        cut = new JMenuItem("Cut");
        copy = new JMenuItem("Copy");
        paste = new JMenuItem("Paste");
        selectAll = new JMenuItem("Select All");
        find = new JMenuItem("Find");
        replace = new JMenuItem("Replace");
        timedate = new JMenuItem("Time & Date");

        font = new JMenuItem("Font");
        
        New.addActionListener(this);
        open.addActionListener(this);
        saveas.addActionListener(this);
        save.addActionListener(this);
        print.addActionListener(this);
        exit.addActionListener(this);

        undo.addActionListener(this);
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        selectAll.addActionListener(this);
        find.addActionListener(this);
        replace.addActionListener(this);
        timedate.addActionListener(this);

        mb = new JMenuBar();
        mb.setBounds(0, 0, 1000, 30);

        file = new JMenu("File");
        edit = new JMenu("Edit");
        format = new JMenu("Format");
        help = new JMenu("Help");
        view = new JMenu("View");

        file.add(New);
        file.add(open);
        file.add(save);
        file.add(saveas);
        file.add(print);
        file.add(exit);
        
        format.add(font);

        edit.add(undo);
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(selectAll);
        edit.add(find);
        edit.add(replace);
        edit.add(timedate);


        mb.add(file);
        mb.add(edit);
        mb.add(format);
        mb.add(view);
        mb.add(help);
        
        New.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,KeyEvent.CTRL_DOWN_MASK));
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,KeyEvent.CTRL_DOWN_MASK));
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.CTRL_DOWN_MASK));
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,KeyEvent.CTRL_DOWN_MASK));
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,KeyEvent.CTRL_DOWN_MASK));
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,KeyEvent.CTRL_DOWN_MASK));
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,KeyEvent.CTRL_DOWN_MASK));
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,KeyEvent.CTRL_DOWN_MASK));
        selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,KeyEvent.CTRL_DOWN_MASK));
        find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,KeyEvent.CTRL_DOWN_MASK));
        
        ta = new JTextArea();
        ta.setBounds(0, 30, 1000, 700);

        f.add(mb);
        f.add(ta);

        f.setLayout(null);
        f.setSize(1000, 700);
        f.setTitle("WotePad");
        f.setVisible(true);
        f.addWindowListener(this);
        //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cut)
            ta.cut();
        else if (e.getSource() == copy)
            ta.copy();
        else if (e.getSource() == paste)
            ta.paste();
        else if (e.getSource() == selectAll)
            ta.selectAll();
        else if (e.getSource() == timedate){
        	Date d = new Date();
        	SimpleDateFormat ff = new SimpleDateFormat("hh:mm dd/M/yyyy");
        	ta.setText("Current Time & Date : "+ff.format(d));
        }
        	

        else if (e.getSource() == New)
            newTextArea();
        else if (e.getSource() == open)
            openDialog();
        else if (e.getSource() == save)
            saveDialog();
        else if (e.getSource() == saveas)
            saveAsDialog();
        else if (e.getSource() == exit)
            exitWindow();

    }

    void newTextArea() {
        if (ta.getText().length() != 0) {
            int option = JOptionPane.showConfirmDialog(f, "Do you want to save the file?", "Close Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.NO_OPTION) {
                ta.setText("");
            } else {
                saveAsDialog();
                ta.setText("");
            }
        }
    }

    void openDialog() {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fc.showOpenDialog(f);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            ta.setText("");
            Scanner in = null;
            try { in = new Scanner(selectedFile);
                while ( in .hasNext()) {
                    String line = in .nextLine();
                    ta.append(line + "\n");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally { in .close();
            }
        }
    }

    void saveDialog() {
        JFileChooser fc = new JFileChooser();
        int result = fc.showSaveDialog(f);

        if (result == JFileChooser.APPROVE_OPTION) {
            File targetFile = fc.getSelectedFile();

            try {
                if (!targetFile.exists()) {
                    targetFile.createNewFile();
                }

                FileWriter fw = new FileWriter(targetFile);
                fw.write(ta.getText());
                fw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }


    void saveAsDialog() {
        JFileChooser fc = new JFileChooser();
        int result = fc.showSaveDialog(f);
        if (result == JFileChooser.APPROVE_OPTION) {
            File targetFile = fc.getSelectedFile();

            try {
                FileWriter fw = new FileWriter(targetFile);
                fw.write(ta.getText());
                fw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void exitWindow() {
        if (ta.getText().length() == 0)
            System.exit(0);
        else {
            int option = JOptionPane.showConfirmDialog(f, "Do you want to save the file ?", "Close Confirmation", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.NO_OPTION) {
                System.exit(0);
            } else if (option == JOptionPane.YES_OPTION) {
                saveAsDialog();
                System.exit(0);
            } else {
                f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }

        }
    }

    public void windowOpened(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    public void windowClosing(WindowEvent e) {
        int option = JOptionPane.showConfirmDialog(f, "Are you sure !", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        } else if (option == JOptionPane.NO_OPTION) {
            f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }
    }

    public void windowClosed(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    public void windowIconified(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    public void windowDeiconified(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    public void windowActivated(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    public void windowDeactivated(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    public static void main(String[] args) {
        new Notepad();
    }
}