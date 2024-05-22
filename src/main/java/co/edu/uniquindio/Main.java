package co.edu.uniquindio;

import co.edu.uniquindio.utilities.Archivo;
import co.edu.uniquindio.utilities.Dato;
import co.edu.uniquindio.utilities.Grafica;
import co.edu.uniquindio.utilities.Metodo;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main
{

    private static final int SEQUENTIAL_THRESHOLD = 64;

    public static void main(String[] args) throws IOException {

        Archivo.validarYRenombrarArchivo();

        int tamano_matriz = 11;

        for (int i = 1; i <= tamano_matriz ; i++) {
            ejecutarMetodos((int) Math.pow(2, i));
        }

        String nombre_archivo = "./src/main/java/co/edu/uniquindio/matrixes_files/resultados.txt";
        Grafica grafica = new Grafica();

        Map<Integer, List<Dato>> datos = grafica.leerDatos(nombre_archivo);

        // Itera sobre las claves del mapa
        for (Integer indiceConjunto : datos.keySet()) {
            try {
                Grafica.graficarComparativa(datos, indiceConjunto);
            } catch (IOException e) {
                e.printStackTrace();
                // Manejo de excepciones
            }
        }

    }

    public static void ejecutarMetodos(int tamano_matriz){
        String ruta = "./src/main/java/co/edu/uniquindio/matrixes_files/matriz"+tamano_matriz+"x"+tamano_matriz+".txt";
        Archivo archivo = new Archivo();
        Metodo metodo = new Metodo();

        Archivo.generarTxtMatrizPrueba(tamano_matriz);
        int[][] matriz_a = Archivo.leerArchivoMatriz(ruta);
        Archivo.generarTxtMatrizPrueba(tamano_matriz);
        int[][] matriz_b = Archivo.leerArchivoMatriz(ruta);

        //System.out.println("Matriz A");
        //Metodo.imprimirMatriz(matriz_a);

        //System.out.println("Matriz B");
        //Metodo.imprimirMatriz(matriz_b);

        int[][] matriz_c = new int[tamano_matriz][tamano_matriz];

        int n = matriz_a.length;
        int p = matriz_b.length;
        int m = matriz_b[0].length;
        System.out.println(n + " " +  p+ " " +m);

        medirTiempoEjecucion(() -> Metodo.NaivOnArray(matriz_a, matriz_b, matriz_c, n, p, m), "NaivOnArray", n);
        medirTiempoEjecucion(() -> Metodo.NaivLoopUnrollingTwo(matriz_a, matriz_b, matriz_c, n, p, m), "NaivLoopUnrollingTwo", n);
        medirTiempoEjecucion(() -> Metodo.NaivLoopUnrollingFour(matriz_a, matriz_b, matriz_c, n, p, m), "NaivLoopUnrollingFour", n);
        medirTiempoEjecucion(() -> Metodo.WinogradOriginal(matriz_a, matriz_b, matriz_c, n, p, m), "WinogradOriginal", n);
        medirTiempoEjecucion(() -> Metodo.WinogradScaled(matriz_a, matriz_b, matriz_c, n, p, m), "WinogradScaled", n);
        medirTiempoEjecucion(() -> Metodo.StrassenNaiv(matriz_a, matriz_b, matriz_c, n, p, m), "StrassenNaiv", n);
        medirTiempoEjecucion(() -> Metodo.StrassenWinograd(matriz_a, matriz_b, matriz_c, n, p, m), "StrassenWinograd", n);
        medirTiempoEjecucion(() -> Metodo.III3SequentialBlock(matriz_a, matriz_b, matriz_c, n, p, m), "III3SequentialBlock", n);
        medirTiempoEjecucion(() -> Metodo.III4ParallelBlock(matriz_a, matriz_b, matriz_c, n, p, m), "III4ParallelBlock", n);
        medirTiempoEjecucion(() -> Metodo.III5EnhancedParallelBlock(matriz_a, matriz_b, matriz_c, n, SEQUENTIAL_THRESHOLD), "III5EnhancedParallelBlock", n);
        medirTiempoEjecucion(() -> Metodo.IV3SequentialBlock(matriz_a, matriz_b, matriz_c, n, p, m), "IV3SequentialBlock", n);
        medirTiempoEjecucion(() -> Metodo.IV4ParallelBlock(matriz_a, matriz_b, matriz_c, n, p, m), "IV4ParallelBlock", n);
        medirTiempoEjecucion(() -> Metodo.V3SequentialBlock(matriz_a, matriz_b, matriz_c, n, p, m), "V3SequentialBlock", n);
        medirTiempoEjecucion(() -> Metodo.V4ParallelBlock(matriz_a, matriz_b, matriz_c, n, p, m), "V4ParallelBlock", n);
        medirTiempoEjecucion(() -> Metodo.IV5EnhancedParallelBlock(matriz_a, matriz_b, matriz_c, n, SEQUENTIAL_THRESHOLD), "IV5EnhancedParallelBlock", n);


        Archivo.guardarResultado(0, 0, "");
    }

    public static void medirTiempoEjecucion(Runnable metodo, String nombreMetodo, int n) {
        long startTime = System.nanoTime();
        metodo.run();
        long endTime = System.nanoTime();
        float tiempoPromedio = (endTime - startTime) / 1e9f;
        System.out.println("Tiempo promedio transcurrido en " + nombreMetodo + ": " + tiempoPromedio + " segundos");
        Archivo.guardarResultado(n, tiempoPromedio, nombreMetodo);
    }

    public static double[][] matrizEnteraDouble(int[][] matrizInt){
        // Crear una nueva matriz de dobles con las mismas dimensiones que la matriz de enteros
        double[][] matrizDoubles = new double[matrizInt.length][];

        // Copiar y convertir los elementos de la matriz de enteros a la matriz de dobles
        for (int i = 0; i < matrizInt.length; i++) {
            matrizDoubles[i] = Arrays.stream(matrizInt[i])
                    .asDoubleStream()
                    .toArray();
        }

        return matrizDoubles;
    }
    public static int[][] matrizDoubleEntera(double[][] matrizDouble) {
        int filas = matrizDouble.length;
        int columnas = matrizDouble[0].length;
        int[][] matrizEntera = new int[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matrizEntera[i][j] = (int) matrizDouble[i][j];
            }
        }

        return matrizEntera;
    }
}