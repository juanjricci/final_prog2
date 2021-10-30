import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './producto-vendido.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ProductoVendidoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const productoVendidoEntity = useAppSelector(state => state.productoVendido.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productoVendidoDetailsHeading">
          <Translate contentKey="carroComprasApp.productoVendido.detail.title">ProductoVendido</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{productoVendidoEntity.id}</dd>
          <dt>
            <span id="idProducto">
              <Translate contentKey="carroComprasApp.productoVendido.idProducto">Id Producto</Translate>
            </span>
          </dt>
          <dd>{productoVendidoEntity.idProducto}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="carroComprasApp.productoVendido.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{productoVendidoEntity.nombre}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="carroComprasApp.productoVendido.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{productoVendidoEntity.descripcion}</dd>
          <dt>
            <span id="cantidad">
              <Translate contentKey="carroComprasApp.productoVendido.cantidad">Cantidad</Translate>
            </span>
          </dt>
          <dd>{productoVendidoEntity.cantidad}</dd>
          <dt>
            <span id="precioTotal">
              <Translate contentKey="carroComprasApp.productoVendido.precioTotal">Precio Total</Translate>
            </span>
          </dt>
          <dd>{productoVendidoEntity.precioTotal}</dd>
          <dt>
            <Translate contentKey="carroComprasApp.productoVendido.idVenta">Id Venta</Translate>
          </dt>
          <dd>{productoVendidoEntity.idVenta ? productoVendidoEntity.idVenta.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/producto-vendido" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/producto-vendido/${productoVendidoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductoVendidoDetail;
