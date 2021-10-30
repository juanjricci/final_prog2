import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICarroCompra } from 'app/shared/model/carro-compra.model';
import { getEntities as getCarroCompras } from 'app/entities/carro-compra/carro-compra.reducer';
import { getEntity, updateEntity, createEntity, reset } from './producto-carro.reducer';
import { IProductoCarro } from 'app/shared/model/producto-carro.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ProductoCarroUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const carroCompras = useAppSelector(state => state.carroCompra.entities);
  const productoCarroEntity = useAppSelector(state => state.productoCarro.entity);
  const loading = useAppSelector(state => state.productoCarro.loading);
  const updating = useAppSelector(state => state.productoCarro.updating);
  const updateSuccess = useAppSelector(state => state.productoCarro.updateSuccess);

  const handleClose = () => {
    props.history.push('/producto-carro');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCarroCompras({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...productoCarroEntity,
      ...values,
      idCarroCompra: carroCompras.find(it => it.id.toString() === values.idCarroCompraId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...productoCarroEntity,
          idCarroCompraId: productoCarroEntity?.idCarroCompra?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="carroComprasApp.productoCarro.home.createOrEditLabel" data-cy="ProductoCarroCreateUpdateHeading">
            <Translate contentKey="carroComprasApp.productoCarro.home.createOrEditLabel">Create or edit a ProductoCarro</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="producto-carro-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('carroComprasApp.productoCarro.idProducto')}
                id="producto-carro-idProducto"
                name="idProducto"
                data-cy="idProducto"
                type="text"
              />
              <ValidatedField
                label={translate('carroComprasApp.productoCarro.nombre')}
                id="producto-carro-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
              />
              <ValidatedField
                label={translate('carroComprasApp.productoCarro.descripcion')}
                id="producto-carro-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="text"
              />
              <ValidatedField
                label={translate('carroComprasApp.productoCarro.cantidad')}
                id="producto-carro-cantidad"
                name="cantidad"
                data-cy="cantidad"
                type="text"
              />
              <ValidatedField
                label={translate('carroComprasApp.productoCarro.precio')}
                id="producto-carro-precio"
                name="precio"
                data-cy="precio"
                type="text"
              />
              <ValidatedField
                id="producto-carro-idCarroCompra"
                name="idCarroCompraId"
                data-cy="idCarroCompra"
                label={translate('carroComprasApp.productoCarro.idCarroCompra')}
                type="select"
              >
                <option value="" key="0" />
                {carroCompras
                  ? carroCompras.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/producto-carro" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ProductoCarroUpdate;
