import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IVenta } from 'app/shared/model/venta.model';
import { getEntities as getVentas } from 'app/entities/venta/venta.reducer';
import { getEntity, updateEntity, createEntity, reset } from './producto-vendido.reducer';
import { IProductoVendido } from 'app/shared/model/producto-vendido.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ProductoVendidoUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const ventas = useAppSelector(state => state.venta.entities);
  const productoVendidoEntity = useAppSelector(state => state.productoVendido.entity);
  const loading = useAppSelector(state => state.productoVendido.loading);
  const updating = useAppSelector(state => state.productoVendido.updating);
  const updateSuccess = useAppSelector(state => state.productoVendido.updateSuccess);

  const handleClose = () => {
    props.history.push('/producto-vendido');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getVentas({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...productoVendidoEntity,
      ...values,
      idVenta: ventas.find(it => it.id.toString() === values.idVentaId.toString()),
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
          ...productoVendidoEntity,
          idVentaId: productoVendidoEntity?.idVenta?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="carroComprasApp.productoVendido.home.createOrEditLabel" data-cy="ProductoVendidoCreateUpdateHeading">
            <Translate contentKey="carroComprasApp.productoVendido.home.createOrEditLabel">Create or edit a ProductoVendido</Translate>
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
                  id="producto-vendido-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('carroComprasApp.productoVendido.idProducto')}
                id="producto-vendido-idProducto"
                name="idProducto"
                data-cy="idProducto"
                type="text"
              />
              <ValidatedField
                label={translate('carroComprasApp.productoVendido.nombre')}
                id="producto-vendido-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
              />
              <ValidatedField
                label={translate('carroComprasApp.productoVendido.descripcion')}
                id="producto-vendido-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="text"
              />
              <ValidatedField
                label={translate('carroComprasApp.productoVendido.cantidad')}
                id="producto-vendido-cantidad"
                name="cantidad"
                data-cy="cantidad"
                type="text"
              />
              <ValidatedField
                label={translate('carroComprasApp.productoVendido.precioTotal')}
                id="producto-vendido-precioTotal"
                name="precioTotal"
                data-cy="precioTotal"
                type="text"
              />
              <ValidatedField
                id="producto-vendido-idVenta"
                name="idVentaId"
                data-cy="idVenta"
                label={translate('carroComprasApp.productoVendido.idVenta')}
                type="select"
              >
                <option value="" key="0" />
                {ventas
                  ? ventas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/producto-vendido" replace color="info">
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

export default ProductoVendidoUpdate;
