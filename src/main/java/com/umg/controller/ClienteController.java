package com.umg.controller;

import com.umg.bo.Cliente;
import com.umg.dto.PageResponse;
import com.umg.service.ClienteService;
import com.umg.service.ClienteService.CorreoDuplicadoException;
import com.umg.utils.PaginationUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import jakarta.validation.Valid;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller("/clientes")
@Secured("ADMIN")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Get("/{id}")
    public HttpResponse<?> obtener(@PathVariable BigInteger id) {
        Optional<Cliente> cliente = clienteService.obtener(id);
        if (cliente.isPresent()) {
            return HttpResponse.ok(cliente.get());
        }
        return HttpResponse.notFound()
                .body(new ErrorResponse("Cliente no encontrado con ID: " + id));
    }

    @Post
    @Status(io.micronaut.http.HttpStatus.CREATED)
    public HttpResponse<?> crear(@Valid @Body Cliente cliente) {
        int entero = (int) (Math.random() * 100) + 1;

        cliente.setClienteId(new BigInteger(String.valueOf(entero)));
        try {
            return HttpResponse.status(io.micronaut.http.HttpStatus.CREATED)
                    .body(clienteService.crear(cliente));
        } catch (CorreoDuplicadoException exception) {
            return HttpResponse.status(io.micronaut.http.HttpStatus.CONFLICT)
                    .body(new ErrorResponse(exception.getMessage()));
        }
    }

    @Put("/{id}")
    public HttpResponse<?> actualizar(@PathVariable BigInteger id, @Valid @Body Cliente cliente) {
        Optional<Cliente> actualizado;
        try {
            actualizado = clienteService.actualizar(id, cliente);
        } catch (CorreoDuplicadoException exception) {
            return HttpResponse.status(io.micronaut.http.HttpStatus.CONFLICT)
                    .body(new ErrorResponse(exception.getMessage()));
        }
        if (actualizado.isPresent()) {
            return HttpResponse.ok(actualizado.get());
        }
        return HttpResponse.notFound()
                .body(new ErrorResponse("Cliente no encontrado con ID: " + id));
    }

    @Delete("/{id}")
    @Status(io.micronaut.http.HttpStatus.NO_CONTENT)
    public HttpResponse<?> eliminar(@PathVariable BigInteger id) {
        return clienteService.eliminar(id)
                ? HttpResponse.noContent()
                : HttpResponse.notFound()
                .body(new ErrorResponse("Cliente no encontrado con ID: " + id));
    }

    public static class ErrorResponse {
        private String message;
        public ErrorResponse(String message) { this.message = message; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }

    @Get
    public HttpResponse<PageResponse<Cliente>> listar(
            @QueryValue(defaultValue = "0") int page,
            @QueryValue(defaultValue = "5") int size,
            @QueryValue(defaultValue = "clienteId,asc") String sort,
            @QueryValue(defaultValue = "") String query,
            @QueryValue Optional<List<String>> fields
    ) {
        List<Cliente> todos = clienteService.listar();

        // Le pasamos la lista y un "Mapa" de cómo obtener cada campo
        PageResponse<Cliente> response = PaginationUtils.paginate(todos, page, size, query, (field) -> {
            switch (field) {
                case "nombres": return Cliente::getNombres;
                case "apellidos": return Cliente::getApellidos;
                case "dpi": return Cliente::getDpi;
                case "correo": return Cliente::getCorreo;
                case "telefono": return Cliente::getTelefono;
                default: return null;
            }
        });

        return HttpResponse.ok(response);
    }
}
