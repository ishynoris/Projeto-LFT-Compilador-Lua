package controllers;

import interfaces.IController;
import models.SourceCodeModel;

import javax.swing.*;

public class NewFileController implements IController {

    private JDialog dialog;
    private int index;
    private String title;
    private JComboBox  cmbProject;
    private JTextField txtFile;
    private boolean confirm;

    public NewFileController(JDialog dialog, JComboBox  cmbProject, JTextField txtFile){
        this.dialog = dialog;
        this.cmbProject = cmbProject;
        this.txtFile = txtFile;
        this.index = -1;
        this.title = "";
        this.confirm = false;
    }

    public String getTitle(){
        return title;
    }

    public boolean hasConfirm(){
        return confirm;
    }

    public int getIndex(){
        return index;
    }

    @Override
    public void confirm() {
        index = cmbProject.getSelectedIndex();
        title = txtFile.getText();
        if(!title.endsWith(SourceCodeModel.EXTENSION)){
            title += SourceCodeModel.EXTENSION;
        }
        confirm = true;
        cancel();
    }

    public void cancel(){
        dialog.dispose();
    }
}
