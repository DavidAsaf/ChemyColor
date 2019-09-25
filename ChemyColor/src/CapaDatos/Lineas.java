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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author gerso
 */
@Entity
@Table(name = "LINEAS")
@NamedQueries({
    @NamedQuery(name = "Lineas.findAll", query = "SELECT l FROM Lineas l")})
public class Lineas implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "CODIGOLINEA")
    private BigDecimal codigolinea;
    @Column(name = "NOMBRELINEAS")
    private String nombrelineas;
    @OneToMany(mappedBy = "codigolinea")
    private List<Articulos> articulosList;
    @JoinColumn(name = "CODIGOGRUPO", referencedColumnName = "CODIGOGRUPO")
    @ManyToOne
    private Grupos codigogrupo;

    public Lineas() {
    }

    public Lineas(BigDecimal codigolinea) {
        this.codigolinea = codigolinea;
    }

    public BigDecimal getCodigolinea() {
        return codigolinea;
    }

    public void setCodigolinea(BigDecimal codigolinea) {
        this.codigolinea = codigolinea;
    }

    public String getNombrelineas() {
        return nombrelineas;
    }

    public void setNombrelineas(String nombrelineas) {
        this.nombrelineas = nombrelineas;
    }

    public List<Articulos> getArticulosList() {
        return articulosList;
    }

    public void setArticulosList(List<Articulos> articulosList) {
        this.articulosList = articulosList;
    }

    public Grupos getCodigogrupo() {
        return codigogrupo;
    }

    public void setCodigogrupo(Grupos codigogrupo) {
        this.codigogrupo = codigogrupo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigolinea != null ? codigolinea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lineas)) {
            return false;
        }
        Lineas other = (Lineas) object;
        if ((this.codigolinea == null && other.codigolinea != null) || (this.codigolinea != null && !this.codigolinea.equals(other.codigolinea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CapaDatos.Lineas[ codigolinea=" + codigolinea + " ]";
    }
    
}
