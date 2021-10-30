import { IUsuario } from 'app/shared/model/usuario.model';
import { IProductoCarro } from 'app/shared/model/producto-carro.model';
import { IVenta } from 'app/shared/model/venta.model';

export interface ICarroCompra {
  id?: number;
  idUsuario?: IUsuario | null;
  productoCarros?: IProductoCarro[] | null;
  ventas?: IVenta[] | null;
}

export const defaultValue: Readonly<ICarroCompra> = {};
