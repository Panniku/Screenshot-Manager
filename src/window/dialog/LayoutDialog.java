package window.dialog;

import javax.swing.*;

import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import org.kordamp.ikonli.swing.FontIcon;

import window.Window;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;

public class LayoutDialog extends JDialog {

    JPanel root, container;
    //
    JTextField layoutName;
    JButton drawLayout;
    JSpinner x1s, y1s, x2s, y2s;
    //
    JPanel buttonContainer, buttonPanel;
    JButton ok, cancel;
    
    static String name;
    
    //private boolean confirmed;

    public LayoutDialog(JFrame parent) {
        super(parent, "Add new Layout", true);
        setSize(200, 200);
        setResizable(false);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

        root = new JPanel(new BorderLayout());

        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        layoutName = new JTextField("foo");
        //layoutName.setFocusable(true);
        layoutName.setEditable(true);
        layoutName.setEnabled(true);
        layoutName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                layoutName.selectAll();
            }
        });
        setSizes(layoutName, 100, 20);

        drawLayout = new JButton("Draw Layout");
        setSizes(drawLayout, 100, 20);

        x1s = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        y1s = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        x2s = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        y2s = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));

        
        container.add(setLayoutToParent(new JLabel(), drawLayout));
        container.add(setLayoutToParent(new JLabel("x1"), x1s));
        container.add(setLayoutToParent(new JLabel("y1"), y1s));
        container.add(setLayoutToParent(new JLabel("x2"), x2s));
        container.add(setLayoutToParent(new JLabel("y2"), y2s));

        buttonContainer = new JPanel(new BorderLayout());
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        //
        ok = new JButton("OK");
        cancel = new JButton("Cancel");
        //

        buttonPanel.add(ok);
        buttonPanel.add(cancel);
        buttonContainer.add(buttonPanel, BorderLayout.EAST);

        root.add(container, BorderLayout.CENTER);
        root.add(buttonContainer, BorderLayout.SOUTH);
        setContentPane(root);

        layoutName.setFocusable(true);
        layoutName.requestFocusInWindow();

        pack();
        setLocationRelativeTo(parent);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                layoutName.requestFocusInWindow();
            }
        });
        
        setVisible(true);

    }

    private JComponent setLayoutToParent(JComponent item1, JComponent item2) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(item1, BorderLayout.WEST);
        panel.add(item2, BorderLayout.EAST);
        return panel;
    }

    private void setSizes(JComponent comp, int w, int h) {
        comp.setPreferredSize(new Dimension(w, h));
        comp.setMaximumSize(new Dimension(w, h));
    }
}
