import { Moment } from 'moment';

export interface IAddressBook {
  id?: number;
  phoneNumber?: string;
  email?: string;
  fullName?: string;
  invitedDate?: Moment;
  createdDate?: Moment;
  userLogin?: string;
  userId?: number;
  friendLogin?: string;
  friendId?: number;
}

export class AddressBook implements IAddressBook {
  constructor(
    public id?: number,
    public phoneNumber?: string,
    public email?: string,
    public fullName?: string,
    public invitedDate?: Moment,
    public createdDate?: Moment,
    public userLogin?: string,
    public userId?: number,
    public friendLogin?: string,
    public friendId?: number
  ) {}
}
