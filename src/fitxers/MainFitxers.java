package fitxers;
import java.io.File;
import xiflib.XifLib;

public class MainFitxers {

	public static void main(String[] args) {
		File in = new File("in.txt");
		File out = new File("out.txt");
		XifLib xif = new XifLib();
		xif.xifrar(in, out);
		System.out.println();
		xif.xifrar(out, in);
		System.out.println("\nHolaaa");
		System.out.println(xif.xifrar("Hola arbol"));
		System.out.println(xif.xifrar(xif.xifrar("Hola arbol")));

	}
}