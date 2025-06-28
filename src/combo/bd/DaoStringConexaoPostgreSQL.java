/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package combo.bd;

import combo.vo.VoConexao;
import combo.util.ConfigUtil;

public class DaoStringConexaoPostgreSQL implements DaoStringConexao {

    @Override
    public String getStringConexao(VoConexao vo) {
        // monta conexão
        String url = "jdbc:postgresql://" + vo.getHost() +
                ":" + vo.getPorta() + "/" + vo.getBaseDados();

        // mostra conexao
        System.out.println(url);

        // retorna
        return url;
    }

    @Override
    public VoConexao getConfiguracaoDefault() {
        // cria objeto
        VoConexao vo = new VoConexao();

        // seta valores usando arquivo de configuração
        vo.setSgbd("PostgreSQL");
        vo.setHost(ConfigUtil.getProperty("postgresql.host", "localhost"));
        vo.setPorta(ConfigUtil.getProperty("postgresql.porta", "5432"));
        vo.setBaseDados(ConfigUtil.getProperty("postgresql.database", "livros"));
        vo.setUsuario(ConfigUtil.getProperty("postgresql.usuario", "postgres"));
        vo.setSenha(ConfigUtil.getProperty("postgresql.senha", "")); // Vazio por segurança - configure em
                                                                     // config.properties
        vo.setClassDriver(ConfigUtil.getProperty("postgresql.driver", "org.postgresql.Driver"));

        // returns
        return vo;
    }

    @Override
    public VoConexao getConfiguracaoAlternativa() {
        // cria objeto
        VoConexao vo = new VoConexao();

        // seta valores alternativos usando arquivo de configuração
        vo.setSgbd("PostgreSQL");
        vo.setHost(
                ConfigUtil.getProperty("postgresql.prod.host", ConfigUtil.getProperty("postgresql.host", "localhost")));
        vo.setPorta(
                ConfigUtil.getProperty("postgresql.prod.porta", ConfigUtil.getProperty("postgresql.porta", "5432")));
        vo.setBaseDados(ConfigUtil.getProperty("postgresql.prod.database",
                ConfigUtil.getProperty("postgresql.database", "livros")));
        vo.setUsuario(ConfigUtil.getProperty("postgresql.prod.usuario",
                ConfigUtil.getProperty("postgresql.usuario", "postgres")));
        vo.setSenha(ConfigUtil.getProperty("postgresql.prod.senha",
                ConfigUtil.getProperty("postgresql.senha", ""))); // Vazio por segurança
        vo.setClassDriver(ConfigUtil.getProperty("postgresql.driver", "org.postgresql.Driver"));

        // returns
        return vo;
    }

}