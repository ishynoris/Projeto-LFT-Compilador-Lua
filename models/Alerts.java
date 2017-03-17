package models;

import javax.swing.*;
import java.awt.*;

public class Alerts {

    public static void hasNoChildError(Component c, String msg){
        JOptionPane.showMessageDialog(c, msg, "Nenhum projeto criado", JOptionPane.ERROR_MESSAGE);
    }

    public static void invalidProjectTitleError(Component c, String msg){
        JOptionPane.showMessageDialog(c, msg, "Erro ao criar o projeto", JOptionPane.ERROR_MESSAGE);
    }

    public static void invalidFileTitleError(Component c, String msg){
        JOptionPane.showMessageDialog(c, msg, "Erro ao criar o arquivo", JOptionPane.ERROR_MESSAGE);
    }
}
