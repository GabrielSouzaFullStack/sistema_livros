package combo.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utilitário para carregar configurações de banco de dados
 * de arquivo externo (config.properties)
 */
public class ConfigUtil {

    private static Properties properties = null;

    /**
     * Carrega as configurações do arquivo config.properties
     * 
     * @return Properties com as configurações
     */
    public static Properties getConfig() {
        if (properties == null) {
            properties = new Properties();
            try {
                // Tenta carregar do arquivo config.properties
                InputStream input = new FileInputStream("config.properties");
                properties.load(input);
                input.close();
                System.out.println("Configurações carregadas de config.properties");
            } catch (IOException e) {
                System.err.println("Não foi possível carregar config.properties: " + e.getMessage());
                System.err.println("Usando configurações padrão (hardcoded)");
                // Se não conseguir carregar, usar configurações padrão
                loadDefaultConfig();
            }
        }
        return properties;
    }

    /**
     * Carrega configurações padrão em caso de erro
     */
    private static void loadDefaultConfig() {
        System.err.println("ATENÇÃO: Usando configurações padrão para desenvolvimento local!");
        System.err.println("Para produção, configure o arquivo config.properties");

        // MySQL padrão (sem credenciais sensíveis)
        properties.setProperty("mysql.host", "localhost");
        properties.setProperty("mysql.porta", "3306");
        properties.setProperty("mysql.database", "livros");
        properties.setProperty("mysql.usuario", "root");
        properties.setProperty("mysql.senha", ""); // Vazio por segurança - deve ser configurado
        properties.setProperty("mysql.driver", "com.mysql.cj.jdbc.Driver");

        // PostgreSQL padrão (sem credenciais sensíveis)
        properties.setProperty("postgresql.host", "localhost");
        properties.setProperty("postgresql.porta", "5432");
        properties.setProperty("postgresql.database", "livros");
        properties.setProperty("postgresql.usuario", "postgres");
        properties.setProperty("postgresql.senha", ""); // Vazio por segurança - deve ser configurado
        properties.setProperty("postgresql.driver", "org.postgresql.Driver");
    }

    /**
     * Obtém uma propriedade específica
     * 
     * @param key chave da propriedade
     * @return valor da propriedade
     */
    public static String getProperty(String key) {
        return getConfig().getProperty(key);
    }

    /**
     * Obtém uma propriedade com valor padrão
     * 
     * @param key          chave da propriedade
     * @param defaultValue valor padrão se a chave não existir
     * @return valor da propriedade ou valor padrão
     */
    public static String getProperty(String key, String defaultValue) {
        return getConfig().getProperty(key, defaultValue);
    }
}
