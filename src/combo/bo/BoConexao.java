/*
 * Sistema de Gerenciamento de Livros
 * Classe de negócio para conexão com banco de dados
 */
package combo.bo;

import combo.bd.DaoConectarBD;
import combo.bd.DaoConsultarBD;
import combo.bd.E_BD;
import java.sql.SQLException;

public class BoConexao {
    private DaoConectarBD conexao;
    private DaoConsultarBD bd;

    public BoConexao() {
    }

    /**
     * Conectar com SGBD específico
     * @param sgbd nome do SGBD (mysql ou postgresql)
     */
    public void conectar(String sgbd) throws E_BD, ClassNotFoundException, SQLException {
        // testa se existe conexao
        if (this.getConexao() == null) {
            // cria conexao
            this.setConexao(new DaoConectarBD());

            // cria objeto de consulta
            this.setBd(new DaoConsultarBD(this.getConexao()));

            // conectar com SGBD específico
            this.getConexao().conectar(sgbd);
        }
    }

    /**
     * Desconectar do banco de dados
     */
    public void desconectar() throws SQLException {
        if (this.getConexao() != null) {
            this.getConexao().desConectar();
        }
    }

    public DaoConectarBD getConexao() {
        return conexao;
    }

    private void setConexao(DaoConectarBD conexao) {
        this.conexao = conexao;
    }

    public DaoConsultarBD getBd() {
        return bd;
    }

    private void setBd(DaoConsultarBD bd) {
        this.bd = bd;
    }
}
