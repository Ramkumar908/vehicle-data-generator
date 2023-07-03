import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { VehicleDataGeneratorService } from '../../services/vehicle-data-generator.service';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
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
  selling_Dealer: string;
}

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.scss']
})
export class AdminDashboardComponent implements OnInit {

  public displayedColumns: string[] = [
    'vin',
    'reg_number',
    'reg_date',
    'brand',
    'mfg_date',
    'price',
    'currency',
    'action',
  ];
  public registrationContent: any = {};
  public dataList: any[] = [];
  public dataSource: MatTableDataSource<RegistrationDetails>;
  public totalLength = 0;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    private vehicleDataGeneratorService: VehicleDataGeneratorService,
    private changeDetector: ChangeDetectorRef,
    private loaderService: LoaderService,
    private router: Router,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.fetchRegistrationDetails();
  }
  
  fetchData(event) {
    console.log(event);
  }

  fetchRegistrationDetails(): void {
    this.loaderService.show();
    this.dataList = [];
    this.vehicleDataGeneratorService.getRegistrationDetails().subscribe(
      (data) => {
        if (data.response.length) {
          this.construcDataSource(data.response);
        } else {
          this.loaderService.hide();
        }
      },
      (error) => {
        this.loaderService.hide();
      }
    );
  }

  construcDataSource(data) {
    data.forEach((item) => {
      item['mfg_Date'] = moment(item['mfg_Date']).format('DD/MM/YYYY');
      item['reg_Date'] = moment(item['reg_Date']).format('DD/MM/YYYY');
      item['warranty_Date'] = moment(item['warranty_Date']).format('DD/MM/YYYY');
    });
    this.dataList = data;
    this.dataSource = new MatTableDataSource(this.dataList);
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

  onExportToExcel(): void {
    let dataList = this.dataList;
    dataList.forEach((data) => {
      delete data.created_date;
      delete data.created_user;
    });
    this.vehicleDataGeneratorService.exportToExcel(dataList, 'vehicle-data', 'Vehicle Data Generator');
  }

  navigateToPage(key: string): void {
    if (key === 'register') {
      this.router.navigate([
        'vehicle',
        'admin',
        'vehicle-registration',
      ]);
    } else if (key === 'search') {
      this.router.navigate(['vehicle', 'admin', 'search']);
    }
  }
}
