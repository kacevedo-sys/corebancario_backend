package com.umg.controller;

import com.umg.bo.Cliente;
import com.umg.service.ClienteService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.validation.Valid;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Controller("/clientes")
@Secured("ADMIN")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Get
    public HttpResponse<Iterable<Cliente>> listar() {
        return HttpResponse.ok(clienteService.listar());
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
    public HttpResponse<Cliente> crear(@Valid @Body Cliente cliente) {
        int entero = (int) (Math.random() * 100) + 1;

        cliente.setClienteId(new BigInteger(String.valueOf(entero)));
        return HttpResponse.status(io.micronaut.http.HttpStatus.CREATED)
                .body(clienteService.crear(cliente));
    }

    @Put("/{id}")
    public HttpResponse<?> actualizar(@PathVariable BigInteger id, @Valid @Body Cliente cliente) {
        Optional<Cliente> actualizado = clienteService.actualizar(id, cliente);
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
}