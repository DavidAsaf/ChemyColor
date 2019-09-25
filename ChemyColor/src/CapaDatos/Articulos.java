/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaDatos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
 * @author gerso
 */
@Entity
@Table(name = "ARTICULOS")
@NamedQueries({
    @NamedQuery(name = "Articulos.findAll", query = "SELECT a FROM Articulos a")})
public class Articulos implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "CODIGOARTICULO")
    private BigDecimal codigoarticulo;
    @Basic(optional = false)
    @Column(name = "CODIGOPRODUCTOS")
    private String codigoproductos;
    @Basic(optional = false)
    @Column(name = "NOMBREARTICULO")
    private String nombrearticulo;
    @Basic(optional = false)
    @Column(name = "CODIGOBARRA")
    private String codigobarra;
    @Column(name = "EXISTENCIAMIN")
    private BigInteger existenciamin;
    @Column(name = "EXISTENCIA")
    private BigInteger existencia;
    @Column(name = "UTILIDAD")
    private BigDecimal utilidad;
    @JoinColumn(name = "CODIGOBODEGA", referencedColumnName = "CODIGOBODEGA")
    @ManyToOne
    private Bodegas codigobodega;
    @JoinColumn(name = "CODIGOGRUPO", referencedColumnName = "CODIGOGRUPO")
    @ManyToOne
    private Grupos codigogrupo;
    @JoinColumn(name = "CODIGOLINEA", referencedColumnName = "CODIGOLINEA")
    @ManyToOne
    private Lineas codigolinea;

    public Articulos() {
    }

    public Articulos(BigDecimal codigoarticulo) {
        this.codigoarticulo = codigoarticulo;
    }

    public Articulos(BigDecimal codigoarticulo, String codigoproductos, String nombrearticulo, String codigobarra) {
        this.codigoarticulo = codigoarticulo;
        this.codigoproductos = codigoproductos;
        this.nombrearticulo = nombrearticulo;
        this.codigobarra = codigobarra;
    }

    public BigDecimal getCodigoarticulo() {
        return codigoarticulo;
    }

    public void setCodigoarticulo(BigDecimal codigoarticulo) {
        this.codigoarticulo = codigoarticulo;
    }

    public String getCodigoproductos() {
        return codigoproductos;
    }

    public void setCodigoproductos(String codigoproductos) {
        this.codigoproductos = codigoproductos;
    }

    public String getNombrearticulo() {
        return nombrearticulo;
    }

    public void setNombrearticulo(String nombrearticulo) {
        this.nombrearticulo = nombrearticulo;
    }

    public String getCodigobarra() {
        return codigobarra;
    }

    public void setCodigobarra(String codigobarra) {
        this.codigobarra = codigobarra;
    }

    public BigInteger getExistenciamin() {
        return existenciamin;
    }

    public void setExistenciamin(BigInteger existenciamin) {
        this.existenciamin = existenciamin;
    }

    public BigInteger getExistencia() {
        return existencia;
    }

    public void setExistencia(BigInteger existencia) {
        this.existencia = existencia;
    }

    public BigDecimal getUtilidad() {
        return utilidad;
    }

    public void setUtilidad(BigDecimal utilidad) {
        this.utilidad = utilidad;
    }

    public Bodegas getCodigobodega() {
        return codigobodega;
    }

    public void setCodigobodega(Bodegas codigobodega) {
        this.codigobodega = codigobodega;
    }

    public Grupos getCodigogrupo() {
        return codigogrupo;
    }

    public void setCodigogrupo(Grupos codigogrupo) {
        this.codigogrupo = codigogrupo;
    }

    public Lineas getCodigolinea() {
        return codigolinea;
    }

    public void setCodigolinea(Lineas codigolinea) {
        this.codigolinea = codigolinea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoarticulo != null ? codigoarticulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Articulos)) {
            return false;
        }
        Articulos other = (Articulos) object;
        if ((this.codigoarticulo == null && other.codigoarticulo != null) || (this.codigoarticulo != null && !this.codigoarticulo.equals(other.codigoarticulo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CapaDatos.Articulos[ codigoarticulo=" + codigoarticulo + " ]";
    }
    
}
