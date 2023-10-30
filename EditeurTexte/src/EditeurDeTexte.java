import java.awt.font.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.*;

public class EditeurDeTexte extends JFrame implements ActionListener {
    Random ran = new Random();
    JMenuBar mb;
    JMenu m, m1, m2;
    JMenuItem mn, mi, mg, ms;
    JMenuItem mcop, mcor, mpeg;
    JMenuItem ayu, faq;
    static Scanner leer = new Scanner(System.in);
    Toolkit tk = getToolkit();
    Dimension dim = tk.getScreenSize();
    JPanel panel1, panel2;
    JComboBox cb, cbt;
    JScrollPane scroll;
    Font fuente, tam, fcur;
    JTextArea area = new JTextArea(5, 10);
    JButton neg, cur, subr, copier, coller, couper, colfon, collet;
    String[] vecfuentes = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    String texte;
    Color couleur;
    Cursor curseur;
    JLabel fond;
    ImageIcon i1, i2, i3, i4, i5;
    Icon ic1;
    Container cont = getContentPane();
    ImageIcon icf = new ImageIcon("/image/icono.png");
    int a = ran.nextInt(255), b = ran.nextInt(255), c = ran.nextInt(255);

    public EditeurDeTexte() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
        setTitle("Projet Wordpad");
        setIconImage(new ImageIcon(getClass().getResource("/image/icono1.png")).getImage());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(dim.width, dim.height);
        scroll = new JScrollPane(area);
        tam = new Font("Agency FB", Font.PLAIN, 9);
        area.setFont(tam);
        setLayout(new BorderLayout());
        barreSuperieure();
        panneau1();
        add(panel1, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        setResizable(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void barreSuperieure() {
        mb = new JMenuBar();
        setJMenuBar(mb);
        mb.setBorder(BorderFactory.createTitledBorder("BARRE D'OUTILS"));
        mb.setBackground(new Color(237, 237, 233));
        m = new JMenu("Fichier");
        mb.add(m);
        m1 = new JMenu("Édition");
        mb.add(m1);
        mn = new JMenuItem("Nouveau");
        m.add(mn);
        mn.addActionListener(this);
        mi = new JMenuItem("Ouvrir");
        m.add(mi);
        mi.addActionListener(this);
        mg = new JMenuItem("Enregistrer");
        m.add(mg);
        mg.addActionListener(this);
        ms = new JMenuItem("Quitter");
        m.add(ms);
        ms.addActionListener(this);
        mcop = new JMenuItem(new DefaultEditorKit.CopyAction());
        mcop.setText("Copier");
        m1.add(mcop);
        mcor = new JMenuItem(new DefaultEditorKit.CutAction());
        mcor.setText("Couper");
        m1.add(mcor);
        mpeg = new JMenuItem(new DefaultEditorKit.PasteAction());
        mpeg.setText("Coller");
        m1.add(mpeg);
        m2 = new JMenu("Aide");
        mb.add(m2);
        ayu = new JMenuItem("Info");
        m2.add(ayu);
        ayu.addActionListener(this);
        faq = new JMenuItem("FAQ");
        m2.add(faq);
        faq.addActionListener(this);
    }

    public void panneau1() {
        panel1 = new JPanel();
        panel1.setBorder(BorderFactory.createTitledBorder("Police"));
        panel1.setLayout(new FlowLayout());
        panel1.setBackground(new Color(237, 237, 233));
        cb = new JComboBox();
        for (String fu : vecfuentes) {
            cb.addItem(fu);
        }
        panel1.add(cb);
        cb.addActionListener(this);
        cbt = new JComboBox();
        for (int i = 9; i < 73; i++) {
            String a = String.valueOf(i);
            cbt.addItem(a);
        }
        panel1.add(cbt);
        cbt.addActionListener(this);
        neg = new JButton();
        neg.setIcon(new ImageIcon(this.getClass().getResource("/image/bold.png")));
        panel1.add(neg);
        neg.addActionListener(this);
        cur = new JButton();
        cur.setIcon(new ImageIcon(this.getClass().getResource("/image/italic.png")));
        panel1.add(cur);
        cur.addActionListener(this);
        subr = new JButton();
        subr.setIcon(new ImageIcon(this.getClass().getResource("/image/under.png")));
        panel1.add(subr);
        subr.addActionListener(this);
        coller = new JButton(new DefaultEditorKit.PasteAction());
        coller.setText("Coller");
        coller.setIcon(new ImageIcon(this.getClass().getResource("/image/paste3.png")));
        panel1.add(coller);
        copier = new JButton(new DefaultEditorKit.CopyAction());
        copier.setText("Copier");
        copier.setIcon(new ImageIcon(this.getClass().getResource("/image/copy2.png")));
        panel1.add(copier);
        couper = new JButton(new DefaultEditorKit.CutAction());
        couper.setText("Couper");
        couper.setIcon(new ImageIcon(this.getClass().getResource("/image/cut1.png")));
        panel1.add(couper);
        colfon = new JButton("Couleur d'arrière-plan");
        colfon.setIcon(new ImageIcon(this.getClass().getResource("/image/col.png")));
        panel1.add(colfon);
        colfon.addActionListener(this);
        collet = new JButton("Couleur");
        collet.setIcon(new ImageIcon(this.getClass().getResource("/image/letc.png")));
        panel1.add(collet);
        collet.addActionListener(this);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    String ouvrirFichier() {
        String aux = "";
        texte = "";
        try {
            JFileChooser file = new JFileChooser();
            file.showOpenDialog(this);
            File abre = file.getSelectedFile();
            if (abre != null) {
                FileReader fichiers = new FileReader(abre);
                BufferedReader lee = new BufferedReader(fichiers);
                while ((aux = lee.readLine()) != null) {
                    texte += aux + "\n";
                }
                lee.close();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex + ""
                            + "\nFichier introuvable",
                    "Avertissement", JOptionPane.WARNING_MESSAGE);
        }
        return texte;
    }

    void exporter() {
        try {
            JFileChooser fichier = new JFileChooser(System.getProperty("user.dir"));
            fichier.showSaveDialog(this);
            if (fichier.getSelectedFile() != null) {
                try (FileWriter sauvegarde = new FileWriter(fichier.getSelectedFile() + ".txt")) {
                    sauvegarde.write(area.getText());
                    JOptionPane.showMessageDialog(rootPane, "Le fichier a été enregistré avec succès dans le chemin défini");
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    public void panneau2() {
        panel2 = new JPanel();
    }
    int b1 = 0;
    int b2 = 0;
    int b3 = 0;

    @Override
    public void actionPerformed(ActionEvent e) {

        String let = (String) cb.getSelectedItem();
        String le = (String) cbt.getSelectedItem();
        int t = Integer.parseInt(le);
        if (cb.getSelectedItem().equals(let)) {
            tam = new Font(let, Font.PLAIN, t);
            area.setFont(tam);
        }
        if (cbt.getSelectedItem().equals(le)) {
            fuente = new Font(let, Font.PLAIN, t);
            area.setFont(fuente);
        }
        if (e.getSource() == neg) {
            if (b1 == 0) {
                tam = new Font(let, Font.BOLD, t);
                area.setFont(tam);
                b1 = 1;
            } else {
                tam = new Font(let, Font.PLAIN, t);
                area.setFont(tam);
                b1 = 0;
            }
        }
        if (e.getSource() == cur) {
            if (b2 == 0) {
                tam = new Font(let, Font.ITALIC, t);
                area.setFont(tam);
                b2 = 1;
            } else {
                tam = new Font(let, Font.PLAIN, t);
                area.setFont(tam);
                b2 = 0;
            }
        }
        if (e.getSource() == subr) {
            if (b3 == 0) {
                Map<TextAttribute, Object> attributs = (Map<TextAttribute, Object>) tam.getAttributes();
                attributs.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                area.setFont(tam.deriveFont(attributs));
                b3 = 1;
            } else {
                tam = new Font(let, Font.PLAIN, t);
                area.setFont(tam);
                b3 = 0;
            }
        }
        if (e.getSource() == mi) {
            String fichier = ouvrirFichier();
            area.setText(fichier);
        }
        if (e.getSource() == mn) {
            area.setText(null);
        }
        if (e.getSource() == ms) {
            System.exit(0);
        }
        if (e.getSource() == mg) {
            exporter();
        }
        if (e.getSource() == colfon) {
            JColorChooser selecteurCouleur = new JColorChooser();
            couleur = selecteurCouleur.showDialog(null, "Sélectionnez une couleur d'arrière-plan", Color.WHITE);
            area.setBackground(couleur);
        }
        if (e.getSource() == ayu) {
            JOptionPane.showMessageDialog(rootPane, "Créé par 2M2HAS", "Afficher les informations", JOptionPane.INFORMATION_MESSAGE);
        }
        if (e.getSource() == faq) {
            JOptionPane.showMessageDialog(rootPane, "Questions ou précisions, veuillez nous contacter sur Bensaltanahassan@gmail.com", "Des questions", JOptionPane.INFORMATION_MESSAGE);
        }
        if (e.getSource() == collet) {
            JColorChooser selecteurCouleur = new JColorChooser();
            couleur = selecteurCouleur.showDialog(null, "Sélectionnez une couleur de police", Color.WHITE);
            area.setForeground(couleur);
        }
    }

    public static void main(String[] args) {
        try {
            new EditeurDeTexte();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Une exception s'est produite" + e, "Exception", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
