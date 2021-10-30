package ar.ed.um.programacion2.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ar.ed.um.programacion2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductoCarroTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductoCarro.class);
        ProductoCarro productoCarro1 = new ProductoCarro();
        productoCarro1.setId(1L);
        ProductoCarro productoCarro2 = new ProductoCarro();
        productoCarro2.setId(productoCarro1.getId());
        assertThat(productoCarro1).isEqualTo(productoCarro2);
        productoCarro2.setId(2L);
        assertThat(productoCarro1).isNotEqualTo(productoCarro2);
        productoCarro1.setId(null);
        assertThat(productoCarro1).isNotEqualTo(productoCarro2);
    }
}
