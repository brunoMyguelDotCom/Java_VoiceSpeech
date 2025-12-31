package com.voiceassistant.ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class CommandDialog extends JDialog {

    private JTextField commandField;
    private JTextField pathField;

    public CommandDialog(Frame parent, String key, String currentPath) {
        super(parent, true);
        setTitle(key == null ? "Novo Comando" : "Editar Comando");

        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel main = new JPanel(new GridLayout(4, 1, 5, 5));
        main.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblCommand = new JLabel("Frase de voz:");
        commandField = new JTextField();

        JLabel lblPath = new JLabel("Caminho do executável:");
        pathField = new JTextField();

        if (key != null) commandField.setText(key);
        if (currentPath != null) pathField.setText(currentPath);

        main.add(lblCommand);
        main.add(commandField);
        main.add(lblPath);

        JPanel pathPanel = new JPanel(new BorderLayout(5, 5));
        JButton browse = new JButton("Procurar");
        browse.addActionListener(e -> chooseFile());

        pathPanel.add(pathField, BorderLayout.CENTER);
        pathPanel.add(browse, BorderLayout.EAST);

        main.add(pathPanel);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton save = new JButton("Salvar");

        save.addActionListener(e -> onSave());

        buttons.add(save);

        add(main, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        setSize(420, 220);
        setLocationRelativeTo(parent);
        setResizable(false);
        setVisible(true);
    }

    private void chooseFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (!pathField.getText().isBlank()) {
            File f = new File(pathField.getText());
            if (f.exists()) chooser.setCurrentDirectory(f.getParentFile());
        }

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            pathField.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void onSave() {
        String key = commandField.getText().trim();
        String path = pathField.getText().trim();

        if (key.isEmpty() || path.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }

        if (getParent() instanceof ConfigWindow window) {
            window.updateCommand(key, path);
        }

        dispose();
    }
}
