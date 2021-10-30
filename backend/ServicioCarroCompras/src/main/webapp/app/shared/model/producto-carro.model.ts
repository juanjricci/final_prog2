import { ICarroCompra } from 'app/shared/model/carro-compra.model';

export interface IProductoCarro {
  id?: number;
  idProducto?: number | null;
  nombre?: string | null;
  descripcion?: string | null;
  cantidad?: number | null;
  precio?: number | null;
  idCarroCompra?: ICarroCompra | null;
}

export const defaultValue: Readonly<IProductoCarro> = {};
