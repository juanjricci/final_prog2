import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProductoCarro from './producto-carro';
import CarroCompra from './carro-compra';
import Usuario from './usuario';
import Venta from './venta';
import ProductoVendido from './producto-vendido';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}producto-carro`} component={ProductoCarro} />
      <ErrorBoundaryRoute path={`${match.url}carro-compra`} component={CarroCompra} />
      <ErrorBoundaryRoute path={`${match.url}usuario`} component={Usuario} />
      <ErrorBoundaryRoute path={`${match.url}venta`} component={Venta} />
      <ErrorBoundaryRoute path={`${match.url}producto-vendido`} component={ProductoVendido} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
