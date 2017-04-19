package views;

import controllers.NewProjectController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class ViewNewProject {

    private File defaultPath;
    private JDialog dialog;
    private JTextField txtWorkspace, txtProject;
    private JButton btnWorkspace, btnConfirm, btnCancel;
    private NewProjectController controller;

    public ViewNewProject(JFrame frame, File defaultPath) {

        this.defaultPath = defaultPath;
        this.dialog = new JDialog(frame, "Novo projeto", true);
        this.txtWorkspace = new JTextField(defaultPath.getAbsolutePath());
        this.txtProject = new JTextField();
        this.btnWorkspace = new JButton("...");
        this.btnConfirm = new JButton("Ok");
        this.btnCancel = new JButton("Cancel");
        this.controller = new NewProjectController(dialog, txtProject);

        configButtons();
        configLayout(frame);
    }

    public String getProjectName(){
        return controller.getProjectName();
    }

    public boolean hasConfirm(){
        return controller.hasConfirm();
    }

    public File getDefaultPath(){
        return defaultPath;
    }

    private void updateDefaultPath(File path){
        txtWorkspace.setText(path.getAbsolutePath());
        defaultPath = path;
    }

    private void configButtons() {

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Object source = e.getSource();
                if (source.equals(btnWorkspace)) {

                    File newPath = controller.updateWorkspace(dialog, defaultPath);
                    updateDefaultPath(newPath);

                } else if (source.equals(btnConfirm)) {

                    controller.confirm();

                } else if (source.equals(btnCancel)) {

                    controller.cancel();
                }
            }
        };
        btnWorkspace.addActionListener(listener);
        btnConfirm.addActionListener(listener);
        btnCancel.addActionListener(listener);
    }

    private void configLayout(JFrame frame) {

        txtWorkspace.setEditable(false);

        GridBagLayout mainLayout = new GridBagLayout();
        mainLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        mainLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};

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
        componentsLayout.columnWidths = new int[]{0, 0, 0, 0};
        componentsLayout.rowHeights = new int[]{0, 0, 0};
        componentsLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
        componentsLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        mainPanel.setLayout(componentsLayout);

        GridBagConstraints constLblWorkspace = new GridBagConstraints();
        constLblWorkspace.insets = new Insets(0, 0, 5, 5);
        constLblWorkspace.anchor = GridBagConstraints.WEST;
        mainPanel.add(new JLabel("Workspace"), constLblWorkspace);

        GridBagConstraints constTxtWorkspace = new GridBagConstraints();
        constTxtWorkspace.insets = new Insets(0, 0, 5, 5);
        constTxtWorkspace.fill = GridBagConstraints.HORIZONTAL;
        constTxtWorkspace.gridx = 1;
        mainPanel.add(txtWorkspace, constTxtWorkspace);

        GridBagConstraints constBtnWorkspace = new GridBagConstraints();
        constBtnWorkspace.insets = new Insets(0, 0, 5, 0);
        constBtnWorkspace.gridx = 2;
        mainPanel.add(btnWorkspace, constBtnWorkspace);

        GridBagConstraints constLblProject = new GridBagConstraints();
        constLblProject.anchor = GridBagConstraints.WEST;
        constLblProject.insets = new Insets(0, 0, 0, 5);
        constLblProject.gridy = 1;
        mainPanel.add(new JLabel("Nome do projeto"), constLblProject);

        GridBagConstraints constTxtProject = new GridBagConstraints();
        constTxtProject.insets = new Insets(0, 0, 0, 5);
        constTxtProject.fill = GridBagConstraints.HORIZONTAL;
        constTxtProject.gridx = 1;
        constTxtProject.gridy = 1;
        mainPanel.add(txtProject, constTxtProject);
        txtProject.setColumns(10);

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
        dialog.setPreferredSize(new Dimension(400, 200));
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }
}
