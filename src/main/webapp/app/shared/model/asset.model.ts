export interface IAsset {
  id?: number;
  name?: string;
  longName?: string;
  marketCap?: number;
  price?: number;
  supply?: number;
  isVisible?: boolean;
}

export class Asset implements IAsset {
  constructor(
    public id?: number,
    public name?: string,
    public longName?: string,
    public marketCap?: number,
    public price?: number,
    public supply?: number,
    public isVisible?: boolean
  ) {
    this.isVisible = this.isVisible || false;
  }
}
