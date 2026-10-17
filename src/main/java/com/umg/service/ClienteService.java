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
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Optional<Cliente> actualizar(BigInteger id, Cliente cliente) {
        return clienteRepository.findById(id).map(existing -> {
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
}