package views;

import controllers.CompilerController;

import javax.swing.*;
import javax.swing.tree.TreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by anail on 03/03/2017.
 */
public class ViewCompiler implements ActionListener {
    //private int quantAbas;

    private CompilerController controller;

    private JFrame frame;
    private JMenuBar barMenu;
    private JMenu menuArquivo, menuNovo, menuEditar, menuAjuda;
    private JMenuItem mNewProject, mNewFile, mImportFile, mSave,
            mSaveAs, mExit, mCompile, mMore, mAbout;
    private JButton btnNewFile, btnNewProject, btnImportFile, btnSave, btnCompile;
    private JSplitPane sptPaneExterno, sptPaneInterno;
    private JScrollPane scrPaneTree;

    private JTree projectTree;
    private JToolBar barStatus, barBotoes;

    public ViewCompiler() {
        controller = new CompilerController(this);

        frame = new JFrame("JShark - Compilador de código Lua");
        barMenu = new JMenuBar();
        menuArquivo = new JMenu("Arquivo");
        menuNovo = new JMenu("Novo");
        menuEditar = new JMenu("Editar");
        menuAjuda = new JMenu("Ajuda");
        mNewProject = new JMenuItem("Projeto");
        mNewFile = new JMenuItem("Code fonte");
        mImportFile = new JMenuItem("Importar");
        mSave = new JMenuItem("Salvar");
        mSaveAs = new JMenuItem("Salvar como...");
        mExit = new JMenuItem("Sair");
        mCompile = new JMenuItem("Compilar");
        //mItemExtra = new JMenuItem("Extra");
        //mMore = new JMenuItem("Mais...");
        mAbout = new JMenuItem("Sobre");

        btnNewFile = new JButton();
        btnNewProject = new JButton();
        btnImportFile = new JButton();
        btnSave = new JButton();
        btnCompile = new JButton();

        sptPaneInterno = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        sptPaneExterno = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        projectTree = new JTree();
        barStatus = new JToolBar();
        barBotoes = new JToolBar();

        scrPaneTree = new JScrollPane();

        configMenus();
        configButtons();
        configLayout();
        controller.newSourceCodeExample();
    }

    public void updateTreeModel(TreeModel model) {
        projectTree = new JTree(model);
        scrPaneTree.setViewportView(projectTree);
        frame.repaint();
    }

    private void configMenus() {

        mNewProject.addActionListener(this);
        mNewFile.addActionListener(this);
        mImportFile.addActionListener(this);
        mSave.addActionListener(this);
        mSaveAs.addActionListener(this);
        mExit.addActionListener(this);
        mCompile.addActionListener(this);
        mAbout.addActionListener(this);

        menuArquivo.add(menuNovo);
        menuNovo.add(mNewProject);
        menuNovo.add(mNewFile);
        menuArquivo.add(mImportFile);
        menuArquivo.add(mSave);
        menuArquivo.add(mSaveAs);
        menuArquivo.add(mExit);
        menuEditar.add(mCompile);
        menuAjuda.add(mAbout);

        barMenu.add(menuArquivo);
        barMenu.add(menuEditar);
        barMenu.add(menuAjuda);

        frame.setJMenuBar(barMenu);
    }

    private void configButtons() {

        barBotoes.add(btnNewProject);
        barBotoes.add(btnNewFile);
        barBotoes.add(btnImportFile);
        barBotoes.add(btnSave);
        barBotoes.add(new JSeparator(JSeparator.VERTICAL));
        barBotoes.add(btnCompile);

        btnNewFile.addActionListener(this);
        btnNewProject.addActionListener(this);
        btnImportFile.addActionListener(this);
        btnSave.addActionListener(this);
        btnCompile.addActionListener(this);

        btnNewFile.setToolTipText("Criar novo código");
        btnNewProject.setToolTipText("Criar novo projeto");
        btnImportFile.setToolTipText("Importar codigo para projeto atual");
        btnSave.setToolTipText("Salvar projeto atual");
        btnCompile.setToolTipText("Compilar código fonte");

        btnNewFile.setIcon(new ImageIcon(getClass().getResource("/img/newCode.png")));
        btnNewProject.setIcon(new ImageIcon(getClass().getResource("/img/newProject.png")));
        btnImportFile.setIcon(new ImageIcon(getClass().getResource("/img/import.png")));
        btnSave.setIcon(new ImageIcon(getClass().getResource("/img/save.png")));
        btnCompile.setIcon(new ImageIcon(getClass().getResource("/img/compile.png")));
    }

    private void configLayout() {

        barStatus.setFloatable(false);
        barBotoes.setFloatable(false);

        //scrPaneCodigo.setPreferredSize(new Dimension(800, 800));
        scrPaneTree.setPreferredSize(new Dimension(200, 0));
        scrPaneTree.setViewportView(projectTree);

        sptPaneExterno.setLeftComponent(scrPaneTree);
        sptPaneExterno.setRightComponent(sptPaneInterno);
        sptPaneInterno.setResizeWeight(0.75);

        controller.updateProjectTabPane(sptPaneInterno);
        controller.updateConsoleTabPane(sptPaneInterno);
        controller.updateTreeModel();

        GroupLayout layout = new GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(sptPaneExterno, GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
                .addComponent(barStatus, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                .addComponent(barBotoes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addGap(6, 6, 6)
                .addComponent(barBotoes, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sptPaneExterno, GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(barStatus, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)));


        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/icon.png")));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 700));
        frame.setMinimumSize(new Dimension(600, 500));
        frame.pack();
        frame.setLocationRelativeTo(null);
    }
    @Override
    public void actionPerformed(ActionEvent evt) {

        Object source = evt.getSource();
        
        if (source.equals(mNewProject) || source.equals(btnNewProject)) {

            controller.newProject(frame);

        } else if (source.equals(mNewFile) || source.equals(btnNewFile)) {

            controller.newFile(frame);

        } else if (source.equals(mImportFile) || source.equals(btnImportFile)) {

            controller.importFile(frame);

        } else if (source.equals(mSave) || source.equals(btnSave)) {

            controller.save(frame);

        } else if (source.equals(mSaveAs)) {

            controller.saveAs(frame);

        } else if (source.equals(mCompile) || source.equals(btnCompile)) {

            controller.compile();
            /*

            if (tabPaneCodigo.getQuantAbas() == 0) {

                JOptionPane.showMessageDialog(null,
                        "Ainda não foi criado nenhum projeto.\n"
                                + "Para continuar, crie um novo projeto.",
                        "Nenhum projeto encontrado",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                String codigo = tabPaneCodigo.getSourceCode(
                        tabPaneCodigo.getSelectedIndex()).getText();
                if (codigo == "") {

                    JOptionPane.showMessageDialog(null,
                            "Nenhum código Java foi encontrado.",
                            "Nenhum código encontrado",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    controller.compile(codigo);
                }
            }
            */


        } else if (source.equals(mExit)) {

            int exit = JOptionPane.showConfirmDialog(null,
                    "Você realmente deseja sair?", "Sair", JOptionPane.YES_NO_OPTION);

            if (exit == JOptionPane.YES_OPTION) {
                System.exit(10);
            }
        } else if (source.equals(mMore)) {
            controller.more();
        } else if (source.equals(mAbout)) {
            controller.about();
        }
    }

    public static void main(String args[]) throws IOException {

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 

        new ViewCompiler();
    }
}
