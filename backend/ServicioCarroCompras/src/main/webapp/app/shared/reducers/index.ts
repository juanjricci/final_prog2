import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale from './locale';
import authentication from './authentication';
import applicationProfile from './application-profile';

import administration from 'app/modules/administration/administration.reducer';
import userManagement from 'app/modules/administration/user-management/user-management.reducer';
import register from 'app/modules/account/register/register.reducer';
import activate from 'app/modules/account/activate/activate.reducer';
import password from 'app/modules/account/password/password.reducer';
import settings from 'app/modules/account/settings/settings.reducer';
import passwordReset from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import productoCarro from 'app/entities/producto-carro/producto-carro.reducer';
// prettier-ignore
import carroCompra from 'app/entities/carro-compra/carro-compra.reducer';
// prettier-ignore
import usuario from 'app/entities/usuario/usuario.reducer';
// prettier-ignore
import venta from 'app/entities/venta/venta.reducer';
// prettier-ignore
import productoVendido from 'app/entities/producto-vendido/producto-vendido.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const rootReducer = {
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  productoCarro,
  carroCompra,
  usuario,
  venta,
  productoVendido,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
};

export default rootReducer;
