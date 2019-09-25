/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaDatos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author gerso
 */
@Entity
@Table(name = "BODEGAS")
@NamedQueries({
    @NamedQuery(name = "Bodegas.findAll", query = "SELECT b FROM Bodegas b")})
public class Bodegas implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "CODIGOBODEGA")
    private BigDecimal codigobodega;
    @Basic(optional = false)
    @Column(name = "NOMBREBODEGA")
    private String nombrebodega;
    @Basic(optional = false)
    @Column(name = "DIRECCION")
    private String direccion;
    @Basic(optional = false)
    @Column(name = "EMAIL")
    private String email;
    @Basic(optional = false)
    @Column(name = "ENCARGADO")
    private String encargado;
    @Column(name = "DARCREDITO")
    private BigInteger darcredito;
    @Column(name = "PMODPREC")
    private BigInteger pmodprec;
    @Column(name = "PFACSINEXIS")
    private BigInteger pfacsinexis;
    @Column(name = "DESCMAXPORVEND")
    private String descmaxporvend;
    @Column(name = "SERIECOMPROBANTE")
    private String seriecomprobante;
    @Column(name = "DESCMAXPERMITIDO")
    private String descmaxpermitido;
    @Column(name = "MULTICAJA")
    private BigInteger multicaja;
    @Column(name = "IMPRECAB")
    private BigInteger imprecab;
    @Column(name = "ESTADO")
    private BigInteger estado;
    @OneToMany(mappedBy = "codigobodega")
    private List<Articulos> articulosList;

    public Bodegas() {
    }

    public Bodegas(BigDecimal codigobodega) {
        this.codigobodega = codigobodega;
    }

    public Bodegas(BigDecimal codigobodega, String nombrebodega, String direccion, String email, String encargado) {
        this.codigobodega = codigobodega;
        this.nombrebodega = nombrebodega;
        this.direccion = direccion;
        this.email = email;
        this.encargado = encargado;
    }

    public BigDecimal getCodigobodega() {
        return codigobodega;
    }

    public void setCodigobodega(BigDecimal codigobodega) {
        this.codigobodega = codigobodega;
    }

    public String getNombrebodega() {
        return nombrebodega;
    }

    public void setNombrebodega(String nombrebodega) {
        this.nombrebodega = nombrebodega;
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

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public BigInteger getDarcredito() {
        return darcredito;
    }

    public void setDarcredito(BigInteger darcredito) {
        this.darcredito = darcredito;
    }

    public BigInteger getPmodprec() {
        return pmodprec;
    }

    public void setPmodprec(BigInteger pmodprec) {
        this.pmodprec = pmodprec;
    }

    public BigInteger getPfacsinexis() {
        return pfacsinexis;
    }

    public void setPfacsinexis(BigInteger pfacsinexis) {
        this.pfacsinexis = pfacsinexis;
    }

    public String getDescmaxporvend() {
        return descmaxporvend;
    }

    public void setDescmaxporvend(String descmaxporvend) {
        this.descmaxporvend = descmaxporvend;
    }

    public String getSeriecomprobante() {
        return seriecomprobante;
    }

    public void setSeriecomprobante(String seriecomprobante) {
        this.seriecomprobante = seriecomprobante;
    }

    public String getDescmaxpermitido() {
        return descmaxpermitido;
    }

    public void setDescmaxpermitido(String descmaxpermitido) {
        this.descmaxpermitido = descmaxpermitido;
    }

    public BigInteger getMulticaja() {
        return multicaja;
    }

    public void setMulticaja(BigInteger multicaja) {
        this.multicaja = multicaja;
    }

    public BigInteger getImprecab() {
        return imprecab;
    }

    public void setImprecab(BigInteger imprecab) {
        this.imprecab = imprecab;
    }

    public BigInteger getEstado() {
        return estado;
    }

    public void setEstado(BigInteger estado) {
        this.estado = estado;
    }

    public List<Articulos> getArticulosList() {
        return articulosList;
    }

    public void setArticulosList(List<Articulos> articulosList) {
        this.articulosList = articulosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigobodega != null ? codigobodega.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bodegas)) {
            return false;
        }
        Bodegas other = (Bodegas) object;
        if ((this.codigobodega == null && other.codigobodega != null) || (this.codigobodega != null && !this.codigobodega.equals(other.codigobodega))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CapaDatos.Bodegas[ codigobodega=" + codigobodega + " ]";
    }
    
}
