import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { VehicleComponent } from './vehicle.component';

const routes: Routes = [
  {
    path: '',
    component: VehicleComponent,
    children: [
      {
        path: 'admin',
        loadChildren: () => import('./admin/admin.module').then((module) => module.AdminModule)
      },
      {
        path: 'user',
        loadChildren: () => import('./user/user.module').then((module) => module.UserModule)
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class VehicleRoutingModule {}
