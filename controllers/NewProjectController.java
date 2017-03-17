package controllers;

import interfaces.IController;
import views.ViewNewProject;

import javax.swing.*;
import java.io.File;

public class NewProjectController implements IController{

    private final String WORKSPACE = "Workspace";

    private JDialog dialog;
    private JTextField txtProject;
    private String projectName;
    private boolean confirm;

    public NewProjectController(JDialog dialog, JTextField txtProject){
        this.dialog = dialog;
        this.txtProject = txtProject;
        this.projectName = null;
        this.confirm = false;
    }

    public String getProjectName(){
        return projectName;
    }
    public boolean hasConfirm(){
        return confirm;
    }

    public File updateWorkspace(JDialog dialog, File defaultPath){

        JFileChooser chooser = new JFileChooser(defaultPath);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if(chooser.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION){
            return createWorkspace(chooser.getSelectedFile());
        }
        return defaultPath;
    }

    private File createWorkspace(File path){
        File newPath = new File(path, WORKSPACE);
        if(!newPath.exists()){
            newPath.mkdir();
        }
        return newPath;
    }

    @Override
    public void confirm() {
        projectName = txtProject.getText();
        confirm = true;
        cancel();
    }

    public void cancel(){
        dialog.dispose();
    }
}
