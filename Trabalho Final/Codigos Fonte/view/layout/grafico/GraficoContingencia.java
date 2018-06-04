package view.layout.grafico;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;


public class GraficoContingencia implements Grafico{
    private ChartPanel painel;

    public GraficoContingencia(String tituloTabela1, String tituloTabela2, String[] titulo, String[][] dados) {
        boolean legenda = titulo.length <= 10;
        JFreeChart barChart = ChartFactory.createBarChart(
                "\""+tituloTabela1+"\" VS \""+tituloTabela2+"\"",
                tituloTabela2,
                "Repetições",
                criarDados(titulo, dados),
                PlotOrientation.VERTICAL,
                legenda, true, false);

        ChartPanel chartPanel = new ChartPanel( barChart, true );
        int tamanho = dados.length < titulo.length? titulo.length : dados.length;
        tamanho = tamanho > 500? 800 : 500;
        chartPanel.setPreferredSize(new java.awt.Dimension(tamanho, 300));

        // CategoryPlot plot = barChart.getCategoryPlot();
        //  plot.getDomainAxis(0).setVisible(false);
        painel = chartPanel;
    }

    public ChartPanel getPainel() {
        return painel;
    }

    private CategoryDataset criarDados(String[] titulo, String[][] dados) {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );

        for (int coluna = 1; coluna < titulo.length; coluna++){
            for (int linha = 0; linha < dados.length; linha++){
                if (Float.parseFloat(dados[linha][coluna]) != 0) dataset.addValue(Float.parseFloat(dados[linha][coluna]), titulo[coluna], dados[linha][0]);
            }
        }




        return dataset;
    }

}