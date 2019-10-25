/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { DefiTestModule } from '../../../test.module';
import { TrustedDeviceUpdateComponent } from 'app/entities/trusted-device/trusted-device-update.component';
import { TrustedDeviceService } from 'app/entities/trusted-device/trusted-device.service';
import { TrustedDevice } from 'app/shared/model/trusted-device.model';

describe('Component Tests', () => {
  describe('TrustedDevice Management Update Component', () => {
    let comp: TrustedDeviceUpdateComponent;
    let fixture: ComponentFixture<TrustedDeviceUpdateComponent>;
    let service: TrustedDeviceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DefiTestModule],
        declarations: [TrustedDeviceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TrustedDeviceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TrustedDeviceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TrustedDeviceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TrustedDevice(123);
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
        const entity = new TrustedDevice();
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
