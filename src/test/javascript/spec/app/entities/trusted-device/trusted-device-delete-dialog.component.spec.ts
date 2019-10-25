/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DefiTestModule } from '../../../test.module';
import { TrustedDeviceDeleteDialogComponent } from 'app/entities/trusted-device/trusted-device-delete-dialog.component';
import { TrustedDeviceService } from 'app/entities/trusted-device/trusted-device.service';

describe('Component Tests', () => {
  describe('TrustedDevice Management Delete Component', () => {
    let comp: TrustedDeviceDeleteDialogComponent;
    let fixture: ComponentFixture<TrustedDeviceDeleteDialogComponent>;
    let service: TrustedDeviceService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DefiTestModule],
        declarations: [TrustedDeviceDeleteDialogComponent]
      })
        .overrideTemplate(TrustedDeviceDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TrustedDeviceDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TrustedDeviceService);
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
