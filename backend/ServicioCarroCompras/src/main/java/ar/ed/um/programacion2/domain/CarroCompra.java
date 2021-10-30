package ar.ed.um.programacion2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CarroCompra.
 */
@Entity
@Table(name = "carro_compra")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CarroCompra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private Usuario idUsuario;

    @OneToMany(mappedBy = "idCarroCompra")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "idCarroCompra" }, allowSetters = true)
    private Set<ProductoCarro> productoCarros = new HashSet<>();

    @OneToMany(mappedBy = "idCarroCompra")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "productoVendidos", "idCarroCompra" }, allowSetters = true)
    private Set<Venta> ventas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CarroCompra id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(Usuario usuario) {
        this.idUsuario = usuario;
    }

    public CarroCompra idUsuario(Usuario usuario) {
        this.setIdUsuario(usuario);
        return this;
    }

    public Set<ProductoCarro> getProductoCarros() {
        return this.productoCarros;
    }

    public void setProductoCarros(Set<ProductoCarro> productoCarros) {
        if (this.productoCarros != null) {
            this.productoCarros.forEach(i -> i.setIdCarroCompra(null));
        }
        if (productoCarros != null) {
            productoCarros.forEach(i -> i.setIdCarroCompra(this));
        }
        this.productoCarros = productoCarros;
    }

    public CarroCompra productoCarros(Set<ProductoCarro> productoCarros) {
        this.setProductoCarros(productoCarros);
        return this;
    }

    public CarroCompra addProductoCarro(ProductoCarro productoCarro) {
        this.productoCarros.add(productoCarro);
        productoCarro.setIdCarroCompra(this);
        return this;
    }

    public CarroCompra removeProductoCarro(ProductoCarro productoCarro) {
        this.productoCarros.remove(productoCarro);
        productoCarro.setIdCarroCompra(null);
        return this;
    }

    public Set<Venta> getVentas() {
        return this.ventas;
    }

    public void setVentas(Set<Venta> ventas) {
        if (this.ventas != null) {
            this.ventas.forEach(i -> i.setIdCarroCompra(null));
        }
        if (ventas != null) {
            ventas.forEach(i -> i.setIdCarroCompra(this));
        }
        this.ventas = ventas;
    }

    public CarroCompra ventas(Set<Venta> ventas) {
        this.setVentas(ventas);
        return this;
    }

    public CarroCompra addVenta(Venta venta) {
        this.ventas.add(venta);
        venta.setIdCarroCompra(this);
        return this;
    }

    public CarroCompra removeVenta(Venta venta) {
        this.ventas.remove(venta);
        venta.setIdCarroCompra(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarroCompra)) {
            return false;
        }
        return id != null && id.equals(((CarroCompra) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CarroCompra{" +
            "id=" + getId() +
            "}";
    }
}
