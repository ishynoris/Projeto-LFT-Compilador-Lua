package models;


import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Formatter;

public class SourceCodeModel {
    private File currentFile;

    public static final String EXTENSION = ".lua";

    SourceCodeModel(File parent, File oldFile){
        this.currentFile = createFile(parent, fileNameWithoutExtension(oldFile.getName()));
        importFile(oldFile, currentFile);
    }

    SourceCodeModel(File parent, String title) {
        this.currentFile = createFile(parent, title);
        write(currentFile, defaultCode());
    }

    public String getTitle(){
        return currentFile.getName();
    }

    public String getSourceCode(){
        return readFile();
    }

    public String getPath(){
        return currentFile.getAbsolutePath();
    }

    private File createFile(File parent, String title){

        File file = new File(parent, title);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    private String readFile(){
        try {
            BufferedReader br = new BufferedReader(new FileReader(currentFile));

            String line;
            String sourceCode = "";

            while ((line = br.readLine()) != null) {
                sourceCode += line + "\n";
            }
            br.close();
            return sourceCode;

        } catch (IOException ioExp) {
            ioExp.printStackTrace();
        }
        return null;
    }

    private void write(File file, String sourceCode){

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(sourceCode);
            bw.flush();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void importFile(File oldFile, File newFile){

        FileChannel oldChannel;
        FileChannel newChannel;
        try {
            oldChannel = new FileInputStream(oldFile).getChannel();
            newChannel = new FileOutputStream(newFile).getChannel();
            oldChannel.transferTo(0, oldChannel.size(), newChannel);

            oldChannel.close();
            newChannel.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private String defaultCode(){
        final String TAB = "\t";
        Formatter out = new Formatter();
        out.format("%s%s%s%s%s",
                "-- Author:\n",
                "--[[This code was generated automatically]]\n\n",
                "function example()\n",
                TAB + "print \"Hello, world!\"\n",
                "end");
        return out.toString();
    }

    private String fileNameWithoutExtension(String completeName){
        return completeName.substring(0, completeName.lastIndexOf("."));
    }

    @Override
    public String toString() {
        return this.currentFile.getName();
    }
}