/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package combo.bo;

import combo.bd.E_BD;
import combo.dao.DaoCombo;
import java.sql.ResultSet;
import java.sql.SQLException;


public class BoCombo {
    // atributos
    private BoConexao conexao;
    private DaoCombo dao;

    // construtor
    public BoCombo(BoConexao conexao) {
        this.conexao = conexao;
        this.dao = new DaoCombo(conexao);
    }

    /**
     * obtem lista de livros e seus dados relacionados (paginado)
     * 
     * @param paginaAtual
     * @return
     * @throws SQLException
     * @throws E_BD
     * @throws ClassNotFoundException
     */
    public ResultSet pesquisaDadosLivros(int paginaAtual, int tamanhoPagina)
            throws SQLException, E_BD, ClassNotFoundException {
        return dao.pesquisaDadosLivros(paginaAtual, tamanhoPagina);
    }

    /**
     * obtem lista de livros (não paginado, compatível com código antigo)
     * 
     * 
     * @return
     * @throws SQLException
     * @throws E_BD
     * @throws ClassNotFoundException
     */
    public ResultSet listaLivros() throws SQLException, E_BD, ClassNotFoundException {
        return this.getDao().listaLivros();
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
        return dao.contarTotalLivros();
    }

    // getters
    public BoConexao getConexao() {
        return conexao;
    }

    public DaoCombo getDao() {
        return dao;
    }

}
