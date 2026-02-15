/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;


public abstract class Boleto {
   
    private String boletoId;
    private Cliente cliente;
    private Asiento asiento;
    private Evento evento;
    private static int contador = 0;
    
    public Boleto(Evento evento, Cliente cliente, Asiento asiento) {
      
        this.evento = evento;
        this.cliente = cliente;
        this.asiento = asiento;
        this.boletoId = "BOL-" +contador;
    }//constructor para nuevos boletos
    
    public Boleto(Evento evento, Cliente cliente, Asiento asiento, String boletoId) {
        this.evento = evento;
        this.cliente = cliente;
        this.asiento = asiento;
        this.boletoId = boletoId;

        actualizarContador(boletoId);
    }//constructor para carga desde archivos
    
    public String getBoletoId() {
        return boletoId;
    }

    public void setBoletoId(String boletoId) {
        this.boletoId = boletoId;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public void setAsiento(Asiento asiento) {
        this.asiento = asiento;
    } 

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }
    
    private static void actualizarContador(String id) {
        int numero = Integer.parseInt(id.split("-")[1]);
        if (numero > contador) {
            contador = numero;
        }
    }
    
    public abstract double calcularPrecioFinal();
    
}
