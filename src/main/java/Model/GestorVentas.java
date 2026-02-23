/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

 /**
     
     * @param identificacionCliente
     * @param nombreCompletoCliente
     * @param idDelEvento
     * @param numeroFila
     * @param numeroColumna
     * @param categoriaBoleto
     * @return 
     * @throws java.lang.Exception 
     */

public class GestorVentas {

    private GestorEventos gestorDeEventos;
    private GestorClientes gestorDeClientes;
    private ServicioPersistencia persistencia;
    private static int contadorGlobalDeBoletos = 0;

    public GestorVentas(GestorEventos gestorDeEventos, GestorClientes gestorDeClientes) {
        this.gestorDeEventos = gestorDeEventos;
        this.gestorDeClientes = gestorDeClientes;
        //this.persistenciaDeDatos = new Persistencia();
    }

    public Boleto procesarVentaDeBoleto(String identificacionCliente, String nombreCompletoCliente, String idDelEvento,
            int numeroFila, int numeroColumna, String categoriaBoleto) throws Exception {

        // 1. Localizar el evento seleccionado
        Evento eventoSeleccionado = gestorDeEventos.buscarEventoPorId(idDelEvento);

        // 2. Gestionar la información del cliente
        Cliente clienteComprador = gestorDeClientes.buscarclientePorId(identificacionCliente);
        if (clienteComprador == null) {
           
            clienteComprador = gestorDeClientes.crearCliente(nombreCompletoCliente, identificacionCliente);
        }

        // 3. Obtener el asiento específico del evento
        Asiento asientoObjetivo = eventoSeleccionado.obtenerAsiento(numeroFila, numeroColumna);

        // 4. Intentar ocupar el asiento 
        asientoObjetivo.ocupar();

        // 6. Crear la instancia específica del boleto usando Polimorfismo
        Boleto nuevoBoleto;
        switch (categoriaBoleto.toUpperCase()) {
            case "VIP":
                nuevoBoleto = new BoletoVIP(eventoSeleccionado, clienteComprador, asientoObjetivo);
                break;
            case "ESTUDIANTE":
                nuevoBoleto = new BoletoEstudiante(eventoSeleccionado, clienteComprador, asientoObjetivo);
                break;
            default:
                nuevoBoleto = new BoletoGeneral(eventoSeleccionado, clienteComprador, asientoObjetivo);
                break;
        }

        // 7. Registrar el boleto en el historial del evento y persistir en archivo
        eventoSeleccionado.agregarBoleto(nuevoBoleto);
        
        return nuevoBoleto;
    }

}
