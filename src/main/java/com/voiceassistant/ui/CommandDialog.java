package com.voiceassistant.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

public class CommandDialog extends JDialog {

    private static final Color BACKGROUND = new Color(45, 45, 48);
    private static final Color FOREGROUND = new Color(241, 241, 241);
    private static final Color ACCENT = new Color(0, 122, 204);
    private static final Color FIELD_BG = new Color(30, 30, 30);
    private static final Color FIELD_BORDER = new Color(62, 62, 66);

    public CommandDialog(ConfigWindow parent, String key, String currentPath) {
        super(parent, key == null ? "Novo Comando" : "Editar Comando", true);

        getContentPane().setBackground(BACKGROUND);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 10));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel fieldsPanel = new JPanel(new GridLayout(4, 1, 5, 2));
        fieldsPanel.setBackground(BACKGROUND);

        JLabel lblFrase = new JLabel("Frase de voz:");
        lblFrase.setForeground(FOREGROUND);
        JTextField commandField = createStyledTextField();

        JLabel lblCaminho = new JLabel("Caminho completo do Executável:");
        lblCaminho.setForeground(FOREGROUND);
        JTextField pathField = createStyledTextField();

        if (key != null) commandField.setText(key);
        if (currentPath != null) pathField.setText(currentPath);

        fieldsPanel.add(lblFrase);
        fieldsPanel.add(commandField);

        JButton browseButton = createStyledButton("Procurar");

        browseButton.addActionListener(e -> openFileChooser(pathField));

        JPanel pathPanel = new JPanel(new BorderLayout(5, 0));
        pathPanel.setBackground(BACKGROUND);
        pathPanel.add(pathField, BorderLayout.CENTER);
        pathPanel.add(browseButton, BorderLayout.EAST);

        fieldsPanel.add(lblCaminho);
        fieldsPanel.add(pathPanel);

        mainPanel.add(fieldsPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(BACKGROUND);

        JButton saveBtn = createStyledButton("Salvar");
        saveBtn.setBackground(ACCENT);

        saveBtn.addActionListener(e -> {
            String path = pathField.getText().trim();

            if (commandField.getText().trim().isEmpty() || path.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
                return;
            }

            parent.updateCommand(commandField.getText().trim(), path);
            dispose();
        });

        buttonPanel.add(saveBtn);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void openFileChooser(JTextField targetField) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Selecionar executável");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        String current = targetField.getText();
        if (!current.isBlank()) {
            File f = new File(current);
            if (f.exists()) {
                chooser.setCurrentDirectory(f.getParentFile());
            }
        }

        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Executáveis (*.exe, *.bat, *.cmd, *.lnk)",
                "exe", "bat", "cmd", "lnk"
        ));

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            targetField.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(35);
        field.setBackground(FIELD_BG);
        field.setForeground(FOREGROUND);
        field.setCaretColor(FOREGROUND);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(FIELD_BORDER, 1),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return field;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);

        button.setBackground(new Color(62, 62, 66));
        button.setForeground(FOREGROUND);

        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);

        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(82, 82, 86), 1),
                BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));

        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

}