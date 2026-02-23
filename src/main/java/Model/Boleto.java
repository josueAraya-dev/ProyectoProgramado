package Model;

import java.util.List;

public abstract class Boleto implements IImprimible {
   
    private String idBoleto;
    private Cliente cliente;
    private Asiento asiento;
    private Evento evento;
    private static int contadorBoletos = 1;
    
    public Boleto(Evento evento, Cliente cliente, Asiento asiento) {
        validarParametros(evento, cliente, asiento);
        this.evento = evento;
        this.cliente = cliente;
        this.asiento = asiento;
        this.idBoleto = generarIdBoleto();
    }
    
    protected Boleto(Evento evento, Cliente cliente, Asiento asiento, String idBoleto) {
        validarParametros(evento, cliente, asiento);
        if (idBoleto == null || idBoleto.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del boleto no puede estar vacío");
        }
        this.evento = evento;
        this.cliente = cliente;
        this.asiento = asiento;
        this.idBoleto = idBoleto;
    }
    
    public static void sincronizarContador(List<Boleto> boletosExistentes) {
        int maxId = 0;
        for (Boleto b : boletosExistentes) {
            String id = b.getIdBoleto();
            if (id.startsWith("BOL-")) {
                try {
                    int num = Integer.parseInt(id.substring(4));
                    maxId = Math.max(maxId, num);
                } catch (NumberFormatException e) {}
            }
        }
        contadorBoletos = maxId + 1;
    }
    
    private void validarParametros(Evento evento, Cliente cliente, Asiento asiento) {
        if (evento == null) throw new IllegalArgumentException("El evento no puede ser null");
        if (cliente == null) throw new IllegalArgumentException("El cliente no puede ser null");
        if (asiento == null) throw new IllegalArgumentException("El asiento no puede ser null");
    }
     
    private String generarIdBoleto() {
        return "BOL-" + String.format("%03d", contadorBoletos++);
    }
    
    public String getIdBoleto() { return idBoleto; }
    public void setIdBoleto(String idBoleto) { this.idBoleto = idBoleto; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Asiento getAsiento() { return asiento; }
    public void setAsiento(Asiento asiento) { this.asiento = asiento; } 
    public Evento getEvento() { return evento; }
    public void setEvento(Evento evento) { this.evento = evento; }
    
    public abstract double calcularPrecioFinal();

    @Override
    public String imprimir() {
        String pos = (asiento.getFila() + 1) + "-" + (asiento.getColumna() + 1);
        return "----- TICKET -----\n"
                + "ID: " + idBoleto + "\n"
                + "Cliente: " + cliente.getNombre() + "\n"
                + "Evento: " + evento.getNombre() + "\n"
                + "Asiento: " + pos + "\n"
                + "Precio: " + calcularPrecioFinal();
    }
}
//metodo sincronizar contador 
/*Este método encuentra el ID más alto entre todos los boletos cargados y ajusta
 * el contador para que el próximo boleto creado tenga un ID único que no colisione
 * con los existentes.
 * */