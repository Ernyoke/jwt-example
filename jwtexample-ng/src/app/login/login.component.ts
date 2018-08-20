import { Token } from './../model/Token';
import { AuthenticationService } from './../authentication.service';
import { Credentials } from './../model/Credentials';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  private credentials: Credentials;
  public displayLoginFailed: boolean;

  constructor(private authService: AuthenticationService, private router: Router) {
    this.credentials = new Credentials();
    this.displayLoginFailed = false;
  }

  ngOnInit() {}

  onSubmit() {
    this.authService.isAuthenticated().subscribe(response => {
      if (!response) {
        this.authService.login(this.credentials).subscribe(
          (loginResponse: Token) => {
            this.displayLoginFailed = false;
            this.router.navigate(['/home']);
          },
          (loginError: HttpErrorResponse) => {
            this.displayLoginFailed = true;
            console.log(loginError);
          }
        );
      }
    });
  }

}
