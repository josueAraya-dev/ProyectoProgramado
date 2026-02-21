package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataSystem {
   
    public static ObservableList<String> listaEventos = FXCollections.observableArrayList();

   
    public static boolean[][] asientosOcupados = new boolean[10][10];
    
   
    public static void limpiarSala() {  
        asientosOcupados = new boolean[10][10]; 
    }
}