package combo.bd;

import combo.vo.VoConexao;
import combo.util.ConfigUtil;

public class DaoStringConexaoMySQL implements DaoStringConexao {

    @Override
    public String getStringConexao(VoConexao vo) {
        // monta conexão MySQL
        String url = "jdbc:mysql://" + vo.getHost() +
                ":" + vo.getPorta() + "/" + vo.getBaseDados();

        // mostra conexão
        System.out.println(url);

        // retorna
        return url;
    }

    @Override
    public VoConexao getConfiguracaoDefault() {
        // cria objeto
        VoConexao vo = new VoConexao();

        // seta valores para MySQL usando arquivo de configuração
        vo.setSgbd("MySQL");
        vo.setHost(ConfigUtil.getProperty("mysql.host", "localhost"));
        vo.setPorta(ConfigUtil.getProperty("mysql.porta", "3306"));
        vo.setBaseDados(ConfigUtil.getProperty("mysql.database", "livros"));
        vo.setUsuario(ConfigUtil.getProperty("mysql.usuario", "root"));
        vo.setSenha(ConfigUtil.getProperty("mysql.senha", "")); // Vazio por segurança - configure em config.properties
        vo.setClassDriver(ConfigUtil.getProperty("mysql.driver", "com.mysql.cj.jdbc.Driver"));

        // retorna
        return vo;
    }

    @Override
    public VoConexao getConfiguracaoAlternativa() {
        // cria objeto
        VoConexao vo = new VoConexao();

        // seta valores alternativos para MySQL usando arquivo de configuração
        vo.setSgbd("MySQL");
        vo.setHost(ConfigUtil.getProperty("mysql.prod.host", ConfigUtil.getProperty("mysql.host", "localhost")));
        vo.setPorta(ConfigUtil.getProperty("mysql.prod.porta", ConfigUtil.getProperty("mysql.porta", "3306")));
        vo.setBaseDados(
                ConfigUtil.getProperty("mysql.prod.database", ConfigUtil.getProperty("mysql.database", "livros")));
        vo.setUsuario(ConfigUtil.getProperty("mysql.prod.usuario", ConfigUtil.getProperty("mysql.usuario", "root")));
        vo.setSenha(ConfigUtil.getProperty("mysql.prod.senha", ConfigUtil.getProperty("mysql.senha", ""))); // Vazio por
                                                                                                            // segurança
        vo.setClassDriver(ConfigUtil.getProperty("mysql.driver", "com.mysql.cj.jdbc.Driver"));

        // retorna
        return vo;
    }
}
