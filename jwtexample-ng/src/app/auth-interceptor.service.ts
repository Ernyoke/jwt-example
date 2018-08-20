import { AuthenticationService } from './authentication.service';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthInterceptorService implements HttpInterceptor {
  constructor(
    private authService: AuthenticationService,
    private router: Router
  ) {}

  // Intercept any HTTP request and append the JWT token to its header if the token exists.
  // In case of 401 (Unauthorized) error, clear the local storage because the existing token is no longer valid.
  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const token = this.authService.getToken();
    if (token) {
      return next.handle(this.addToken(request, token)).pipe(
        catchError((error: HttpErrorResponse) => {
          if (error.status === 401) {
            this.authService.logout();
            this.router.navigate(['/home']);
          }
          return throwError(error);
        })
      );
    } else {
      return next.handle(request);
    }
  }

  private addToken(request: HttpRequest<any>, token: string) {
    return request.clone({ setHeaders: { Authorization: 'Bearer ' + token } });
  }
}
