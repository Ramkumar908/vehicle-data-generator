import { Component, OnInit } from '@angular/core';
import { VehicleDataGeneratorService } from '../../services/vehicle-data-generator.service';
import { LoaderService } from '../../services/loader.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-vehicle-registration',
  templateUrl: './vehicle-registration.component.html',
  styleUrls: ['./vehicle-registration.component.scss'],
})

export class VehicleRegistrationComponent implements OnInit {

  public vehicleRegistrationForm: FormGroup;
  public brandData: any[] = [];
  public currencyData: any[] = [];
  public warrantyCoverageData: any[] = [];
  public sellingDealerData: any[] = [];
  public minRegDate: Date;

  constructor(
    private vehicleDataGeneratorService: VehicleDataGeneratorService,
    private loaderService: LoaderService,
    private router: Router, private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.initializingFrom();
    this.fetchBrand();
    this.fetchCurrency();
    this.fetchWarrantyCoverage();
    this.fetchSellingDealer();
  }

  initializingFrom(): void {
    this.vehicleRegistrationForm = this.fb.group({
      vin: ['', Validators.required],
      reg_Number: ['', Validators.required],
      reg_Date: ['', Validators.required],
      brand: ['', Validators.required],
      mfg_Date: ['', Validators.required],
      price: ['', Validators.required],
      currency: ['', Validators.required],
      is_Insured: [false, Validators.required],
      warranty_Date: ['', Validators.required],
      warranty_Coverage: ['', Validators.required],
      selling_Dealer: ['', Validators.required],
    });
  }

  fetchBrand(): void {
    this.brandData = [
      {
        id: 1,
        name: 'Mahindra & Mahindra',
      },
      {
        id: 2,
        name: 'Maruti Suzuki',
      },
      {
        id: 3,
        name: 'Hyundai',
      },
      {
        id: 4,
        name: 'Tata Motors',
      },
      {
        id: 5,
        name: 'Kia',
      },
      {
        id: 6,
        name: 'Toyota',
      },
      {
        id: 7,
        name: 'Honda',
      },
      {
        id: 8,
        name: 'Renault',
      },
      {
        id: 9,
        name: 'Skoda',
      },
      {
        id: 10,
        name: 'MG',
      },
    ];
  }

  fetchCurrency(): void {
    this.currencyData = [{ id: 1, name: 'INR' }];
  }

  fetchWarrantyCoverage(): void {
    this.warrantyCoverageData = [
      { id: 1, name: '0 to 1 Years' },
      { id: 2, name: '1 to 2 Years' },
      { id: 3, name: '2 to 3 Years' },
    ];
  }

  fetchSellingDealer(): void {
    this.sellingDealerData = [
      { id: 1, name: 'Ankit Belge' },
      { id: 2, name: 'Sandip Pawar' },
      { id: 3, name: 'Sachin Pardeshi' },
      { id: 4, name: 'Gauri Patle' },
    ];
  }

  vehicleRegistration(payload: any): void {
    this.loaderService.show();
    this.vehicleDataGeneratorService.saveVehicleRegistrationDetails(payload).subscribe(
      resp => {
        if (resp != null && resp != undefined) {
          this.router.navigate(['vehicle', 'admin', 'dashboard']);
        } else {
          this.vehicleRegistrationForm.reset();
          this.loaderService.hide();
        }
      },
      error => {
        this.loaderService.hide();
      }
    )
  }

  fetchMfgDate(): void {
    this.minRegDate = this.vehicleRegistrationForm.controls['mfg_Date'].value;
  }

  isFromValid(): boolean {
    return !this.vehicleRegistrationForm.valid;
  }

  number(e: any): any {
    const charCode = e.which ? e.which : e.keyCode;
    return !(charCode > 31 && (charCode < 48 || charCode > 57));
  }

  specialChar(event: any): any {
    var k;
    k = event.charCode;
    return (
      (k > 64 && k < 91) ||
      (k > 96 && k < 123) ||
      k == 8 ||
      k == 32 ||
      (k >= 48 && k <= 57)
    );
  }

  navigateToPage(): void {
    this.router.navigate(['vehicle', 'admin', 'dashboard']);
  }
}
