package view;

import model.Emprestimo;
import model.Livro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class TelaPrincipal extends JFrame {

    // ========================= DADOS (ArrayList) =========================
    private final ArrayList<Livro> livros = new ArrayList<>();
    private final ArrayList<Emprestimo> emprestimos = new ArrayList<>();

    // ========================= CORES DO TEMA =========================
    private static final Color COR_PRIMARIA      = new Color(26, 58, 92);
    private static final Color COR_SECUNDARIA    = new Color(45, 85, 130);
    private static final Color COR_DESTAQUE      = new Color(52, 152, 219);
    private static final Color COR_SUCESSO       = new Color(39, 174, 96);
    private static final Color COR_ALERTA        = new Color(231, 76, 60);
    private static final Color COR_FUNDO         = new Color(236, 240, 241);
    private static final Color COR_PAINEL        = new Color(255, 255, 255);
    private static final Color COR_TEXTO         = new Color(44, 62, 80);
    private static final Color COR_TEXTO_CLARO   = new Color(255, 255, 255);
    private static final Color COR_BORDA         = new Color(189, 195, 199);
    private static final Color COR_HOVER         = new Color(41, 128, 185);
    private static final Color COR_AMARELO       = new Color(243, 156, 18);

    // ========================= FONTES =========================
    private static final Font FONTE_TITULO   = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font FONTE_SUBTITULO = new Font("Segoe UI", Font.BOLD, 16);
    private static final Font FONTE_BOTAO    = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font FONTE_LABEL    = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONTE_CAMPO    = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONTE_TABELA   = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font FONTE_HEADER   = new Font("Segoe UI", Font.BOLD, 12);
    private static final Font FONTE_STAT     = new Font("Segoe UI", Font.BOLD, 28);
    private static final Font FONTE_STAT_LBL = new Font("Segoe UI", Font.PLAIN, 11);

    // ========================= COMPONENTES PRINCIPAIS =========================
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel painelConteudo = new JPanel(cardLayout);

    // --- Tela Cadastro ---
    private JTextField campoCodigo, campoTitulo, campoAutor, campoCategoria, campoQuantidade;

    // --- Tela Consulta ---
    private JTextField campoPesquisa;
    private JTable tabelaLivros;
    private DefaultTableModel modeloTabelaLivros;

    // --- Tela Empréstimos ---
    private JTextField campoCodigoEmprestimo, campoNomeUsuario;
    private JTable tabelaEmprestimos;
    private DefaultTableModel modeloTabelaEmprestimos;

    // --- Tela Relatório ---
    private JLabel lblTotalTitulos, lblTotalExemplares, lblExemplaresDisponiveis, lblEmprestimosAtivos;
    private JTable tabelaRelatorio;
    private DefaultTableModel modeloTabelaRelatorio;

    // ========================= CONSTRUTOR =========================
    public TelaPrincipal() {
        configurarJanela();
        inicializarComponentes();
        setVisible(true);
    }

    private void configurarJanela() {
        setTitle("📚 Gerenciador de Biblioteca Acadêmica");
        setSize(1000, 700);
        setMinimumSize(new Dimension(900, 650));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(COR_FUNDO);
    }

    private void inicializarComponentes() {
        // Barra lateral de navegação
        add(criarBarraLateral(), BorderLayout.WEST);

        // Painel de conteúdo com CardLayout
        painelConteudo.setBackground(COR_FUNDO);
        painelConteudo.add(criarTelaInicial(), "INICIO");
        painelConteudo.add(criarTelaCadastro(), "CADASTRO");
        painelConteudo.add(criarTelaConsulta(), "CONSULTA");
        painelConteudo.add(criarTelaEmprestimos(), "EMPRESTIMOS");
        painelConteudo.add(criarTelaRelatorio(), "RELATORIO");
        add(painelConteudo, BorderLayout.CENTER);

        cardLayout.show(painelConteudo, "INICIO");
    }

    // ========================= BARRA LATERAL =========================
    private JPanel criarBarraLateral() {
        JPanel barra = new JPanel();
        barra.setLayout(new BoxLayout(barra, BoxLayout.Y_AXIS));
        barra.setBackground(COR_PRIMARIA);
        barra.setPreferredSize(new Dimension(220, 0));
        barra.setBorder(new EmptyBorder(0, 0, 0, 0));

        // Logo / Título
        JPanel painelLogo = new JPanel();
        painelLogo.setLayout(new BoxLayout(painelLogo, BoxLayout.Y_AXIS));
        painelLogo.setBackground(COR_PRIMARIA);
        painelLogo.setBorder(new EmptyBorder(25, 15, 25, 15));
        painelLogo.setMaximumSize(new Dimension(220, 100));

        JLabel lblIcone = new JLabel("\uD83D\uDCDA");
        lblIcone.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        lblIcone.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitulo = new JLabel("Biblioteca");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(COR_TEXTO_CLARO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubtitulo = new JLabel("Acadêmica");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitulo.setForeground(new Color(149, 165, 181));
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        painelLogo.add(lblIcone);
        painelLogo.add(Box.createVerticalStrut(5));
        painelLogo.add(lblTitulo);
        painelLogo.add(lblSubtitulo);
        barra.add(painelLogo);

        // Separador
        JSeparator sep = new JSeparator();
        sep.setForeground(COR_SECUNDARIA);
        sep.setMaximumSize(new Dimension(200, 1));
        barra.add(sep);
        barra.add(Box.createVerticalStrut(15));

        // Botões de navegação
        barra.add(criarBotaoNavegacao("\uD83C\uDFE0  Início", "INICIO"));
        barra.add(Box.createVerticalStrut(5));
        barra.add(criarBotaoNavegacao("\u2795  Cadastrar Livro", "CADASTRO"));
        barra.add(Box.createVerticalStrut(5));
        barra.add(criarBotaoNavegacao("\uD83D\uDD0D  Consultar Livros", "CONSULTA"));
        barra.add(Box.createVerticalStrut(5));
        barra.add(criarBotaoNavegacao("\uD83D\uDD04  Empréstimos", "EMPRESTIMOS"));
        barra.add(Box.createVerticalStrut(5));
        barra.add(criarBotaoNavegacao("\uD83D\uDCCA  Relatório", "RELATORIO"));

        barra.add(Box.createVerticalGlue());

        // Rodapé
        JLabel lblRodape = new JLabel("Eng. Software - 2° Sem.");
        lblRodape.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        lblRodape.setForeground(new Color(149, 165, 181));
        lblRodape.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblRodape.setBorder(new EmptyBorder(0, 0, 15, 0));
        barra.add(lblRodape);

        return barra;
    }

    private JPanel criarBotaoNavegacao(String texto, String tela) {
        JPanel btn = new JPanel(new BorderLayout());
        btn.setBackground(COR_PRIMARIA);
        btn.setMaximumSize(new Dimension(220, 42));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(10, 20, 10, 15));

        JLabel lbl = new JLabel(texto);
        lbl.setFont(FONTE_BOTAO);
        lbl.setForeground(COR_TEXTO_CLARO);
        btn.add(lbl, BorderLayout.CENTER);

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(COR_SECUNDARIA);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(COR_PRIMARIA);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (tela.equals("CONSULTA")) atualizarTabelaLivros(null);
                if (tela.equals("RELATORIO")) atualizarRelatorio();
                if (tela.equals("EMPRESTIMOS")) atualizarTabelaEmprestimos();
                cardLayout.show(painelConteudo, tela);
            }
        });

        return btn;
    }

    // ========================= TELA INICIAL =========================
    private JPanel criarTelaInicial() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(COR_FUNDO);

        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setBackground(COR_FUNDO);
        centro.setBorder(new EmptyBorder(30, 40, 30, 40));

        JLabel icone = new JLabel("\uD83D\uDCDA");
        icone.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 64));
        icone.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titulo = new JLabel("Bem-vindo ao Gerenciador de Biblioteca");
        titulo.setFont(FONTE_TITULO);
        titulo.setForeground(COR_PRIMARIA);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel desc = new JLabel("<html><center>Sistema para gerenciar o cadastro de livros e controlar<br>"
                + "empréstimos e devoluções da biblioteca acadêmica.</center></html>");
        desc.setFont(FONTE_LABEL);
        desc.setForeground(COR_TEXTO);
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);

        centro.add(icone);
        centro.add(Box.createVerticalStrut(20));
        centro.add(titulo);
        centro.add(Box.createVerticalStrut(10));
        centro.add(desc);
        centro.add(Box.createVerticalStrut(40));

        // Cards de atalho
        JPanel cardsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        cardsPanel.setBackground(COR_FUNDO);
        cardsPanel.setMaximumSize(new Dimension(750, 120));
        cardsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        cardsPanel.add(criarCardInicial("\u2795", "Cadastrar", "Novo livro", COR_SUCESSO, "CADASTRO"));
        cardsPanel.add(criarCardInicial("\uD83D\uDD0D", "Consultar", "Buscar livros", COR_DESTAQUE, "CONSULTA"));
        cardsPanel.add(criarCardInicial("\uD83D\uDD04", "Empréstimos", "Gerenciar", COR_AMARELO, "EMPRESTIMOS"));
        cardsPanel.add(criarCardInicial("\uD83D\uDCCA", "Relatório", "Estatísticas", COR_ALERTA, "RELATORIO"));

        centro.add(cardsPanel);

        painel.add(centro);
        return painel;
    }

    private JPanel criarCardInicial(String icone, String titulo, String desc, Color cor, String tela) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(COR_PAINEL);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_BORDA, 1),
                new EmptyBorder(15, 15, 15, 15)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblIcone = new JLabel(icone);
        lblIcone.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        lblIcone.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(FONTE_BOTAO);
        lblTitulo.setForeground(cor);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblDesc = new JLabel(desc);
        lblDesc.setFont(FONTE_STAT_LBL);
        lblDesc.setForeground(COR_TEXTO);
        lblDesc.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(lblIcone);
        card.add(Box.createVerticalStrut(8));
        card.add(lblTitulo);
        card.add(Box.createVerticalStrut(3));
        card.add(lblDesc);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(cor, 2),
                        new EmptyBorder(14, 14, 14, 14)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COR_BORDA, 1),
                        new EmptyBorder(15, 15, 15, 15)
                ));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (tela.equals("CONSULTA")) atualizarTabelaLivros(null);
                if (tela.equals("RELATORIO")) atualizarRelatorio();
                if (tela.equals("EMPRESTIMOS")) atualizarTabelaEmprestimos();
                cardLayout.show(painelConteudo, tela);
            }
        });

        return card;
    }

    // ========================= TELA CADASTRO =========================
    private JPanel criarTelaCadastro() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(COR_FUNDO);

        JPanel formulario = new JPanel();
        formulario.setLayout(new BoxLayout(formulario, BoxLayout.Y_AXIS));
        formulario.setBackground(COR_PAINEL);
        formulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_BORDA, 1),
                new EmptyBorder(30, 40, 30, 40)
        ));
        formulario.setMaximumSize(new Dimension(550, 500));
        formulario.setPreferredSize(new Dimension(500, 460));

        // Título
        JLabel titulo = criarTituloPagina("\u2795 Cadastro de Livro");
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        formulario.add(titulo);
        formulario.add(Box.createVerticalStrut(5));

        JLabel descricao = new JLabel("Preencha os dados do novo livro abaixo.");
        descricao.setFont(FONTE_LABEL);
        descricao.setForeground(new Color(127, 140, 141));
        descricao.setAlignmentX(Component.LEFT_ALIGNMENT);
        formulario.add(descricao);
        formulario.add(Box.createVerticalStrut(20));

        // Campos
        campoCodigo = criarCampoTexto();
        campoTitulo = criarCampoTexto();
        campoAutor = criarCampoTexto();
        campoCategoria = criarCampoTexto();
        campoQuantidade = criarCampoTexto();

        formulario.add(criarGrupoCampo("Código *", campoCodigo));
        formulario.add(Box.createVerticalStrut(12));
        formulario.add(criarGrupoCampo("Título *", campoTitulo));
        formulario.add(Box.createVerticalStrut(12));
        formulario.add(criarGrupoCampo("Autor *", campoAutor));
        formulario.add(Box.createVerticalStrut(12));
        formulario.add(criarGrupoCampo("Categoria", campoCategoria));
        formulario.add(Box.createVerticalStrut(12));
        formulario.add(criarGrupoCampo("Quantidade de Exemplares *", campoQuantidade));
        formulario.add(Box.createVerticalStrut(25));

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        painelBotoes.setBackground(COR_PAINEL);
        painelBotoes.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelBotoes.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JButton btnLimpar = criarBotao("Limpar", COR_BORDA, COR_TEXTO);
        btnLimpar.addActionListener(e -> limparCamposCadastro());

        JButton btnCadastrar = criarBotao("Cadastrar Livro", COR_SUCESSO, COR_TEXTO_CLARO);
        btnCadastrar.addActionListener(e -> cadastrarLivro());

        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnCadastrar);
        formulario.add(painelBotoes);

        painel.add(formulario);
        return painel;
    }

    // ========================= TELA CONSULTA =========================
    private JPanel criarTelaConsulta() {
        JPanel painel = new JPanel(new BorderLayout(0, 15));
        painel.setBackground(COR_FUNDO);
        painel.setBorder(new EmptyBorder(25, 25, 25, 25));

        // Topo - Título e pesquisa
        JPanel topo = new JPanel(new BorderLayout(15, 10));
        topo.setBackground(COR_FUNDO);

        JLabel titulo = criarTituloPagina("\uD83D\uDD0D Consulta de Livros");
        topo.add(titulo, BorderLayout.NORTH);

        JPanel painelPesquisa = new JPanel(new BorderLayout(10, 0));
        painelPesquisa.setBackground(COR_PAINEL);
        painelPesquisa.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_BORDA, 1),
                new EmptyBorder(10, 15, 10, 15)
        ));

        campoPesquisa = criarCampoTexto();
        campoPesquisa.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        campoPesquisa.setToolTipText("Pesquisar por código ou título...");
        // Placeholder
        campoPesquisa.setText("Pesquisar por código ou título...");
        campoPesquisa.setForeground(Color.GRAY);
        campoPesquisa.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (campoPesquisa.getText().equals("Pesquisar por código ou título...")) {
                    campoPesquisa.setText("");
                    campoPesquisa.setForeground(COR_TEXTO);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (campoPesquisa.getText().isEmpty()) {
                    campoPesquisa.setText("Pesquisar por código ou título...");
                    campoPesquisa.setForeground(Color.GRAY);
                }
            }
        });

        JButton btnPesquisar = criarBotao("Pesquisar", COR_DESTAQUE, COR_TEXTO_CLARO);
        btnPesquisar.addActionListener(e -> pesquisarLivros());

        JButton btnMostrarTodos = criarBotao("Mostrar Todos", COR_SECUNDARIA, COR_TEXTO_CLARO);
        btnMostrarTodos.addActionListener(e -> {
            campoPesquisa.setText("Pesquisar por código ou título...");
            campoPesquisa.setForeground(Color.GRAY);
            atualizarTabelaLivros(null);
        });

        JPanel painelBotoesPesquisa = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        painelBotoesPesquisa.setBackground(COR_PAINEL);
        painelBotoesPesquisa.add(btnPesquisar);
        painelBotoesPesquisa.add(btnMostrarTodos);

        painelPesquisa.add(campoPesquisa, BorderLayout.CENTER);
        painelPesquisa.add(painelBotoesPesquisa, BorderLayout.EAST);

        topo.add(painelPesquisa, BorderLayout.CENTER);
        painel.add(topo, BorderLayout.NORTH);

        // Tabela de livros
        String[] colunas = {"Código", "Título", "Autor", "Categoria", "Total", "Disponível"};
        modeloTabelaLivros = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaLivros = criarTabela(modeloTabelaLivros);

        JScrollPane scroll = new JScrollPane(tabelaLivros);
        scroll.setBorder(BorderFactory.createLineBorder(COR_BORDA, 1));
        scroll.getViewport().setBackground(COR_PAINEL);
        painel.add(scroll, BorderLayout.CENTER);

        // Botões de ação
        JPanel painelAcoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        painelAcoes.setBackground(COR_FUNDO);

        JButton btnEditar = criarBotao("✏ Editar", COR_AMARELO, COR_TEXTO_CLARO);
        btnEditar.addActionListener(e -> editarLivro());

        JButton btnExcluir = criarBotao("🗑 Excluir", COR_ALERTA, COR_TEXTO_CLARO);
        btnExcluir.addActionListener(e -> excluirLivro());

        painelAcoes.add(btnEditar);
        painelAcoes.add(btnExcluir);
        painel.add(painelAcoes, BorderLayout.SOUTH);

        return painel;
    }

    // ========================= TELA EMPRÉSTIMOS =========================
    private JPanel criarTelaEmprestimos() {
        JPanel painel = new JPanel(new BorderLayout(0, 15));
        painel.setBackground(COR_FUNDO);
        painel.setBorder(new EmptyBorder(25, 25, 25, 25));

        JLabel titulo = criarTituloPagina("\uD83D\uDD04 Empréstimos e Devoluções");
        painel.add(titulo, BorderLayout.NORTH);

        // Centro - formulário + tabela
        JPanel centro = new JPanel(new BorderLayout(0, 15));
        centro.setBackground(COR_FUNDO);

        // Formulário de empréstimo
        JPanel formEmprestimo = new JPanel();
        formEmprestimo.setLayout(new BoxLayout(formEmprestimo, BoxLayout.Y_AXIS));
        formEmprestimo.setBackground(COR_PAINEL);
        formEmprestimo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_BORDA, 1),
                new EmptyBorder(20, 25, 20, 25)
        ));

        JLabel lblForm = new JLabel("Novo Empréstimo");
        lblForm.setFont(FONTE_SUBTITULO);
        lblForm.setForeground(COR_PRIMARIA);
        lblForm.setAlignmentX(Component.LEFT_ALIGNMENT);
        formEmprestimo.add(lblForm);
        formEmprestimo.add(Box.createVerticalStrut(15));

        JPanel linhaForm = new JPanel(new GridLayout(1, 2, 15, 0));
        linhaForm.setBackground(COR_PAINEL);
        linhaForm.setAlignmentX(Component.LEFT_ALIGNMENT);
        linhaForm.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));

        campoCodigoEmprestimo = criarCampoTexto();
        campoNomeUsuario = criarCampoTexto();

        linhaForm.add(criarGrupoCampo("Código do Livro", campoCodigoEmprestimo));
        linhaForm.add(criarGrupoCampo("Nome do Usuário", campoNomeUsuario));
        formEmprestimo.add(linhaForm);
        formEmprestimo.add(Box.createVerticalStrut(15));

        JPanel painelBotoesEmp = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        painelBotoesEmp.setBackground(COR_PAINEL);
        painelBotoesEmp.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnEmprestar = criarBotao("📖 Registrar Empréstimo", COR_SUCESSO, COR_TEXTO_CLARO);
        btnEmprestar.addActionListener(e -> registrarEmprestimo());

        painelBotoesEmp.add(btnEmprestar);
        formEmprestimo.add(painelBotoesEmp);

        centro.add(formEmprestimo, BorderLayout.NORTH);

        // Tabela de empréstimos ativos
        String[] colunasEmp = {"Código Livro", "Título", "Usuário", "Status"};
        modeloTabelaEmprestimos = new DefaultTableModel(colunasEmp, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaEmprestimos = criarTabela(modeloTabelaEmprestimos);

        JScrollPane scrollEmp = new JScrollPane(tabelaEmprestimos);
        scrollEmp.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(COR_BORDA, 1),
                        " Empréstimos Registrados ",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        FONTE_SUBTITULO,
                        COR_PRIMARIA
                ),
                new EmptyBorder(5, 5, 5, 5)
        ));
        scrollEmp.getViewport().setBackground(COR_PAINEL);
        centro.add(scrollEmp, BorderLayout.CENTER);

        painel.add(centro, BorderLayout.CENTER);

        // Botão devolver
        JPanel painelDevolver = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        painelDevolver.setBackground(COR_FUNDO);

        JButton btnDevolver = criarBotao("↩ Registrar Devolução", COR_DESTAQUE, COR_TEXTO_CLARO);
        btnDevolver.addActionListener(e -> registrarDevolucao());
        painelDevolver.add(btnDevolver);

        painel.add(painelDevolver, BorderLayout.SOUTH);

        return painel;
    }

    // ========================= TELA RELATÓRIO =========================
    private JPanel criarTelaRelatorio() {
        JPanel painel = new JPanel(new BorderLayout(0, 15));
        painel.setBackground(COR_FUNDO);
        painel.setBorder(new EmptyBorder(25, 25, 25, 25));

        JLabel titulo = criarTituloPagina("\uD83D\uDCCA Relatório da Biblioteca");
        painel.add(titulo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(0, 15));
        centro.setBackground(COR_FUNDO);

        // Cards de estatísticas
        JPanel cardsStats = new JPanel(new GridLayout(1, 4, 15, 0));
        cardsStats.setBackground(COR_FUNDO);
        cardsStats.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
        cardsStats.setPreferredSize(new Dimension(0, 110));

        lblTotalTitulos = new JLabel("0");
        lblTotalExemplares = new JLabel("0");
        lblExemplaresDisponiveis = new JLabel("0");
        lblEmprestimosAtivos = new JLabel("0");

        cardsStats.add(criarCardEstatistica("Total de Títulos", lblTotalTitulos, COR_DESTAQUE));
        cardsStats.add(criarCardEstatistica("Total Exemplares", lblTotalExemplares, COR_SUCESSO));
        cardsStats.add(criarCardEstatistica("Disponíveis", lblExemplaresDisponiveis, COR_AMARELO));
        cardsStats.add(criarCardEstatistica("Empréstimos Ativos", lblEmprestimosAtivos, COR_ALERTA));

        centro.add(cardsStats, BorderLayout.NORTH);

        // Tabela detalhada
        String[] colunasRel = {"Código", "Título", "Autor", "Total", "Disponível", "Emprestados"};
        modeloTabelaRelatorio = new DefaultTableModel(colunasRel, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaRelatorio = criarTabela(modeloTabelaRelatorio);

        JScrollPane scrollRel = new JScrollPane(tabelaRelatorio);
        scrollRel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(COR_BORDA, 1),
                        " Detalhamento por Livro ",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        FONTE_SUBTITULO,
                        COR_PRIMARIA
                ),
                new EmptyBorder(5, 5, 5, 5)
        ));
        scrollRel.getViewport().setBackground(COR_PAINEL);
        centro.add(scrollRel, BorderLayout.CENTER);

        painel.add(centro, BorderLayout.CENTER);

        return painel;
    }

    private JPanel criarCardEstatistica(String label, JLabel valorLabel, Color cor) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(COR_PAINEL);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(3, 0, 0, 0, cor),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COR_BORDA, 1),
                        new EmptyBorder(15, 15, 15, 15)
                )
        ));

        valorLabel.setFont(FONTE_STAT);
        valorLabel.setForeground(cor);
        valorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblDesc = new JLabel(label);
        lblDesc.setFont(FONTE_STAT_LBL);
        lblDesc.setForeground(COR_TEXTO);
        lblDesc.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalGlue());
        card.add(valorLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(lblDesc);
        card.add(Box.createVerticalGlue());

        return card;
    }

    // ========================= COMPONENTES UTILITÁRIOS =========================
    private JLabel criarTituloPagina(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(FONTE_TITULO);
        lbl.setForeground(COR_PRIMARIA);
        lbl.setBorder(new EmptyBorder(0, 0, 10, 0));
        return lbl;
    }

    private JTextField criarCampoTexto() {
        JTextField campo = new JTextField();
        campo.setFont(FONTE_CAMPO);
        campo.setForeground(COR_TEXTO);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_BORDA, 1),
                new EmptyBorder(8, 10, 8, 10)
        ));
        return campo;
    }

    private JPanel criarGrupoCampo(String labelTexto, JTextField campo) {
        JPanel grupo = new JPanel();
        grupo.setLayout(new BoxLayout(grupo, BoxLayout.Y_AXIS));
        grupo.setBackground(COR_PAINEL);
        grupo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lbl = new JLabel(labelTexto);
        lbl.setFont(FONTE_LABEL);
        lbl.setForeground(COR_TEXTO);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));

        grupo.add(lbl);
        grupo.add(Box.createVerticalStrut(4));
        grupo.add(campo);

        return grupo;
    }

    private JButton criarBotao(String texto, Color corFundo, Color corTexto) {
        JButton btn = new JButton(texto);
        btn.setFont(FONTE_BOTAO);
        btn.setBackground(corFundo);
        btn.setForeground(corTexto);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(8, 18, 8, 18));
        btn.setOpaque(true);

        Color corHover = corFundo.darker();
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(corHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(corFundo);
            }
        });

        return btn;
    }

    private JTable criarTabela(DefaultTableModel modelo) {
        JTable tabela = new JTable(modelo);
        tabela.setFont(FONTE_TABELA);
        tabela.setRowHeight(32);
        tabela.setSelectionBackground(new Color(214, 234, 248));
        tabela.setSelectionForeground(COR_TEXTO);
        tabela.setGridColor(new Color(230, 230, 230));
        tabela.setShowGrid(true);
        tabela.setIntercellSpacing(new Dimension(1, 1));
        tabela.getTableHeader().setReorderingAllowed(false);

        // Header estilizado
        JTableHeader header = tabela.getTableHeader();
        header.setFont(FONTE_HEADER);
        header.setBackground(COR_PRIMARIA);
        header.setForeground(COR_TEXTO_CLARO);
        header.setPreferredSize(new Dimension(0, 36));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        // Centralizar células
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tabela.getColumnCount(); i++) {
            tabela.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }

        return tabela;
    }

    // ========================= LÓGICA DE NEGÓCIO =========================

    // --- Cadastro ---
    private void cadastrarLivro() {
        String codigo = campoCodigo.getText().trim();
        String titulo = campoTitulo.getText().trim();
        String autor = campoAutor.getText().trim();
        String categoria = campoCategoria.getText().trim();
        String quantidadeStr = campoQuantidade.getText().trim();

        // Validações
        if (codigo.isEmpty() || titulo.isEmpty() || autor.isEmpty() || quantidadeStr.isEmpty()) {
            mostrarErro("Preencha todos os campos obrigatórios (*).");
            return;
        }

        // Verificar código duplicado
        for (Livro l : livros) {
            if (l.getCodigo().equalsIgnoreCase(codigo)) {
                mostrarErro("Já existe um livro cadastrado com o código \"" + codigo + "\".");
                return;
            }
        }

        int quantidade;
        try {
            quantidade = Integer.parseInt(quantidadeStr);
        } catch (NumberFormatException ex) {
            mostrarErro("A quantidade deve ser um número inteiro válido.");
            return;
        }

        if (quantidade <= 0) {
            mostrarErro("A quantidade deve ser maior que zero.");
            return;
        }

        Livro novo = new Livro(codigo, titulo, autor, categoria, quantidade);
        livros.add(novo);

        mostrarSucesso("Livro \"" + titulo + "\" cadastrado com sucesso!");
        limparCamposCadastro();
    }

    private void limparCamposCadastro() {
        campoCodigo.setText("");
        campoTitulo.setText("");
        campoAutor.setText("");
        campoCategoria.setText("");
        campoQuantidade.setText("");
        campoCodigo.requestFocus();
    }

    // --- Consulta ---
    private void pesquisarLivros() {
        String termo = campoPesquisa.getText().trim();
        if (termo.isEmpty() || termo.equals("Pesquisar por código ou título...")) {
            atualizarTabelaLivros(null);
            return;
        }

        ArrayList<Livro> resultado = new ArrayList<>();
        for (Livro l : livros) {
            if (l.getCodigo().equalsIgnoreCase(termo)
                    || l.getTitulo().toLowerCase().contains(termo.toLowerCase())) {
                resultado.add(l);
            }
        }
        atualizarTabelaLivros(resultado);

        if (resultado.isEmpty()) {
            mostrarInfo("Nenhum livro encontrado para \"" + termo + "\".");
        }
    }

    private void atualizarTabelaLivros(ArrayList<Livro> lista) {
        modeloTabelaLivros.setRowCount(0);
        ArrayList<Livro> fonte = (lista != null) ? lista : livros;
        for (Livro l : fonte) {
            modeloTabelaLivros.addRow(new Object[]{
                    l.getCodigo(),
                    l.getTitulo(),
                    l.getAutor(),
                    l.getCategoria(),
                    l.getQuantTotal(),
                    l.getQuantDisponivel()
            });
        }
    }

    // --- Edição ---
    private void editarLivro() {
        int linhaSelecionada = tabelaLivros.getSelectedRow();
        if (linhaSelecionada == -1) {
            mostrarErro("Selecione um livro na tabela para editar.");
            return;
        }

        String codigoSelecionado = (String) modeloTabelaLivros.getValueAt(linhaSelecionada, 0);
        Livro livro = buscarLivroPorCodigo(codigoSelecionado);
        if (livro == null) return;

        // Painel de edição com campos preenchidos
        JTextField edTitulo = new JTextField(livro.getTitulo());
        JTextField edAutor = new JTextField(livro.getAutor());
        JTextField edCategoria = new JTextField(livro.getCategoria());
        JTextField edQuantidade = new JTextField(String.valueOf(livro.getQuantTotal()));

        JPanel painelEdicao = new JPanel(new GridLayout(4, 2, 10, 8));
        painelEdicao.setBorder(new EmptyBorder(10, 10, 10, 10));
        painelEdicao.add(new JLabel("Título:"));
        painelEdicao.add(edTitulo);
        painelEdicao.add(new JLabel("Autor:"));
        painelEdicao.add(edAutor);
        painelEdicao.add(new JLabel("Categoria:"));
        painelEdicao.add(edCategoria);
        painelEdicao.add(new JLabel("Qtd. Total:"));
        painelEdicao.add(edQuantidade);

        int result = JOptionPane.showConfirmDialog(this, painelEdicao,
                "Editar Livro - " + codigoSelecionado, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String novoTitulo = edTitulo.getText().trim();
            String novoAutor = edAutor.getText().trim();
            String novaCategoria = edCategoria.getText().trim();
            String novaQtdStr = edQuantidade.getText().trim();

            if (novoTitulo.isEmpty() || novoAutor.isEmpty() || novaQtdStr.isEmpty()) {
                mostrarErro("Título, autor e quantidade são obrigatórios.");
                return;
            }

            int novaQtd;
            try {
                novaQtd = Integer.parseInt(novaQtdStr);
            } catch (NumberFormatException ex) {
                mostrarErro("A quantidade deve ser um número inteiro válido.");
                return;
            }

            if (novaQtd < 0) {
                mostrarErro("A quantidade não pode ser negativa.");
                return;
            }

            // Calcular empréstimos ativos deste livro
            int emprestimosAtivosDoLivro = contarEmprestimosAtivos(livro);
            if (novaQtd < emprestimosAtivosDoLivro) {
                mostrarErro("A quantidade total não pode ser menor que os empréstimos ativos ("
                        + emprestimosAtivosDoLivro + ").");
                return;
            }

            livro.setTitulo(novoTitulo);
            livro.setAutor(novoAutor);
            livro.setCategoria(novaCategoria);
            int diferenca = novaQtd - livro.getQuantTotal();
            livro.setQuantTotal(novaQtd);
            livro.setQuantDisponivel(livro.getQuantDisponivel() + diferenca);

            atualizarTabelaLivros(null);
            mostrarSucesso("Livro atualizado com sucesso!");
        }
    }

    // --- Exclusão ---
    private void excluirLivro() {
        int linhaSelecionada = tabelaLivros.getSelectedRow();
        if (linhaSelecionada == -1) {
            mostrarErro("Selecione um livro na tabela para excluir.");
            return;
        }

        String codigoSelecionado = (String) modeloTabelaLivros.getValueAt(linhaSelecionada, 0);
        Livro livro = buscarLivroPorCodigo(codigoSelecionado);
        if (livro == null) return;

        // Verificar empréstimos ativos
        if (contarEmprestimosAtivos(livro) > 0) {
            mostrarErro("Não é possível excluir o livro \"" + livro.getTitulo()
                    + "\" pois possui empréstimos ativos.");
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir o livro \"" + livro.getTitulo() + "\"?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirmacao == JOptionPane.YES_OPTION) {
            livros.remove(livro);
            // Remover empréstimos inativos associados
            emprestimos.removeIf(emp -> emp.getLivro().getCodigo().equals(codigoSelecionado));
            atualizarTabelaLivros(null);
            mostrarSucesso("Livro excluído com sucesso!");
        }
    }

    // --- Empréstimos ---
    private void registrarEmprestimo() {
        String codigo = campoCodigoEmprestimo.getText().trim();
        String nomeUsuario = campoNomeUsuario.getText().trim();

        if (codigo.isEmpty() || nomeUsuario.isEmpty()) {
            mostrarErro("Preencha o código do livro e o nome do usuário.");
            return;
        }

        Livro livro = buscarLivroPorCodigo(codigo);
        if (livro == null) {
            mostrarErro("Livro com código \"" + codigo + "\" não encontrado.");
            return;
        }

        if (livro.getQuantDisponivel() <= 0) {
            mostrarErro("Não há exemplares disponíveis do livro \"" + livro.getTitulo() + "\".");
            return;
        }

        Emprestimo emp = new Emprestimo(livro, nomeUsuario);
        emprestimos.add(emp);
        livro.setQuantDisponivel(livro.getQuantDisponivel() - 1);

        campoCodigoEmprestimo.setText("");
        campoNomeUsuario.setText("");

        atualizarTabelaEmprestimos();
        mostrarSucesso("Empréstimo registrado: \"" + livro.getTitulo() + "\" para " + nomeUsuario + ".");
    }

    private void registrarDevolucao() {
        int linhaSelecionada = tabelaEmprestimos.getSelectedRow();
        if (linhaSelecionada == -1) {
            mostrarErro("Selecione um empréstimo na tabela para devolver.");
            return;
        }

        // Encontrar o empréstimo correspondente (considerar apenas ativos)
        String codigoLivro = (String) modeloTabelaEmprestimos.getValueAt(linhaSelecionada, 0);
        String nomeUsuario = (String) modeloTabelaEmprestimos.getValueAt(linhaSelecionada, 2);
        String status = (String) modeloTabelaEmprestimos.getValueAt(linhaSelecionada, 3);

        if (!"Ativo".equals(status)) {
            mostrarErro("Este empréstimo já foi devolvido.");
            return;
        }

        // Encontrar o empréstimo ativo correspondente
        for (Emprestimo emp : emprestimos) {
            if (emp.isAtivo()
                    && emp.getLivro().getCodigo().equals(codigoLivro)
                    && emp.getNomeUsuario().equals(nomeUsuario)) {
                emp.setAtivo(false);
                emp.getLivro().setQuantDisponivel(emp.getLivro().getQuantDisponivel() + 1);
                atualizarTabelaEmprestimos();
                mostrarSucesso("Devolução registrada: \"" + emp.getLivro().getTitulo()
                        + "\" de " + nomeUsuario + ".");
                return;
            }
        }
    }

    private void atualizarTabelaEmprestimos() {
        modeloTabelaEmprestimos.setRowCount(0);
        for (Emprestimo emp : emprestimos) {
            modeloTabelaEmprestimos.addRow(new Object[]{
                    emp.getLivro().getCodigo(),
                    emp.getLivro().getTitulo(),
                    emp.getNomeUsuario(),
                    emp.isAtivo() ? "Ativo" : "Devolvido"
            });
        }
    }

    // --- Relatório ---
    private void atualizarRelatorio() {
        int totalTitulos = livros.size();
        int totalExemplares = 0;
        int totalDisponiveis = 0;
        int totalEmprestimosAtivos = 0;

        modeloTabelaRelatorio.setRowCount(0);

        for (Livro l : livros) {
            totalExemplares += l.getQuantTotal();
            totalDisponiveis += l.getQuantDisponivel();
            int emprestados = l.getQuantTotal() - l.getQuantDisponivel();
            totalEmprestimosAtivos += emprestados;

            modeloTabelaRelatorio.addRow(new Object[]{
                    l.getCodigo(),
                    l.getTitulo(),
                    l.getAutor(),
                    l.getQuantTotal(),
                    l.getQuantDisponivel(),
                    emprestados
            });
        }

        lblTotalTitulos.setText(String.valueOf(totalTitulos));
        lblTotalExemplares.setText(String.valueOf(totalExemplares));
        lblExemplaresDisponiveis.setText(String.valueOf(totalDisponiveis));
        lblEmprestimosAtivos.setText(String.valueOf(totalEmprestimosAtivos));
    }

    // ========================= UTILITÁRIOS =========================
    private Livro buscarLivroPorCodigo(String codigo) {
        for (Livro l : livros) {
            if (l.getCodigo().equalsIgnoreCase(codigo)) {
                return l;
            }
        }
        return null;
    }

    private int contarEmprestimosAtivos(Livro livro) {
        int count = 0;
        for (Emprestimo emp : emprestimos) {
            if (emp.isAtivo() && emp.getLivro().getCodigo().equals(livro.getCodigo())) {
                count++;
            }
        }
        return count;
    }

    private void mostrarErro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarSucesso(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarInfo(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Informação", JOptionPane.INFORMATION_MESSAGE);
    }

    // ========================= MAIN =========================
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(TelaPrincipal::new);
    }
}
