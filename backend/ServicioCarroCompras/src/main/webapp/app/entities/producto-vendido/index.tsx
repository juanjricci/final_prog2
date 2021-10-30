import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProductoVendido from './producto-vendido';
import ProductoVendidoDetail from './producto-vendido-detail';
import ProductoVendidoUpdate from './producto-vendido-update';
import ProductoVendidoDeleteDialog from './producto-vendido-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProductoVendidoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProductoVendidoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProductoVendidoDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProductoVendido} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProductoVendidoDeleteDialog} />
  </>
);

export default Routes;
