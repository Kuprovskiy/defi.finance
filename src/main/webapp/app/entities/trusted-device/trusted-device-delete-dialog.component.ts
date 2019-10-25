import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITrustedDevice } from 'app/shared/model/trusted-device.model';
import { TrustedDeviceService } from './trusted-device.service';

@Component({
  selector: 'jhi-trusted-device-delete-dialog',
  templateUrl: './trusted-device-delete-dialog.component.html'
})
export class TrustedDeviceDeleteDialogComponent {
  trustedDevice: ITrustedDevice;

  constructor(
    protected trustedDeviceService: TrustedDeviceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.trustedDeviceService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'trustedDeviceListModification',
        content: 'Deleted an trustedDevice'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-trusted-device-delete-popup',
  template: ''
})
export class TrustedDeviceDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ trustedDevice }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TrustedDeviceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.trustedDevice = trustedDevice;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/trusted-device', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/trusted-device', { outlets: { popup: null } }]);
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
