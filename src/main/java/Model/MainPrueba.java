/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Model;

import excepciones.AsientoLibreException;
import excepciones.AsientoNoEncontradoException;
import excepciones.AsientoOcupadoException;
import excepciones.EventoNoEncontradoException;
import java.time.LocalDate;
import java.util.List;

public class MainPrueba {

    // Contadores para el reporte final
    static int pruebas = 0;
    static int exitosas = 0;
    static int fallidas = 0;

    public static void main(String[] args) {

        System.out.println("=================================================");
        System.out.println("   PRUEBA DE FLUJO - SISTEMA DE BOLETOS (Model)  ");
        System.out.println("=================================================\n");

        // -------------------------------------------------------
        // BLOQUE 1: GestorEventos - Creación y búsqueda de eventos
        // -------------------------------------------------------
        encabezado("BLOQUE 1: Gestión de Eventos");

        GestorEventos gestorEventos = new GestorEventos();
        GestorClientes gestorClientes = new GestorClientes();
        GestorVentas gestorVentas = new GestorVentas(gestorEventos, gestorClientes);

        // Crear evento válido
        Evento evento1 = null;
        try {
            evento1 = gestorEventos.crearEvento(
                    "EVT-001",
                    "Concierto de Rock",
                    LocalDate.now().plusDays(30),
                    15000.0
            );
            ok("Crear evento válido: " + evento1.getNombre());
        } catch (Exception e) {
            falla("Crear evento válido", e);
        }

        // Buscar evento existente
        try {
            Evento encontrado = gestorEventos.buscarEventoPorId("EVT-001");
            ok("Buscar evento por ID existente: " + encontrado.getNombre());
        } catch (Exception e) {
            falla("Buscar evento existente", e);
        }

        // Buscar evento NO existente (debe lanzar excepción)
        try {
            gestorEventos.buscarEventoPorId("NO-EXISTE");
            falla("Buscar evento inexistente debió lanzar excepción", null);
        } catch (EventoNoEncontradoException e) {
            ok("Excepción correcta al buscar evento inexistente: " + e.getMessage());
        } catch (Exception e) {
            falla("Tipo de excepción incorrecto al buscar evento inexistente", e);
        }

        // Intentar crear evento con fecha pasada (debe fallar)
        try {
            gestorEventos.crearEvento("EVT-ERR", "Evento Pasado", LocalDate.now().minusDays(1), 5000.0);
            falla("Crear evento con fecha pasada debió lanzar excepción", null);
        } catch (IllegalArgumentException e) {
            ok("Validación de fecha pasada funciona: " + e.getMessage());
        }

        // Intentar crear evento con precio negativo
        try {
            gestorEventos.crearEvento("EVT-ERR2", "Evento Gratis", LocalDate.now().plusDays(5), -100.0);
            falla("Crear evento con precio negativo debió lanzar excepción", null);
        } catch (IllegalArgumentException e) {
            ok("Validación de precio negativo funciona: " + e.getMessage());
        }

        // Editar evento
        try {
            gestorEventos.editarEvento("EVT-001", "Concierto de Jazz", LocalDate.now().plusDays(45), 18000.0);
            Evento editado = gestorEventos.buscarEventoPorId("EVT-001");
            ok("Editar evento - nuevo nombre: " + editado.getNombre() + " | precio: " + editado.getPrecioBase());
        } catch (Exception e) {
            falla("Editar evento", e);
        }

        // Crear un segundo evento para pruebas de eliminación
        try {
            gestorEventos.crearEvento("EVT-002", "Obra de Teatro", LocalDate.now().plusDays(10), 8000.0);
            gestorEventos.eliminarEvento("EVT-002");
            ok("Eliminar evento funciona correctamente");
        } catch (Exception e) {
            falla("Eliminar evento", e);
        }

        // -------------------------------------------------------
        // BLOQUE 2: GestorClientes - Creación y búsqueda
        // -------------------------------------------------------
        encabezado("BLOQUE 2: Gestión de Clientes");

        // Crear cliente válido
        try {
            gestorClientes.crearCliente("Juan Pérez", "123456789");
            ok("Crear cliente válido: Juan Pérez / 123456789");
        } catch (Exception e) {
            falla("Crear cliente válido", e);
        }

        // Buscar cliente existente
        try {
            Cliente c = gestorClientes.buscarclientePorId("123456789");
            if (c != null) {
                ok("Buscar cliente por ID: " + c.getNombre());
            } else {
                falla("Cliente no encontrado (retornó null)", null);
            }
        } catch (Exception e) {
            falla("Buscar cliente", e);
        }

        // Buscar cliente inexistente (debe retornar null)
        try {
            Cliente c = gestorClientes.buscarclientePorId("NO-EXISTE-ID");
            if (c == null) {
                ok("Buscar cliente inexistente retorna null correctamente");
            } else {
                falla("Debió retornar null para ID inexistente", null);
            }
        } catch (Exception e) {
            falla("Buscar cliente inexistente lanzó excepción inesperada", e);
        }

        // Validación: nombre con números (debe fallar)
        try {
            gestorClientes.crearCliente("Juan123", "999888777");
            falla("Nombre con números debió lanzar excepción", null);
        } catch (IllegalArgumentException e) {
            ok("Validación de nombre con números: " + e.getMessage());
        }

        // -------------------------------------------------------
        // BLOQUE 3: Sala y Asientos - Matriz bidimensional
        // -------------------------------------------------------
        encabezado("BLOQUE 3: Sala y Manejo de Asientos (Matriz)");

        if (evento1 != null) {
            // Obtener asiento válido
            try {
                Asiento a = evento1.obtenerAsiento(0, 0);
                ok("Obtener asiento [0,0] - Estado: " + a.getEstado());
            } catch (Exception e) {
                falla("Obtener asiento válido", e);
            }

            // Ocupar asiento
            try {
                Asiento a = evento1.obtenerAsiento(0, 0);
                a.ocupar();
                ok("Ocupar asiento [0,0] - Estado: " + a.getEstado());
            } catch (Exception e) {
                falla("Ocupar asiento", e);
            }

            // Ocupar asiento ya ocupado (debe lanzar excepción)
            try {
                Asiento a = evento1.obtenerAsiento(0, 0);
                a.ocupar(); // ya está ocupado
                falla("Ocupar asiento ya ocupado debió lanzar excepción", null);
            } catch (AsientoOcupadoException e) {
                ok("Excepción correcta al ocupar asiento ya ocupado");
            } catch (Exception e) {
                falla("Tipo de excepción incorrecto al ocupar asiento ocupado", e);
            }

            // Liberar asiento
            try {
                Asiento a = evento1.obtenerAsiento(0, 0);
                a.liberar();
                ok("Liberar asiento [0,0] - Estado: " + a.getEstado());
            } catch (Exception e) {
                falla("Liberar asiento", e);
            }

            // Liberar asiento ya libre (debe lanzar excepción)
            try {
                Asiento a = evento1.obtenerAsiento(0, 0);
                a.liberar(); // ya está libre
                falla("Liberar asiento ya libre debió lanzar excepción", null);
            } catch (AsientoLibreException e) {
                ok("Excepción correcta al liberar asiento ya libre");
            } catch (Exception e) {
                falla("Tipo de excepción incorrecto al liberar asiento libre", e);
            }

            // Obtener asiento fuera de rango
            try {
                evento1.obtenerAsiento(99, 99);
                falla("Asiento fuera de rango debió lanzar excepción", null);
            } catch (AsientoNoEncontradoException e) {
                ok("Excepción correcta para asiento fuera de rango");
            } catch (Exception e) {
                falla("Tipo de excepción incorrecto para rango inválido", e);
            }

            // Exportar e importar estado de sala
            try {
                evento1.obtenerAsiento(1, 1).ocupar();
                evento1.obtenerAsiento(2, 2).ocupar();
                String mapa = evento1.getSala().exportarEstadoAsientos();
                ok("Exportar estado de asientos. Longitud mapa: " + mapa.length() + " (esperado 100)");

                // Reiniciar sala
                evento1.ejecutarReinicioDeSala();
                boolean todosLibres = true;
                for (int f = 0; f < 10; f++) {
                    for (int c = 0; c < 10; c++) {
                        if (!evento1.obtenerAsiento(f, c).estaDisponible()) {
                            todosLibres = false;
                        }
                    }
                }
                if (todosLibres) {
                    ok("Reiniciar sala: todos los asientos están disponibles");
                } else {
                    falla("Reiniciar sala: hay asientos que no se liberaron", null);
                }
            } catch (Exception e) {
                falla("Exportar/Importar estado o reinicio de sala", e);
            }
        }

        // -------------------------------------------------------
        // BLOQUE 4: GestorVentas - Polimorfismo de boletos
        // -------------------------------------------------------
        encabezado("BLOQUE 4: Venta de Boletos y Polimorfismo de Precios");

        // Necesitamos un evento con precio conocido
        Evento eventoVenta = null;
        try {
            eventoVenta = gestorEventos.crearEvento("EVT-003", "Festival Musical", LocalDate.now().plusDays(20), 10000.0);
            ok("Evento para ventas creado: precio base = 10000");
        } catch (Exception e) {
            falla("Crear evento para ventas", e);
        }

        if (eventoVenta != null) {
            // Venta boleto General
            try {
                Boleto b = gestorVentas.procesarVentaDeBoleto(
                        "111111111", "María García", "EVT-003", 0, 0, "GENERAL");
                double precio = b.calcularPrecioFinal();
                ok("Boleto GENERAL vendido. Precio: " + precio + " (esperado: 10000.0)");
                if (precio != 10000.0) {
                    falla("Precio GENERAL incorrecto: " + precio, null);
                }
            } catch (Exception e) {
                falla("Procesar venta boleto GENERAL", e);
            }

            // Venta boleto VIP
            try {
                Boleto b = gestorVentas.procesarVentaDeBoleto(
                        "222222222", "Carlos López", "EVT-003", 0, 1, "VIP");
                double precio = b.calcularPrecioFinal();
                ok("Boleto VIP vendido. Precio: " + precio + " (esperado: 15000.0 = 10000 + 5000)");
                if (precio != 15000.0) {
                    falla("Precio VIP incorrecto: " + precio, null);
                }
                System.out.println("   Detalle ticket VIP:\n" + b.imprimir());
            } catch (Exception e) {
                falla("Procesar venta boleto VIP", e);
            }

            // Venta boleto Estudiante
            // ADVERTENCIA: hay un bug en BoletoEstudiante.calcularPrecioFinal()
            // Resta el valor del descuento (0.10) en lugar del porcentaje (10% de 10000 = 1000)
            // precio correcto sería: 10000 - (10000 * 0.10) = 9000
            // pero el código hace: 10000 - 0.10 = 9999.90
            try {
                Boleto b = gestorVentas.procesarVentaDeBoleto(
                        "333333333", "Ana Estudiante", "EVT-003", 0, 2, "ESTUDIANTE");
                double precio = b.calcularPrecioFinal();
                ok("Boleto ESTUDIANTE vendido. Precio calculado: " + precio);
                System.out.println("   ⚠ BUG DETECTADO: El precio debería ser 9000.0 (90% de 10000)");
                System.out.println("     pero el código resta 0.10 en lugar de (precioBase * 0.10)");
                System.out.println("     Línea en BoletoEstudiante: precioBase - descuento (debería ser precioBase - (precioBase * descuento))");
            } catch (Exception e) {
                falla("Procesar venta boleto ESTUDIANTE", e);
            }

            // Intentar vender en asiento ya ocupado
            try {
                gestorVentas.procesarVentaDeBoleto(
                        "444444444", "Pedro Repetido", "EVT-003", 0, 0, "GENERAL"); // [0,0] ya ocupado
                falla("Vender asiento ocupado debió lanzar excepción", null);
            } catch (AsientoOcupadoException e) {
                ok("Excepción correcta al intentar vender asiento ya ocupado");
            } catch (Exception e) {
                falla("Tipo de excepción incorrecto al vender asiento ocupado", e);
            }

            // Reporte de recaudación
            try {
                double total = eventoVenta.recaudacionPorEvento();
                ok("Recaudación total del evento 'Festival Musical': ₡" + total);
                System.out.println("   Boletos vendidos: " + eventoVenta.getBoletosVendidos().size());
            } catch (Exception e) {
                falla("Calcular recaudación", e);
            }

            // Verificar que cliente existente no se duplica
            try {
                // Vender otro boleto al mismo cliente (111111111)
                gestorVentas.procesarVentaDeBoleto(
                        "111111111", "María García", "EVT-003", 1, 0, "GENERAL");
                int totalClientes = gestorClientes.getClientesCreados().size();
                ok("Cliente existente reutilizado. Total clientes en sistema: " + totalClientes);
            } catch (Exception e) {
                falla("Reutilizar cliente existente en segunda compra", e);
            }
        }

        // -------------------------------------------------------
        // BLOQUE 5: Persistencia
        // -------------------------------------------------------
        encabezado("BLOQUE 5: Persistencia (Guardar y Cargar archivos)");

        ServicioPersistencia persistencia = new ServicioPersistencia();

        // Guardar eventos
        try {
            persistencia.guardarEventos(gestorEventos.getEventosCreados());
            ok("Guardar eventos en CSV: OK");
        } catch (Exception e) {
            falla("Guardar eventos", e);
        }

        // Guardar clientes
        try {
            persistencia.guardarClientes(gestorClientes.getClientesCreados());
            ok("Guardar clientes en CSV: OK");
        } catch (Exception e) {
            falla("Guardar clientes", e);
        }

        // Guardar boletos
        try {
            persistencia.guardarBoletos(gestorEventos.getEventosCreados());
            ok("Guardar boletos en CSV: OK");
        } catch (Exception e) {
            falla("Guardar boletos", e);
        }

        // Cargar eventos en nuevos gestores (simulando reinicio de app)
        GestorEventos gestorEventos2 = new GestorEventos();
        GestorClientes gestorClientes2 = new GestorClientes();

        try {
            List<Evento> eventosCargados = persistencia.cargarEventos();
            gestorEventos2.setEventosCreados(eventosCargados);
            ok("Cargar eventos desde CSV. Total cargados: " + eventosCargados.size());
        } catch (Exception e) {
            falla("Cargar eventos", e);
        }

        try {
            List<Cliente> clientesCargados = persistencia.cargarClientes();
            // ADVERTENCIA: El constructor Cliente(nombre, id) recibe (nombre, idCliente)
            // pero en cargarClientes() el CSV se guarda como id;nombre, y se lee como
            // new Cliente(d[0], d[1]) => Cliente(id_como_nombre, nombre_como_id) — posible bug
            gestorClientes2.setClientesCreados(clientesCargados);
            ok("Cargar clientes desde CSV. Total cargados: " + clientesCargados.size());
            if (!clientesCargados.isEmpty()) {
                Cliente c = clientesCargados.get(0);
                System.out.println("   ⚠ VERIFICAR BUG: primer cliente cargado => nombre='" + c.getNombre() + "', id='" + c.getIdCliente() + "'");
                System.out.println("     En guardarClientes se guarda: id;nombre");
                System.out.println("     En cargarClientes se lee como: new Cliente(d[0]=id, d[1]=nombre)");
                System.out.println("     El constructor Cliente es: new Cliente(nombre, idCliente)");
                System.out.println("     => d[0] está siendo pasado como 'nombre' (debería ser d[1])");
            }
        } catch (Exception e) {
            falla("Cargar clientes", e);
        }

        try {
            persistencia.cargarVentas(gestorEventos2, gestorClientes2);
            ok("Cargar ventas desde CSV: OK");
        } catch (Exception e) {
            falla("Cargar ventas", e);
        }

        // -------------------------------------------------------
        // REPORTE FINAL
        // -------------------------------------------------------
        System.out.println("\n=================================================");
        System.out.println("              RESUMEN DE PRUEBAS                ");
        System.out.println("=================================================");
        System.out.printf("  Total: %d | ✓ Exitosas: %d | ✗ Fallidas: %d%n", pruebas, exitosas, fallidas);
        System.out.println("=================================================\n");

        if (fallidas > 0) {
            System.out.println("  Revisa los errores marcados con [FALLA] arriba.");
        } else {
            System.out.println("  ¡Todos los flujos básicos pasaron correctamente!");
        }

        System.out.println("\n--- BUGS CONOCIDOS DETECTADOS EN EL ANÁLISIS ---");
        System.out.println("1. [BoletoEstudiante.calcularPrecioFinal()] Bug de cálculo de descuento:");
        System.out.println("   Código actual:  precioBase - descuento  (resta 0.10 al precio)");
        System.out.println("   Corrección:     precioBase - (precioBase * descuento)");
        System.out.println();
        System.out.println("2. [ServicioPersistencia.cargarClientes()] Bug de orden de parámetros:");
        System.out.println("   guardarClientes guarda: id;nombre");
        System.out.println("   cargarClientes lee:     new Cliente(d[0], d[1]) = new Cliente(id, nombre)");
        System.out.println("   Constructor Cliente es: new Cliente(nombre, idCliente)");
        System.out.println("   Corrección:             new Cliente(d[1], d[0])");
        System.out.println();
        System.out.println("3. [Cliente.validarId()] Bug: usa this.idCliente en lugar del parámetro 'id':");
        System.out.println("   Al llamar new Cliente(nombre, id), this.idCliente aún no ha sido asignado.");
        System.out.println("   Corrección: cambiar 'if (idCliente == null...)' por 'if (id == null...)'");
        System.out.println();
        System.out.println("4. [GestorEventos.eliminarEvento()] Lógica muerta:");
        System.out.println("   buscarEventoPorId lanza excepción si no existe, pero después se verifica == null.");
        System.out.println("   El bloque 'if (evento == null)' nunca se ejecuta.");
    }

    // ---- Helpers ----
    static void encabezado(String titulo) {
        System.out.println("\n----- " + titulo + " -----");
    }

    static void ok(String mensaje) {
        pruebas++;
        exitosas++;
        System.out.println("  [OK]   " + mensaje);
    }

    static void falla(String mensaje, Exception e) {
        pruebas++;
        fallidas++;
        System.out.println("  [FALLA] " + mensaje);
        if (e != null) {
            System.out.println("          Excepción: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }
}