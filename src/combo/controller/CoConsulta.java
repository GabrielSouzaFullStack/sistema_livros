/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package combo.controller;

import combo.bd.E_BD;
import combo.bo.BoCombo;
import combo.gui.consulta.GuiConsulta;
import combo.gui.consulta.GuiMontarJTable;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dialog;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.JTableHeader;


public class CoConsulta {

    private ResultSet rs;
    private GuiConsulta gui;
    private BoCombo bo;
    private boolean retorno;
    private ArrayList<String> objetoConsulta;
    private int paginaAtual = 1;
    private int totalPaginas = 5;
    private int totalRegistros = 999;
    private int tamanhoPagina = 200; // Defina o tamanho da página padrão

    public CoConsulta(JFrame parent, boolean modal, ResultSet rs, String title, BoCombo boCombo) {
        this.rs = rs;
        this.gui = new GuiConsulta(parent, true, title);
        this.gui.setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
        this.gui.setController(this);
        this.bo = boCombo; // Garante que os botões chamem os métodos do controller

        try {
            // Inicializa os valores de totalRegistros e totalPaginas
            this.totalRegistros = bo.contarTotalLivros();
            this.totalPaginas = (int) Math.ceil((double) totalRegistros / tamanhoPagina);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(gui, "Erro ao contar registros: " + ex.getMessage());
        }
    }

