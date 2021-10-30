import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './producto-carro.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ProductoCarroDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const productoCarroEntity = useAppSelector(state => state.productoCarro.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productoCarroDetailsHeading">
          <Translate contentKey="carroComprasApp.productoCarro.detail.title">ProductoCarro</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{productoCarroEntity.id}</dd>
          <dt>
            <span id="idProducto">
              <Translate contentKey="carroComprasApp.productoCarro.idProducto">Id Producto</Translate>
            </span>
          </dt>
          <dd>{productoCarroEntity.idProducto}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="carroComprasApp.productoCarro.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{productoCarroEntity.nombre}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="carroComprasApp.productoCarro.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{productoCarroEntity.descripcion}</dd>
          <dt>
            <span id="cantidad">
              <Translate contentKey="carroComprasApp.productoCarro.cantidad">Cantidad</Translate>
            </span>
          </dt>
          <dd>{productoCarroEntity.cantidad}</dd>
          <dt>
            <span id="precio">
              <Translate contentKey="carroComprasApp.productoCarro.precio">Precio</Translate>
            </span>
          </dt>
          <dd>{productoCarroEntity.precio}</dd>
          <dt>
            <Translate contentKey="carroComprasApp.productoCarro.idCarroCompra">Id Carro Compra</Translate>
          </dt>
          <dd>{productoCarroEntity.idCarroCompra ? productoCarroEntity.idCarroCompra.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/producto-carro" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/producto-carro/${productoCarroEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductoCarroDetail;
