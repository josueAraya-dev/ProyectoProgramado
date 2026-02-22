/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import Model.Asiento;
import Model.BoletoGeneral;
import Model.BoletoVIP;
import Model.Cliente;
import Model.Evento;
import Model.Sala;
import Model.EstadoAsiento;
import java.time.LocalDate;
import static java.time.temporal.TemporalQueries.localDate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author josue
 */
public class AsientoTest{
    
    private Sala nuevasala;
    private EstadoAsiento estado;
    
    @BeforeEach
    public void setUp() {
     
        
        
    }

    @Test
   public void obtenerAsiento(){
   
  // EstadoAsiento estado;
       
   Asiento asientoresultado = nuevasala.obtenerAsiento(1, 3);
   asientoresultado.getEstado();
   
   assertEquals(EstadoAsiento.Disponible,asientoresultado.getEstado());
   
   }
   
      @Test
   public void cambiar_estado_asiento(){ 
       
   Asiento asientoresultado = nuevasala.obtenerAsiento(1, 3);
   asientoresultado.ocupar();
   asientoresultado.getEstado();
   
   assertEquals(EstadoAsiento.Ocupado,asientoresultado.getEstado());
  
   }
   
   @Test
   public void reiniciar_sala(){
       
    Asiento asiento = nuevasala.obtenerAsiento(1, 3);
    
    asiento.ocupar();
    estado = asiento.getEstado(); 
    assertEquals(EstadoAsiento.Ocupado, estado);
    
    
    nuevasala.reiniciarSala();
    estado = asiento.getEstado();
    assertEquals(EstadoAsiento.Disponible, estado);
    
   }
   
   
   @Test
   public void liberar_Asiento(){
       
    Asiento asiento = nuevasala.obtenerAsiento(1, 3);
    
    asiento.ocupar();
    estado = asiento.getEstado(); 
    assertEquals(EstadoAsiento.Ocupado, estado);
    
    
    asiento.liberar();
    asiento.getEstado();
    assertEquals(EstadoAsiento.Disponible, estado);
    
   }
   
}
