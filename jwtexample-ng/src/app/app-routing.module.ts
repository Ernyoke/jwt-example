import { AdminComponent } from './admin/admin.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LogoutComponent } from './logout/logout.component';
import { UserComponent } from './user/user.component';

const appRoutes: Routes = [
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'login',
    component: LoginComponent
    // canActivate: [AuthGuardService]
  },
  {
    path: 'logout',
    component: LogoutComponent
    // canActivate: [AuthGuardService]
  },
  {
    path: 'admin',
    component: AdminComponent
    // canActivate: [AuthGuardService]
  },
  {
    path: 'user',
    component: UserComponent
    // canActivate: [AuthGuardService]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes, { enableTracing: true })],
  exports: [RouterModule]
})
export class AppRoutingModule {}
