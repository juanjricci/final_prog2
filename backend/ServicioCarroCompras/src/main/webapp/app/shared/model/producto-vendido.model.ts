import { IVenta } from 'app/shared/model/venta.model';

export interface IProductoVendido {
  id?: number;
  idProducto?: number | null;
  nombre?: string | null;
  descripcion?: string | null;
  cantidad?: number | null;
  precioTotal?: number | null;
  idVenta?: IVenta | null;
}

export const defaultValue: Readonly<IProductoVendido> = {};
