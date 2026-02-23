/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import Model.*;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EventoTesting {
    
    private Evento evento, evento2;
    private Cliente cliente;
    private Boleto boleto;
    private GestorEventos geventos = new GestorEventos();

    @BeforeEach
    public void setUp() {
            
    evento = new Evento("Evt-001","lago de los cisnes",LocalDate.now(),
            25000);
    
    evento2 = new Evento();
    }
    
    
    @Test
    public void buscar_evento_porid(){
    evento.getIdEvento();
    
    String esperado = "Evt-001";
    assertEquals(esperado, evento.getIdEvento());
    }
   
    @Test
    public void crear_evento(){
    
    geventos.crearEvento("Evt-002", "parque Jurasico", LocalDate.now(), 15000);
    
    evento2 = geventos.buscarEventoPorId("Evt-002");
   
    String esperado = "Evt-002";
    
     assertEquals(esperado, evento2.getIdEvento());
    
    }
    
    
    @Test 
    public void eliminar_evento(){
    
      geventos.eliminarEvento("Evt-002");
      
      String esperado = "Evt-002";
      
      geventos.getEventosCreados();
      
      
      assertEquals(esperado, geventos.getEventosCreados() );
    //asegurarme que lanza la excepcion
    }
    
    @Test
    public void editar_id_evento(){
        
        geventos.crearEvento("Evt-002", "parque Jurasico", LocalDate.now(), 15000);
        
        geventos.editarEvento("Evt-002", "noche de cine", LocalDate.now(), 15000);
        
        evento2 = geventos.buscarEventoPorId("Evt-002");
        
        String esperado = "noche de cine";
       
        assertEquals(esperado, geventos.getEventosCreados());  
        
    }
                                                                                                            
    
}
