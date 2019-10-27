import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAddressBook } from 'app/shared/model/address-book.model';

@Component({
  selector: 'jhi-address-book-detail',
  templateUrl: './address-book-detail.component.html'
})
export class AddressBookDetailComponent implements OnInit {
  addressBook: IAddressBook;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ addressBook }) => {
      this.addressBook = addressBook;
    });
  }

  previousState() {
    window.history.back();
  }
}
