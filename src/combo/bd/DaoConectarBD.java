/*
 * Sistema de Gerenciamento de Livros
 * Classe de acesso a dados para conexão com banco de dados
 */
package combo.bd;

import combo.vo.VoConexao;
import java.sql.*;

public class DaoConectarBD {

    private VoConexao voConexao;
    private Connection conexao;

    private DaoConectarBD(VoConexao voConexao, Connection conexao) {
        this.voConexao = voConexao;
        this.conexao = conexao;
    }

    public DaoConectarBD() throws E_BD, ClassNotFoundException, SQLException {
        this(null, null);
    }

    /**
     * Conectar com SGBD específico
     * @param sgbd nome do SGBD (mysql ou postgresql)
     */
    public Connection conectar(String sgbd) throws E_BD, java.lang.ClassNotFoundException, SQLException {
        // usar Factory para obter a configuração correta
        DaoStringConexao daoConexao = DaoStringConexaoFactory.getConexao(sgbd);

        // pegar configuração alternativa do SGBD especificado
        this.voConexao = daoConexao.getConfiguracaoAlternativa();

        // testa dados da conexão, se não existem gera exceção
        if ((this.getVoConexao() == null) || (this.getVoConexao().getBaseDados() == null)
                || (this.getVoConexao().getHost() == null) || (this.getVoConexao().getPorta() == null)
                || (this.getVoConexao().getSenha() == null) || (this.getVoConexao().getSgbd() == null)
                || (this.getVoConexao().getUsuario() == null)) {
            throw new E_BD("Não foi possível conectar com o SGBD com as" +
                    " informações " + this.getVoConexao());
        }

        // pega configuração da conexao
        String url = daoConexao.getStringConexao(this.getVoConexao());

        // carrega o Driver
        Class.forName(this.getVoConexao().getClassDriver());

        // faz a conexao com o SGBD
        try {
            conexao = DriverManager.getConnection(url,
                    this.getVoConexao().getUsuario(),
                    this.getVoConexao().getSenha());
            conexao.setAutoCommit(false);
        } catch (SQLException e) {
            throw new E_BD("Erro ao conectar no banco de dados! " + e.getMessage());
        }

        // retorna conexao
        return conexao;
    }

    /**
     * Desconectar do banco de dados
     */
    public void desConectar() throws SQLException {
        if (conexao != null && !conexao.isClosed()) {
            conexao.close();
        }
    }

    public VoConexao getVoConexao() {
        return voConexao;
    }

    public void setVoConexao(VoConexao voConexao) {
        this.voConexao = voConexao;
    }

    public Connection getConexao() {
        return conexao;
    }
}