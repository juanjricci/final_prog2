export interface IDistribuidor {
  id?: number;
  nombre?: string | null;
  descripcion?: string | null;
}

export const defaultValue: Readonly<IDistribuidor> = {};
