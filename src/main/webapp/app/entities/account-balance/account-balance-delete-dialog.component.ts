import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAccountBalance } from 'app/shared/model/account-balance.model';
import { AccountBalanceService } from './account-balance.service';

@Component({
  selector: 'jhi-account-balance-delete-dialog',
  templateUrl: './account-balance-delete-dialog.component.html'
})
export class AccountBalanceDeleteDialogComponent {
  accountBalance: IAccountBalance;

  constructor(
    protected accountBalanceService: AccountBalanceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.accountBalanceService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'accountBalanceListModification',
        content: 'Deleted an accountBalance'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-account-balance-delete-popup',
  template: ''
})
export class AccountBalanceDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ accountBalance }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AccountBalanceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.accountBalance = accountBalance;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/account-balance', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/account-balance', { outlets: { popup: null } }]);
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
