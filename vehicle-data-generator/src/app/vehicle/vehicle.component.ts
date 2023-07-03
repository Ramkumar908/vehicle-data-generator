import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { LoaderService } from './services/loader.service';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-vehicle',
  templateUrl: './vehicle.component.html',
  styleUrls: ['./vehicle.component.scss'],
})
export class VehicleComponent implements OnInit {

  isLoading: Subject<boolean> = this.loaderService.isLoading;

  constructor(
    private loaderService: LoaderService,
    private changeDetector: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.changeDetector.detectChanges();
  }
}
