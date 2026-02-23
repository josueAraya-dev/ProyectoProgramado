package Model;

public class GestorVentas {

    private GestorEventos gestorDeEventos;
    private GestorClientes gestorDeClientes;

    public GestorVentas(GestorEventos gestorDeEventos, GestorClientes gestorDeClientes) {
        this.gestorDeEventos = gestorDeEventos;
        this.gestorDeClientes = gestorDeClientes;
    }

    public Boleto procesarVentaDeBoleto(String identificacionCliente, String nombreCompletoCliente, String idDelEvento,
            int numeroFila, int numeroColumna, String categoriaBoleto) throws Exception {

        Evento eventoSeleccionado = gestorDeEventos.buscarEventoPorId(idDelEvento);

        Cliente clienteComprador = gestorDeClientes.buscarclientePorId(identificacionCliente);
        if (clienteComprador == null) {
            clienteComprador = gestorDeClientes.crearCliente(nombreCompletoCliente, identificacionCliente);
        }

        Asiento asientoObjetivo = eventoSeleccionado.obtenerAsiento(numeroFila, numeroColumna);
        asientoObjetivo.ocupar();

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

        eventoSeleccionado.agregarBoleto(nuevoBoleto);
        return nuevoBoleto;
    }
}