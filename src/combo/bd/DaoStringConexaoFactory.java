package combo.bd;

// Factory para retorar a implemetação correta
public class DaoStringConexaoFactory {
    public static DaoStringConexao getConexao(String sgbd) {
        // verifica o SGBD
        if (sgbd.equalsIgnoreCase("PostgreSQL")) {
            // retorna a implementação do PostgreSQL
            return new DaoStringConexaoPostgreSQL();
        } else if (sgbd.equalsIgnoreCase("MySQL")) {
            // retorna a implementação do MySQL
            return new DaoStringConexaoMySQL();
        } else {
            // lança exceção se o SGBD não for suportado
            throw new IllegalArgumentException("SGBD não suportado: " + sgbd);
        }
    }
}
