/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package combo.dao;

import combo.bd.DaoConsultarBD;
import combo.bd.E_BD;
import combo.bo.BoConexao;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DaoCombo {
    // atributos
    private BoConexao conexao;
    private DaoConsultarBD bd;

    // construtor
    public DaoCombo(BoConexao conexao) {
        this.conexao = conexao;
        this.bd = conexao.getBd();
    }

    /**
     * obtem lista de livros
     * 
     * @return
     * @throws SQLException
     * @throws E_BD
     * @throws ClassNotFoundException
     */
    public ResultSet listaLivros() throws SQLException, E_BD, ClassNotFoundException {
        String sql = "SELECT * FROM livros";
        return this.getBd().consulta(sql);
    }

    /**
     * obtem lista de livros e seus dados relacionados
     * 
     * @return
     * @throws SQLException
     * @throws E_BD
     * @throws ClassNotFoundException
     */
    public ResultSet pesquisaDadosLivros(int paginaAtual, int tamanhoPagina)
            throws SQLException, E_BD, ClassNotFoundException {
        int offset = (paginaAtual - 1) * tamanhoPagina;
        String sql = "SELECT * FROM livrostemp ORDER BY codigo LIMIT " + tamanhoPagina + " OFFSET " + offset;
        return this.getBd().consulta(sql);
    }

    /**
     * Conta o total de registros na tabela livrostemp
     * 
     * @return
     * @throws SQLException
     * @throws E_BD
     * @throws ClassNotFoundException
     */
    public int contarTotalLivros() throws SQLException, E_BD, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM livrostemp";
        ResultSet rs = this.getBd().consulta(sql);
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    // getters
    public BoConexao getConexao() {
        return conexao;
    }

    public DaoConsultarBD getBd() {
        return bd;
    }
}