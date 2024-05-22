package co.edu.uniquindio.utilities;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Archivo {

	public Archivo() {
	}

	/**
	 * Este metodo recibe una ruta de las matrices de prueba txt y retorna la matriz
	 * de tipo int[][]
	 * @param ruta
	 * @return
	 */
	public static int[][] leerArchivoMatriz(String ruta) {
		int[][] matriz = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(ruta));
			// Primera linea nos dice longitud de la matriz
			String linea = br.readLine();
			int longitud = Integer.parseInt(linea);
			matriz = new int[longitud][longitud];
			// Las siguientes lineas son filas de la matriz
			linea = br.readLine();
			int fila = 0; // Para recorrer las filas de la matriz
			while (linea != null) {
				/*
				 * Tenemos todos los enteros JUNTOS en el String linea. Con
				 * split() los SEPARAMOS en un array donde cada entero es un
				 * String individual. Con un bucle, los parseamos a Integer para
				 * guardarlos en la matriz
				 */
				String[] enteros = linea.split(" ");
				for (int i = 0; i < enteros.length; i++)
					matriz[fila][i] = Integer.parseInt(enteros[i]);

				fila++; // Incrementamos fila para la pr�xima l�nea de enteros
				linea = br.readLine(); // Leemos siguiente l�nea
			}
			br.close(); // Cerramos el lector de ficheros

		} catch (FileNotFoundException e) {
			System.out.println("No se encuentra archivo");
			e.printStackTrace();
		} catch (NumberFormatException e) {
			System.out.println("No se pudo convertir a entero");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error accediendo al archivo.");
			e.printStackTrace();
		}
		return matriz;
	}

	/**
	 * Este metodo genera una matriz de prueba segun un n indicado por parametro
	 * @param n
	 */
	public static void generarTxtMatrizPrueba(int n) {
		int[][] matriz = new int[n][n];
		String nombreArchivo = "matriz" + n + "x" + n + ".txt";
		String rutaDirectorio = "./src/main/java/co/edu/uniquindio/matrixes_files/";

		try {
			File directorio = new File(rutaDirectorio);

			// Intenta crear el directorio
			if (!directorio.exists()) {
				if (!directorio.mkdirs()) {
					System.out.println("No se pudo crear el directorio.");
					throw new IOException("No se pudo crear el directorio");
				}
			}

			File archivo = new File(directorio, nombreArchivo);
			FileWriter fw = new FileWriter(archivo, false);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write(n + "\r\n");
			for (int i = 0; i < matriz.length; i++) {
				for (int j = 0; j < matriz[i].length; j++) {
					matriz[i][j] = (int) (Math.random() * 900000) + 100000;
					if (j < matriz[i].length - 1) {
						bw.write(matriz[i][j] + " ");
					} else {
						bw.write("" + matriz[i][j]);
					}
				}
				bw.write("\r\n");
			}
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void guardarResultado(int n, double te, String algoritmo) {
		String linea = n + "," + algoritmo + "," + te + "\n";
		String nombreArchivo = "./src/main/java/co/edu/uniquindio/matrixes_files/resultados.txt";

		try {
			FileWriter fw = new FileWriter(nombreArchivo, true); // El segundo argumento true indica que se abrirá en modo de agregar (append)
			if (n == 0) {
				fw.write("-------------------------------------------------------------------------\n");
			} else {
				fw.write(linea);
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void validarYRenombrarArchivo() {
		String rutaArchivo = "./src/main/java/co/edu/uniquindio/matrixes_files/resultados.txt";
		if (existeArchivo(rutaArchivo)) {
			renombrarArchivo(rutaArchivo);
		} else {
			System.out.println("El archivo no existe en la ruta proporcionada.");
		}
	}

	public static boolean existeArchivo(String rutaArchivo) {
		File archivo = new File(rutaArchivo);
		return archivo.exists();
	}

	public static void renombrarArchivo(String rutaArchivo) {
		// Verificar si el archivo existe
		File archivo = new File(rutaArchivo);
		if (archivo.exists()) {
			// Obtener el nombre actual del archivo
			String nombreActual = archivo.getName();

			// Extraer la extensión del archivo
			String extension = nombreActual.substring(nombreActual.lastIndexOf("."));

			// Generar un nuevo nombre con fecha y hora
			String nuevoNombre = nombreActual.substring(0, nombreActual.lastIndexOf(".")) + "_"
					+ new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + extension;

			// Renombrar el archivo
			File nuevoArchivo = new File(archivo.getParent(), nuevoNombre);
			if (archivo.renameTo(nuevoArchivo)) {
				System.out.println("El archivo " + nombreActual + " se ha renombrado a " + nuevoNombre);
			} else {
				System.out.println("Error al renombrar el archivo: " + nombreActual);
			}
		} else {
			System.out.println("El archivo " + rutaArchivo + " no existe.");
		}
	}

}
