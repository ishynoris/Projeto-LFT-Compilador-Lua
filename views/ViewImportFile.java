package views;

import controllers.ImportFileController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class ViewImportFile {

    private JDialog dialog;
    private JComboBox<Object> cmbProjeto;
    private JButton btnConfirm, btnCancel;
    private ImportFileController controller;



    public ViewImportFile(JFrame frame, String[] projetos) {
        dialog = new JDialog(frame, "Importar arquivo", true);
        cmbProjeto = new JComboBox<>(projetos);
        btnConfirm = new JButton("Ok");
        btnCancel = new JButton("Cancelar");
        controller = new ImportFileController(dialog, cmbProjeto);

        configButtons();
        configLayout(frame);
    }

    public int getIndex(){
        return controller.getIndex();
    }
    public boolean hasConfirm(){
        return controller.hasConfirm();
    }

    private void configButtons() {

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Object source = e.getSource();
                if (source.equals(btnConfirm)) {

                    controller.confirm();

                } else if (source.equals(btnCancel)) {

                    controller.cancel();
                }
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
        componentsLayout.rowHeights = new int[]{0, 0};
        componentsLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        componentsLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        mainPanel.setLayout(componentsLayout);

        GridBagConstraints constLblProjects = new GridBagConstraints();
        constLblProjects.gridx = 0;
        constLblProjects.gridy = 0;
        constLblProjects.insets = new Insets(0, 0, 0, 5);
        constLblProjects.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Selecione o projeto"), constLblProjects);

        GridBagConstraints constCmbProjects = new GridBagConstraints();
        constCmbProjects.fill = GridBagConstraints.HORIZONTAL;
        constCmbProjects.gridx = 1;
        constCmbProjects.gridy = 0;
        mainPanel.add(cmbProjeto, constCmbProjects);

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
        dialog.setPreferredSize(new Dimension(450, 170));
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }
}