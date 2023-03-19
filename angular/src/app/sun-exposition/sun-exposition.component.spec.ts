import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SunExpositionComponent } from './sun-exposition.component';

describe('SunExpositionComponent', () => {
  let component: SunExpositionComponent;
  let fixture: ComponentFixture<SunExpositionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SunExpositionComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SunExpositionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
