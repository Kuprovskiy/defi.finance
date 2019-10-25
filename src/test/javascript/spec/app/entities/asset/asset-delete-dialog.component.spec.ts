/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DefiTestModule } from '../../../test.module';
import { AssetDeleteDialogComponent } from 'app/entities/asset/asset-delete-dialog.component';
import { AssetService } from 'app/entities/asset/asset.service';

describe('Component Tests', () => {
  describe('Asset Management Delete Component', () => {
    let comp: AssetDeleteDialogComponent;
    let fixture: ComponentFixture<AssetDeleteDialogComponent>;
    let service: AssetService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DefiTestModule],
        declarations: [AssetDeleteDialogComponent]
      })
        .overrideTemplate(AssetDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AssetDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AssetService);
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
