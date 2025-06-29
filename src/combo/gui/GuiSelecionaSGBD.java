package combo.gui;

import javax.swing.*;
import java.awt.*;

public class GuiSelecionaSGBD extends JDialog {
    private String sgbdSelecionado = null;
    private JComboBox<String> comboSGBD;
    private JButton btnConfirmar;
    private JButton btnCancelar;

    public GuiSelecionaSGBD(Frame parent) {
        super(parent, "Selecione o SGBD", true);
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        // Painel principal com espaçamento
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Área de seleção
        JPanel selectionPanel = new JPanel(new GridLayout(4, 1, 5, 10));
        JLabel lblInstrucao = new JLabel("Selecione o Sistema Gerenciador de Banco de Dados:");
        lblInstrucao.setFont(new Font("Tahoma", Font.BOLD, 14));

        comboSGBD = new JComboBox<>(new String[] { "MySQL", "PostgreSQL" });
        comboSGBD.setFont(new Font("Tahoma", Font.PLAIN, 14));

        // Adiciona informações sobre os drivers
        JLabel lblDriver = new JLabel("Certifique-se de incluir o driver JDBC correto no classpath:");
        lblDriver.setFont(new Font("Tahoma", Font.ITALIC, 12));
        lblDriver.setForeground(new Color(128, 0, 0));

        JTextArea txtCommand = new JTextArea();
        txtCommand.setText("MySQL: java -cp \"bin;lib\\mysql-connector-j-9.3.0.jar\" combo.principal.Principal\n" +
                "PostgreSQL: java -cp \"bin;lib\\postgresql-42.7.7.jar\" combo.principal.Principal");
        txtCommand.setEditable(false);
        txtCommand.setFont(new Font("Consolas", Font.PLAIN, 11));
        txtCommand.setBackground(new Color(240, 240, 240));
        txtCommand.setBorder(BorderFactory.createEtchedBorder());

        selectionPanel.add(lblInstrucao);
        selectionPanel.add(comboSGBD);
        selectionPanel.add(lblDriver);
        selectionPanel.add(txtCommand);

        // Botões
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnConfirmar.setBackground(new Color(230, 230, 250));
        btnConfirmar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnCancelar.setBackground(new Color(230, 230, 250));
        btnCancelar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));

        btnConfirmar.addActionListener(e -> {
            sgbdSelecionado = (String) comboSGBD.getSelectedItem();

            // Verificar se o driver está disponível
            boolean driverDisponivel = false;
            String driverClass = sgbdSelecionado.equals("MySQL") ? "com.mysql.cj.jdbc.Driver" : "org.postgresql.Driver";

            try {
                Class.forName(driverClass);
                driverDisponivel = true;
            } catch (ClassNotFoundException ex) {
                driverDisponivel = false;
            }

            // Avisar se o driver não estiver disponível
            if (!driverDisponivel) {
                String mensagem = "O driver JDBC para " + sgbdSelecionado + " não foi encontrado no classpath!\n\n" +
                        "Execute com o comando apropriado:\n" +
                        (sgbdSelecionado.equals("MySQL")
                                ? "java -cp \"bin;lib\\mysql-connector-j-9.3.0.jar\" combo.principal.Principal"
                                : "java -cp \"bin;lib\\postgresql-42.7.7.jar\" combo.principal.Principal");
                JOptionPane.showMessageDialog(this, mensagem, "Driver não encontrado", JOptionPane.WARNING_MESSAGE);
            }

            setVisible(false);
            dispose();
            return;
        });

        btnCancelar.addActionListener(e -> {
            sgbdSelecionado = null;
            setVisible(false);
            dispose();
        });

        buttonsPanel.add(btnConfirmar);
        buttonsPanel.add(btnCancelar);

        mainPanel.add(selectionPanel, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setSize(600, 300); // Tamanho aumentado para acomodar as novas informações
        setResizable(false);
    }

    public String getSgbdSelecionado() {
        return sgbdSelecionado;
    }
}
