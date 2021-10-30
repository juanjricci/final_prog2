import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProductoCarro from './producto-carro';
import ProductoCarroDetail from './producto-carro-detail';
import ProductoCarroUpdate from './producto-carro-update';
import ProductoCarroDeleteDialog from './producto-carro-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProductoCarroUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProductoCarroUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProductoCarroDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProductoCarro} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProductoCarroDeleteDialog} />
  </>
);

export default Routes;
