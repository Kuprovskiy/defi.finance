import { Moment } from 'moment';

export interface ITrustedDevice {
  id?: number;
  device?: string;
  deviceVersion?: string;
  deviceOs?: string;
  deviceDetails?: string;
  location?: string;
  createdAt?: Moment;
  ipAddress?: string;
  trusted?: boolean;
  userLogin?: string;
  userId?: number;
}

export class TrustedDevice implements ITrustedDevice {
  constructor(
    public id?: number,
    public device?: string,
    public deviceVersion?: string,
    public deviceOs?: string,
    public deviceDetails?: string,
    public location?: string,
    public createdAt?: Moment,
    public ipAddress?: string,
    public trusted?: boolean,
    public userLogin?: string,
    public userId?: number
  ) {
    this.trusted = this.trusted || false;
  }
}
