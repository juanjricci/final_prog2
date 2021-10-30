package ar.ed.um.programacion2.service.impl;

import ar.ed.um.programacion2.domain.ProductoVendido;
import ar.ed.um.programacion2.repository.ProductoVendidoRepository;
import ar.ed.um.programacion2.service.ProductoVendidoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductoVendido}.
 */
@Service
@Transactional
public class ProductoVendidoServiceImpl implements ProductoVendidoService {

    private final Logger log = LoggerFactory.getLogger(ProductoVendidoServiceImpl.class);

    private final ProductoVendidoRepository productoVendidoRepository;

    public ProductoVendidoServiceImpl(ProductoVendidoRepository productoVendidoRepository) {
        this.productoVendidoRepository = productoVendidoRepository;
    }

    @Override
    public ProductoVendido save(ProductoVendido productoVendido) {
        log.debug("Request to save ProductoVendido : {}", productoVendido);
        return productoVendidoRepository.save(productoVendido);
    }

    @Override
    public Optional<ProductoVendido> partialUpdate(ProductoVendido productoVendido) {
        log.debug("Request to partially update ProductoVendido : {}", productoVendido);

        return productoVendidoRepository
            .findById(productoVendido.getId())
            .map(existingProductoVendido -> {
                if (productoVendido.getIdProducto() != null) {
                    existingProductoVendido.setIdProducto(productoVendido.getIdProducto());
                }
                if (productoVendido.getNombre() != null) {
                    existingProductoVendido.setNombre(productoVendido.getNombre());
                }
                if (productoVendido.getDescripcion() != null) {
                    existingProductoVendido.setDescripcion(productoVendido.getDescripcion());
                }
                if (productoVendido.getCantidad() != null) {
                    existingProductoVendido.setCantidad(productoVendido.getCantidad());
                }
                if (productoVendido.getPrecioTotal() != null) {
                    existingProductoVendido.setPrecioTotal(productoVendido.getPrecioTotal());
                }

                return existingProductoVendido;
            })
            .map(productoVendidoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductoVendido> findAll(Pageable pageable) {
        log.debug("Request to get all ProductoVendidos");
        return productoVendidoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoVendido> findOne(Long id) {
        log.debug("Request to get ProductoVendido : {}", id);
        return productoVendidoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductoVendido : {}", id);
        productoVendidoRepository.deleteById(id);
    }
}
