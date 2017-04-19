package controllers;

import interfaces.IController;

import javax.swing.*;

/**
 * Created by anail on 09/03/2017.
 */
public class ImportFileController implements IController {

    private JDialog dialog;
    private JComboBox cmbProjects;
    private int index;
    private boolean confirm;

    public ImportFileController(JDialog dialog, JComboBox cmbProjects){
        this.dialog = dialog;
        this.cmbProjects = cmbProjects;
        this.index = -1;
        this.confirm = false;
    }

    public int getIndex() {
        return index;
    }

    public boolean hasConfirm(){
        return confirm;
    }

    public void confirm(){
        index = cmbProjects.getSelectedIndex();
        confirm = true;
        cancel();
    }

    public void cancel(){
        dialog.dispose();
    }
}
