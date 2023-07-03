import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  public loginUrl = environment.apiUrlGetway + '/api/auth/token';
  public signUpUrl = environment.apiUrlWithoutGetway + '/v2/api/user/registration';

  constructor(private http: HttpClient) {}

  login(payload: any): Observable<any> {
    return this.http.post(this.loginUrl, payload);
  }

  signUp(payload: any): Observable<any> {
    return this.http.post(this.signUpUrl, payload);
  }
}
