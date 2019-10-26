/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { DefiTestModule } from '../../../test.module';
import { WalletUpdateComponent } from 'app/entities/wallet/wallet-update.component';
import { WalletService } from 'app/entities/wallet/wallet.service';
import { Wallet } from 'app/shared/model/wallet.model';

describe('Component Tests', () => {
  describe('Wallet Management Update Component', () => {
    let comp: WalletUpdateComponent;
    let fixture: ComponentFixture<WalletUpdateComponent>;
    let service: WalletService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DefiTestModule],
        declarations: [WalletUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(WalletUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WalletUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WalletService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Wallet(123);
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
        const entity = new Wallet();
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
