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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author mario
 */
@Entity
@Table(name = "USUARIOS")
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u")})
public class Usuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODIGOUSUARIO")
    private BigDecimal codigousuario;
        @SequenceGenerator(initialValue=1, sequenceName="usuario1",allocationSize=1,name="USUARIO")
    @GeneratedValue(generator="USUARIO")
    @Basic(optional = false)
    @Column(name = "NOMBREUSUARIO")
    private String nombreusuario;
    @Basic(optional = false)
    @Column(name = "APELLIDOUSUARIO")
    private String apellidousuario;
    @Column(name = "EMAIL")
    private String email;
    @Basic(optional = false)
    @Column(name = "USUARIO")
    private String usuario;
    @Basic(optional = false)
    @Column(name = "CONTRASENA")
    private String contrasena;
    @Column(name = "CELULAR")
    private String celular;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "ESTADO")
    private BigInteger estado;
    @JoinColumn(name = "CODTIPOUSUARIO", referencedColumnName = "CODTIPOUSUARIO")
    @ManyToOne
    private Tipousuarios codtipousuario;

    public Usuarios() {
    }

    public Usuarios(BigDecimal codigousuario) {
        this.codigousuario = codigousuario;
    }

    public Usuarios(BigDecimal codigousuario, String nombreusuario, String apellidousuario, String usuario, String contrasena) {
        this.codigousuario = codigousuario;
        this.nombreusuario = nombreusuario;
        this.apellidousuario = apellidousuario;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public BigDecimal getCodigousuario() {
        return codigousuario;
    }

    public void setCodigousuario(BigDecimal codigousuario) {
        this.codigousuario = codigousuario;
    }

    public String getNombreusuario() {
        return nombreusuario;
    }

    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }

    public String getApellidousuario() {
        return apellidousuario;
    }

    public void setApellidousuario(String apellidousuario) {
        this.apellidousuario = apellidousuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public BigInteger getEstado() {
        return estado;
    }

    public void setEstado(BigInteger estado) {
        this.estado = estado;
    }

    public Tipousuarios getCodtipousuario() {
        return codtipousuario;
    }

    public void setCodtipousuario(Tipousuarios codtipousuario) {
        this.codtipousuario = codtipousuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigousuario != null ? codigousuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.codigousuario == null && other.codigousuario != null) || (this.codigousuario != null && !this.codigousuario.equals(other.codigousuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CapaDatos.Usuarios[ codigousuario=" + codigousuario + " ]";
    }
    
}
