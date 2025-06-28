package combo.bd;

import combo.vo.VoConexao;


public interface DaoStringConexao {

    // métodos para obter a string de conexão
    public String getStringConexao(VoConexao vo);
    public VoConexao getConfiguracaoDefault();
    public VoConexao getConfiguracaoAlternativa();
}
