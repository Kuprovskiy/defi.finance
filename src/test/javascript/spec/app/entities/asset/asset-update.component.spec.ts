/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { DefiTestModule } from '../../../test.module';
import { AssetUpdateComponent } from 'app/entities/asset/asset-update.component';
import { AssetService } from 'app/entities/asset/asset.service';
import { Asset } from 'app/shared/model/asset.model';

describe('Component Tests', () => {
  describe('Asset Management Update Component', () => {
    let comp: AssetUpdateComponent;
    let fixture: ComponentFixture<AssetUpdateComponent>;
    let service: AssetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DefiTestModule],
        declarations: [AssetUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AssetUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AssetUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AssetService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Asset(123);
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
        const entity = new Asset();
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
