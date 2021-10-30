import dayjs from 'dayjs';
import { IProductoVendido } from 'app/shared/model/producto-vendido.model';
import { ICarroCompra } from 'app/shared/model/carro-compra.model';

export interface IVenta {
  id?: number;
  fechaVenta?: string | null;
  precioTotal?: number | null;
  productoVendidos?: IProductoVendido[] | null;
  idCarroCompra?: ICarroCompra | null;
}

export const defaultValue: Readonly<IVenta> = {};
