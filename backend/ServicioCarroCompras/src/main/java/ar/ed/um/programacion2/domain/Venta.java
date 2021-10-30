package ar.ed.um.programacion2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Venta.
 */
@Entity
@Table(name = "venta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha_venta")
    private Instant fechaVenta;

    @Column(name = "precio_total")
    private Long precioTotal;

    @OneToMany(mappedBy = "idVenta")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "idVenta" }, allowSetters = true)
    private Set<ProductoVendido> productoVendidos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "idUsuario", "productoCarros", "ventas" }, allowSetters = true)
    private CarroCompra idCarroCompra;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Venta id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFechaVenta() {
        return this.fechaVenta;
    }

    public Venta fechaVenta(Instant fechaVenta) {
        this.setFechaVenta(fechaVenta);
        return this;
    }

    public void setFechaVenta(Instant fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Long getPrecioTotal() {
        return this.precioTotal;
    }

    public Venta precioTotal(Long precioTotal) {
        this.setPrecioTotal(precioTotal);
        return this;
    }

    public void setPrecioTotal(Long precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Set<ProductoVendido> getProductoVendidos() {
        return this.productoVendidos;
    }

    public void setProductoVendidos(Set<ProductoVendido> productoVendidos) {
        if (this.productoVendidos != null) {
            this.productoVendidos.forEach(i -> i.setIdVenta(null));
        }
        if (productoVendidos != null) {
            productoVendidos.forEach(i -> i.setIdVenta(this));
        }
        this.productoVendidos = productoVendidos;
    }

    public Venta productoVendidos(Set<ProductoVendido> productoVendidos) {
        this.setProductoVendidos(productoVendidos);
        return this;
    }

    public Venta addProductoVendido(ProductoVendido productoVendido) {
        this.productoVendidos.add(productoVendido);
        productoVendido.setIdVenta(this);
        return this;
    }

    public Venta removeProductoVendido(ProductoVendido productoVendido) {
        this.productoVendidos.remove(productoVendido);
        productoVendido.setIdVenta(null);
        return this;
    }

    public CarroCompra getIdCarroCompra() {
        return this.idCarroCompra;
    }

    public void setIdCarroCompra(CarroCompra carroCompra) {
        this.idCarroCompra = carroCompra;
    }

    public Venta idCarroCompra(CarroCompra carroCompra) {
        this.setIdCarroCompra(carroCompra);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Venta)) {
            return false;
        }
        return id != null && id.equals(((Venta) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Venta{" +
            "id=" + getId() +
            ", fechaVenta='" + getFechaVenta() + "'" +
            ", precioTotal=" + getPrecioTotal() +
            "}";
    }
}
