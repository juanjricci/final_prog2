import { IDistribuidor } from 'app/shared/model/distribuidor.model';

export interface IProducto {
  id?: number;
  nombre?: string | null;
  descripcion?: string | null;
  precio?: number | null;
  cantVendida?: number | null;
  distribuidor?: IDistribuidor | null;
}

export const defaultValue: Readonly<IProducto> = {};
