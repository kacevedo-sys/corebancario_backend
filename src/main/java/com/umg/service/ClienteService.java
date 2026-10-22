package com.umg.service;

import com.umg.bo.Cliente;
import com.umg.repository.ClienteRepository;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Singleton
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> obtener(BigInteger id) {
        return clienteRepository.findById(id);
    }

    @Transactional
    public Cliente crear(Cliente cliente) {
        normalizar(cliente);
        validarCorreoUnico(cliente.getCorreo(), null);
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Optional<Cliente> actualizar(BigInteger id, Cliente cliente) {
        return clienteRepository.findById(id).map(existing -> {
            normalizar(cliente);
            validarCorreoUnico(cliente.getCorreo(), id);
            cliente.setClienteId(id);
            return clienteRepository.update(cliente);
        });
    }

    @Transactional
    public boolean eliminar(BigInteger id) {
        return clienteRepository.findById(id).map(c -> {
            clienteRepository.delete(c);
            return true;
        }).orElse(false);
    }

    private void validarCorreoUnico(String correo, BigInteger clienteIdActual) {
        if (correo == null || correo.isBlank()) {
            return;
        }

        clienteRepository.findByCorreoIgnoreCase(correo).ifPresent(cliente -> {
            if (clienteIdActual == null || !cliente.getClienteId().equals(clienteIdActual)) {
                throw new CorreoDuplicadoException();
            }
        });
    }

    private void normalizar(Cliente cliente) {
        cliente.setNombres(cliente.getNombres() == null ? null : cliente.getNombres().trim());
        cliente.setApellidos(cliente.getApellidos() == null ? null : cliente.getApellidos().trim());
        cliente.setDpi(cliente.getDpi() == null ? null : cliente.getDpi().trim());
        cliente.setTelefono(cliente.getTelefono() == null ? "" : cliente.getTelefono().trim());
        cliente.setCorreo(cliente.getCorreo() == null ? "" : cliente.getCorreo().trim().toLowerCase());
    }

    public static class CorreoDuplicadoException extends RuntimeException {
        public CorreoDuplicadoException() {
            super("El correo ya está registrado para otro cliente.");
        }
    }
}
