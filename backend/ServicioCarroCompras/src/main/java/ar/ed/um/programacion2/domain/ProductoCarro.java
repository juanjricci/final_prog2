package ar.ed.um.programacion2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProductoCarro.
 */
@Entity
@Table(name = "producto_carro")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductoCarro implements Serializable {

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
    private Integer cantidad;

    @Column(name = "precio")
    private Long precio;

    @ManyToOne
    @JsonIgnoreProperties(value = { "idUsuario", "productoCarros", "ventas" }, allowSetters = true)
    private CarroCompra idCarroCompra;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProductoCarro id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdProducto() {
        return this.idProducto;
    }

    public ProductoCarro idProducto(Integer idProducto) {
        this.setIdProducto(idProducto);
        return this;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return this.nombre;
    }

    public ProductoCarro nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public ProductoCarro descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidad() {
        return this.cantidad;
    }

    public ProductoCarro cantidad(Integer cantidad) {
        this.setCantidad(cantidad);
        return this;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Long getPrecio() {
        return this.precio;
    }

    public ProductoCarro precio(Long precio) {
        this.setPrecio(precio);
        return this;
    }

    public void setPrecio(Long precio) {
        this.precio = precio;
    }

    public CarroCompra getIdCarroCompra() {
        return this.idCarroCompra;
    }

    public void setIdCarroCompra(CarroCompra carroCompra) {
        this.idCarroCompra = carroCompra;
    }

    public ProductoCarro idCarroCompra(CarroCompra carroCompra) {
        this.setIdCarroCompra(carroCompra);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductoCarro)) {
            return false;
        }
        return id != null && id.equals(((ProductoCarro) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductoCarro{" +
            "id=" + getId() +
            ", idProducto=" + getIdProducto() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", cantidad=" + getCantidad() +
            ", precio=" + getPrecio() +
            "}";
    }
}