    /**
     * monta a tela de consulta e mostra ela para o usuário
     */
    public void consultar() {
        try {

            this.setRetorno(false);
            // monta jtable
            GuiMontarJTable iMontarJTable = new GuiMontarJTable(this.getRs());
            this.getGui().setjTable(new JTable(iMontarJTable.criaTabela()));
            this.getGui().getjTable().setFillsViewportHeight(true);
            this.getGui().getjTable().setFont(new java.awt.Font("Arial", 1, 18));
            this.getGui().getjTable().setRowHeight(28);
            this.getGui().getjTable().setBorder(new BevelBorder(1, Color.black, Color.black));
            this.getGui().getjTable().getTableHeader().setBorder(new BevelBorder(1, Color.black, Color.black));
            this.getGui().getjTable().setShowGrid(true);
            JTableHeader header = this.getGui().getjTable().getTableHeader();
            header.setForeground(Color.black);
            header.setFont(new java.awt.Font("Arial", 1, 18));
            this.getGui().getjTable().addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    try {
                        jTableMouseClicked(evt);
                    } catch (SQLException ex) {
                        Logger.getLogger(CoConsulta.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            // adiciona ao container
            boolean selecao = this.getGui().getjTable().isEnabled();
            Container pane = this.getGui().getContentPane();
            pane.remove(this.getGui().getjScrollPane());
            this.getGui().setjScrollPane(new JScrollPane(this.getGui().getjTable()));
            this.getGui().getjScrollPane().setBounds(0, 0, 800, 400);
            pane.add(this.getGui().getjScrollPane());
            this.getGui().getjTable().setEnabled(selecao);

            // Atualiza o status após montar a tabela
            atualizarStatus();
            // mostrar tela
            this.getGui().setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getGui(), "Não foi possível "
                    + " consultar os dados no banco de dados!\n" + ex,
                    "Consulta de Informações", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * *
     * controla a seleção de uma linha da JTable de consulta
     *
     * @param evt
     * @throws SQLException
     */
    private void jTableMouseClicked(java.awt.event.MouseEvent evt) throws SQLException {
        // TODO add your handling code here:
        // pega linha selecionada da tabela
        int linha = this.getGui().getjTable().getSelectedRow();

        // pega o tamanho de colunas do resultado
        int tamanho = this.getRs().getMetaData().getColumnCount();

        // testa se a linha é maior que o tamanho
        if (linha < 0) {
            // não há resultado válido
            this.setRetorno(false);
            return;
        }

        // cria resultado
        this.setObjetoConsulta(new ArrayList<String>(tamanho));

        // posiciona cursor no inicio
        this.getRs().beforeFirst();

        // percorre cursor
        while (this.getRs().next()) {
            if (this.getRs().getRow() == linha + 1) {
                for (int i = 1; i < tamanho + 1; i++) {
                    this.getObjetoConsulta().add(this.getRs().getString(i));
                }
                break;
            }
        }

        // sets.....
        this.setRetorno(true);
        this.getGui().setVisible(false);
    }

    /**
     * método para permitir ou inibir a seleção na tabela de consulta
     *
     * @param selecao
     */
    public void setSelecaoTabela(boolean selecao) {
        this.getGui().getjTable().setEnabled(selecao);
    }

    /**
     * *
     * montar um JTable para ser usado em uma outra tela de consulta fixa no seu
     * painel ou frame
     */
    /**
     * monta a tela de consulta e mostra ela para o usuário
     */
    public JTable montarTelaConsultar() {
        // variavel de retorno
        JTable tabela = null;
        try {
            // monta jtable
            GuiMontarJTable iMontarJTable = new GuiMontarJTable(this.getRs());

            // criar objeto jtable e configurar
            tabela = new JTable(iMontarJTable.criaTabela());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getGui(), "Não foi possível "
                    + " consultar os dados no banco de dados!\n" + ex,
                    "Consulta de Informações", JOptionPane.ERROR_MESSAGE);
        }

        // retorna
        return tabela;
    }

    /**
     * *
     * getters and setters
     *
     * @return
     */
    private ResultSet getRs() {
        return rs;
    }

    private GuiConsulta getGui() {
        return gui;
    }

    public boolean isRetorno() {
        return retorno;
    }

    private void setRetorno(boolean retorno) {
        this.retorno = retorno;
    }

    public ArrayList<String> getObjetoConsulta() {
        return objetoConsulta;
    }

    private void setObjetoConsulta(ArrayList<String> objetoConsulta) {
        this.objetoConsulta = objetoConsulta;
    }

    public void atualizarStatus() {
        String texto = "Página " + paginaAtual + " de " + totalPaginas + " (Total: " + totalRegistros + " registros)";
        this.getGui().setStatus(texto);
    }

    public void irParaPrimeiraPagina() throws SQLException, E_BD, ClassNotFoundException {
        paginaAtual = 1;
        atualizarTabela();
    }

    public void paginaAnterior() throws SQLException, ClassNotFoundException {
        if (paginaAtual > 1) {
            paginaAtual--;
            try {
                atualizarTabela();
            } catch (E_BD ex) {
                JOptionPane.showMessageDialog(gui, "Erro ao navegar para página anterior: " + ex.getMessage());
            }
        }
    }

    public void proximaPagina() throws SQLException, ClassNotFoundException {
        if (paginaAtual < totalPaginas) {
            paginaAtual++;
            try {
                atualizarTabela();
            } catch (E_BD ex) {
                JOptionPane.showMessageDialog(gui, "Erro ao navegar para próxima página: " + ex.getMessage());
            }
        }
    }

    public void irParaUltimaPagina() throws SQLException, ClassNotFoundException {
        paginaAtual = totalPaginas;
        try {
            atualizarTabela();
        } catch (E_BD ex) {
            JOptionPane.showMessageDialog(gui, "Erro ao navegar para última página: " + ex.getMessage());
        }
    }

    public void atualizarTabela() throws SQLException, ClassNotFoundException, E_BD {
        try {
            // Atualize o ResultSet com a página correta
            rs = bo.pesquisaDadosLivros(paginaAtual, tamanhoPagina);

            // Monta JTable com os dados atualizados
            GuiMontarJTable iMontarJTable = new GuiMontarJTable(rs);
            gui.setjTable(new JTable(iMontarJTable.criaTabela()));
            gui.getjTable().setFillsViewportHeight(true);
            gui.getjTable().setFont(new java.awt.Font("Arial", 1, 18));
            gui.getjTable().setRowHeight(28);
            gui.getjTable().setBorder(new BevelBorder(1, Color.black, Color.black));
            gui.getjTable().getTableHeader().setBorder(new BevelBorder(1, Color.black, Color.black));
            gui.getjTable().setShowGrid(true);

            JTableHeader header = gui.getjTable().getTableHeader();
            header.setForeground(Color.black);
            header.setFont(new java.awt.Font("Arial", 1, 18));

            // Atualiza o container para exibir a nova tabela
            Container pane = gui.getContentPane();
            pane.remove(gui.getjScrollPane());
            gui.setjScrollPane(new JScrollPane(gui.getjTable()));
            gui.getjScrollPane().setBounds(0, 0, 800, 400);
            pane.add(gui.getjScrollPane());

            // Recalcula o total de registros e páginas
            this.totalRegistros = bo.contarTotalLivros();
            this.totalPaginas = (int) Math.ceil((double) totalRegistros / tamanhoPagina);

            // Atualize o status
            atualizarStatus();

            // Força a atualização da interface
            gui.revalidate();
            gui.repaint();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(gui, "Erro ao atualizar tabela: " + ex.getMessage());
            ex.printStackTrace();
            throw ex;
        }
    }
}