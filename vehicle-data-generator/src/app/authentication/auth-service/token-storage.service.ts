import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class TokenStorageService {
  
  public AUTH_TOKEN_KEY = 'auth-token';
  public JWT_TOKEN_KEY = 'jwt-token';
  public ROLE_KEY = 'role';

  constructor() {}

  logout(): void {
    window.sessionStorage.removeItem(this.AUTH_TOKEN_KEY);
    window.sessionStorage.removeItem(this.JWT_TOKEN_KEY);
    window.sessionStorage.removeItem(this.ROLE_KEY);
  }

  public setAuthToken(token: string): void {
    window.sessionStorage.removeItem(this.AUTH_TOKEN_KEY);
    window.sessionStorage.setItem(this.AUTH_TOKEN_KEY, token);
  }

  public getAuthToken(): string {
    return window.sessionStorage.getItem(this.AUTH_TOKEN_KEY);
  }

  public setJwtToken(token: string): void {
    window.sessionStorage.removeItem(this.JWT_TOKEN_KEY);
    window.sessionStorage.setItem(this.JWT_TOKEN_KEY, token);
  }

  public getJwtToken(): string {
    return window.sessionStorage.getItem(this.JWT_TOKEN_KEY);
  }

  public setAppRole(role: string): void {
    window.sessionStorage.removeItem(this.ROLE_KEY);
    window.sessionStorage.setItem(this.ROLE_KEY, role);
  }

  public getAppRole(): string {
    return window.sessionStorage.getItem(this.ROLE_KEY);
  }
}
