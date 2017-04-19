package models;

import java.io.File;
import java.util.*;

public class ProjectModel {

    private File parent;
    private List<SourceCodeModel> codigos = new ArrayList<>();

    public ProjectModel(File root, String title) {
        parent = createFile(root, title);
    }

    public SourceCodeModel importFile(File oldFile){
        SourceCodeModel code = new SourceCodeModel(parent, oldFile);
        addFile(code);
        return code;
    }

    public SourceCodeModel newFile(String title){
        SourceCodeModel code = new SourceCodeModel(parent, title);
        addFile(code);
        return code;
    }

    public List<SourceCodeModel> getCodes() {
        return Collections.unmodifiableList(codigos);
    }

    private void addFile(SourceCodeModel code){
        codigos.add(code);
    }

    private File createFile(File parent, String title){

        File file = new File(parent, title);
        if(!file.exists()) {
            file.mkdir();
        }
        return file;
    }

    public String simpleName(){
        return parent.getName();
    }

    public String toString(){
        Formatter formatter = new Formatter();
        formatter.format("<html><b>%s</b> [%s]</html>", parent.getName(), parent.getAbsolutePath());
        return formatter.toString();
    }
}

