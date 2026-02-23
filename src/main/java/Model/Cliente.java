package Model;

/**
 * @author josue (Corregido)
 */
public class Cliente {

    private String nombre;
    private String idCliente;
    
    public Cliente(String nombre, String idCliente) {
        // Primero validamos los parámetros que entran
        validarNombre(nombre);
        validarId(idCliente);

        // Si las validaciones pasan, asignamos
        this.nombre = nombre;
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        validarNombre(nombre); // Validar también al editar
        this.nombre = nombre;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        validarId(idCliente); // Validar también al editar
        this.idCliente = idCliente;
    }

    private void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacío");
        }
        if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            throw new IllegalArgumentException("El nombre del cliente solo puede contener letras y espacios");
        }
        if (nombre.trim().length() < 2) {
            throw new IllegalArgumentException("El nombre del cliente debe tener al menos 2 caracteres");
        }

    }

    private void validarId(String id) {
        // CORRECCIÓN: Usar 'id' (el parámetro), no 'idCliente' (la variable global)
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del cliente no puede estar vacío");
        }
        
        // Se ha removido la validación de longitud (5-20) para permitir IDs extranjeros
        // y evitar conflictos con objetos temporales de cálculo.
    }
}