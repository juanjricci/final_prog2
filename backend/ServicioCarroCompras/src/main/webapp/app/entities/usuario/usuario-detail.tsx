import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './usuario.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const UsuarioDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const usuarioEntity = useAppSelector(state => state.usuario.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="usuarioDetailsHeading">
          <Translate contentKey="carroComprasApp.usuario.detail.title">Usuario</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{usuarioEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="carroComprasApp.usuario.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{usuarioEntity.nombre}</dd>
          <dt>
            <span id="apellido">
              <Translate contentKey="carroComprasApp.usuario.apellido">Apellido</Translate>
            </span>
          </dt>
          <dd>{usuarioEntity.apellido}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="carroComprasApp.usuario.email">Email</Translate>
            </span>
          </dt>
          <dd>{usuarioEntity.email}</dd>
          <dt>
            <span id="clave">
              <Translate contentKey="carroComprasApp.usuario.clave">Clave</Translate>
            </span>
          </dt>
          <dd>{usuarioEntity.clave}</dd>
          <dt>
            <span id="tipo">
              <Translate contentKey="carroComprasApp.usuario.tipo">Tipo</Translate>
            </span>
          </dt>
          <dd>{usuarioEntity.tipo ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/usuario" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/usuario/${usuarioEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default UsuarioDetail;
