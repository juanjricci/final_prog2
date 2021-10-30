package ar.ed.um.programacion2.service.impl;

import ar.ed.um.programacion2.domain.CarroCompra;
import ar.ed.um.programacion2.repository.CarroCompraRepository;
import ar.ed.um.programacion2.service.CarroCompraService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CarroCompra}.
 */
@Service
@Transactional
public class CarroCompraServiceImpl implements CarroCompraService {

    private final Logger log = LoggerFactory.getLogger(CarroCompraServiceImpl.class);

    private final CarroCompraRepository carroCompraRepository;

    public CarroCompraServiceImpl(CarroCompraRepository carroCompraRepository) {
        this.carroCompraRepository = carroCompraRepository;
    }

    @Override
    public CarroCompra save(CarroCompra carroCompra) {
        log.debug("Request to save CarroCompra : {}", carroCompra);
        return carroCompraRepository.save(carroCompra);
    }

    @Override
    public Optional<CarroCompra> partialUpdate(CarroCompra carroCompra) {
        log.debug("Request to partially update CarroCompra : {}", carroCompra);

        return carroCompraRepository
            .findById(carroCompra.getId())
            .map(existingCarroCompra -> {
                return existingCarroCompra;
            })
            .map(carroCompraRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CarroCompra> findAll(Pageable pageable) {
        log.debug("Request to get all CarroCompras");
        return carroCompraRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CarroCompra> findOne(Long id) {
        log.debug("Request to get CarroCompra : {}", id);
        return carroCompraRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CarroCompra : {}", id);
        carroCompraRepository.deleteById(id);
    }
}
