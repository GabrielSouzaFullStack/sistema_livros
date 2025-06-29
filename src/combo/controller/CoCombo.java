/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package combo.controller;

import combo.bd.E_BD;
import combo.bo.BoCombo;
import combo.bo.BoConexao;
import combo.gui.GuiCombo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class CoCombo {
    // atributos
    private BoCombo bo;
    private GuiCombo gui;

    // construtor
    public CoCombo(GuiCombo gui, BoConexao conexao) {
        this.gui = gui;
        this.bo = new BoCombo(conexao);
    }

    /**
     * carrega os livros no combobox
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void carregarListaLivros() {
        try {

            // obter lista de livros paginada
            ResultSet rs = this.getBo().listaLivros();

            // monta lista para o combobox
            java.util.List<String> titulos = new java.util.ArrayList<>();
            while (rs.next()) {
                titulos.add(rs.getString("titulo"));
            }

            // seta combobox
            this.getGui().getjComboBoxCliente().setModel(new javax.swing.DefaultComboBoxModel(titulos.toArray()));

            // mensagem
            JOptionPane.showMessageDialog(this.getGui(),
                    "Carregado " + titulos.size() + " registros!",
                    "Registros Carregados", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(CoCombo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (E_BD ex) {
            Logger.getLogger(CoCombo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CoCombo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OutOfMemoryError e) {
            JOptionPane.showMessageDialog(null, "Estouro de memória, compre mais !!! ",
                    "Consulta de Informações", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * carrega as informações de livros em uma jtable
     * Abre a tela de consulta apenas uma vez, e a navegação de páginas é feita pelo
     * controller da tela de consulta (CoConsulta)
     */
    public void obterLista() {
        try {
            // Sempre abre na página 1
            int paginaInicial = 1;
            ResultSet rs = bo.pesquisaDadosLivros(paginaInicial, 200);

            // cria objeto controlador da tela de consulta
            String title = "Consultar livros";
            combo.controller.CoConsulta controllerConsulta = new combo.controller.CoConsulta(new JFrame(),
                    true, rs, title, bo);

            // mostrar consulta (a navegação de páginas é feita dentro da tela de consulta)
            controllerConsulta.consultar();

            // pega retorno
            if (controllerConsulta.isRetorno()) {
                // pega objeto selecionado
                ArrayList<String> objeto = controllerConsulta.getObjetoConsulta();

                // recupera todos os clientes deste horário
                JOptionPane.showMessageDialog(this.getGui(), objeto,
                        "Consulta livros", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CoCombo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (E_BD ex) {
            Logger.getLogger(CoCombo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CoCombo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public BoCombo getBo() {
        return bo;
    }

    public GuiCombo getGui() {
        return gui;
    }

}