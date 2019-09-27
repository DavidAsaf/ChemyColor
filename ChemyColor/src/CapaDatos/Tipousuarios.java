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
@Table(name = "TIPOUSUARIOS")
@NamedQueries({
    @NamedQuery(name = "Tipousuarios.findAll", query = "SELECT t FROM Tipousuarios t")})
public class Tipousuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "CODTIPOUSUARIO")
    private BigDecimal codtipousuario;
    @Basic(optional = false)
    @Column(name = "TIPOUSUARIO")
    private String tipousuario;
    @OneToMany(mappedBy = "codtipousuario")
    private List<Usuarios> usuariosList;

    public Tipousuarios() {
    }

    public Tipousuarios(BigDecimal codtipousuario) {
        this.codtipousuario = codtipousuario;
    }

    public Tipousuarios(BigDecimal codtipousuario, String tipousuario) {
        this.codtipousuario = codtipousuario;
        this.tipousuario = tipousuario;
    }

    public BigDecimal getCodtipousuario() {
        return codtipousuario;
    }

    public void setCodtipousuario(BigDecimal codtipousuario) {
        this.codtipousuario = codtipousuario;
    }

    public String getTipousuario() {
        return tipousuario;
    }

    public void setTipousuario(String tipousuario) {
        this.tipousuario = tipousuario;
    }

    public List<Usuarios> getUsuariosList() {
        return usuariosList;
    }

    public void setUsuariosList(List<Usuarios> usuariosList) {
        this.usuariosList = usuariosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codtipousuario != null ? codtipousuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipousuarios)) {
            return false;
        }
        Tipousuarios other = (Tipousuarios) object;
        if ((this.codtipousuario == null && other.codtipousuario != null) || (this.codtipousuario != null && !this.codtipousuario.equals(other.codtipousuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CapaDatos.Tipousuarios[ codtipousuario=" + codtipousuario + " ]";
    }
    
}
