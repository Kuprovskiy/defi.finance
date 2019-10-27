import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAddressBook } from 'app/shared/model/address-book.model';
import { AddressBookService } from './address-book.service';

@Component({
  selector: 'jhi-address-book-delete-dialog',
  templateUrl: './address-book-delete-dialog.component.html'
})
export class AddressBookDeleteDialogComponent {
  addressBook: IAddressBook;

  constructor(
    protected addressBookService: AddressBookService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.addressBookService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'addressBookListModification',
        content: 'Deleted an addressBook'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-address-book-delete-popup',
  template: ''
})
export class AddressBookDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ addressBook }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AddressBookDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.addressBook = addressBook;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/address-book', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/address-book', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
