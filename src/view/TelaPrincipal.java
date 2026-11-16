package view;

import controller.BibliotecaService;
import model.Emprestimo;
import model.Livro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class TelaPrincipal extends JFrame {

    // Toda a logica e os dados ficam no service; a tela so exibe e chama os metodos dele
    private final BibliotecaService service = new BibliotecaService();

    // Paleta de cores do sistema
    private static final Color AZUL_ESCURO  = new Color(30, 60, 114);
    private static final Color AZUL_MEDIO   = new Color(42, 82, 152);
    private static final Color AZUL_CLARO   = new Color(52, 152, 219);
    private static final Color VERDE         = new Color(39, 174, 96);
    private static final Color VERMELHO      = new Color(192, 57, 43);
    private static final Color AMARELO       = new Color(243, 156, 18);
    private static final Color CINZA_FUNDO   = new Color(240, 242, 245);
    private static final Color BRANCO        = new Color(255, 255, 255);
    private static final Color TEXTO_ESCURO  = new Color(44, 62, 80);
    private static final Color TEXTO_CINZA   = new Color(127, 140, 141);
    private static final Color BORDA_CINZA   = new Color(200, 206, 212);

    // Fontes usadas no sistema
    private static final Font FONTE_TITULO     = new Font("Segoe UI", Font.BOLD, 20);
    private static final Font FONTE_SUBTITULO  = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FONTE_LABEL      = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONTE_CAMPO      = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONTE_BOTAO      = new Font("Segoe UI", Font.BOLD, 12);
    private static final Font FONTE_TABELA     = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font FONTE_TAB_HEADER = new Font("Segoe UI", Font.BOLD, 12);
    private static final Font FONTE_RELATORIO  = new Font("Segoe UI", Font.PLAIN, 15);
    private static final Font FONTE_STAT_NUM   = new Font("Segoe UI", Font.BOLD, 32);
    private static final Font FONTE_STAT_LABEL = new Font("Segoe UI", Font.PLAIN, 11);

    // Componentes da aba de Cadastro
    private JTextField txtCodigo;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtCategoria;
    private JTextField txtQuantidade;

    // Componentes da aba de Consulta
    private JTextField txtPesquisa;
    private JTable tabelaLivros;
    private DefaultTableModel modeloTabelaLivros;

    // Componentes da aba de Emprestimos
    private JTextField txtCodigoEmprestimo;
    private JTextField txtNomeUsuario;
    private JTable tabelaEmprestimos;
    private DefaultTableModel modeloTabelaEmprestimos;

    // Componentes da aba de Relatorio
    private JLabel lblStatTitulos;
    private JLabel lblStatExemplares;
    private JLabel lblStatDisponiveis;
    private JLabel lblStatAtivos;

    public TelaPrincipal() {
        setTitle("Gerenciador de Biblioteca Acadêmica");
        setSize(920, 650);
        setMinimumSize(new Dimension(800, 550));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(CINZA_FUNDO);
        setLayout(new BorderLayout());

        // Header superior com titulo do sistema
        add(criarHeader(), BorderLayout.NORTH);

        // Painel de abas
        JTabbedPane abas = new JTabbedPane();
        abas.setFont(FONTE_TAB_HEADER);
        abas.setBackground(BRANCO);

        abas.addTab("  Cadastro  ", criarAbaCadastro());
        abas.addTab("  Consulta  ", criarAbaConsulta());
        abas.addTab("  Empréstimos  ", criarAbaEmprestimos());
        abas.addTab("  Relatório  ", criarAbaRelatorio());

        // Atualiza o relatorio quando o usuario clicar na aba dele
        abas.addChangeListener(e -> {
            if (abas.getSelectedIndex() == 3) {
                atualizarRelatorio();
            }
        });

        add(abas, BorderLayout.CENTER);
    }

    // =======================================================
    // HEADER DO SISTEMA
    // =======================================================
    private JPanel criarHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(AZUL_ESCURO);
        header.setPreferredSize(new Dimension(0, 60));
        header.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel lblTitulo = new JLabel("Biblioteca Acadêmica");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(BRANCO);

        JLabel lblSubtitulo = new JLabel("Sistema de Gerenciamento de Livros e Empréstimos");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitulo.setForeground(new Color(174, 194, 220));

        JPanel textos = new JPanel();
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.setOpaque(false);
        textos.add(lblTitulo);
        textos.add(lblSubtitulo);

        header.add(textos, BorderLayout.WEST);

        JLabel lblEngSoftware = new JLabel("Eng. Software - 2° Semestre");
        lblEngSoftware.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblEngSoftware.setForeground(new Color(149, 165, 181));
        header.add(lblEngSoftware, BorderLayout.EAST);

        return header;
    }

    // =======================================================
    // COMPONENTES REUTILIZAVEIS
    // =======================================================
    private JTextField criarCampo() {
        JTextField campo = new JTextField();
        campo.setFont(FONTE_CAMPO);
        campo.setForeground(TEXTO_ESCURO);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDA_CINZA, 1),
                new EmptyBorder(7, 10, 7, 10)
        ));
        return campo;
    }

    private JButton criarBotao(String texto, Color corFundo, Color corTexto) {
        JButton btn = new JButton(texto);
        btn.setFont(FONTE_BOTAO);
        btn.setBackground(corFundo);
        btn.setForeground(corTexto);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(9, 20, 9, 20));
        return btn;
    }

    private JTable criarTabela(DefaultTableModel modelo) {
        JTable tabela = new JTable(modelo);
        tabela.setFont(FONTE_TABELA);
        tabela.setRowHeight(30);
        tabela.setSelectionBackground(new Color(212, 230, 250));
        tabela.setSelectionForeground(TEXTO_ESCURO);
        tabela.setGridColor(new Color(228, 232, 236));
        tabela.setShowGrid(true);
        tabela.getTableHeader().setReorderingAllowed(false);

        // Estilizar o cabecalho da tabela
        JTableHeader header = tabela.getTableHeader();
        header.setFont(FONTE_TAB_HEADER);
        header.setBackground(AZUL_MEDIO);
        header.setForeground(BRANCO);
        header.setPreferredSize(new Dimension(0, 34));

        // Centralizar o texto do cabecalho
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Centralizar o conteudo das celulas
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tabela.getColumnCount(); i++) {
            tabela.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }

        return tabela;
    }

    private JPanel criarCampoComLabel(String labelTexto, JTextField campo) {
        JPanel grupo = new JPanel(new BorderLayout(0, 4));
        grupo.setOpaque(false);

        JLabel lbl = new JLabel(labelTexto);
        lbl.setFont(FONTE_LABEL);
        lbl.setForeground(TEXTO_ESCURO);

        grupo.add(lbl, BorderLayout.NORTH);
        grupo.add(campo, BorderLayout.CENTER);

        return grupo;
    }

    private JPanel criarCardEstatistica(String titulo, JLabel valorLabel, Color cor) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(BRANCO);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(3, 0, 0, 0, cor),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDA_CINZA, 1),
                        new EmptyBorder(18, 15, 18, 15)
                )
        ));

        valorLabel.setFont(FONTE_STAT_NUM);
        valorLabel.setForeground(cor);
        valorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblDescricao = new JLabel(titulo);
        lblDescricao.setFont(FONTE_STAT_LABEL);
        lblDescricao.setForeground(TEXTO_CINZA);
        lblDescricao.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalGlue());
        card.add(valorLabel);
        card.add(Box.createVerticalStrut(4));
        card.add(lblDescricao);
        card.add(Box.createVerticalGlue());

        return card;
    }

    // =======================================================
    // 1. ABA DE CADASTRO
    // =======================================================
    private JPanel criarAbaCadastro() {
        JPanel painelExterno = new JPanel(new GridBagLayout());
        painelExterno.setBackground(CINZA_FUNDO);

        // Card central do formulario
        JPanel formulario = new JPanel();
        formulario.setLayout(new BoxLayout(formulario, BoxLayout.Y_AXIS));
        formulario.setBackground(BRANCO);
        formulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDA_CINZA, 1),
                new EmptyBorder(25, 35, 25, 35)
        ));
        formulario.setPreferredSize(new Dimension(500, 520));

        // Titulo do formulario
        JLabel lblTituloCad = new JLabel("Cadastro de Novo Livro");
        lblTituloCad.setFont(FONTE_TITULO);
        lblTituloCad.setForeground(AZUL_ESCURO);
        lblTituloCad.setAlignmentX(Component.LEFT_ALIGNMENT);
        formulario.add(lblTituloCad);

        JLabel lblDescricao = new JLabel("Preencha os dados abaixo para registrar um novo livro.");
        lblDescricao.setFont(FONTE_LABEL);
        lblDescricao.setForeground(TEXTO_CINZA);
        lblDescricao.setAlignmentX(Component.LEFT_ALIGNMENT);
        formulario.add(lblDescricao);
        formulario.add(Box.createVerticalStrut(18));

        // Campos do formulario
        txtCodigo = criarCampo();
        txtTitulo = criarCampo();
        txtAutor = criarCampo();
        txtCategoria = criarCampo();
        txtQuantidade = criarCampo();

        JPanel campoCodigo = criarCampoComLabel("Código *", txtCodigo);
        campoCodigo.setAlignmentX(Component.LEFT_ALIGNMENT);
        campoCodigo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        formulario.add(campoCodigo);
        formulario.add(Box.createVerticalStrut(10));

        JPanel campoTitulo = criarCampoComLabel("Título *", txtTitulo);
        campoTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        campoTitulo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        formulario.add(campoTitulo);
        formulario.add(Box.createVerticalStrut(10));

        JPanel campoAutor = criarCampoComLabel("Autor *", txtAutor);
        campoAutor.setAlignmentX(Component.LEFT_ALIGNMENT);
        campoAutor.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        formulario.add(campoAutor);
        formulario.add(Box.createVerticalStrut(10));

        JPanel campoCategoria = criarCampoComLabel("Categoria", txtCategoria);
        campoCategoria.setAlignmentX(Component.LEFT_ALIGNMENT);
        campoCategoria.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        formulario.add(campoCategoria);
        formulario.add(Box.createVerticalStrut(10));

        JPanel campoQtd = criarCampoComLabel("Quantidade de Exemplares *", txtQuantidade);
        campoQtd.setAlignmentX(Component.LEFT_ALIGNMENT);
        campoQtd.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        formulario.add(campoQtd);
        formulario.add(Box.createVerticalStrut(20));

        // Botoes
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        painelBotoes.setOpaque(false);
        painelBotoes.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelBotoes.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JButton btnLimpar = criarBotao("Limpar", BORDA_CINZA, TEXTO_ESCURO);
        JButton btnCadastrar = criarBotao("Cadastrar Livro", VERDE, BRANCO);

        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnCadastrar);
        formulario.add(painelBotoes);

        btnCadastrar.addActionListener(e -> cadastrarLivro());
        btnLimpar.addActionListener(e -> limparCamposCadastro());

        painelExterno.add(formulario);
        return painelExterno;
    }

    private void cadastrarLivro() {
        String qtdStr = txtQuantidade.getText().trim();

        if (qtdStr.isEmpty()) {
            mostrarAviso("Por favor, preencha todos os campos obrigatórios!");
            return;
        }

        int quantidade;
        try {
            quantidade = Integer.parseInt(qtdStr);
        } catch (NumberFormatException ex) {
            mostrarErro("A quantidade deve ser um número inteiro!");
            return;
        }

        try {
            service.cadastrarLivro(
                    txtCodigo.getText(),
                    txtTitulo.getText(),
                    txtAutor.getText(),
                    txtCategoria.getText(),
                    quantidade);
        } catch (IllegalArgumentException ex) {
            mostrarErro(ex.getMessage());
            return;
        }

        JOptionPane.showMessageDialog(this, "Livro cadastrado com sucesso!");
        limparCamposCadastro();
        atualizarTabelaLivros(service.listarLivros());
    }

    private void limparCamposCadastro() {
        txtCodigo.setText("");
        txtTitulo.setText("");
        txtAutor.setText("");
        txtCategoria.setText("");
        txtQuantidade.setText("");
        txtCodigo.requestFocus();
    }

    // =======================================================
    // 2. ABA DE CONSULTA E LISTAGEM
    // =======================================================
    private JPanel criarAbaConsulta() {
        JPanel painel = new JPanel(new BorderLayout(0, 12));
        painel.setBackground(CINZA_FUNDO);
        painel.setBorder(new EmptyBorder(18, 18, 18, 18));

        // Topo: titulo + barra de pesquisa
        JPanel topo = new JPanel(new BorderLayout(0, 10));
        topo.setOpaque(false);

        JLabel lblTituloConsulta = new JLabel("Consulta de Livros");
        lblTituloConsulta.setFont(FONTE_TITULO);
        lblTituloConsulta.setForeground(AZUL_ESCURO);
        topo.add(lblTituloConsulta, BorderLayout.NORTH);

        // Barra de pesquisa
        JPanel barraPesquisa = new JPanel(new BorderLayout(10, 0));
        barraPesquisa.setBackground(BRANCO);
        barraPesquisa.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDA_CINZA, 1),
                new EmptyBorder(8, 12, 8, 12)
        ));

        txtPesquisa = criarCampo();
        txtPesquisa.setBorder(new EmptyBorder(4, 6, 4, 6));

        JPanel botoesPesquisa = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        botoesPesquisa.setOpaque(false);

        JButton btnBuscar = criarBotao("Buscar", AZUL_CLARO, BRANCO);
        JButton btnListarTodos = criarBotao("Listar Todos", AZUL_MEDIO, BRANCO);

        botoesPesquisa.add(btnBuscar);
        botoesPesquisa.add(btnListarTodos);

        barraPesquisa.add(txtPesquisa, BorderLayout.CENTER);
        barraPesquisa.add(botoesPesquisa, BorderLayout.EAST);

        topo.add(barraPesquisa, BorderLayout.CENTER);
        painel.add(topo, BorderLayout.NORTH);

        // Tabela central de livros
        String[] colunas = {"Código", "Título", "Autor", "Categoria", "Qtd. Total", "Qtd. Disponível"};
        modeloTabelaLivros = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaLivros = criarTabela(modeloTabelaLivros);

        JScrollPane scrollTabela = new JScrollPane(tabelaLivros);
        scrollTabela.setBorder(BorderFactory.createLineBorder(BORDA_CINZA, 1));
        scrollTabela.getViewport().setBackground(BRANCO);
        painel.add(scrollTabela, BorderLayout.CENTER);

        // Botoes de acao inferiores
        JPanel painelAcoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        painelAcoes.setOpaque(false);

        JButton btnEditar = criarBotao("Editar Selecionado", AMARELO, BRANCO);
        JButton btnExcluir = criarBotao("Excluir Selecionado", VERMELHO, BRANCO);

        painelAcoes.add(btnEditar);
        painelAcoes.add(btnExcluir);
        painel.add(painelAcoes, BorderLayout.SOUTH);

        // Acoes
        btnBuscar.addActionListener(e -> pesquisarLivro());
        btnListarTodos.addActionListener(e -> {
            txtPesquisa.setText("");
            atualizarTabelaLivros(service.listarLivros());
        });
        btnEditar.addActionListener(e -> editarLivro());
        btnExcluir.addActionListener(e -> excluirLivro());

        return painel;
    }

    private void pesquisarLivro() {
        String termo = txtPesquisa.getText();
        List<Livro> resultado = service.pesquisarLivros(termo);

        atualizarTabelaLivros(resultado);

        if (resultado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum livro encontrado com o termo informado.");
        }
    }

    private void atualizarTabelaLivros(List<Livro> lista) {
        modeloTabelaLivros.setRowCount(0);
        for (Livro l : lista) {
            Object[] linha = {
                    l.getCodigo(),
                    l.getTitulo(),
                    l.getAutor(),
                    l.getCategoria(),
                    l.getQuantTotal(),
                    l.getQuantDisponivel()
            };
            modeloTabelaLivros.addRow(linha);
        }
    }

    private void editarLivro() {
        int linhaSelecionada = tabelaLivros.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um livro na tabela para editar.");
            return;
        }

        String codigo = (String) modeloTabelaLivros.getValueAt(linhaSelecionada, 0);
        Livro livro = service.buscarLivro(codigo);
        if (livro == null) return;

        // Dialogo de edicao com campos organizados
        JTextField edTitulo = criarCampo();
        edTitulo.setText(livro.getTitulo());
        JTextField edAutor = criarCampo();
        edAutor.setText(livro.getAutor());
        JTextField edCategoria = criarCampo();
        edCategoria.setText(livro.getCategoria());
        JTextField edQuantidade = criarCampo();
        edQuantidade.setText(String.valueOf(livro.getQuantTotal()));

        JPanel painelEdicao = new JPanel(new GridLayout(4, 2, 10, 8));
        painelEdicao.setBorder(new EmptyBorder(10, 5, 10, 5));
        painelEdicao.add(new JLabel("Título:"));
        painelEdicao.add(edTitulo);
        painelEdicao.add(new JLabel("Autor:"));
        painelEdicao.add(edAutor);
        painelEdicao.add(new JLabel("Categoria:"));
        painelEdicao.add(edCategoria);
        painelEdicao.add(new JLabel("Qtd. Total:"));
        painelEdicao.add(edQuantidade);

        int resultado = JOptionPane.showConfirmDialog(this, painelEdicao,
                "Editar Livro - " + codigo, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                int novaQtd = Integer.parseInt(edQuantidade.getText().trim());
                service.atualizarLivro(codigo, edTitulo.getText(), edAutor.getText(),
                        edCategoria.getText(), novaQtd);

                JOptionPane.showMessageDialog(this, "Livro atualizado com sucesso!");
                atualizarTabelaLivros(service.listarLivros());

            } catch (NumberFormatException ex) {
                mostrarErro("Número inválido informado para quantidade!");
            } catch (IllegalArgumentException ex) {
                mostrarErro(ex.getMessage());
            }
        }
    }

    private void excluirLivro() {
        int linhaSelecionada = tabelaLivros.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um livro na tabela para excluir!");
            return;
        }

        String codigo = (String) modeloTabelaLivros.getValueAt(linhaSelecionada, 0);
        Livro livro = service.buscarLivro(codigo);
        if (livro == null) return;

        int confirmacao = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir o livro '" + livro.getTitulo() + "'?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                service.excluirLivro(codigo);
                atualizarTabelaLivros(service.listarLivros());
                JOptionPane.showMessageDialog(this, "Livro excluído com sucesso!");
            } catch (IllegalArgumentException ex) {
                mostrarAviso(ex.getMessage());
            }
        }
    }

    // =======================================================
    // 3. ABA DE EMPRÉSTIMOS E DEVOLUÇÕES
    // =======================================================
    private JPanel criarAbaEmprestimos() {
        JPanel painel = new JPanel(new BorderLayout(0, 12));
        painel.setBackground(CINZA_FUNDO);
        painel.setBorder(new EmptyBorder(18, 18, 18, 18));

        // Titulo
        JLabel lblTituloEmp = new JLabel("Empréstimos e Devoluções");
        lblTituloEmp.setFont(FONTE_TITULO);
        lblTituloEmp.setForeground(AZUL_ESCURO);

        // Formulario de novo emprestimo
        JPanel formEmprestimo = new JPanel();
        formEmprestimo.setLayout(new BoxLayout(formEmprestimo, BoxLayout.Y_AXIS));
        formEmprestimo.setBackground(BRANCO);
        formEmprestimo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDA_CINZA, 1),
                new EmptyBorder(15, 20, 15, 20)
        ));

        JLabel lblForm = new JLabel("Registrar Novo Empréstimo");
        lblForm.setFont(FONTE_SUBTITULO);
        lblForm.setForeground(AZUL_ESCURO);
        lblForm.setAlignmentX(Component.LEFT_ALIGNMENT);
        formEmprestimo.add(lblForm);
        formEmprestimo.add(Box.createVerticalStrut(12));

        // Campos lado a lado
        JPanel linhaCampos = new JPanel(new GridLayout(1, 2, 15, 0));
        linhaCampos.setOpaque(false);
        linhaCampos.setAlignmentX(Component.LEFT_ALIGNMENT);
        linhaCampos.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));

        txtCodigoEmprestimo = criarCampo();
        txtNomeUsuario = criarCampo();

        linhaCampos.add(criarCampoComLabel("Código do Livro", txtCodigoEmprestimo));
        linhaCampos.add(criarCampoComLabel("Nome do Usuário / Aluno", txtNomeUsuario));
        formEmprestimo.add(linhaCampos);
        formEmprestimo.add(Box.createVerticalStrut(12));

        JPanel painelBtnEmp = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        painelBtnEmp.setOpaque(false);
        painelBtnEmp.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnRegistrar = criarBotao("Registrar Empréstimo", VERDE, BRANCO);
        painelBtnEmp.add(btnRegistrar);
        formEmprestimo.add(painelBtnEmp);

        // Topo com titulo e formulario
        JPanel topo = new JPanel(new BorderLayout(0, 10));
        topo.setOpaque(false);
        topo.add(lblTituloEmp, BorderLayout.NORTH);
        topo.add(formEmprestimo, BorderLayout.CENTER);

        painel.add(topo, BorderLayout.NORTH);

        // Tabela de emprestimos
        String[] colunas = {"Código Livro", "Título do Livro", "Usuário", "Status"};
        modeloTabelaEmprestimos = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaEmprestimos = criarTabela(modeloTabelaEmprestimos);

        JScrollPane scroll = new JScrollPane(tabelaEmprestimos);
        scroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(BORDA_CINZA, 1),
                        " Empréstimos Realizados ",
                        TitledBorder.LEFT, TitledBorder.TOP,
                        FONTE_SUBTITULO, AZUL_ESCURO
                ),
                new EmptyBorder(5, 5, 5, 5)
        ));
        scroll.getViewport().setBackground(BRANCO);
        painel.add(scroll, BorderLayout.CENTER);

        // Botao de devolucao
        JPanel painelSul = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        painelSul.setOpaque(false);

        JButton btnDevolver = criarBotao("Registrar Devolução", AZUL_CLARO, BRANCO);
        painelSul.add(btnDevolver);
        painel.add(painelSul, BorderLayout.SOUTH);

        // Acoes
        btnRegistrar.addActionListener(e -> registrarEmprestimo());
        btnDevolver.addActionListener(e -> registrarDevolucao());

        return painel;
    }

    private void registrarEmprestimo() {
        String usuario = txtNomeUsuario.getText().trim();

        try {
            service.registrarEmprestimo(txtCodigoEmprestimo.getText(), usuario);
        } catch (IllegalArgumentException ex) {
            mostrarAviso(ex.getMessage());
            return;
        }

        txtCodigoEmprestimo.setText("");
        txtNomeUsuario.setText("");

        atualizarTabelaEmprestimos();
        atualizarTabelaLivros(service.listarLivros());
        JOptionPane.showMessageDialog(this, "Empréstimo registrado com sucesso para " + usuario + "!");
    }

    private void registrarDevolucao() {
        int linhaSelecionada = tabelaEmprestimos.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um empréstimo na tabela para devolver.");
            return;
        }

        try {
            service.registrarDevolucao(linhaSelecionada);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
            return;
        }

        atualizarTabelaEmprestimos();
        atualizarTabelaLivros(service.listarLivros());
        JOptionPane.showMessageDialog(this, "Devolução concluída com sucesso!");
    }

    private void atualizarTabelaEmprestimos() {
        modeloTabelaEmprestimos.setRowCount(0);
        for (Emprestimo emp : service.listarEmprestimos()) {
            Object[] linha = {
                    emp.getLivro().getCodigo(),
                    emp.getLivro().getTitulo(),
                    emp.getNomeUsuario(),
                    emp.isAtivo() ? "Ativo" : "Devolvido"
            };
            modeloTabelaEmprestimos.addRow(linha);
        }
    }

    // =======================================================
    // 4. ABA DE RELATÓRIO
    // =======================================================
    private JPanel criarAbaRelatorio() {
        JPanel painel = new JPanel(new BorderLayout(0, 18));
        painel.setBackground(CINZA_FUNDO);
        painel.setBorder(new EmptyBorder(18, 18, 18, 18));

        // Titulo
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setOpaque(false);

        JLabel lblTituloRel = new JLabel("Relatório da Biblioteca");
        lblTituloRel.setFont(FONTE_TITULO);
        lblTituloRel.setForeground(AZUL_ESCURO);

        JButton btnAtualizar = criarBotao("Atualizar Dados", AZUL_MEDIO, BRANCO);
        btnAtualizar.addActionListener(e -> atualizarRelatorio());

        painelTopo.add(lblTituloRel, BorderLayout.WEST);
        painelTopo.add(btnAtualizar, BorderLayout.EAST);
        painel.add(painelTopo, BorderLayout.NORTH);

        // Cards de estatisticas
        JPanel cardsPanel = new JPanel(new GridLayout(1, 4, 14, 0));
        cardsPanel.setOpaque(false);
        cardsPanel.setPreferredSize(new Dimension(0, 110));

        lblStatTitulos = new JLabel("0");
        lblStatExemplares = new JLabel("0");
        lblStatDisponiveis = new JLabel("0");
        lblStatAtivos = new JLabel("0");

        cardsPanel.add(criarCardEstatistica("Total de Títulos", lblStatTitulos, AZUL_CLARO));
        cardsPanel.add(criarCardEstatistica("Total de Exemplares", lblStatExemplares, VERDE));
        cardsPanel.add(criarCardEstatistica("Disponíveis", lblStatDisponiveis, AMARELO));
        cardsPanel.add(criarCardEstatistica("Empréstimos Ativos", lblStatAtivos, VERMELHO));

        painel.add(cardsPanel, BorderLayout.CENTER);

        // Texto explicativo na parte inferior
        JPanel painelInferior = new JPanel(new BorderLayout());
        painelInferior.setOpaque(false);

        JLabel lblDica = new JLabel("Os dados são atualizados automaticamente ao abrir esta aba, ou clique em \"Atualizar Dados\".");
        lblDica.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblDica.setForeground(TEXTO_CINZA);
        painelInferior.add(lblDica, BorderLayout.SOUTH);

        painel.add(painelInferior, BorderLayout.SOUTH);

        return painel;
    }

    private void atualizarRelatorio() {
        lblStatTitulos.setText(String.valueOf(service.getTotalTitulos()));
        lblStatExemplares.setText(String.valueOf(service.getTotalExemplares()));
        lblStatDisponiveis.setText(String.valueOf(service.getTotalDisponiveis()));
        lblStatAtivos.setText(String.valueOf(service.getTotalEmprestimosAtivos()));
    }

    // =======================================================
    // AUXILIARES DE MENSAGEM
    // =======================================================
    private void mostrarAviso(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    private void mostrarErro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}
