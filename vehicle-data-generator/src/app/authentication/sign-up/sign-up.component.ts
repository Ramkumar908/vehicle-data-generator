import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../auth-service/auth.service';
import { Router } from '@angular/router';
import { LoaderService } from 'src/app/vehicle/services/loader.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss'],
})

export class SignUpComponent implements OnInit {
  public signUpForm: FormGroup;
  public userRoleData: any[] = [];
  public emailPattern = "^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$";

  constructor(private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private loaderService: LoaderService) {}

  ngOnInit(): void {
    this.initializingFrom();
    this.fetchUserRole();
  }

  initializingFrom(): void {
    this.signUpForm = this.fb.group({
      name: ['',Validators.pattern('^[a-zA-Z ]*$')],
      username: ['', Validators.required],
      email: ['', Validators.pattern(this.emailPattern)],
      mobileNo: ['', Validators.required],
      // role: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  fetchUserRole(): void {
    this.userRoleData = [
      {
        id: 1,
        role: 'admin',
      },
      {
        id: 2,
        role: 'user',
      },
    ];
  }

  isFromValid(): boolean {
    return !this.signUpForm.valid;
  }

  onSignUp(payload): void {
    this.loaderService.show();
    payload['role'] = 'USER';
    this.authService.signUp(payload).subscribe(
      (response) => {
        if(response.success && (response.response !== null)){
          this.router.navigate(['auth', 'login']);
        }
      },
      (error) => {
        this.loaderService.hide();
      }
    );
  }

  number(e) {
    const charCode = e.which ? e.which : e.keyCode;
    return !(charCode > 31 && (charCode < 48 || charCode > 57))
  }
}
