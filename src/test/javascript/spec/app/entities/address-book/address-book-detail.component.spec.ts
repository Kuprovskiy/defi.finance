/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefiTestModule } from '../../../test.module';
import { AddressBookDetailComponent } from 'app/entities/address-book/address-book-detail.component';
import { AddressBook } from 'app/shared/model/address-book.model';

describe('Component Tests', () => {
  describe('AddressBook Management Detail Component', () => {
    let comp: AddressBookDetailComponent;
    let fixture: ComponentFixture<AddressBookDetailComponent>;
    const route = ({ data: of({ addressBook: new AddressBook(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DefiTestModule],
        declarations: [AddressBookDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AddressBookDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AddressBookDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.addressBook).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
