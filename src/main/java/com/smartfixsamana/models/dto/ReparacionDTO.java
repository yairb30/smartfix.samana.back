package com.smartfixsamana.models.dto;

import java.time.LocalDate;

public class ReparacionDTO {
    
    private Long clienteId;
    private Long celularId;
    private String problema;
    private String estado;
    private LocalDate fechaIngreso;
    private LocalDate fechaEstimadaEntrega;


    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getCelularId() {
        return celularId;
    }

    public void setCelularId(Long celularId) {
        this.celularId = celularId;
    }

    public String getProblema() {
        return problema;
    }

    public void setProblema(String problema) {
        this.problema = problema;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaEstimadaEntrega() {
        return fechaEstimadaEntrega;
    }

    public void setFechaEstimadaEntrega(LocalDate fechaEstimadaEntrega) {
        this.fechaEstimadaEntrega = fechaEstimadaEntrega;
    }

}
