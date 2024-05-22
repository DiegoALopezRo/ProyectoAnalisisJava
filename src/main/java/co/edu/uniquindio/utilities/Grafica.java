package co.edu.uniquindio.utilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grafica {

    public Grafica() {
    }

    public static Map<Integer, List<Dato>> leerDatos(String nombreArchivo) throws IOException {
        Map<Integer, List<Dato>> datos = new HashMap<>();
        List<Dato> conjuntoActual = new ArrayList<>();
        int conjuntoIndex = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("-------------------------------------------------------------------------")) {
                    if (!conjuntoActual.isEmpty()) {
                        datos.put(conjuntoIndex, conjuntoActual);
                        conjuntoActual = new ArrayList<>();
                        conjuntoIndex++;
                    }
                } else {
                    String[] partes = linea.split(",");
                    int n = Integer.parseInt(partes[0]);
                    String algoritmo = partes[1];
                    double te = Double.parseDouble(partes[2]);
                    conjuntoActual.add(new Dato(n, algoritmo, te));
                }
            }
            if (!conjuntoActual.isEmpty()) {
                datos.put(conjuntoIndex, conjuntoActual);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        return datos;
    }

    public static void graficarComparativa(Map<Integer, List<Dato>> datos, int indiceConjunto) throws IOException {
        List<Dato> conjunto = datos.get(indiceConjunto);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Dato dato : conjunto) {
            dataset.addValue(dato.getTiempoEjecucion(), dato.getAlgoritmo(), String.valueOf(dato.getN()));
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Matriz Tamano " + (int) Math.pow(2, indiceConjunto + 1) + "x" + (int) Math.pow(2, indiceConjunto + 1),
                "Algoritmo",
                "Tiempo de ejecución (s)",
                dataset
        );

        ChartUtils.saveChartAsPNG(new File("./src/main/java/co/edu/uniquindio/matrixes_files/img/comparativa_matriz" + (int) Math.pow(2, indiceConjunto + 1) + "x" + (int) Math.pow(2, indiceConjunto + 1) + ".png"), chart, 800, 600);
    }
}

