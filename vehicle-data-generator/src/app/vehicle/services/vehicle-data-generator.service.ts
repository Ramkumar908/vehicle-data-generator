import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as XLSX from 'xlsx';
import { saveAs } from 'file-saver';

@Injectable({
  providedIn: 'root',
})
export class VehicleDataGeneratorService {
  public registrationDetailsUrl = environment.apiUrlGetway;

  constructor(private http: HttpClient) {}

  getRegistrationDetails(): Observable<any> {
    return this.http.get(
      this.registrationDetailsUrl + '/v1/api/getAllVehicles'
    );
  }

  saveVehicleRegistrationDetails(obj: any): Observable<any> {
    return this.http.post(this.registrationDetailsUrl + '/v1/api/vehicle', obj);
  }

  searchVehicle(data: any): Observable<any> {
    return this.http.get(
      this.registrationDetailsUrl +
        `/v1/api/searchVehicle?vin=${data.vin}&regNumber=${data.reg_Number}`
    );
  }

  getAllVin(): Observable<any> {
    return this.http.get(
      this.registrationDetailsUrl + '/v1/api/getAllVehicles'
    );
  }

  exportToExcel(data: any[], fileName: string, sheetName: string): void {
    const worksheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(data);
    const workbook: XLSX.WorkBook = {
      Sheets: { [sheetName]: worksheet },
      SheetNames: [sheetName],
    };
    const excelBuffer: any = XLSX.write(workbook, {
      bookType: 'xlsx',
      type: 'array',
    });
    const fileData: Blob = new Blob([excelBuffer], {
      type:
        'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet charset=utf-8',
    });
    saveAs(fileData, fileName + '.xlsx');
  }
}
