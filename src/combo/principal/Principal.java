/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package combo.principal;

import combo.bo.BoConexao;
import combo.gui.GuiCombo;
import combo.gui.GuiSelecionaSGBD;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class Principal {
    // atributos
    private GuiCombo gui;
    private BoConexao conexao;

    // construtor
    public Principal() {
        this.conexao = new BoConexao();
    }

    /**
     * conectar
     */
    private void conectar() {
        try {
            // conectar
            this.getConexao().conectar();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(new javax.swing.JFrame(),
                    "Erro ao conectar no banco de dados!"
                            + ex.toString(),
                    "Manipular tabelas do banco de dados!", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * conectar com SGBD específico
     */
    private void conectar(String sgbd) {
        try {
            // conectar com SGBD específico
            this.getConexao().conectar(sgbd);
            System.out.println("conectou com " + sgbd);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(new javax.swing.JFrame(),
                    "Erro ao conectar no banco de dados " + sgbd + "!"
                            + ex.toString(),
                    "Manipular tabelas do banco de dados!", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * conectar com MySQL
     */
    private void conectarMySQL() {
        try {
            // conectar com MySQL
            this.getConexao().conectarMySQL();
            System.out.println("conectou com MySQL");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(new javax.swing.JFrame(),
                    "Erro ao conectar no banco de dados MySQL!"
                            + ex.toString(),
                    "Manipular tabelas do banco de dados!", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * desconectar do banco de dados
     */
    private void desconectar() {
        try {
            // desconectar
            this.getConexao().desconectar();
            System.out.println("desconectou");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(new javax.swing.JFrame(),
                    "Erro ao desconectar no banco de dados!"
                            + ex.toString(),
                    "Manipular tabelas do banco de dados!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public BoConexao getConexao() {
        return conexao;
    }

    /**
     * *
     * controle de execucao
     */
    public void executar() {
        // conectar
        this.conectar();

        // cria tela
        this.gui = new GuiCombo(true, conexao);

        // mostra tela
        this.gui.setVisible(true);

        // desconescta
        this.desconectar();
    }

    /**
     * *
     * controle de execucao com SGBD específico
     */
    public void executar(String sgbd) {
        // conectar com SGBD específico
        this.conectar(sgbd);

        // cria tela
        this.gui = new GuiCombo(true, conexao);

        // mostra tela
        this.gui.setVisible(true);

        // desconescta
        this.desconectar();
    }

    /**
     * *
     * controle de execucao com MySQL
     */
    public void executarMySQL() {
        // conectar com MySQL
        this.conectarMySQL();

        // cria tela
        this.gui = new GuiCombo(true, conexao);

        // mostra tela
        this.gui.setVisible(true);

        // desconescta
        this.desconectar();
    }

    // main
    public static void main(String[] args) {
        // Criar e exibir o diálogo de seleção de SGBD
        GuiSelecionaSGBD dialogoSGBD = new GuiSelecionaSGBD(null);
        dialogoSGBD.setVisible(true);

        // Obter a escolha do usuário
        String sgbdSelecionado = dialogoSGBD.getSgbdSelecionado();

        // Se o usuário cancelou, encerra a aplicação
        if (sgbdSelecionado == null) {
            System.exit(0);
            return;
        }

        // Configurar a conexão com base na seleção
        try {
            String sgbd = (sgbdSelecionado.equals("MySQL")) ? "mysql" : "postgresql";
            Principal app = new Principal();
            app.executar(sgbd);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage(),
                    "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
        }
    }
}
