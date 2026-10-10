package com.umg.bo;

import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
public class Cuenta {
    private BigInteger id;
    private String numeroCuenta;
    private String tipoCuenta;         // AHORRO, MONETARIA, etc.
    private BigDecimal saldo;
    private BigInteger clienteId;
    private LocalDateTime fechaApertura;
    private String estado;             // ACTIVA, INACTIVA, BLOQUEADA

    public Cuenta() {}

    public Cuenta(BigInteger id, String numeroCuenta, String tipoCuenta, BigDecimal saldo,
                  BigInteger clienteId, LocalDateTime fechaApertura, String estado) {
        this.id = id;
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.saldo = saldo;
        this.clienteId = clienteId;
        this.fechaApertura = fechaApertura;
        this.estado = estado;
    }

}