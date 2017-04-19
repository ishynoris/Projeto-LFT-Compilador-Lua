package models;

import jflex.Token;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.text.*;

@SuppressWarnings("serial")
public class ProjectTabbedPaneModel extends JTabbedPane {

    private int quantAbas = 0;
    private ArrayList<JTextPane> sourceCodes;

    public ProjectTabbedPaneModel() {

        this.setPreferredSize(new Dimension(0, 400));
        sourceCodes = new ArrayList<>();
    }

    public String getSourceCode(int index) {
        return getTabPaneProject(index).getText();
    }

    public JTextPane getTabPaneProject(int index){
        return sourceCodes.get(index);
    }

    public void addConsole(JTextPane txtPane){

        addTab("", null, new JScrollPane(txtPane), "");
        setTabComponentAt(quantAbas, new TitleTab("Console"));
        setSelectedIndex(quantAbas);
        quantAbas++;
    }

    public void newTab(SourceCodeModel code) {

        JTextPane txtPane = new JTextPane();
        txtPane.setText(code.getSourceCode());
        formatDefaultSourceCode(txtPane);

        sourceCodes.add(txtPane);

        JScrollPane scr = new JScrollPane();
        scr.setViewportView(sourceCodes.get(quantAbas));

        addTab("", null, scr, code.getPath());
        setTabComponentAt(quantAbas, new TitleTab(code.getTitle(), this));
        setSelectedIndex(quantAbas);
        quantAbas++;
    }

    public void formatDefaultSourceCode(JTextPane txtPane){

        Style normal = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
        StyleConstants.setFontFamily(normal, "Courier New");
        StyleConstants.setFontSize(normal, 12);
        txtPane.getStyledDocument().setCharacterAttributes(0, txtPane.getText().length(), normal, true);
    }

    public void formatDefaultSourceCode(JTextPane txtPane, String out,
                                        boolean bold, boolean italic, boolean underline){

        Style normal = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
        Style style = txtPane.getStyledDocument().addStyle("style", normal);
        StyleConstants.setBold(style, bold);
        StyleConstants.setItalic(style, italic);
        StyleConstants.setUnderline(style, underline);

        try {
            txtPane.getStyledDocument().insertString(txtPane.getText().length(), out, style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public void formatDefaultSourceCode(String name, JTextPane txtPane, Token token, Color color,
                                        boolean bold, boolean italic, boolean underline){

        Style normal = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
        StyledDocument styleDoc = txtPane.getStyledDocument();

        Style style = styleDoc.addStyle(name, normal);
        StyleConstants.setForeground(style, color);
        StyleConstants.setBold(style, bold);
        StyleConstants.setItalic(style, italic);
        StyleConstants.setUnderline(style, underline);

        styleDoc.setCharacterAttributes(token.getInitialPos(), token.length(), style, true);
    }

    public void uppdateTab(int indice, String titulo, String codigo) {

        sourceCodes.get(indice).setText(codigo);
        setTabComponentAt(indice, new TitleTab(titulo, ProjectTabbedPaneModel.this));
        setSelectedIndex(indice);
    }

    private void removeTab(int i) {
        remove(i);
        sourceCodes.remove(i);
        quantAbas--;
    }

    private class TitleTab extends JPanel {

        TitleTab (String title){
            super(new FlowLayout(FlowLayout.LEADING, 0, 0));
            setOpaque(false);

            add(new JLabel(title));
        }

        TitleTab(String titulo, ProjectTabbedPaneModel pane) {

            super(new FlowLayout(FlowLayout.LEADING, 0, 0));
            setOpaque(false);

            Icon icon = new ImageIcon(getClass().getResource("/img/icon_tab.png"));
            JLabel lblIcon = new JLabel(icon);
            lblIcon.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
            add(lblIcon);

            JLabel label = new JLabel(titulo);
            label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
            add(label);

            JButton button = new CloseButton(pane, this);
            add(button);
            setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
        }
    }

    private class CloseButton extends JButton implements ActionListener {

        private final int MAX_COL = 17;
        private final int MAX_LIN = 17;
        private ProjectTabbedPaneModel pane;
        private TitleTab tab;

        CloseButton(ProjectTabbedPaneModel pane, TitleTab tab) {
            this.pane = pane;
            this.tab = tab;

            setPreferredSize(new Dimension(MAX_COL, MAX_LIN));
            setToolTipText("Fechar aba");

            setContentAreaFilled(false);

            setRolloverEnabled(true);
            addActionListener(this);
        }

        public void actionPerformed(ActionEvent e) {

            int i = pane.indexOfTabComponent(tab);
            if (i != -1) {
                pane.removeTab(i);
            }
        }

        public void updateUI() {
            setUI(new BasicButtonUI());
        }

        protected void paintComponent(Graphics g) {

            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();

            if (getModel().isPressed()) {
                g2.translate(-1, -1);
            }

            if (getModel().isRollover()) {
                printRect(g2, Color.RED);
                printX(g2, Color.WHITE);
            } else {
                printX(g2, Color.GRAY);
            }
            g.dispose();
        }

        private void printRect(Graphics2D g2d, Color c){
            g2d.setColor(c);
            g2d.fillRect(0, 0, MAX_COL, MAX_LIN);
        }

        private void printX(Graphics2D g2, Color c){
            g2.setColor(c);

            g2.drawLine(6, 6, MAX_COL - 6, MAX_LIN - 6);
            g2.drawLine(6, 7, MAX_COL - 7, MAX_LIN - 6);
            g2.drawLine(7, 6, MAX_COL - 6, MAX_LIN - 7);

            g2.drawLine(MAX_COL - 6, 6, 6, MAX_LIN - 6);
            g2.drawLine(MAX_COL - 7, 6, 6, MAX_LIN - 7);
            g2.drawLine(MAX_COL - 6, 7, 7, MAX_LIN - 6);
        }
    }
}