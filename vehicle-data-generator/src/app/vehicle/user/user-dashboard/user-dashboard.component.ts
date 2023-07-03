import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { VehicleDataGeneratorService } from '../../services/vehicle-data-generator.service';
import { MatDialog } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { startWith, map } from 'rxjs/operators';
import { LoaderService } from '../../services/loader.service';
import * as moment from 'moment';

export interface RegistrationDetails {
  vin: string;
  reg_Number: string;
  reg_Date: string;
  brand: string;
  mfg_Date: string;
  price: string;
  currency: string;
  is_Insured: string;
  warranty_Date: string;
  warranty_Coverage: string;
  selling_Dealer: string
}

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.scss'],
})

export class UserDashboardComponent implements OnInit {

  public userRole: boolean = false;
  public searchVehicleForm: FormGroup;
  public showData: boolean = false;
  public displayedColumns: string[] = [
    'vin',
    'reg_Number',
    'reg_Date',
    'brand',
    'mfg_Date',
    'price',
    'currency',
    'action',
  ];
  public registrationContent: any = {};
  public dataSource: MatTableDataSource<RegistrationDetails>;
  public totalLength = 0;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  streets: string[] = [];
  filteredStreets: Observable<string[]>;

  constructor(
    private vehicleDataGeneratorService: VehicleDataGeneratorService,
    private changeDetector: ChangeDetectorRef,
    private loaderService: LoaderService,
    private router: Router,
    private dialog: MatDialog,
    private fb: FormBuilder
  ) {
    let role = this.router.routerState.snapshot.url.split('/')[2];
    if (role.toLowerCase() === 'admin'.toLowerCase()) {
      this.userRole = true;
    } else {
      this.userRole = false;
    }
  }

  ngOnInit(): void {
    this.fetchAllVin();
    this.initializingFrom();
  }

  initializingFrom() {
    this.searchVehicleForm = this.fb.group({
      vin: [''],
      reg_Number: [''],
    });
  }

  fetchAllVin() {
    this.vehicleDataGeneratorService.getAllVin().subscribe(
      (data) => {
        if (data.response && data.response.length) {
          data.response.forEach((data) => {
            this.streets.push(data.vin);
          });
          this.filteredStreets = this.searchVehicleForm.controls['vin'].valueChanges.pipe(
            startWith(''),
            map((value) => this._filter(value || ''))
          );
          this.loaderService.hide();
        } else {
          this.loaderService.hide();
        }
      },
      (error) => {
        this.loaderService.hide();
      }
    );
  }

  isFormValid(): boolean {
    return !(
      this.searchVehicleForm.controls['vin'].value ||
      this.searchVehicleForm.controls['reg_Number'].value
    );
  }

  fetchData(event): void {
    console.log(event);
  }

  resetSearchVehicleForm(): void {
    this.searchVehicleForm.reset();
    this.isError = false;
    this.showData = false;
  }

  erroMessage: any;
  isError = false;
  fetchSearchRegistrationDetails(payload: any): void {
    this.loaderService.show();
    this.showData = false;
    this.vehicleDataGeneratorService.searchVehicle(payload).subscribe(
      (data) => {
        if (data.response.length) {
          this.construcDataSource(data.response);
        } else {
          this.showData = false;
          this.loaderService.hide();
        }
      },
      (error) => {
        this.isError = true
        this.erroMessage = error.error.response;
        this.loaderService.hide();
      }
    );
  }

  onDismissError(){
    this.isError = false;
  }

  construcDataSource(data) {
    this.showData = true;
    this.isError = false;
    data.forEach((item) => {
      item['mfg_Date'] = moment(item['mfg_Date']).format('DD/MM/YYYY');
      item['reg_Date'] = moment(item['reg_Date']).format('DD/MM/YYYY');
      item['warranty_Date'] = moment(item['warranty_Date']).format('DD/MM/YYYY');
    });
    this.dataSource = new MatTableDataSource(data);
    this.changeDetector.detectChanges();
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.loaderService.hide();
  }

  openDialog(extractedRecordDialog, data): any {
    this.registrationContent = {};
    this.dialog.open(extractedRecordDialog, { panelClass: 'common-dialog' });
    this.registrationContent = data;
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  navigateToPage(): void {
    this.router.navigate(['vehicle', 'admin', 'dashboard']);
  }

  private _filter(value: string): string[] {
    const filterValue = this._normalizeValue(value);
    return this.streets.filter((street) =>
      this._normalizeValue(street).includes(filterValue)
    );
  }

  private _normalizeValue(value: string): string {
    return value.toLowerCase().replace(/\s/g, '');
  }

  omit_special_char(event) {
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

  allowNumericDigitsOnlyOnKeyUp(e) {
    const charCode = e.which ? e.which : e.keyCode;
    return !(charCode > 31 && (charCode < 48 || charCode > 57))
  }
}
