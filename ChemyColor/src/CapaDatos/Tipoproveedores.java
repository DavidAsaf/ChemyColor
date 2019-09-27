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
 * @author gerso
 */
@Entity
@Table(name = "TIPOPROVEEDORES")
@NamedQueries({
    @NamedQuery(name = "Tipoproveedores.findAll", query = "SELECT t FROM Tipoproveedores t")})
public class Tipoproveedores implements Serializable {

    @OneToMany(mappedBy = "codtipoprov")
    private List<Proveedores> proveedoresList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "CODTIPOPROV")
    private BigDecimal codtipoprov;
    @Column(name = "TIPOPROV")
    private String tipoprov;

    public Tipoproveedores() {
    }

    public Tipoproveedores(BigDecimal codtipoprov) {
        this.codtipoprov = codtipoprov;
    }

    public BigDecimal getCodtipoprov() {
        return codtipoprov;
    }

    public void setCodtipoprov(BigDecimal codtipoprov) {
        this.codtipoprov = codtipoprov;
    }

    public String getTipoprov() {
        return tipoprov;
    }

    public void setTipoprov(String tipoprov) {
        this.tipoprov = tipoprov;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codtipoprov != null ? codtipoprov.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipoproveedores)) {
            return false;
        }
        Tipoproveedores other = (Tipoproveedores) object;
        if ((this.codtipoprov == null && other.codtipoprov != null) || (this.codtipoprov != null && !this.codtipoprov.equals(other.codtipoprov))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CapaDatos.Tipoproveedores[ codtipoprov=" + codtipoprov + " ]";
    }

    public List<Proveedores> getProveedoresList() {
        return proveedoresList;
    }

    public void setProveedoresList(List<Proveedores> proveedoresList) {
        this.proveedoresList = proveedoresList;
    }
    
}
