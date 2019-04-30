/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basededatos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author PC06
 */
@Entity
@Table(name = "TRABAJO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Trabajo.findAll", query = "SELECT t FROM Trabajo t")
    , @NamedQuery(name = "Trabajo.findByIdTrabajo", query = "SELECT t FROM Trabajo t WHERE t.idTrabajo = :idTrabajo")
    , @NamedQuery(name = "Trabajo.findByNombre", query = "SELECT t FROM Trabajo t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "Trabajo.findByEmpresa", query = "SELECT t FROM Trabajo t WHERE t.empresa = :empresa")
    , @NamedQuery(name = "Trabajo.findByHorasSemanales", query = "SELECT t FROM Trabajo t WHERE t.horasSemanales = :horasSemanales")
    , @NamedQuery(name = "Trabajo.findBySalario", query = "SELECT t FROM Trabajo t WHERE t.salario = :salario")
    , @NamedQuery(name = "Trabajo.findByFechaAdmision", query = "SELECT t FROM Trabajo t WHERE t.fechaAdmision = :fechaAdmision")})
public class Trabajo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_TRABAJO")
    private Integer idTrabajo;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "EMPRESA")
    private String empresa;
    @Basic(optional = false)
    @Column(name = "HORAS_SEMANALES")
    private short horasSemanales;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SALARIO")
    private BigDecimal salario;
    @Column(name = "FECHA_ADMISION")
    @Temporal(TemporalType.DATE)
    private Date fechaAdmision;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trabajo")
    private Collection<Usuario> usuarioCollection;

    public Trabajo() {
    }

    public Trabajo(Integer idTrabajo) {
        this.idTrabajo = idTrabajo;
    }

    public Trabajo(Integer idTrabajo, String nombre, short horasSemanales) {
        this.idTrabajo = idTrabajo;
        this.nombre = nombre;
        this.horasSemanales = horasSemanales;
    }

    public Integer getIdTrabajo() {
        return idTrabajo;
    }

    public void setIdTrabajo(Integer idTrabajo) {
        this.idTrabajo = idTrabajo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public short getHorasSemanales() {
        return horasSemanales;
    }

    public void setHorasSemanales(short horasSemanales) {
        this.horasSemanales = horasSemanales;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public Date getFechaAdmision() {
        return fechaAdmision;
    }

    public void setFechaAdmision(Date fechaAdmision) {
        this.fechaAdmision = fechaAdmision;
    }

    @XmlTransient
    public Collection<Usuario> getUsuarioCollection() {
        return usuarioCollection;
    }

    public void setUsuarioCollection(Collection<Usuario> usuarioCollection) {
        this.usuarioCollection = usuarioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTrabajo != null ? idTrabajo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Trabajo)) {
            return false;
        }
        Trabajo other = (Trabajo) object;
        if ((this.idTrabajo == null && other.idTrabajo != null) || (this.idTrabajo != null && !this.idTrabajo.equals(other.idTrabajo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "basededatos.Trabajo[ idTrabajo=" + idTrabajo + " ]";
    }
    
}
