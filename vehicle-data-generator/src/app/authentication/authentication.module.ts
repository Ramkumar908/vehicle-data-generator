import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { AuthenticationRoutingModule } from './authentication-routing.module';
import { SharedModule } from '../vehicle/shared/shared.module';

@NgModule({
  declarations: [
    LoginComponent, 
    SignUpComponent
  ],
  imports: [
    CommonModule, 
    AuthenticationRoutingModule, 
    SharedModule
  ],
})
export class AuthenticationModule {}
