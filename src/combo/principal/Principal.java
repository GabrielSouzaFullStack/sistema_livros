/*
 * Sistema de Gerenciamento de Livros
 * Versão modernizada com configuração externalizada
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
     * Conectar com SGBD específico
     * @param sgbd nome do SGBD (mysql ou postgresql)
     */
    private void conectar(String sgbd) {
        try {
            this.getConexao().conectar(sgbd);
            System.out.println("Conectado com " + sgbd);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(new javax.swing.JFrame(),
                    "Erro ao conectar no banco de dados " + sgbd + "!\n" + ex.getMessage(),
                    "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Desconectar do banco de dados
     */
    private void desconectar() {
        try {
            this.getConexao().desconectar();
            System.out.println("Desconectado do banco de dados");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(new javax.swing.JFrame(),
                    "Erro ao desconectar do banco de dados!\n" + ex.getMessage(),
                    "Erro de Desconexão", JOptionPane.ERROR_MESSAGE);
        }
    }

    public BoConexao getConexao() {
        return conexao;
    }

    /**
     * Controle de execução com SGBD específico
     * @param sgbd nome do SGBD (mysql ou postgresql)
     */
    public void executar(String sgbd) {
        // conectar com SGBD específico
        this.conectar(sgbd);

        // criar e exibir tela principal
        this.gui = new GuiCombo(true, conexao);
        this.gui.setVisible(true);

        // desconectar quando a tela for fechada
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
            JOptionPane.showMessageDialog(null, 
                    "Erro ao conectar ao banco de dados: " + e.getMessage(),
                    "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
        }
    }
}
