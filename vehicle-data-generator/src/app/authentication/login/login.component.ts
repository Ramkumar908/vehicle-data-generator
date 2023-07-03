import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AuthService } from '../auth-service/auth.service';
import { Router } from '@angular/router';
import { TokenStorageService } from '../auth-service/token-storage.service';
import { LoaderService } from 'src/app/vehicle/services/loader.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})

export class LoginComponent implements OnInit {

  public loginForm: FormGroup;
  public authData: any;
  public appRole: string = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private loaderService: LoaderService,
    private tokenStorage: TokenStorageService
  ) {}

  ngOnInit(): void {
    this.loaderService.show();
    this.initializingFrom();
  }

  initializingFrom(): void {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    });
    this.loaderService.hide();
  }

  isFromValid(): boolean {
    return !this.loginForm.valid;
  }

  onLogin(payload: any): void {
    this.loaderService.show();
    this.authData = {};
    this.appRole = '';
    this.authService.login(payload).subscribe(
      (response: any) => {
        if (response.token.length) {
          this.authData = response;
          this.tokenStorage.setAuthToken(this.authData.token);
          this.tokenStorage.setJwtToken(this.authData.token);
          this.tokenStorage.setAppRole(this.authData.userRole);
          this.appRole = this.tokenStorage.getAppRole();

          if(this.appRole.toLowerCase() === 'ADMIN'.toLowerCase()){
            this.router.navigate(['vehicle', 'admin', 'dashboard']);
          } else if(this.appRole.toLowerCase() === 'USER'.toLowerCase()) {
            this.router.navigate(['vehicle', 'user', 'dashboard']);
          } else {
            console.log('Role Missing');
            this.loaderService.hide();
          }
        }
      },
      (error: any) => {
        this.loaderService.hide();
      }
    );
  }
}
