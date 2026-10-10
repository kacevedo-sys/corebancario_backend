package com.umg.repository;

import com.umg.bo.Cliente;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import java.math.BigInteger;

@JdbcRepository(dialect = Dialect.ORACLE)
public interface ClienteRepository extends CrudRepository<Cliente, BigInteger> {
}