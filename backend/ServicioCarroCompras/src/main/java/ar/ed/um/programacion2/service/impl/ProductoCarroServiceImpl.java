package ar.ed.um.programacion2.service.impl;

import ar.ed.um.programacion2.domain.ProductoCarro;
import ar.ed.um.programacion2.repository.ProductoCarroRepository;
import ar.ed.um.programacion2.service.ProductoCarroService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductoCarro}.
 */
@Service
@Transactional
public class ProductoCarroServiceImpl implements ProductoCarroService {

    private final Logger log = LoggerFactory.getLogger(ProductoCarroServiceImpl.class);

    private final ProductoCarroRepository productoCarroRepository;

    public ProductoCarroServiceImpl(ProductoCarroRepository productoCarroRepository) {
        this.productoCarroRepository = productoCarroRepository;
    }

    @Override
    public ProductoCarro save(ProductoCarro productoCarro) {
        log.debug("Request to save ProductoCarro : {}", productoCarro);
        return productoCarroRepository.save(productoCarro);
    }

    @Override
    public Optional<ProductoCarro> partialUpdate(ProductoCarro productoCarro) {
        log.debug("Request to partially update ProductoCarro : {}", productoCarro);

        return productoCarroRepository
            .findById(productoCarro.getId())
            .map(existingProductoCarro -> {
                if (productoCarro.getIdProducto() != null) {
                    existingProductoCarro.setIdProducto(productoCarro.getIdProducto());
                }
                if (productoCarro.getNombre() != null) {
                    existingProductoCarro.setNombre(productoCarro.getNombre());
                }
                if (productoCarro.getDescripcion() != null) {
                    existingProductoCarro.setDescripcion(productoCarro.getDescripcion());
                }
                if (productoCarro.getCantidad() != null) {
                    existingProductoCarro.setCantidad(productoCarro.getCantidad());
                }
                if (productoCarro.getPrecio() != null) {
                    existingProductoCarro.setPrecio(productoCarro.getPrecio());
                }

                return existingProductoCarro;
            })
            .map(productoCarroRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductoCarro> findAll(Pageable pageable) {
        log.debug("Request to get all ProductoCarros");
        return productoCarroRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoCarro> findOne(Long id) {
        log.debug("Request to get ProductoCarro : {}", id);
        return productoCarroRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductoCarro : {}", id);
        productoCarroRepository.deleteById(id);
    }
}
