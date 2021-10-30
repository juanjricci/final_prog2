package ar.ed.um.programacion2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProductoVendido.
 */
@Entity
@Table(name = "producto_vendido")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductoVendido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_producto")
    private Integer idProducto;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "cantidad")
    private Long cantidad;

    @Column(name = "precio_total")
    private Long precioTotal;

    @ManyToOne
    @JsonIgnoreProperties(value = { "productoVendidos", "idCarroCompra" }, allowSetters = true)
    private Venta idVenta;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProductoVendido id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdProducto() {
        return this.idProducto;
    }

    public ProductoVendido idProducto(Integer idProducto) {
        this.setIdProducto(idProducto);
        return this;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return this.nombre;
    }

    public ProductoVendido nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public ProductoVendido descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getCantidad() {
        return this.cantidad;
    }

    public ProductoVendido cantidad(Long cantidad) {
        this.setCantidad(cantidad);
        return this;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public Long getPrecioTotal() {
        return this.precioTotal;
    }

    public ProductoVendido precioTotal(Long precioTotal) {
        this.setPrecioTotal(precioTotal);
        return this;
    }

    public void setPrecioTotal(Long precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Venta getIdVenta() {
        return this.idVenta;
    }

    public void setIdVenta(Venta venta) {
        this.idVenta = venta;
    }

    public ProductoVendido idVenta(Venta venta) {
        this.setIdVenta(venta);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductoVendido)) {
            return false;
        }
        return id != null && id.equals(((ProductoVendido) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductoVendido{" +
            "id=" + getId() +
            ", idProducto=" + getIdProducto() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", cantidad=" + getCantidad() +
            ", precioTotal=" + getPrecioTotal() +
            "}";
    }
}
