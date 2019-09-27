/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaDatos;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author mario
 */
@Entity
@Table(name = "TIPOCONTRIBUYENTE")
@NamedQueries({
    @NamedQuery(name = "Tipocontribuyente.findAll", query = "SELECT t FROM Tipocontribuyente t")})
public class Tipocontribuyente implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "CODIGOTIPOCONTRIBUYENTE")
    private BigDecimal codigotipocontribuyente;
    @Column(name = "TIPOCONTRIBUYENTE")
    private String tipocontribuyente;
    @OneToMany(mappedBy = "codigotipocontribuyente")
    private List<Proveedores> proveedoresList;

    public Tipocontribuyente() {
    }

    public Tipocontribuyente(BigDecimal codigotipocontribuyente) {
        this.codigotipocontribuyente = codigotipocontribuyente;
    }

    public BigDecimal getCodigotipocontribuyente() {
        return codigotipocontribuyente;
    }

    public void setCodigotipocontribuyente(BigDecimal codigotipocontribuyente) {
        this.codigotipocontribuyente = codigotipocontribuyente;
    }

    public String getTipocontribuyente() {
        return tipocontribuyente;
    }

    public void setTipocontribuyente(String tipocontribuyente) {
        this.tipocontribuyente = tipocontribuyente;
    }

    public List<Proveedores> getProveedoresList() {
        return proveedoresList;
    }

    public void setProveedoresList(List<Proveedores> proveedoresList) {
        this.proveedoresList = proveedoresList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigotipocontribuyente != null ? codigotipocontribuyente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipocontribuyente)) {
            return false;
        }
        Tipocontribuyente other = (Tipocontribuyente) object;
        if ((this.codigotipocontribuyente == null && other.codigotipocontribuyente != null) || (this.codigotipocontribuyente != null && !this.codigotipocontribuyente.equals(other.codigotipocontribuyente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CapaDatos.Tipocontribuyente[ codigotipocontribuyente=" + codigotipocontribuyente + " ]";
    }
    
}
