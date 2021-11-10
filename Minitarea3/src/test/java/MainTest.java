import org.junit.jupiter.api.Test;
import java.util.Hashtable;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void splitterTest() {
        assertThrows(InvalidFormatException.class, () -> {
            Main.splitter("Hola");
        });

        assertThrows(InvalidFormatException.class, () -> {
            Main.splitter("20.948.234-1 hola que tal");
        });

        assertThrows(InvalidFormatException.class, () -> {
            Main.splitter("20.948.234-1\thola\tquetal");
        });

        assertDoesNotThrow(() -> {
            Main.splitter("20.948.234-1\thola");
        });

        assertDoesNotThrow(() -> {
            Main.splitter("test\thello World");
        });

    }

    @Test
    void rLineTest() {
        assertThrows(InvalidRutException.class, () -> {
            Main.rLine("20.000.000-k");
        });

        assertThrows(InvalidRutException.class, () -> {
            Main.rLine("200.948.234-1");
        });

        assertThrows(InvalidRutException.class, () -> {
            Main.rLine("200.948.234-1");
        });

        assertThrows(InvalidRutException.class, () -> {
            Main.rLine("0.948.234-1");
        });

        assertThrows(InvalidRutException.class, () -> {
            Main.rLine("20.9480.234-1");
        });

        assertThrows(InvalidRutException.class, () -> {
            Main.rLine("20.948.2341");
        });

        assertThrows(InvalidRutException.class, () -> {
            Main.rLine("test");
        });

        assertThrows(InvalidRutException.class, () -> {
            Main.rLine("20.979.925-0");
        });

        assertDoesNotThrow(() -> {
            Main.rLine("20351616-9");
        });

        assertDoesNotThrow(() -> {
            Main.rLine("20.351.616-9");
        });

        assertDoesNotThrow(() -> {
            Main.rLine("20351.616-9");
        });


        assertDoesNotThrow(() -> {
            Main.rLine("8.090.533-5");
        });

        assertDoesNotThrow(() -> {
            Main.rLine("8.010.979-2");
        });

        assertDoesNotThrow(() -> {
            Main.rLine("16476014-6");
        });

        assertDoesNotThrow(() -> {
            Main.rLine("4636232-2");
        });

    }

    @Test
    void openFileTest() {
        assertThrows(InvalidFileException.class, () -> {
            Main.openFile(".tsv");
        });

        assertThrows(InvalidFileException.class, () -> {
            Main.openFile("tsv");
        });

        assertThrows(InvalidFileException.class, () -> {
            Main.openFile("test.csv");
        });

        assertDoesNotThrow(() -> {
            Main.openFile("test.tsv");
        });

        assertThrows(InvalidFileException.class, () -> {
            Main.openFile("test2.txt");
        });

        assertDoesNotThrow(() -> {
            Main.openFile("test3.tsv");
        });
    }

    /**
     * Tests the AuxMethod method and the insertions on the dictionary;
     */
    @Test
    void mainTest() {
        Hashtable<String, String> test = new Hashtable<String, String>();
        test.put("20351616-9", "Giorno Giovana");
        test.put("8090533-5", "Joseph Joestar");
        test.put("8010979-2", "Erina Pendleton");
        test.put("16476014-6", "Jotaro Kujo");
        test.put("4636232-2", "Lisa Lisa");
        assertTrue(test.equals(Main.AuxMethod("test.tsv")));
        assertTrue(test.equals(Main.AuxMethod("invalidfile.tsv")));
    }
}