package views;

import controllers.NewFileController;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class ViewNewFile {

    private JDialog dialog;
    private JTextField txtFile;
    private JComboBox<String> cmbProject;
    private JButton btnConfirm, btnCancel;
    private NewFileController controller;

    public ViewNewFile(JFrame frame, String[] projects) {

        dialog = new JDialog(frame, "Novo arquivo", true);
        cmbProject = new JComboBox<>(projects);
        txtFile = new JTextField();
        btnConfirm = new JButton("Ok");
        btnCancel = new JButton("Cancelar");
        controller = new NewFileController(dialog, cmbProject, txtFile);

        configButtons();
        configLayout(frame);
    }

    public boolean hasConfirm(){
        return controller.hasConfirm();
    }

    public int getIndex() {
        return controller.getIndex();
    }

    public String getTitleCode() {
        return controller.getTitle();
    }

    private void configButtons() {

        ActionListener listener = (e) -> {
            if(e.getSource().equals(btnConfirm)){

                controller.confirm();

            } else if(e.getSource().equals(btnCancel)){

                controller.cancel();
            }
        };

        btnConfirm.addActionListener(listener);
        btnCancel.addActionListener(listener);
    }


    private void configLayout(JFrame frame) {

        GridBagLayout mainLayout = new GridBagLayout();
        mainLayout.columnWeights = new double[]{1.0};
        mainLayout.rowWeights = new double[]{0.0, 1.0, 0.0};

        Container container = dialog.getContentPane();
        container.setLayout(mainLayout);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new LineBorder(new Color(240, 240, 240), 15));
        GridBagConstraints constMainPanel = new GridBagConstraints();
        constMainPanel.anchor = GridBagConstraints.NORTH;
        constMainPanel.fill = GridBagConstraints.HORIZONTAL;
        constMainPanel.insets = new Insets(0, 0, 5, 0);
        container.add(mainPanel, constMainPanel);

        GridBagLayout componentsLayout = new GridBagLayout();
        componentsLayout.columnWidths = new int[]{0, 0, 0};
        componentsLayout.rowHeights = new int[]{0, 0, 0};
        componentsLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        componentsLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        mainPanel.setLayout(componentsLayout);

        GridBagConstraints constLblWorkspace = new GridBagConstraints();
        constLblWorkspace.gridx = 0;
        constLblWorkspace.gridy = 0;
        constLblWorkspace.insets = new Insets(0, 0, 5, 5);
        constLblWorkspace.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Projetos"), constLblWorkspace);

        GridBagConstraints gbc_comboBox = new GridBagConstraints();
        gbc_comboBox.insets = new Insets(0, 0, 5, 0);
        gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox.gridx = 1;
        gbc_comboBox.gridy = 0;
        mainPanel.add(cmbProject, gbc_comboBox);

        GridBagConstraints constLblProject = new GridBagConstraints();
        constLblProject.gridx = 0;
        constLblProject.anchor = GridBagConstraints.WEST;
        constLblProject.insets = new Insets(0, 0, 0, 5);
        constLblProject.gridy = 1;
        mainPanel.add(new JLabel("Arquivo"), constLblProject);

        GridBagConstraints constTxtProject = new GridBagConstraints();
        constTxtProject.fill = GridBagConstraints.HORIZONTAL;
        constTxtProject.gridx = 1;
        constTxtProject.gridy = 1;
        mainPanel.add(txtFile, constTxtProject);
        txtFile.setColumns(10);

        //Separator
        JSeparator separator = new JSeparator();
        GridBagConstraints constSeparator = new GridBagConstraints();
        constSeparator.anchor = GridBagConstraints.SOUTH;
        constSeparator.fill = GridBagConstraints.HORIZONTAL;
        constSeparator.insets = new Insets(0, 0, 5, 0);
        constSeparator.gridy = 1;
        container.add(separator, constSeparator);

        //Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(new LineBorder(new Color(240, 240, 240), 5));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        GridBagConstraints constButtonPane = new GridBagConstraints();
        constButtonPane.anchor = GridBagConstraints.NORTH;
        constButtonPane.fill = GridBagConstraints.HORIZONTAL;
        constButtonPane.gridy = 2;
        container.add(buttonPanel, constButtonPane);

        buttonPanel.add(btnConfirm);
        buttonPanel.add(btnCancel);

        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setPreferredSize(new Dimension(300, 200));
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }
}

