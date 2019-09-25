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
@Table(name = "GRUPOS")
@NamedQueries({
    @NamedQuery(name = "Grupos.findAll", query = "SELECT g FROM Grupos g")})
public class Grupos implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "CODIGOGRUPO")
    private BigDecimal codigogrupo;
    @Column(name = "NOMBREGRUPO")
    private String nombregrupo;
    @Column(name = "COMISION")
    private BigDecimal comision;
    @OneToMany(mappedBy = "codigogrupo")
    private List<Articulos> articulosList;
    @OneToMany(mappedBy = "codigogrupo")
    private List<Lineas> lineasList;

    public Grupos() {
    }

    public Grupos(BigDecimal codigogrupo) {
        this.codigogrupo = codigogrupo;
    }

    public BigDecimal getCodigogrupo() {
        return codigogrupo;
    }

    public void setCodigogrupo(BigDecimal codigogrupo) {
        this.codigogrupo = codigogrupo;
    }

    public String getNombregrupo() {
        return nombregrupo;
    }

    public void setNombregrupo(String nombregrupo) {
        this.nombregrupo = nombregrupo;
    }

    public BigDecimal getComision() {
        return comision;
    }

    public void setComision(BigDecimal comision) {
        this.comision = comision;
    }

    public List<Articulos> getArticulosList() {
        return articulosList;
    }

    public void setArticulosList(List<Articulos> articulosList) {
        this.articulosList = articulosList;
    }

    public List<Lineas> getLineasList() {
        return lineasList;
    }

    public void setLineasList(List<Lineas> lineasList) {
        this.lineasList = lineasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigogrupo != null ? codigogrupo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grupos)) {
            return false;
        }
        Grupos other = (Grupos) object;
        if ((this.codigogrupo == null && other.codigogrupo != null) || (this.codigogrupo != null && !this.codigogrupo.equals(other.codigogrupo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CapaDatos.Grupos[ codigogrupo=" + codigogrupo + " ]";
    }
    
}
