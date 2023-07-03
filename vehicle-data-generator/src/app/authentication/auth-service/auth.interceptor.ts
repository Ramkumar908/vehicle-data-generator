import { HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {
  HttpInterceptor,
  HttpHandler,
  HttpRequest,
} from '@angular/common/http';
import { TokenStorageService } from '../auth-service/token-storage.service';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthInterceptor implements HttpInterceptor {
  constructor(private token: TokenStorageService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let authReq = req;
    const authtoken = this.token.getAuthToken();
    const jwtTtoken = this.token.getJwtToken();

    if (authtoken !== null && jwtTtoken !== null) {
      authReq = req.clone({
        setHeaders: {
          // Auth_Token: `Bearer ${authtoken}`,
          // Jwt_Token: `${jwtTtoken}`,
          /***Need to be remove***/
          Authorization: `Bearer ${authtoken}`
          /***Need to be remove***/
        }
      });
    }
    return next.handle(authReq);
  }
}
