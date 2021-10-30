package ar.ed.um.programacion2.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ar.ed.um.programacion2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductoVendidoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductoVendido.class);
        ProductoVendido productoVendido1 = new ProductoVendido();
        productoVendido1.setId(1L);
        ProductoVendido productoVendido2 = new ProductoVendido();
        productoVendido2.setId(productoVendido1.getId());
        assertThat(productoVendido1).isEqualTo(productoVendido2);
        productoVendido2.setId(2L);
        assertThat(productoVendido1).isNotEqualTo(productoVendido2);
        productoVendido1.setId(null);
        assertThat(productoVendido1).isNotEqualTo(productoVendido2);
    }
}
