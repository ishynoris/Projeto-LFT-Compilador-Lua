package models;

import java.util.List;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class ProjectTreeModel implements TreeModel {

    private List<ProjectModel> projects;
    private String root = "Workspace";

    public ProjectTreeModel(List<ProjectModel> projects) {
        this.projects = projects;
    }

    public void updateModel(List<ProjectModel> projects) {
        this.projects = projects;
    }

    public boolean hasChild() {
        return getChildCount(root) != 0;
    }

    public Object getChild(Object parent, int index) {

        if (parent == root) {
            return projects.get(index);
        }

        if (parent instanceof ProjectModel) {
            return ((ProjectModel) parent).getCodes().get(index);
        }

        throw new IllegalArgumentException("Invalid parent class"
                + parent.getClass().getSimpleName());
    }

    @Override
    public int getChildCount(Object parent) {

        if (parent == root) {
            return projects.size();
        }

        if (parent instanceof ProjectModel) {
            return ((ProjectModel) parent).getCodes().size();
        }

        throw new IllegalArgumentException("Invalid parent class"
                + parent.getClass().getSimpleName());
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {

        if (parent == root) {
            return projects.indexOf(child);
        }

        if (parent instanceof ProjectModel) {
            return ((ProjectModel) parent).getCodes().indexOf(child);
        }
        return 0;
    }

    @Override
    public Object getRoot() {
        return root;
    }

    @Override
    public boolean isLeaf(Object node) {
        return node instanceof SourceCodeModel;
    }

    @Override
    public void removeTreeModelListener(TreeModelListener arg0) {}

    @Override
    public void addTreeModelListener(TreeModelListener arg0) {}

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {}
}