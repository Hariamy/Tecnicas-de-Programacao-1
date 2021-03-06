package visao.janela;
import controlador.Controlador;
import visao.layout.Botao;
import visao.layout.Cores;
import visao.layout.Fontes;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;


public class PainelInicial extends JPanel {
    public Janela janela;
    private JTextField texto;
    private boolean focoInicial = true;
    Botao buscar;

    private JPanel painelSuperior = new JPanel();
    private JPanel painelInferir = new JPanel();
    private JPanel painelTexto = new JPanel();
    private JPanel painelBotao = new JPanel();

    public PainelInicial(Janela janela){
        super();
        this.configuracoes();
        this.janela = janela;
        this.setVisible(true);
    }

    public void configuracoes(){
        //     LAYOUT DA PÁGINA INICIAL     //

        Border bordaVazia = BorderFactory.createEmptyBorder(0, 0, 0, 0);
        this.setBorder(bordaVazia);
        painelInferir.setBorder(bordaVazia);
        painelSuperior.setBorder(bordaVazia);

        this.setLayout(new BorderLayout());
        painelInferir.setLayout(new BorderLayout());
        painelSuperior.setLayout(new BorderLayout());

        try {
            //IMAGEM DA TELA DE INICIO
            URL inicial = ClassLoader.getSystemResource("imagens/inicial.png");
            Icon imagemTitulo = new ImageIcon(inicial);
            JLabel titulo = new JLabel(imagemTitulo, SwingConstants.CENTER);
            painelSuperior.add(titulo, BorderLayout.CENTER);
            painelSuperior.setPreferredSize(new Dimension(900, 450));

        }catch (Exception e){
            //Carrega icone alternativo
            Icon imagemTitulo = new ImageIcon(getClass().getResource("/imagens\\inicial.png"));
            JLabel titulo = new JLabel(imagemTitulo, SwingConstants.CENTER);
            painelSuperior.add(titulo, BorderLayout.CENTER);
            painelSuperior.setPreferredSize(new Dimension(900, 450));
        }


        //CAIXA DE TEXTO
        Border bordaTexto = BorderFactory.createEmptyBorder(7, 11, 7, 5);
        Cursor cursor = new Cursor(Cursor.TEXT_CURSOR);
        texto = new JTextField("Digite o diretório do arquivo", 35);

        texto.setCursor(cursor);
        texto.addFocusListener(new TextoOculto());
        texto.setBorder(bordaTexto);
        texto.setSize(110, 20);
        texto.setFont(Fontes.ROBOTO_MENOR);

        this.painelTexto.add(texto, BorderLayout.CENTER);
        this.painelTexto.setBackground(Cores.rosaClaro);
        painelTexto.setBorder(bordaVazia);

        //BOTAO DE PROCURAR ARQUIVO CSV

        buscar = new Botao("BUSCAR");
        buscar.setMargin(new Insets(2, 10, 2, 10));
        buscar.configurarFonteCorFundo(Fontes.ROBOTO_BOLD_PEQUENA, Cores.corBotaoAzulEscuro, Cores.corVerde);
        buscar.addActionListener(new BotaoBuscar());
        buscar.requestFocusInWindow ();
        painelTexto.setPreferredSize(new Dimension(900, 120));

        this.painelTexto.add(buscar, BorderLayout.NORTH);
        this.painelInferir.add(painelTexto, "North");

        //BOTAO ANALISAR
        Botao analisar = new Botao("ANALISAR");
        analisar.setMargin(new Insets(10, 40, 10, 40));
        analisar.configurarFonteCorFundo(Fontes.ROBOTO_BOLD_MEDIA, Cores.corBranca, Cores.corBotaoAzulEscuro);
        analisar.addActionListener(new BotaoAnalisar());


        painelBotao.setBackground(Cores.rosaClaro);
        painelBotao.setBorder(bordaVazia);
        painelSuperior.setBackground(Cores.rosaClaro);

        painelBotao.add(analisar, BorderLayout.CENTER);

        painelInferir.add(painelBotao, "Center");


        this.add(painelSuperior, "North");
        this.add(painelInferir, "Center");
        this.setBackground(Cores.rosaClaro);

    }

    public class BotaoAnalisar implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            boolean erro;
            Controlador controlador = new Controlador(texto.getText());
            erro = controlador.erro();

            if (erro){
                String mensagem = "";
                if (texto.getText().equals("") || texto.getText().equals("Digite o diretório do arquivo")) mensagem = "<html>Escolha um arquivo clicando em buscar <br/>ou digite o diretório na caixa de texto</html>";
                else mensagem = "Não foi possível abrir o arquivo!";

                URL erroIcone = ClassLoader.getSystemResource("imagens/erro.png");
                Icon iconeErro = new ImageIcon(erroIcone);
                JOptionPane.showMessageDialog (new JFrame(), mensagem, "Erro", JOptionPane.INFORMATION_MESSAGE, iconeErro);

            } else{
                janela.setControlador(controlador);
                janela.getContentPane().removeAll();
                janela.conteudoJanela(new PainelInformacoes(janela));
                janela.revalidate();
                janela.repaint();
            }

        }
    }


    public class TextoOculto implements FocusListener {
        public void focusGained ( FocusEvent  e){
            if (focoInicial) {
                focoInicial = false;
                buscar.requestFocus();

            } else if (texto.getText().matches("Digite o diretório do arquivo")) texto.setText("");

        }
        public void focusLost ( FocusEvent  e){
            if (texto.getText().matches("")) texto.setText("Digite o diretório do arquivo");

        }
    }

    public class BotaoBuscar implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            JFileChooser arquivo;
            LookAndFeel anteriro = UIManager.getLookAndFeel();
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                arquivo = new JFileChooser();
                UIManager.setLookAndFeel(anteriro);

            } catch (Exception e) {
                arquivo = new JFileChooser();
            }

            FileNameExtensionFilter filtroCSV = new FileNameExtensionFilter("Arquivos CSV", "csv");
            arquivo.addChoosableFileFilter(filtroCSV);
            arquivo.setAcceptAllFileFilterUsed(false);

            if(arquivo.showOpenDialog(new JFrame()) == JFileChooser.APPROVE_OPTION){
                texto.setText(arquivo.getSelectedFile().getAbsolutePath());
            }
        }
    }

}
