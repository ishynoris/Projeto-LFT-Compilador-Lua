package controllers;

import jflex.Parser;
import jflex.Token;
import models.*;
import views.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class CompilerController {

    private final String PROJECT_NAME = "[Projeto LFT] Compilador Lua";
    private final String WORKSPACE = "Workspace";

    private File defaultPath;
    private ViewCompiler viewParent;
    private ArrayList<ProjectModel> projects;
    private ProjectTreeModel treeModel;
    private ProjectTabbedPaneModel projectTabPane;
    private ProjectTabbedPaneModel consoleTabPane;
    private JTextPane txtAreaConsole;

    public CompilerController(ViewCompiler viewParent) {

        this.viewParent = viewParent;
        this.defaultPath = new File(PROJECT_NAME);
        this.projects = new ArrayList<>();

        this.treeModel = new ProjectTreeModel(projects);
        this.projectTabPane = new ProjectTabbedPaneModel();
        this.consoleTabPane = new ProjectTabbedPaneModel();
        this.txtAreaConsole = new JTextPane();

        createWorkspace(defaultPath);
        txtAreaConsole.setEditable(false);
    }

    public void newSourceCodeExample(){
        ProjectModel project = new ProjectModel(defaultPath, "Projeto Demo");
        projects.add(project);
        SourceCodeModel souceCode = project.newFile("Exemplo1" + SourceCodeModel.EXTENSION);
        addFileToProject(0, project, souceCode);
        updateTreeModel();
    }

    public void updateTreeModel() {
        treeModel.updateModel(projects);
        viewParent.updateTreeModel(treeModel);
    }

    public void updateProjectTabPane(JSplitPane pane) {
        pane.setTopComponent(projectTabPane);
    }

    public void updateConsoleTabPane(JSplitPane pane){
        consoleTabPane.addConsole(txtAreaConsole);
        pane.setBottomComponent(consoleTabPane);
    }

    public void newProject(JFrame frame) {

        ViewNewProject view = new ViewNewProject(frame, defaultPath);
        defaultPath = view.getDefaultPath(); //TODO: REFATORAR

        if(view.hasConfirm()) {
            String projectName = view.getProjectName();

            if (isValidTitle(projectName)) {

                projects.add(new ProjectModel(defaultPath, projectName));
                updateTreeModel();

            } else {
                Alerts.invalidProjectTitleError(frame, "O nome do projeto é invalido");
            }
        }
    }

    public void newFile(JFrame frame) {

        if(!treeModel.hasChild()){
            Alerts.hasNoChildError(frame,
                    "Ainda não foi criado nenhum projeto. Crie um projeto para realizar essa ação");
            return;
        }

        ViewNewFile view = new ViewNewFile(frame, nameProjects());
        if(view.hasConfirm()){

            int index = view.getIndex();
            String title = view.getTitleCode();

            if (isValidTitle(title)) {
                ProjectModel project = projects.get(index);
                SourceCodeModel code = project.newFile(title);

                addFileToProject(index, project, code);
                updateTreeModel();

            } else {
                Alerts.invalidFileTitleError(frame, "O nome do arquivo inserido é invalido");
            }
        }
    }

    public void importFile(JFrame frame) {

        if(!treeModel.hasChild()){
            Alerts.hasNoChildError(frame,
                    "Ainda não foi criado nenhum projeto. Crie um projeto para realizar essa ação");
            return;
        }

        ViewImportFile view = new ViewImportFile(frame, nameProjects());
        if(view.hasConfirm()) {

            int index = view.getIndex();
            JFileChooser chooser = new JFileChooser(defaultPath);
            chooser.setFileFilter(new FileNameExtensionFilter("Arquivos Lua (*.lua)", SourceCodeModel.EXTENSION));

            if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {

                defaultPath = chooser.getCurrentDirectory();
                File selectedFile = chooser.getSelectedFile();
                if (selectedFile.exists()) {

                    ProjectModel project = projects.get(index);
                    SourceCodeModel code = project.importFile(selectedFile);

                    addFileToProject(index, project, code);
                    updateTreeModel();
                }
            }
        }
    }

    public void save(JFrame frame) {
        saveAs(frame);
//		JOptionPane.showMessageDialog(null, "NÃO IMPLEMENTADO", "SALVAR",
//				JOptionPane.ERROR_MESSAGE);
    }

    public void saveAs(JFrame frame) {
        if(!treeModel.hasChild()){
            Alerts.hasNoChildError(frame,
                    "Ainda não foi criado nenhum projeto. Crie um projeto para realizar essa ação");
            return;
        }

        JFileChooser diretorioSaida = new JFileChooser(defaultPath);

        diretorioSaida.setFileFilter(new FileNameExtensionFilter(
                "Arquivos Lua (*.lua)", SourceCodeModel.EXTENSION));

        if (diretorioSaida.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

            String nomeArquivo = diretorioSaida.getSelectedFile().getName() + "." + SourceCodeModel.EXTENSION;
            try {

                defaultPath = diretorioSaida.getCurrentDirectory();
                File arquivoSalvo = new File(defaultPath, nomeArquivo);
                BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoSalvo));

                int index = projectTabPane.getSelectedIndex();
                String sourceCode = projectTabPane.getSourceCode(index);

                projectTabPane.uppdateTab(index, nomeArquivo, sourceCode);

                bw.write(sourceCode);
                bw.close();

            } catch (IOException e) {
				e.printStackTrace();
            }
        }
    }

    public void compile() {

        Parser parser = new Parser();
        int index = projectTabPane.getSelectedIndex();
        String out = parser.lexical(projectTabPane.getSourceCode(index));
        ArrayList<Token> tokens = parser.getTokens();

        new Thread(()->{
            formatSourceCode(index, projectTabPane, tokens);
        }).start();

        new Thread(()->{
            showConsole(out);
        }).start();
    }

    public void more() {
        JOptionPane.showMessageDialog(null, "NÃO IMPLEMENTADO", "MAIS",
                JOptionPane.ERROR_MESSAGE);
    }

    public void about() {
        JOptionPane.showMessageDialog(null, "NÃO IMPLEMENTADO", "SOBRE",
                JOptionPane.ERROR_MESSAGE);
    }

    private void createWorkspace(File path){
        File newPath = new File(path, WORKSPACE);
        if(!newPath.exists()){
            newPath.mkdir();
        }
        defaultPath = newPath;
    }

    private boolean isValidTitle(String title) {
        return title != null && !title.matches(" *") && !title.equals("");
    }

    private String[] nameProjects(){

        int nProjects = projects.size();

        String [] names = new String [nProjects];
        for (int i = 0; i < nProjects; i++){
            names[i] = projects.get(i).simpleName();
        }
        return  names;
    }

    private void addFileToProject(int index, ProjectModel project, SourceCodeModel code){
        projectTabPane.newTab(code);
        projects.set(index, project);
    }

    private void showConsole(String out){
        if(!Objects.equals(txtAreaConsole.getText(), "")){
            txtAreaConsole.setText("");
        }
        //txtAreaConsole.setText(out);
        consoleTabPane.formatDefaultSourceCode(txtAreaConsole, out, true, true, true);
    }

    private void formatSourceCode(int index, ProjectTabbedPaneModel tabPane, ArrayList<Token> tokens){

        JTextPane txtPane = tabPane.getTabPaneProject(index);
        tabPane.formatDefaultSourceCode(txtPane);

        for(Token token: tokens){
            if(token.isKeyword()){

                tabPane.formatDefaultSourceCode("keyword", txtPane, token, Color.BLUE, true, false, false);

            } else if(token.isComent()){

                tabPane.formatDefaultSourceCode("coments", txtPane, token, Color.GRAY, false, false, false);

            } else if(token.isLiteral()){

                tabPane.formatDefaultSourceCode("literal", txtPane, token, Color.GREEN, true, false, false);

            } else if(token.isNumber()){

                tabPane.formatDefaultSourceCode("number", txtPane, token, Color.BLUE, false, false, false);

            } else if(token.isUnexpected()){

                tabPane.formatDefaultSourceCode("error", txtPane, token, Color.RED, true, false, true);
            }
        }
    }
}
