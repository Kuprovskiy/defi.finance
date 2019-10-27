/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DefiTestModule } from '../../../test.module';
import { AddressBookDeleteDialogComponent } from 'app/entities/address-book/address-book-delete-dialog.component';
import { AddressBookService } from 'app/entities/address-book/address-book.service';

describe('Component Tests', () => {
  describe('AddressBook Management Delete Component', () => {
    let comp: AddressBookDeleteDialogComponent;
    let fixture: ComponentFixture<AddressBookDeleteDialogComponent>;
    let service: AddressBookService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DefiTestModule],
        declarations: [AddressBookDeleteDialogComponent]
      })
        .overrideTemplate(AddressBookDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AddressBookDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AddressBookService);
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
