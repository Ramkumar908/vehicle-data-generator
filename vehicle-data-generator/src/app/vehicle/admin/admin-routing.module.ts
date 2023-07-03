import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { UserDashboardComponent } from '../user/user-dashboard/user-dashboard.component';
import { VehicleRegistrationComponent } from './vehicle-registration/vehicle-registration.component';

const routes: Routes = [
  {
    path: 'dashboard', 
    component: AdminDashboardComponent
  },
  {
    path: 'search', 
    component: UserDashboardComponent
  },
  {
    path: 'vehicle-registration',
    component: VehicleRegistrationComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
