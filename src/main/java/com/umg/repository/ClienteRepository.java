package com.umg.repository;

import com.umg.bo.Cliente;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import io.micronaut.data.repository.PageableRepository;

import java.math.BigInteger;

@JdbcRepository(dialect = Dialect.ORACLE)
public interface ClienteRepository extends
        CrudRepository<Cliente, BigInteger>,
        PageableRepository<Cliente, BigInteger> {

}