/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaDatos;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author mario
 */
@Entity
@Table(name = "PROVEEDORES")
@NamedQueries({
    @NamedQuery(name = "Proveedores.findAll", query = "SELECT p FROM Proveedores p")})
public class Proveedores implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "CODIGOPROVEEDOR")
    private BigDecimal codigoproveedor;
    @Column(name = "NOMBRES")
    private String nombres;
    @Column(name = "NRC")
    private String nrc;
    @Column(name = "FECHAINGRESO")
    private String fechaingreso;
    @Column(name = "SALDO")
    private BigDecimal saldo;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "REGISTRO")
    private String registro;
    @Column(name = "NIT")
    private String nit;
    @Column(name = "DUI")
    private String dui;
    @Column(name = "GIRO")
    private String giro;
    @Column(name = "LIMITE")
    private String limite;
    @Column(name = "CUENTAPORPAGAR")
    private String cuentaporpagar;
    @JoinColumn(name = "CODIGOTIPOCONTRIBUYENTE", referencedColumnName = "CODIGOTIPOCONTRIBUYENTE")
    @ManyToOne
    private Tipocontribuyente codigotipocontribuyente;
    @JoinColumn(name = "CODTIPOPROV", referencedColumnName = "CODTIPOPROV")
    @ManyToOne
    private Tipoproveedores codtipoprov;

    public Proveedores() {
    }

    public Proveedores(BigDecimal codigoproveedor) {
        this.codigoproveedor = codigoproveedor;
    }

    public BigDecimal getCodigoproveedor() {
        return codigoproveedor;
    }

    public void setCodigoproveedor(BigDecimal codigoproveedor) {
        this.codigoproveedor = codigoproveedor;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getNrc() {
        return nrc;
    }

    public void setNrc(String nrc) {
        this.nrc = nrc;
    }

    public String getFechaingreso() {
        return fechaingreso;
    }

    public void setFechaingreso(String fechaingreso) {
        this.fechaingreso = fechaingreso;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public String getGiro() {
        return giro;
    }

    public void setGiro(String giro) {
        this.giro = giro;
    }

    public String getLimite() {
        return limite;
    }

    public void setLimite(String limite) {
        this.limite = limite;
    }

    public String getCuentaporpagar() {
        return cuentaporpagar;
    }

    public void setCuentaporpagar(String cuentaporpagar) {
        this.cuentaporpagar = cuentaporpagar;
    }

    public Tipocontribuyente getCodigotipocontribuyente() {
        return codigotipocontribuyente;
    }

    public void setCodigotipocontribuyente(Tipocontribuyente codigotipocontribuyente) {
        this.codigotipocontribuyente = codigotipocontribuyente;
    }

    public Tipoproveedores getCodtipoprov() {
        return codtipoprov;
    }

    public void setCodtipoprov(Tipoproveedores codtipoprov) {
        this.codtipoprov = codtipoprov;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoproveedor != null ? codigoproveedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proveedores)) {
            return false;
        }
        Proveedores other = (Proveedores) object;
        if ((this.codigoproveedor == null && other.codigoproveedor != null) || (this.codigoproveedor != null && !this.codigoproveedor.equals(other.codigoproveedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CapaDatos.Proveedores[ codigoproveedor=" + codigoproveedor + " ]";
    }
    
}
