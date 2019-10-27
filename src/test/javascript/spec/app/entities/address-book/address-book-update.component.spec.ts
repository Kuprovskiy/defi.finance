/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { DefiTestModule } from '../../../test.module';
import { AddressBookUpdateComponent } from 'app/entities/address-book/address-book-update.component';
import { AddressBookService } from 'app/entities/address-book/address-book.service';
import { AddressBook } from 'app/shared/model/address-book.model';

describe('Component Tests', () => {
  describe('AddressBook Management Update Component', () => {
    let comp: AddressBookUpdateComponent;
    let fixture: ComponentFixture<AddressBookUpdateComponent>;
    let service: AddressBookService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DefiTestModule],
        declarations: [AddressBookUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AddressBookUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AddressBookUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AddressBookService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AddressBook(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new AddressBook();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
