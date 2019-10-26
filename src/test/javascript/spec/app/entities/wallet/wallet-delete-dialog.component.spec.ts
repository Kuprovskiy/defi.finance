/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DefiTestModule } from '../../../test.module';
import { WalletDeleteDialogComponent } from 'app/entities/wallet/wallet-delete-dialog.component';
import { WalletService } from 'app/entities/wallet/wallet.service';

describe('Component Tests', () => {
  describe('Wallet Management Delete Component', () => {
    let comp: WalletDeleteDialogComponent;
    let fixture: ComponentFixture<WalletDeleteDialogComponent>;
    let service: WalletService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DefiTestModule],
        declarations: [WalletDeleteDialogComponent]
      })
        .overrideTemplate(WalletDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WalletDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WalletService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
