import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VehicleRegistrationComponent } from './vehicle-registration.component';

describe('VehicleRegistrationComponent', () => {
  let component: VehicleRegistrationComponent;
  let fixture: ComponentFixture<VehicleRegistrationComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VehicleRegistrationComponent]
    });
    fixture = TestBed.createComponent(VehicleRegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
